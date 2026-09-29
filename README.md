# Contest Judge

A local programming contest for Java: read problems, write code in the browser, submit, and get a verdict
(Accepted, Wrong answer, Time limit exceeded, Runtime error, Compilation error) against hidden tests.

Needs **Python 3.10+** and a **JDK 17+** (`java` and `javac` on your PATH). Nothing else to install.

## Set up on a new laptop (Windows)

1. Open **PowerShell** and install what's missing. Skip anything you already have:
   ```
   winget install --id Git.Git
   winget install --id Python.Python.3.12
   winget install --id EclipseAdoptium.Temurin.21.JDK
   ```
   To host contests for friends in other places, also install:
   ```
   winget install --id Cloudflare.cloudflared
   ```
2. **Close PowerShell and open a new window**, so it finds the new programs. Check that Java works:
   ```
   javac -version
   ```
3. Download the contest to your Desktop:
   ```
   cd $HOME\Desktop
   git clone https://github.com/manishtiwari2/contest-self.git
   cd contest-self
   ```
4. For an empty lobby, delete the `data` folder. It holds the results of the last contest run on the laptop the
   code was pushed from:
   ```
   Remove-Item -Recurse -Force data
   ```
5. Start it: double-click `start.bat` to practise alone, or `start-online.bat` to host a contest for friends
   anywhere.

To get the latest version later, run `git pull` inside the `contest-self` folder.

## Start a contest

| To | Run |
|---|---|
| Play alone | `python judge.py` (or double-click `start.bat`) |
| Play with friends on the same Wi-Fi | `python judge.py --share` (or double-click `start-shared.bat`) |
| Play with friends anywhere, over the internet | `python judge.py --online` (or double-click `start-online.bat`) |
| Pick a contest when there are several | `python judge.py graph-traversals` |

The browser opens at `http://127.0.0.1:8000`. Join with your name. The host (whoever runs `judge.py`) presses
**Start the contest**, and the clock starts for everyone at once.

With `--share`, the terminal and the lobby show an address like `http://192.168.1.20:8000` and a 6-digit join
code. Friends open that address on the same Wi-Fi and join with the code. Their code compiles and runs on the
host's computer, so they only need a browser. If Windows asks, allow Python on **Private** networks.

With `--online`, the judge opens a Cloudflare quick tunnel and shows a link like
`https://some-words.trycloudflare.com` plus the join code. Use **Copy invite message** in the lobby to send both.
It needs `cloudflared` installed once (`winget install --id Cloudflare.cloudflared`). The link changes every
time you start, can take up to a minute to begin working, and lasts while the window stays open. Keep your
laptop plugged in and set it not to sleep during the contest.

## Host dashboard

Whoever runs `judge.py` opens `http://127.0.0.1:8000` and gets the host dashboard instead of the player
screen. It shows:
- Everyone who joined and whether they're online.
- Which problem each person has open, when they last ran code, and submissions being judged test by test.
- Every verdict in a live activity list, and statistics for each problem.
- Every submission's code, with full details of the failing test.

In the waiting room you can remove a participant (for example a mistyped name) and start the contest.

To play as well, click **Compete too** and enter your name. A **Dashboard | My contest** switch then appears in
the top bar. To keep it fair, while the contest runs a competing host sees only what players see (standings and
verdicts). Code and live activity unlock when it ends.

**Reset contest** (host only) deletes all submissions and returns to the lobby.

## Sending the folder to a friend

A friend can also run their own copy: send them the whole folder and they run `python judge.py`. Delete the
`data` folder first, because it holds your submissions. Each copy has its own standings, so compare scores
at the end.

## Rules the judge applies

- Class must be `public class Main`. Read standard input, print standard output.
- Output is compared word by word, so extra spaces and blank lines don't matter.
- Judging stops at the first failed test. A failed hidden test shows only its number until the contest ends;
  test names are never shown.
- Java's start-up time is measured when the judge starts and is not counted against the time limit.
- Programs get 512 MB of memory and a 256 MB stack, so deep recursion works.
- Ranking: points first, then time (the minute of each accepted solution, plus
  `wrong_attempt_penalty_minutes` for each rejected try before it). Set that to `0`, as Graph Traversals does,
  so wrong submissions cost nothing. Submissions after the clock ends are practice.

## Making a new contest

```
python judge.py --new my-contest
```

This creates `contests/my-contest/` with a working example problem. A contest folder looks like this:

```
contests/my-contest/
  contest.json
  A-first-problem/
    statement.html
    Main.java                     (optional starter code shown in the editor)
    tests/sample/01-sample1.in    tests/sample/01-sample1.ans
    tests/secret/01-edge-case.in  tests/secret/01-edge-case.ans
  B-second-problem/
    ...
```

`contest.json`:

```json
{
  "title": "My Contest",
  "duration_minutes": 120,
  "time_limit_seconds": 1,
  "wrong_attempt_penalty_minutes": 10,
  "problems": [
    {"folder": "A-first-problem", "points": 250},
    {"folder": "B-second-problem", "points": 250, "time_limit_seconds": 2}
  ]
}
```

- Problems are lettered A, B, C... in the order listed.
- The problem title is the statement's `<h1>`. The statement shown is everything after `</header>` inside
  `<main>` (or the whole `<body>` if there is no header). Samples use `<div class="sample">` blocks, as in the
  existing statements.
- `Main.java` is the starter code players see. The convention: `main` reads the input and prints the output
  (BufferedReader + StringTokenizer, converting to 0-indexed), and players fill in one function. Pass raw
  input (like an `int[][]` of edges) rather than a built graph when building it is part of the problem.
  Without a `Main.java`, players get a plain template.
- Tests run in order: every `tests/sample/*.in`, then every `tests/secret/*.in`, sorted by file name. Each
  `.in` needs a `.ans` with the same name.
- The judge checks the folder when it starts and names any missing file.

Each contest keeps its own results in `data/<contest-folder>.json`.
