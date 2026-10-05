import sys
import heapq
from collections import deque


# Ignore everything that is not a letter or digit, and ignore case. Does s read the same both ways?
def is_palindrome(s):

    # Write your code here

    return False


def main():
    input = sys.stdin.readline

    s = input().rstrip("\n")

    print("true" if is_palindrome(s) else "false")


main()
