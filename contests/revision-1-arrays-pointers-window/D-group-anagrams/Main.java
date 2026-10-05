import java.io.*;
import java.util.*;

public class Main {

    // Put words that are anagrams of each other into the same group. Any order is fine.
    static List<List<String>> groupAnagrams(String[] words) {

        // Write your code here

        return new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        String[] words = new String[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            words[i] = st.nextToken();
        }

        // Printed in a fixed order: words sorted inside each group, then the groups sorted.
        List<String> lines = new ArrayList<>();

        for (List<String> group : groupAnagrams(words)) {
            List<String> sorted = new ArrayList<>(group);
            Collections.sort(sorted);
            lines.add(String.join(" ", sorted));
        }
        Collections.sort(lines);

        StringBuilder sb = new StringBuilder();
        sb.append(lines.size()).append("\n");

        for (String line : lines) {
            sb.append(line).append("\n");
        }
        System.out.print(sb);
    }
}
