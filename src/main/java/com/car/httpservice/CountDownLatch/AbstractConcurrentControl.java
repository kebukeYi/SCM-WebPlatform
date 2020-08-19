package com.car.httpservice.CountDownLatch;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/12  11:28
 * @Description
 */
public abstract class AbstractConcurrentControl {

    public AtomicLong longCounter = new AtomicLong(0);
    public AtomicInteger intCounter = new AtomicInteger(0);

    /**
     * 默认并发线程数为2000个，子类可重写
     */
    private static final int DEFAULT_CONCURRENT_CONTROL = 10;
    private CountDownLatch latch;

    /**
     * 并发线程数量，默认2000
     */
    private int concurrentThreadNum;
    private ExecutorService threadPool;

    public AbstractConcurrentControl() {
        this(DEFAULT_CONCURRENT_CONTROL);
    }

    public AbstractConcurrentControl(int concurrentThreadNum) {
        this.concurrentThreadNum = concurrentThreadNum;
        latch = new CountDownLatch(concurrentThreadNum);
        threadPool = new ThreadPoolExecutor(concurrentThreadNum, concurrentThreadNum, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 并发执行线程
     */
    public final void process() {
        for (int i = 0; i < concurrentThreadNum; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        blockingCode();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            // 最后一个线程时可以打个断点
            if (i == concurrentThreadNum - 1) {
                latch.countDown();
            } else {
                latch.countDown();
            }
        }
        blockingMainThread();//等待所有线程工作完
        threadPool.shutdown();//关闭线程池
    }

    /**
     * 并发代码
     */
    protected abstract void blockingCode();

    /**
     * 并发数据
     */
    protected abstract <T> T encapsulatingData();

    /**
     * 阻塞主线程，防止JVM关闭，不建议使用Xxx.class.wait，可以使用TimeUnit.SECONDS.sleep(200);
     * 如果使用TimeUnit.SECONDS.sleep(200)，要保证休眠时长足够跑完你的程序，否则会出现异常，因为JVM已经关闭，而测试的线程可能没有执行完成
     */
    public abstract void blockingMainThread();

}
