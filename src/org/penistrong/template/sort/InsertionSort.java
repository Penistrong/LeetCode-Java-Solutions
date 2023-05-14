package org.penistrong.template.sort;

/**
 * 直接插入排序
 * 思想也很简单，将数组分为有序和无序两部分，向后遍历数组，对当前遍历的数字，将其插入到前半部分的有序数组里，并移动其他有序数字
 */
public class InsertionSort implements Sort{
    @Override
    public void sort(int[] arr, int l, int r) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {   // 倒序在前半部分的有序数组里找到插入位置
                if (arr[j] < arr[j - 1]) {  // 一旦发现有序部分里两个相邻元素逆序则进行交换
                    swap(arr, j, j - 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {-1, 3, 5, -2, 7, 8, -8};
        Sort sortUtil = new InsertionSort();
        // mergesort处理的是左闭右开区间[0, arr.length)
        sortUtil.sort(arr, 0, arr.length);
    }
}
