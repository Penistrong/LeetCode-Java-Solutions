package org.penistrong.leetcode.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基础回溯，没使用剪枝，可以查看同题型: 剑指Offer38-字符串排列
 */
public class LeetCode46 {
    public List<List<Integer>> permute(int[] nums) {
        // 对于数组中的每个位置，可以与其后面的任意位置交换，然后继续向后处理
        // 思想是基于DFS的回溯，当搜索完成后返回调用栈后需要修改节点状态
        List<List<Integer>> permutations = new ArrayList<>();
        dfs(nums, 0, permutations);
        return permutations;
    }

    private void dfs(int[] nums, int pos, List<List<Integer>> permutations) {
        if (pos == nums.length - 1) {
            // 此时数组就是一个满足条件的排列，直接加入List中
            // 注意 Arrays.asList(T... a)返回的是List<T>，而int不能作为泛型，所以会被解析为List<int[]>
            // 使用Arrays.stream(int[]).boxed自动装箱，再collect即可
            permutations.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
            return;
        }
        for (int i = pos; i < nums.length; i++) {
            swap(nums, i, pos);                 // 交换nums[i]与nums[pos]
            dfs(nums, pos + 1, permutations);   // 进入下一层DFS寻找排列
            swap(nums, i, pos);                 // 回溯
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
