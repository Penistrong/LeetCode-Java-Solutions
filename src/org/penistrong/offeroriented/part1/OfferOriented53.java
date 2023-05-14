package org.penistrong.offeroriented.part1;

public class OfferOriented53 {
    public int search(int[] nums, int target) {
        // 为了保证速度，用O(log N)的二分查找
        int start = getFirstIndex(nums, 0, nums.length - 1, target);
        if (start == -1) return 0;
        int end = getLastIndex(nums, 0, nums.length - 1, target);
        return end - start + 1;
    }

    private int getFirstIndex(int[] nums, int start, int end, int target) {
        if (start > end) return -1;
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) {
            // 判断当前target是否是第一个该数
            if ((mid > 0 && nums[mid - 1] != target) || mid == 0)
                return mid;
            return getFirstIndex(nums, start, mid - 1, target);
        } else if (nums[mid] > target) {    // 目标在左区间
            return getFirstIndex(nums, start, mid - 1, target);
        } else
            return getFirstIndex(nums, mid + 1, end, target);
    }

    private int getLastIndex(int[] nums, int start, int end, int target) {
        if (start > end) return -1;
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) {
            // 判断当前target是否是最后一个该数
            if ((mid < nums.length - 1 && nums[mid + 1] != target) || mid == nums.length - 1)
                return mid;
            return getLastIndex(nums, mid + 1, end, target);
        } else if (nums[mid] > target) {    // 目标在左区间
            return getLastIndex(nums, start, mid - 1, target);
        } else
            return getLastIndex(nums, mid + 1, end, target);
    }
}
