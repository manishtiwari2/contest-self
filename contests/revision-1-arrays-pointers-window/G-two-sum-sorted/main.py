import sys
import heapq
from collections import deque


# nums is sorted. Return the 1-based positions (a, b) (a < b) of the two numbers that add up to target.
def two_sum_sorted(nums, target):

    # Write your code here

    return -1, -1


def main():
    input = sys.stdin.readline

    n, target = map(int, input().split())
    nums = list(map(int, input().split()))

    a, b = two_sum_sorted(nums, target)

    print(a, b)


main()
