import java.io.*;
import java.util.*;

public class Main {

    // nums is sorted. Keep one copy of each value at the front of nums (in place) and return how many you kept.
    static int removeDuplicates(int[] nums) {

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

        int k = removeDuplicates(nums);

        StringBuilder sb = new StringBuilder();
        sb.append(k).append("\n");

        for (int i = 0; i < k; i++) {
            sb.append(nums[i]).append(" ");
        }
        System.out.println(sb);
    }
}
