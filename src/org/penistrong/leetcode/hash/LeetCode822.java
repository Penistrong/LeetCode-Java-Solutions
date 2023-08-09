package org.penistrong.leetcode.hash;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode822 翻转卡片游戏
 * <p>可以任意翻转卡片，然后选中当前不在任一张卡片正面所写的数字集合中的背面数字
 * <p>找到所有可能中的最小值
 */
public class LeetCode822 {
    public int flipgame(int[] fronts, int[] backs) {
        // 由于只能翻转卡片而不是交换卡片，所以对于正面数字一共只有
        // \sum_{n}^{i=0}C(i, n) = 2^n 种可能
        // 首先可以过滤掉正反两面均相同的卡片，无论怎么翻转该卡片的数组都不可能是最后的数字
        // 然后将所有除此之外的数字选作x(肯定可以作为可能的数字)，找出其中的最小值
        Set<Integer> same = new HashSet<>();
        for (int i = 0; i < fronts.length; i++) {
            if (fronts[i] == backs[i]) same.add(fronts[i]);
        }
        int res = 2001; // x \in [1,2000]
        for (int x : fronts)
            if (!same.contains(x) && x < res)
                res = x;
        for (int x : backs)
            if (!same.contains(x) && x < res)
                res = x;
        return res % 2001;    // 如果是2001则说明没有找到，模2001后返回0
    }
}
