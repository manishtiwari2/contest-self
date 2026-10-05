import sys
import heapq
from collections import deque


# Return all combinations of candidates that add up to target. A number can be used many times.
def combination_sum(candidates, target):

    # Write your code here

    return []


def main():
    input = sys.stdin.readline

    n, target = map(int, input().split())
    candidates = list(map(int, input().split()))

    # Printed in a fixed order, one per line, like [1, 3].
    rows = sorted(sorted(row) for row in combination_sum(candidates, target))

    print(len(rows))
    for row in rows:
        print("[" + ", ".join(map(str, row)) + "]")


main()
