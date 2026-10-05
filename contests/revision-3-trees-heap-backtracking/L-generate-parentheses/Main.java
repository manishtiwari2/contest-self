import java.io.*;
import java.util.*;

public class Main {

    // Return every valid string with n pairs of brackets.
    static List<String> generateParenthesis(int n) {

        // Write your code here

        return new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        List<String> result = new ArrayList<>(generateParenthesis(n));
        Collections.sort(result);              // printed in sorted order

        StringBuilder sb = new StringBuilder();
        sb.append(result.size()).append("\n");

        for (String s : result) {
            sb.append(s).append("\n");
        }
        System.out.print(sb);
    }
}
