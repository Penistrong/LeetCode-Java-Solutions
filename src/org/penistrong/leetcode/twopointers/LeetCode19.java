package org.penistrong.leetcode.twopointers;

import org.penistrong.template.list.ListNode;

public class LeetCode19 {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 快慢指针, 快指针先走n个节点，然后两指针一起向后直到快指针指向链表末端
        ListNode slow = head, fast = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        ListNode sentinel = new ListNode(-1, head);
        ListNode pre = sentinel;
        while (fast != null) {
            fast = fast.next;
            pre = slow;
            slow = slow.next;
        }
        // 断链
        pre.next = pre.next.next;
        return sentinel.next;
    }
}
