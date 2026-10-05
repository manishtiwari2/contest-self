"""Contest judge for Java and Python solutions. Needs Python 3.10+ and a JDK (javac + java).

    python judge.py                            run the only contest in contests/
    python judge.py graph-traversals           run a specific contest
    python judge.py graph-traversals --share   let people on your Wi-Fi join too
    python judge.py --new my-contest           create a new contest to fill in

Each contest is a folder in contests/ holding contest.json and one folder per
problem. See README.md for the format.
"""
import argparse
import json
import os
import re
import secrets
import shutil
import socket
import statistics
import subprocess
import sys
import tempfile
import threading
import time
import webbrowser
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from urllib.parse import parse_qs, urlparse

ROOT = Path(__file__).resolve().parent
CONTESTS = ROOT / "contests"
DATA = ROOT / "data"
INDEX = ROOT / "static" / "index.html"

JAVA_FLAGS = ["-Xss256m", "-Xmx512m", "-XX:+UseSerialGC"]
SLACK = 0.25                   # seconds of timing jitter forgiven on top of the limit
MAX_OUTPUT = 32 * 1024 * 1024  # bytes a program may print before it is stopped
PREVIEW = 2000                 # characters of a failing test shown back
MAX_BODY = 1024 * 1024

JAVA = JAVAC = None
JAVA_VERSION = ""
PYTHON_VERSION = f"Python {sys.version.split()[0]}"
STARTUP = {"java": 0.0, "python": 0.0}  # measured start-up time per language, not charged to solutions
JUDGE_LOCK = threading.Lock()  # one program at a time, so timings stay fair
CONTEST = STORE = None
SHARE = False       # others may join, with the join code
SHARE_MODE = ""     # "wifi" or "online"
SHARE_URL = ""
JOIN_FAILS = []     # times of recent wrong join codes, to slow down guessing
PRESENCE = {}       # token -> what a player is doing right now, for the host dashboard (not saved)
PROXY_HEADERS = ("Cf-Connecting-Ip", "Cf-Ray", "X-Forwarded-For", "X-Forwarded-Host")


def die(msg):
    sys.exit(f"\nError: {msg}\n")


def decode(b):
    return b.decode("utf-8", "replace")


def preview(text):
    if len(text) <= PREVIEW:
        return text
    return text[:PREVIEW] + f"\n... ({len(text) - PREVIEW:,} more characters)"


def strip_tags(s):
    return re.sub(r"<[^>]+>", "", s).strip()


# ------------------------------------------------------------------ contest files
def load_problem(cdir, index, entry, default_tl, python_factor=3.0):
    folder = cdir / entry["folder"]
    statement = folder / "statement.html"
    if not statement.is_file():
        die(f"{statement} is missing.")
    raw = statement.read_text(encoding="utf-8", errors="replace")
    h1 = re.search(r"<h1[^>]*>(.*?)</h1>", raw, re.S)
    body = re.search(r"</header>(.*)</main>", raw, re.S)
    if body:
        body = body.group(1)
    else:
        body = re.search(r"<body[^>]*>(.*)</body>", raw, re.S)
        body = re.sub(r"<h1[^>]*>.*?</h1>", "", body.group(1) if body else raw, count=1, flags=re.S)
    body = re.sub(r"<nav\b.*?</nav>", "", body, flags=re.S | re.I)
    body = re.sub(r"<(script|style)\b.*?</\1>", "", body, flags=re.S | re.I)

    tests = []
    for kind in ("sample", "secret"):
        for inp in sorted((folder / "tests" / kind).glob("*.in")):
            ans = inp.with_suffix(".ans")
            if not ans.is_file():
                die(f"{inp} has no matching {ans.name}.")
            tests.append({"kind": kind, "input": inp, "answer": ans.read_bytes().split()})
    if not tests:
        die(f"{folder / 'tests'} has no .in files.")
    samples = [
        {"input": t["input"].read_text(encoding="utf-8"),
         "output": t["input"].with_suffix(".ans").read_text(encoding="utf-8")}
        for t in tests if t["kind"] == "sample"
    ]
    starters = {lang: folder / info["file"] for lang, info in LANGS.items()}
    java_tl = float(entry.get("time_limit_seconds", default_tl))
    return {
        "id": chr(ord("A") + index),
        "title": strip_tags(h1.group(1)) if h1 else entry["folder"],
        "starters": {lang: p.read_text(encoding="utf-8") if p.is_file() else None for lang, p in starters.items()},
        "points": int(entry.get("points", 100)),
        "time_limit": java_tl,
        "time_limits": {"java": java_tl,
                        "python": float(entry.get("python_time_limit_seconds", java_tl * python_factor))},
        "statement": body.strip(),
        "samples": samples,
        "tests": tests,
    }


