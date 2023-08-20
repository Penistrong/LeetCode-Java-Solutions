package org.penistrong.pattern.singleton;

/**
 * 饿汉单例，类创建时立马初始化单例对象，线程安全，但是浪费内存
 */
public class HungryManSingleton extends Singleton {
    private static final Singleton singleton = new HungryManSingleton();

    public HungryManSingleton() {}

    public static Singleton getSingleton() {
        return singleton;
    }
}
