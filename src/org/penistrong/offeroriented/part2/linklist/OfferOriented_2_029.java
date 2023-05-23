package org.penistrong.offeroriented.part2.linklist;

/**
 * 剑指Offer2-029 排序的循环链表
 * 循环单调非递减链表，给定的初始节点并不一定时链表中最小元素的指针
 * 注意返回的节点仍是作为参数传入的节点
 */
public class OfferOriented_2_029 {

    static class Node {
        public int val;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    }

    public Node insert(Node head, int insertVal) {
        Node insertNode = new Node(insertVal);
        if (head == null) {
            insertNode.next = insertNode;
            return insertNode;
        }
        // 在循环链表中找到插入点, 有两种可能
        // Ⅰ. 给定节点值已大于目标值，那么一直循环找到最小元素所在节点
        int preMax = head.val;
        Node cur = head.next, minNode = head, maxNode = head;   // cur从给定的下个节点开始
        while (cur != head) {   // 注意循环结束条件是不等于输入节点，即不会无限循环而越过给定的起始点
            if (cur.val >= preMax) {
                preMax = cur.val;
                maxNode = cur;
            }
            cur = cur.next;
        }
        minNode = maxNode.next;
        // 处理特殊情况-两端越界情况，直接在maxNode后面执行插入
        // 1. 插入值小于等于最小元素
        // 2. 插入值大于等于最大元素
        if (insertVal <= minNode.val || insertVal >= maxNode.val){
            maxNode.next = insertNode;
            insertNode.next = minNode;
            return head;
        }
        // Ⅱ. 给定节点值小于等于目标值，循环找到插入位置
        cur = minNode;
        Node pre = maxNode;
        while (cur.val < insertVal) {
            pre = cur;
            cur = cur.next;
        }   // 结束循环时，pre是最后一个小于目标值的节点，cur是第一个大于等于目标值的节点
        pre.next = insertNode;
        insertNode.next = cur;
        return head;    // 返回原节点
    }

    public void test() {
        Node head = new Node(3);
        //head.next = head;
        head.next = new Node(3);
        head.next.next = head;
        insert(head, 0);
    }

    public static void main(String[] args) {
        new OfferOriented_2_029().test();
    }
}
