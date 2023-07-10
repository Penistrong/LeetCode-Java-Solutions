package org.penistrong.leetcode.automaton;

import java.util.HashMap;
import java.util.Map;

public class LeetCode8 {
    public int myAtoi(String s) {
        Automaton automaton = new Automaton();
        for(int i = 0; i < s.length(); i++){
            automaton.transition(s.charAt(i));
        }
        return (int)(automaton.sign * automaton.number);
    }

    public class Automaton {
        public int sign = 1;                // 符号位
        public long number = 0;             // 转换后的32位有符号整数
        private State state = State.START;  // 初始状态

        enum State {
            START, SIGNED, IN_NUMBER, END
        }

        // 状态转移表
        private Map<State, State[]> tab = new HashMap<>() {{
            put(State.START, new State[]{State.START, State.SIGNED, State.IN_NUMBER, State.END});
            put(State.SIGNED, new State[]{State.END, State.END, State.IN_NUMBER, State.END});
            put(State.IN_NUMBER, new State[]{State.END, State.END, State.IN_NUMBER, State.END});
            put(State.END, new State[]{State.END, State.END, State.END, State.END});
        }};

        public void transition(char c) {
            state = tab.get(state)[decideTransition(c)];
            if (State.IN_NUMBER.equals(state)) {
                number = number * 10 + (c - '0');   // 从左往右读取数字，之前的数值乘10后加上当前数字
                number = sign == 1 ? Math.min(number, Integer.MAX_VALUE) : Math.min(number, -(long) Integer.MIN_VALUE);
            } else if (State.SIGNED.equals(state)) {
                sign = c == '+' ? 1 : -1;
            }
        }

        // 查表决定状态转移
        public int decideTransition(char c) {
            if (c == ' ') return 0;              // 列0:空格
            if (c == '+' || c == '-') return 1;  // 列1:数字正负号
            if (Character.isDigit(c)) return 2;  // 列2:数字
            return 3;                           // 列3:其他字符
        }
    }
}
