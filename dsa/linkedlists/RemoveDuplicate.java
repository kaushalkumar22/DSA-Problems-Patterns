package dsa.linkedlists;

import java.util.HashSet;
import java.util.Set;

public class RemoveDuplicate {
static class Node {
    int data;
    Node next;
    Node(int val) {
        this.data = val;
        this.next = null;
    }
}
    static Node removeDuplicates(Node head) {
        Node curr = head ,prev = null;
        Set<Integer> set = new HashSet<>();
        // Traverse each node in the list
        while (curr != null) {
            if(set.contains(curr.data)) {
                curr = curr.next;
            }else {
                if(prev!=null){
                    prev.next = curr;
                }
                prev = curr;
                set.add(curr.data);
                curr = curr.next;
            }
        }
        if(set.contains(prev.data)){
            prev.next = curr;
        }
        return head;
    }

    static void printList(Node head) {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // Create a singly linked list:
        // 12 -> 11 -> 12 -> 21 -> 41 -> 43 -> 21
        Node head = new Node(12);
        head.next = new Node(11);
        head.next.next = new Node(12);
        head.next.next.next = new Node(21);
        head.next.next.next.next = new Node(41);
        head.next.next.next.next.next = new Node(43);
        head.next.next.next.next.next.next = new Node(21);
        head.next.next.next.next.next.next.next = new Node(33);


        head = removeDuplicates(head);
        printList(head);
    }
}
