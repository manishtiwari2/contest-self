import java.io.*;
import java.util.*;

public class Main {

    // tokens are numbers and the operators + - * / (division rounds toward zero). Return the value.
    static int evalRPN(String[] tokens) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        String[] tokens = new String[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            tokens[i] = st.nextToken();
        }

        System.out.println(evalRPN(tokens));
    }
}
