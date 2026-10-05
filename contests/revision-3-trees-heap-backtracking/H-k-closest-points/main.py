import sys
import heapq
from collections import deque


# points[i] = (x, y). Return the k points closest to (0, 0) (any order).
def k_closest(points, k):

    # Write your code here

    return []


def main():
    input = sys.stdin.readline

    n, k = map(int, input().split())

    points = []

    for _ in range(n):
        x, y = map(int, input().split())
        points.append((x, y))

    for x, y in sorted(tuple(p) for p in k_closest(points, k)):   # printed in sorted order
        print(x, y)


main()
