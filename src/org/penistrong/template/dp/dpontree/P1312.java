package org.penistrong.template.dp.dpontree;

import java.util.Scanner;

/** 洛谷P1352
 * 没有上司的舞会
 * 某大学有n个职员，编号为1...n
 * 他们之间存在从属关系，父节点是子节点的直接上司
 * 现在拟举办一场舞会，每邀请一个职员都会增加一定的快乐指数r_i
 * 但是如果该职员的直接上司也来参加舞会，那么该职员就无论如何也不愿意参加舞会了(限制条件)
 * 请计算，如何邀请职员使得快乐指数最大，求最大的快乐指数
 * [输入格式]
 * 第1行是一个整数n
 * 第2～(n+1)行，每行一个整数，第(i+1)行的整数代表i号职员的快乐指数r_i
 * 第(n+1)~(2n+1)行，每行输入一个数对l,k，表示k是l的直接上司
 * [数据规模约束]
 * n \in [1, 6e3], r_i \in [-128, 127], 1 <= l,k <= n
 * 给出的关系一定是一棵树
 */
public class P1312 {
    // 树形DP经典题，这道题是从叶向根传递更新
    // 但是这个树不是二叉树，因为可能有超过2个以上的职员，拥有相同的上司(父节点)
    // 使用DFS递归到叶子节点，再自底向上更新DP树
    int[][] childs;     // 记录第i号的职员他的直接下属都有谁
    int[] childNums;    // 记录第i号的职员他的直接下属个数
    boolean[] isChild;  // 记录第i号职员是否是儿子节点(服务于找根节点(顶头上司))

    // dp[i][0]表示第i号职员不参加舞会时的最大快乐指数
    // dp[i][1]表示第i号职员参加舞会时的最大快乐指数
    int[][] dp;

    // 树形DP核心函数
    // 当前dfs到第x号节点
    public void tree_dp(int parent) {
        for (int i = 1; i <= childNums[parent]; i++) {
            int child = childs[parent][i];
            tree_dp(child); // 自底向上，所以一直递归到最深处的儿子节点处
            // 状态转移方程: dp[parent][0]表示第parent号职员不参加舞会，那么他的儿子就可以参加也可以不参加
            // 所以用max比较儿子参加或者不参加的快乐指数哪个更大
            dp[parent][0] = dp[parent][0] + Integer.max(dp[child][0], dp[child][1]);
            // 当前第parent号职员参加舞会，那么他儿子就只能不参加舞会了
            dp[parent][1] = dp[parent][1] + dp[child][0];
        }
    }

    public void solution() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        childs = new int[n + 1][n + 1];
        childNums = new int[n + 1];
        isChild = new boolean[n + 1];
        dp = new int[n + 1][2];
        int[] r = new int[n + 1];   // 编号从1开始

        for (int i = 1; i <= n; i++) {
            r[i] = in.nextInt();
            dp[i][1] = r[i];        // 快乐指数r_i存入dp[i][1]，如果第i号职员要参加舞会，那么他的初始快乐指数就是r_i
        }

        int l, k;
        for (int i = 1; i <= n; i++) {
            l = in.nextInt();
            k = in.nextInt();
            childs[k][++childNums[k]] = l;  // 记录k跟l的父子关系，然后计数器++
            isChild[l] = true;              // 将l标记为儿子节点
        }
        // 找没有父亲的根节点
        int root = 0;
        for (int i = 1; i <= n; i++) {
            if (!isChild[i]) {
                root = i;
                break;
            }
        }
        // 从根节点开始DFS
        tree_dp(root);
        // 最后比较顶头上司参加或者不参加的情况下的最大快乐指数
        System.out.println(Integer.max(dp[root][0], dp[root][1]));
    }
    public static void main(String[] args) {
        new P1312().solution();
    }
}
