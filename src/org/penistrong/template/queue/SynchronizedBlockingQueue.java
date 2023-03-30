package org.penistrong.template.queue;

/**
 * 手写阻塞队列-基于Synchronized与wait()、notify()实现
 */
public class SynchronizedBlockingQueue<E> implements BlockingQueue<E>{

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

    /**
     * 队列为空时让Synchronized锁住的对象，阻塞读线程，唤醒写线程
     */
    private final Object notEmpty;

    /**
     * 队列已满时让Synchronized锁住的对象，阻塞写线程，唤醒读线程
     */
    private final Object notFull;

    public SynchronizedBlockingQueue() {
        this(DEFAULT_CAPACITY);
    }

    public SynchronizedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new NullPointerException();
        }
        this.capacity = capacity;
        this.items = new Object[capacity];
        // 初始所锁持有的对象
        notEmpty = new Object();
        notFull = new Object();

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
        // 队列已满时锁住notFull
        synchronized (notFull) {
            try {
                while (count == capacity) { // 队列塞满时，notFull对象一直等待
                    System.out.println(Thread.currentThread().getName()
                            + "[" + Thread.currentThread().getId() + "]"
                            + "队列已满，等待消费者消费["
                            + "capacity: " + capacity + ", "
                            + "count: " + count + "]");
                    notFull.wait();         // 阻塞写线程
                }
            }catch (InterruptedException ec) {
                ec.printStackTrace();
            }
        }

        // 队列未满时锁住notEmpty
        synchronized (notEmpty) {
            // 当前要入队的元素放在下标为count的位置
            items[offerIndex++] = e;
            if (offerIndex == capacity) {    // 下一个入队元素的索引等于容器大小，说明容器已满
                offerIndex = 0;              // 下一次出队的只能是队头元素，则索引置为0
            }
            count++;
            notEmpty.notify();  // 唤醒读线程
            return true;
        }
    }

    /**
     * 从阻塞队列头部取出元素并返回，队列为空时阻塞调用线程
     * @return
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public E take() {
        // 队列为空时锁住notEmpty
        synchronized (notEmpty) {
            try {
                while (count == 0) {
                    System.out.println(Thread.currentThread().getName()
                            + "[" + Thread.currentThread().getId() + "]"
                            + ": 队列为空，等待生产者生产["
                            + "capacity: " + capacity + ", "
                            + "count: " + count + "]");
                    notEmpty.wait();    //  阻塞读线程
                }
            } catch (InterruptedException ec) {
                ec.printStackTrace();
            }
        }
        // 队列不空
        synchronized (notFull) {
            Object obj = items[takeIndex++];
            // 循环到头部
            if (takeIndex == capacity) {
                takeIndex = 0;
            }
            count--;
            notFull.notify();   // 唤醒写线程
            return (E) obj;
        }
    }

    /**
     * 返回阻塞队列中的元素数量
     * @return
     */
    @Override
    public synchronized int size() {
        return this.count;
    }
}
