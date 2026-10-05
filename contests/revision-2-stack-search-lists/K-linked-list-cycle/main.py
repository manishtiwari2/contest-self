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


# Return True if the list loops back on itself.
def has_cycle(head):

    # Write your code here

    return False


def main():
    input = sys.stdin.readline

    n, pos = map(int, input().split())
    head = build_list(list(map(int, input().split())))

    if pos > 0:                         # the last node points back to node number pos
        target = tail = head
        for _ in range(pos - 1):
            target = target.next
        while tail.next:
            tail = tail.next
        tail.next = target

    print("true" if has_cycle(head) else "false")


main()
