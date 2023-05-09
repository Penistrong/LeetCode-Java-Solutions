package org.penistrong.template.sort;

/**
 * 归并排序
 */
public class MergeSort implements Sort{

    @Override
    public void sort(int[] arr, int l, int r) {
        int[] temp = new int[arr.length];
        merge_sort(arr, 0, arr.length, temp);
    }

    // 左闭右开
    public void merge_sort(int[] arr, int l, int r, int[] temp) {
        // 终止条件，区间里只有一个元素
        if (l + 1 >= r) return;
        // 归并排序按照分治思想:Divide and Conquer
        int mid = l + (r - l) / 2;
        merge_sort(arr, l, mid, temp);  // 处理左区间[l, mid)
        merge_sort(arr, mid, r, temp);  // 处理右区间[mid, r)

        // 开始两两归并
        // 左区间[l, mid)与右区间[mid, r)
        // 双指针分别指向两个区间的左端点，然后向后移动，每次将更小的复制到temp数组区间的i位置上
        int p = l, q = mid, pos = l;
        while (p < mid || q < r) {
            if (q >= r || (p < mid && arr[p] <= arr[q]))
                temp[pos++] = arr[p++];
            else
                temp[pos++] = arr[q++];
        }
        // 将缓存数组里的值复制到原数组里
        for (int i = l; i < r; i++)
            arr[i] = temp[i];
    }

    public static void main(String[] args) {
        int[] arr = {-1, 3, 5, -2, 7, 8, -8};
        Sort sortUtil = new MergeSort();
        // mergesort处理的是左闭右开区间[0, arr.length)
        sortUtil.sort(arr, 0, arr.length);
    }
}
