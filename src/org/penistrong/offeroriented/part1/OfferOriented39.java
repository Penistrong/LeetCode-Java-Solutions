package org.penistrong.offeroriented.part1;

public class OfferOriented39 {

    public int majorityElement(int[] nums) {
        return mooreNotesMethod(nums);
    }

    // 使用摩尔投票法找到数组中占有半数以上的数字(强众数)
    // 记众数的票数为+1,非众数票数为-1,由于众数的定义，一定有所有数字的票数和 > 0
    // 子问题: 如果数组的前a个数字的票数和=0, 则数组剩余的(n-a)个数字的票数和仍 > 0(即剩余部分的众数没变)
    public int mooreNotesMethod(int[] nums) {
        // 每当票数和为0，就可以缩小统计区间，将剩余部分的首个元素假设为众数
        int major = 0, votes = 0;
        for (int num : nums) {
            if (votes == 0) major = num;        // 当前是剩余部分首元素，更新众数假设
            votes += (num == major) ? 1 : -1;
        }
        return major;
    }
}
