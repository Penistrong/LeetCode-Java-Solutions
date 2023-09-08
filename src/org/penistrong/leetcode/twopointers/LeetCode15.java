package org.penistrong.leetcode.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 15 三数之和
 * 排序+双指针
 * 由于需要处理重复的三元组，所以需要先排序，然后在双指针的过程中跳过重复的元素
 * 总得来说细节很多，得谨慎处理，考察编码功底
 * 美团一面，没写出来，还是太菜了
 */
public class LeetCode15 {
    public List<List<Integer>> threeSum(int[] nums) {
        // 不可以包含重复的三元组
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        int size = nums.length;
        for (int i = 0; i < size; i++) {
            if (i > 0 && nums[i] == nums[i - 1])    // 跳过重复的a
                continue;
            int target = -nums[i];                  // 固定a
            int left = i + 1, right = size - 1;     // 双指针找二元组
            for (; left < size; left++) {
                if (left > i + 1 && nums[left] == nums[left - 1])
                    continue;
                // 固定left指针的情况下，缩小right指针，同时保证left始终在right指针左侧
                while (left < right && nums[left] + nums[right] > target)
                    right--;
                // 如果是因为双指针重合导致找不到，说明即使把left指针右移也不会再有满足和为target的二元组，提前结束
                if (left == right) break;
                // 直到二元组的和刚好为target的时候
                if (nums[left] + nums[right] == target) {
                    List<Integer> triple = new ArrayList<>();
                    triple.add(nums[i]);
                    triple.add(nums[left]);
                    triple.add(nums[right]);
                    ans.add(triple);
                }
            }
        }
        return ans;
    }
}