class Contest:
    def __init__(self, cid):
        self.id = cid
        self.dir = CONTESTS / cid
        try:
            cfg = json.loads((self.dir / "contest.json").read_text(encoding="utf-8"))
        except (OSError, ValueError) as e:
            die(f"Could not read {self.dir / 'contest.json'}: {e}")
        self.title = cfg.get("title", cid)
        self.duration = int(cfg.get("duration_minutes", 120)) * 60
        self.penalty = int(cfg.get("wrong_attempt_penalty_minutes", 10))
        tl = float(cfg.get("time_limit_seconds", 1))
        if not cfg.get("problems"):
            die(f"{self.dir / 'contest.json'} lists no problems.")
        factor = float(cfg.get("python_time_multiplier", 3))
        self.problems = [load_problem(self.dir, i, p, tl, factor) for i, p in enumerate(cfg["problems"])]
        self.by_id = {p["id"]: p for p in self.problems}

    def phase(self, start, now):
        if start is None:
            return "lobby"
        return "running" if now < start + self.duration else "ended"


class Store:
    """Participants and submissions for one contest, kept in data/<contest>.json."""

    def __init__(self, path):
        self.path = path
        self.lock = threading.Lock()
        try:
            self.data = json.loads(path.read_text(encoding="utf-8"))
        except (OSError, ValueError):
            self.data = {"join_code": f"{secrets.randbelow(10**6):06d}", "start": None,
                         "users": {}, "submissions": []}
            self.save()

    def save(self):
        self.path.parent.mkdir(exist_ok=True)
        tmp = self.path.with_suffix(".tmp")
        tmp.write_text(json.dumps(self.data), encoding="utf-8")
        os.replace(tmp, self.path)


# ------------------------------------------------------------------ running programs
LANGS = {"java": {"name": "Java", "file": "Main.java"}, "python": {"name": "Python", "file": "main.py"}}

# Runs main.py in a thread with a big stack and a high recursion limit, so deep recursion
# behaves like Java's -Xss256m instead of crashing at Python's default depth of 1000.
PY_RUNNER = """import runpy, sys, threading, traceback
sys.setrecursionlimit(10 ** 6)
for mb in (255, 128, 64, 32):  # the largest stack this system allows (Windows caps it just under 256 MB)
    try:
        threading.stack_size(mb * 1024 * 1024)
        break
    except ValueError:
        pass
status = [1]
def main():
    try:
        runpy.run_path("main.py", run_name="__main__")
        status[0] = 0
    except SystemExit as e:
        if e.code is None or isinstance(e.code, int):
            status[0] = e.code or 0
        else:
            print(e.code, file=sys.stderr)
    except BaseException:
        traceback.print_exc()
thread = threading.Thread(target=main)
thread.start()
thread.join()
sys.stdout.flush()
sys.exit(status[0])
"""


def prepare(lang, code, d):
    """Write the program into d and compile or syntax-check it. Returns an error message, or None."""
    (d / LANGS[lang]["file"]).write_text(code, encoding="utf-8")
    if lang == "python":
        (d / "_run.py").write_text(PY_RUNNER, encoding="utf-8")
        cmd, what = [sys.executable, "-m", "py_compile", "main.py"], "Python"
    else:
        cmd, what = [JAVAC, "-encoding", "UTF-8", "Main.java"], "javac"
    try:
        p = subprocess.run(cmd, cwd=d, capture_output=True, timeout=60)
    except subprocess.TimeoutExpired:
        return "Compilation took longer than 60 seconds."
    if p.returncode != 0:
        return decode(p.stdout + p.stderr)[-6000:] or f"{what} failed without a message."
    if lang == "java" and not (d / "Main.class").is_file():
        return "No class named Main was found. Your code must declare: public class Main"
    return None


def command(lang, d):
    if lang == "python":
        return [sys.executable, "-X", "utf8", "_run.py"]
    return [JAVA, *JAVA_FLAGS, "-cp", str(d), "Main"]


def run_program(lang, d, stdin_path, time_limit):
    out_path, err_path = d / "out.txt", d / "err.txt"
    startup = STARTUP[lang]
    with open(stdin_path, "rb") as fin, open(out_path, "wb") as fout, open(err_path, "wb") as ferr:
        start = time.perf_counter()
        p = subprocess.Popen(command(lang, d), cwd=d, stdin=fin, stdout=fout, stderr=ferr)
        try:
            code = p.wait(timeout=time_limit + startup + SLACK)
        except subprocess.TimeoutExpired:
            p.kill()
            p.wait()
            return {"status": "TLE", "time": time_limit, "out": b"", "err": "", "code": None}
        wall = time.perf_counter() - start
    err = decode(err_path.read_bytes()[-4000:])
    if out_path.stat().st_size > MAX_OUTPUT:
        return {"status": "OLE", "time": wall - startup, "out": b"", "err": err, "code": code}
    return {"status": "OK" if code == 0 else "RE", "time": max(0.0, wall - startup),
            "out": out_path.read_bytes(), "err": err, "code": code}


def calibrate():
    """Time an empty program in each language, so start-up time is not counted against solutions."""
    empty = {"java": "public class Main { public static void main(String[] a) {} }", "python": "pass\n"}
    result = {}
    for lang in LANGS:
        with tempfile.TemporaryDirectory(prefix="judge-", ignore_cleanup_errors=True) as tmp:
            d = Path(tmp)
            if prepare(lang, empty[lang], d):
                die(f"Could not run an empty {LANGS[lang]['name']} program. Check your installation.")
            (d / "empty.in").write_bytes(b"")
            times = []
            for _ in range(3):
                t = time.perf_counter()
                with open(d / "empty.in", "rb") as fin:
                    subprocess.run(command(lang, d), cwd=d, stdin=fin, capture_output=True)
                times.append(time.perf_counter() - t)
            result[lang] = statistics.median(times)
    return result


