import sys
import heapq
from collections import deque


# Return True if some value appears at least twice in nums.
def contains_duplicate(nums):

    # Write your code here

    return False


def main():
    input = sys.stdin.readline

    n = int(input())
    nums = list(map(int, input().split()))

    print("true" if contains_duplicate(nums) else "false")


main()
