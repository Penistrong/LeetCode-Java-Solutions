package org.penistrong.offeroriented.part1;

import java.util.ArrayList;
import java.util.List;

/**
 * 经典滑动窗口
 */
public class OfferOriented57 {
    public int[][] findContinuousSequence(int target) {
        // 由于是连续正数序列，所以只需要遍历到 (1 + target) / 2即可
        // 维护一个序列，序列起始为start，结束为end，[start...end]的和为序列和，可以用等差数列求和公式计算
        // 但是由于是在遍历，所以可以操作序列和进行增减即可
        if (target < 3) return null;
        int start = 1, end = 2;
        int sum = start + end;
        int deadline = (1 + target) / 2;    // 除了限制遍历上限，顺便做结果数组的长度限制
        List<int[]> result = new ArrayList<>();
        while (start < deadline && end <= deadline) {
            while (sum > target) {
                sum -= start;
                start++;
            }
            if (sum == target) {
                result.add(generateSequence(start, end));
            }
            end++;
            sum += end;
        }
        // 给定int数组的构造器，第一个0表示自动推断大小(根据List大小)
        // 即 Collection.toArray(new T[0])
        return result.toArray(new int[0][]);
    }

    private int[] generateSequence(int start, int end) {
        int[] res = new int[end - start + 1];
        int i = 0;
        while (start <= end) {
            res[i++] = start;
            start++;
        }
        return res;
    }
}
