import java.io.*;
import java.util.*;

public class Main {

    // nums is sorted and has no repeats. Return the first index whose value is >= target.
    static int searchInsert(int[] nums, int target) {

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

        StringBuilder sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < q; i++) {
            int target = Integer.parseInt(st.nextToken());

            sb.append(searchInsert(nums, target)).append("\n");
        }
        System.out.print(sb);
    }
}
