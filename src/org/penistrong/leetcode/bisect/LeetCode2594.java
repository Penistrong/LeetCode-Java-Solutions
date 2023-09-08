package org.penistrong.leetcode.bisect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 2594 修车的最少时间
 */
public class LeetCode2594 {
    /**
     * 不能用动规，复杂度太高
     * 二分查找的应用范围: 解的值域范围内有单调性就可以使用二分
     * 1. 如果t分钟内可以修理所有汽车，那么大于等于t分钟都可以修理所有汽车
     * 2. 如果t分钟内修理不完所有汽车，那么小于等于t分钟也不可以修理完所有汽车
     * 枚举时间t，则能力值为r的工人可以修完\sqrt(t/r)辆车(假设所有工人都在全力工作)
     * 如果所有工人可以修完的汽车数量和大于等于cars，则调整右边界为t，去左半区间查找；否则调整左边界为t+1，去右半区间查找
     */
    public long repairCars(int[] ranks, int cars) {
        // 二分查找左闭右闭写法
        long left = 1, right = (long) ranks[0] * cars * cars;
        while (left < right) {
            long mid = left + right >> 1;
            if (check(ranks, cars, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        // 结束时left为区间终点
        return left;
    }

    // mid为查找的区间中位值, 判断是否满足要求，若满足说明找到了时间上界，去左半区间查找
    public boolean check(int[] ranks, int cars, long mid) {
        long sum = 0;
        for (int rank : ranks) {
            sum += (long) Math.sqrt(mid / rank);
            if (sum >= cars) break; // 提早结束
        }
        return sum >= cars;
    }
}
