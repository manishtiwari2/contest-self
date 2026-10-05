import java.io.*;
import java.util.*;

public class Main {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    // a and b are sorted. Merge them into one sorted list and return its head.
    static ListNode mergeTwoLists(ListNode a, ListNode b) {

        // Write your code here

        return a;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        ListNode a = readList(br, n);

        int m = Integer.parseInt(br.readLine().trim());
        ListNode b = readList(br, m);

        printList(mergeTwoLists(a, b));
    }

    // Builds a linked list from the next line of input.
    static ListNode readList(BufferedReader br, int n) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        for (int i = 0; i < n; i++) {
            tail.next = new ListNode(Integer.parseInt(st.nextToken()));
            tail = tail.next;
        }
        return dummy.next;
    }

    static void printList(ListNode head) {
        StringBuilder sb = new StringBuilder();

        for (ListNode node = head; node != null; node = node.next) {
            sb.append(node.val).append(" ");
        }
        System.out.println(sb);
    }
}
