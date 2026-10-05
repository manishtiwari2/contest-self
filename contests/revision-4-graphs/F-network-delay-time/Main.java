import java.io.*;
import java.util.*;

public class Main {

    // times[i] = {u, v, w}: a one-way signal from u to v takes w. Nodes are 1..n, the signal starts at k.
    // Return the time until every node has the signal, or -1 if some node never gets it.
    static int networkDelayTime(int[][] times, int n, int k) {

        // Write your code here

        return -1;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] times = new int[m][3];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            times[i][0] = Integer.parseInt(st.nextToken());
            times[i][1] = Integer.parseInt(st.nextToken());
            times[i][2] = Integer.parseInt(st.nextToken());
        }

        System.out.println(networkDelayTime(times, n, k));
    }
}
