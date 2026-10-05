import sys
import heapq
from collections import deque

DIRS = [(0, 1), (1, 0), (0, -1), (-1, 0)]


# grid is a list of R strings, each with C characters:
# '#' wall, '.' open, 'S' your room, 'M' the Maggi stall.
# Return the fewest steps from S to M, or -1 if you can't get there.
def min_steps(R, C, grid):

    # Write your code here

    return -1


def main():
    input = sys.stdin.readline

    R, C = map(int, input().split())
    grid = [input().strip() for _ in range(R)]

    print(min_steps(R, C, grid))


main()
