package org.penistrong.leetcode.datastructure;

import java.util.HashMap;
import java.util.Map;

/**
 * LFUCache的实现，可以看template包下的LFUCache泛型通用版，这里的key和value固定为Integer
 */
public class LeetCode460 {
    static class LFUCache {

        static class Node {
            int key;
            int value;
            // 计数器 记录该节点使用过的次数，只要对当前节点执行过get或put，计数器就+1
            int freq;
            Node prev;
            Node next;

            public Node() {
                this(-1, -1, 1);
            }

            public Node(int key, int value, int freq) {
                this.key = key;
                this.value = value;
                this.freq = freq;
            }
        }

        // 具有相同使用频率的节点放在一个双向链表里
        // 该链表维护一个LRU顺序，只提供addFirst方法
        // remove时一定只会移除尾节点
        static class LRULinkedList {
            // 利用伪头/尾节点方便处理
            Node dummyHead, dummyTail;
            int size;

            public LRULinkedList() {
                dummyHead = new Node();
                dummyTail = new Node();
                dummyHead.next = dummyTail;
                dummyTail.prev = dummyHead;
                size = 0;
            }

            public Node getHead() {
                return dummyHead.next;
            }

            public Node getTail() {
                return dummyTail.prev;
            }

            public void addFirst(Node node) {
                Node prevHead = dummyHead.next;

                node.prev = dummyHead;
                dummyHead.next = node;

                node.next = prevHead;
                prevHead.prev = node;
                this.size++;
            }

            public void remove(Node node) {
                Node prev = node.prev, next = node.next;
                prev.next = next;
                next.prev = prev;
                this.size--;
            }
        }

        // LFUCache最大容量，超过该容量就要移除最近最不经常使用的节点
        private final int capacity;

        private int minFreq;

        // 利用Map存储键和Node的关系，方便O(1)时间get
        private final Map<Integer, Node> keyTable;

        // 存储使用频率freq与其对应双向链表的映射，这个链表中存放所有使用频率为freq的节点
        // 且每个链表维护LRU顺序(整体LFU, 局部LRU)
        private final Map<Integer, LRULinkedList> freqTable;

        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.keyTable = new HashMap<>();
            this.freqTable = new HashMap<>();
            this.minFreq = 0;
        }

        // 通过键Key在keyTable中找到对应Node, 然后去freqTable中取出其当前freq对应的列表
        public int get(int key) {
            if (capacity == 0 || !keyTable.containsKey(key))
                return -1;

            Node node = keyTable.get(key);
            updateNodeFreq(node);
            return node.value;
        }

        // 要求O(1)时间put
        public void put(int key, int value) {
            if (capacity == 0)
                return;
            Node node;
            LRULinkedList freqNodeList;
            if ((node = keyTable.get(key)) == null) {   // 不存在该键, 新建节点，分别加入keyTable和freqTable中
                if (keyTable.size() >= capacity) { // 超出容量的话移除最久的最不经常使用的节点
                    // 通过minFreq获得freqTable[minFreq]链表中的末尾节点()
                    node = freqTable.get(this.minFreq).getTail();
                    keyTable.remove(node.key);
                    freqTable.get(this.minFreq).remove(node);
                    // 检查minFreq频率对应的链表是否为空，即是否没有节点具有此使用频率，若空则彻底移除链表
                    if (freqTable.get(this.minFreq).size == 0)
                        freqTable.remove(this.minFreq);
                }
                freqNodeList = freqTable.getOrDefault(1, new LRULinkedList());
                node = new Node(key, value, 1);
                freqNodeList.addFirst(node);
                freqTable.put(1, freqNodeList);
                keyTable.put(key, node);
                minFreq = 1;    // 插入了一个新的节点，更新minFreq值
            } else {    // 存在该键，更新值
                node.value = value;
                updateNodeFreq(node);
            }
        }

        // 每次调用get或者put后需要更新节点的使用频率
        // 从旧频率对应的双向链表中取出，并插入到新频率对应的双向链表里
        private void updateNodeFreq(Node node) {
            // 删除旧频率对应的双向链表中的该节点
            int oldFreq = node.freq;
            freqTable.get(oldFreq).remove(node);
            if (freqTable.get(oldFreq).size == 0) {
                freqTable.remove(oldFreq);
                if (minFreq == oldFreq)    // 更新当前保存的最小值
                    minFreq++;
            }

            int newFreq = oldFreq + 1;
            LRULinkedList freqNodeList = freqTable.getOrDefault(newFreq, new LRULinkedList());
            // 更新当前节点的freq, 并插入到对应的双向链表中(LRU头插)
            node.freq = newFreq;
            freqNodeList.addFirst(node);
            freqTable.put(newFreq, freqNodeList);
            keyTable.put(node.key, node);
        }
    }
}
