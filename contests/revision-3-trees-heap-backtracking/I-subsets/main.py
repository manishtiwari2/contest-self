import sys
import heapq
from collections import deque


# Return all subsets of nums (the numbers are all different).
def subsets(nums):

    # Write your code here

    return []


def main():
    input = sys.stdin.readline

    n = int(input())
    nums = list(map(int, input().split()))

    # Printed in a fixed order, one per line, like [1, 3].
    rows = sorted(sorted(row) for row in subsets(nums))

    print(len(rows))
    for row in rows:
        print("[" + ", ".join(map(str, row)) + "]")


main()
