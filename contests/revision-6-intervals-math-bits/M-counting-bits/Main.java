import java.io.*;
import java.util.*;

public class Main {

    // ans[i] = the number of 1-bits in i, for every i from 0 to n.
    static int[] countBits(int n) {

        // Write your code here

        return new int[n + 1];
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        for (int x : countBits(n)) {
            sb.append(x).append(" ");
        }
        System.out.println(sb);
    }
}
