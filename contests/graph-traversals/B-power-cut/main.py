import sys
import heapq
from collections import deque

DIRS = [(0, 1), (1, 0), (0, -1), (-1, 0)]


# grid is a list of R strings, each with C characters: '#', '.', 'L' or 'G'.
# Return the minimum number of portable generators needed.
def min_generators(R, C, grid):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    R, C = map(int, input().split())
    grid = [input().strip() for _ in range(R)]

    print(min_generators(R, C, grid))


main()
