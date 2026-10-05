import sys
import heapq
from collections import deque


# Smash the two heaviest stones again and again. Return the weight of the last stone, or 0.
def last_stone_weight(stones):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n = int(input())
    stones = list(map(int, input().split()))

    print(last_stone_weight(stones))


main()
