package org.penistrong.offeroriented.part2.hashtable;

import java.util.List;

/**
 * 剑指Offer2-035 最小时间差
 */
public class OfferOriented_2_035 {

    public int findMinDifference(List<String> timePoints) {
        // 以分钟数计算，一天一共24*60=1440分钟，直接用哈希表存(数组充当哈希表)
        boolean[] minutes = new boolean[1440];
        for (String time: timePoints) {
            String t[] = time.split(":");
            int minute = Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
            if (minutes[minute])
                return 0;
            minutes[minute] = true;
        }
        // 在有序的哈希表上寻找距离最近的时间
        int minDiff = minutes.length - 1;
        // prev记录上个时间节点
        int first = minutes.length - 1, last = -1, prev = -1;
        for (int i = 0; i < minutes.length; i++) {
            if (minutes[i]) {
                if (prev >= 0)  // 每扫描到一个新的时间节点，和上一个时间节点计算时间差并更新minDiff
                    minDiff = Math.min(i - prev, minDiff);
                prev = i;
                first = Math.min(i, first); // 更新碰到的时间节点最小值
                last = Math.max(i, last);   // 更新碰到的时间节点最大值
            }
        }
        // 要考虑跨过一天计算时间差的情况，用最小值加上一天的分钟数再减去最大值即可
        minDiff = Math.min(first + minutes.length - last, minDiff);
        return minDiff;
    }
}
