import java.io.*;
import java.util.*;

public class Main {

    // Buildings are numbered 0 to n-1 here (building 1 in the statement is building 0).
    // roads[i] = {a, b, t} means a two-way road between a and b that takes t minutes.
    // questions[i] = {a, b, x}: the fastest time from a to b when only buildings 0 to x-1 are open to walk
    // through (a and b themselves are always fine). x = 0 means no building is open.
    // Return the answers in the same order, with -1 where it's impossible.
    static long[] answerQuestions(int n, int[][] roads, int[][] questions) {

        // Write your code here

        return new long[questions.length];
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        int[][] roads = new int[m][3];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            roads[i][0] = Integer.parseInt(st.nextToken()) - 1;
            roads[i][1] = Integer.parseInt(st.nextToken()) - 1;
            roads[i][2] = Integer.parseInt(st.nextToken());
        }

        int[][] questions = new int[q][3];

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());

            questions[i][0] = Integer.parseInt(st.nextToken()) - 1;
            questions[i][1] = Integer.parseInt(st.nextToken()) - 1;
            questions[i][2] = Integer.parseInt(st.nextToken());
        }

        long[] answers = answerQuestions(n, roads, questions);

        StringBuilder sb = new StringBuilder();

        for (long answer : answers) {
            sb.append(answer).append("\n");
        }
        System.out.print(sb);
    }
}
