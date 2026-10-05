import sys
import heapq
from collections import deque


# Return True if t uses exactly the same letters as s, the same number of times.
def is_anagram(s, t):

    # Write your code here

    return False


def main():
    input = sys.stdin.readline

    s = input().strip()
    t = input().strip()

    print("true" if is_anagram(s, t) else "false")


main()
