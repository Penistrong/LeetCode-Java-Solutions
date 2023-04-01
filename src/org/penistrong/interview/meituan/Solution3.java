package org.penistrong.interview.meituan;

import java.util.Scanner;

public class Solution3 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();   // 收藏夹数量
        int m = in.nextInt();   // 操作数量
        int[] dirs = new int[n + 1];    // 收藏夹，里面的价值都是0

        int[] ops = new int[m];
        int[] x = new int[m];
        int[] y = new int[m];
        for (int i = 0; i < m; i++)
            ops[i] = in.nextInt();
        for (int i = 0; i < m; i++)
            x[i] = in.nextInt();
        for (int i = 0; i < m; i++)
            y[i] = in.nextInt();

        // op_i=0为更新操作，会将x_i([1, n])编号的收藏夹欣赏程度更新为y_i([0, 1e4])
        // op_i=1为欣赏操作
        boolean isFirst = true;
        for (int i = 0; i < m; i++) {
            if (ops[i] == 0) {  // 更新
                dirs[x[i]] = y[i];
            } else { // 计算区间和
                int sum = 0;
                for (int k = x[i]; k <= y[i]; k++)
                    sum += dirs[k];
                if (isFirst) {
                    isFirst = false;
                } else System.out.print(" ");
                System.out.print(sum);
            }
        }
    }

}
