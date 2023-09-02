package org.penistrong.leetcode.enumeration;

/**
 * LeetCode 2240 买钢笔和铅笔的方案数
 * 名不符实的medium难度题
 */
public class LeetCode2240 {
    public long waysToBuyPensPencils(int total, int cost1, int cost2) {
        long plans = 0;
        int num1 = total / cost1;
        for (int i = 0; i <= num1; i++) {
            plans += (total - i * cost1) / cost2 + 1;   // 枚举可以买的铅笔个数，0个也囊括在内
        }
        return plans;
    }
}
