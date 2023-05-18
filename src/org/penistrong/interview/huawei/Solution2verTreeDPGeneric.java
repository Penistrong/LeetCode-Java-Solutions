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
public class Solution2verTreeDPGeneric {

    int[][] children;     // children[i][...]表示第i号节点的儿子编号
    int[] childNums;    // 保存第i号节点的儿子个数
    int[] dp;           // DP[i]表示走到第i号方块上能够获得的最大食物价值
    int[] food;         // 保存对应索引下标编号方块上的食物价值

    public void solution() {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();       // N个方块
        food = new int[N];
        children = new int[N][2];   // 顶多只有2个子节点，children[i][...]表示i号方块的子节点编号
        childNums = new int[N];
        dp = new int[N];
        int child_id, parent_id, root = 0;
        for (int i = 0; i < N; i++) {
            child_id = in.nextInt();
            parent_id = in.nextInt();
            food[child_id] = in.nextInt();
            dp[child_id] = food[child_id];  // dp[i]的初始值就是该方块上的食物价值
            if (parent_id == -1) {          // 如果父方块id为-1, 说明当前方块是根方块(有且只有一个)
                root = child_id;
                continue;
            }
            children[parent_id][childNums[parent_id]++] = child_id;
        }
        // 这道题不是直接输出根节点的最大值，因为每个方块都可以作为子树根节点(对应走到其父方块后手动宣布退出游戏)
        // 一次从根节点发起的树形DP可以更新所有节点的最大食物价值情况(节点到其子树中叶子节点的最大路径价值)
        tree_dp(root);
        // 找到从各个节点出发可以得到的最大值max(dp[0...N-1])
        System.out.println(Arrays.stream(dp).max().getAsInt());
    }

    private void tree_dp(int parent) {
        int maxChildPathSum = 0;    // 记录子树里的最大路径价值, 对于负价值，可以不必更新
        for (int i = 0; i < childNums[parent]; i++) {
            int child_id = children[parent][i];
            tree_dp(child_id);  // DFS先下去，自底向上更新
            maxChildPathSum = Integer.max(maxChildPathSum, dp[child_id]); // 维护子树中的最大路径价值
        }
        // 每个方块一旦走了，那就只能老老实实吃这个价值，不存在状态转移
        dp[parent] = dp[parent] + maxChildPathSum;
    }

    public static void main(String[] args) {
        new Solution2verTreeDPGeneric().solution();
    }
}
