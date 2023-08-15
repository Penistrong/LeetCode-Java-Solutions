package org.penistrong.leetcode.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode56 {
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0)
            return new int[0][2];
        // 先按照左端点大小排序所有区间
        Arrays.sort(intervals, (interval1, interval2) -> interval1[0] - interval2[0]);
        List<int[]> merged = new ArrayList<>();
        int prevIntervalRight;
        for (int[] interval : intervals) {
            int left = interval[0], right = interval[1];
            // 当合并的区间数组为空 或者 上一个区间的右端点小于当前遍历区间的左端点时，加入新区间
            if (merged.isEmpty() || (prevIntervalRight = merged.get(merged.size() - 1)[1]) < left) {
                merged.add(new int[]{left, right});
            } else {    // 如果上一个区间的右端点大于当前遍历区间的左端点，则比较两个右端点大小，更新上一个区间的右端点
                merged.get(merged.size() - 1)[1] = Math.max(prevIntervalRight, right);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }
}
