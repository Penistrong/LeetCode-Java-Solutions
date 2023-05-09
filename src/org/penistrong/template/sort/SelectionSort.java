package org.penistrong.template.sort;

/**
 * 选择排序
 * 选择排序的思想最为直观，每一趟遍历尚未排序的部分找到最小/最大的数字，将它与已排序部分下一个位置的元素进行交换
 * 这里的手动实现为，每一趟选择最小的数字放在数组前部，整体升序排序
 */
public class SelectionSort implements Sort{

    @Override
    public void sort(int[] arr, int l, int r) {
        for (int i = 0; i < arr.length - 1; i++) {  // 只需要 n - 1 趟排序
            int select = i;                         // 标记剩余未排序部分中最小数字的下标
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[select])           // 更新下标
                    select = j;
            }
            swap(arr, select, i);                   // 将选择的最小数字与本趟放置位置的元素进行交换
        }
    }

    public static void main(String[] args) {
        int[] arr = {-1, 3, 5, -2, 7, 8, -8};
        Sort sortUtil = new SelectionSort();
        // mergesort处理的是左闭右开区间[0, arr.length)
        sortUtil.sort(arr, 0, arr.length);
    }
}
