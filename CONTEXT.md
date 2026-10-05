Here's the full context in one block, ready to copy:

````markdown
# Project context: Java DSA contest judge (for me and a friend)

## Goal
A self-hosted, Codeforces-style programming contest for two friends (Java only):
read problems in the browser, write code, Run on custom input, Submit against hidden
tests, and get verdicts. It started as "can you make a DSA contest?" and grew to include
online play and a host dashboard.

## Where things are
- Project: C:\Desktop\dsa-contest
- GitHub (public): https://github.com/manishtiwari2/contest-self, branch main, commit af66f2a "added all codes"
- Not committed yet: a README section "Set up on a new laptop (Windows)"
- Reference solutions, kept OUTSIDE the repo so they aren't spoiled: C:\Desktop\dsa-contest-solutions\A-Main.java to D-Main.java
- Original problem pack: C:\Users\manis\Downloads\graph-traversal-problems\graph-traversal-problems

## Folder layout
- judge.py: the whole server (Python standard library only, no pip installs)
- static/index.html: the whole UI (one file; CodeMirror 5 and IBM Plex fonts load from a CDN)
- start.bat / start-shared.bat / start-online.bat: double-click launchers
- README.md
- contests/graph-traversals/contest.json plus one folder per problem:
  - statement.html
  - Main.java: starter code
  - tests/sample/*.in + *.ans, tests/secret/*.in + *.ans
- data/<contest>.json: participants, sign-in tokens, join code, start time, all submissions with code.
  It is currently tracked in git; I chose to leave that for now.

## The contest (graph-traversals)
- 120 minutes, 1 s time limit per test, 250 points each, no penalty for wrong submissions
  (wrong_attempt_penalty_minutes = 0). Ranking: points, then total solve minutes.
- A. The Notes Chain (BFS from student 1, undirected edges): 24 tests
- B. Power Cut (count open regions that have a lab but no generator): 35 tests
- C. Hackathon Leaderboard (Kahn's topological sort; CONTRADICTION beats MULTIPLE; UNIQUE + order): 41 tests
- D. Networking Night (min-heap greedy over the frontier, like Dijkstra; answer needs long): 37 tests
- The reference solutions got Accepted on every test (about 0.1 s per test).

## Starter code (in my CSES style)
- main() reads input with BufferedReader + StringTokenizer, uses a new tokenizer per line, throws Exception,
  converts to 0-indexed with -1, prints with +1, and uses StringBuilder for output.
- The player fills in ONE function:
  - A: List<Integer> studentsWithoutNotes(int n, int[][] friendships)
  - B: int minGenerators(char[][] grid)   (static R, C, dirs are provided)
  - C: List<Integer> leaderboard(int n, int[][] notes)   (null = CONTRADICTION, empty list = MULTIPLE, else the ranking)
  - D: long maxConfidence(int[][] aura, int sr, int sc, int c0)
- Raw edges are passed on purpose, not a built graph, so the "edges go both ways" trap in A still counts.

## How the judge works (judge.py)
- Compiles once with javac, then runs each test with: java -Xss256m -Xmx512m -XX:+UseSerialGC -cp <tmp> Main
- Measures JVM start-up time at launch and doesn't count it. Allows 0.25 s of jitter.
  A timeout is re-run once before giving TLE, because laptop hiccups caused false slowdowns.
- Stops at the first failed test. Compares output word by word. Output limit 32 MB.
- Verdicts: AC, WA, TLE, RE, CE, OLE.
- Hidden tests show only their number until the contest ends; test NAMES are never shown.
  For a WA on a visible test, it shows the first difference (expected token and line vs. output token and line).
- Submitting is asynchronous: POST /api/submit returns a job id, and the browser polls /api/job for live
  progress. One program runs at a time, for fair timing.
- Starting modes:
  - python judge.py                 local only (127.0.0.1:8000)
  - python judge.py --share         same Wi-Fi (listens on 0.0.0.0, needs the join code)
  - python judge.py --online        internet link through a Cloudflare quick tunnel
                                    (needs cloudflared; link like https://xxx.trycloudflare.com;
                                     it changes every start and can take up to a minute to work)
  - python judge.py --new NAME      creates a new contest folder with an example problem
  - python judge.py <contest>       picks a contest when there are several
- The host is whoever opens 127.0.0.1 directly. Tunnel traffic is detected by Cf-Connecting-Ip /
  X-Forwarded-For headers and treated as a normal player.
  6-digit join code; after 20 wrong codes in 10 minutes, joining is blocked; Origin check against other sites.

## API
- GET  /api/state[?p=<problem>]   phase, clock, me, standings, my submissions (?p reports which problem is open)
- GET  /api/problems              statements, samples, starter code (only after the start)
- POST /api/join /api/start /api/reset /api/run /api/submit
- GET  /api/job?id=               live judging progress
- GET  /api/host                  host dashboard data (host only)
- GET  /api/host/submission?id=   full code and failure details (host only; locked while a competing host plays)
- POST /api/host/remove           remove a participant (host only)

## Player screen
- Lobby, then 2-hour countdown. Problem list with solved marks; statement; CodeMirror Java editor;
  code auto-saved per problem in the browser.
- Run = my one custom input (a sample is pre-filled). Submit = every test, hidden ones included.
- Draggable dividers between statement/editor and editor/output (remembered; double-click resets).
- Light/dark switch (remembered; the editor and pop-ups follow it).
- Live progress ("Running test 17 of 41", boxes turn green one by one).
- Short warning before submitting unchanged starter code or code identical to an earlier submission.
- Congratulations pop-up with my name, an encouraging line, and confetti on each first AC.
  It has a "Next problem" button.
- Notices: "<friend> solved B at 34 min"; "15 minutes left" / "5 minutes left"; a "Time's up" screen with my rank.
- Standings tab and My submissions tab (click one to see its verdict and load its code).

## Host dashboard (what the host sees instead of the player screen)
- Tiles: participants and how many are online, submissions and accepted, problems solved,
  judge Busy/Idle with details.
- Participants table:
  - online/idle/offline dot
  - live status such as "Viewing C, ran code on C 8s ago" or "Judging C: test 5 of 41"
  - per-problem cells: ✓ minute (+tries), −n, pulsing while judging, outline on the open problem
- Live activity list (joins, start, verdicts, solves); problem statistics (solved by, attempts, verdict bar,
  first solve); all submissions (click to see code and hidden-test details).
- Waiting room: invite box with a "Copy invite message" button, Remove button for mistyped names,
  Start the contest button.
- "Compete too" joins the host as a player and adds a "Dashboard | My contest" switch in the top bar.
  Fairness rule: while the contest runs, a competing host sees only standings and verdicts.
  Code and live activity unlock when it ends.

## How we run a contest (friend is in another hostel, so there's no shared Wi-Fi)
1. Plug in the laptop and set sleep to Never when plugged in.
2. Double-click start-online.bat and keep the black window open.
3. The dashboard opens. Click "Copy invite message" and send it on WhatsApp.
4. Wait for the friend's name to appear, then click Start the contest.
- If the link doesn't load: wait a minute and refresh, or restart for a new link and send the new invite.

## Setting it up on a friend's laptop
- Already has Python (3.10+) and Java? Check with: python --version and javac -version.
  javac must work (the full JDK, not just the Java runtime).
  If needed: winget install --id EclipseAdoptium.Temurin.21.JDK
- Needs Git to clone (winget install --id Git.Git), or can use GitHub's Code > Download ZIP instead.
- Only to host contests: winget install --id Cloudflare.cloudflared
- Clone: git clone https://github.com/manishtiwari2/contest-self.git
- Delete the data folder in the clone for an empty lobby (it has my last contest's players).
  It gets recreated automatically.
- Double-click start.bat (practice) or start-online.bat (host). Get updates with git pull.
- To just join a contest I host, she needs only a browser.

## Known limitations (accepted for now)
- Submitted code runs unsandboxed on the host's laptop (fine between trusted friends).
- The repo is public, and the hidden tests and data/ are in it. I chose to leave security for now.
- A time warning is missed if the page is reloaded just after the 15- or 5-minute mark.
- The quick-tunnel link changes every start, and the host laptop must stay online.

## My preferences
- Java only. Keep the setup simple (no Docker, no cloud server).
- Starter code should match my own CSES coding style.
- I sometimes want a plan before anything gets built. I watch token spend.
````