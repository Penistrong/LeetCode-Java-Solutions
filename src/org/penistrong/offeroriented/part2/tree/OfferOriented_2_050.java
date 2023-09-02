package org.penistrong.offeroriented.part2.tree;

import org.penistrong.template.tree.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 剑指offer 2 050 二叉树中和为某一值的路径数量
 * 前缀和 + DFS + 回溯
 */
public class OfferOriented_2_050 {
    public int pathSum(TreeNode root, int targetSum) {
        // 对于每个子树，搜索其中是否包含有一条从子树根节点起始的路径
        // 哈希表存储前缀和的出现次数，前缀和与目标值的差值如果出现在哈希表中则说明加上当前访问节点的值就满足目标值
        Map<Long, Integer> prefix = new HashMap<>();
        prefix.put(0L, 1);   // 初始: 前缀和为0的次数为1

        return dfs(root, targetSum, prefix, 0L);
    }

    private int dfs(TreeNode root, int target, Map<Long, Integer> prefix, long path) {
        if (root == null) return 0;
        path += root.val;
        // 当前前缀和(到目前节点为止的路径)减去目标值在哈希表中有记录，说明途中有某一节点到当前节点的非前缀路径满足题意
        int sum = prefix.getOrDefault(path - target, 0);
        prefix.put(path, prefix.getOrDefault(path, 0) + 1);
        sum += dfs(root.left, target, prefix, path);
        sum += dfs(root.right, target, prefix, path);
        // 复原当前访问到的节点，把它从键值对里的值减去1，防止不同子树里的节点干扰
        prefix.put(path, prefix.get(path) - 1);
        return sum;
    }
}
