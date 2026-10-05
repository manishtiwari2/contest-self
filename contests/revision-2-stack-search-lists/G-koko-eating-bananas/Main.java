import java.io.*;
import java.util.*;

public class Main {

    // Koko eats up to k bananas per hour from one pile. Return the smallest k that finishes all piles in h hours.
    static int minEatingSpeed(int[] piles, int h) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        int[] piles = new int[n];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            piles[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(minEatingSpeed(piles, h));
    }
}
