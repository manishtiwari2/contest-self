import sys
import heapq
from collections import deque

MOD = 1_000_000_007


# Spots are numbered 0 to n-1 here (spot 1 in the statement is spot 0).
# roads[i] = (a, b, t) means a two-way road between a and b that takes t minutes.
# Return (fastest time from spot 0 to spot n-1, number of fastest routes modulo MOD),
# or (-1, 0) if the canteen can't be reached.
def fastest_routes(n, roads):

    # Write your code here

    return -1, 0


def main():
    input = sys.stdin.readline

    n, m = map(int, input().split())

    roads = []

    for _ in range(m):
        a, b, t = map(int, input().split())
        roads.append((a - 1, b - 1, t))

    time, ways = fastest_routes(n, roads)

    print(time, ways)


main()
