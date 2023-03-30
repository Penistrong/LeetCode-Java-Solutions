package org.penistrong;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class JavaPlayGround {

    // ThreadLocalMap中的hash函数使用的斐波那契数
    // 每次放入一个变量，ThreadLocal.nextHashCode值就会增长一个斐波那契数
    // 可以使产生的哈希码分布的非常均匀
    private static final int HASH_INCREMENT = 0x61c88647;
    public List<String> msgs = new ArrayList<>();

    public static final ThreadLocal<JavaPlayGround> localVar = ThreadLocal.withInitial(JavaPlayGround::new);

    public static void add(String msg) {
        localVar.get().msgs.add(msg);
    }

    public static List<String> clear() {
        List<String> msgs = localVar.get().msgs;
        localVar.remove();

        System.out.println("After clear ThreadLocal Variables, the msgs' size is " + localVar.get().msgs.size());
        return msgs;
    }

    public static void main(String[] args) {
        JavaPlayGround.add("I Penistrong");
        System.out.println(localVar.get().msgs);
        JavaPlayGround.clear();

        // 测试斐波那契数
        int hashcode = 0;
        int capacity = 16;
        for (int i = 0; i < capacity; i++) {
            hashcode += HASH_INCREMENT;
            System.out.println(i + " 在桶中的位置: " + (hashcode & (capacity - 1)));
        }

        HashSet<Integer> node_set = new HashSet<>();
    }
}
