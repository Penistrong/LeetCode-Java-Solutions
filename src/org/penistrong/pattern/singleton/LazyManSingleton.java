package org.penistrong.pattern.singleton;

/**
 * 普通懒汉单例，类加载时不初始化单例对象，只有在第一次调用时才初始化，线程不安全
 * 多个线程同时调用getSingleton()方法时，可能会创建多个实例
 */
public class LazyManSingleton extends Singleton {
    private static Singleton singleton;

    public LazyManSingleton() {}

    public static Singleton getSingleton() {
        if (singleton == null) {
            singleton = new LazyManSingleton();
        }
        return singleton;
    }
}
