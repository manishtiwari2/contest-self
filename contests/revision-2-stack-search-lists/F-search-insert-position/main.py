import sys
import heapq
from collections import deque


# nums is sorted and has no repeats. Return the first index whose value is >= target.
def search_insert(nums, target):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n, q = map(int, input().split())
    nums = list(map(int, input().split()))
    targets = list(map(int, input().split()))

    print("\n".join(str(search_insert(nums, target)) for target in targets))


main()
