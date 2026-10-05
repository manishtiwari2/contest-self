import sys
import heapq
from collections import deque


# Teams are numbered 0 to n-1 here (team 1 in the statement is team 0).
# notes[i] = (a, b) means team a finished above team b.
# Return:
#   None           if the notes contradict each other   (CONTRADICTION)
#   an empty list  if more than one ranking fits         (MULTIPLE)
#   the ranking    from first place to last, if exactly one fits (UNIQUE)
def leaderboard(n, notes):

    # Write your code here

    return []


def main():
    input = sys.stdin.readline

    n, m = map(int, input().split())

    notes = []

    for _ in range(m):
        a, b = map(int, input().split())
        notes.append((a - 1, b - 1))

    ranking = leaderboard(n, notes)

    if ranking is None:
        print("CONTRADICTION")
    elif not ranking:
        print("MULTIPLE")
    else:
        print("UNIQUE")
        print(" ".join(str(team + 1) for team in ranking))


main()
