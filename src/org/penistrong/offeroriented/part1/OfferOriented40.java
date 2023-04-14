package org.penistrong.offeroriented.part1;

import java.util.Arrays;

public class OfferOriented40 {

    // 利用快排的Partition
    // 每次划分后查看哨兵放置的索引位置，如果刚好等于k, 说明左边都是前k小的数
    // 直接返回这些数即可
    public int[] getLeastNumbers(int[] arr, int k) {
        if (k >= arr.length) return arr;
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int pivot = partition(arr, k, left, right);
            if (pivot < k) {        // 小于k就去右半部分里partition
                left = pivot + 1;
            } else if (pivot > k) { // 大于k则去左半部分里进行partition
                right = pivot - 1;
            } else break;           // 等于k, 返回数组左半部分子数组，没必要再循环下去了
        }
        return Arrays.copyOf(arr, k);
    }

    private int partition(int[] arr, int k, int left, int right) {
        int i = left, j = right;
        // 将arr[left]作为主元
        while (true) {
            while (i < right && arr[i] <= arr[left]) i++;   // 找到左边第一个大于主元的数
            while (j > left && arr[j] >= arr[left]) j--;    // 找到右边第一个小于主元的数
            if (i >= j) break;                              // 越过了，说明pivot左右已划分好
            swap(arr, i, j);                                // 交换
        }
        // 此时j是主元应该放置的位置(循环结束arr[j]小于主元，所以和主元交换位置即可)
        swap(arr, left, j);
        return j;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
