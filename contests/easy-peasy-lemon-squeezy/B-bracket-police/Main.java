import java.io.*;
import java.util.*;

public class Main {

    // s contains only the characters ( ) [ ] { }
    // Return true if every bracket is closed by its matching bracket, in the right order.
    static boolean isBalanced(String s) {

        // Write your code here

        return false;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < t; i++) {
            String s = br.readLine().trim();

            sb.append(isBalanced(s) ? "YES" : "NO").append("\n");
        }
        System.out.print(sb);
    }
}
