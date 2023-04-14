package org.penistrong.template.common;

public class TestLRUCache {

    public static void main(String[] args) {
        // 建立一个最大容量为4的LRUCache
        // LRUCache<Integer, String> cache = new LRUCache<>(4);
        LRUCacheByLinkedHashMap<Integer, String> cache = new LRUCacheByLinkedHashMap<>(4);
        cache.put(1, "first element");
        cache.put(2, "second element");
        cache.put(3, "third element");
        cache.put(4, "fourth element");
        cache.printLRUSequence();

        // 访问3
        cache.get(3);
        cache.printLRUSequence();

        // 插入5,会移除最久没使用过的1
        cache.put(5, "fifth element");
        cache.printLRUSequence();
    }
}
