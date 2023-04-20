package org.penistrong.leetcode.linkedlist;

import org.penistrong.template.list.ListNode;

public class LeetCode25 {

    // 这道题上次腾讯开发岗笔试第一题类似，当时是2个节点一组插入到前面的链表节点后面
    public ListNode reverseKGroup(ListNode head, int k) {
        // 局部反转链表很简单
        // 先统计链表长度
        ListNode pList = head, sentinel = new ListNode(-1);
        int len = 0;
        while (pList != null) {
            len++;
            pList = pList.next;
        }
        // 统计要反转的分段数segment
        int segment = len / k;
        // 处理每个分段的反转
        ListNode pre = null, tmp = null, headPre = sentinel;
        pList = head;
        for (int i = 0; i < segment; i++) {
            int count = k;  // count 记录反转次数
            pre = null;     // 每一个分片里的初始pre都是null
            while (count > 0) {
                tmp = pList.next;
                pList.next = pre;
                pre = pList;
                pList = tmp;
                count--;
            }
            // 现在pre指向当前分片中的头节点，比如 1 -> 2 -> 3 -> 4 : 1 <- 2 <- 3 | 4, pre是3, pList是4
            headPre.next = pre;
            count = k;
            // pre挪到最后一个节点那
            while (count > 1) {
                pre = pre.next;
                count--;
            }
            headPre = pre; // 保存上一次分片中倒置后的最后一个节点
        }
        // segment完成倒置后，后面还有数量小于k的剩余链表(此时指向tmp, tmp若为空即刚好不剩)，直接缀到后面
        headPre.next = tmp;
        return sentinel.next;
    }

    public ListNode reverseKGroupSolution2(ListNode head, int k) {
        // 其实可以不用先统计长度，直接跑就行了
        ListNode sentinel = new ListNode(-1);
        sentinel.next = head;
        ListNode pre = sentinel, end = sentinel;

        // 每一趟中，将end往后挪k次，挪到最后一个节点上，然后翻转[start, end]这段链表即可
        while (end.next != null) {
            for (int i = 0; i < k && end != null; i++)
                end = end.next;
            if (end == null)    // 这一趟不满足k个，不用倒置了
                break;
            ListNode start = pre.next;
            ListNode nextSegment = end.next;    // 要断开end和后面的节点了，先行保存
            end.next = null;
            pre.next = reverseList(start);      // 将断尾的[start, end]分片执行反转
            start.next = nextSegment;           // 这个时候start指向的是反转后的尾部节点

            pre = start;
            end = pre;                          // end与pre都指向本次分片中反转后的尾部节点，便于下次循环
        }
        return sentinel.next;
    }

    public ListNode reverseList(ListNode head) {
        // 基础的反转链表
        ListNode pre = null, curr = head;
        while (curr != null) {
            ListNode tmp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = tmp;
        }
        return pre;
    }
}
