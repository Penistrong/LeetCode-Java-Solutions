package org.penistrong.offeroriented.part1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OfferOriented38 {

    // 没剪枝的暴力DFS
    class FirstSolution {
        private Set<String> permutes;

        public String[] permutation(String s) {
            // 排列的数量即A(n, n)
            // 题目限定字符串长度 n \in [1, 8]，因此最多也就是8!个排列
            permutes = new HashSet<>();
            dfs("", s);
            return permutes.stream().toArray(String[]::new);
        }

        private void dfs(String prefix, String str) {
            if (str == null || str.equals("")) {
                // 将prefix加入Set中
                permutes.add(prefix);
                return;
            }
            int len = str.length();
            for (int i = 0; i < len; i++) {
                dfs(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1));
            }
        }
    }

    // 带回溯剪枝的DFS
    class FinalSolution {
        List<String> res = new ArrayList<>();
        char[] c;   // 将输入的str转换为字符数组，方便索引，同时每次调换元素后作为新的字符串排列

        public String[] permutation(String s) {
            c = s.toCharArray();
            dfs(0);
            return res.toArray(String[]::new);
        }

        private void dfs(int pos) {
            if (pos == c.length - 1) {  // 添加当前排列方案
                res.add(String.valueOf(c));
                return;
            }

            Set<Character> charset = new HashSet<>();
            for (int i = pos; i < c.length; i++) {
                if (charset.contains(c[i])) continue;   // 当前下标位置用过该字符，剪枝
                charset.add(c[i]);
                swap(i, pos);       // 交换c[i]和c[pos]，以期不同排列
                dfs(pos + 1);
                swap(i, pos);       // 复原
            }
        }

        private void swap(int pos1, int pos2) {
            char tmp = c[pos1];
            c[pos1] = c[pos2];
            c[pos2] = tmp;
        }
    }
}
