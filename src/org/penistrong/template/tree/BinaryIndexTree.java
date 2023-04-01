package org.penistrong.template.tree;

/**
 * 二叉索引树，也即树状数组，支持查询数组中的前缀和，且在改变某些位置上的元素后不会导致O(N)的后续前缀和级联更新
 * 树状数组引入的思想：将前缀和(即前x项元素的和)拆分为几段区间里的后缀和来计算
 * 注意，在x的二进制表示中，lowBit(x)提取的是x最低位的1所代表的数
 * 以x=7为例，前7项和等于:
 * 1. 先计算 lowBit(7) = 1
 *    即tree[7]表示第7个元素往前数共计1个元素(就是arr[7]本身)这段区间的元素和
 * 2. 再计算 lowBit(7 - lowBit(7) = 6) = 2
 *    即tree[6]表示第6个元素往前数共计2个元素(arr[6]与arr[5)这段区间的元素和
 * 3. 再计算 lowBit(6 - lowBit(6) = 4) = 4
 *    即tree[4]表示第4个元素往前数共计4个元素(arr[4, 3, 2, 1])这段区间的元素和
 * 4. 最后计算 lowBit(4 - lowBit(4) = 0) = 0
 *    没必要计算了
 * 即 前7项和可以通过多趟lowBit将 7 这个数字拆成了 4 + 2 + 1 这些区间里的和(即二进制中1的数位对应的2的幂)
 */
public class BinaryIndexTree {

    // tree[i]表示 从第i个元素 往前数共计 lowBit(i) 个元素 ，这段区间的和
    // 实际代表的后缀和，以i为右端点的长度为lowBit(i)的后缀和
    // 注意 i \in [1, n]，所以初始化时是tree[n+1]
    private int[] tree;

    // 数组元素总个数
    private int n;

    public BinaryIndexTree(int n) {
        this.n = n;
        this.tree = new int[n + 1];
    }

    /**
     * lowBit函数，求出给定整数 x 的二进制表示中，从右向左数最低位的1, 并返回其对应的int类型整数
     * 注意，其他位都是0，所以lowBit返回的必是2的幂
     * @param x
     * @return
     */
    public static int lowBit(int x) {
        return x & -x;
    }

    // 更新后缀和数组 tree[]
    // 更新原数组arr[x]位置的值为val
    // 那么还要更新下标x对应的所有包含下标x上这个元素的后缀和区间
    // 所以每一趟都要加上lowBit(x)向后更新
    public void update(int x, int val) {
        while (x <= n) {
            tree[x] += val;
            x += lowBit(x);
        }
    }

    // 查询前缀和
    // 每一趟减去lowBit(x)向前查询
    public int query(int x) {
        int ans = 0;
        while (x != 0) {
            ans += tree[x];
            x -= lowBit(x);
        }
        return ans;
    }
}
