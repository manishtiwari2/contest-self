import java.io.*;
import java.util.*;

public class Main {

    // Merge all overlapping intervals and return them sorted by start. Touching intervals like [1, 4] and [4, 5] merge.
    static int[][] merge(int[][] intervals) {

        // Write your code here

        return intervals;
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

        int[][] merged = merge(intervals);

        StringBuilder sb = new StringBuilder();
        sb.append(merged.length).append("\n");

        for (int[] iv : merged) {
            sb.append(iv[0]).append(" ").append(iv[1]).append("\n");
        }
        System.out.print(sb);
    }
}
