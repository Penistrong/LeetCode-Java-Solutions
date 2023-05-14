package org.penistrong.offeroriented.part2.array;

/**
 * 剑指Offer2-009 乘积小于k的子数组
 */
public class OfferOriented_2_009 {

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        // 利用滑动窗口，找乘积小于k的连续子数组
        // 这道题无法利用前缀和+二分查找，因为连续积会特别大而超出类型表示范围(或者使用对数和，取对数后前缀积就被转换为前缀对数和)
        long product = 1;   // 用滑窗的话product的最大值为 k x max{nums}，不可能会溢出
        int left = 0;
        int count = 0;      // 记录满足条件的子数组个数
        for (int right = 0; right < nums.length; right++) {
            product *= nums[right];
            // 当[left, right]间的连续子数组之积大于等于k时，移动左指针，直到积小于k
            while (left <= right && product >= k) {
                product /= nums[left++];
            }
            // 此时[left, right]间的连续子数组以**nums[left]开头**的连续子集都满足乘积小于k
            // 比如[left,...,right-1]、[left,...,right-2]以此类推，一共right - left + 1个
            count += right - left + 1;
        }
        return count;
    }
}
