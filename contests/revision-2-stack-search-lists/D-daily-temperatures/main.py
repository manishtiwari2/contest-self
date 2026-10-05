import sys
import heapq
from collections import deque


# answer[i] = how many days after day i until a warmer day, or 0 if there is none.
def daily_temperatures(temps):

    # Write your code here

    return [0] * len(temps)


def main():
    input = sys.stdin.readline

    n = int(input())
    temps = list(map(int, input().split()))

    print(" ".join(map(str, daily_temperatures(temps))))


main()
