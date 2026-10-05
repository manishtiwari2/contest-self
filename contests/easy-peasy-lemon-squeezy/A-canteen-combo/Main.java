import java.io.*;
import java.util.*;

public class Main {

    // prices[i] = the price of snack i. money = your pocket money.
    // Return how many pairs of different snacks (i < j) together cost exactly money.
    static long countCombos(int[] prices, long money) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        long money = Long.parseLong(st.nextToken());

        int[] prices = new int[n];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            prices[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(countCombos(prices, money));
    }
}
