import java.io.*;
import java.util.*;

public class Main {


    // pre[i] = nums[0] + nums[1] + ... + nums[i-1], and pre[0] = 0
    static long[] buildPrefix(int[] nums) {

        // Write your code here

        return new long[nums.length + 1];
    }

    // Sum of nums[l..r] (both ends included) in O(1)
    static long rangeSum(long[] pre, int l, int r) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        int[] nums = new int[n];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        long[] pre = buildPrefix(nums);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());

            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            sb.append(rangeSum(pre, l, r)).append("\n");
        }
        System.out.print(sb);
    }
}
