package org.penistrong.leetcode.search;

import java.util.*;

/**
 * LeetCode1654 到家的最少跳跃次数
 */
public class LeetCode1654 {
    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        // a是前进时一步可跳的距离，b是后退时一步可跳的距离
        // 由于不能连续往后跳2次，最多只能一前一后的跳
        // 要确定搜索的上限，避免超过x后还在一直搜索
        // 1. a = b时，没有向后跳的必要，只要一直往前跳
        // 2. a > b时, 可以一前一后的跳，但总体是在前进的，因为向前跳的距离必大于向后跳的距离
        // 3. a < b时，上限为max(max(forbidden) + a, x) + b
        // BFS搜索队列的每个元素需要存储3个相关信息: 数轴下标、方向、步数
        Queue<int[]> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(new int[] {0, 1, 0});
        visited.add(0);
        // 确定区间
        int left = 0, right = Math.max(Arrays.stream(forbidden).max().getAsInt() + a, x) + b;
        Set<Integer> banned = new HashSet<>();
        for (int pos : forbidden) banned.add(pos);
        while (!queue.isEmpty()) {
            int[] state = queue.poll();
            int pos = state[0], direction = state[1], step = state[2];
            if (pos == x) return step;  // 返回当前步数
            // 向前跳，不受上一步跳的方向限制
            int nextPos = pos + a;
            int nextDir = 1;
            if (left <= nextPos && nextPos <= right
                    && !visited.contains(nextPos * nextDir)
                    && !banned.contains(nextPos)) {
                visited.add(nextPos * nextDir);
                queue.offer(new int[] {nextPos, nextDir, step + 1});
            }
            // 上一步跳的是正向，这一步还可以选择反向跳
            if (direction == 1) {
                nextPos = pos - b;
                nextDir = -1;
                if (left <= nextPos && nextPos <= right
                        && !visited.contains(nextPos * nextDir)
                        && !banned.contains(nextPos)) {
                    visited.add(nextPos * nextDir);
                    queue.offer(new int[] {nextPos, nextDir, step + 1});
                }
            }
        }
        return -1;
    }
}
