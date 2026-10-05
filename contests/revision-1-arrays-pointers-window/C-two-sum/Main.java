import java.io.*;
import java.util.*;

public class Main {

    // Return {i, j} with i < j and nums[i] + nums[j] == target. There is exactly one answer.
    static int[] twoSum(int[] nums, int target) {

        // Write your code here

        return new int[]{-1, -1};
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int target = Integer.parseInt(st.nextToken());

        int[] nums = new int[n];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        int[] answer = twoSum(nums, target);

        System.out.println(answer[0] + " " + answer[1]);
    }
}
