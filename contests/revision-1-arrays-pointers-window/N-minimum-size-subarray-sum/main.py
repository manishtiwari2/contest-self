import sys
import heapq
from collections import deque


# All numbers are positive. Return the length of the shortest window with sum >= target, or 0.
def min_sub_array_len(target, nums):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n, target = map(int, input().split())
    nums = list(map(int, input().split()))

    print(min_sub_array_len(target, nums))


main()
