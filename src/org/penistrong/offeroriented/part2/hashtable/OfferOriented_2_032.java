package org.penistrong.offeroriented.part2.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * 剑指Offer2-032 有效的变位词
 */
public class OfferOriented_2_032 {

    public boolean isAnagram(String s, String t) {
        // 字符顺序相同也不能视作互为变位词，直接用equals判断即可
        if (s.length() != t.length() || s.equals(t)) return false;
        Map<Character, Integer> counts = new HashMap<>();
        for (char c: s.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        for (char c: t.toCharArray()) {
            if (!counts.containsKey(c) || counts.get(c) == 0)
                return false;
            counts.put(c, counts.get(c) - 1);
        }
        return true;
    }
}
