import java.io.*;
import java.util.*;

public class Main {

    // Students are numbered 0 to n-1 here (student 1 in the statement is student 0).
    // waits[i] = {a, b} means student a is waiting for student b.
    // Return the students who can never finish, in increasing order.
    static List<Integer> stuckStudents(int n, int[][] waits) {

        // Write your code here

        return new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] waits = new int[m][2];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            waits[i][0] = Integer.parseInt(st.nextToken()) - 1;
            waits[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        List<Integer> stuck = stuckStudents(n, waits);

        System.out.println(stuck.size());

        if (stuck.size() > 0) {
            StringBuilder sb = new StringBuilder();

            for (int student : stuck) {
                sb.append(student + 1).append(" ");
            }
            System.out.println(sb);
        }
    }
}
