package org.penistrong.offeroriented.part2.string;

/**
 * 剑指Offer2-014 字符串中的变位词
 */
public class OfferOriented_2_014 {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;
        // 利用数组存储每个小写英文字母在s1中的出现次数
        int[] counts = new int[26];
        int size = s1.length();
        // 建立一个长为s1.length()的滑窗，在s2上滑动，判定每个滑窗内的子串是否是s1的一个排列
        for (int i = 0; i < size; i++) {
            counts[s1.charAt(i) - 'a']++;   // 遍历s1
            counts[s2.charAt(i) - 'a']--;   // 遍历s2的初始滑动窗口
        }
        if (isAllZero(counts)) return true;
        // 开始滑动
        for (int i = size; i < s2.length(); i++) {
            counts[s2.charAt(i - size) - 'a']++;    // 左端点挪出去后将对应字符出现次数++
            counts[s2.charAt(i) - 'a']--;           // 右端点囊括进来后将对应字符出现次数--
            if (isAllZero(counts)) return true;
        }
        return false;
    }

    private boolean isAllZero(int[] counts) {
        for (int count : counts)
            if (count != 0) return false;
        return true;
    }
}
