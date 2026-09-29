import java.io.*;
import java.util.*;

public class Main {

    static int R, C;
    static int[][] dirs = {{0, 1},{1, 0},{0, -1},{-1, 0}};

    // grid has R rows and C columns. Each cell is '#', '.', 'L' or 'G'.
    // Return the minimum number of portable generators needed.
    static int minGenerators(char[][] grid) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        char[][] grid = new char[R][C];

        for (int i = 0; i < R; i++) {
            grid[i] = br.readLine().toCharArray();
        }

        System.out.println(minGenerators(grid));
    }
}
