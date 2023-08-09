package org.penistrong.leetcode.dp;

import java.util.Arrays;

/**
 * LeetCode2681 英雄的力量
 * <p>求得nums数组的所有非空子序列作为英雄组，尔后求所有英雄组的力量之和
 * <p>Power of Group := \max{nums_{i_0~i_k}}^2 * \min{nums_{i_0~i_k}}
 */
public class LeetCode2681 {
    public int sumOfPower(int[] nums) {
        // 先对数组排序O(NlogN)，这样子序列中的最小值最大值可以直接通过下标顺序确定
        Arrays.sort(nums);
        // 排序后，以nums[i]结尾的子序列其最大值一定是该nums[i]，所以只需考虑如何找到子序列的**最小值之和**(乘法分配律)
        // 即Power of Groups end with nums[i] := nums[i] * \sum{\min{group_{k}}}
        // dp[j]表示以nums[j]结尾的所有子序列其**最小值**之和
        // 以nums[0...i-1]结尾的各个子序列再加上nums[i]形成的子序列，和长度为1的仅有nums[i]构成的子序列
        // 据此更新以nums[i]为结尾的所有子序列的最小值之和为: dp[i] = nums[i] + \sum_{j=0}^{i-1}dp[j]
        // DP数组按顺序计算完毕后，再次遍历每个下标，计算 (\sum_{i=0}^{n-1} nums[i]^2 * dp[i]) % (10^9 + 7) 即可
        // 由于每一步都需要计算前面所有dp[0...i-1]的和，所以使用前缀和进行优化，减少计算次数
        int len = nums.length;
        int[] dp = new int[len];
        int[] preSum = new int[len + 1];    // preSum[0]悬空，表示前0个, preSum[i]表示前i个dp的和也就是nums[0...i-1](注意不包含nums[i])
        int sum = 0, mod = 1000000007;
        for (int i = 0; i < len; i++) {
            dp[i] = (preSum[i] + nums[i]) % mod;        // update dp
            preSum[i + 1] = (preSum[i] + dp[i]) % mod;  // update preSum
            // 累加每次计算得到的英雄组之力(注意里面使用了乘法取余运算规则, 省略了dp[i] % mod = dp[i]的事实)
            sum = (int) ((sum + (long) nums[i] * nums[i] % mod * dp[i]) % mod);
            if (sum < 0) sum += mod;    // 溢出了
        }

        return sum;
    }
}
