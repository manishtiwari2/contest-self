import sys
import heapq
from collections import deque


# Chapter i takes hours[i] hours to study and is worth marks[i] marks.
# Each chapter can be studied at most once.
# Return the most marks you can get in at most H hours.
def max_marks(H, hours, marks):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n, H = map(int, input().split())

    hours = []
    marks = []

    for _ in range(n):
        h, m = map(int, input().split())
        hours.append(h)
        marks.append(m)

    print(max_marks(H, hours, marks))


main()
