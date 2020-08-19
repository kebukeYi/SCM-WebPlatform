package com.car.httpservice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/9  0:14
 * @Description
 */
public class HttpTestSend {


    private static final int DEFAULT_CONCURRENT_CONTROL = 5000;


    static class AnalogUser implements Runnable {
        CountDownLatch latch;
        String deviceId;

        public AnalogUser(CountDownLatch countDownLatch, String deviceId) {
            this.deviceId = deviceId;
            this.latch = countDownLatch;
        }

        @Override
        public void run() {
            long starTime = 0;
            try {
                starTime = System.currentTimeMillis();
                latch.await();
                String ctrl = "(A" + deviceId + "008986089860987654321020020810510599883070+180N22.123456E104.234567000.1356460,00,9234,1234,-100)";
                System.out.println("请求开始了: " + deviceId);
                Object object = HttpSendCrtl.sendloc(ctrl);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            Long t = endTime - starTime;
            System.out.println(t / 1000F + "秒");
            System.out.println(deviceId + "OVER");
        }
    }

    /**
     * corePoolSize与maximumPoolSize相等，即其线程全为核心线程，是一个固定大小的线程池，是其优势；
     * keepAliveTime = 0 该参数默认对核心线程无效，而FixedThreadPool全部为核心线程；
     * workQueue 为LinkedBlockingQueue（无界阻塞队列），队列最大值为Integer.MAX_VALUE。如果任务提交速度持续大余任务处理速度，会造成队列大量阻塞。因为队列很大，很有可能在拒绝策略前，内存溢出。是其劣势；
     * FixedThreadPool的任务执行是无序的；
     * 适用场景：可用于Web服务瞬时削峰，但需注意长时间持续高峰情况造成的队列阻塞。
     */
    public static void main(String[] args) {
        int start = 1111100000;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(DEFAULT_CONCURRENT_CONTROL, DEFAULT_CONCURRENT_CONTROL, 5, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(DEFAULT_CONCURRENT_CONTROL));
        CountDownLatch latch = new CountDownLatch(1);
        //模拟连接
        for (int i = 1; i <= DEFAULT_CONCURRENT_CONTROL; i++) {
//            String deviceID=monitorTipsMap.get(i).getDeviceNumber();
            //100002089117142
            String deviceID = start + i + "";
            AnalogUser analogUser = new AnalogUser(latch, deviceID);
            executor.submit(analogUser);
        }
        //计数器減一  所有线程释放 并发访问。
        latch.countDown();
        executor.shutdown();

    }


}
