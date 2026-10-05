import sys
import heapq
from collections import deque


# A stack that can also tell its smallest value. Every method should be O(1).
class MinStack:

    def __init__(self):
        # Add your fields here
        pass

    def push(self, x):
        # Write your code here
        pass

    def pop(self):
        # Write your code here
        pass

    def top(self):
        # Write your code here
        return 0

    def get_min(self):
        # Write your code here
        return 0


def main():
    input = sys.stdin.readline

    q = int(input())

    stack = MinStack()
    out = []

    for _ in range(q):
        parts = input().split()

        if parts[0] == "push":
            stack.push(int(parts[1]))
        elif parts[0] == "pop":
            stack.pop()
        elif parts[0] == "top":
            out.append(stack.top())
        else:
            out.append(stack.get_min())

    print("\n".join(map(str, out)))


main()
