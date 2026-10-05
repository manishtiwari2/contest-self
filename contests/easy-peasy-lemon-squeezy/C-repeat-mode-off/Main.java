import java.io.*;
import java.util.*;

public class Main {

    // songs[i] = the id of the i-th song in the playlist.
    // Return the length of the longest run of consecutive songs in which no song repeats.
    static int longestFreshRun(int[] songs) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());

        int[] songs = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            songs[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(longestFreshRun(songs));
    }
}
