import java.io.*;
import java.util.*;

public class Main {

    // Ignore everything that is not a letter or digit, and ignore case. Does s read the same both ways?
    static boolean isPalindrome(String s) {

        // Write your code here

        return false;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();

        System.out.println(isPalindrome(s));
    }
}
