package org.penistrong.leetcode.search;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class LeetCode934 {

    private static final int[] direction = {-1, 0, 1, 0, -1};

    public int shortestBridge(int[][] grid) {
        // 建桥的最短路径，即岛与岛之间的最短距离
        // 复合类型的题，首先要找到第一座岛，可以利用任意搜索方式
        // 然后根据第一座岛的所有边界单元格(水域格子)，利用BFS逐级找到第二座岛的任意单元格，更新最短距离
        int row = grid.length, col = grid[0].length;
        Queue<int[]> grids = new LinkedList<>();
        // 首先利用DFS搜索第一座岛屿的边界水域格子，每搜索到一个则加入队列中
        boolean firstGrid = false;  // 找到第一个为1的单元格后，会以它为入口向下DFS
        for (int i = 0; i < row; i++) {
            if (firstGrid) break;
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    firstGrid = true;
                    dfs(grids, i, j, grid);
                    break;
                }
            }
        }
        // 利用队列里的所有第一座岛屿的边界水域格子，进行BFS搜索
        // 每一级level对应填海造陆的一层
        int x, y, level = 0;
        while (!grids.isEmpty()) {
            level++;
            // 对当前队列里的所有level=1的水域格子执行搜索
            int cur_level_grids_num = grids.size();
            while (cur_level_grids_num-- > 0) {
                int[] coord = grids.poll();
                // 查看该水域格子的四个方向的相邻格子
                for (int k = 0; k < 4; k++) {
                    x = coord[0] + direction[k];
                    y = coord[1] + direction[k + 1];
                    if (x >= 0 && x < row && y >= 0 && y < col) {
                        if (grid[x][y] == 2) continue;      // 第一座岛屿的格子(包括逐级填海造陆)
                        if (grid[x][y] == 1) return level;  // 第二座岛屿的陆地格子，当前level即最小的填海造陆格子数
                        grids.offer(new int[]{x, y});       // grid[x][y]==0, 说明又是个水域格子
                        grid[x][y] = 2;                     // 填海造陆，也设置为2
                    }
                }
            }
        }
        return 0;
    }

    private void dfs(Queue<int[]> grids, int i, int j, int[][] grid) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length
                || grid[i][j] == 2)
            return;
        if (grid[i][j] == 0) {  // 碰到第一座岛屿近海的第一个水域格子
            grids.offer(new int[]{i, j});
            return;
        }
        // 将第一个岛屿的陆地单元格标识符置为2，以区分第二座岛屿
        grid[i][j] = 2;
        for (int k = 0; k < 4; k++) {
            int x = i + direction[k], y = j + direction[k + 1];
            dfs(grids, x, y, grid);
        }
    }
}
