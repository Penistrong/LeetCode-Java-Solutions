package org.penistrong.leetcode.twopointers;

/**
 * LeetCode9 回文数判断
 */
public class LeetCode9 {
    public boolean isPalindrome(int x) {
        // 直接转成字符串，然后双指针判断
        String str = String.valueOf(x);
        int i = 0, j = str.length() - 1;
        while (i <= j) {
            if (str.charAt(i) == str.charAt(j)) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }

    // 不转成字符串，仅从数学出发，如何判断
    public boolean isPalindromeSolution2(int x) {
        // 反转数字的一半，然后比较大小
        // 模10得到最低位，再通过除法除以10, 移除最低位
        // 后半部分每分离出一个最低位，就乘10，直到原始数字小于等于反转后的数字就意味着处理完毕
        if (x < 0 || (x % 10 == 0 && x != 0))
            return false;
        int revertHalf = 0;
        while (x > revertHalf) {
            revertHalf = revertHalf * 10 + x % 10;
            x /= 10;
        }
        // 最后再处理奇数长度情况，此时revertHalf应该多出了一位，可以两种情况并行考虑
        return x == revertHalf || x == revertHalf / 10;
    }
}
