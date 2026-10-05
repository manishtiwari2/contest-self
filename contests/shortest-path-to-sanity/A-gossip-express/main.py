import sys
import heapq
from collections import deque


# Students are numbered 0 to n-1 here (student 1 in the statement is student 0).
# sources = the students who already know the gossip at minute 0.
# friends[i] = (a, b) means a and b are friends (it works both ways).
# Return a list where answer[v] = the minute student v first hears the gossip, or -1 if never.
def gossip_time(n, sources, friends):

    # Write your code here

    return [0] * n


def main():
    input = sys.stdin.readline

    n, m, k = map(int, input().split())

    sources = [int(x) - 1 for x in input().split()]

    friends = []

    for _ in range(m):
        a, b = map(int, input().split())
        friends.append((a - 1, b - 1))

    print(" ".join(map(str, gossip_time(n, sources, friends))))


main()
