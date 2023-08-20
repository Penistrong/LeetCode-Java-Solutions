package org.penistrong.leetcode.dp;

/**
 * LeetCode 1388 3n块披萨 Hard
 * 二维动态规划：类似附加了条件的01背包问题
 */
public class LeetCode1388 {
    // 一定是3n块部分组成的披萨
    // 你选择第i块后，Alice会选择第i-1块，Bob会选择第i+1块 (是一个环所以要mod 3n)
    // 直到所有披萨都被选完，返回你选择的披萨块数总和的最大值
    // 直接贪心显然是不行的
    // 采用动态规划，在长度为3n的**普通**序列上选择n个不相邻的数
    // dp[i][j]表示在普通序列的前i个数中选择j个不相邻数的最大和
    // 1. 如果选择了第i个数，则第i-1个数不能选择，即要从前i-2个数中选择j-1个: dp[i][j] = dp[i-2][j-1] + slices[i]
    // 2. 如果不选择第i个数，则从前i-1个数中选择j个: dp[i][j] = dp[i-1][j]
    // 取最大值即可进行转移
    // 对于边界条件，比如dp[i][0]都是0，而dp[i][j]都可转移得来
    // 由于是环状序列，处理环形的话截断:
    // 第一趟动规 不处理序列第一个数，这样最后一个数就可以被选择
    // 第二趟动规 不处理序列最后的数，这样第一个数就可以被选择
    public int maxSizeSlices(int[] slices) {
        int size = slices.length;
        int[] slice_without_head = new int[size - 1];
        int[] slice_without_tail = new int[size - 1];
        System.arraycopy(slices, 1, slice_without_head, 0, size - 1);
        System.arraycopy(slices, 0, slice_without_tail, 0, size - 1);
        return Math.max(funcDP(slice_without_head), funcDP(slice_without_tail));
    }

    private int funcDP(int[] sequence) {
        int N = sequence.length, n = (N + 1) / 3;
        int[][] dp = new int[N + 1][n + 1]; // i \in [0, 3n], j \in [0, n]
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= n; j++) {  // 枚举j \in [1, N/3]，更新dp数组
                if (i >= 2)
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-2][j-1] + sequence[i - 1]);
                else                        // i为1时，由于无法选择前 i-2=-1 个数，因此都是从0转移得来
                    dp[i][j] = Math.max(dp[i-1][j], 0 + sequence[i - 1]);
            }
        }
        return dp[N][n];
    }
}
