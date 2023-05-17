package org.penistrong.offeroriented.part1;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 剑指Offer59-T2, 定义一个队列并能够得到队列里的最大值，且push_back()、pop_back()、max_value()函数的均摊复杂度为O(1)
 */
public class OfferOriented59_2 {
    /**
     * Your MaxQueue object will be instantiated and called as such:
     * MaxQueue obj = new MaxQueue();
     * int param_1 = obj.max_value();
     * obj.push_back(value);
     * int param_3 = obj.pop_front();
     */
    static class MaxQueue {

        private Deque<Tuple> maximums; // 队头始终存储队列最大值的单调队列
        private Deque<Tuple> data;     // 按照正常入队出队顺序保存所有数据的双端队列
        private int curIndex;          // 下一个插入数据的对应下标

        static class Tuple {
            int val;
            int index;

            public Tuple (int val, int index) {
                this.val = val;
                this.index = index;
            }
        }

        public MaxQueue() {
            this.maximums = new LinkedList<>();
            this.data = new LinkedList<>();
            this.curIndex = 0;
        }

        public int max_value() {
            if (maximums.isEmpty())
                return -1;
            // 直接返回maximums队列的队头
            return maximums.peekFirst().val;
        }

        public void push_back(int value) {
            Tuple t = new Tuple(value, curIndex++);
            // 插入数据
            data.addLast(t);    // 插入原始数据队列
            // 对于维护最大值的单调队列，从队列尾部移除所有小于当前插入数据的其他数据
            while (!maximums.isEmpty() && maximums.peekLast().val <= value)
                maximums.pollLast();
            maximums.addLast(t);
        }

        public int pop_front() {
            if (data.isEmpty())
                return -1;
            // 移除队头时，要判断其是否也是单调队列的当前队头，若是的话也要移除该队头
            if (maximums.peekFirst().index == data.peekFirst().index)
                maximums.pollFirst();
            return data.pollFirst().val;
        }
    }
}
