package org.penistrong.leetcode.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LeetCode77 {
    private List<List<Integer>> combinations;

    public List<List<Integer>> combine(int n, int k) {
        // 组合，而不是排列，即不关注顺序，只要有就行
        // 就是在1~n这n个数字里随意选取k个进行组合，而且要返回组合顺序
        // 回溯DFS即可，回溯后删去count
        combinations = new ArrayList<>();
        int[] combination = new int[k]; // 一共就k个数
        int count = 0;
        dfs(1, n, k, combination, count);
        return combinations;
    }

    private void dfs(int pos, int n, int k, int[] combination, int count) {
        if (count == k) {
            combinations.add(Arrays.stream(combination).boxed().collect(Collectors.toList()));
            return;
        }
        for (int i = pos; i <= n; i++) {
            combination[count++] = i;
            dfs(i + 1, n, k, combination, count);
            count--;    // 回溯当前选择的第count个数字
        }
    }
}
