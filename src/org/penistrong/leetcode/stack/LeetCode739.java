package org.penistrong.leetcode.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode739 每日温度
 * 单调栈经典应用
 */
public class LeetCode739 {
    public int[] dailyTemperatures(int[] temperatures) {
        // 如果采取n趟扫描，每访问一个温度就去其后找比它更高的温度，这样大致时间复杂度为O(N^2)
        // 空间换时间，用一个栈保存前面访问到的温度对应的下标，保证栈内温度是非严格单调递减的
        // 每访问到一个比当前栈顶更高的温度则不断出栈，并计算两个下标之间的差值作为天数，同时将结果数组对应位置设为该天数即可,大致O(N)复杂度
        int len = temperatures.length;
        int[] answer = new int[len];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {
            int cur_temp = temperatures[i];
            while (!stack.isEmpty() && temperatures[stack.peek()] < cur_temp) {
                int j = stack.pop();
                answer[j] = i - j;
            }
            stack.push(i);
        }
        return answer;  // 没有处理的位置默认是0
    }
}
