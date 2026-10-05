import java.io.*;
import java.util.*;

public class Main {

    // Return all different triples {a, b, c} from nums with a + b + c == 0.
    static List<List<Integer>> threeSum(int[] nums) {

        // Write your code here

        return new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] nums = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        // Printed in a fixed order: each triple sorted, then the triples sorted.
        List<int[]> rows = new ArrayList<>();

        for (List<Integer> t : threeSum(nums)) {
            int[] row = {t.get(0), t.get(1), t.get(2)};
            Arrays.sort(row);
            rows.add(row);
        }
        rows.sort((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0])
                          : a[1] != b[1] ? Integer.compare(a[1], b[1]) : Integer.compare(a[2], b[2]));

        StringBuilder sb = new StringBuilder();
        sb.append(rows.size()).append("\n");

        for (int[] row : rows) {
            sb.append(row[0]).append(" ").append(row[1]).append(" ").append(row[2]).append("\n");
        }
        System.out.print(sb);
    }
}
