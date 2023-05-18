package org.penistrong.offeroriented.part2.string;

/**
 * 剑指Offer2-019 最多删除一个字符得到回文
 */
public class OfferOriented_2_019 {

    public boolean validPalindrome(String s) {
        // 首先根据正常回文串判断开始，找到第一个不是回文串的左右字符
        // 由于只有1次机会，那么可以判断除去右字符时或者除去左字符后，剩下的子串是否是回文串
        int left = 0, right = s.length() - 1;
        while (left < s.length() / 2) {
            if (s.charAt(left) != s.charAt(right)) break;
            left++;
            right--;
        }
        return left == s.length() / 2
                || isPalindrome(s, left, right - 1)
                || isPalindrome(s, left + 1, right);
    }

    public boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }
}
