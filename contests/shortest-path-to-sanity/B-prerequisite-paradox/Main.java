import java.io.*;
import java.util.*;

public class Main {

    // Courses are numbered 0 to n-1 here (course 1 in the statement is course 0).
    // rules[i] = {a, b} means: to take course a, you must first pass course b. Rules are added in this order.
    // Return the number of the first rule (1-based, as in the statement) after which some course needs
    // itself, or -1 if that never happens.
    static int firstBadRule(int n, int[][] rules) {

        // Write your code here

        return -1;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] rules = new int[m][2];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            rules[i][0] = Integer.parseInt(st.nextToken()) - 1;
            rules[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        System.out.println(firstBadRule(n, rules));
    }
}
