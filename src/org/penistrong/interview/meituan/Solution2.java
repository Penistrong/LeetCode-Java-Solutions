package org.penistrong.interview.meituan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<Integer> heights = new ArrayList<>();
        for (int i = 0; i < n; i++)
            heights.add(in.nextInt());

        // 丑陋值为相邻装饰品的高度差的绝对值，想要最小化丑陋值
        // 按高度大小进行排序
        List<Integer> decos = heights.stream().sorted().collect(Collectors.toList());
        // 计算差分
        int[] diff_height = new int[n - 1];
        int ugly_val = 0;
        for (int i = 0; i < n - 1; i++) {
            diff_height[i] = Math.abs(decos.get(i + 1) - decos.get(i));
            ugly_val += diff_height[i];
        }
        System.out.println(ugly_val);
    }
}
