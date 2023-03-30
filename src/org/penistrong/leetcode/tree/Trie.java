package org.penistrong.leetcode.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 208 字典树(前缀树)
 */
public class Trie {

    class TrieNode {
        public Map<Character, TrieNode> childNodes;
        public boolean isLeaf;

        public TrieNode () {
            isLeaf = false;
            childNodes = new HashMap<>();
//            for (int i = 0; i < 26; i++) {
//                childNodes.put((char) ('a' + i), null);
//            }
        }
    }

    public TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // 向字典树中插入一个单词
    public void insert(String word) {
        TrieNode tmp = root;
        // 对单词的每个字符
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (tmp.childNodes.get(c) == null) {
                tmp.childNodes.put(c, new TrieNode());
            }
            tmp = tmp.childNodes.get(c);
        }
        // 直到叶子节点，将这个叶子节点的isLeaf标记为true
        tmp.isLeaf = true;
    }

    // 判断树中是否有单词
    public boolean search(String word) {
        TrieNode tmp = root;
        for (int i = 0; i < word.length(); i++) {
            if (tmp == null) break;
            tmp = tmp.childNodes.get(word.charAt(i));
        }
        return tmp == null ? false : tmp.isLeaf;
    }

    // 判断字典树是否有给定前缀
    public boolean startsWith(String prefix) {
        TrieNode tmp = root;
        for (int i = 0; i < prefix.length(); i++) {
            if (tmp == null) break;
            tmp = tmp.childNodes.get(prefix.charAt(i));
        }
        // tmp不为空说明存在前缀返回true
        return tmp != null;
    }
}
