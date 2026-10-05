import java.io.*;
import java.util.*;

public class Main {

    static int R, C;
    static int[][] dirs = {{0, 1},{1, 0},{0, -1},{-1, 0}};

    // grid has R rows and C columns: '#' wall, '.' open, 'S' your room, 'M' the Maggi stall.
    // Return the fewest steps from S to M, or -1 if you can't get there.
    static int minSteps(char[][] grid) {

        // Write your code here

        return -1;
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

        System.out.println(minSteps(grid));
    }
}
