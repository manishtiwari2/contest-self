import sys
import heapq
from collections import deque


# Return every order of the numbers (they are all different).
def permute(nums):

    # Write your code here

    return []


def main():
    input = sys.stdin.readline

    n = int(input())
    nums = list(map(int, input().split()))

    # Printed in a fixed order, one per line, like [1, 3].
    rows = sorted(list(row) for row in permute(nums))

    print(len(rows))
    for row in rows:
        print("[" + ", ".join(map(str, row)) + "]")


main()
