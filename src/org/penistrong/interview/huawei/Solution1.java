package org.penistrong.interview.huawei;

import java.util.Arrays;
import java.util.Scanner;

public class Solution1 {
    public static void main(String[] args) {
        // 上游系统的前缀和
        Scanner in = new Scanner(System.in);
        // 直接开10^5
        int[] R = new int[100000];
        int n = 0;
        String raw_r = in.nextLine();
        for (String r: raw_r.split(" ")) {
            R[n++] = Integer.parseInt(r);
        }
        int cnt = Integer.parseInt(in.nextLine());

        // 通过设置最大的阈值，使得所有上游系统都能够正常调用，且总和不超过cnt
        // 如果有n个系统，而总调用量cnt小于n, 那么无论怎么限制都是0
        int limit = 0;
        if (n > cnt) {
            System.out.println(limit);
            return;
        }
        // 排序后再计算前缀和
        int[] sorted_R = Arrays.copyOf(R, n);
        Arrays.sort(sorted_R);
        long[] prefix_sum = new long[n + 1];
        int truncated_idx = 0;
        for(int i = 1; i <= n; i++) {
            prefix_sum[i] = prefix_sum[i - 1] + sorted_R[i - 1];
            // 一旦发现当前prefix_sum[i] > cnt， 则要开始嘎了
            if (prefix_sum[i] > cnt) {
                truncated_idx = i - 1;
                break;
            }
        }

        // 此时前truncated_idx个数的和小于cnt，加上truncated_idx就不满足了
        // 在前缀和逆序寻找 前面的前缀和
        for (int j = truncated_idx; j >= 0; j--) {
            // 寻找区间 [sortedR_{j-1}, sortedR_{j}]之间的lim,使得总体调用量小于cnt
            for (long lim = sorted_R[j] - 1; lim >= ((j >= 1) ? sorted_R[j - 1] : 0); lim--) {
                if (prefix_sum[j] +  (n - j) * lim <= cnt) {
                    // 直接return最大的limit
                    System.out.println(lim);
                    return;
                }
            }
        }
        System.out.println(limit);
    }
}
