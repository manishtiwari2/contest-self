import sys
import heapq
from collections import deque


# Return (i, j) with i < j and nums[i] + nums[j] == target. There is exactly one answer.
def two_sum(nums, target):

    # Write your code here

    return -1, -1


def main():
    input = sys.stdin.readline

    n, target = map(int, input().split())
    nums = list(map(int, input().split()))

    i, j = two_sum(nums, target)

    print(i, j)


main()
