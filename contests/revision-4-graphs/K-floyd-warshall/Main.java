import java.io.*;
import java.util.*;

public class Main {

    // dist[i][j] starts as the edge weight (or INF), and dist[i][i] = 0. Turn it into the shortest distances.
    static void floydWarshall(long[][] dist) {

        // Write your code here

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        long INF = Long.MAX_VALUE / 4;
        long[][] dist = new long[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            long w = Long.parseLong(st.nextToken());

            dist[a][b] = Math.min(dist[a][b], w);      // one-way edge, keep the cheapest
        }

        floydWarshall(dist);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            sb.append(dist[a][b] >= INF ? -1 : dist[a][b]).append("\n");
        }
        System.out.print(sb);
    }
}
