package com.car.httpservice.CountDownLatch;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/12  11:31
 * @Description
 */
public class RequestTest extends AbstractConcurrentControl {

    public RequestTest() {
        super();
    }

    private MyService myService = new MyService();

    /**
     * 单元测试，调用process方法
     */
    @Test
    public void test() {
        process();
    }

    @Override
    public void blockingCode() {
        Data data = (Data) encapsulatingData();
        myService.process(data);
    }


    @SuppressWarnings("unchecked")
    @Override
    protected <T> T encapsulatingData() {
        Data data = new Data();
        String name = "a";
        int i = intCounter.incrementAndGet();
        data.setId(i);
        data.setName(name + ":" + i);
        return (T) data;
    }

    @Override
    public void blockingMainThread() {
        try {
//            TimeUnit.SECONDS.sleep(200);
            System.out.println("开始睡眠");
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (Exception e) {
        }
    }

    public class Data {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class MyService {
        public void process(Data data) {
            System.out.println(data.getId() + "-" + data.getName());
        }
    }
}
