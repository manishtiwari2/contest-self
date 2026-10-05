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


# Remove the n-th node from the end (n = 1 is the last node) and return the head.
def remove_nth_from_end(head, n):

    # Write your code here

    return head


def main():
    input = sys.stdin.readline

    size, n = map(int, input().split())
    head = build_list(list(map(int, input().split())))

    print_list(remove_nth_from_end(head, n))


main()
