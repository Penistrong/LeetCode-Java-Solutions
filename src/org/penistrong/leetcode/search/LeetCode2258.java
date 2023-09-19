package org.penistrong.leetcode.search;

import java.util.*;

/**
 * LeetCode 2258 逃离火灾
 * 两次BFS
 */
public class LeetCode2258 {

    private static final int[] directions = {-1, 0, 1, 0, -1};

    public int maximumMinutes(int[][] grid) {
        // 由于人和火都在动态移动，可以转换思想使用另一种参考方式: 人或火到达每个格子的最短时间
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        List<Integer> humanTime = bfs(grid, queue);
        if (humanTime.get(0) < 0) return -1;        // 人无法到达安全屋

        // 处理火的传播
        queue.clear();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1)
                    queue.offer(new int[]{i, j});
            }
        }
        List<Integer> fireTime = bfs(grid, queue);
        if (fireTime.get(0) < 0) return (int) 1e9;  // 火焰无法扩散到安全屋
        // 火焰到达安全屋的时间与人到达安全屋的时间，这之间的差值
        int diff = fireTime.get(0) - humanTime.get(0);
        if (diff < 0) return -1;
        int humanLeftTime = humanTime.get(1), humanUpTime = humanTime.get(2);
        int fireLeftTime = fireTime.get(1), fireUpTime = fireTime.get(2);
        // 用时间差来判断，如果火和人 到达安全屋左侧 或者 到达安全屋上侧 的时间差刚好为两者到达安全屋的时间差
        // 说明火和人是从同一方向进入安全屋(都从左侧或者都从上侧)，因为人不能碰到火，所以需要错开时间
        // 因为求得是在初始位置的最多停留时间，那么就需要再早1分钟出发，保证火在自己身后一格
        if (humanLeftTime < 0 || humanUpTime < 0 || fireLeftTime - humanLeftTime == diff && fireUpTime - humanUpTime == diff) {
            return diff - 1;
        }
        return diff;
    }

    private List<Integer> bfs(int[][] grid, Queue<int[]> queue) {
        int m = grid.length, n = grid[0].length;
        int[][] time = new int[m][n];   // 记录到达每个格子的时间
        for (int i = 0; i < m; i++)
            Arrays.fill(time[i], -1);
        // 对于在队列中的起始节点，初始时间都是0
        for (int[] coord : queue) {
            time[coord[0]][coord[1]] = 0;
        }
        // 模拟时间步进
        for (int t = 1; !queue.isEmpty(); t++) {
            Queue<int[]> tmp = queue;
            queue = new LinkedList<>();
            while (!tmp.isEmpty()) {
                int[] coord = tmp.poll();
                for (int k = 0; k < 4; k++) {
                    int x = coord[0] + directions[k], y = coord[1] + directions[k + 1];
                    // 当格子没有走过才可以BFS
                    if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == 0 && time[x][y] < 0) {
                        time[x][y] = t;
                        queue.offer(new int[]{x, y});
                    }
                }
            }
        }
        // 安全屋在棋盘右下角
        // 但是要返回到达右下角及其左侧和上侧的时间，以判断火追人问题
        List<Integer> arriveTime = new ArrayList<>();
        arriveTime.add(time[m - 1][n - 1]);
        arriveTime.add(time[m - 1][n - 2]);
        arriveTime.add(time[m - 2][n - 1]);
        return arriveTime;
    }
}
