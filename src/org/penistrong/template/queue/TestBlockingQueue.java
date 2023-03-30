package org.penistrong.template.queue;

public class TestBlockingQueue {

    public static void main(String[] args) {

        // BlockingQueue<Integer> blockingQueue = new SynchronizedBlockingQueue<>();
        BlockingQueue<Integer> blockingQueue = new ReentrantLockBlockingQueue<>();

        Thread producerThread = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                blockingQueue.offer(i);
                System.out.println("生产者生产了: " + i);
            }
        }, "Producer");

        Thread consumerThread = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者消费了： " + blockingQueue.take());
            }
        }, "Consumer");

        producerThread.start();
        consumerThread.start();
    }
}
