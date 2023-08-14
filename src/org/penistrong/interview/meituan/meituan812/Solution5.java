package org.penistrong.interview.meituan.meituan812;

import java.util.*;

/**
 * 小美得到一棵树，每个节点初始都为白色，如果两个有边连接的节点其权值的乘积刚好为完全平方数，就可以染红这两个节点
 * 求可染红的最大节点数
 * 输入:
 * 第一行一个n，表示树的节点个数
 * 第二行n个正整数a_i，代表每个节点的权值
 * 其后一共n - 1行，每行包含两个数u和v，代表u和v之间有一条边
 * 范围限制:
 * 1 <= n <= 1e5
 * 1 <= a_i <= 1e9
 * 1 <= u,v <= n
 */
public class Solution5 {
    public static void main(String[] args) {
        new Solution5().solve();
    }

    // 节点个数
    int n;
    // 存储树中节点的权值
    int[] a;
    // 存储树中节点的连接关系(如果用数组存储还需要扫描节点u对应的一行中有哪些节点v对应的列为1)
    Map<Integer, List<Integer>> graph;
    // dp[i][0]表示不染红第i号节点时的子树中红节点的个数
    // dp[i][1]表示染红第i号节点时的子树中红节点个数(包括其本身)
    int[][] dp;

    public void solve() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }
        graph = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }
        // 保证是树了, 所以一定不会出现图的情况, 但是一个树可能有多于2个子节点
        for (int i = 0; i < n - 1; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        dp = new int[n + 1][2];
        // 从节点1开始，且此时父节点不存在标明为-1
        tree_dfs(1, -1);
    }

    /**
     * 树形DP，在图上
     * @param node 当前DFS的节点编号
     * @param parent 当前DFS节点的父节点(因为给出的数据没有标明节点之间的父子关系，将遍历到的边的出点作为父节点)
     */
    public void tree_dfs(int node, int parent) {
        // 先DFS到没有子节点的叶子结点处(利用父节点编号忽略叶子节点到父节点之间的边)
        for (int child : graph.get(node))
            if (child != parent)
                tree_dfs(child, node);
        // 边界情况: 当前节点没有子节点(包括其唯一子节点是父节点的情况), 将该子节点染红则以该node号节点的子树中有1个红节点
        if (graph.get(node).isEmpty() || graph.get(node).get(0) == parent) {
            dp[node][1] = 1;
        }
        // 不染色情况: 当前节点不染色时，以当前节点为根的子树中取其直接子节点里 不染红/染红 情况的最大值
        for (int child : graph.get(node)) {
            if (child != parent) {
                dp[node][0] += Math.max(dp[child][0], dp[child][1]);
            }
        }
        // 染色情况: 当前节点染色时，则遍历每个可能的子节点情况，更新当前节点染红时的最优解
        for (int child : graph.get(node)) {
            if (child != parent && matchCondition(a[child], a[node])) {
                // 如果要将当前节点从不染色情况改为染色情况就需要子节点也处于不染色情况，才能将二者一起染色
                // 从当前节点的子节点中挑选一个，和当前节点一起染色(其他子节点则不能和当前节点一起被染色)
                // dp[node][0]为当前节点不染色时，所有子节点中每个以子节点为根的子树中最多染色节点的个数
                // 减去子节点 染红/不染红 情况的最大值再加上子节点 不染红 情况的值(即减掉子节点染红所导致的子树中染红总数的差值, 将子节点染红情况复原到不染红情况)
                // +2 表示将当前节点和当前选择的子节点一起染红后，新增了2个染红节点
                int nodeStained = dp[node][0] - Math.max(dp[child][0], dp[child][1]) + dp[child][0] + 2;
                // 更新每个子节点可以形成的更优解
                dp[node][1] = Math.max(dp[node][1], nodeStained);
            }
        }
    }

    public boolean matchCondition(int u, int v) {
        if (u <= 0 || v <= 0) return false;
        long multi = (long) u * v;
        int sq = (int) Math.sqrt(multi);
        return multi == (long) sq * sq;
    }
}
