import java.io.*;
import java.util.*;

public class Main {

    // A robot in an m x n grid moves only right or down. Return the number of paths from top-left to bottom-right.
    static int uniquePaths(int m, int n) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        System.out.println(uniquePaths(m, n));
    }
}
