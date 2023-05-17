package org.penistrong.offeroriented.part2.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 剑指Offer2-017 包含所有字符的最短字符串
 */
public class OfferOriented_2_017 {

    public String minWindow(String s, String t) {
        // t是目标字符串，找到s中包含t的所有字符的最短连续子串，如果存在多个只需任意返回一个
        // 仍然使用哈希表+双指针遍历字符串s
        Map<Character, Integer> charFreqs = new HashMap<>();
        // 首先统计t中各字符及其出现次数
        for (int i = 0; i < t.length(); i++)
            charFreqs.put(t.charAt(i), charFreqs.getOrDefault(t.charAt(i), 0) + 1);
        // count记录t中字符的种数(而不是总个数)
        int left = 0, right = 0, maxLen = 0, count = charFreqs.size();
        int minLeft = 0, minRight = 0, minLen = Integer.MAX_VALUE;
        // 移动右指针，直到[left, right]子串包含t中所有字符(count==0时)
        // 然后收缩左指针，直到某次收缩会导致子串不包含t中所有字符，记录当前局部最短连续子串长度
        for (; right < s.length(); right++) {
            char c = s.charAt(right);
            if (charFreqs.containsKey(c)) {
                charFreqs.put(c, charFreqs.get(c) - 1);
                if (charFreqs.get(c) == 0)
                    count--;
            }
            // 当count为0时，左指针向右移动，缩小滑窗范围
            if (count == 0) {
                while (count == 0) {
                    char lc = s.charAt(left++);
                    if (charFreqs.containsKey(lc)) {    // 对于t中不包含的字符直接跳过
                        charFreqs.put(lc, charFreqs.get(lc) + 1);
                        if (charFreqs.get(lc) == 1) count++;
                    }
                }
                // 结束循环时，left指向滑窗实际起始点的后一个位置(因为left++，所以实际窗口长度为right - (left - 1) + 1)
                if (right - left + 2 < minLen) {
                    minLen = right - left + 2;
                    minLeft = left - 1;
                    minRight = right;
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minRight + 1);
    }
}
