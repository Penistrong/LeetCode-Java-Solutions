package org.penistrong.offeroriented.part2.linklist;

import org.penistrong.template.list.ListNode;

/**
 * 剑指Offer2-023 两个链表的第一个重合节点
 */
public class OfferOriented_2_023 {
    // 要求O(N)时间复杂度，O(1)空间复杂度，所以不能使用栈

    // 解法1: 构造环，然后利用Floyd判圈法找到环的入口节点
    // 即，通过先遍历其中一个链表，直到尾部节点，然后将尾部节点同另一个链表的头节点相连接
    // 再利用快慢指针法找到环的入口节点
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 遍历A，然后将A的尾节点链接到B的头节点，构造环
        ListNode node = headA;
        while (node.next != null)
            node = node.next;
        node.next = headB;
        // 然后快慢指针都指向headA，开始判环
        ListNode slow = headA, fast = headA;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;    // 第一次相遇，打破循环
        }
        if (slow != fast) {
            node.next = null;   // 题目要求返回结果时链表需要保持其原始结构，所以此处断开构造的环
            return null;        // 说明此时不存在环，两个链表不相交
        }
        // slow挪回头节点，两个指针以相同步长移动
        slow = headA;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        node.next = null;
        return slow;
    }

    // 解法2: 统计每个链表的长度，然后根据差值，让更长的那个链表的遍历指针提前走差值步，再一起走，每一步判断地址是否相等即可
    public ListNode getIntersectionNodeSolution2(ListNode headA, ListNode headB) {
        int countA = countNodeNum(headA);
        int countB = countNodeNum(headB);
        ListNode longerP = countA > countB ? headA : headB;
        ListNode shorterP = countA > countB ? headB : headA;
        int diff = Math.abs(countA - countB);
        while (diff-- > 0) {
            longerP = longerP.next;
        }
        while (shorterP != null && longerP != null) {
            if (shorterP == longerP) return shorterP;
            else {
                shorterP = shorterP.next;
                longerP = longerP.next;
            }
        }
        return null;
    }

    private int countNodeNum(ListNode head) {
        int count = 0;
        while (head != null) {
            head = head.next;
            count++;
        }
        return count;
    }
}
