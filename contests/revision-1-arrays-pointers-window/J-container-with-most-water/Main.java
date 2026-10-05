import java.io.*;
import java.util.*;

public class Main {

    // height[i] is a wall. Water between walls i and j = min(height[i], height[j]) * (j - i). Return the most water.
    static int maxArea(int[] height) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] height = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            height[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(maxArea(height));
    }
}
