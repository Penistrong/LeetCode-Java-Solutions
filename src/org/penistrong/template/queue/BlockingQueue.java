package org.penistrong.template.queue;

/**
 * 手写阻塞队列的接口，可基于Synchronized或ReentrantLock实现
 */
public interface BlockingQueue<E> {

    /**
     * 往阻塞队列尾部添加一个元素，队列充满时阻塞调用线程
     * @param e
     * @return
     */
    boolean offer(E e);

    /**
     * 从阻塞队列头部取出元素并返回，队列为空时阻塞调用线程
     * @return
     */
    E take();

    /**
     * 返回阻塞队列中的元素数量
     * @return
     */
    int size();
}
