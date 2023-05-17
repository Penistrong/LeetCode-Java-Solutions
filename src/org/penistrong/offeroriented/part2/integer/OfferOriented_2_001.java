package org.penistrong.offeroriented.part2.integer;

public class OfferOriented_2_001 {
    // 对于32位整数而言，范围为[-2^31, 2^31 - 1]，因此将任意正数转换为负数都不会导致溢出
    // 由于不能直接使用*/%，那么要用减法模拟除法，将被除数和除数都转换为负数再进行计算
    // 由于除数b为整数，商的绝对值一定是小于等于被除数a的绝对值，所以只会出现一种溢出情况
    // 即a = -2^31, b = -1时，这样商就是2^31超出了int类型的表示范围
    public int divide(int a, int b) {
        if (a == Integer.MIN_VALUE && b == -1)  // Integer.MIN_VALUE即0x80000000, -2^31
            return Integer.MAX_VALUE;           // 返回题意规定的溢出情况
        // 统计被除数和除数的负号个数，且将它们转换为负数，再进行减法运算模拟
        int negative_num = 2;
        if (a > 0) {
            --negative_num;
            a = -a;
        }
        if (b > 0) {
            --negative_num;
            b = -b;
        }

        // 利用减法模拟，不断地从被除数a中减去1份除数b，直到余数小于除数b为止
        // 为了优化这个O(N)的减法过程，每次减法时将除数b翻倍，快速减去份数
        int res = 0;
        while (a <= b) {
            int divisor = b;
            int quotient = 1;
            // 只要被除数a大于当前除数的2倍，就可以不断翻倍除数，最终到达原除数的2^k倍即可终止，减去的份数正是2^k份
            // 由于此时被除数和除数都是负数，每次将除数翻倍都会使得除数不断向-2^31靠拢，考虑到不可能超过-2^30的2倍，因此扩大到-2^30即可停止
            // 0x80000000右移1位即0xC0000000，表示为-2^31的一半，即-2^30
            while (divisor >= (Integer.MIN_VALUE >> 1) && a <= divisor + divisor) {
                quotient += quotient;
                divisor += divisor;
            }
            res += quotient;
            a -= divisor;
        }

        return negative_num == 1? -res : res;   // 最后返回答案时要根据负号的个数在商前面追加负号
    }
}
