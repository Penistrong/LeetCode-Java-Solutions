package org.penistrong.offeroriented.part1;

public class OfferOriented43 {

    /**
     * n 最多可达Integer.MAX_VALUE
     * 从1~n的十进制表示中，计算包含1的数字出现的次数
     * 对每个数字而言，若是它的第一位就是1, 那么从1999...9~1000...0全部都是包含1的数字
     * 一共有10^x个包含1的数，其中x为1后面的数位个数，即 x = nums.length - 1
     */
    public int countDigitOne(int n) {
        // 转换为String便于计算
        return countDigitOneRecursive(Integer.toString(n));
    }

    private int countDigitOneRecursive(String num_str) {
        // 递归终止条件
        if (num_str == null || num_str.equals(""))
            return 0;
        // 假设num_str的格式为 head-xxx...xx
        // 分离出第一个数位，看是否为1
        int head = num_str.charAt(0) - '0';
        int len = num_str.length();
        if (len == 1 && head == 0) return 0;    // num_str长为1,只有1个0,所以比num小的数中不存在包含1的数
        if (len == 1 && head > 0) return 1;     // 只有单数1满足条件
        int numHeadDigit = 0;                   // 根据第一个数位是否为1，分情况计算
        if (head > 1)                           // 首位大于1,那么1做为首位时一共有 10^{len - 1}个包含1的数
            numHeadDigit = (int) Math.pow(10, len - 1);
        else if (head == 1)                     // 首位刚好为1, 所以以1作首位时，包含1的个数就是后面的数字个数再加1
            numHeadDigit = Integer.parseInt(num_str.substring(1)) + 1;   // 1-000..0 ~ 1-xxx..x

        // 首位计算完毕后，计算除去首位后剩余的数字到当前num_str的其他数位包含1的数字个数
        // 数字计算区间为 (xxx...xx, head-xxx...xx]，那么这段左开右闭区间还可以再分为head段
        // (xxx...xx, 1-xxx...xx] U (1-xxx..xxx, 2-xxx...xx] U ... U ((head-1)-xxx...xx, head-xxx...xx]
        // 每一段中的 低 (len - 1) 位数字中，都可以选择任意一位为1，其余数位任选0~9这10个digit的任一个
        // 那么一共有 段个数 * (len-1)位 * (固定任一位为1后)剩余(len-2)个数位任选 0~9共计10^{len-2}个
        int numNonHeadDigit = head * (len - 1) * (int) Math.pow(10, len - 2);

        // 递归计算剩余区间(0, xxx...xx] 中的包含1的数字个数
        int numKillHeadDigit = countDigitOneRecursive(num_str.substring(1));

        return numHeadDigit + numNonHeadDigit + numKillHeadDigit;
    }
}
