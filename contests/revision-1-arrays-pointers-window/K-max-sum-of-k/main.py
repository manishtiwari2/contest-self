import sys
import heapq
from collections import deque


# Return the biggest sum of k numbers that are next to each other.
def max_sum_of_k(nums, k):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n, k = map(int, input().split())
    nums = list(map(int, input().split()))

    print(max_sum_of_k(nums, k))


main()
