import java.io.*;
import java.util.*;

public class Main {

    // Return the k values that appear most often (any order).
    static int[] topKFrequent(int[] nums, int k) {

        // Write your code here

        return new int[k];
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] nums = new int[n];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        int[] answer = topKFrequent(nums, k);
        Arrays.sort(answer);                 // printed from small to big

        StringBuilder sb = new StringBuilder();

        for (int x : answer) {
            sb.append(x).append(" ");
        }
        System.out.println(sb);
    }
}
