import sys
import heapq
from collections import deque


# Stalls are numbered 0 to n-1 here (stall 1 in the statement is stall 0).
# walkways[i] = (a, b, x) means a one-way walkway from stall a to stall b that changes your coins by x
# (x > 0: you win coins, x < 0: you pay).
# Return the most coins you can have on reaching stall n-1 from stall 0,
# or the string "JACKPOT" if you can make that number as large as you like.
def max_coins(n, walkways):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n, m = map(int, input().split())

    walkways = []

    for _ in range(m):
        a, b, x = map(int, input().split())
        walkways.append((a - 1, b - 1, x))

    print(max_coins(n, walkways))


main()