def first_difference(output, expected):
    """Where output first differs from expected, word by word, as the judge compares them."""
    def words(text):
        return [(w, n) for n, line in enumerate(text.splitlines(), 1) for w in line.split()]
    got, exp = words(output), words(expected)
    for i in range(max(len(got), len(exp))):
        g = got[i] if i < len(got) else None
        e = exp[i] if i < len(exp) else None
        if g is None or e is None or g[0] != e[0]:
            return {"expected": e[0][:60] if e else None, "expected_line": e[1] if e else None,
                    "got": g[0][:60] if g else None, "got_line": g[1] if g else None}
    return None


def judge(prob, code, job=None, lang="java"):
    """Judge code on every test, stopping at the first failure. job, if given, receives live progress."""
    total = len(prob["tests"])
    limit = prob["time_limits"][lang]
    with JUDGE_LOCK, tempfile.TemporaryDirectory(prefix="judge-", ignore_cleanup_errors=True) as tmp:
        d = Path(tmp)
        if job:
            job["state"] = "compiling"
        err = prepare(lang, code, d)
        if err:
            return {"verdict": "CE", "compile_output": err, "passed": 0, "total": total, "tests": []}
        if job:
            job["state"] = "judging"
        results = []
        for i, t in enumerate(prob["tests"]):
            r = run_program(lang, d, t["input"], limit)
            if r["status"] == "TLE":
                # A laptop can stall for a moment (virus scan, updates), so confirm a timeout once.
                r = run_program(lang, d, t["input"], limit)
            v = r["status"]
            if v == "OK":
                v = "AC" if r["out"].split() == t["answer"] else "WA"
            results.append({"v": v, "t": round(r["time"], 2)})
            if job:
                job["done"], job["tests"] = i + 1, list(results)
            if v != "AC":
                expected = t["input"].with_suffix(".ans").read_text(encoding="utf-8", errors="replace")
                output = decode(r["out"])
                return {
                    "verdict": v, "test": i + 1, "passed": i, "total": total, "tests": results,
                    "time": round(max(x["t"] for x in results), 2),
                    "failure": {
                        "sample": t["kind"] == "sample",
                        "input": preview(t["input"].read_text(encoding="utf-8", errors="replace")),
                        "expected": preview(expected),
                        "output": preview(output),
                        "diff": first_difference(output, expected) if v == "WA" else None,
                        "stderr": r["err"],
                        "exit_code": r["code"],
                    },
                }
        return {"verdict": "AC", "passed": total, "total": total, "tests": results,
                "time": round(max(x["t"] for x in results), 2)}


def run_custom(prob, code, stdin_text, lang="java"):
    with JUDGE_LOCK, tempfile.TemporaryDirectory(prefix="judge-", ignore_cleanup_errors=True) as tmp:
        d = Path(tmp)
        err = prepare(lang, code, d)
        if err:
            return {"status": "CE", "compile_output": err}
        (d / "custom.in").write_text(stdin_text, encoding="utf-8")
        r = run_program(lang, d, d / "custom.in", prob["time_limits"][lang])
    res = {"status": r["status"], "time": round(r["time"], 2), "output": decode(r["out"][:65536]),
           "stderr": r["err"], "exit_code": r["code"]}
    for s in prob["samples"]:
        if s["input"].split() == stdin_text.split():
            res["expected"] = s["output"]
            if r["status"] == "OK":
                res["matches"] = r["out"].split() == s["output"].encode().split()
                if not res["matches"]:
                    res["diff"] = first_difference(res["output"], s["output"])
            break
    return res


JOBS = {}  # submission id -> live progress, polled by the browser while judging


