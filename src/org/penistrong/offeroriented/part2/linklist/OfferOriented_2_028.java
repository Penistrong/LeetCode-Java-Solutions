package org.penistrong.offeroriented.part2.linklist;

/**
 * 剑指Offer2-028 展平多级双向链表
 */
public class OfferOriented_2_028 {

    // 同级双向链表节点中除了包含指向前驱/后继节点的指针
    // 还包含指向下一级双向链表头结点的指针child
    // 展平时，采用DFS的方式
    // 一旦某个节点的child指针不为空，就去下一级链表里进行遍历
    static class Node {
        public int val;
        public Node prev, next;
        public Node child;

        public Node (int val) {
            this.val = val;
        }
    }

    public Node flatten(Node head) {
        if (head == null) return null;
        Node sentinel = new Node(-1);
        Node pre = sentinel, cur = head;
        while (cur != null) {
            pre.next = cur;
            cur.prev = pre;
            Node curLevelNext = cur.next;
            // 存在下一级双向链表，DFS到下一级中
            if (cur.child != null) {
                Node nextLevelHead = flatten(cur.child);
                cur.child = null;   // 下一级双向链表已被扁平化
                cur.next = nextLevelHead;
                nextLevelHead.prev = cur;
                // 然后遍历到末尾，再链接当前级别的下一个节点
                while (cur.next != null)
                    cur = cur.next;
                cur.next = curLevelNext;
                // 当前级别链表节点的下一个节点有可能为空，因此要添加一个判空操作以链接前序节点
                if (curLevelNext != null) curLevelNext.prev = cur;
            }
            pre = cur;
            cur = cur.next;
        }
        sentinel.next.prev = null;  // 断开实际头节点同哨兵的前序指针
        return sentinel.next;
    }

    public void test() {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.prev = head;
        head.child = new Node(3);
        Node flattenResult = flatten(head);
    }

    public static void main(String[] args) {
        new OfferOriented_2_028().test();
    }
}
