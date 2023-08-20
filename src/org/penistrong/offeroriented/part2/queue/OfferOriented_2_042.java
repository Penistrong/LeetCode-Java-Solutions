package org.penistrong.offeroriented.part2.queue;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 剑指Offer 2-042 最近请求次数
 */
public class OfferOriented_2_042 {

    class RecentCounter {
        // 利用FIFO的队列记录请求，并移除更早的请求
        private Queue<Integer> records;

        public RecentCounter() {
            records = new ArrayDeque<>();
        }

        public int ping(int t) {
            records.offer(t);       // 先插入，这样下面的循环不用进行判空
            while (records.peek() < t - 3000) {
                records.poll();
            }
            return records.size();
        }
    }

}
