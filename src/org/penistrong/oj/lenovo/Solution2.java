package org.penistrong.oj.lenovo;

import java.util.*;

/**
 * 树是一张n个点n-1条边的无向联通图，每两个点都有唯一的一条简单路径。
 * 有根树是指以其中一个点为根节点的树，叶子节点是指除根节点外度数为1的节点，一个点的度数是指与其相连的点的个数。
 * 有根树上，一个点的深度是指其与根节点之间的简单路径的边数。
 * 在某一棵以1为根的有根树上，有两个节点a，b上各存在一只毛毛虫。这两只毛毛虫只会往深度更大的点前进，当毛毛虫走到叶子节点时会停下。
 * 设第一只毛毛虫可能走到的节点为P1，第二只毛毛虫可能走到的节点为P2，想要知道二元组(P1，P2)的个数(P1可以等于p2)。
 * 一共有Q次询问。
 * <p>
 * 输入描述：
 * 第一行两个正整数n，Q(1 ≤ n，Q ≤ 50000)
 * 第二行n-1个正整数f2,f3…fn,(1 < f¡ ≤ i)，表示树上节点i与fi之间有一条边。第三行Q个正整数a1,a2…,aQ(1 ≤ a¡ ≤ n);
 * 第四行Q个正整数b1,b2,…bQ(1 ≤ b¡ ≤ n，a¡不等于b¡) 第三行和第四行表示a¡，b¡是第i个查询对应的两只毛毛虫所在的节点
 * <p>
 * 输出描述：
 * 为了避免输出量较大，你需要输出所有询问的答案的异或和。
 * <p>
 * 用例:
 * 8 4
 * 1 1 2 2 3 3 3
 * 4 2 1 5
 * 5 3 2 8
 * <p>
 * 输出:
 * 12
 */
public class Solution2 {

    public static void main(String[] args) {
        new Solution2().solve();
    }

    int n, Q;
    Map<Integer, List<Integer>> tree;   // 记录f_i对应的所有子节点
    int[] leaves;                       // 记录以节点i为根的子树中叶子节点的个数

    public void solve() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        Q = in.nextInt();
        tree = new HashMap<>();
        leaves = new int[n + 1];    // 编号从1~n
        for (int i = 2; i <= n; i++) {
            int f_i = in.nextInt();
            List<Integer> children = tree.getOrDefault(f_i, new ArrayList<>());
            children.add(i);
            tree.put(f_i, children);
        }
        dfs(1);             // 从根节点开始递归
        int[] a = new int[Q], b = new int[Q];
        for (int i = 0; i < Q; i++)
            a[i] = in.nextInt();
        for (int i = 0; i < Q; i++)
            b[i] = in.nextInt();
        // 进行Q次查询
        int ans = 0;
        for (int i = 0; i < Q; i++)
            ans ^= leaves[a[i]] * leaves[b[i]];
        System.out.println(ans);
    }

    private int dfs(int curNode) {
        if (!tree.containsKey(curNode)) {
            leaves[curNode] = 1;
            return 1;
        }
        int sum = 0;
        for (Integer child : tree.get(curNode)) {
            sum += dfs(child);
        }
        leaves[curNode] = sum;
        return sum;
    }
}
