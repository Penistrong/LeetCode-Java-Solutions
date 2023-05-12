package org.penistrong.offeroriented.part2.array;

/**
 * 剑指Offer2-012 左右两边子数组和相等
 */
public class OfferOriented_2_012 {

    public int pivotIndex(int[] nums) {
        // 中心下标定义，其左侧元素和等于右侧元素和，当中心下标位于边界上时，不存在元素的一侧视作和为0
        // 如果有多个中心下标，返回最靠左的那个
        int n = nums.length;
        int[] prefix_sum = new int[n + 1];
        for (int i = 1; i <= n; i++)
            prefix_sum[i] = prefix_sum[i - 1] + nums[i - 1];
        // 前缀和计算完毕后，从左开始寻找所有满足条件的中心下标，只要找到一个就直接返回
        // prefix_sum[i]表示前i个元素的前缀和，因此从i=1开始
        for (int i = 1; i <= n; i++) {
            int leftSum = prefix_sum[i - 1];                // 不考虑nums[i]位置
            int rightSum = prefix_sum[n] - prefix_sum[i];   // i为此时考虑的候选中心下标
            if (leftSum == rightSum) return i - 1;          // 实际对应下标要减-1
        }
        return -1;
    }

}
