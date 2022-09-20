package org.example;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private class LinkedListNode {
        K key;
        V value;

        LinkedListNode next;
        LinkedListNode prev;

        LinkedListNode() {
        }

        LinkedListNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, LinkedListNode> nodeMap;

    private final LinkedListNode head;
    private LinkedListNode tail;

    private void dropNode(LinkedListNode node) {
        assert node != head;

        LinkedListNode prev = node.prev;
        LinkedListNode next = node.next;

        prev.next = next;
        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }

        assert prev.next != node && (next == null || next.prev != node);
    }

    private void addNode(LinkedListNode node) {
        assert node != null;

        LinkedListNode oldTail = tail;

        tail.next = node;
        node.prev = tail;
        tail = node;

        assert tail.prev == oldTail;
    }

    public LRUCache(int capacity) {
        assert capacity > 0 : "Capacity should be positive";

        this.capacity = capacity;
        this.nodeMap = new HashMap<>();

        this.head = new LinkedListNode();
        this.tail = this.head;
    }

    public V get(K key) {
        assert key != null;
        final int mapSize = nodeMap.size();

        LinkedListNode oldNode = nodeMap.get(key);

        if (oldNode == null) {
            return null;
        }

        LinkedListNode node = new LinkedListNode(key, oldNode.value);
        dropNode(oldNode);
        addNode(node);
        nodeMap.put(key, node);

        assert mapSize == nodeMap.size();

        return node.value;
    }

    public void put(K key, V value) {
        assert key != null && value != null;
        final int mapSize = nodeMap.size();

        LinkedListNode node = new LinkedListNode(key, value);

        LinkedListNode oldNode = nodeMap.get(key);
        if (oldNode != null) {
            dropNode(oldNode);
        } else {
            if (nodeMap.size() >= capacity) {
                nodeMap.remove(head.next.key);
                dropNode(head.next);
            }
        }
        nodeMap.put(key, node);
        addNode(node);

        assert (mapSize + 1 == nodeMap.size() || mapSize == nodeMap.size()) && nodeMap.size() <= capacity;
    }
}
