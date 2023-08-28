package org.penistrong.leetcode.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 57 插入区间
 */
public class LeetCode57 {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        // 寻找区间插入位置，保证列表中的区间有序、且不重叠
        // 因为一开始给的就是未重叠的区间，直接加新区间加入
        List<int[]> res = new ArrayList<>();
        int left = newInterval[0], right = newInterval[1];
        boolean inserted = false;
        for (int[] interval : intervals) {
            if (interval[1] < left) {         // 当前区间右端点小于插入区间的左端点
                res.add(interval);
            } else if (interval[0] > right) {  // 当前区间左端点大于插入区间的右端点
                if (!inserted) {
                    res.add(new int[]{left, right});
                    inserted = true;
                }
                res.add(interval);
            } else {                                    // 当前区间与插入区间有交集，更新左右端点扩展插入区间
                left = Math.min(left, interval[0]);
                right = Math.max(right, interval[1]);
            }
        }
        // 处理未插入情况，即不存在区间左端点大于插入区间右端点的情况
        if (!inserted) res.add(new int[]{left, right});
        return res.toArray(new int[res.size()][]);
    }
}
