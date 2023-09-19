package org.penistrong.leetcode.dp;

/**
 * LeetCode 72 编辑距离
 * 经典DP，需要设计好最优子结构，找到状态转移方程即可迎刃而解
 */
public class LeetCode72 {
    public int minDistance(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        // dp[i][j]表示word1的前i个字符转换成word2的前j个字符所需的最少操作数
        int[][] dp = new int[len1 + 1][len2 + 1];
        // 对于 i = 0, 即word1的空前缀，word2的每个j都需要word1的空前缀进行j次插入操作才能转换成后者
        // j = 0同理
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }
        // 0(n^2)
        // 1. 从word1的前i个字符删除第i个字符，转换为word1[0..i-1]和word2[0..j]的子问题
        // 2. 在word1的前i个字符后插入第i+1个字符(与word2[j]相同)，转换为word1[0..i]和word2[0..j-1]的子问题
        // 3. 替换word1的第i个字符为word2[j]，转换为word1[0..i-1]和word2[0..j-1]的子问题
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }
        return dp[len1][len2];
    }
}
