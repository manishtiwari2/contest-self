import sys
import heapq
from collections import deque


# Students are numbered 0 to n-1 here (student 1 in the statement is student 0).
# waits[i] = (a, b) means student a is waiting for student b.
# Return the students who can never finish, in increasing order.
def stuck_students(n, waits):

    # Write your code here

    return []


def main():
    input = sys.stdin.readline

    n, m = map(int, input().split())

    waits = []

    for _ in range(m):
        a, b = map(int, input().split())
        waits.append((a - 1, b - 1))

    stuck = stuck_students(n, waits)

    print(len(stuck))

    if stuck:
        print(" ".join(str(student + 1) for student in stuck))


main()
