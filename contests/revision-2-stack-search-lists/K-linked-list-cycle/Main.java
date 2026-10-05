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

    // Return true if the list loops back on itself.
    static boolean hasCycle(ListNode head) {

        // Write your code here

        return false;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int pos = Integer.parseInt(st.nextToken());

        ListNode head = readList(br, n);

        if (pos > 0) {                       // the last node points back to node number pos
            ListNode target = head, tail = head;
            for (int i = 1; i < pos; i++) {
                target = target.next;
            }
            while (tail.next != null) {
                tail = tail.next;
            }
            tail.next = target;
        }

        System.out.println(hasCycle(head));
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
