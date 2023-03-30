package org.penistrong.leetcode.sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案.
 */
public class LeetCode347 {

    // 3种解法
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> buckets = new HashMap<>();
        for (int num : nums)
            buckets.put(num, buckets.getOrDefault(num, 0) + 1);
        return minHeapSolution(buckets, k);
    }

    public int[] directSortSolution(Map<Integer, Integer> buckets, int k) {
        // 用Stream写法，注意是对value排序然后收集key
        List<Integer> desc_unique_nums = buckets.entrySet().stream()
            .sorted((kv1, kv2) -> kv2.getValue() - kv1.getValue())  // 降序比较！
            .map(Map.Entry::getKey)
            .toList();
        int[] res = new int[k];
        for (int i = 0; i < k; i++)
            res[i] = desc_unique_nums.get(i);
        return res;
    }

    /**
     * 建立大根堆的解法，否则像上面那样对Map排序后再全部收集，时间复杂度和空间复杂度都很高啊
     * 使用优先队列建立大根堆，Map.Entry<>，key为元素值，value为出现次数
     * 因为存放的是数组，要告诉优先队列如何进行排序(按出现次数降序排序)
     * 时间复杂度 O(N log N) + O(N) ~ O(N log N)
     * @param buckets
     * @param k
     * @return
     */
    public int[] maxHeapSolution(Map<Integer, Integer> buckets, int k) {
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>(
                (kv1, kv2) -> kv2.getValue() - kv1.getValue());
        // 一次性放进所有entrySet，自动维护插入顺序
        maxHeap.addAll(buckets.entrySet());
        int[] res = new int[k];
        for (int i = 0; i < k && !maxHeap.isEmpty(); ++i)
            res[i] = maxHeap.poll().getKey();
        return res;
    }

    /**
     * 小根堆解法，维护具有k个节点的小根堆
     * 时间复杂度为O(N log k) + O(N) ~ O(N log k)
     * @param buckets
     * @param k
     * @return
     */
    public int[] minHeapSolution(Map<Integer, Integer> buckets, int k) {
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(
                (kv1, kv2) -> kv1.getValue() - kv2.getValue());
        for (Map.Entry<Integer, Integer> entry : buckets.entrySet()) {
            // 小根堆已满，检查堆顶entry的value(即出现次数)是否比当前遍历的元素的出现次数更大
            // 堆顶更大则说明，至少有k个数字的出现次数比当前遍历的元素的出现次数更大，舍弃当前值不插入小根堆中
            // 堆顶更小，则需要弹出堆顶，将这个具有更多出现次数的entry插入堆中
            if (minHeap.size() == k) {
                if (minHeap.peek().getValue() < entry.getValue()) {
                    minHeap.poll();
                    minHeap.offer(entry);
                }
            } else { // 小根堆节点还没装满，直接装进来
              minHeap.offer(entry);
            }
        }
        int[] res = new int[k];
        for (int i = 0; i < k && !minHeap.isEmpty(); ++i)
            res[i] = minHeap.poll().getKey();
        return res;
    }
}
