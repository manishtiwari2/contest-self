import java.io.*;
import java.util.*;

public class Main {

    static final long JACKPOT = Long.MAX_VALUE;

    // Stalls are numbered 0 to n-1 here (stall 1 in the statement is stall 0).
    // walkways[i] = {a, b, x} means a one-way walkway from stall a to stall b that changes your coins by x
    // (x > 0: you win coins, x < 0: you pay).
    // Return the most coins you can have on reaching stall n-1 from stall 0,
    // or JACKPOT if you can make that number as large as you like.
    static long maxCoins(int n, int[][] walkways) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] walkways = new int[m][3];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            walkways[i][0] = Integer.parseInt(st.nextToken()) - 1;
            walkways[i][1] = Integer.parseInt(st.nextToken()) - 1;
            walkways[i][2] = Integer.parseInt(st.nextToken());
        }

        long answer = maxCoins(n, walkways);

        System.out.println(answer == JACKPOT ? "JACKPOT" : String.valueOf(answer));
    }
}
