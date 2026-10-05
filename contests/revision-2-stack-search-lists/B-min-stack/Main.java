import java.io.*;
import java.util.*;

public class Main {

    // A stack that can also tell its smallest value. Every method should be O(1).
    static class MinStack {

        // Add your fields here

        void push(int x) {
            // Write your code here
        }

        void pop() {
            // Write your code here
        }

        int top() {
            // Write your code here
            return 0;
        }

        int getMin() {
            // Write your code here
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        MinStack stack = new MinStack();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String op = st.nextToken();

            if (op.equals("push")) {
                stack.push(Integer.parseInt(st.nextToken()));
            } else if (op.equals("pop")) {
                stack.pop();
            } else if (op.equals("top")) {
                sb.append(stack.top()).append("\n");
            } else {
                sb.append(stack.getMin()).append("\n");
            }
        }
        System.out.print(sb);
    }
}
