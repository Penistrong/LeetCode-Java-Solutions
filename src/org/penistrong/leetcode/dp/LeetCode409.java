package org.penistrong.leetcode.dp;

import java.util.HashMap;
import java.util.Map;

public class LeetCode409 {
    public int longestPalindrome(String s) {
        // 朴实的算法，用一个哈希表保存每个字符出现的次数
        // 选择一个出现了奇数次的字符(这个字符的偶数个也可以加入串中)和其他出现偶数次的字符就能组成最长的回文串
        Map<Character, Integer> charFreqs = new HashMap<>();
        // 每当当前统计字符频率为偶数，则成对计入回文串中
        int len = 0;
        for(int i = 0; i < s.length(); i++) {
            charFreqs.put(s.charAt(i), charFreqs.getOrDefault(s.charAt(i), 0) + 1);
            if (charFreqs.get(s.charAt(i)) == 2) {
                len += 2;
                charFreqs.put(s.charAt(i), 0);
            }
        }
        // 拼接成的回文串长度小于原串，说明多了某些奇数频率字符，随便挑一个就行
        return len < s.length() ? len + 1 : len;
    }
}
