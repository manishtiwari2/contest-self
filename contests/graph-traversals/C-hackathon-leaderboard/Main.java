import java.io.*;
import java.util.*;

public class Main {

    // Teams are numbered 0 to n-1 here (team 1 in the statement is team 0).
    // notes[i] = {a, b} means team a finished above team b.
    // Return:
    //   null           if the notes contradict each other   (CONTRADICTION)
    //   an empty list  if more than one ranking fits         (MULTIPLE)
    //   the ranking    from first place to last, if exactly one fits (UNIQUE)
    static List<Integer> leaderboard(int n, int[][] notes) {

        // Write your code here

        return new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] notes = new int[m][2];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            notes[i][0] = Integer.parseInt(st.nextToken()) - 1;
            notes[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        List<Integer> ranking = leaderboard(n, notes);

        if (ranking == null) {
            System.out.println("CONTRADICTION");
        } else if (ranking.isEmpty()) {
            System.out.println("MULTIPLE");
        } else {
            StringBuilder sb = new StringBuilder();

            for (int team : ranking) {
                sb.append(team + 1).append(" ");
            }
            System.out.println("UNIQUE");
            System.out.println(sb);
        }
    }
}
