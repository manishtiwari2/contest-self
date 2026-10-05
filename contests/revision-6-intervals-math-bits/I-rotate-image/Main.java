import java.io.*;
import java.util.*;

public class Main {

    // Turn the n x n matrix 90 degrees clockwise, in place.
    static void rotate(int[][] m) {

        // Write your code here

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[][] m = new int[n][n];

        for (int r = 0; r < n; r++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int c = 0; c < n; c++) {
                m[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        rotate(m);

        StringBuilder sb = new StringBuilder();

        for (int[] row : m) {
            for (int x : row) {
                sb.append(x).append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}
