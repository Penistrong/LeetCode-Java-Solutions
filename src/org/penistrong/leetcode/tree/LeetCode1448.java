package org.penistrong.leetcode.tree;

import org.penistrong.template.tree.TreeNode;

/**
 * LeetCode 1448 统计二叉树中好节点的数目
 */
public class LeetCode1448 {
    int num = 0;

    public int goodNodes(TreeNode root) {
        dfs(root, Integer.MIN_VALUE);
        return num;
    }

    private void dfs(TreeNode root, int maxInPath) {
        if (root == null) {
            return;
        }
        if (root.val >= maxInPath) {
            num++;
        }
        maxInPath = Math.max(root.val, maxInPath);
        dfs(root.left, maxInPath);
        dfs(root.right, maxInPath);
    }
}
