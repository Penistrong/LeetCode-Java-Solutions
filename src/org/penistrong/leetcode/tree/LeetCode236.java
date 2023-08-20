package org.penistrong.leetcode.tree;

import org.penistrong.template.tree.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * LeetCode 236 二叉树的最近公共祖先
 * 递归
 */
public class LeetCode236 {

    // 由于是最近公共祖先，一定满足该祖先的左右子树分别包含目标pq两节点
    // 且它们不可能在同一侧的子树中，否则就不满足"最近"的定义
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // dfs先序遍历，遇到任一节点时返回，向上回溯，直到另一个节点在某个子树根节点的异侧
        // 找到后向上返回该最近公共祖先
        if (root == null || root == p || root == q)
            return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) return right;
        if (right == null) return left;
        // left 和 right都不为空，说明这两个节点都在当前子树root的异侧(递归到找到该最近公共祖先后，再返回即可)
        return root;
    }

}
