package org.penistrong.interview.huawei;

import java.util.Arrays;
import java.util.Scanner;

/**
 * T2.获取最多的食物
 * 主办方设计了一个获取食物的游戏
 * 游戏的地图由N个方格组成，每个方格上至多2个传送门，通过传送门可将参与者传送至指定的其它方格
 * 同时，每个方格上标注了三个数字:
 * (1) 第一个数字id: 代表方格的编号，从0到N-1，每个方格各不相同
 * (2) 第二个数字parent-id: 代表从编号为parent-id的方格可以通过传送门传送到当前方格
 *     注意: -1则表示没有任何方格可以通过传送门传送到此方格，这样的方格在地图中有且仅有一个
 * (3) 第三个数字value: 取值在[-100，100]的整数值
 *     - 正整数代表参与者得到相队取值单位的食物
 *     - 负整数代表失去相应数值单位的食物(参与者可能存在临时持有食物为负数的情况)，
 *     - 0代表无变化
 * 此外，地图设计时保证了参与者不可能到达相同的方格两次，并且至少有一个方格的value是正整数
 * 游戏开始后，参与者任意选择一个方格作为出发点，当遇到下列情况之一退出游戏:
 * (1) 参与者当前所处的方格无传送门
 * (2) 参与者在任意方格上主动宣布退出游戏
 * 请计算参与者退出游戏后，最多可以获得多少单位的食物
 * 时间限制: C/C++ 1300ms, 其他语言:2600ms
 * 内存限制: C/C++256MB, 其他语言:512MB
 * 输入描述:
 * 第一行:方块个数N (N<10000)
 * 尔后是N行，每行3个数字i,j,k，依次表示 当前方格编号i、能通过传送门抵达该方块的父方块下标j、当前方格食物价值k
 * [用例示例]
 * 6
 * 0 2 8
 * 1 2 -1
 * 2 3 -2
 * 3 -1 3
 * 4 3 -4
 * 5 4 9
 * 输出: 9
 * 解释: 3号方块为根方块，走3->2->0可获得9，走3->4->5也可获得9
 */
public class Solution2verTreeDPSpecial {

    // 经典树形DP题，而且还限制了每个根节点的儿子数量为2，且只存在一个父方格编号为-1(即没有父节点)的最顶层根节点
    // 注意题目中给出了一个条件 **不可能到达相同的方格两次**，就说明这就是一颗二叉树，而不是图
    // 建树后直接利用DP计算每个节点向其可达叶子节点
    public void solution() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        // nodes[i][]表示第i个节点，node[i][0]表示当前节点的父节点编号，node[i][1]表示当前节点的价值
        int[][] nodes = new int[n][2];
        for (int i = 0; i < n; i++) {
            int id = in.nextInt();
            nodes[id][0] = in.nextInt();
            nodes[id][1] = in.nextInt();
        }
        // 创建dp数组, dp[i]表示以第i号节点为路径结尾时，可以获得的最大价值
        // 对于第i号节点，可以选择从其父节点传送过来获取当前节点食物，或者不传送过来，那么状态转移方程为
        // dp[i] = max(food_i + dp[parent_of_i], food_i)
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MIN_VALUE);
        for (int i = 0; i < n; i++) {   // 利用DFS进行树形DP
            dfs(i, nodes, dp);
        }
        // 然后对dp[]数组寻找最大值
        System.out.println(Arrays.stream(dp).max().getAsInt());
    }

    private int dfs(int i, int[][] nodes, int[] dp) {
        if (dp[i] != Integer.MIN_VALUE) return dp[i];   // 第i号节点被更新过，直接返回
        if (nodes[i][0] == -1) return nodes[i][1];      // 当前节点是根节点，直接返回食物价值
        dp[i] = Math.max(nodes[i][1],
                         nodes[i][1] + dfs(nodes[i][0], nodes, dp));
        return dp[i];
    }

    public static void main(String[] args) {
        new Solution2verTreeDPSpecial().solution();
    }
}
