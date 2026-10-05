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

    // Mirror the tree (swap left and right everywhere) and return the root.
    static TreeNode invertTree(TreeNode root) {

        // Write your code here

        return root;
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

        System.out.println(serialize(invertTree(root)));
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

    // The tree as level-order tokens, like on LeetCode, without the nulls at the end.
    static String serialize(TreeNode root) {
        List<String> out = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();   // LinkedList can hold null
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node == null) {
                out.add("null");
            } else {
                out.add(String.valueOf(node.val));
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        int end = out.size();
        while (end > 0 && out.get(end - 1).equals("null")) {
            end--;
        }
        return String.join(" ", out.subList(0, end));
    }
}
