package org.penistrong.leetcode.math;

/**
 * LeetCode 1281 整数的各位积和之差
 */
public class LeetCode1281 {
    public int subtractProductAndSum(int n) {
        // 不停地除10就行了
        int sum = 0, mul = 1;
        while (n != 0) {
            int rem = n % 10;
            mul *= rem;
            sum += rem;
            n /= 10;
        }
        return mul - sum;
    }
}
