package org.penistrong.offeroriented.part2.linklist;

import org.penistrong.template.list.ListNode;

/**
 * 剑指Offer2_025 链表中的数字相加
 */
public class OfferOriented_2_025 {

    // 由于是单链表，两个链表之间的加法，模拟加法竖式计算的话，需要从尾部的个位数节点开始相加
    // 那么首先通过反转链表，将个位数、十位数、...、依次反转，然后逐个相加并计算进位
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverseList(l1);
        l2 = reverseList(l2);
        int carry = 0;
        ListNode sentinel = new ListNode(-1), pre = sentinel;
        // 开始计算，如果更短的链表遍历完毕，则后续继续追加更长的链表
        while (l1 != null || l2 != null) {
            int sum = (l1 != null ? l1.val : 0)
                    + (l2 != null ? l2.val : 0)
                    + carry;
            carry = sum / 10;
            pre.next = new ListNode(sum % 10);
            pre = pre.next;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        if (carry != 0) pre.next = new ListNode(carry);
        ListNode reversedRes = sentinel.next;
        sentinel.next = null;
        return reverseList(reversedRes);
    }

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
