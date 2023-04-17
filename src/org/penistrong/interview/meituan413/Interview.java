package org.penistrong.interview.meituan413;

/**
 * 输入升序数组，构建平衡二叉查找树
 * LeetCode108，仔细分析是非常简单的一道题，做的时候脑子宕机没写出来，反手被挂，真菜！
 */
public class Interview {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
        }
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        // 因为是有序的，直接二分，中间为子树根节点，左区间和右区间分别是左右子树
        // 这样就能保证高度平衡
        return buildTree(nums, 0, nums.length - 1);
    }

    // 采用归并排序的思路，每次取中间节点作为子树
    private TreeNode buildTree(int[] nums, int left, int right) {
        if (left == right) {
            return new TreeNode(nums[left]);
        }
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        if (mid - 1 >= left)
            root.left = buildTree(nums, left, mid - 1);
        if (mid + 1 <= right)
            root.right = buildTree(nums, mid + 1, right);
        return root;
    }
}
