package org.penistrong.leetcode.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode417 {
    private static final int[] direction = {-1, 0, 1, 0, -1};
    private boolean[][] can_reach_pacific;
    private boolean[][] can_reach_atlantic;

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        // 返回的result[i] = [r_i, c_i]
        // 表示单元格heights[i][j]里的雨水既可以流向太平洋也可以流向大西洋
        // 显然右上角和左下角这两个格子既可以流入太平洋也可以流入大西洋
        // 如果对每个单元格都执行搜索，显然复杂度有点高
        // 如果从太平洋和大西洋的海岸出发，各自搜索所有单元格是否能到达己身
        if (heights == null || heights.length == 0 || heights[0].length == 0)
            return new ArrayList<>();
        int row = heights.length, col = heights[0].length;
        can_reach_pacific = new boolean[row][col];
        can_reach_atlantic = new boolean[row][col];
        // 从四条边出发，各自对应从海洋出发，逆向寻找更高的单元格
        for (int i = 0; i < row; i++) {
            dfs(heights, can_reach_pacific, i, 0);       // 邻接太平洋的左-边
            dfs(heights, can_reach_atlantic, i, col - 1);  // 邻接大西洋的右-边
        }
        for (int j = 0; j < col; j++) {
            dfs(heights, can_reach_pacific, 0, j);       // 邻接太平洋的上-边
            dfs(heights, can_reach_atlantic, row - 1, j);  // 邻接大西洋的下-边
        }
        // 找到两个海洋都可以到达的单元格
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (can_reach_pacific[i][j] && can_reach_atlantic[i][j]){
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        return result;
    }

    private void dfs(int[][] heights, boolean[][] can_reach_ocean, int i, int j) {
        if (can_reach_ocean[i][j])
            return;
        can_reach_ocean[i][j] = true;
        int x, y;
        // 搜索每个单元格4个方向上的相邻单元格
        for (int k = 0; k < 4; k++) {
            x = i + direction[k];
            y = j + direction[k + 1];
            if (x >= 0 && x < heights.length && y >= 0 && y < heights[0].length
                    && heights[i][j] <= heights[x][y])
                dfs(heights, can_reach_ocean, x, y);
        }

    }
}
