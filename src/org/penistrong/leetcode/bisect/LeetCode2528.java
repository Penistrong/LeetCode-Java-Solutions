package org.penistrong.leetcode.bisect;

/**
 * LeetCode 2528 最大化城市的最小供电站数目
 * 二分查找 + 前缀和 + 差分数组 + 贪心
 */
public class LeetCode2528 {
    public long maxPower(int[] stations, int r, int k) {
        // r是固定的，假设一座电站建在城市i处，那么根据它的供电范围，一共有i+2r个城市能被该电站供电
        int n = stations.length;
        long[] prefixSum = new long[n + 1]; // 电站数量的前缀和
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + stations[i];
        }
        long minPower = Long.MAX_VALUE;
        long[] power = new long[n];     // 每座城市实际被供电的量
        for (int i = 0; i < n; i++) {   // 利用前缀和数组，更新第i座城市的实际电量(前后[i - r, i + r]范围里的供电量)
            power[i] = prefixSum[Math.min(i + r + 1, n)] - prefixSum[Math.max(i - r, 0)];
            minPower = Math.min(minPower, power[i]);    // 更新所有城市里最低的电量(供电站数目)
        }

        // 以初始minPower为二分查找的区间左端点，同时以某个城市同时被额外k座供电站供电的最高电量为右端点
        // 左闭右开写法
        long left = minPower, right = minPower + k + 1;
        while (left + 1 < right) {
            long mid = left + (right - left) / 2;
            if (isAvailable(mid, power, n, r, k)) { // 满足条件则去[mid, right]中继续二分
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // 判断给定的minPower作为新的最小供电量是否可行
    private boolean isAvailable(long minPower, long[] power, int n, int r, int k) {
        long[] diff = new long[n + 1];  // 差分数组
        long sum_d = 0, need = 0;       // sum_d为差分值累加，记录各个城市i的供电量变化量，方便更新
        for (int i = 0; i < n; i++) {
            sum_d += diff[i];
            // 当前检索的最小供电量减去当前城市供电量，再减去差分值，就知道目前需要补充建造的供电站数目
            long target = minPower - power[i] - sum_d;
            if (target > 0) {
                need += target;             // 需求量加上本次搜索的供电站数目
                if (need > k) return false; // 如果需求量超过了最多可建造的供电站数目k，则说明本次搜索失败
                sum_d += target;            // 差分值累加target
                // 每次都贪心地建在i + r处(注意不能超过n)，让当前城市i在新电站覆盖范围的左边界上
                // 然后将差分数组中新电站覆盖范围的右边界的差分值减去target
                if (i + r * 2 + 1 < n)
                    diff[i + r * 2 + 1] -= target;
            }
        }
        return true;
    }
}
