import sys
import heapq
from collections import deque


# nums is sorted. Keep one copy of each value at the front of nums (in place) and return how many you kept.
def remove_duplicates(nums):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n = int(input())
    nums = list(map(int, input().split()))

    k = remove_duplicates(nums)

    print(k)
    print(" ".join(map(str, nums[:k])))


main()
