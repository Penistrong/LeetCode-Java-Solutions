package org.penistrong.interview.huawei;

public class Solution519 {

    public void solution() {
        int[] nums = new int[]{1,4,2,5,5,1,6};
        int cnt = 13;
        if (isAvailable(nums, Integer.MAX_VALUE, cnt)) {
            System.out.println(-1);
            return;
        }
        int left = 0, right = 100000;   // 没给出数据范围，假定[0, 1e5]
        // 二分阈值，查找区间，直到区间长度只剩一个元素，此时应该是满足最大条件的limit
        // 左开右闭写法: (left, right]，最终收缩到left == right
        while (left < right) {
            // 计算区间中点，注意偶数区间长度下取靠右的中间点，满足左开右闭
            int mid = (left + right + 1) >> 1;
            if (isAvailable(nums, mid, cnt)) left = mid;
            else right = mid - 1;
        }
        System.out.println(left);
    }

    // 检查给定阈值limit的情况下，数组里超过该阈值的数降至该阈值，并检查最终总和是否小于cnt
    private boolean isAvailable(int[] nums, int limit, int cnt) {
        int sum = 0;
        for(int num : nums) {
            sum += Math.min(num, limit);
        }
        return cnt >= sum;
    }

    public static void main(String[] args) {
        new Solution519().solution();
    }
}
