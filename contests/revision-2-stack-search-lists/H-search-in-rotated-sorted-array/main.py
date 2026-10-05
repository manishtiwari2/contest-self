import sys
import heapq
from collections import deque


# nums was sorted (no repeats) and then rotated. Return the index of target, or -1. Aim for O(log n).
def search_rotated(nums, target):

    # Write your code here

    return -1


def main():
    input = sys.stdin.readline

    n, q = map(int, input().split())
    nums = list(map(int, input().split()))
    targets = list(map(int, input().split()))

    print("\n".join(str(search_rotated(nums, target)) for target in targets))


main()
