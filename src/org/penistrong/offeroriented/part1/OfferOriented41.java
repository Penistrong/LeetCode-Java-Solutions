package org.penistrong.offeroriented.part1;

import java.util.PriorityQueue;

public class OfferOriented41 {

    class MedianFinder {
        // 数据流中的元素个数为奇数时，中位数就是排序后的nums[n/2 + 1]
        // 为偶数时，中位数为nums[n/2]与nums[n/2 + 1]的平均值
        // 用一个最大堆和一个最小堆分别维护中位数左半部分和右半部分
        PriorityQueue<Integer> left_max_heap;   // 堆顶为nums[n/2]
        PriorityQueue<Integer> right_min_heap;  // 堆顶为nums[n/2 + 1]

        /** initialize your data structure here. */
        public MedianFinder() {
            // 优先队列默认是小根堆
            left_max_heap = new PriorityQueue<>((num1, num2) -> num2 - num1);
            right_min_heap = new PriorityQueue<>();
        }

        /**
         * 从数据流中不断添加元素时，需要保证最大堆与最小堆的元素个数差值不能大于1
         * 如果当前总数目为偶数，就将新数据插入右半部分最小堆，这样最小堆堆顶就是奇数时的中位数，否则插入最大堆
         * 同时还要保证，如果新加入的数比左边最大堆堆顶要小，那么应该把最大堆堆顶加入到最小堆中
         * 如果新加入的数比右边最小堆堆顶要大，那么应该把最小堆堆顶加入到最大堆中
         */
        public void addNum(int num) {
            if (left_max_heap.size() == right_min_heap.size()) {
                // 当前数据流总个数为偶数(两个堆大小相等)，新元素插入到最小堆中
                if (!left_max_heap.isEmpty() && num < left_max_heap.peek()) {
                    left_max_heap.offer(num);      // 新元素加入最大堆
                    num = left_max_heap.poll();    // 取出左边堆顶并将插入元素更新为最大堆堆顶元素
                }
                right_min_heap.offer(num);         // 插入元素加入最小堆
            } else {
                // 当前数据流总个数为奇数，新元素插入到最大堆中
                if (!right_min_heap.isEmpty() && num > right_min_heap.peek()) {
                    right_min_heap.offer(num);     // 新元素加入最小堆
                    num = right_min_heap.poll();   // 取出右边堆顶并将插入元素更新为最小堆堆顶元素
                }
                left_max_heap.offer(num);
            }
        }

        public double findMedian() {
            // 获得左右两半部分的元素总个数
            // 不存在中位数时返回0
            if ((left_max_heap.size() + right_min_heap.size()) == 0) return 0;
            double median = 0;

            if (left_max_heap.size() != right_min_heap.size()) {
                // 奇数，中位数就是nums[n/2+1]，即右边小根堆的堆顶
                median = right_min_heap.peek();
            } else {
                // 偶数，返回最大堆与最小堆顶的平均值
                median = (left_max_heap.peek() + right_min_heap.peek()) / 2.0;
            }
            return median;
        }
    }

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
}
