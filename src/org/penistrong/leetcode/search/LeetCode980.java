package org.penistrong.leetcode.search;

/**
 * LeetCode 980 不同路径III
 * 经典回溯DFS
 * 相当于一笔画游戏，从起始方格一笔画到结束方格，且障碍方格无需绘出
 * 先找到起点后再DFS(回溯)直到找出所有的不同路径
 */
public class LeetCode980 {
    private static final int[] directions = {-1, 0, 1, 0, -1};

    public int uniquePathsIII(int[][] grid) {
        if (grid.length == 0) return 0;
        int row = grid.length, col = grid[0].length;
        int pathSum = 0, start_x = -1, start_y = -1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 0) pathSum++;
                else if (grid[i][j] == 1) {
                    start_x = i;
                    start_y = j;
                }
            }
        }
        return dfs(grid, start_x, start_y, pathSum + 1);    // 起点没有算到无障碍方格总数里，所以这里也需要加上起点方格
    }

    /**
     * 由于需要保证一笔画过程中访问到所有方格，所以DFS的参数需要增加一个left字段表示还需访问剩余left个无障碍方格
     * 如果访问到当前grid[x][y]=2(即找到终点)，但是left != 0说明没有一笔画成功，此时也需要返回0表示不合法的路径
     * @param grid 方格盘
     * @param x 横坐标x
     * @param y 纵坐标y
     * @param left 剩余无障碍方格的个数
     * @return 0:不合法的路径; 1:找到了一条合法的路径
     */
    private int dfs(int[][] grid, int x, int y, int left) {
        // x/y出边界或者grid[x][y]为障碍，不合法路径返回0
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || grid[x][y] < 0)
            return 0;
        // 当前为终点，但是还有其他无障碍方格没访问
        if (grid[x][y] == 2)
            return left == 0? 1 : 0;
        // 当前方格已访问，标记为障碍，深入DFS后可以复原
        grid[x][y] = -1;
        int cur_path_num = 0, i, j;
        for (int k = 0; k < directions.length - 1; k++) {
            i = x + directions[k];
            j = y + directions[k + 1];
            cur_path_num += dfs(grid, i, j, left - 1);
        }
        // 函数调用栈即将返回，恢复当前方格
        grid[x][y] = 0;
        return cur_path_num;
    }
}
