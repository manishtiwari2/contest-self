import sys
import heapq
from collections import deque


# Buildings are numbered 0 to n-1 here (building 1 in the statement is building 0).
# roads[i] = (a, b, t) means a two-way road between a and b that takes t minutes.
# questions[i] = (a, b, x): the fastest time from a to b when only buildings 0 to x-1 are open to walk
# through (a and b themselves are always fine). x = 0 means no building is open.
# Return the answers in the same order, with -1 where it's impossible.
def answer_questions(n, roads, questions):

    # Write your code here

    return [0] * len(questions)


def main():
    input = sys.stdin.readline

    n, m, q = map(int, input().split())

    roads = []

    for _ in range(m):
        a, b, t = map(int, input().split())
        roads.append((a - 1, b - 1, t))

    questions = []

    for _ in range(q):
        a, b, x = map(int, input().split())
        questions.append((a - 1, b - 1, x))

    print("\n".join(map(str, answer_questions(n, roads, questions))))


main()
