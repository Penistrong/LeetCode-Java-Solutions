package org.penistrong.pattern.singleton;

/**
 * 同步方法懒汉单例，但是性能存在问题，静态同步方法锁住类对象，锁粒度太高影响执行效率
 */
public class LazyManSynchronizedSingleton extends Singleton {

    private static Singleton singleton;

    public LazyManSynchronizedSingleton() {}

    public static synchronized Singleton getSingleton() {
        if (singleton == null) {
            singleton = new LazyManSynchronizedSingleton();
        }
        return singleton;
    }
}
