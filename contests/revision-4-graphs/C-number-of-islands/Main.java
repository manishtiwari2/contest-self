import java.io.*;
import java.util.*;

public class Main {

    // grid has '1' (land) and '0' (water). Return the number of islands (land joined up, down, left or right).
    static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};   // down, up, right, left

    static int numIslands(char[][] grid) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());

        char[][] grid = new char[rows][];

        for (int r = 0; r < rows; r++) {
            grid[r] = br.readLine().trim().toCharArray();
        }

        System.out.println(numIslands(grid));
    }
}
