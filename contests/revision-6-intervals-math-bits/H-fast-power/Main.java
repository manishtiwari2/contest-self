import java.io.*;
import java.util.*;

public class Main {

    // Return (base ^ exp) % mod quickly, even when exp is huge.
    static long modPow(long base, long exp, long mod) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            long base = Long.parseLong(st.nextToken());
            long exp = Long.parseLong(st.nextToken());
            long mod = Long.parseLong(st.nextToken());

            sb.append(modPow(base, exp, mod)).append("\n");
        }
        System.out.print(sb);
    }
}
