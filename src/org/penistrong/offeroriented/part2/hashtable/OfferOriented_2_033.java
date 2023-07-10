package org.penistrong.offeroriented.part2.hashtable;

import java.util.*;

/**
 * 剑指Offer2-033 变位词组
 */
public class OfferOriented_2_033 {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();
        // 对每个词按照自然顺序排序，以排序后的词作为键，存储相同键的变位词组
        for (String str: strs) {
            char[] cArr = str.toCharArray();
            Arrays.sort(cArr);
            String sortedStr = new String(cArr);
            groups.putIfAbsent(sortedStr, new ArrayList<>());
            groups.get(sortedStr).add(str);
        }
        return new ArrayList<>(groups.values());
    }
}
