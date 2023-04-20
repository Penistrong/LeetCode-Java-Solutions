package org.penistrong.template.queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockBlockingQueue<E> implements BlockingQueue<E>{

    private static final int DEFAULT_CAPACITY = 10;
    private int capacity;

    // 盛放队列元素的对象数组
    private Object[] items;

    // 队列里的实际元素数量
    private int count;

    // 下一个入队元素要被放置的索引
    private int offerIndex;

    // 下一个出队元素的索引
    private int takeIndex;

    // 使用可重入锁实现
    private ReentrantLock lock;

    // 配合可重入锁，读条件，队列为空则阻塞读线程，唤醒写线程
    private Condition notEmpty;

    // 配合可重入锁，写条件，队列已满则阻塞写线程，唤醒读线程
    private Condition notFull;

    public ReentrantLockBlockingQueue() {
        this(DEFAULT_CAPACITY);
    }

    public ReentrantLockBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new NullPointerException();
        }
        this.capacity = capacity;
        this.items = new Object[capacity];

        // 采用公平可重入锁(排队获取)
        lock = new ReentrantLock(true);
        // 初始化锁持有的对象
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();

        count = 0;
        offerIndex = 0;
        takeIndex = 0;
    }

    /**
     * 往阻塞队列尾部添加一个元素，队列充满时阻塞调用线程
     * @param e
     * @return
     */
    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        // 获取锁
        lock.lock();
        try {
            // 队列已满时阻塞当前写线程，释放notFull条件对象
            while (count == capacity) { // 队列塞满时，notFull对象一直等待
                System.out.println(Thread.currentThread().getName()
                        + "[" + Thread.currentThread().getId() + "]"
                        + "队列已满，等待消费者消费["
                        + "capacity: " + capacity + ", "
                        + "count: " + count + "]");
                notFull.await();
            }

            // 队列未满时当前即可放入元素
            // 当前要入队的元素放在下标为count的位置
            items[offerIndex++] = e;
            if (offerIndex == capacity) {    // 下一个入队元素的索引等于容器大小，说明容器已满
                offerIndex = 0;              // 下一次出队的只能是队头元素，则索引置为0
            }
            count++;
            notEmpty.signal();  // 唤醒一个因队列为空而阻塞的读线程，通知它可以取出元素了
            return true;
        } catch (InterruptedException ec) {
            notEmpty.signal();  // 被中断时也唤醒其他读线程
        } finally {
            lock.unlock();      // 释放锁
        }


        return false;
    }

    /**
     * 从阻塞队列头部取出元素并返回，队列为空时阻塞调用线程
     * @return
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public E take() {
        lock.lock();
        try {
            // 队列为空时阻塞当前读线程，释放notEmpty条件对象
            while (count == 0) {
                System.out.println(Thread.currentThread().getName()
                        + "[" + Thread.currentThread().getId() + "]"
                        + ": 队列为空，等待生产者生产["
                        + "capacity: " + capacity + ", "
                        + "count: " + count + "]");
                notEmpty.await();
            }

            // 一旦不空则可出队
            Object obj = items[takeIndex++];
            // 循环到头部
            if (takeIndex == capacity) {
                takeIndex = 0;
            }
            count--;
            notFull.signal();   // 唤醒一个因队列已满而阻塞的写线程，通知它可以将其待入队元素入队了
            return (E) obj;

        } catch (InterruptedException ec) {
            notFull.signal();   // 锁不住了唤醒写线程
        } finally {
            lock.unlock();      // 释放锁
        }
        return null;
    }

    /**
     * 返回阻塞队列中的元素数量
     * @return
     */
    @Override
    public int size() {
        // 获取可重入锁
        lock.lock();
        try {
            return this.count;
        } finally {
            lock.unlock();
        }
    }
}
