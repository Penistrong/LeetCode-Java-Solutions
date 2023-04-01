package org.penistrong.interview.meituan;

import java.util.Scanner;

public class Solution4 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] x = new int[n + 1];   // 每个杯子容纳的水量上限
        int[] y = new int[n + 1];   // 每个杯子的初始水量
        int[] z = new int[n + 1];   // 每个杯子添加1ml水需要消耗的法力值

        for (int i = 1; i <= n; i++)
            x[i] = in.nextInt();
        for (int i = 1; i <= n; i++)
            y[i] = in.nextInt();
        for (int i = 1; i <= n; i++)
            z[i] = in.nextInt();

        int m = in.nextInt();
        int[] q = new int[m + 1];   // 第i次练习需要将第q_i个杯子倒满
        for (int i = 1; i <= m; i++)
            q[i] = in.nextInt();

        // 因为每个杯子消耗的法力不同，对需要倒满的杯子，找到其前序里第一个消耗法力值最小的杯子
        // 同时还需要计算跨越过的杯子总容量
        boolean isFirst = true;
        for (int i = 1; i <= m; i++) {  // q[i]为杯子编号
            if (isFirst) {
                isFirst = false;
            } else System.out.print(" ");

            int cup_id = q[i];
            int capacity = x[cup_id] - y[cup_id];
            int min_val = capacity * z[cup_id];
            if (min_val == 0) { // 这个杯子都不用注水了，直接继续下一趟循环
                System.out.print(min_val);
                continue;
            }
            // 否则对前序所有杯子计算将其倒满并顺延到下个杯子直到所有杯子倒满
            for (int k = cup_id - 1; k >= 1; k--) {
                capacity += x[k] - y[k];
                min_val = Math.min(capacity * z[k], min_val);
            }
            System.out.print(min_val);
        }

    }
}
