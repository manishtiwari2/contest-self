import sys
import heapq
from collections import deque


# Students are numbered 0 to n-1 here (student 1 in the statement is student 0).
# friendships[i] = (a, b) means students a and b are friends.
# Return the students who do NOT get the notes, in increasing order.
def students_without_notes(n, friendships):

    # Write your code here

    return []


def main():
    input = sys.stdin.readline

    n, m = map(int, input().split())

    friendships = []

    for _ in range(m):
        a, b = map(int, input().split())
        friendships.append((a - 1, b - 1))

    missed = students_without_notes(n, friendships)

    print(len(missed))

    if missed:
        print(" ".join(str(student + 1) for student in missed))


main()
