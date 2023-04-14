package org.penistrong.template.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 利用JDK提供的LinkedHashMap，继承LinkedHashMap，并调用父类构造函数，传入accessOrder = true
 * 然后重写removeEldestEntry(Map.Entry<K, V> eldest) 即可, 函数体判断当前大小是否超过给定的大小上限
 * 由于LinkedHashMap是将刚刚访问的节点尾插到顺序链表中，头部head才是最近最久未访问的节点
 * @param <K>
 * @param <V>
 */
public class LRUCacheByLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public LRUCacheByLinkedHashMap (int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.capacity;
    }

    public void printLRUSequence() {
        System.out.println(this.keySet());
    }
}
