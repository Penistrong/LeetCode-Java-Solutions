package org.penistrong.offeroriented.part1;

/*
 * 入门经典-约瑟夫环
 * 删除第m个数字后，下标计数从被删除数字的下一个开始
 * 如果直接用环形链表模拟，在用例比较大的情况下O(mn)会比较大
 * 用数学归纳(根据原书)，方程f(n, m)表示每次在n个数字中删除第m个数字后剩下的数字
 * 每一次删除后，序列的数字个数n都会减小一个
 * f(n, m) = [f(n-1, m) + m] % n, n > 1
 *         = 0,                   n = 1 (只剩1个数字时不执行删除)
 */
public class OfferOriented62 {
    public int lastRemaining(int n, int m) {
        if (n < 1 || m < 1) return -1;
        int last = 0;
        // 一共要执行n - 1次删除
        // 最后一次删除后，剩余的数字的下标last肯定为0，上推n-1轮即可复原下标位置
        // 往回利用下标映射反推，(last对应下标 + 每轮移位m) % 上一轮剩余数字的个数
        for (int i = 2; i <= n; i++) {
            last = (last + m) % i;
        }

        return last;
    }
}
