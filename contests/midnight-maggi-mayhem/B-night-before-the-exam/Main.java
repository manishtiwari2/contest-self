import java.io.*;
import java.util.*;

public class Main {

    // Chapter i takes hours[i] hours to study and is worth marks[i] marks.
    // Each chapter can be studied at most once.
    // Return the most marks you can get in at most H hours.
    static long maxMarks(int H, int[] hours, int[] marks) {

        // Write your code here

        return 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());

        int[] hours = new int[n];
        int[] marks = new int[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            hours[i] = Integer.parseInt(st.nextToken());
            marks[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(maxMarks(H, hours, marks));
    }
}
