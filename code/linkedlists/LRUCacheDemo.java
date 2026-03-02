package code.linkedlists;

import java.util.HashMap;
import java.util.Map;

// Eviction Policy (Strategy)
interface EvictionPolicy<K> {
    void keyAccessed(K key);
    K evictKey();
}

// Concrete Strategy: LRU
class LRUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final DoublyLinkedList<K> dll = new DoublyLinkedList<>();
    private final Map<K, Node<K>> keyNodeMap = new HashMap<>();

    @Override
    public void keyAccessed(K key) {
        if (keyNodeMap.containsKey(key)) {
            dll.moveToFront(keyNodeMap.get(key));
        } else {
            Node<K> node = new Node<>(key);
            dll.addToFront(node);
            keyNodeMap.put(key, node);
        }
    }

    @Override
    public K evictKey() {
        Node<K> last = dll.removeLast();
        if (last == null) return null;
        keyNodeMap.remove(last.key);
        return last.key;
    }
}

// Concrete Strategy: LFU (stub for extensibility)
class LFUEvictionPolicy<K> implements EvictionPolicy<K> {
    @Override
    public void keyAccessed(K key) {
        // TODO: track frequency counts here
    }
    @Override
    public K evictKey() {
        return null; // TODO: evict least frequently used
    }
}

// Cache Interface
interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
}

// Cache Implementation (uses Strategy)
class GenericCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private final Map<K, V> store = new HashMap<>();
    private final EvictionPolicy<K> evictionPolicy;

    public GenericCache(int capacity, EvictionPolicy<K> evictionPolicy) {
        this.capacity = capacity;
        this.evictionPolicy = evictionPolicy;
    }

    @Override
    public V get(K key) {
        if (!store.containsKey(key)) return null;
        evictionPolicy.keyAccessed(key);
        return store.get(key);
    }

    @Override
    public void put(K key, V value) {
        if (store.size() == capacity && !store.containsKey(key)) {
            K evictKey = evictionPolicy.evictKey();
            if (evictKey != null) {
                store.remove(evictKey);
            }
        }
        store.put(key, value);
        evictionPolicy.keyAccessed(key);
    }
}

// Factory for Cache creation
class CacheFactory {
    public static <K, V> Cache<K, V> create(String type, int capacity) {
        return switch (type.toUpperCase()) {
            case "LRU" -> new GenericCache<>(capacity, new LRUEvictionPolicy<>());
            case "LFU" -> new GenericCache<>(capacity, new LFUEvictionPolicy<>());
            default -> throw new IllegalArgumentException("Unknown cache type: " + type);
        };
    }
}

// Doubly Linked List Node
class Node<K> {
    K key;
    Node<K> prev, next;
    Node(K key) { this.key = key; }
}

// Doubly Linked List
class DoublyLinkedList<K> {
    private final Node<K> head, tail;

    DoublyLinkedList() {
        head = new Node<>(null);
        tail = new Node<>(null);
        head.next = tail;
        tail.prev = head;
    }

    public void moveToFront(Node<K> node) {
        remove(node);
        addToFront(node);
    }

    public void addToFront(Node<K> node) {
        Node<K> nextNode = head.next;
        head.next = node;
        node.prev = head;
        node.next = nextNode;
        nextNode.prev = node;
    }

    public void remove(Node<K> node) {
        if (node.prev != null && node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public Node<K> removeLast() {
        if (tail.prev == head) return null;
        Node<K> last = tail.prev;
        remove(last);
        return last;
    }
}

// Demo
public class LRUCacheDemo {
    public static void main(String[] args) {
        Cache<Integer, String> cache = CacheFactory.create("LRU", 3);

        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        System.out.println(cache.get(1)); // Access A → keeps it fresh

        cache.put(4, "D"); // Evicts key 2
        System.out.println(cache.get(2)); // null
        System.out.println(cache.get(3)); // "C"
        System.out.println(cache.get(4)); // "D"
    }
}
