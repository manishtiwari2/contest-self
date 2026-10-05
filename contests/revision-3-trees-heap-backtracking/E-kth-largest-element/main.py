import sys
import heapq
from collections import deque


# Return the k-th largest value (k = 1 is the largest).
def find_kth_largest(nums, k):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n, k = map(int, input().split())
    nums = list(map(int, input().split()))

    print(find_kth_largest(nums, k))


main()
