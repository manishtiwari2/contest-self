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

    // Remove the n-th node from the end (n = 1 is the last node) and return the head.
    static ListNode removeNthFromEnd(ListNode head, int n) {

        // Write your code here

        return head;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int size = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        ListNode head = readList(br, size);

        printList(removeNthFromEnd(head, n));
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
