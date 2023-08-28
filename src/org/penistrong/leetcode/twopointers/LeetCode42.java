package org.penistrong.leetcode.twopointers;

import java.util.ArrayDeque;
import java.util.Deque;

public class LeetCode42 {
    /** 接雨水 典中典 一题N解 我选单调递减栈
     *  单调递减栈中存储的是下标i，满足height[i]在栈中呈现递减趋势，栈顶是最小的元素
     *  一趟从左到右遍历，只有在栈中至少有2个元素时(比如left,top)，当遍历到的下标i满足
     *  height[i] > height[top], 由于单调栈特性必有height[left] >= height[top]
     *  所以三元组 left, top, i 之间就可以形成一个接雨水的区域
     *  尔后top出栈，原来的left成为新的top
     *  重复直到栈顶top_new满足 height[i] <= height[top_new] 就可以停止本次计算，继续遍历下一个i
     */
    public int trap(int[] height) {
        int sum = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) break; // 为了满足单调栈特性，同时栈内元素必须大于2个才可以计算
                int left = stack.peek();    // 注意是peek()不是pop()
                int w = i - left - 1;       // 柱子本身不算，所以宽度是 (i - 1) - (left + 1) + 1
                int h = Math.min(height[left], height[i]) - height[top];    // 木桶效应，取最矮的柱
                sum += w * h;
            }
            stack.push(i);
        }
        return sum;
    }
}
