import sys
import heapq
from collections import deque


# height[i] is a wall. Water between walls i and j = min(height[i], height[j]) * (j - i). Return the most water.
def max_area(height):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n = int(input())
    height = list(map(int, input().split()))

    print(max_area(height))


main()
