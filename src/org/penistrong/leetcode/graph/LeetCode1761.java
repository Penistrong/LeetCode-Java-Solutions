package org.penistrong.leetcode.graph;

/**
 * LeetCode1761 一个图中连通三元组的最小度数
 * 暴力枚举，简单剪枝，时间复杂度O(n^3)，空间复杂度O(n^2)
 */
public class LeetCode1761 {
    public int minTrioDegree(int n, int[][] edges) {
        if (n <= 2) return -1;
        boolean[][] connected = new boolean[n + 1][n + 1];  // 记录节点i与节点j之间是否有边
        int[] degrees = new int[n + 1];                     // 记录每个节点的度数
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            connected[u][v] = connected[v][u] = true;
            ++degrees[u];
            ++degrees[v];
        }
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (!connected[i][j]) continue; // 提前判断，减少循环次数
                for (int k = j + 1; k <= n; k++) {
                    // 判断三元组是否两两有边
                    if (connected[i][k] && connected[j][k]) {
                        res = Math.min(res, degrees[i] + degrees[j] + degrees[k] - 6);
                    }
                }
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}
