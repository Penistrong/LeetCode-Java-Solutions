package org.penistrong.offeroriented.part2.tree;

import org.penistrong.template.tree.TreeNode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 剑指Offer 2-048 二叉树的序列化与反序列化
 * 层序遍历编码为满二叉树，再层序遍历复原
 */
public class OfferOriented_2_048 {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "null";
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        StringBuilder str = new StringBuilder();
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.val == Integer.MIN_VALUE) {    // 空节点占位，不处理其为空的左右节点
                str.append("null").append(",");
                continue;
            }
            str.append(cur.val).append(",");
            if (cur.left != null) queue.offer(cur.left);
            else queue.offer(new TreeNode(Integer.MIN_VALUE));
            if (cur.right != null) queue.offer(cur.right);
            else queue.offer(new TreeNode(Integer.MIN_VALUE));
        }
        // 移除最后多余的","
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] vals = data.split(",");    // 相当于反序列化一颗满二叉树，注意每层的对应关系
        if (vals[0].equals("null")) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
        queue.offer(root);
        int idx = 1;    // 节点下标
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (!vals[idx].equals("null")) {
                cur.left = new TreeNode(Integer.parseInt(vals[idx]));
                queue.offer(cur.left);
            }
            idx++;
            if (!vals[idx].equals("null")) {
                cur.right = new TreeNode(Integer.parseInt(vals[idx]));
                queue.offer(cur.right);
            }
            idx++;
        }
        return root;
    }
}
