package org.penistrong.offeroriented.part2.linklist;

import org.penistrong.template.list.ListNode;

/**
 * 剑指Offer2-024 反转链表
 * 最基础的链表处理
 */
public class OfferOriented_2_024 {

    public ListNode reverseList(ListNode head) {
        ListNode pre = null, cur = head, tmp;
        while (cur != null) {
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }
}
