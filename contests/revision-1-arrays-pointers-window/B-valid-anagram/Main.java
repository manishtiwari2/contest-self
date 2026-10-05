import java.io.*;
import java.util.*;

public class Main {

    // Return true if t uses exactly the same letters as s, the same number of times.
    static boolean isAnagram(String s, String t) {

        // Write your code here

        return false;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine().trim();
        String t = br.readLine().trim();

        System.out.println(isAnagram(s, t));
    }
}
