import sys
import heapq
from collections import deque


# Stops are numbered 0 to n-1 here (stop 1 in the statement is stop 0).
# routes[i] = (a, b, c) means a one-way auto ride from stop a to stop b that costs c rupees.
# You have one coupon that halves the fare of a single ride (rounded down).
# Return the cheapest cost from stop 0 to stop n-1, or -1 if you can't get there.
def cheapest_trip(n, routes):

    # Write your code here

    return -1


def main():
    input = sys.stdin.readline

    n, m = map(int, input().split())

    routes = []

    for _ in range(m):
        a, b, c = map(int, input().split())
        routes.append((a - 1, b - 1, c))

    print(cheapest_trip(n, routes))


main()
