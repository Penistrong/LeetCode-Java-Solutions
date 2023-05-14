package org.penistrong.offeroriented.part2.array;

/**
 * 剑指Offer2-006 排序数组中两个数字之和
 */
public class OfferOriented_2_006 {

    public int[] twoSum(int[] numbers, int target) {
        // 由于数组已升序排列，那么利用双指针指向一头一尾，向中间查找和为target的目标数
        int i = 0, j = numbers.length - 1;
        while (i < j && numbers[i] + numbers[j] != target) {
            if (numbers[i] + numbers[j] < target) ++i;
            else --j;
        }
        // 最后停止时i, j即为下标值
        return new int[]{i, j};
    }
}
