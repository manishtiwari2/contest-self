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


# The tree as level-order tokens, like on LeetCode, without the nulls at the end.
def serialize(root):
    out = []
    queue = deque([root])
    while queue:
        node = queue.popleft()
        if node is None:
            out.append("null")
        else:
            out.append(str(node.val))
            queue.append(node.left)
            queue.append(node.right)
    while out and out[-1] == "null":
        out.pop()
    return " ".join(out)


# Mirror the tree (swap left and right everywhere) and return the root.
def invert_tree(root):

    # Write your code here

    return root


def main():
    input = sys.stdin.readline

    n = int(input())
    root = build_tree(input().split())

    print(serialize(invert_tree(root)))


main()
