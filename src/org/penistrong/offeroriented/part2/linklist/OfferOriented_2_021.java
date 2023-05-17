package org.penistrong.offeroriented.part2.linklist;

import org.penistrong.template.list.ListNode;

/**
 * 剑指Offer2-021 删除链表中倒数第k个节点
 */
public class OfferOriented_2_021 {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 输入已满足倒数第n个节点是一定存在的
        // fast指针先在链表上走n步，然后slow和fast一起走，直到fast.next指向空，此时slow就是倒数第n+1个节点
        // 删除slow.next即可，注意slow初始指向头节点的前一个节点，保证slow.next是倒数第n个节点
        // 为了处理头节点被删除的情况，设置哨兵
        ListNode sentinel = new ListNode(-1);
        sentinel.next = head;
        ListNode slow = sentinel, fast = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        // 删除slow.next节点
        slow.next = slow.next.next;
        return sentinel.next;
    }
}
