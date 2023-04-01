package org.penistrong.interview.meituan;

import java.util.*;

public class Solution1 {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] nums = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
            sum += nums[i];
        }

        int m = in.nextInt();

        // 注意第t_i个加号，其中i下标从1开始, 刚好t_i就是计算 nums[i-1]与nums[i] 之间的操作数
        // 每次操作只是改变初始的全加号式子
        in.nextLine();
        String ops_str = in.nextLine();
        String[] raw_ops = ops_str.split(" ");

        boolean isFirst = true;
        for (int k = 0; k < raw_ops.length; k += 2) {
            float res = 0;
            // 找到优先计算的两个操作数
            int change_op_pos = Integer.parseInt(raw_ops[k]);
            String operator = raw_ops[k + 1];
            int num1 = change_op_pos - 1;
            int num2 = change_op_pos;
            switch (operator) {
                case "+" -> res = nums[num1] + nums[num2];
                case "-" -> res = nums[num1] - nums[num2];
                case "*" -> res = nums[num1] * nums[num2];
                case "/" -> res = (float) nums[num1] / (float) nums[num2];
            }

            float sum_this_turn = ((float) sum) - nums[num1] - nums[num2] + res;
            if (isFirst) { isFirst = false;
            } else System.out.print(" ");
            System.out.printf("%.1f", sum_this_turn);
        }
    }
}
