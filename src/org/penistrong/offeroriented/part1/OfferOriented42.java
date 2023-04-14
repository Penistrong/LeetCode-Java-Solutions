package org.penistrong.offeroriented.part1;

public class OfferOriented42 {
    /**
     * nums.length最大为1e5, 不好通过前缀和的方式计算
     * 采用动态规划一遍遍历最好
     * dp[i]表示以nums[i]为结尾的子数组最大和(而不是前i元素里的连续子数组最大和)
     * 由于数组存在负数，遍历到第i个元素时，需要判断以i-1个元素结尾的连续子数组和
     * 如果为负数，那么没必要再在dp[i-1]的基础上加上nums[i]，怎样都会比nums[i]单独组成一个子数组的和小
     * dp[i] = nums[i], if dp[i - 1] <= 0
     *         or dp[i-1] + nums[i], if dp[i-1] > 0
     * 最后求max(dp[i])即可(连续子数组可能会在某处断开)
     */
    public int maxSubArray(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len + 1];
        dp[0] = 0;
        int res = Integer.MIN_VALUE;
        for (int i = 1; i <= len; i++) {
            if (dp[i-1] <= 0)
                dp[i] = nums[i-1];
            else
                dp[i] = dp[i - 1] + nums[i-1];
            res = Integer.max(res, dp[i]);
        }
        return res;
    }
}
