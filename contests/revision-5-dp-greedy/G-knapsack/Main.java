import java.io.*;
import java.util.*;

public class Main {

    // Each item has a weight and a value. Take each item at most once, total weight <= capacity. Return the most value.
    static int knapsack(int[] weight, int[] value, int capacity) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int capacity = Integer.parseInt(st.nextToken());

        int[] weight = new int[n];
        int[] value = new int[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            weight[i] = Integer.parseInt(st.nextToken());
            value[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(knapsack(weight, value, capacity));
    }
}
