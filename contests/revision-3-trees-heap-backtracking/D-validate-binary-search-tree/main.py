import sys
import heapq
from collections import deque


class TreeNode:
    def __init__(self, val, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


# Builds a tree from level-order tokens, like on LeetCode: 3 9 20 null null 15 7
def build_tree(tokens):
    if not tokens or tokens[0] == "null":
        return None
    root = TreeNode(int(tokens[0]))
    queue = deque([root])
    i = 1
    while i < len(tokens):
        node = queue.popleft()
        if tokens[i] != "null":
            node.left = TreeNode(int(tokens[i]))
            queue.append(node.left)
        i += 1
        if i < len(tokens) and tokens[i] != "null":
            node.right = TreeNode(int(tokens[i]))
            queue.append(node.right)
        i += 1
    return root


# Return True if every node is bigger than everything on its left and smaller than everything on its right.
def is_valid_bst(root):

    # Write your code here

    return False


def main():
    input = sys.stdin.readline

    n = int(input())
    root = build_tree(input().split())

    print("true" if is_valid_bst(root) else "false")


main()
