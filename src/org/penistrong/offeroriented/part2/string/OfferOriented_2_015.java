package org.penistrong.offeroriented.part2.string;

import java.util.ArrayList;
import java.util.List;

/**
 * 剑指Offer2-015 字符串中的所有变位词
 * 014进阶版
 */
public class OfferOriented_2_015 {

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> anagrams = new ArrayList<>();
        // 注意p是模式串，s是目标长串
        if (s.length() < p.length()) return anagrams;
        // 返回变位词子串的起始索引，结合014可知，每次出现只需要记录滑窗的左端点
        int[] counts = new int[26];
        int size = p.length();
        // 建立一个长为p.length()的滑窗，在s上滑动，判定每个滑窗内的子串是否是p的一个排列
        for (int i = 0; i < size; i++) {
            counts[p.charAt(i) - 'a']++;   // 遍历p
            counts[s.charAt(i) - 'a']--;   // 遍历s的初始滑动窗口
        }
        if (isAllZero(counts)) anagrams.add(0);
        // 开始滑动
        for (int i = size; i < s.length(); i++) {
            counts[s.charAt(i - size) - 'a']++;    // 左端点挪出去后将对应字符出现次数++
            counts[s.charAt(i) - 'a']--;           // 右端点囊括进来后将对应字符出现次数--
            if (isAllZero(counts)) anagrams.add(i - size + 1);  // 记录当前左端点
        }
        return anagrams;
    }

    private boolean isAllZero(int[] counts) {
        for (int count : counts)
            if (count != 0) return false;
        return true;
    }
}
