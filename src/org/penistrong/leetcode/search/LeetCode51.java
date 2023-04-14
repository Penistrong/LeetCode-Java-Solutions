package org.penistrong.leetcode.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * N皇后问题
 */
public class LeetCode51 {
    public List<List<String>> solveNQueens(int n) {
        // N皇后哈哈哈
        // 我测，要输出所有皇后的位置，直接返回棋盘，这么牛逼吗
        // 而且每一行每一列都只存在一个皇后，且任意对角线上没有皇后
        List<List<String>> solutions = new ArrayList<>();
        if (n == 0) return solutions;
        // 建立棋盘，初始都是'.'空位状态
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(board[i], '.');
        boolean[] column = new boolean[n];
        // 再用2个数组保存每条斜线(西北-东南走向和东北-西南走向)上是否有皇后存在，方便快速判断
        // 每个走向一共有2n-1个斜线
        boolean[] lDiag = new boolean[2 * n - 1], rDiag = new boolean[2 * n - 1];
        dfs(board, 0, n, column, lDiag, rDiag, solutions);
        return solutions;
    }

    private void dfs(char[][] board, int row, int n, boolean[] column,
                     boolean[] lDiag, boolean[] rDiag, List<List<String>> solutions)
    {
        if (row == n) { // 此时棋盘状态满足题意
            List<String> solution = new ArrayList<>();
            for (char[] chars : board) {
                solution.add(String.valueOf(chars));
            }
            solutions.add(solution);
            return;
        }
        // 尝试在当前行上放置皇后，然后DFS继续搜索下一行
        // 遍历当前行中每一列可放置的位置
        for (int i = 0; i < n; i++) {
            // board[row][i]所占据的实际斜线序号有如下计算方式:
            // 对于西北-东南走向斜线: 从左下角的第一条斜线算起，即board[n-1][0]对应lDiag[0]
            // 其余利用 n - row 和 i - 1分别定位行列，加在一起后去lDiag中找对应的即可
            // 对于东北-西南走向斜线: 从左上角的第一条斜线算起，即board[0][0]对应rDiag[0]
            if (column[i] || lDiag[n - row + i - 1] || rDiag[row + i])
                continue;
            // 可以放置
            board[row][i] = 'Q';
            column[i] = lDiag[n - row + i - 1] = rDiag[row + i] = true; // 设置状态
            dfs(board, row + 1, n, column, lDiag, rDiag, solutions);
            column[i] = lDiag[n - row + i - 1] = rDiag[row + i] = false; // 回溯状态
            board[row][i] = '.';
        }
    }
}
