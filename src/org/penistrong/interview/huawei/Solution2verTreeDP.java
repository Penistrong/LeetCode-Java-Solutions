package org.penistrong.interview.huawei;

import java.util.Scanner;

/**
 * 华为开发岗第二道题，其实就是树形DP基础题(没有DP的树形)，还是个二叉树(每个方块上最多有2个传送门可以到达其他节点)
 * 第一行输入N, 表示有N个方块，编号为0...N
 * 接下来的N行每行都是个三元组，i, j, k
 * 分别表示该方块下标i、能通过传送门抵达该方块的父方块下标j、该方块的食物价值k
 * j为-1时，表示当前方块没有其他方块能够通过传送门到达(根方块)
 * [用例示例]
 * 6
 * 0 2 8
 * 1 2 -1
 * 2 3 -2
 * 3 -1 3
 * 4 3 -4
 * 5 4 9
 *
 * 输出 9 解释: 3号方块为根方块，走3->2->0可获得9，走3->4->5也可获得9
 */
public class Solution2verTreeDP {

    int[][] childs;     // childs[i][...]表示第i号节点的儿子编号
    int[] childNums;    // 保存第i号节点的儿子个数
    int[] dp;           // DP[i]表示走到第i号方块上能够获得的最大食物价值

    public void solution() {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();       // N个方块
        int[] food = new int[N];    // 保存对应索引下标编号方块上的食物价值
        childs = new int[N][2];     // 顶多只有2个子节点，childs[i][...]表示i号方块的子节点编号
        childNums = new int[N];
        dp = new int[N];
        int child_id, parent_id, root = 0;
        for (int i = 0; i < N; i++) {
            child_id = in.nextInt();
            parent_id = in.nextInt();
            food[child_id] = in.nextInt();
            dp[child_id] = food[child_id];      // dp[i]的初始值就是该方块上的食物价值
            if (parent_id == -1) {  // 如果父方块id为-1, 说明当前方块是根方块(有且只有一个)
                root = child_id;
                continue;
            }
            childs[parent_id][childNums[parent_id]++] = child_id;
        }
        // 这道题不是直接输出根节点的最大值，因为每个方块都可以作为根节点
        // 一次树形DP可以更新所有节点的最大食物价值情况(节点到其子树中叶子节点的最大路径价值)
        tree_dp(root);
        // 输出dp[0...N-1]的最大值
        int maxTotal = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            maxTotal = Integer.max(maxTotal, dp[i]);
        }
        System.out.println(maxTotal);
    }

    private void tree_dp(int parent) {
        int maxChildPathSum = 0;    // 记录子树里的最大路径价值, 对于负价值，可以不必更新
        for (int i = 0; i < childNums[parent]; i++) {
            int child_id = childs[parent][i];
            tree_dp(child_id);  // DFS先下去，自底向上更新
            // 这道题，每个方块一旦走了，那就只能老老实实吃这个价值，不存在状态转移
            // 维护子树中的最大路径价值，
            maxChildPathSum = Integer.max(maxChildPathSum, dp[child_id]);
        }
        dp[parent] = dp[parent] + maxChildPathSum;
    }

    public static void main(String[] args) {
        new Solution2verTreeDP().solution();
    }
}
