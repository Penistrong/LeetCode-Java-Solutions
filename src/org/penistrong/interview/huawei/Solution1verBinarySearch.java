package org.penistrong.interview.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * T1.交易系统的降级策略
 * 有一个核心交易系统接口被N个上游系统调用，每个上游系统的调用量R=[R1,R2,...,RN]
 * 由于核心交易系统集群故障，需要暂时系统降级限制调用，核心交易系统能接受的最大调用量为cnt
 * 设置降级规则如下：
 * 如果sum(R1,R2,...,RN)小于等于cnt，则全部可以正常调用，返回-1
 * 如果sum(R1,R2,...,RN)大于cnt，设置一个阈值limit
 * 如果某个上游系统发起的调用量超过limit，就将该上游系统的调用量限制为limit，其余未达到limit的系统可以正常发起调用
 * 求出这个最大的limit (可以为0)，此题目对效率有要求，请选择高效的方式
 * 输入描述:
 * 第一行 - 每个上游系统的调用量R_i，以整型数组方式给出, len(R) \in (0, 10^5], R_i \in (0, 10^5)
 * 第二行 - 最大调用量cnt, cnt \in (0, 10^9]
 */
public class Solution1verBinarySearch {

    public void solution() {
        List<Integer> R = new ArrayList<>();
        Scanner in  = new Scanner(System.in);
        String[] raw_R = in.nextLine().split(" ");
        for (String r : raw_R)
            R.add(Integer.parseInt(r));
        int cnt = Integer.parseInt(in.nextLine());

        // 直接二分阈值limit, 再测试是否小于调用量cnt，采用左开右闭写法，处理边界情况
        // 因为要返回二分查找最后的下标，而区间不断缩小直到left==right
        // 如果是左开右闭或者左闭右开，循环的终止条件就是left < right，开始条件也不同
        // 左开右闭(left, right], left_0 = -1, right_0 = n - 1
        // 左闭右开[left, right), left_0 = 0, right_0 = n
        int left = -1, right =  100000;
        while (left < right) {
            int mid = (left + right + 1) >> 1;
            if (isAvailable(R, mid, cnt)) left = mid;
            else right = mid - 1;
        }
        // 左开右闭的二分结束后，最后的区间为(left, right]，其实就是left=right=mid
        System.out.println(left);
    }

    // 根据给定阈值，计算和上限
    private boolean isAvailable(List<Integer> R, int limit, int cnt) {
        int sum = 0;
        for (int r : R) {
            sum += Math.min(r, limit);
        }
        return sum <= cnt;
    }

    public static void main(String[] args) {
        new Solution1verBinarySearch().solution();
    }
}
