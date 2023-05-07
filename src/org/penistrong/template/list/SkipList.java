package org.penistrong.template.list;

import java.util.Arrays;

/**
 * 跳表实现，常用于查找无重复有序数据，范围查找的效率高于红黑树
 * 在REDIS-ZSet的底层实现中也使用了跳表
 * 参考<a href="https://github.com/wangzheng0822/algo/blob/master/java/17_skiplist/SkipList.java">王争-数据结构与算法之美</a>
 */
public class SkipList {

    // 各级节点晋升为更高级索引的概率，Redis的ZSet采用0.25f(每4个晋升1个索引)
    private static final float SKIPLIST_P = 0.5f;
    // 跳表的最大层数(包括最底层的原始链表数据层)
    private static final int MAX_LEVEL = 16;

    // 记录当前跳表层数
    private int levelCount = 1;

    // 链表头部, dummyHead
    private Node head = new Node();

    // 跳表的内部节点数据结构
    static class Node {
        private int data = -1;
        private Node[] forwards = new Node[MAX_LEVEL];  // 存储当前节点在各层跳表里对应的**后继节点**引用
        private int maxLevel = 0;   // 当前节点最多可存在于maxLevel层跳表里

        public Node () {}

        public Node (int data, int maxLevel){
            this.data = data;
            this.maxLevel = maxLevel;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{ data: ");
            sb.append(data);
            sb.append("; levels: ");
            sb.append(maxLevel);
            sb.append(" }");
            return sb.toString();
        }
    }

    /**
     * 插入元素时，随机指定该元素需要插入到第几层里(level=1为原始链表数据层)
     * 每一层的晋升概率为 SKIPLIST_P，randomLevel()函数会随机生成1~MAX_LEVEL之间的数
     * @return
     */
    private int randomLevel() {
        int level = 1;

        while (Math.random() < SKIPLIST_P && level < MAX_LEVEL)
            level += 1;

        return level;
    }

    // 在跳表中寻找目标值
    public Node find(int target) {
        Node p = head;
        // 从最顶级索引开始查找，在每层索引里找到最后一个小于目标值的索引节点，然后下沉到次级索引继续查找
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < target)
                p = p.forwards[i];
        }
        if (p.forwards[0] != null && p.forwards[0].data == target)
            return p.forwards[0];
        else return null;
    }

    // 向跳表中插入目标值
    public void insert(int target) {
        int level = randomLevel();  // 利用概率算法生成当前插入的新节点需要插入到几层跳表里
        Node newNode = new Node(target, level);
        Node[] update = new Node[level];
        Arrays.fill(update, head);  // 每层搜索路径的初始值都是dummyHead

        // 将每层跳表里小于当前插入目标值的最大值节点记入update[]数组里
        Node p = head;
        for (int i = level - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < target)
                p = p.forwards[i];
            update[i] = p;  // 利用update[]存储搜索路径里每一层进行下沉的节点，后面插入时需要将这些节点的后继设置为目标值节点
        }

        // 插入新目标值节点，设置前一个节点的后继
        for (int i = 0; i < level; ++i) {
            newNode.forwards[i] = update[i].forwards[i];    // 新节点的后继指向前一个节点的后继
            update[i].forwards[i] = newNode;                // 前一个节点的后继节点设置为新节点
        }

        // 更新跳表高度(本次插入时随机生成的高度大于跳表当前高度)
        if (levelCount < level) levelCount = level;
    }

    // 在各层跳表里删除目标值对应的节点
    public void delete(int target) {
        Node[] update = new Node[levelCount];
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < target)
                p = p.forwards[i];
            update[i] = p;  // 利用update[]存储搜索路径里目标节点的前驱节点，后面删除时需要将这些节点的后继删除
        }

        // 依次删除各层里的目标节点
        if (p.forwards[0] != null && p.forwards[0].data == target) {
            for (int i = levelCount - 1; i >= 0; --i) {
                if (update[i].forwards[i] != null && update[i].forwards[i].data == target) {
                    update[i].forwards[i] = update[i].forwards[i].forwards[i];
                }
            }
        }

        // 如果本次删除导致某层(甚至某几层)索引里的索引节点都被删空，更新高度
        while (levelCount > 1 && head.forwards[levelCount] == null)
            levelCount--;
    }

    // 自顶向下按层打印各级跳表
    public void printAll() {
        Node[] c = head.forwards;   // dummyHead不用打印
        Node[] d = c;
        int maxLevel = c.length;
        for (int i = maxLevel - 1; i >= 0; --i) {
            // 打印每层各个节点，依次寻找对应节点的后驱
            boolean notFirstNodeInCurLevel = false;
            do {
                if (notFirstNodeInCurLevel) System.out.print("----->");
                else {
                    System.out.print("@Level " + i + " : ");
                    notFirstNodeInCurLevel = true;
                }
                System.out.print("[" + (d[i] != null ? d[i].data : null) + "]");
            } while (d[i] != null && (d = d[i].forwards)[i] != null);
            System.out.println();
            d = c;
        }
    }

    public static void main(String[] args) {
        SkipList sl = new SkipList();
        for (int i = 1; i <= 10; ++i)
            sl.insert(i);
        sl.printAll();
    }

}
