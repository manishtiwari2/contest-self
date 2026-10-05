import java.io.*;
import java.util.*;

public class Main {

    // Stops are numbered 0 to n-1 here (stop 1 in the statement is stop 0).
    // routes[i] = {a, b, c} means a one-way auto ride from stop a to stop b that costs c rupees.
    // You have one coupon that halves the fare of a single ride (rounded down).
    // Return the cheapest cost from stop 0 to stop n-1, or -1 if you can't get there.
    static long cheapestTrip(int n, int[][] routes) {

        // Write your code here

        return -1;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] routes = new int[m][3];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            routes[i][0] = Integer.parseInt(st.nextToken()) - 1;
            routes[i][1] = Integer.parseInt(st.nextToken()) - 1;
            routes[i][2] = Integer.parseInt(st.nextToken());
        }

        System.out.println(cheapestTrip(n, routes));
    }
}
