package org.penistrong.offeroriented.part2.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 剑指Offer2-007 数组中和为0的3个数字
 */
public class OfferOriented_2_007 {
    public List<List<Integer>> threeSum(int[] nums) {
        // 需返回所有和为0的不重复三元组
        List<List<Integer>> triples = new ArrayList<>();
        // 为了方便过滤重复元素，对数组进行排序
        Arrays.sort(nums);  // O(NlogN)
        // 每次固定一个元素，在已排序数组的剩余部分里利用双指针寻找二元组，使其和为固定元素的相反数
        int k = 0;
        while (k < nums.length) {
            int first = nums[k], target = -nums[k];
            int i = k + 1, j = nums.length - 1;
            while (i < j) {
                if (nums[i] + nums[j] == target) {          // 找到一个三元组
                    triples.add(Arrays.asList(first, nums[i], nums[j]));
                    int sameVal = nums[i];
                    while (nums[i] == sameVal && i < j) i++;// 由于已排好序，跳过接下来所有与nums[i]相同的数，向后移动指针i
                } else if (nums[i] + nums[j] < target) i++; // 前指针向后移动
                else j--;                                   // 后指针向前移动
            }
            // 固定的第一个元素找完其所有匹配的三元组后，跳过与固定元素相同的元素
            while (k < nums.length && nums[k] == first) k++;
        }
        return triples;
    }
}
