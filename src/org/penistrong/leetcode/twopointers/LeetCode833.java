package org.penistrong.leetcode.twopointers;

import java.util.HashMap;
import java.util.Map;

public class LeetCode833 {
    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        int k = indices.length;
        // 一共k次替换操作，且要保证"并行"完成
        // key为索引替换位置，value为要替换的pattern在各个数组里的索引
        Map<Integer, Integer> patterns = new HashMap<>();
        for (int i = 0; i < k; i++) {
            if (s.startsWith(sources[i], indices[i])) {
                patterns.put(indices[i], i);
            }
        }
        // 根据原始串s的长度收集所有的pattern
        StringBuilder res = new StringBuilder();
        int j = 0, i = 0;
        while (j < s.length()) {
            if (patterns.containsKey(j)) {
                i = patterns.get(j);
                res.append(targets[i]);
                j += sources[i].length();
            } else {
                res.append(s.charAt(j));
                j++;
            }
        }
        return res.toString();
    }

    public static void main(String[] args) {
        String s = "abcd";
        int[] indices = new int[]{0, 2};
        String[] sources = new String[]{"a", "cd"};
        String[] targets = new String[]{"eee", "ffff"};
        new LeetCode833().findReplaceString(s, indices, sources, targets);
    }
}
