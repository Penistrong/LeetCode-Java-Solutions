package org.penistrong.offeroriented.part2.hashtable;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Random;

/**
 * 剑指Offer2-030 插入、删除和随机访问都是O(1)的容器
 */
public class OfferOriented_2_030 {
    /**
     * Your RandomizedSet object will be instantiated and called as such:
     * RandomizedSet obj = new RandomizedSet();
     * boolean param_1 = obj.insert(val);
     * boolean param_2 = obj.remove(val);
     * int param_3 = obj.getRandom();
     */
    public class RandomizedSet {
        // 利用HashMap进行O(1)查找，key为存储的实际值，value为该实际值在变长数组中的索引下标
        private HashMap<Integer, Integer> innerMap;

        // 利用变长数组(ArrayList)实现O(1)随机访问
        // 我的初步想法是直接用Map.keySet()方法，但是生成KeySet()也是需要O(N)时间的
        private List<Integer> vals;

        private Random random;

        /** Initialize your data structure here. */
        public RandomizedSet() {
            this.innerMap = new HashMap<>();
            this.vals = new ArrayList<>();
            this.random = new Random();
        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if (innerMap.containsKey(val))
                return false;
            innerMap.put(val, vals.size());
            vals.add(val);  // 每次都是将新元素加到变长列表尾部
            return true;
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if (!innerMap.containsKey(val)) return false;
            int idx = innerMap.get(val);
            int lastEle = vals.get(vals.size() - 1);    // 获取末尾元素
            vals.set(idx, lastEle);                     // 用末尾元素覆盖被删除的元素
            innerMap.put(lastEle, idx);                 // 更新原末尾元素的下标
            vals.remove(vals.size() - 1);               // 直接删除末尾元素
            innerMap.remove(val);
            return true;
        }

        /** Get a random element from the set. */
        public int getRandom() {
            return vals.get(random.nextInt(vals.size()));
        }
    }
}
