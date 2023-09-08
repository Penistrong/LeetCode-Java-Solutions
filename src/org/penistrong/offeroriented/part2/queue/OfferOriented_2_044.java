package org.penistrong.offeroriented.part2.queue;

import org.penistrong.template.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 剑指Offer2-044 二叉树每层的最大值
 */
public class OfferOriented_2_044 {
    // 利用队列层序遍历
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> maximums = new ArrayList<>();
        if (root == null)
            return maximums;
        int maxInPerLevel = Integer.MIN_VALUE;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int curLevelSize = queue.size();    // 快照当前层的节点个数
            int maxVal = Integer.MIN_VALUE;     // 当前层的最大值
            for (int i = 0; i < curLevelSize; i++) {
                TreeNode curNode = queue.poll();
                maxVal = Math.max(maxVal, curNode.val);
                if (curNode.left != null) queue.offer(curNode.left);
                if (curNode.right != null) queue.offer(curNode.right);
            }
            maximums.add(maxVal);
        }
        return maximums;
    }
}
