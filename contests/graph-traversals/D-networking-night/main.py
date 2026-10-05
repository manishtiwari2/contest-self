import sys
import heapq
from collections import deque

DIRS = [(0, 1), (1, 0), (0, -1), (-1, 0)]


# aura has R rows and C columns: aura[i][j] is the senior in row i, column j.
# Aarav starts in cell (sr, sc), 0-indexed here, whose aura is 0.
# c0 is his starting confidence.
# Return his maximum possible confidence at the end of the party.
def max_confidence(R, C, aura, sr, sc, c0):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    R, C, c0 = map(int, input().split())
    sr, sc = map(int, input().split())
    sr -= 1
    sc -= 1

    aura = [list(map(int, input().split())) for _ in range(R)]

    print(max_confidence(R, C, aura, sr, sc, c0))


main()
