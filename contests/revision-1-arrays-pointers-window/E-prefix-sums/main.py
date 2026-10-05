import sys
import heapq
from collections import deque



# pre[i] = nums[0] + nums[1] + ... + nums[i-1], and pre[0] = 0
def build_prefix(nums):

    # Write your code here

    return [0] * (len(nums) + 1)


# Sum of nums[l..r] (both ends included) in O(1)
def range_sum(pre, l, r):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n, q = map(int, input().split())
    nums = list(map(int, input().split()))

    pre = build_prefix(nums)
    out = []

    for _ in range(q):
        l, r = map(int, input().split())
        out.append(range_sum(pre, l, r))

    print("\n".join(map(str, out)))


main()
