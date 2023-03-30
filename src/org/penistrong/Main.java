package org.penistrong;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException,
                   NoSuchMethodException, InvocationTargetException {

        List<Integer> list = new ArrayList<>();
        list.add(100);
        // list.add("100");
        System.out.println(list.get(0));    // 100

        Class<?> clazz = list.getClass();
        Method listAdd = clazz.getMethod("add", Object.class);  // 反正任何类型都是Object的子类
        // 用反射机制创建了一个String对象，但是无法强转成Integer，运行时会抛出ClassCastException
        // Integer obj = (Integer) clazz.getDeclaredConstructor().newInstance();
        listAdd.invoke(list, "哈哈我越过泛型检查了!");
        System.out.println(list.get(1));    // 哈哈我越过泛型检查了!

        BlockingQueue<Integer> q = new LinkedBlockingQueue<>();

    }

}
