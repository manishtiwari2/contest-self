import java.io.*;
import java.util.*;

public class Main {

    // Every number appears twice except one. Return that one, using O(1) extra memory.
    static int singleNumber(int[] nums) {

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

        System.out.println(singleNumber(nums));
    }
}
