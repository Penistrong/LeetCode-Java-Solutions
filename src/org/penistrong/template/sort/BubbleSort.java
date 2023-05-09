package org.penistrong.template.sort;

/**
 * 冒泡排序的思想很简单，对于长度为n的数组，进行n-1趟排序
 * 每趟排序都会遍历数组的一部分，相邻元素两两比较，将更大的元素交换到当前比较位置的后面
 * 第i趟可以确定整个数组中第i大的数字(i从1开始的话)
 */
public class BubbleSort implements Sort{
    @Override
    public void sort(int[] arr, int l, int r) {
        int len = arr.length;
        boolean swapped;    // 如果某一趟冒泡没有发生交换，那么说明所有元素都已有序，可以提前终止排序过程
        for (int i = 1; i < len; i++) {
            swapped = false;
            for (int j = 0; j < len - i; j++) { // 注意每一趟冒泡的右界
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void main(String[] args) {
        int[] arr = {-1, 3, 5, -2, 7, 8, -8};
        Sort sortUtil = new BubbleSort();
        // mergesort处理的是左闭右开区间[0, arr.length)
        sortUtil.sort(arr, 0, arr.length);
    }
}
