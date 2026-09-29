# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A local programming contest judge for Java solutions: a single Python process serves both the API and the
frontend, compiles/runs submitted `Main.java` against hidden tests, and scores a leaderboard. No build step,
no dependencies beyond the standard library — everything lives in `judge.py` (backend) and
`static/index.html` (frontend, inline JS/CSS in one file).

Requires **Python 3.10+** and a JDK 17+ (`java`/`javac` on PATH). There is no test suite, linter, or package
manifest in this repo.

## Running it

```
python judge.py                      # run the only contest in contests/
python judge.py graph-traversals     # run a specific contest by folder name
python judge.py --share              # let others on the same Wi-Fi join (needs a join code)
python judge.py --online             # tunnel via cloudflared for internet play
python judge.py --new my-contest     # scaffold a new contest folder with a working example problem
```

There's no automated test suite — verify changes by actually running a contest (`python judge.py
graph-traversals --no-browser`) and hitting the endpoints, or submitting a known-good/known-bad `Main.java`
to confirm verdicts (AC/WA/TLE/RE/CE) come back as expected.

## Architecture

**Single-process, in-memory-plus-JSON model.** `judge.py` runs a `ThreadingHTTPServer` (`Handler`) that both
serves `static/index.html` at `/` and answers a small JSON API under `/api/*`. There is no database — all
contest state (`Store`) lives in memory and is persisted to `data/<contest-folder>.json` on every mutation
via atomic write (`tmp` + `os.replace`).

**Two read models, one write model.** The frontend polls rather than pushes:
- `/api/state` (polled every 4s, `setInterval(refresh, 4000)`) — the player's own view: scoreboard, their own
  submissions, contest phase/clock.
- `/api/host` (polled every 2s by the dashboard) — the host's view: every participant's presence, live
  judging queue, and the activity feed. `Handler.host_private()` hides code/live-activity from the host
  while they are competing (`--host_competing`), to keep it fair; it reopens when the contest ends.
- `/api/job?id=` — polled while a submission is judging, to show live per-test progress in the UI.

Writes go through a small set of POST routes (`join`, `start`, `reset`, `run`, `submit`, `host/remove`),
each gated by `is_host()` (loopback address, no proxy headers) or by a valid session token in `X-Token`.

**Judging pipeline** (`judge()` in judge.py): compile once per submission into a temp dir, then run tests in
order (`tests/sample/*` then `tests/secret/*`, sorted by filename), stopping at the first non-AC verdict.
Each submission runs on a background thread (`process_submission`) but actual Java execution is serialized
through `JUDGE_LOCK` so timing stays fair across concurrent submitters. JVM start-up time is measured once at
launch (`calibrate()`) and subtracted from every submission's wall-clock time before comparing to the
problem's time limit.

**Contest content is data, not code.** A contest is a folder under `contests/<name>/` with `contest.json`
(title, duration, scoring) plus one subfolder per problem (`A-title/`, `B-title/`, ...). Each problem folder
holds `statement.html` (parsed for `<h1>` as title and everything inside `<main>` after `</header>` as the
shown statement — see `load_problem()`), an optional `Main.java` starter template, and `tests/sample/` +
`tests/secret/` `.in`/`.ans` pairs. Adding or editing a contest never requires touching `judge.py` — the
judge reads and validates this structure at startup and dies with a specific error if a file is missing.

**Sharing modes** (`--share` / `--online`) change only how the server binds and whether a join code is
enforced (`SHARE`, `SHARE_MODE`, `SHARE_URL` globals) — the judging and scoring logic is identical to solo
play. `--online` opens a `cloudflared` quick tunnel (`start_tunnel()`) and never binds beyond loopback,
since all traffic arrives through the tunnel.

## Conventions when editing

- Keep `judge.py` a single file — there's no package structure to slot new modules into, and the project's
  own design goal is "nothing else to install."
- Any new contest content goes under `contests/<name>/`, following the `contest.json` schema and folder
  layout documented in README.md ("Making a new contest"). Don't hardcode contest-specific logic into
  `judge.py`.
- `data/*.json` is generated output (submissions/standings) — treat it as disposable, not something to hand-edit or commit meaning into.
