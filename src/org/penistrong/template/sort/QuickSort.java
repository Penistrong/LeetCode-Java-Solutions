package org.penistrong.template.sort;


/**
 * 手写快排，强化记忆
 */
public class QuickSort implements Sort{

    // 左右均为闭区间的二分写法, l和r分别为区间左端点和右端点
    public void sort(int[] arr, int l, int r) {
        // 只剩一个元素或者区间范围不对，则递归终止
        if (l >= r) return;
        // 主元选取当前分段的首元素
        int left = l, right = r, pivot = arr[left];
        while (left < right) {
            // 从后往前找到下一个小于主元的数
            while (left < right && arr[right] >= pivot) {
                --right;
            }
            arr[left] = arr[right];
            // 从前往后找到下一个大于主元的数
            while (left < right && arr[left] <= pivot) {
                ++left;
            }
            arr[right] = arr[left];
        }
        // 交换完毕后，left位置为空，刚好放置主元
        arr[left] = pivot;
        // 分治左右区间
        sort(arr, l, left - 1);
        sort(arr, left + 1, r);
    }

    public static void main(String[] args) {
        int[] arr = {-1, 3, 5, -2, 7, 8, -8};
        Sort sortUtil = new QuickSort();
        sortUtil.sort(arr, 0, arr.length - 1);
    }

}
