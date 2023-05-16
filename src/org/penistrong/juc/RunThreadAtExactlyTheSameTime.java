package org.penistrong.juc;

import java.time.Instant;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

/**
 * Java多线程基础-腾讯CDG面试题: 创建10个线程后，通常都是通过调用Thread.start()方法，有什么办法能够让它们几乎同时开始运行?
 * 根据爱因斯坦的狭义相对论，不可能完全保证两个空间分离的独立事件确切出现在同一时间
 * 以Hotspot VM为例，在设计时jvm的线程跟操作系统线程是一一对应关系
 * 但是可以近似达成这种效果，比如说利用J.U.C下的并发工具类比如CyclicBarrier、CountDownLatch、Phaser等近似实现
 * 这些实现只是说将线程启动后阻塞在某个位置，再一起开始
 */
public class RunThreadAtExactlyTheSameTime {

    // 基于AQS的CountDownLatch, 缺点是不能复用
    public class WorkerWithCountDownLatch extends Thread {
        private CountDownLatch latch;

        public WorkerWithCountDownLatch(String name, CountDownLatch latch) {
            this.latch = latch;
            this.setName(name);
        }

        @Override
        public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the latch...\n", getName());
                latch.await();   // 阻塞在这里，等待CountDownLatch设置的倒计时结束后再被唤醒
                System.out.printf("[ %s ] started at : %s\n", getName(), Instant.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void usingCountDownLatch() throws InterruptedException {
        System.out.println("===============================================");
        System.out.println("        >>> Using CountDownLatch <<<<");
        System.out.println("===============================================");
        // 倒计时设为1，即创建的10个Worker内部的run方法都因wait()阻塞后，调用一次latch.countDown()即可减小倒计时
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            WorkerWithCountDownLatch worker = new WorkerWithCountDownLatch("Worker" + i, latch);
            worker.start();
        }
        Thread.sleep(1000); // 模拟线程正式被唤醒前主线程在进行其他工作

        System.out.println("-----------------------------------------------");
        System.out.println(" Now release the latch:");
        System.out.println("-----------------------------------------------");
        // 调用1次倒计时，使得所有线程同步被唤醒
        latch.countDown();
    }

    public class WorkerWithCyclicBarrier extends Thread {
        private CyclicBarrier barrier;

        public WorkerWithCyclicBarrier (String name, CyclicBarrier barrier) {
            this.barrier = barrier;
            this.setName(name);
        }

        @Override
        public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the latch...\n", getName());
                barrier.await();   // 阻塞在这里，调用CyclicBarrier.await()，然后内部的count计数器-1
                System.out.printf("[ %s ] started at : %s\n", getName(), Instant.now());
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void usingCyclicBarrier() throws BrokenBarrierException, InterruptedException{
        System.out.println("===============================================");
        System.out.println("        >>> Using CyclicBarrier <<<<");
        System.out.println("===============================================");

        // 传入参数parties会使得CyclicBarrier内部的count计数器初始化为相同数值
        // 每调用一次barrier.await()就会使计数器-1
        CyclicBarrier barrier = new CyclicBarrier(11);
        for (int i = 0; i < 10; i++) {
            WorkerWithCyclicBarrier worker = new WorkerWithCyclicBarrier("Worker" + i, barrier);
            worker.start();
        }
        Thread.sleep(1000);
        System.out.println("-----------------------------------------------");
        System.out.println(" Now open the barrier:");
        System.out.println("-----------------------------------------------");
        // 第11次调用barrier.await()，计数器减至0
        barrier.await();
    }

    public class WorkerWithPhaser extends Thread {
        private Phaser phaser;

        public WorkerWithPhaser (String name, Phaser phaser) {
            this.phaser = phaser;
            this.setName(name);
            // 注册当前Thread类到phaser中，
            phaser.register();
        }

        @Override
        public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the latch...\n", getName());
                phaser.arriveAndAwaitAdvance();   // 阻塞在这里，调用CyclicBarrier.await()，然后内部的count计数器-1
                System.out.printf("[ %s ] started at : %s\n", getName(), Instant.now());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    private void usingPhaser() throws IllegalStateException, InterruptedException {
        System.out.println("==========================================");
        System.out.println("          >>> Using Phaser <<<");
        System.out.println("==========================================");

        Phaser phaser = new Phaser();
        phaser.register();
        for (int i = 0; i < 10; i++) {
            WorkerWithPhaser worker = new WorkerWithPhaser("Worker" + i, phaser);
            worker.start();
        }

        Thread.sleep(1000);
        System.out.println("-----------------------------------------------");
        System.out.println(" Now open the phaser barrier:");
        System.out.println("-----------------------------------------------");
        // 主线程是首个Phaser注册成员，前面10个线程arrive后当前主线程也到场参加会议
        phaser.arriveAndAwaitAdvance();
    }

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        // 测试CountDownLatch实现
        new RunThreadAtExactlyTheSameTime().usingCountDownLatch();
        Thread.sleep(1000);
        // 测试CyclicBarrier实现
        new RunThreadAtExactlyTheSameTime().usingCyclicBarrier();
        Thread.sleep(1000);
        // 测试Phaser实现
        new RunThreadAtExactlyTheSameTime().usingPhaser();
    }
}
