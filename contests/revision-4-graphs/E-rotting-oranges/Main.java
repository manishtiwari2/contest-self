import java.io.*;
import java.util.*;

public class Main {

    static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};   // down, up, right, left

    // 0 = empty, 1 = fresh, 2 = rotten. Return the minutes until nothing is fresh, or -1 if that never happens.
    static int orangesRotting(int[][] grid) {

        // Write your code here

        return -1;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());

        int[][] grid = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            st = new StringTokenizer(br.readLine());

            for (int c = 0; c < cols; c++) {
                grid[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(orangesRotting(grid));
    }
}
