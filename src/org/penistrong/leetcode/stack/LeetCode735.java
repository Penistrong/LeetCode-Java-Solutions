package org.penistrong.leetcode.stack;

import java.util.*;

/**
 * LeetCode735 - 行星碰撞
 */
public class LeetCode735 {

    public int[] asteroidCollision(int[] asteroids) {
        // 相邻的移动方向相反的小行星互相碰撞
        // 我的想法是，两趟扫描
        // 第一趟从左到右，一路处理向右移动的行星，并抵消向左移动的行星


        // 但是这样会导致前面仍在向右移动的陨石无法被计算，因此用栈进行模拟
        Deque<Integer> stack = new ArrayDeque<>();
        // 从左向右遍历，将陨石加入栈中，每碰到一个向左移动的陨石，就依次从栈中取出右移陨石互相碰撞
        for (int asteroid : asteroids) {
            boolean notBoomed = true;   // 标记当前左移陨石(值小于0是否还存活)
            while (notBoomed && asteroid < 0 && !stack.isEmpty() && stack.peek() > 0) {
                notBoomed = stack.peek() < Math.abs(asteroid);  // 质量相等时才会使当前左移陨石也爆炸
                if (stack.peek() <= Math.abs(asteroid)) {   // 栈顶右移陨石小于等于当前左移陨石质量，栈顶爆炸
                    stack.pop();
                }
            }
            if (notBoomed) stack.push(asteroid); // 碰完后当前左移陨石还存活，则加入栈中
        }
        // 倒序收集栈中剩余陨石
        int n = stack.size();
        int[] res = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }

    public static void main(String[] args) {
        int[] aliveAsteroids = new LeetCode735().asteroidCollision(new int[]{7,-1,2,-3,-6,-6,-6,4,10,2});
        System.out.println(Arrays.toString(aliveAsteroids));
    }
}
