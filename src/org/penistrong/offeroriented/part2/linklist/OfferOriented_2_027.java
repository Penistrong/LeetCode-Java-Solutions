package org.penistrong.offeroriented.part2.linklist;

import org.penistrong.template.list.ListNode;

/**
 * 剑指Offer2-027 回文链表
 */
public class OfferOriented_2_027 {

    public boolean isPalindrome(ListNode head) {
        if (head == null) return true;
        // 利用快慢指针直接找到中间节点，然后反转前半链表，再依次进行处理
        ListNode slow = head, fast = head;
        // 注意这里用的不是while fast and fast.next，是为了将slow定位到前半部分最后一个链表节点上
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode part2 = slow.next;
        slow.next = null;
        // 找到中点后，如果fast不为null，说明原链表为奇数长度，那么前半部分一定比后半部分长1或者长度相等
        // 所以这里要反转后半链表即可
        ListNode pre = null, tmp = null;
        while (part2 != null) {
            tmp = part2.next;
            part2.next = pre;
            pre = part2;
            part2 = tmp;
        }

        part2 = pre;
        // 依次比较head和part2各个节点是否相同, 由于前半部分大于等于后半部分的长度，所以挨个比较直到后半部分链表消耗完毕即可
        while (head != null && part2 != null) {
            if (head.val != part2.val) return false;
            head = head.next;
            part2 = part2.next;
        }
        return true;
    }
}
