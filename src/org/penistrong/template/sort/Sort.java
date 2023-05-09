package org.penistrong.template.sort;

/**
 * 排序类接口
 */
public interface Sort {
    void sort(int[] arr, int l, int r);

    /**
     *  排序算法常用到的交换函数
     */
    default void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
