package org.penistrong.jvm;

public class SynchronizedExample {

    public void method() {
        synchronized (SynchronizedExample.class) {
            System.out.println("我是同步代码块");
        }
    }

    public synchronized void synchronizedMethod() {
        System.out.println("我是同步方法");
    }
}
