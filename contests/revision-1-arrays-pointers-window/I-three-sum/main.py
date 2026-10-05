import sys
import heapq
from collections import deque


# Return all different triples (a, b, c) from nums with a + b + c == 0.
def three_sum(nums):

    # Write your code here

    return []


def main():
    input = sys.stdin.readline

    n = int(input())
    nums = list(map(int, input().split()))

    # Printed in a fixed order: each triple sorted, then the triples sorted.
    rows = sorted(tuple(sorted(t)) for t in three_sum(nums))

    print(len(rows))
    for row in rows:
        print(*row)


main()
