package org.penistrong.offeroriented.part2.array;

import java.util.Arrays;

/**
 * 剑指Offer2-008 和大于等于target的最短子数组
 */
public class OfferOriented_2_008 {

    // 双指针滑动窗口 - O(N)解法
    public int minSubArrayLen(int target, int[] nums) {
        // 找出最短的连续子数组，使其和大于等于一个正整数target
        // 运用双指针，初始指针都指向数组的起始位置，计算两个指针之间的子数组之和
        int left = 0;
        int sum = 0, minLen = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            // 判断当前子数组和是否大于等于target，更新子数组长度，并向右移动左指针
            while (left <= right && sum >= target) {
                minLen = Integer.min(minLen, right - left + 1);
                sum -= nums[left++];    // 移动左指针，子数组和减去左边删去的元素
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    // 前缀和+二分查找 - O(NlogN)解法
    // 由于输入保证是正整数数组，因此可以用二分查找在递增的前缀和数组中寻找目标
    public int minSubArrayLenSolution2(int target, int[] nums) {
        int n = nums.length;
        int[] prefix_sum = new int[n + 1];  // 前缀和数组
        for (int i = 1; i <= n; i++)
            prefix_sum[i] = prefix_sum[i-1] + nums[i - 1];
        int minLen = Integer.MAX_VALUE;
        // 遍历每个位置上的前缀和，在后序区间里搜索大于等于target+当前前缀和的其他位置的前缀和，这样两个位置上前缀和的差值就大于等于target
        for (int i = 1; i <= n; i++) {
            int aim_prefix = target + prefix_sum[i - 1];    // 注意是prefix_sum[i-1]，因为当前搜索位置nums[i]不能在前序前缀和里
            int bound = Arrays.binarySearch(prefix_sum, aim_prefix);    // 找到第一个大于等于目标前缀和的下标
            if (bound < 0) bound = -bound - 1;              // - (插入点) - 1,
            if (bound <= n) {   // 找到一个目标下标
                minLen = Integer.min(minLen, bound - i + 1);
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
