import java.io.*;
import java.util.*;

public class Main {

    // points[i] = {x, y}. Return the k points closest to (0, 0) (any order).
    static int[][] kClosest(int[][] points, int k) {

        // Write your code here

        return new int[0][];
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] points = new int[n][2];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            points[i][0] = Integer.parseInt(st.nextToken());
            points[i][1] = Integer.parseInt(st.nextToken());
        }

        int[][] answer = kClosest(points, k);
        Arrays.sort(answer, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));

        StringBuilder sb = new StringBuilder();

        for (int[] p : answer) {
            sb.append(p[0]).append(" ").append(p[1]).append("\n");
        }
        System.out.print(sb);
    }
}
