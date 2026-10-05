import java.io.*;
import java.util.*;

public class Main {

    // nums[i] is the longest jump from index i. Return true if you can reach the last index from index 0.
    static boolean canJump(int[] nums) {

        // Write your code here

        return false;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] nums = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(canJump(nums));
    }
}
