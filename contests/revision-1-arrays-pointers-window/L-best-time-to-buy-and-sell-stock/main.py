import sys
import heapq
from collections import deque


# prices[i] = price on day i. Buy once, then sell on a later day. Return the biggest profit, or 0.
def max_profit(prices):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n = int(input())
    prices = list(map(int, input().split()))

    print(max_profit(prices))


main()
