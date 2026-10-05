import java.io.*;
import java.util.*;

public class Main {

    // Smash the two heaviest stones again and again. Return the weight of the last stone, or 0.
    static int lastStoneWeight(int[] stones) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[] stones = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            stones[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(lastStoneWeight(stones));
    }
}
