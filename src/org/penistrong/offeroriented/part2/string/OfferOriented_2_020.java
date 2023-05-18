package org.penistrong.offeroriented.part2.string;

/**
 * 剑指Offer2-020 回文子串的个数
 */
public class OfferOriented_2_020 {

    public int countSubstrings(String s) {
        // 遍历下标i从0 到 n-1，利用中心扩展函数找到回文子串，中心分别为[i]和[i, i+1]
        // 对应奇数长度和偶数长度情况，注意边界的i=n-1时不用担心indexOutOfBound
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count += expandFromCenter(s, i, i);
            count += expandFromCenter(s, i, i + 1);
        }
        return count;
    }

    // 从给定的左右下标[left, right]开始分别向左右扩展，直到不构成回文子串
    // 注意为了处理奇数长度和偶数长度，当奇数长度时，传入参数left==right，偶数长度时left = right - 1
    public int expandFromCenter(String s, int left, int right) {
        // 以每个字符为中心，不断寻找回文子串
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
            count++;
        }
        return count;
    }
}
