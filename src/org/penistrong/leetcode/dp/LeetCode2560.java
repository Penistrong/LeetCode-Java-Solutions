package org.penistrong.leetcode.dp;

/**
 * LeetCode 2560 打家劫舍Ⅳ
 * 二分+DP
 */
public class LeetCode2560 {
    public int minCapability(int[] nums, int k) {
        // 窃取过程中最少窃取k间，且不会窃取相邻的房屋
        // 最小化窃取过程中单间房屋的最大值-按照灵茶山艾府的说法，看到最大化最小值或最小化最大值，就要想到如何去二分答案
        // 首先找到二分右端点right
        int left = 0, right = 0;
        for (int num : nums) {
            right = Math.max(right, num);
        }
        // 二分查找-左闭右开写法
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(mid, nums, k)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    /**
     * dp[i]表示从前i座房屋里中偷取金额不超过mid的房屋，且相邻房屋不能窃取，此时的最多房间数
     * 如果最后dp[n] >= k，则说明当前上限mid满足条件，可以继续搜索更小的条件；否则说明mid不满足，需要搜索更大的条件
     */
    private boolean check(int mid, int[] nums, int k) {
        int limit = mid, n = nums.length;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = nums[0] > limit ? 0 : 1;
        for (int i = 2; i <= n; i++) {
            if (nums[i - 1] <= limit) {   // 可选dp[i]时，要考虑偷相邻房屋和偷隔一个房屋+偷本间房屋，哪个更大
                dp[i] = Math.max(dp[i - 1], dp[i - 2] + 1);
            } else {
                dp[i] = dp[i - 1];
            }
        }
        return dp[n] >= k;
    }
}
