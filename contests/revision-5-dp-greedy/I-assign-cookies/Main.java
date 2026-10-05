import java.io.*;
import java.util.*;

public class Main {

    // Child i is happy with a cookie of size >= g[i]. Each child gets at most one cookie. Return the most happy children.
    static int findContentChildren(int[] g, int[] s) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] g = new int[n];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            g[i] = Integer.parseInt(st.nextToken());
        }

        int[] s = new int[m];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < m; i++) {
            s[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(findContentChildren(g, s));
    }
}
