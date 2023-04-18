package org.penistrong.offeroriented.part1;

import org.penistrong.template.tree.TreeNode;

/**
 * 55Ⅱ. 平衡二叉树判定
 */
public class OfferOriented55 {
    private boolean notBalanced;

    // 后序遍历+深度判定，时间复杂度O(N)，空间复杂度O(N)，即函数调用栈的最大深度
    public boolean isBalanced(TreeNode root) {
        // 利用高度函数递归计算左子树和右子树的高度，再判断差值是否超过1
        heightTree(root);
        return !notBalanced;
    }

    // 其实可以剪枝，一旦发现一个不平衡的，则直接返回-1
    public int heightTree(TreeNode root) {
        if (root == null)
            return 0;
        int leftHeight = heightTree(root.left);
        int rightHeight = heightTree(root.right);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            notBalanced = true;
        }
        return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
    }

    // 后序遍历剪枝版
    public int postOrder(TreeNode root) {
        if (root == null)
            return 0;
        int leftHeight = postOrder(root.left);
        if (leftHeight == -1) return -1;
        int rightHeight = postOrder(root.right);
        if (rightHeight == -1) return -1;

        return Math.abs(leftHeight - rightHeight) <= 1 ? Integer.max(leftHeight, rightHeight) + 1 : -1;
    }

    public boolean isBalancedSolution2(TreeNode root) {
        return postOrder(root) != -1;
    }
}
