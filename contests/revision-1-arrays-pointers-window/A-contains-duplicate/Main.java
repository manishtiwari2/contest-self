import java.io.*;
import java.util.*;

public class Main {

    // Return true if some value appears at least twice in nums.
    static boolean containsDuplicate(int[] nums) {

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

        System.out.println(containsDuplicate(nums));
    }
}
