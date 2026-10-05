import java.io.*;
import java.util.*;

public class Main {

    // Return true if n is prime.
    static boolean isPrime(int n) {

        // Write your code here

        return false;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            int x = Integer.parseInt(br.readLine().trim());

            sb.append(isPrime(x)).append("\n");
        }
        System.out.print(sb);
    }
}
