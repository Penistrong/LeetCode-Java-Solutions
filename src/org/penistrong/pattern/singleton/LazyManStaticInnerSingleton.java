package org.penistrong.pattern.singleton;

/**
 * 使用静态内部类实现懒汉单例
 * 由于静态内部类只有在被第一次调用时才会进行类加载，而JVM在进行类加载时会加锁，所以静态内部类里的类变量初始化时是线程安全的
 */
public class LazyManStaticInnerSingleton {

    public LazyManStaticInnerSingleton() {}

    private static class SingletonHolder extends Singleton {
        private static final Singleton singleton = new SingletonHolder();
    }

    public static Singleton getSingleton() {
        return SingletonHolder.singleton;
    }
}
