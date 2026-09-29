import java.io.*;
import java.util.*;

public class Main {

    static int R, C;
    static int[][] dirs = {{0, 1},{1, 0},{0, -1},{-1, 0}};

    // aura has R rows and C columns: aura[i][j] is the senior in row i, column j.
    // Aarav starts in cell (sr, sc), 0-indexed here, whose aura is 0.
    // c0 is his starting confidence.
    // Return his maximum possible confidence at the end of the party.
    static long maxConfidence(int[][] aura, int sr, int sc, int c0) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        int c0 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int sr = Integer.parseInt(st.nextToken()) - 1;
        int sc = Integer.parseInt(st.nextToken()) - 1;

        int[][] aura = new int[R][C];

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < C; j++) {
                aura[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(maxConfidence(aura, sr, sc, c0));
    }
}
