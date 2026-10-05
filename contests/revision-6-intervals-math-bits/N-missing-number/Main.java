import java.io.*;
import java.util.*;

public class Main {

    // nums has n different numbers from 0 to n. Return the one that is missing.
    static int missingNumber(int[] nums) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] nums = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(missingNumber(nums));
    }
}
