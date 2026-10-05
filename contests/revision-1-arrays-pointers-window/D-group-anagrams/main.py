import sys
import heapq
from collections import deque


# Put words that are anagrams of each other into the same group. Any order is fine.
def group_anagrams(words):

    # Write your code here

    return []


def main():
    input = sys.stdin.readline

    n = int(input())
    words = input().split()

    # Printed in a fixed order: words sorted inside each group, then the groups sorted.
    lines = sorted(" ".join(sorted(group)) for group in group_anagrams(words))

    print(len(lines))
    print("\n".join(lines))


main()
