import java.io.*;
import java.util.*;

public class Main {

    // s has only ( ) [ ] { }. Return true if every bracket is closed by the right type, in the right order.
    static boolean isValid(String s) {

        // Write your code here

        return false;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine().trim();

        System.out.println(isValid(s));
    }
}
