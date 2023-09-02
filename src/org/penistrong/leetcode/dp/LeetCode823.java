package org.penistrong.leetcode.dp;

import java.util.Arrays;

/**
 * LeetCode823 带因子的二叉树
 * 动态规划 + 双指针
 */
public class LeetCode823 {
    /**
     * 每个非叶子节点的值都为它的两个子节点值的乘积，所以肯定是一颗满树
     * arr中整数可以使用任意次数(且必大于1)
     * 以arr[i]为非叶子节点的二叉树，它的所有子节点的值都必小于arr[i]
     * 这样子就变成了动态规划:
     * 对于下标i, dp[i]存储以arr[i]为根节点的带因子二叉树数目, 枚举区间arr[0, i-1)
     * 如果有arr[left] \times arr[right] = arr[i]成立，就可以作为arr[i]的两个子节点
     * 同时因为left, right < i，则可得到dp[left]和dp[right]对应的带因子二叉树数目
     * 即 dp[i] = dp[left] x dp[right] x 2(交换子树顺序)，如果left==right则不用乘2
     */
    public int numFactoredBinaryTrees(int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        long[] dp = new long[n];
        long res = 0, mod = (long) 1e9+7;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;  // 只有arr[i]一个节点作为根时，也可以成为带因子二叉树
            // 双指针 两端向中间搜，因为arr已经排序了，所以判断条件很简单，仅判断是否小于即可
            for (int left = 0, right = i - 1; left <= right; left++) {
                while (right >= left && (long) arr[left] * arr[right] > arr[i]) {
                    right--;
                }
                if (right >= left && (long) arr[left] * arr[right] == arr[i]) {
                    if (right == left) dp[i] = (dp[i] + dp[left] * dp[right]) % mod;
                    else dp[i] = (dp[i] + dp[left] * dp[right] * 2) % mod;
                }
            }
            res = (res + dp[i]) % mod;  // 每计算完毕位置i的dp[i]，就累加到结果里
        }
        return (int) res;
    }
}
