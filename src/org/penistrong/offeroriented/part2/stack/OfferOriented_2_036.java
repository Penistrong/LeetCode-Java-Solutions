package org.penistrong.offeroriented.part2.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class OfferOriented_2_036 {
    public int evalRPN(String[] tokens) {
        // 利用栈依次处理逆波兰表达式的各个token
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token: tokens) {
            // 弹出操作数栈栈顶的两个元素，并进行计算
            if (isOperator(token)) {
                int op2 = stack.pop();
                int op1 = stack.pop();
                stack.push(calculate(op1, op2, token));
            } else {
                stack.push(Integer.valueOf(token));
            }
        }
        // 由于输入的测试用例已保证是合法的逆波兰表达式，因此计算完毕后栈中只剩一个元素作为整个表达式的结果
        return stack.pop();
    }

    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }

    private int calculate(int op1, int op2, String op) {
        int res = 0;
        switch (op) {
            case "+" -> res = op1 + op2;
            case "-" -> res = op1 - op2;
            case "*" -> res = op1 * op2;
            case "/" -> res = op1 / op2;
        }
        return res;
    }

    public static void main(String[] args) {
        String[] tokens = new String[]{"2", "1", "+", "3", "*"};
        OfferOriented_2_036 runner = new OfferOriented_2_036();
        System.out.println(runner.evalRPN(tokens));
    }
}
