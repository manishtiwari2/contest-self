import sys
import heapq
from collections import deque


# Courses are numbered 0 to n-1 here (course 1 in the statement is course 0).
# rules[i] = (a, b) means: to take course a, you must first pass course b. Rules are added in this order.
# Return the number of the first rule (1-based, as in the statement) after which some course needs
# itself, or -1 if that never happens.
def first_bad_rule(n, rules):

    # Write your code here

    return -1


def main():
    input = sys.stdin.readline

    n, m = map(int, input().split())

    rules = []

    for _ in range(m):
        a, b = map(int, input().split())
        rules.append((a - 1, b - 1))

    print(first_bad_rule(n, rules))


main()
