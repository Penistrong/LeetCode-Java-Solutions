package org.penistrong.offeroriented.part2.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 剑指Offer2-037 小行星碰撞
 */
public class OfferOriented_2_037 {

    public int[] asteroidCollision(int[] asteroids) {
        // 用栈模拟小行星相撞抵消的过程
        Deque<Integer> stack = new ArrayDeque<>();
        // 每碰到一个负值(向左移动的行星)，则取出栈顶不断地与该行星进行碰撞直到结束
        for (int asteroid: asteroids) {
            // 标记左移行星是否依然存活
            boolean notBoomed = true;
            while (notBoomed && asteroid < 0 && !stack.isEmpty() && stack.peek() > 0) {
                // 栈顶行星小于左移行星质量则会爆炸，左移行星依然存活
                notBoomed = stack.peek() < Math.abs(asteroid);
                // 延迟处理栈顶行星是否爆炸
                if (stack.peek() <= Math.abs(asteroid)) {
                    stack.pop();
                }
            }
            // 当前行星是右移行星或者栈为空或者栈顶已经是左移行星，则加入当前行星
            if (notBoomed) stack.push(asteroid);
        }
        // 倒序收集剩余陨石(栈顶在数组末尾)
        int n = stack.size();
        int[] res = new int[n];
        for (int i = n - 1; i >= 0;i--)
            res[i] = stack.pop();
        return res;
    }
}
