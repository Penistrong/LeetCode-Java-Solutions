package org.penistrong.offeroriented.part2.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 剑指Offer2-011 0和1个数相同的子数组
 */
public class OfferOriented_2_011 {

    public int findMaxLength(int[] nums) {
        // 原想法
        // 数组中数字不是0就是1
        // 利用前缀和，遍历到每个位置上计算前缀和，然后判断区间里的前缀和差值是否满足等于区间长度的一半

        // 前缀和+哈希表优化
        // 将数组中的0视为-1, 计算前缀和，并使用哈希表记录每种前缀和出现的位置
        // 当出现重复前缀和时，当前下标和哈希表中存储的旧前缀和出现位置下标之间的子数组(左开右闭)刚好满足具有相同数量的0和1(对原数组而言)
        // 这样就可以更新最长子数组的长度
        int n = nums.length;
        int sum = 0, maxLen = 0;
        Map<Integer, Integer> prefixOccurs = new HashMap<>();   // 映射 <前缀和，出现位置>
        prefixOccurs.put(0, -1);    // 初始化时，前缀为空时其结束下标为-1(针对前缀数组刚好满足条件的情况，否则无法计入)
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) sum++;
            else sum--;                             // nums[i]=0时将其视为-1,作用在前缀和上即sum--
            if (prefixOccurs.containsKey(sum)) {    // 以前遍历时出现过该前缀和
                int prevPos = prefixOccurs.get(sum);
                maxLen = Integer.max(maxLen, i - prevPos);  // 子数组范围为[prevPos + 1, i]，因此长度为i - prevPos
            } else prefixOccurs.put(sum, i);        // 由于求的是最长子数组，即使前缀和重新出现也不再更新其出现位置
        }

        return maxLen;
    }
}
