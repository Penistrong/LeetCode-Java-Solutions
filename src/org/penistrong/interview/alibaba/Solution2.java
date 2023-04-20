package org.penistrong.interview.alibaba;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution2 {

    private int n, m;
    private static final int[] direction = {-1, 0, 1, 0, -1};

    private int[][] states;
    private char[][] color_grid;
    private int[][] score_grid;

    private int scores;

    public void solution() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        states = new int[n][m];
        color_grid = new char[n][m];
        score_grid = new int[n][m];

        in.nextLine();
        for (int i = 0; i < n; i++) {
            String[] color_score = in.nextLine().split(" ");
            for (int j = 0; j < m; j++) {
                color_grid[i][j] = color_score[j].charAt(0);
                score_grid[i][j] = Integer.parseInt(color_score[j].substring(1));
            }
        }
        int q = Integer.parseInt(in.nextLine());
        List<int[]> query = new ArrayList<>();
        for (int k = 0; k < q; k++) {
            String[] ij = in.nextLine().split(" ");
            query.add(new int[]{Integer.parseInt(ij[0]), Integer.parseInt(ij[1])});
        }

        for (int k = 0; k < q; k++) {
            scores = 0;
            int[] ij = query.get(k);
            int i = ij[0] - 1;
            int j = ij[1] - 1;
            dfs(i, j, color_grid[i][j]);
            System.out.println(scores);
            update();   // 更新棋盘状态
        }
    }

    public void dfs(int i, int j, char color) {
        // 本轮访问过的，准备消除的，前几轮为空的，这些格子都不做判断
        if (states[i][j] == -1 || states[i][j] == 1 || states[i][j] == 2)
            return;
        states[i][j] = -1;  // 先置本轮当前访问格子的状态为-1
        if (color_grid[i][j] == color) {    // 当前访问格子颜色匹配本轮颜色
            scores += score_grid[i][j];
            states[i][j] = 1;               // 标记为本轮待消除
        }
        for (int k = 0; k < 4; k++) {
            int x = i + direction[k], y = j + direction[k + 1];
            if (x >= 0 && x < n && y >= 0 && y < m)
                dfs(x, y, color);
        }
    }

    private void update() {
        // 每轮点击结束后，首先复原所有为-1的格子(被访问过，但是没有消除)
        for (int i = 0 ; i < n; i++)
            for (int j = 0; j < m; j++)
                if (states[i][j] == -1) states[i][j] = 0;
        // 遍历所有本轮要消除的格子，将他们上方所有格子落下
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < m; j++) {
                if (states[i][j] == 1) {
                    // 当前格子要被消除，向上找到本列没有被消除的格子
                    // ERROR in Exam: 我当时，把这个行标赋成了列表，我测我自己我真是个大怨种
                    int row = i, pos = i;   // row为行标, pos为可以放置的行标(每落下一个pos--)
                    for (; row >= 0; row--) {
                        if (states[row][j] == 0) {
                            color_grid[pos][j] = color_grid[row][j]; // 更新对应颜色
                            score_grid[pos][j] = score_grid[row][j]; // 更新对应分数
                            states[pos][j] = 0;
                            pos--;
                        }
                        states[row][j] = 2;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new Solution2().solution();
    }
}
