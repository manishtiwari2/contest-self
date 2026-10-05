import java.io.*;
import java.util.*;

public class Main {

    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // Return the number of nodes on the longest path from the root down to a leaf (0 for an empty tree).
    static int maxDepth(TreeNode root) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        String[] tokens = new String[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            tokens[i] = st.nextToken();
        }
        TreeNode root = buildTree(tokens);

        System.out.println(maxDepth(root));
    }

    // Builds a tree from level-order tokens, like on LeetCode: 3 9 20 null null 15 7
    static TreeNode buildTree(String[] tokens) {
        if (tokens.length == 0 || tokens[0].equals("null")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int i = 1;

        while (i < tokens.length) {
            TreeNode node = queue.poll();

            if (!tokens[i].equals("null")) {
                node.left = new TreeNode(Integer.parseInt(tokens[i]));
                queue.offer(node.left);
            }
            i++;
            if (i < tokens.length && !tokens[i].equals("null")) {
                node.right = new TreeNode(Integer.parseInt(tokens[i]));
                queue.offer(node.right);
            }
            i++;
        }
        return root;
    }
}
