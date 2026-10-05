import java.io.*;
import java.util.*;

public class Main {

    // Return how many 1-bits n has.
    static int hammingWeight(int n) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            int x = Integer.parseInt(br.readLine().trim());

            sb.append(hammingWeight(x)).append("\n");
        }
        System.out.print(sb);
    }
}
