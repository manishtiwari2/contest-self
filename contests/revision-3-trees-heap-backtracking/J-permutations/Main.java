import java.io.*;
import java.util.*;

public class Main {

    // Return every order of the numbers (they are all different).
    static List<List<Integer>> permute(int[] nums) {

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

        printLists(permute(nums), false);
    }

    // Prints the lists in a fixed order, one per line, like [1, 3].
    static void printLists(List<List<Integer>> lists, boolean sortInside) {
        List<List<Integer>> rows = new ArrayList<>();

        for (List<Integer> list : lists) {
            List<Integer> row = new ArrayList<>(list);
            if (sortInside) {
                Collections.sort(row);
            }
            rows.add(row);
        }
        rows.sort((a, b) -> {
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                int c = Integer.compare(a.get(i), b.get(i));
                if (c != 0) {
                    return c;
                }
            }
            return Integer.compare(a.size(), b.size());
        });

        StringBuilder sb = new StringBuilder();
        sb.append(rows.size()).append("\n");

        for (List<Integer> row : rows) {
            sb.append(row).append("\n");
        }
        System.out.print(sb);
    }
}
