package org.penistrong.offeroriented.part1;

import org.penistrong.template.tree.TreeNode;

/**
 * 54. 二叉搜索树的第 k 大节点
 */
public class OfferOriented54 {
    private int k;  // 全局变量，保存遍历次数
    private int kth;// 目标的第k大的数

    public int kthLargest(TreeNode root, int k) {
        // 递归查找，由于是BST，使用逆向的中序遍历先访问右子树在访问左子树就可以降序排序
        // 所以只需要只需要中序遍历到第k个节点即可
        this.k = k;
        inOrder(root);
        return kth;
    }

    private void inOrder(TreeNode root) {
        if (root == null)
            return;
        inOrder(root.right);
        if (--k == 0) {
            kth = root.val;
            return;
        }
        inOrder(root.left);
    }
}
