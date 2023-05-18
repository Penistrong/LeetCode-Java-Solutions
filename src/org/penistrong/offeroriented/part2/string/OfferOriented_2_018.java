package org.penistrong.offeroriented.part2.string;

/**
 * 剑指Offer2-018 有效的回文
 */
public class OfferOriented_2_018 {

    public boolean isPalindrome(String s) {
        // 双指针一前一后，处理时可以跳过所有的空格和标点
        // 处理前先转为LowerCase，还要考虑数字字符
        s = s.toLowerCase();
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (right > left && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (left < right && (s.charAt(left++) != s.charAt(right--))) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        boolean res = new OfferOriented_2_018().isPalindrome("0P");
    }
}
