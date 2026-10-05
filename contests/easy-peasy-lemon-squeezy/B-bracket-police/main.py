import sys
import heapq
from collections import deque


# s contains only the characters ( ) [ ] { }
# Return True if every bracket is closed by its matching bracket, in the right order.
def is_balanced(s):

    # Write your code here

    return False


def main():
    input = sys.stdin.readline

    t = int(input())

    out = []

    for _ in range(t):
        s = input().strip()
        out.append("YES" if is_balanced(s) else "NO")

    print("\n".join(out))


main()
