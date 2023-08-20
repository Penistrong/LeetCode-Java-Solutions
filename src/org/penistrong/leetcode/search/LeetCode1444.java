package org.penistrong.leetcode.search;

import java.util.Arrays;

/**
 * LeetCode 1444 切披萨的方案数 Hard
 * 二维前缀和+记忆化搜索
 * 也可改为动态规划
 */
public class LeetCode1444 {
    private int rows, cols;
    private MatrixSum ms;
    private int[][][] memories;
    private static final int MOD = (int) 1e9 + 7;

    public int ways(String[] pizza, int k) {
        // 每次切的时候都要保证切出去分配的披萨至少包含一个苹果
        rows = pizza.length;
        cols = pizza[0].length();
        ms = new MatrixSum(pizza);
        // memories[k'][i][j]表示切k'刀时左上角(i, j),右下角固定(rows-1, cols-1)时，切的每一块都包含一个苹果的方案数
        memories = new int[k][rows][cols];
        for (int i = 0; i < k; i++)
            for (int j = 0; j < rows; j++)
                Arrays.fill(memories[i][j], -1);
        // 由于垂直切的时候一定分配左半披萨，水平切的时候一定分配上半披萨
        // 开始切, 利用dfs和记忆化搜索，每次DFS调用中选择水平或者垂直切法，再挑选所有没被切过的位置
        return dfs(k - 1, 0, 0);  // 切k - 1次(step从k-1倒序开始)
    }

    private int dfs(int step, int i, int j) {
        if (step == 0) {    // 搜索到边界
            return ms.query(i, j, rows, cols) > 0 ? 1 : 0;
        }
        if (memories[step][i][j] != -1)
            return memories[step][i][j];    // 返回记忆化搜索过的值
        int res = 0;
        // 垂直切一刀，首先找到isCutCol中最右边的一列true，因为左边在前面的每次切法里都会被分配出去
        for (int col = j + 1; col < cols; col++)
            if (ms.query(i, j, rows, col) > 0)  // 子矩形里有苹果, 准备切左上角为(i, col)的子矩形
                res = (res + dfs(step - 1, i, col)) % MOD;
        // 水平切一刀
        for (int row = i + 1; row < rows; row++)
            if (ms.query(i, j, row, cols) > 0)
                res = (res + dfs(step - 1, row, j)) % MOD;
        return memories[step][i][j] = res;
    }

    // 二维前缀和
    static class MatrixSum {
        private final int[][] sum;

        public MatrixSum(String[] matrix) {
            int rows = matrix.length, cols = matrix[0].length();
            sum = new int[rows + 1][cols + 1];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    sum[i + 1][j + 1] = sum[i + 1][j] + sum[i][j + 1] - sum[i][j] +
                            (matrix[i].charAt(j) == 'A' ? 1 : 0);
                }
            }
        }

        // 返回左上角(x1, y1)和右下角(x2, y2)的子矩阵元素和
        public int query(int x1, int y1, int x2, int y2) {
            return sum[x2][y2] - sum[x2][y1] - sum[x1][y2] + sum[x1][y1];
        }
    }
}
