package lld.lrucache;

import java.util.HashMap;
import java.util.Map;

/* -------- Node -------- */
class Node {
    int key, val;
    Node prev, next;
    Node(int k, int v) {
        key = k;
        val = v;
    }
}

/* -------- Doubly Linked List -------- */
class DLinkedList {
    Node head, tail;

    DLinkedList() {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    void addToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    void moveToFront(Node node) {
        remove(node);
        addToFront(node);
    }

    Node removeLast() {
        if (tail.prev == head) return null;
        Node last = tail.prev;
        remove(last);
        return last;
    }
}

/* -------- LRU Cache -------- */
class LRUCache {
    private final int capacity;
    private final Map<Integer, Node> map = new HashMap<>();
    private final DLinkedList dll = new DLinkedList();

    LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        dll.moveToFront(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            dll.moveToFront(node);
            return;
        }

        if (map.size() == capacity) {
            Node evicted = dll.removeLast();
            map.remove(evicted.key);
        }

        Node node = new Node(key, value);
        dll.addToFront(node);
        map.put(key, node);
    }
}

/* -------- Demo -------- */
public class Demo {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 10);
        cache.put(2, 20);
        System.out.println(cache.get(1)); // 10

        cache.put(3, 30); // evicts key 2
        System.out.println(cache.get(2)); // -1
        System.out.println(cache.get(3)); // 30
    }
}
