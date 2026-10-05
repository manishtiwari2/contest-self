import sys
import heapq
from collections import deque


# nums is sorted and has no repeats. Return the index of target, or -1.
def search(nums, target):

    # Write your code here

    return -1


def main():
    input = sys.stdin.readline

    n, q = map(int, input().split())
    nums = list(map(int, input().split()))
    targets = list(map(int, input().split()))

    print("\n".join(str(search(nums, target)) for target in targets))


main()
