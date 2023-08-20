package org.penistrong.offeroriented.part2.queue;

import org.penistrong.template.tree.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 剑指Offer 2-043 完全二叉树插入器
 */
public class OfferOriented_2_043 {
    class CBTInserter {
        private Queue<TreeNode> queue;
        private TreeNode root;
        // 由于完全二叉树的性质，除了叶节点那层每层都是满的
        // 层序遍历时只要找到第一个子节点不满的某个节点，就可以将新节点插入到该节点的子树里
        public CBTInserter(TreeNode root) {
            this.root = root;
            this.queue = new ArrayDeque<>();
            queue.offer(root);
            while (queue.peek().left != null && queue.peek().right != null) {
                TreeNode node = queue.poll();   // 取出左右子树都存在的队头
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        public int insert(int v) {
            // 插入时，队头肯定是子树不满的节点
            TreeNode parent = queue.peek();
            if (parent.left == null) {
                parent.left = new TreeNode(v, null, null);
            }
            else if (parent.right == null) {
                parent.right = new TreeNode(v, null, null);
                queue.poll();
                queue.offer(parent.left);
                queue.offer(parent.right);
            }
            return parent.val;
        }

        public TreeNode get_root() {
            return root;
        }
    }

/**
 * Your CBTInserter object will be instantiated and called as such:
 * CBTInserter obj = new CBTInserter(root);
 * int param_1 = obj.insert(v);
 * TreeNode param_2 = obj.get_root();
 */
}
