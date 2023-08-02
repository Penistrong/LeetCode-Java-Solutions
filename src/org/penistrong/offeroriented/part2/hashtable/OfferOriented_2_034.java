package org.penistrong.offeroriented.part2.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * 剑指Offer2-034 外星语言是否排序
 */
public class OfferOriented_2_034 {

    public boolean isAlienSorted(String[] words, String order) {
        // Mapping alien alphabet to english alphabet
        Map<Character, Character> dict = new HashMap<>();
        for (int i = 0; i < order.length(); i++) {
            dict.put(order.charAt(i), (char)('a' + i));
        }
        String thresh = "0";    // 从排序意义上的最小字符串开始
        for (String word: words) {
            char[] cArr = word.toCharArray();
            // 依次映射到English Alphabet，然后直接按原字典序排序即可
            for (int i = 0; i < cArr.length; i++)
                cArr[i] = dict.get(cArr[i]);
            word = String.valueOf(cArr);
            if (word.compareTo(thresh) < 0)
                return false;
            thresh = word;      // 更新门限值，下一个字符串如果排序低于门限值说明没有按照外星语言字典序
        }
        return true;
    }
}
