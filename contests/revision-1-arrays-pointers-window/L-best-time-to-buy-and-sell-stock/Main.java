import java.io.*;
import java.util.*;

public class Main {

    // prices[i] = price on day i. Buy once, then sell on a later day. Return the biggest profit, or 0.
    static int maxProfit(int[] prices) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] prices = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            prices[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(maxProfit(prices));
    }
}
