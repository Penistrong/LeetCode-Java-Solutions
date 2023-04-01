package org.penistrong.interview.meituan;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

public class Solution5 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();       // n <= 50000
        int[] p = new int[n + 1];   // 2<=i<=n
        for (int i = 2; i <= n; i++)
            p[i] = in.nextInt();    // p[i]表示第i个节点的父亲是谁 (第i=1个节点是根节点)
        int[] c = new int[n + 1];   // c[i] = 1表示 第i个节点是红色 c[i]=2为绿色
        for (int i = 1; i <= n; i++)
            c[i] = in.nextInt();
        // 已经保证树的任意非叶子节点，要么就没儿子，要么就有两个儿子
        // 先扫描p，记录所有有2个儿子的父节点编号
        Map<Integer, Integer[]> parents_with_child = new HashMap<>();
        for (int i = 2; i <= n; i++) {
            if (parents_with_child.containsKey(p[i])) {
                Integer[] child = parents_with_child.get(p[i]);
                child[1] = i;
                parents_with_child.put(p[i], child);
            } else {
                Integer[] child = new Integer[2];
                child[0] = i;
                parents_with_child.put(p[i], child);
            }
        }
        // 对每个节点，判断其是否在Map中，如果在说明它就有2个儿子，其值根据自己的颜色和2个儿子的值决定
        int[] node_val = new int[n + 1];
        for (int i = 1; i <= n; i++)    // 初始化，每个节点都当作没儿子的状态，值都为1
            node_val[i] = 1;
        for (int i = n; i >=1; i--) {   // 自底向上更新
            if (parents_with_child.containsKey(i)) {
                Integer[] children = parents_with_child.get(i);
                if (c[i] == 1) {    // 红色节点，儿子价值之和
                    node_val[i] = node_val[children[0]] + node_val[children[1]];
                } else {
                    node_val[i] = node_val[children[0]] ^ node_val[children[1]];
                }
            }
        }
        System.out.println(node_val[1]);
    }
}
