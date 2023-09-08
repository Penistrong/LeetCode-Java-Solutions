package org.penistrong.offeroriented.part2.queue;

import org.penistrong.template.tree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 剑指Offer2-045 二叉树最底层最左边的值
 */
public class OfferOriented_2_045 {

    public int findBottomLeftValue(TreeNode root) {
        // 很简单喏，既然是最底层最左边，直接层序遍历，而且每层的访问顺序是从右到左
        int aim = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            aim = cur.val;
            if (cur.right != null) queue.offer(cur.right);  // 先右
            if (cur.left != null) queue.offer(cur.left);    // 再左
        }
        return aim;
    }
}
