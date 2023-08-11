package org.penistrong.leetcode.twopointers;

/**
 * LeetCode 344 反转字符串
 * 双指针秒了
 */
public class LeetCode344 {
    public void reverseString(char[] s) {
        for (int left = 0, right = s.length - 1; left < right; left++, right--) {
            char tmp = s[right];
            s[right] = s[left];
            s[left] = tmp;
        }
    }
}
