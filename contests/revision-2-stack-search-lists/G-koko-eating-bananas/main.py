import sys
import heapq
from collections import deque


# Koko eats up to k bananas per hour from one pile. Return the smallest k that finishes all piles in h hours.
def min_eating_speed(piles, h):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n, h = map(int, input().split())
    piles = list(map(int, input().split()))

    print(min_eating_speed(piles, h))


main()
