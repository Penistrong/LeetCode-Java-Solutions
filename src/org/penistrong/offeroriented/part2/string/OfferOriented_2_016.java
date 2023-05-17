package org.penistrong.offeroriented.part2.string;

import java.util.HashSet;
import java.util.Set;

/**
 * 剑指Offer2-016 不含重复字符的最长连续子串
 */
public class OfferOriented_2_016 {

    /**
     * 利用双指针确定连续子串，当记录字符是否出现过的哈希集合不存在对应字符时，右指针++
     * 一旦碰到重复字符，就将左指针向右移动，直到找到该重复字符后继续移动使得子串中不包含重复字符
     */
    public int lengthOfLongestSubstring(String s) {
        Set<Character> isOccur = new HashSet<>();
        int left = 0, right = 0, len = s.length(), maxLen = 0;
        // 右指针不断挪动
        for (; right < len; right++) {
            char c = s.charAt(right);
            while (isOccur.contains(c)) {   // 当出现重复字符时，移动左指针直到不存在重复字符
                isOccur.remove(s.charAt(left++));
            }
            isOccur.add(c);
            maxLen = Integer.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}
