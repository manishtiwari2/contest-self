import java.io.*;
import java.util.*;

public class Main {

    // gcd = greatest common divisor, lcm = least common multiple.
    static long gcd(long a, long b) {

        // Write your code here

        return 1;
    }

    static long lcm(long a, long b) {

        // Write your code here

        return 1;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());

            sb.append(gcd(a, b)).append(" ").append(lcm(a, b)).append("\n");
        }
        System.out.print(sb);
    }
}
