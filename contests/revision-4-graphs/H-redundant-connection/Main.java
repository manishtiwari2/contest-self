import java.io.*;
import java.util.*;

public class Main {

    static class DSU {
        int[] parent, size;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;                         // everyone starts as their own leader
                size[i] = 1;
            }
        }

        int find(int x) {                              // the leader of x's group
            if (parent[x] != x) {
                parent[x] = find(parent[x]);           // shortcut: point straight at the leader
            }
            return parent[x];
        }

        boolean union(int a, int b) {                  // false if they were already together
            int ra = find(a), rb = find(b);
            if (ra == rb) {
                return false;
            }
            if (size[ra] < size[rb]) {                 // attach the smaller group below the bigger
                int t = ra; ra = rb; rb = t;
            }
            parent[rb] = ra;
            size[ra] += size[rb];
            return true;
        }
    }

    // A tree on nodes 1..n got one extra edge. Return the edge that closes the loop (the last one in the input).
    // The DSU class from the notes is already above.
    static int[] findRedundantConnection(int[][] edges) {

        // Write your code here

        return new int[]{0, 0};
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[][] edges = new int[n][2];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            edges[i][0] = Integer.parseInt(st.nextToken());
            edges[i][1] = Integer.parseInt(st.nextToken());
        }

        int[] answer = findRedundantConnection(edges);

        System.out.println(answer[0] + " " + answer[1]);
    }
}
