import java.io.*;
import java.util.*;

public class Main {

    static final long MOD = 1_000_000_007L;

    // Spots are numbered 0 to n-1 here (spot 1 in the statement is spot 0).
    // roads[i] = {a, b, t} means a two-way road between a and b that takes t minutes.
    // Return {fastest time from spot 0 to spot n-1, number of fastest routes modulo MOD},
    // or {-1, 0} if the canteen can't be reached.
    static long[] fastestRoutes(int n, int[][] roads) {

        // Write your code here

        return new long[]{-1, 0};
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] roads = new int[m][3];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            roads[i][0] = Integer.parseInt(st.nextToken()) - 1;
            roads[i][1] = Integer.parseInt(st.nextToken()) - 1;
            roads[i][2] = Integer.parseInt(st.nextToken());
        }

        long[] answer = fastestRoutes(n, roads);

        System.out.println(answer[0] + " " + answer[1]);
    }
}
