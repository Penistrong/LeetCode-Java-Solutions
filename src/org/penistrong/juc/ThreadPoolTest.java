package org.penistrong.juc;

import java.time.Instant;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池测试-ThreadPoolExecutor的七大参数
 * 1. int corePoolSize                  : 线程池核心线程数
 * 2. int maximumPoolSize               : 线程池最大线程数
 * 3. long keepAliveTime                : 外包线程存活的最长时间
 * 4. TimeUnit unit                     : 存活时间的时间单位
 * 5. BlockingQueue<Runnable> workQueue : 任务队列，存储等待执行的任务的阻塞队列
 * 6. ThreadFactory threadFactory       : 线程工厂，线程池创建线程的工厂类，一般默认
 * 7. RejectedExecutionHandler handler  : 拒绝策略，任务队列已满且外包线程也在全力工作时对于新提交任务的处理策略
 * 关于拒绝策略，为ThreadPoolExecutor的内置类，默认为AbortPolicy，提供了4种饱和策略
 * 1. AbortPolicy:          抛出`RejectedExecutionException`，拒绝新提交的任务
 * 2. CallerRunsPolicy:     让提交任务的调用者对应的线程自己去执行该被拒绝的任务
 * 3. DiscardPolicy:        静默策略，对于无法处理的新提交任务，直接丢弃不做任何处理
 * 4. DiscardOldestPolicy:  丢弃任务队列中最早的未被处理的任务，保证新提交任务可以入队
 */
public class ThreadPoolTest {

    public void test(){
        // 核心线程数设置的较少以测试线程池饱和策略
        // 任务队列容量为2，线程池最多同时存在4个任务，2个在执行中/2个在任务队列中等待
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                2,
                2000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        // 提交5个任务，由于任务队列上限为2，而线程池最大线程数为2
        // 采用DiscardOldestPolicy的情况下会丢弃任务队列里最早等待的任务
        for (int i = 0; i < 5; i++) {
            int idx = i;
            Thread task = new Thread(() -> {
                System.out.printf("[ %s ] is running... Time at %s\n", "Task " + idx, Instant.now());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("[ %s ] executed... Time at %s\n", "Task " + idx, Instant.now());
            });
            pool.execute(task);
        }
        // 关闭线程池的两大方法 shutdown() 与 shutdownNow()
        // 1. shutdown(): 线程池的状态变为`SHUTDOWN`，线程池不再接受新任务，但是所有任务都要执行完毕后才会彻底关闭
        // 2. shutdownNow(): 线程池的状态变为`STOP`，线程池会终止正在执行的任务，同时停止处理排队的任务并用List返回等待执行的任务
        pool.shutdown();
        pool.shutdownNow();
        while(!pool.isTerminated()){}
        System.out.println("All tasks in ThreadPoolExecutor have done");
    }

    public static void main(String[] args){
        new ThreadPoolTest().test();
    }
}
