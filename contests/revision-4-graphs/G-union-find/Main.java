import java.io.*;
import java.util.*;

public class Main {

    // Keep groups of nodes 0 to n-1. find(x) = the leader of x's group.
    // union(a, b) joins the two groups and returns false if a and b were already together.
    static class DSU {

        // Add your fields here

        DSU(int n) {
            // Write your code here
        }

        int find(int x) {
            // Write your code here
            return x;
        }

        boolean union(int a, int b) {
            // Write your code here
            return false;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        DSU dsu = new DSU(n);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());

            String op = st.nextToken();
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            if (op.equals("union")) {
                sb.append(dsu.union(a, b)).append("\n");
            } else {
                sb.append(dsu.find(a) == dsu.find(b)).append("\n");
            }
        }
        System.out.print(sb);
    }
}
