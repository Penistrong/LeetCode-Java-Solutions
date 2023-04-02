package org.penistrong.template.tree;

/**
 * 线段树，常用于解决区间问题，包括但不限于区间最大值、最小值、和等基于区间的子问题
 */
public class SegmentTree {

    private int[] segmentTree;
    private int n;

    public SegmentTree(int[] nums) {
        n = nums.length;
        segmentTree = new int[n << 2];  // 开4n大小，足以保存所有区间节点
        build(0, 0, n - 1, nums);
    }

    // 按照模板方法抽出来的，更新线段树上节点的值，该值可以是区间和等不同目标，继承线段树重写该方法即可
    protected void updateByConcreteAim(int k) {
        // 默认是区间和
        segmentTree[k] = segmentTree[2 * k + 1] + segmentTree[2 * k + 2];
    }

    // 建树，在线段树的k号节点处保存区间[left, right]的区间和
    // 如果left == right，则segmentTree[k]就是叶子节点，叶子结点和就是nums[left]
    // 如果left != right，则需要对k节点的左子树和右子树计算区间和，分别代表区间[left, (left + right)/2]和[(left + right)/2, right]
    // 由于运算符优先级问题，算术运算符优先级高于位移运算符，所以要记得打括号，或者只写算术运算符，让编译器自己优化
    private void build(int k, int left, int right, int[] nums) {
        // 自底向上建树
        if (left == right) {
            segmentTree[k] = nums[left];
            return;
        }
        int mid = left + ((right - left) >> 1);   // 用位运算加速，要记得打括号！
        build(2 * k + 1, left, mid, nums);     // 建立左子树
        build(2 * k + 2, mid + 1, right, nums);    // 建立右子树

        //segmentTree[k] = segmentTree[2 * k + 1] + segmentTree[2 * k + 2]; // 获得左右儿子的子区间的和
        updateByConcreteAim(k);
    }

    // 单点更改，改变nums数组里下标为index的元素的值为val，注意同时还要给出线段树的节点号k和区间左右端点
    private void change(int index, int val, int k, int left, int right) {
        // 自底向上传播更新
        if (left == right) {    // 找到需要更新的叶子节点k，其所代表的区间就是[index, index]
            segmentTree[k] = val;
            return;
        }
        int mid = left + ((right - left) >> 1);
        // 根据mid区间中点判断nums[index]所在的线段树节点, index <= mid则说明在左子树里，否则在右子树里
        if (index <= mid)
            change(index, val, 2 * k + 1, left, mid);
        else
            change(index, val, 2 * k + 2, mid + 1, right);
        // 更新本节点里储存的区间和
        // segmentTree[k] = segmentTree[2 * k + 1] + segmentTree[2 * k + 2];
        updateByConcreteAim(k);
    }

    // 区间求和，给定nums数组的某段区间[begin, end]，将这个大区间拆分为线段树节点对应的小区间，并自底向上求和
    // k表示当前遍历的线段树节点编号
    private int rangeSum(int begin, int end, int k, int left, int right) {
        if (begin == left && end == right)  // 当前节点k对应的left和right刚好是所求区间，则直接返回
            return segmentTree[k];
        int mid = left + ((right - left) >> 1);
        if (end <= mid)         // 所求区间右端点比当前节点代表区间的中点要小，去左子树里求和
            return rangeSum(begin, end, 2 * k + 1, left, mid);
        else if (begin > mid)   // 所求区间左端点比中点大，去右子树里求和
            return rangeSum(begin, end, 2 * k + 2, mid + 1, right);
        else    // 所求区间刚好横跨中点区域，同时是当前k号节点所代表的区间的真子集，用mid切分，同时计算左子树和右子树里的部分区间和
            return rangeSum(begin, mid, 2 * k + 1, left, mid)
                    + rangeSum(mid + 1, end, 2 * k + 2, mid + 1, right);
    }

    public void update(int index, int val) {
        // 调用内建函数change，单点更新数组index处的元素
        change(index, val, 0, 0, n - 1);
    }

    public int sumRange(int left, int right) {
        // 调用内建函数rangeSum，计算给定区间和
        return rangeSum(left, right, 0, 0, n - 1);
    }
}
