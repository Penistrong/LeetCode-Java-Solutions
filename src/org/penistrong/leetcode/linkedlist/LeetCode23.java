package org.penistrong.leetcode.linkedlist;

/**
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表
 */
public class LeetCode23 {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        // 两两合并，类似归并排序
        return mergeRecursively(lists, 0, lists.length - 1);
    }

    private ListNode mergeRecursively(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }
        if (left + 1 == right) {
            return merge2List(lists[left], lists[right]);
        }
        int mid = left + (right - left) / 2;
        ListNode leftMerged = mergeRecursively(lists, left, mid);
        ListNode rightMerged = mergeRecursively(lists, mid + 1, right);
        return merge2List(leftMerged, rightMerged);
    }

    public ListNode merge2List(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) return null;
        ListNode sentinel = new ListNode(-1);
        ListNode prev = sentinel;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                prev.next = list1;
                list1 = list1.next;
            } else {
                prev.next = list2;
                list2 = list2.next;
            }
            prev = prev.next;
        }
        if (list1 != null)
            prev.next = list1;
        if (list2 != null)
            prev.next = list2;
        return sentinel.next;
    }
}
