import java.io.*;
import java.util.*;

public class Main {

    // Return all values of the matrix in spiral order: right, down, left, up, and inwards.
    static List<Integer> spiralOrder(int[][] m) {

        // Write your code here

        return new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());

        int[][] m = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            st = new StringTokenizer(br.readLine());

            for (int c = 0; c < cols; c++) {
                m[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int x : spiralOrder(m)) {
            sb.append(x).append(" ");
        }
        System.out.println(sb);
    }
}
