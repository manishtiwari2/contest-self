import java.io.*;
import java.util.*;

public class Main {

    // prerequisites[i] = {a, b}: course a needs course b first. Courses are 0 to n-1. Can you take them all?
    static boolean canFinish(int n, int[][] prerequisites) {

        // Write your code here

        return false;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] prerequisites = new int[m][2];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            prerequisites[i][0] = Integer.parseInt(st.nextToken()) - 1;
            prerequisites[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        System.out.println(canFinish(n, prerequisites));
    }
}
