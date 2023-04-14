package org.penistrong.leetcode.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全排列加强版
 */
public class LeetCode47 {
    public List<List<Integer>> permuteUnique(int[] nums) {
        // 数组中有重复字符，返回所有不重复的全排列，
        List<List<Integer>> permutations = new ArrayList<>();
        List<Integer> permute = new ArrayList<>();  // 每个排列
        Arrays.sort(nums);  // 先对nums排序，方便后续筛选掉相邻的重复数字
        // 为了保证不会出现重复的全排列
        // 从排序后的nums里从左到右找到第一个还没有被填过的数字
        // 用一个boolean数组表示是否可视化
        boolean[] vis = new boolean[nums.length];
        dfs(0, nums, vis, permute, permutations);
        return permutations;
    }

    private void dfs(int pos, int[] nums, boolean[] vis,
                     List<Integer> permute, List<List<Integer>> permutations)
    {
        if (pos == nums.length) {   // 找到一个不重复的排列
            permutations.add(new ArrayList<>(permute));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            // 如果前面某个元素被用过说明vis[i]==true, 直接跳过寻找下一个元素
            // 当vis[i]==false, 向右继续搜索所有相等的元素，同时要满足前一个元素都是未使用过的
            if (vis[i] || (i > 0 && nums[i] == nums[i - 1] && !vis[i-1])) {
                continue;
            }
            permute.add(nums[i]);
            vis[i] = true;  // nums[i]表明已经用过索引为i的元素了
            dfs(pos + 1, nums, vis, permute, permutations);
            vis[i] = false; // 回溯，下一次循环填入下一个数字
            permute.remove(pos);
        }
    }
}
