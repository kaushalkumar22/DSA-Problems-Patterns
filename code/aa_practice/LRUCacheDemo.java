package code.aa_practice;

import java.util.HashMap;
import java.util.Map;
/*
1. Problem Restatement
We need an LRU (Least Recently Used) Cache with:
	• get(key) → return value or -1 if missing.
	• put(key, value) → insert or update, evict LRU if capacity exceeded.
	• Both must run in O(1) time.

2. Clarify: key/value types, capacity edge cases (0?), and concurrency expectations.

3. Intuition
	• Use HashMap for O(1) lookups.
	• Doubly Linked List for usage order:
		○ Head → most recently used.
		○ Tail → least recently used.
	Why Doubly Linked List because Singly linked list = O(n) to remove middle, doubly linked list = O(1) remove/insert
	given a node pointe

	• On get/put: move node to head.
	• On overflow: evict tail + remove from map.

This approach balances fast lookup with fast eviction.

4. Edge Cases
	• Capacity ≤ 0 → treat as invalid or always miss.
	• Updating existing key → no size increase, just move to head.
	• Must remove from both map and list when evicting.
	• Hash collisions handled by HashMap.
	• Thread safety → can be added with locks or concurrent wrappers if needed.

5. Desing consideration
	• SRP: Separate eviction policy from cache logic.
	• OCP: Support other strategies (LFU, FIFO) via a strategy interface.
	• Factory: Choose eviction policy at construction.

 */
// --- Demo ---
public class LRUCacheDemo {
    public static void main(String[] args) throws Exception {
        Cache cache = CacheFactory.create(3, "LRU");

        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        System.out.println(cache.get(1)); // 1 (keeps it fresh)

        cache.put(4, 4); // Evicts key 2
        System.out.println(cache.get(2)); // -1 (evicted)
        System.out.println(cache.get(3)); // 3
        System.out.println(cache.get(4)); // 4
    }
}

// --- Factory ---
class CacheFactory {
    public static Cache create(int capacity, String type) throws Exception {
        return switch (type.toUpperCase()) {
            case "LRU" -> new CacheImpl(new LRUCacheStrategy(capacity));
            case "LFU" -> new CacheImpl(new LFUCacheStrategy(capacity));
            default -> throw new Exception("Unsupported cache type: " + type);
        };
    }
}

// --- Cache Abstraction ---
interface Cache {
    int get(int key);
    void put(int key, int val);
}

class CacheImpl implements Cache {
    private final EvictionStrategy evictionStrategy;

    public CacheImpl(EvictionStrategy evictionStrategy) {
        this.evictionStrategy = evictionStrategy;
    }

    @Override
    public int get(int key) {
        return evictionStrategy.getVal(key);
    }

    @Override
    public void put(int key, int val) {
        evictionStrategy.addKey(key, val);
    }
}

// --- Strategy Abstraction ---
interface EvictionStrategy {
    void addKey(int key, int val);
    int getVal(int key);
}

// --- LRU Strategy ---
class LRUCacheStrategy implements EvictionStrategy {
    private final int capacity;
    private final DLinkedList dll;
    private final Map<Integer, Node> storage;

    public LRUCacheStrategy(int capacity) {
        this.capacity = capacity;
        this.dll = new DLinkedList();
        this.storage = new HashMap<>(capacity);
    }

    @Override
    public int getVal(int key) {
        if (!storage.containsKey(key)) {
            return -1;
        }
        Node node = storage.get(key);
        dll.moveToFront(node);
        return node.val;
    }

    @Override
    public void addKey(int key, int val) {
        if (storage.containsKey(key)) {
            Node node = storage.get(key);
            node.val = val; // update
            dll.moveToFront(node);
            return;
        }

        if (storage.size() == capacity) {
            int evictKey = dll.removeTail();
            storage.remove(evictKey);
        }

        Node node = new Node(key, val);
        dll.addToFront(node);
        storage.put(key, node);
    }
}

// --- LFU Strategy (stub for extension) ---
class LFUCacheStrategy implements EvictionStrategy {
    public LFUCacheStrategy(int capacity) {}

    @Override
    public void addKey(int key, int val) {
        throw new UnsupportedOperationException("LFU not implemented yet");
    }

    @Override
    public int getVal(int key) {
        throw new UnsupportedOperationException("LFU not implemented yet");
    }
}

// --- Doubly Linked List Support ---
class Node {
    Node next;
    Node prev;
    int key;
    int val;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

class DLinkedList {
    private final Node head;
    private final Node tail;

    public DLinkedList() {
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public void addToFront(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    public void moveToFront(Node node) {
        remove(node);
        addToFront(node);
    }

    public void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public int removeTail() {
        if (tail.prev == head) return -1;
        Node node = tail.prev;
        remove(node);
        return node.key;
    }
}
