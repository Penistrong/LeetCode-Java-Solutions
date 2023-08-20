package org.penistrong.leetcode.dp;

import java.util.PriorityQueue;

/**
 * LeetCode1749 任意子数组和的绝对值的最大值
 * 两种解法: 动态规划/前缀和
 */
public class LeetCode1749 {
    public int maxAbsoluteSum(int[] nums) {
        // 既然是连续子数组的和 的绝对值，那么先使用前缀和计算, 便于后面进行处理
        // 找到前缀和的最大值和最小值:
        // 1. 最大值在最小值右边，说明目标的最大和绝对值就是前缀和最大值减去最小值的差
        // 2. 最大值在最小值左边，说明目标的最大和绝对值就是前缀和最小值减去最大值的差的相反数
        int len = nums.length, min = 0, max = 0;
        int[] prefixSum = new int[len + 1];
        for (int i = 0; i < len; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
            if (prefixSum[i + 1] > max) max = prefixSum[i + 1];
            else if (prefixSum[i + 1] < min) min = prefixSum[i + 1];
        }
        return max - min;
    }

    public int maxAbsoluteSumVerDP(int[] nums) {
        // 只需同时算出最大子数组和 与 最小子数组和的绝对值，二者中取更大的作为答案即可
        // dpMax[i]表示以nums[i]结尾的最大子数组和, dpMin[i]表示以nums[i]结尾的最小子数组和
        // 假设Fn可取\max和\min，则dpFn[i]=Fn(nums[i], dpFn[i-1] + nums[i]) = Fn(0, dpFn[i-1]) + nums[i]
        // 由于只需要前一个位置的dp，所以优化为滚动数组
        int res = 0, dpMax = 0, dpMin = 0;
        for (int num : nums) {
            dpMax = Math.max(dpMax, 0) + num;
            dpMin = Math.min(dpMin, 0) + num;
            res = Math.max(res, Math.max(dpMax, Math.abs(dpMin)));
        }
        return res;
    }
}
