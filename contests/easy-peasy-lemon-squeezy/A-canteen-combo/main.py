import sys
import heapq
from collections import deque


# prices[i] = the price of snack i. money = your pocket money.
# Return how many pairs of different snacks (i < j) together cost exactly money.
def count_combos(prices, money):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n, money = map(int, input().split())

    prices = list(map(int, input().split()))

    print(count_combos(prices, money))


main()
