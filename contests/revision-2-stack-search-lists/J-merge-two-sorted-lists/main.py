import sys
import heapq
from collections import deque


class ListNode:
    def __init__(self, val, next=None):
        self.val = val
        self.next = next


# Builds a linked list from a list of values.
def build_list(values):
    dummy = ListNode(0)
    tail = dummy
    for v in values:
        tail.next = ListNode(v)
        tail = tail.next
    return dummy.next


def print_list(head):
    values = []
    while head:
        values.append(head.val)
        head = head.next
    print(" ".join(map(str, values)))


# a and b are sorted. Merge them into one sorted list and return its head.
def merge_two_lists(a, b):

    # Write your code here

    return a


def main():
    input = sys.stdin.readline

    n = int(input())
    a = build_list(list(map(int, input().split())))

    m = int(input())
    b = build_list(list(map(int, input().split())))

    print_list(merge_two_lists(a, b))


main()
