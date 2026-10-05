import sys
import heapq
from collections import deque


# Return the k values that appear most often (any order).
def top_k_frequent(nums, k):

    # Write your code here

    return [0] * k


def main():
    input = sys.stdin.readline

    n, k = map(int, input().split())
    nums = list(map(int, input().split()))

    answer = sorted(top_k_frequent(nums, k))      # printed from small to big

    print(" ".join(map(str, answer)))


main()
