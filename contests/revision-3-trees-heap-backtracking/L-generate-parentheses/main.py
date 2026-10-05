import sys
import heapq
from collections import deque


# Return every valid string with n pairs of brackets.
def generate_parenthesis(n):

    # Write your code here

    return []


def main():
    input = sys.stdin.readline

    n = int(input())

    result = sorted(generate_parenthesis(n))      # printed in sorted order

    print(len(result))
    print("\n".join(result))


main()
