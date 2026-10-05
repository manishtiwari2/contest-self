import java.io.*;
import java.util.*;

public class Main {

    // Students are numbered 0 to n-1 here (student 1 in the statement is student 0).
    // sources = the students who already know the gossip at minute 0.
    // friends[i] = {a, b} means a and b are friends (it works both ways).
    // Return an array where answer[v] = the minute student v first hears the gossip, or -1 if never.
    static int[] gossipTime(int n, int[] sources, int[][] friends) {

        // Write your code here

        return new int[n];
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] sources = new int[k];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < k; i++) {
            sources[i] = Integer.parseInt(st.nextToken()) - 1;
        }

        int[][] friends = new int[m][2];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            friends[i][0] = Integer.parseInt(st.nextToken()) - 1;
            friends[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        int[] answer = gossipTime(n, sources, friends);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            sb.append(answer[i]).append(" ");
        }
        System.out.println(sb);
    }
}
