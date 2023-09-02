package org.penistrong.offeroriented.part2.tree;

import org.penistrong.template.tree.TreeNode;

/**
 * 剑指offer 2 049 二叉树中从根节点到叶节点的路径数字之和
 */
public class OfferOriented_2_049 {
    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode root, int sum) {
        if (root == null) return 0;
        sum = sum * 10 + root.val;
        // 左子树和右子树都为空时，直接返回sum
        if (root.left == null && root.right == null)
            return sum;
        return dfs(root.left, sum) + dfs(root.right, sum);
    }
}
