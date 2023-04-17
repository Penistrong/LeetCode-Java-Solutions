package org.penistrong.offeroriented.part1;

import org.penistrong.template.list.ListNode;

import java.util.HashSet;

/**
 * 两个链表的第一个公共节点
 * 如果两个链表没有交点，返回null
 * O(N)时间复杂度，O(1)空间复杂度
 * 且链表中没有环
 */
public class OfferOriented52 {

    // 我自己的朴素做法，先遍历一个链表，然后存入HashSet中
    // 但是空间复杂度为O(N)
    ListNode getIntersectionNodeSolution1(ListNode headA, ListNode headB) {
        HashSet<ListNode> nodeSet = new HashSet<>();
        ListNode pA = headA;
        while (pA != null) {
            nodeSet.add(pA);
            pA = pA.next;
        }
        ListNode pB = headB, intersect = null;
        while (pB != null) {
            if (nodeSet.contains(pB)) {
                intersect = pB;
                break;
            }
            pB = pB.next;
        }
        return intersect;
    }

    // Y型链表，且自交点开始，后面所有节点个数都相同
    // 那么可以先统计各自链表的长度，然后长的链表先走差值之后，再逐步挨个比较找到交点
    // 空间复杂度为O(1)，满足题意
    ListNode getIntersectionNodeSolution2(ListNode headA, ListNode headB) {
        int lenA = getLength(headA);
        int lenB = getLength(headB);
        // 找到最长的链表
        ListNode headLonger = headA, headShorter = headB;
        int lenDiff = lenA - lenB;
        if (lenDiff < 0) {
            headLonger = headB;
            headShorter = headA;
            lenDiff = lenB - lenA;
        }
        // 先让最长的链表走lenDiff步
        while (lenDiff-- > 0) {
            headLonger = headLonger.next;
        }
        // 逐个比较
        while (headLonger != null && headShorter != null && headLonger != headShorter) {
            headLonger = headLonger.next;
            headShorter = headShorter.next;
        }
        // 循环结束时，要么是空要么是交点
        return headLonger;
    }

    private int getLength(ListNode head) {
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return len;
    }

    // 省去计算长度差值
    // 双指针一直走，设链表A不相交部分长为a，链表B不相交部分长为b，相交部分长为c
    // 每当两个指针之一走向了null,就把它移到另一个链表的头部继续遍历
    // 当指针A按顺序走了 (List A)a + (List A)c + (List B)b
    // 此时指针B也按顺序走了 (List B)b + (List B)c + (List A)a
    // 走的长度刚好相等，此时刚好是交点位置
    // 如果不相交也是一样，一个走了 m + n，另一个走n + m，同时指向null
    public ListNode getIntersectionNodeSolution3(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }

}
