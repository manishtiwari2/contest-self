import sys
import heapq
from collections import deque


# tokens are numbers and the operators + - * / (division rounds toward zero). Return the value.
def eval_rpn(tokens):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n = int(input())
    tokens = input().split()

    print(eval_rpn(tokens))


main()
