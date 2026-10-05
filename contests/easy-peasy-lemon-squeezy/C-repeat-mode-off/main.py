import sys
import heapq
from collections import deque


# songs[i] = the id of the i-th song in the playlist.
# Return the length of the longest run of consecutive songs in which no song repeats.
def longest_fresh_run(songs):

    # Write your code here

    return 0


def main():
    input = sys.stdin.readline

    n = int(input())

    songs = list(map(int, input().split()))

    print(longest_fresh_run(songs))


main()
