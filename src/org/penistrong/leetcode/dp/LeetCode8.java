package org.penistrong.leetcode.dp;

/**
 * 字符串中的最长回文子串，下面为中心扩展这种直观方法的题解
 */
public class LeetCode8 {

    /**
     * 回文串即反着来也相同，那么首尾一定相同
     * 显然将s中的每个字符作为回文串的中心，向两边进行扩展，直到碰到边界无法扩展时再停下
     * 返回最长的回文串即可
     * 由于长度为1的串本身就是回文串，而长度为2的串左右两个元素都相同那么也是回文串
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int left = 0, right = 0;    // 保存当前最长的回文子串的左右下标
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandFromCenter(s, i, i);             // 计算长度为1的子串s[i]向两边扩展的回文串长度
            int len2 = expandFromCenter(s, i, i + 1);   // 计算长度为2的子串s[i:i+1]向两边扩展的回文串长度
            int len = Math.max(len1, len2);
            if (len > right - left) {       // 如果长度大于right - left，说明可以更新子串长度了
                left = i - (len - 1) / 2;   // 当前i为回文中心下标，往左找到起始下标
                right = i + len / 2;
            }
        }
        return s.substring(left, right + 1);
    }

    // 给定串s, 和左闭右闭的子区间，向两边扩展获得最长回文串
    private int expandFromCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 返回长度，在最后一轮计算中s[left] != s[right]，即实际的回文串区间为s[left+1:right-1]
        // 则长度len = right - 1 - (left + 1) + 1 = right - left - 1
        return right - left - 1;
    }
}
