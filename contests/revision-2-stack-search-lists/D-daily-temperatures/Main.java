import java.io.*;
import java.util.*;

public class Main {

    // answer[i] = how many days after day i until a warmer day, or 0 if there is none.
    static int[] dailyTemperatures(int[] temps) {

        // Write your code here

        return new int[temps.length];
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] temps = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            temps[i] = Integer.parseInt(st.nextToken());
        }

        StringBuilder sb = new StringBuilder();

        for (int x : dailyTemperatures(temps)) {
            sb.append(x).append(" ");
        }
        System.out.println(sb);
    }
}
