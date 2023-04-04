package org.penistrong.template.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 手写LRU缓存
 */
public class LRUCache<K, V> {

    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev, next;

        Node (K key, V value, Node<K, V> prev, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    // LRU最大容量
    private int capacity;

    // 快速访问给定键值对，省去链表上的O(N)查找时间
    private Map<K, Node<K, V>> elements;

    // LRU维护的双向链表，手写LRU, head放置刚刚访问的节点，tail为最近最久未访问的节点
    // 注意在JDK8::LinkedHashMap中，tail放置的才是刚刚访问的节点，且是采用尾插法插入节点
    private Node<K, V> head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.elements = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    // 向LRUCache中存放元素，采用头插法
    public void put(K key, V value) {
        Node<K, V> node;
        // 如果插入的元素已存在，更新值并调用get()函数，将该元素挪至头部，表示刚刚访问
        if ((node = elements.get(key)) != null) {
            node.value = value;
            elements.put(key, node);
            this.get(key);  // 维护LRU顺序
            return;
        }
        node = new Node<>(key, value, null, null);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            Node<K, V> first = head;    // first节点引用node插入位置的下一个节点
            node.next = first;
            first.prev = node;
            head = node;                // 更新head为node

            if (elements.size() == capacity) {   // 容量已满，移除最久未访问的tail元素，保证节点个数不超过容量上限
                elements.remove(tail.key);
                tail = tail.prev;
                tail.next = null;
            }
        }
        elements.put(key, node);
    }

    // 访问LRUCache中的元素，并将该元素挪到头部，表示最近已访问
    public V get(K key) {
        Node<K, V> node, first;         // first节点引用node插入位置的下一个节点
        if ((node = elements.get(key)) == null) { // 不存在该键
            return null;
        }
        if ((first = head) != node) {   // 如果head就是node的话不用调整顺序
            Node<K, V> prev = node.prev, next = node.next;
            node.prev = null;
            if (next == null) {         // node.next为空说明node就是tail, 所以将其前驱节点设为尾部
                tail = prev;
            } else {
                next.prev = prev;       // 否则将后驱节点的前驱指针指向node的前驱节点
            }
            if (prev != null) {         // node.prev不为空说明node存在前驱
                prev.next = next;       // 将前驱节点的后驱指针指向node的后驱节点
            } else {
                first = next;           // 否则更新first节点为node的后驱节点
            }
            if (first == null) {        // node插入位置的下一个节点为空，说明node也是尾部tail节点
                tail = node;
            } else {                    // 不为空，双向链接node和first
                node.next = first;
                first.prev = node;
            }
            head = node;                // 最后将head指向node
        }
        return node.value;
    }

    public void printLRUSequence() {
        Node<K, V> node = head;
        System.out.println("Current Inner LRU Sequence:");
        while(node != null) {
            System.out.println("{"+ node.key + " : " + node.value + "}");
            node = node.next;
        }
        System.out.println("------");
    }
}
