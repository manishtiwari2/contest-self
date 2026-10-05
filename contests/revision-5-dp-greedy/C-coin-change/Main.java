import java.io.*;
import java.util.*;

public class Main {

    // Return the fewest coins that make amount (each coin can be used many times), or -1.
    static int coinChange(int[] coins, int amount) {

        // Write your code here

        return -1;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int amount = Integer.parseInt(st.nextToken());

        int[] coins = new int[n];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            coins[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(coinChange(coins, amount));
    }
}
