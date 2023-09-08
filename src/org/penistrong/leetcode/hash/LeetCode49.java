package org.penistrong.leetcode.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LeetCode49 字母异位词分组
 * Java Stream API Collectors.groupingBy()方法的使用
 */
public class LeetCode49 {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 自定义一种编码方式，利用素数的性质，每个字母对应一个素数，分解每个strs[i]的各个字母，用其对应的素数相乘就可以得到一个绝对不会重复的键
        // int[] factors = new int[]{2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,91,97};
        // 当用例过多时会有错误
        return new ArrayList<>(Arrays.stream(strs)
                .collect(Collectors.groupingBy(str -> {
                    int[] counts = new int[26];
                    for (char c: str.toCharArray())
                        counts[c - 'a']++;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 26; i++) {
                        if (counts[i] != 0) {
                            sb.append((char) ('a' + i));
                            sb.append(counts[i]);
                        }
                    }
                    return sb.toString();
                })).values());
    }
}