def process_submission(job, token, prob, code, at, lang="java"):
    try:
        result = judge(prob, code, job, lang)
    except Exception as e:  # keep the browser from waiting forever
        job.update(state="error", error=f"The judge hit an error: {e}")
        return
    with STORE.lock:
        start = STORE.data["start"]
        if start is None:
            job.update(state="error", error="The host reset the contest while your code was being judged.")
            return
        if token not in STORE.data["users"]:
            job.update(state="error", error="The host removed you from the contest.")
            return
        next_id = max((s["id"] for s in STORE.data["submissions"]), default=0) + 1
        sub = {"id": next_id, "user": token, "problem": prob["id"], "lang": lang,
               "code": code, "at": at, "minute": int((at - start) // 60),
               "in_contest": at < start + CONTEST.duration, **result}
        STORE.data["submissions"].append(sub)
        STORE.save()
    job.update(submission=public_submission(sub, CONTEST.phase(start, time.time())), state="done")


# ------------------------------------------------------------------ scoring
def scoreboard(now):
    d = STORE.data
    rows = {}
    for token, u in d["users"].items():
        rows[token] = {"token": token, "name": u["name"], "points": 0, "penalty": 0,
                       "cells": {p["id"]: {"solved": False, "wrong": 0, "minute": None} for p in CONTEST.problems}}
    for s in d["submissions"]:
        row = rows.get(s["user"])
        if not row or not s["in_contest"]:
            continue
        cell = row["cells"][s["problem"]]
        if cell["solved"] or s["verdict"] == "CE":
            continue
        if s["verdict"] == "AC":
            cell["solved"], cell["minute"] = True, s["minute"]
            row["points"] += CONTEST.by_id[s["problem"]]["points"]
            row["penalty"] += s["minute"] + CONTEST.penalty * cell["wrong"]
        else:
            cell["wrong"] += 1
    ranked = sorted(rows.values(), key=lambda r: (-r["points"], r["penalty"], r["name"].lower()))
    for i, r in enumerate(ranked):
        same = i and (r["points"], r["penalty"]) == (ranked[i - 1]["points"], ranked[i - 1]["penalty"])
        r["rank"] = ranked[i - 1]["rank"] if same else i + 1
    return ranked


def public_submission(s, phase):
    out = {k: s.get(k) for k in ("id", "problem", "lang", "verdict", "test", "passed", "total", "time", "minute",
                                 "in_contest", "at", "code", "tests", "compile_output")}
    f = s.get("failure")
    if f:
        # Hidden tests stay hidden until the contest ends.
        out["failure"] = f if f["sample"] or phase == "ended" else {"sample": False, "hidden": True}
    return out


# ------------------------------------------------------------------ HTTP
class Handler(BaseHTTPRequestHandler):
    server_version = "ContestJudge"

    def log_message(self, *args):
        pass

    def is_host(self):
        # Tunnel traffic also arrives from 127.0.0.1, but carries forwarding headers.
        if any(self.headers.get(h) for h in PROXY_HEADERS):
            return False
        return self.client_address[0] in ("127.0.0.1", "::1")

    def current_user(self):
        token = self.headers.get("X-Token", "")
        return token, STORE.data["users"].get(token)

    def reply(self, obj, status=200):
        body = json.dumps(obj).encode()
        self.send_response(status)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(body)))
        self.send_header("Cache-Control", "no-store")
        self.end_headers()
        self.wfile.write(body)

    def error(self, status, message):
        self.reply({"error": message}, status)

    def request_allowed(self):
        """Block other websites in the same browser from driving the judge."""
        host = self.headers.get("Host", "")
        if not SHARE and host.rsplit(":", 1)[0] not in ("127.0.0.1", "localhost", "[::1]"):
            return False
        origin = self.headers.get("Origin")
        return origin is None or origin in (f"http://{host}", f"https://{host}") or bool(SHARE_URL and origin == SHARE_URL)

    def do_GET(self):
        path = self.path.split("?", 1)[0]
        if path in ("/", "/index.html"):
            body = INDEX.read_bytes()
            self.send_response(200)
            self.send_header("Content-Type", "text/html; charset=utf-8")
            self.send_header("Content-Length", str(len(body)))
            self.send_header("Cache-Control", "no-store")
            self.end_headers()
            self.wfile.write(body)
        elif not self.request_allowed():
            self.error(403, "Request refused.")
        elif path == "/api/state":
            self.reply(self.state())
        elif path == "/api/job":
            token, _ = self.current_user()
            job = JOBS.get(parse_qs(urlparse(self.path).query).get("id", [""])[0])
            if not job or job["user"] != token:
                return self.error(404, "That submission was not found.")
            self.reply({k: v for k, v in job.items() if k != "user"})
        elif path == "/api/host":
            if not self.is_host():
                return self.error(403, "Only the host can open the dashboard.")
            self.reply(self.host_view())
        elif path == "/api/host/submission":
            self.host_submission(parse_qs(urlparse(self.path).query).get("id", [""])[0])
        elif path == "/api/problems":
            _, user = self.current_user()
            if not user:
                return self.error(401, "Join the contest first.")
            if CONTEST.phase(STORE.data["start"], time.time()) == "lobby":
                return self.error(403, "The contest has not started yet.")
            keys = ("id", "title", "points", "time_limit", "time_limits", "statement", "samples", "starters")
            self.reply([{**{k: p[k] for k in keys}, "test_count": len(p["tests"])} for p in CONTEST.problems])
        else:
            self.error(404, "Not found.")

    def do_POST(self):
        if not self.request_allowed() or "application/json" not in self.headers.get("Content-Type", ""):
            return self.error(403, "Request refused.")
        length = int(self.headers.get("Content-Length") or 0)
        if length > MAX_BODY:
            return self.error(413, "That is too large to send.")
        try:
            body = json.loads(self.rfile.read(length) or b"{}")
        except ValueError:
            return self.error(400, "The request was not valid JSON.")
        path = self.path.split("?", 1)[0]
        routes = {"/api/join": self.join, "/api/start": self.start, "/api/reset": self.reset,
                  "/api/run": self.run, "/api/submit": self.submit, "/api/host/remove": self.remove,
                  "/api/host/duration": self.set_duration}
        if path not in routes:
            return self.error(404, "Not found.")
        routes[path](body)

    def state(self):
        now = time.time()
        d = STORE.data
        phase = CONTEST.phase(d["start"], now)
        token, user = self.current_user()
        if user:
            seen = PRESENCE.setdefault(token, {"runs": 0})
            seen["seen"] = now
            viewing = parse_qs(urlparse(self.path).query).get("p", [""])[0]
            if viewing in CONTEST.by_id:
                seen["viewing"] = viewing
        out = {
            "title": CONTEST.title, "duration": CONTEST.duration, "start": d["start"], "now": now,
            "phase": phase, "penalty": CONTEST.penalty, "problem_count": len(CONTEST.problems),
            "total_points": sum(p["points"] for p in CONTEST.problems),
            "time_limit": sorted({p["time_limit"] for p in CONTEST.problems}),
            "python_time_limit": sorted({p["time_limits"]["python"] for p in CONTEST.problems}),
            "java": JAVA_VERSION, "python": PYTHON_VERSION, "is_host": self.is_host(), "needs_code": SHARE and not self.is_host(),
            "me": {"name": user["name"], "host": bool(user.get("host"))} if user else None,
            "participants": sorted((u["name"] for u in d["users"].values()), key=str.lower),
        }
        if self.is_host():
            out["join_code"] = d["join_code"] if SHARE else None
            out["share_url"] = SHARE_URL
            out["share_mode"] = SHARE_MODE
        if user and phase != "lobby":
            out["problems"] = [{"id": p["id"], "title": p["title"], "points": p["points"]} for p in CONTEST.problems]
            out["scoreboard"] = [{k: v for k, v in r.items() if k != "token"} for r in scoreboard(now)]
            out["submissions"] = [public_submission(s, phase) for s in reversed(d["submissions"])
                                  if s["user"] == token]
        return out

    def host_private(self, phase):
        """While the host competes, the dashboard shows only what any player can see."""
        return phase == "running" and any(u.get("host") for u in STORE.data["users"].values())

    def host_view(self):
        now = time.time()
        d = STORE.data
        phase = CONTEST.phase(d["start"], now)
        private = self.host_private(phase)
        subs = d["submissions"]
        names = {t: u["name"] for t, u in d["users"].items()}
        busy = [j for j in sorted(JOBS.values(), key=lambda j: j["created"]) if j["state"] in ("queued", "compiling", "judging")]
        judging = {}
        for j in busy:
            judging.setdefault(j["user"], j)

        people = []
        for r in scoreboard(now):
            token = r.pop("token")
            seen = PRESENCE.get(token, {})
            mine = [s for s in subs if s["user"] == token]
            last = mine[-1] if mine else None
            row = {**r, "host": bool(d["users"][token].get("host")), "submissions": len(mine),
                   "online_ago": round(now - seen["seen"]) if "seen" in seen else None,
                   "last_submission": {"problem": last["problem"], "verdict": last["verdict"], "test": last.get("test"),
                                       "ago": round(now - last["at"])} if last else None}
            if not private:
                job = judging.get(token)
                row.update(viewing=seen.get("viewing"), runs=seen.get("runs", 0),
                           last_run_ago=round(now - seen["last_run"]) if "last_run" in seen else None,
                           last_run_problem=seen.get("last_run_problem"),
                           judging={k: job[k] for k in ("problem", "state", "done", "total")} if job else None)
            people.append(row)

        problems = []
        for p in CONTEST.problems:
            ps = [s for s in subs if s["problem"] == p["id"] and s["in_contest"]]
            verdicts, solvers = {}, {}
            for s in ps:
                verdicts[s["verdict"]] = verdicts.get(s["verdict"], 0) + 1
                if s["verdict"] == "AC":
                    solvers.setdefault(s["user"], s)
            first = min(solvers.values(), key=lambda s: s["at"]) if solvers else None
            problems.append({"id": p["id"], "title": p["title"], "points": p["points"], "solved_by": len(solvers),
                             "attempts": sum(1 for s in ps if s["verdict"] != "CE"), "verdicts": verdicts,
                             "first_solve": {"name": names.get(first["user"], "?"), "minute": first["minute"]} if first else None})

        feed = [{"type": "join", "at": u["joined"], "name": u["name"]} for u in d["users"].values()]
        if d["start"]:
            feed.append({"type": "start", "at": d["start"]})
        solved = set()
        for s in subs:
            first_ac = s["verdict"] == "AC" and (s["user"], s["problem"]) not in solved
            if s["verdict"] == "AC":
                solved.add((s["user"], s["problem"]))
            feed.append({"type": "submission", "at": s["at"], "id": s["id"], "name": names.get(s["user"], "?"),
                         "problem": s["problem"], "verdict": s["verdict"], "test": s.get("test"), "minute": s["minute"],
                         "in_contest": s["in_contest"], "first_ac": first_ac})
        feed.sort(key=lambda e: e["at"], reverse=True)

        keys = ("id", "problem", "lang", "verdict", "test", "passed", "total", "time", "minute", "in_contest", "at")
        return {
            "contest": {"title": CONTEST.title, "phase": phase, "start": d["start"], "now": now,
                        "duration": CONTEST.duration, "penalty": CONTEST.penalty, "share_url": SHARE_URL,
                        "share_mode": SHARE_MODE, "join_code": d["join_code"] if SHARE else None,
                        "private": private, "host_competing": any(u.get("host") for u in d["users"].values())},
            "participants": people,
            "problems": problems,
            "feed": feed[:60],
            "queue": [] if private else [{"name": names.get(j["user"], "?"), **{k: j[k] for k in ("problem", "state", "done", "total")}}
                                         for j in busy],
            "queue_count": len(busy),
            "submissions": [{**{k: s.get(k) for k in keys}, "name": names.get(s["user"], "?")} for s in reversed(subs)],
        }

    def host_submission(self, sid):
        if not self.is_host():
            return self.error(403, "Only the host can see submissions.")
        s = next((s for s in STORE.data["submissions"] if str(s["id"]) == sid), None)
        if not s:
            return self.error(404, "That submission was not found.")
        if self.host_private(CONTEST.phase(STORE.data["start"], time.time())):
            return self.error(403, "Code is hidden while you're competing. It unlocks when the contest ends.")
        user = STORE.data["users"].get(s["user"], {})
        self.reply({**{k: v for k, v in s.items() if k != "user"}, "name": user.get("name", "?")})

    def remove(self, body):
        if not self.is_host():
            return self.error(403, "Only the host can remove participants.")
        name = str(body.get("name", "")).lower()
        with STORE.lock:
            token = next((t for t, u in STORE.data["users"].items() if u["name"].lower() == name), None)
            if not token:
                return self.error(404, "That participant was not found.")
            del STORE.data["users"][token]
            STORE.data["submissions"] = [s for s in STORE.data["submissions"] if s["user"] != token]
            PRESENCE.pop(token, None)
            STORE.save()
        self.reply({"ok": True})

    def join(self, body):
        name = " ".join(str(body.get("name", "")).split())[:24]
        if not name:
            return self.error(400, "Enter your name.")
        if SHARE and not self.is_host():
            now = time.time()
            JOIN_FAILS[:] = [t for t in JOIN_FAILS if now - t < 600]
            if len(JOIN_FAILS) >= 20:
                return self.error(429, "Too many wrong join codes. Wait 10 minutes and try again.")
            if str(body.get("code", "")).strip() != STORE.data["join_code"]:
                JOIN_FAILS.append(now)
                return self.error(403, "That join code is wrong. Ask the host for the code shown on their screen.")
        with STORE.lock:
            for token, u in STORE.data["users"].items():
                if u["name"].lower() == name.lower():
                    return self.reply({"token": token, "rejoined": True})
            token = secrets.token_urlsafe(18)
            STORE.data["users"][token] = {"name": name, "joined": time.time(), **({"host": True} if self.is_host() else {})}
            STORE.save()
        self.reply({"token": token})

    def start(self, body):
        if not self.is_host():
            return self.error(403, "Only the host can start the contest.")
        with STORE.lock:
            if STORE.data["start"] is None:
                STORE.data["start"] = time.time()
                STORE.save()
        self.reply({"ok": True})

    def set_duration(self, body):
        if not self.is_host():
            return self.error(403, "Only the host can change the contest length.")
        try:
            minutes = int(body.get("minutes"))
        except (TypeError, ValueError):
            return self.error(400, "Enter the length in minutes.")
        if not 1 <= minutes <= 1440:
            return self.error(400, "The length must be between 1 and 1440 minutes.")
        now = time.time()
        with STORE.lock:
            start = STORE.data["start"]
            phase = CONTEST.phase(start, now)
            if phase == "ended":
                return self.error(409, "The contest has ended. Reset it to change the length.")
            if phase == "running" and start + minutes * 60 <= now:
                ran = int((now - start) // 60)
                return self.error(400, f"The contest has already run for {ran} minutes. Choose more than that.")
            CONTEST.duration = minutes * 60
            STORE.data["duration"] = CONTEST.duration
            STORE.save()
        self.reply({"ok": True, "duration": CONTEST.duration})

    def reset(self, body):
        if not self.is_host():
            return self.error(403, "Only the host can reset the contest.")
        with STORE.lock:
            STORE.data["start"] = None
            STORE.data["submissions"] = []
            STORE.save()
        self.reply({"ok": True})

    def problem_for(self, body):
        token, user = self.current_user()
        if not user:
            self.error(401, "Join the contest first.")
            return None, None
        if CONTEST.phase(STORE.data["start"], time.time()) == "lobby":
            self.error(403, "The contest has not started yet.")
            return None, None
        prob = CONTEST.by_id.get(body.get("problem"))
        code = body.get("code")
        if not prob or not isinstance(code, str) or not code.strip():
            self.error(400, "Pick a problem and write some code first.")
            return None, None
        if body.get("lang", "java") not in LANGS:
            self.error(400, "Pick Java or Python.")
            return None, None
        return token, prob

    def run(self, body):
        token, prob = self.problem_for(body)
        if prob:
            seen = PRESENCE.setdefault(token, {"runs": 0})
            seen.update(runs=seen["runs"] + 1, last_run=time.time(), last_run_problem=prob["id"], seen=time.time())
            self.reply(run_custom(prob, body["code"], str(body.get("input", "")), body.get("lang", "java")))

    def submit(self, body):
        token, prob = self.problem_for(body)
        if not prob:
            return
        at = time.time()
        PRESENCE.setdefault(token, {"runs": 0})["seen"] = at
        job = {"user": token, "state": "queued", "done": 0, "total": len(prob["tests"]), "tests": [],
               "problem": prob["id"], "created": at}
        with STORE.lock:
            for old in [k for k, j in JOBS.items() if at - j["created"] > 3600]:
                del JOBS[old]
            job_id = secrets.token_hex(8)
            JOBS[job_id] = job
        lang = body.get("lang", "java")
        threading.Thread(target=process_submission, args=(job, token, prob, body["code"], at, lang), daemon=True).start()
        self.reply({"job": job_id, "total": job["total"]})


# ------------------------------------------------------------------ setup
def lan_ip():
    try:
        with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
            s.connect(("10.255.255.255", 1))  # picks the Wi-Fi interface; sends nothing
            return s.getsockname()[0]
    except OSError:
        return "YOUR-IP"


TUNNEL_URL = re.compile(r"https://(?!api\.)[-a-z0-9]+\.trycloudflare\.com")


def find_cloudflared():
    found = shutil.which("cloudflared")
    if found:
        return found
    # winget installs here; a terminal opened before the install won't have it on PATH yet.
    for base in (os.environ.get("ProgramFiles(x86)"), os.environ.get("ProgramFiles"), str(ROOT)):
        if base:
            for p in (Path(base) / "cloudflared" / "cloudflared.exe", Path(base) / "cloudflared.exe"):
                if p.is_file():
                    return str(p)
    return None


def start_tunnel(port):
    """Open a Cloudflare quick tunnel to the judge and return (process, public URL)."""
    global SHARE_URL
    exe = find_cloudflared()
    if not exe:
        die("cloudflared is not installed. Install it once with:\n"
            "    winget install --id Cloudflare.cloudflared\n"
            "then close this window and start again.")
    proc = subprocess.Popen([exe, "tunnel", "--no-autoupdate", "--url", f"http://127.0.0.1:{port}"],
                            stdout=subprocess.DEVNULL, stderr=subprocess.PIPE, text=True,
                            encoding="utf-8", errors="replace")
    ready = threading.Event()

    def read_log():
        global SHARE_URL
        for line in proc.stderr:  # keep draining, or cloudflared blocks on a full pipe
            m = TUNNEL_URL.search(line)
            if m and not ready.is_set():
                SHARE_URL = m.group(0)
                ready.set()

    threading.Thread(target=read_log, daemon=True).start()
    if not ready.wait(45):
        proc.kill()
        die("The internet link did not start. Check that your internet works and try again.")
    return proc


def new_contest(name):
    cdir = CONTESTS / name
    if cdir.exists():
        die(f"{cdir} already exists.")
    prob = cdir / "A-sum-of-two"
    for kind in ("sample", "secret"):
        (prob / "tests" / kind).mkdir(parents=True)
    (cdir / "contest.json").write_text(json.dumps({
        "title": name.replace("-", " ").title(),
        "duration_minutes": 120,
        "time_limit_seconds": 1,
        "wrong_attempt_penalty_minutes": 10,
        "problems": [{"folder": "A-sum-of-two", "points": 250}],
    }, indent=2) + "\n", encoding="utf-8")
    (prob / "statement.html").write_text(
        "<!doctype html>\n<html lang=\"en\"><head><meta charset=\"utf-8\"><title>A: Sum of Two</title></head>\n"
        "<body><main><header><h1>Sum of Two</h1></header>\n"
        "<p>Given two integers <i>a</i> and <i>b</i>, print their sum.</p>\n"
        "<h2>Input</h2>\n<p>One line with two integers <i>a</i> and <i>b</i> (|<i>a</i>|, |<i>b</i>| &le; 10<sup>9</sup>).</p>\n"
        "<h2>Output</h2>\n<p>Print <i>a</i> + <i>b</i>.</p>\n"
        "<h2>Examples</h2>\n"
        "<div class=\"sample\"><div><h3>Sample input 1</h3><pre>2 3\n</pre></div>"
        "<div><h3>Sample output 1</h3><pre>5\n</pre></div></div>\n"
        "<p class=\"explain\"><strong>Explanation.</strong> 2 + 3 = 5.</p>\n"
        "</main></body></html>\n", encoding="utf-8")
    (prob / "Main.java").write_text(
        "import java.io.*;\nimport java.util.*;\n\npublic class Main {\n\n"
        "    // Return a + b.\n"
        "    static long sum(int a, int b) {\n\n        // Write your code here\n\n        return 0;\n    }\n\n"
        "    public static void main(String[] args) throws Exception {\n\n"
        "        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));\n"
        "        StringTokenizer st = new StringTokenizer(br.readLine());\n\n"
        "        int a = Integer.parseInt(st.nextToken());\n"
        "        int b = Integer.parseInt(st.nextToken());\n\n"
        "        System.out.println(sum(a, b));\n    }\n}\n", encoding="utf-8")
    (prob / "main.py").write_text(
        "import sys\n\n\n# Return a + b.\ndef sum_of_two(a, b):\n\n    # Write your code here\n\n    return 0\n\n\n"
        "def main():\n    input = sys.stdin.readline\n\n    a, b = map(int, input().split())\n\n"
        "    print(sum_of_two(a, b))\n\n\nmain()\n", encoding="utf-8")
    files = {"sample/01-sample1": ("2 3\n", "5\n"), "secret/01-negative": ("-4 1\n", "-3\n"),
             "secret/02-overflow": ("1000000000 1000000000\n", "2000000000\n")}
    for stem, (inp, ans) in files.items():
        (prob / "tests" / f"{stem}.in").write_text(inp, encoding="utf-8")
        (prob / "tests" / f"{stem}.ans").write_text(ans, encoding="utf-8")
    print(f"Created {cdir}\nEdit contest.json, replace A-sum-of-two with your problems, then run:\n"
          f"    python judge.py {name}")


def pick_contest(name):
    if name:
        if not (CONTESTS / name / "contest.json").is_file():
            die(f"There is no contest called '{name}'. Contests live in {CONTESTS}.")
        return name
    found = sorted(p.parent.name for p in CONTESTS.glob("*/contest.json"))
    if len(found) == 1:
        return found[0]
    if not found:
        die("No contests found. Create one with: python judge.py --new my-contest")
    how = "Several contests found. Pick one:\n" + "\n".join(f"    python judge.py {c}" for c in found)
    if not sys.stdin.isatty():
        die(how)
    print("\nWhich contest?")
    for i, c in enumerate(found, 1):
        try:
            title = json.loads((CONTESTS / c / "contest.json").read_text(encoding="utf-8")).get("title", c)
        except (OSError, ValueError):
            title = c
        print(f"  {i}. {title}")
    while True:
        try:
            choice = input(f"Type a number (1-{len(found)}) and press Enter: ").strip()
        except EOFError:  # no keyboard (e.g. started from a script)
            die(how)
        if choice.isdigit() and 1 <= int(choice) <= len(found):
            return found[int(choice) - 1]
        print("That isn't one of the numbers above.")


def main():
    global JAVA, JAVAC, JAVA_VERSION, STARTUP, CONTEST, STORE, SHARE, SHARE_MODE, SHARE_URL
    ap = argparse.ArgumentParser(description="Run a Java programming contest.")
    ap.add_argument("contest", nargs="?", help="folder name inside contests/")
    ap.add_argument("--share", action="store_true", help="let others on the same Wi-Fi join")
    ap.add_argument("--online", action="store_true", help="give friends anywhere a link over the internet (uses cloudflared)")
    ap.add_argument("--port", type=int, default=8000)
    ap.add_argument("--no-browser", action="store_true", help="don't open the browser")
    ap.add_argument("--new", metavar="NAME", help="create a new contest folder and exit")
    args = ap.parse_args()

    if args.new:
        return new_contest(args.new)
    JAVA, JAVAC = shutil.which("java"), shutil.which("javac")
    if not JAVA or not JAVAC:
        die("java and javac must both be on your PATH. Install a JDK (17 or newer).")
    if os.name == "nt":
        import ctypes
        ctypes.windll.kernel32.SetErrorMode(0x0001 | 0x0002)  # no crash dialogs from failing programs
    JAVA_VERSION = decode(subprocess.run([JAVA, "-version"], capture_output=True).stderr).splitlines()[0]

    cid = pick_contest(args.contest)
    CONTEST = Contest(cid)
    STORE = Store(DATA / f"{cid}.json")
    if STORE.data.get("duration"):  # the host changed the length on the dashboard
        CONTEST.duration = STORE.data["duration"]
    print(f"\n{CONTEST.title}  ({CONTEST.duration // 60} minutes)")
    for p in CONTEST.problems:
        tl = p["time_limits"]
        print(f"  {p['id']}. {p['title']:<28} {p['points']} pts  {len(p['tests'])} tests  "
              f"{tl['java']:g}s Java, {tl['python']:g}s Python")
    print("Measuring start-up time...", end=" ", flush=True)
    STARTUP = calibrate()
    print(f"Java {STARTUP['java']:.2f}s, Python {STARTUP['python']:.2f}s (not counted against solutions)")

    SHARE = args.share or args.online
    SHARE_MODE = "online" if args.online else "wifi" if args.share else ""
    # Online mode reaches the judge through the tunnel, so it never needs to listen on the Wi-Fi.
    host = "0.0.0.0" if args.share and not args.online else "127.0.0.1"
    try:
        server = ThreadingHTTPServer((host, args.port), Handler)
    except OSError:
        die(f"Port {args.port} is busy. Try: python judge.py {cid} --port 8001")
    local = f"http://127.0.0.1:{args.port}"
    tunnel = None
    if args.online:
        threading.Thread(target=server.serve_forever, daemon=True).start()
        print("Opening an internet link...", end=" ", flush=True)
        tunnel = start_tunnel(args.port)
        print("ready")
    elif args.share:
        SHARE_URL = f"http://{lan_ip()}:{args.port}"
    print(f"\nYou open {local}")
    if SHARE:
        where = "from anywhere" if args.online else "on the same Wi-Fi"
        print(f"Your friend opens {SHARE_URL}  ({where})\nJoin code: {STORE.data['join_code']}")
        if args.online:
            print("The link can take up to a minute to start working, and it changes every time you start.")
        else:
            print("If Windows asks, allow Python on Private networks.")
    print("Keep this window open during the contest. Press Ctrl+C to stop.\n")
    if not args.no_browser:
        webbrowser.open(local)
    try:
        if tunnel:
            while tunnel.poll() is None:  # sleep, unlike wait(), lets Ctrl+C through on Windows
                time.sleep(0.5)
            print("The internet link closed. Start again to get a new link.")
        else:
            server.serve_forever()
    except KeyboardInterrupt:
        print("Stopped.")
    finally:
        if tunnel and tunnel.poll() is None:
            tunnel.terminate()


if __name__ == "__main__":
    main()
