package org.penistrong.offeroriented.part2.linklist;

import org.penistrong.template.list.ListNode;

/**
 * 剑指Offer2-022 - 链表中环的入口节点
 * 快慢指针法-Floyd判圈法的应用
 */
public class OfferOriented_2_022 {

    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null; // 特判情况，是为了放置快慢指针只有一个节点时的情况
        // 无环时返回null
        // 利用Floyd判圈法/快慢指针判圈
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;    // 相遇，打破循环
        }
        if (slow != fast) return null;  // 因为无环而导致fast提前走到链表末尾，这种情况slow!=fast且不存在环的入口节点
        // 相遇后，将slow挪回头节点
        slow = head;
        // 接下来步长相同
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        // 相遇时即为环的入口节点
        return slow;
    }
}
