package org.penistrong.leetcode.twopointers;

/**
 * LeetCode 2337 移动片段得到字符串
 */
public class LeetCode2337 {
    public boolean canChange(String start, String target) {
        // 先判断合法性
        // 1. L和R这两种字符数量在原始字符串和目标字符串中相同
        // 2. 每个L在target中的下标都小于等于start中的下标, 每个R在target中的下标都大于等于start中的下标
        int size = start.length();
        int i = 0, j = 0;   // 双指针分别指向start和target的头部
        // 在 '_' 与 '_' 形成的间隔中匹配每个字符
        while (i < size && j < size) {
            while (i < size && start.charAt(i) == '_')
                i++;
            while (j < size && target.charAt(j) == '_')
                j++;
            if (i < size && j < size) {
                // 如果该间隔中的两字符不是同一字符，肯定无法实现
                if (start.charAt(i) != target.charAt(j))
                    return false;
                char c = start.charAt(i);
                // 在该间隔中，如果当前字符是L，而L在target中的下标大于在source中的下标，那么无论L怎么左移也不行
                // R同理
                if ((c == 'L' && i < j) || (c == 'R' && i> j))
                    return false;
                i++;
                j++;
            }
        }
        // 检测剩余字符里还有咩有多余的非‘_’字符
        while (i < size) {
            if (start.charAt(i) != '_')
                return false;
            i++;
        }
        while (j < size) {
            if (target.charAt(j) != '_')
                return false;
            j++;
        }
        return true;
    }
}
