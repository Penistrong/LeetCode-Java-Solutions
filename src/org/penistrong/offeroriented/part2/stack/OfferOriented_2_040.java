package org.penistrong.offeroriented.part2.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 剑指Offer2_040 矩阵中的最大矩形面积
 * 柱状图找矩形面积的单调栈解法，巧妙变形
 */
public class OfferOriented_2_040 {
    public int maximalRectangle(String[] matrix) {
        // 按照题解的做法，首先处理每一行，计算每一个位置其左边(包含其本身)连续1的个数
        // 尔后对每个点matrix[i][j]找到位于同一j列上前面0~i-1行的其他点，计算以matrix[i][j]为右下角的最大矩形面积
        if (matrix.length == 0) return 0;
        int row = matrix.length;
        int col = matrix[0].length();
        // 1 1 0 1 1 1 0 假设有一行
        // 1 2 0 1 2 3 0 找到其左边连续1的个数作为以matrix[i][j]为右下角的矩形宽度(横着看的宽度)
        int[][] width = new int[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                if (matrix[i].charAt(j) == '1')
                    width[i][j] = (j == 0 ? 0 : width[i][j-1]) + 1;

        // 将每一列视作柱状图的**横轴**，即剑指Offer39题将柱状图逆时针旋转90度，找到柱子间可以形成的最大矩形面积
        // 然后使用单调栈寻找即可，单调栈中存储的是各柱子的下标，也就是各行的下标
        int rect = 0;
        for (int j = 0; j < col; j++) {
            int[] upNeighbor = new int[row];    // 从上到下记录每一行对应的柱子其周边相邻的小于当前柱子高度的最高柱
            int[] downNeighbor = new int[row];  // 从下到上
            Deque<Integer> stack = new ArrayDeque<>();

            // 由上到下遍历各个柱子
            stack.push(-1);     // -1用作从上到下扫描柱子下标的上边界
            // 当前柱子如果高于栈顶的柱子，则弹出栈顶的柱子直到栈内柱子高度都小于当前柱子
            // 尔后将当前栈顶柱子下标记入up[i]，即找到高度小于等于当前柱子的柱子下标，才可以形成矩形
            for(int i = 0; i < row; i++) {
                while (stack.peek() != -1 && width[stack.peek()][j] >= width[i][j]) {
                    stack.pop();
                }
                upNeighbor[i] = stack.peek();
                stack.push(i);
            }
            stack.clear();

            // 由下到上遍历各个柱子
            stack.push(row);    // row用作从下到上扫描柱子下标的下边界
            for (int i = row - 1; i >= 0; i--) {
                while (stack.peek() != row && width[stack.peek()][j] >= width[i][j]) {
                    stack.pop();
                }
                downNeighbor[i] = stack.peek();
                stack.push(i);
            }

            // 两趟遍历后已经找到了每一行对应柱子其相邻能够成为矩形的上下柱子下标，利用下标差计算宽度
            for (int i = 0; i < row; i++) {
                int rect_width = downNeighbor[i] - upNeighbor[i] - 1;
                int area = rect_width * width[i][j];;
                rect = Math.max(rect, area);
            }
        }
        return rect;
    }
}
