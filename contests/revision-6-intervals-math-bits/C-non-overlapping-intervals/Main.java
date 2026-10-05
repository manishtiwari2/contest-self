import java.io.*;
import java.util.*;

public class Main {

    // Return the fewest intervals to remove so that the rest don't overlap (touching is fine).
    static int eraseOverlapIntervals(int[][] intervals) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[][] intervals = new int[n][2];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            intervals[i][0] = Integer.parseInt(st.nextToken());
            intervals[i][1] = Integer.parseInt(st.nextToken());
        }

        System.out.println(eraseOverlapIntervals(intervals));
    }
}
