package org.penistrong.offeroriented.part2.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class OfferOriented_2_038 {

    public int[] dailyTemperatures(int[] temperatures) {
        // 输出每个位置上的温度其后第一个超过当天温度的天数，否则用0替代
        Deque<Integer> stack = new ArrayDeque<>();
        int size = temperatures.length;
        int[] result = new int[size];
        // 顺序处理数组中每一个温度，每取到一个温度和栈中下标对应的温度进行比较(便于快速找到当天温度)
        // 等待的天数为两个温度在数组中的下标之差
        for (int i = 0; i < temperatures.length; i++) {
            // 不断比较栈顶，只要不为空且temp[栈顶] < temp[当前]，就出栈
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int j = stack.pop();
                result[j] = i - j;
            }
            // 当前温度对应下标入栈
            stack.push(i);
        }
        // 栈中剩余元素为之后温度都不会更高的天数，所以全部取出并赋0(其实数组默认为0也可以不处理)
        while (!stack.isEmpty()) {
            result[stack.pop()] = 0;
        }
        return result;
    }
}
