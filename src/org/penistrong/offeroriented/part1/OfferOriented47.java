package org.penistrong.offeroriented.part1;

/**
 * 47. 礼物的最大价值
 * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。
 * 你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。
 * 给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
 */
public class OfferOriented47 {
    public int maxValue(int[][] grid) {
        // 二维DP，不断更新即可，边界价值都是0
        // dp[i][j] = max{dp[i-1][j] + grid[i][j], dp[i][j-1] + grid[i][j]}
        if (grid == null || grid[0].length == 0)
            return 0;
        int row = grid.length, col = grid[0].length;
        int[][] dp = new int[row + 1][col + 1];
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                dp[i][j] = Integer.max(dp[i-1][j] + grid[i-1][j-1], dp[i][j-1] + grid[i-1][j-1]);
            }
        }
        return dp[row][col];
    }
}
