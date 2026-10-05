import java.io.*;
import java.util.*;

public class Main {

    // Return the station to start from to drive the whole circle once, or -1. The answer is unique.
    static int canCompleteCircuit(int[] gas, int[] cost) {

        // Write your code here

        return -1;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] gas = new int[n];
        int[] cost = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            gas[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(canCompleteCircuit(gas, cost));
    }
}
