package org.penistrong.leetcode.linkedlist;

/**
 * LeetCode287 - 寻找重复数
 * 要求空间复杂度O(1)，且不能对输入数组排序
 * 牛客面经上另一位老哥WeChatPay一面手撕题之一, 要求O(N)+O(1)解法, 不能使用哈希表，不能对数组排序
 */
public class LeetCode287 {

    // 二分解法 - O(NlogN)
    public int findDuplicateVersion1(int[] nums) {
        // 因为不能修改原始数组，也不能使用额外空间，那么所有操作都是在这个数组上进行的
        // nums.length = n + 1
        int left = 1, right = nums.length - 1, duplicate = -1;
        // 每趟在[left, right]范围内二分出mid值，遍历数组，统计值处于[left, mid]的元素个数
        while (left <= right) {
            int count = 0, mid = left + (right - left) / 2;
            for (int num: nums)
                if (num <= mid) ++count;
            if (count <= mid) {  // 说明[left, mid]区间不存在重复元素，下一趟搜索[mid, right]区间
                left = mid + 1;
            } else {             // 说明[left, mid]区间含有重复元素，在这里面二分继续搜索
                right = mid - 1;
                // 在这里就赋值duplicate, 是根据最后一趟结束条件left > right情况
                // 即right赋值为前一个mid-1,导致小于left,说明原来right == left,重复元素就是mid(left/right)
                duplicate = mid;
            }
        }
        return duplicate;
    }

    // 快慢指针法(Floyd判圈法) - O(N)
    // 对nums数组建图，每个下标位置i连一条 i -> nums[i] 的边
    // 因为重复数字duplicate的存在，因此一定会有两个或以上的下标位置指向这个重复数字值
    public int findDuplicateVersion2(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        // 第一次slow和fast相遇后，将slow挪回起始点，尔后两者每次移动相同步长
        // 下次再相遇时就是环的入口节点，即重复的那个数字
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
