package org.penistrong.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程交叉打印测试
 */
public class ThreadPrintTest {

    static class SynchronizedThread implements Runnable {
        private static int count = 0;   // 用一个静态类变量记录当前打印的数字，这样实例化出的其他线程也共享这个变量
        private static final Object lock = new Object();    // 不同实例化出的对象共享同一个静态锁对象
        private final int limit;

        public SynchronizedThread (int limit) {
            this.limit = limit;
        }

        @Override
        public void run() {
            while (count <= limit) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + ": \t" + count++);
                    lock.notify();
                    if (count <= limit) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class ReentrantLockThread {
        private final Lock lock;

        private final int limit;

        public ReentrantLockThread (Lock lock, int limit) {
            this.lock = lock;
            this.limit = limit;
        }

        /**
         * 用Lock对应的Condition实现线程交替打印
         * @param num 起始数字
         * @param curr 当前线程的Condition
         * @param next 下一个线程的Condition
         */
        public void print(int num, Condition curr, Condition next) {
            while (num <= limit) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + ": \t" + num);
                    num += 2;
                    next.signal();
                    if (num <= limit)
                        curr.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("================== Using Synchronized ===================");
        Thread odd = new Thread(new SynchronizedThread(100), "Odd Thread");
        Thread even = new Thread(new SynchronizedThread(100), "Even Thread");
        even.start();
        odd.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("================== Using ReentrantLock ===================");
        ReentrantLock lock = new ReentrantLock();
        ReentrantLockThread printer = new ReentrantLockThread(lock, 100);
        Condition oddCondition = lock.newCondition();
        Condition evenCondition = lock.newCondition();
        even = new Thread(() -> printer.print(0, evenCondition, oddCondition), "Even Thread");
        odd = new Thread(() -> printer.print(1, oddCondition, evenCondition), "Odd Thread");
        even.start();
        odd.start();
    }
}
