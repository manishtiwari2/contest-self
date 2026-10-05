import java.io.*;
import java.util.*;

public class Main {

    // flights[i] = {from, to, price}, cities are 0 to n-1. Return the cheapest price from src to dst
    // with at most k stops (k + 1 flights), or -1.
    static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        // Write your code here

        return -1;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int src = Integer.parseInt(st.nextToken()) - 1;
        int dst = Integer.parseInt(st.nextToken()) - 1;
        int k = Integer.parseInt(st.nextToken());

        int[][] flights = new int[m][3];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            flights[i][0] = Integer.parseInt(st.nextToken()) - 1;
            flights[i][1] = Integer.parseInt(st.nextToken()) - 1;
            flights[i][2] = Integer.parseInt(st.nextToken());
        }

        System.out.println(findCheapestPrice(n, flights, src, dst, k));
    }
}
