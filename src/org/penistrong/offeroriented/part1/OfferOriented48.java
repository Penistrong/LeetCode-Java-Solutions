package org.penistrong.offeroriented.part1;

import java.util.HashMap;
import java.util.Map;

public class OfferOriented48 {
    public int lengthOfLongestSubstring(String s) {
        // 经典动规
        // dp[i]表示以第i个字符结尾的最长不包含重复字符的子串长度
        // 状态转移方程: dp[i] = dp[i-1] + 1, if s[i] not contains in s[xxx:i-1]
        // 如果s[i]出现过，那就要计算s[i]和它上次出现过的位置之间的距离d
        // 如果d 小于等于 dp[i-1]，说明s[i]出现在了以s[i-1]结尾的最长子串中，那么dp[i]就更新为d(除去那个字符)
        // 如果d 大于 dp[i-1]，那么s[i-1]结尾的最长子串中不包含上次出现过的s[i]，那么还是以dp[i]=dp[i-1]+1更新
        int len = s.length(), maxLen = 0;   // 不用Integer.MIN_VALUE的原因是，空串要返回0
        int[] dp = new int[len + 1];
        // 为了方便求距离d,用一个hashmap存储每个字符最后一次出现的索引即可
        Map<Character, Integer> lastPos = new HashMap<>();
        int prevPos = -1;   // 遍历到的字符上一次出现的索引下标，-1表示没出现过
        for (int i = 1; i <= len; i++) {
            prevPos = lastPos.getOrDefault(s.charAt(i - 1), -1);
            if (prevPos == -1) {    // 没出现过，直接更新
                dp[i] = dp[i - 1] + 1;
            } else {                // 出现过，计算距离d
                int dist = i - 1 - prevPos;             // 距离是不包括s[i-1]的!!!
                if (dist <= dp[i - 1]) dp[i] = dist;    // 上次出现位置在s[i-1]结尾的最长子串中
                else dp[i] = dp[i - 1] + 1;             // 上次出现位置在s[i-1]结尾的最长子串外
            }
            lastPos.put(s.charAt(i - 1), i - 1);        // 更新字符最后出现的位置
            maxLen = Integer.max(maxLen, dp[i]);        // 更新最长长度
        }
        return maxLen;
    }

    public static void main(String[] args) {
        String s = "abcabcbb";
        OfferOriented48 test = new OfferOriented48();
        int len = test.lengthOfLongestSubstring(s);
    }
}
