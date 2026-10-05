import java.io.*;
import java.util.*;

public class Main {

    // nums is sorted and has no repeats. Return the index of target, or -1.
    static int search(int[] nums, int target) {

        // Write your code here

        return -1;
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

            sb.append(search(nums, target)).append("\n");
        }
        System.out.print(sb);
    }
}
