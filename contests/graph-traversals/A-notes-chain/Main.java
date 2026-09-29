import java.io.*;
import java.util.*;

public class Main {

    // Students are numbered 0 to n-1 here (student 1 in the statement is student 0).
    // friendships[i] = {a, b} means students a and b are friends.
    // Return the students who do NOT get the notes, in increasing order.
    static List<Integer> studentsWithoutNotes(int n, int[][] friendships) {

        // Write your code here

        return new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] friendships = new int[m][2];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            friendships[i][0] = Integer.parseInt(st.nextToken()) - 1;
            friendships[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        List<Integer> missed = studentsWithoutNotes(n, friendships);

        System.out.println(missed.size());

        if (missed.size() > 0) {
            StringBuilder sb = new StringBuilder();

            for (int student : missed) {
                sb.append(student + 1).append(" ");
            }
            System.out.println(sb);
        }
    }
}
