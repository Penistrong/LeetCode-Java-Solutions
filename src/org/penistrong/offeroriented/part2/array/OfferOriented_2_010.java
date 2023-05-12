package org.penistrong.offeroriented.part2.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 剑指Offer2-010 和为k的子数组
 */
public class OfferOriented_2_010 {

    public int subarraySum(int[] nums, int k) {
        // 一边计算前缀和，一边统计当前缀和为x，前面是否存在前缀和为x - k的某个位置，并计算x - k出现的次数
        int n = nums.length, count = 0, sum = 0;
        Map<Integer, Integer> prefixOccurs = new HashMap<>();
        prefixOccurs.put(0, 1);
        for (int num : nums) {
            // 计算前i个元素的前缀和x=sum
            sum += num;
            // 从Map中获取前面统计的前缀和 x-k 出现的次数 ( x - (x - k) = k )
            count += prefixOccurs.getOrDefault(sum - k, 0);
            prefixOccurs.put(sum, prefixOccurs.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
