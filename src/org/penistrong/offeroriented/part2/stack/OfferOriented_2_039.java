package org.penistrong.offeroriented.part2.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 剑指Offer2-039 直方图最大矩形面积(单调栈)
 */
public class OfferOriented_2_039 {
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1); // 压入-1作为占位符, 同时作为计算矩形宽度的最左边界

        int maxArea = 0;
        // 依次遍历各个矩形，往单调栈中压入柱子的下标
        for (int i = 0; i < heights.length; i++) {
            // 当前柱子高度小于栈顶柱子高度时，则不断将栈顶出栈，并寻找栈顶最高柱子周围矮柱子形成的最大矩形
            while (stack.peek() != -1 && heights[i] <= heights[stack.peek()]) {
                int height = heights[stack.pop()];
                // 当前柱子因为比栈顶柱子矮，所以最大矩形的高度不可能高过当前柱子的高度
                // 之前栈顶的最高柱子出栈后，当前栈顶为次高柱，以次高柱为最左柱的左开边界，计算宽度
                int width = i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);  // 终于，将当前柱子入栈
        }
        // 栈内还剩余柱子没处理，依次出栈计算(由于遍历完毕，此时i = heights.length)
        while (stack.peek() != -1) {
            int height = heights[stack.pop()];
            int width = heights.length - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        return maxArea;
    }
}
