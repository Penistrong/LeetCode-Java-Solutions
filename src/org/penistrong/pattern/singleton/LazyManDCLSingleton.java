package org.penistrong.pattern.singleton;

/**
 * 双重检查锁定-懒汉单例，降低了锁粒度
 */
public class LazyManDCLSingleton extends Singleton{
    // 注意DCL单例必须添加volatile关键字，保证单例创建后的可见性
    private static volatile Singleton singleton;

    public LazyManDCLSingleton() {}

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (LazyManDCLSingleton.class) {
                if (singleton == null) {
                    singleton = new LazyManDCLSingleton();
                }
            }
        }
        return singleton;
    }
}
