package org.penistrong.offeroriented.part1;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 滑动窗口的最大值，如果直接暴力使用优先队列，可能会导致超时
 */
public class OfferOriented59 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        // 如果直接采用滑窗的原始定义，直接扫描每个窗口里的最大值，显然时间复杂度为O(nk)
        // 利用双端队列：
        // 队头(左部)始终存放当前滑窗最大值**对应的下标**
        // 队尾(右部)存放滑动窗口移动时囊括的新数字(有可能成为新的最大值)
        // 同时，窗口滑动时要删除队列中不处于窗口范围的下标
        // 即队列中存储的下标对应的实际数字是单调递减的，队头最大，尔后单调下降
        int len = nums.length;
        int[] maxInSW = new int[len - k + 1];
        Deque<Integer> queue = new LinkedList<>();

        // 构建初始滑动窗口，新囊括的数字下标追加到双端队列队尾
        // 且每次囊括时，都要取出所有队列中小于当前新数字的其他数字，保证队头是当前滑窗最大值
        for (int i = 0; i < k; i++) {
            while (!queue.isEmpty() && nums[i] >= nums[queue.peekLast()])
                queue.pollLast();
            queue.addLast(i);
        }

        for (int i = k; i < len; i++) {
            // 记录上一次滑窗里的最大值
            maxInSW[i - k] = nums[queue.peekFirst()];
            // 滑窗右移，囊括新数字，队尾处理保持一致
            while (!queue.isEmpty() && nums[i] >= nums[queue.peekLast()])
                queue.pollLast();
            // 左边的队头(未移动滑窗前的最大值下标)可能会超出下一个滑窗的范围，进行判断
            if (!queue.isEmpty() && queue.peekFirst() <= (i - k))
                queue.pollFirst();
            queue.addLast(i);
        }
        // 最后一次滑动后的最大值还未计入
        maxInSW[len - k] = nums[queue.peekFirst()];

        return maxInSW;
    }
}
