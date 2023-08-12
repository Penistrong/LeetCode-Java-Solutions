package org.penistrong.interview.meituan.meituan812;

import java.util.Scanner;

/**
 * 小美得到一棵树，每个节点初始都为白色，如果两个有边连接的节点其权值的乘积刚好为完全平方数，就可以染红这两个节点
 * 求可染红的最大节点数
 * 输入:
 * 第一行一个n，表示树的节点个数
 * 第二行n个正整数，代表每个节点的权值
 * 其后一共n - 1行，每行包含两个数u和v，代表u和v之间有一条边
 * 范围限制:
 * 1 <= n <= 1e5
 * 1 <= weight <= 1e9
 * 1 <= u,v <= n
 */
public class Solution5 {
    // 注意类名必须为 Main, 不要有任何 package xxx 信息
    public class Main {
        public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            int n = in.nextInt();
            int[] a = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                a[i] = in.nextInt();
            }
            boolean[][] isConnected = new boolean[n + 1][n + 1];
            // 保证是树了, 所以一定不会出现图的情况, 但是一个树可能有多于2个子节点
            for (int i = 0; i < n - 1; i++) {
                int u = in.nextInt();
                int v = in.nextInt();
                isConnected[u][v] = true;
                isConnected[v][u] = true;
            }
            // 找到所有叶子节点(即不存在其他对向边的节点，只有u<->v)
            // dp[i][0]表示不染红第i号节点时的子树中红节点的个数
            // dp[i][1]表示染红第i号节点时的子树中红节点个数(包括其本身)
            int[][] dp = new int[n + 1][2];


            // 暴力一下
            int sum = 0;
            boolean[] isRed = new boolean[n + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (isConnected[i][j] && !isRed[i] && !isRed[j]) {
                        long multi = a[i] * a[j];
                        if(Math.sqrt(multi) * Math.sqrt(multi) == multi) {
                            sum += 2;
                            isRed[i] = true;
                            isRed[j] = true;
                        }
                    }
                }
            }
            System.out.println(sum);
        }

        public static void tree_dp(int parent) {

        }
    }
}
