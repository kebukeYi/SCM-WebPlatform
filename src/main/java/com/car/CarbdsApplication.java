package com.car;

import com.car.domain.MonitorTips;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@MapperScan(basePackages = "com.car.mapper")
@EnableSwagger2
public class CarbdsApplication {

    public static Map<String, MonitorTips> MonitorTipsmap = new HashMap<String, MonitorTips>();
    public static Map<String, String> commandList = new HashMap<String, String>();

    public static void main(String[] args) {

        SpringApplication.run(CarbdsApplication.class, args);
//        String data = "\"[" +
//                "{\\\"name\\\":\\\"唐山供销社\\\",\\\"stationID\\\":null,\\\"type\\\":\\\"line\\\",\\\"smooth\\\":false,\\\"itemStyle\\\":{\\\"normal\\\":{\\\"color\\\":\\\"#125CA1\\\",\\\"lineStyle\\\":{\\\"width\\\":4,\\\"type\\\":\\\"solid\\\"}}},\\\"showAllSymbol\\\":\\\"true\\\",\\\"data\\\":[" +
//                "{\\\"name\\\":\\\"2019/9/17 20:00:00\\\",\\\"value\\\":\\\"50.5\\\",\\\"pm10\\\":\\\"103\\\",\\\"pm25\\\":\\\"52\\\"},{\\\"name\\\":\\\"2019/9/17 21:00:00\\\",\\\"value\\\":\\\"47.8\\\",\\\"pm10\\\":\\\"113\\\",\\\"pm25\\\":\\\"54\\\"},{\\\"name\\\":\\\"2019/9/18 4:00:00\\\",\\\"value\\\":\\\"44.1\\\",\\\"pm10\\\":\\\"68\\\",\\\"pm25\\\":\\\"30\\\"},{\\\"name\\\":\\\"2019/9/18 5:00:00\\\",\\\"value\\\":\\\"41.4\\\",\\\"pm10\\\":\\\"70\\\",\\\"pm25\\\":\\\"29\\\"},{\\\"name\\\":\\\"2019/9/18 6:00:00\\\",\\\"value\\\":\\\"55.0\\\",\\\"pm10\\\":\\\"60\\\",\\\"pm25\\\":\\\"33\\\"},{\\\"name\\\":\\\"2019/9/18 7:00:00\\\",\\\"value\\\":\\\"41.0\\\",\\\"pm10\\\":\\\"61\\\",\\\"pm25\\\":\\\"25\\\"},{\\\"name\\\":\\\"2019/9/18 8:00:00\\\",\\\"value\\\":\\\"44.1\\\",\\\"pm10\\\":\\\"59\\\",\\\"pm25\\\":\\\"26\\\"},{\\\"name\\\":\\\"2019/9/18 9:00:00\\\",\\\"value\\\":\\\"34.5\\\",\\\"pm10\\\":\\\"55\\\",\\\"pm25\\\":\\\"19\\\"},{\\\"name\\\":\\\"2019/9/18 10:00:00\\\",\\\"value\\\":\\\"20.4\\\",\\\"pm10\\\":\\\"54\\\",\\\"pm25\\\":\\\"11\\\"},{\\\"name\\\":\\\"2019/9/18 11:00:00\\\",\\\"value\\\":\\\"16.3\\\",\\\"pm10\\\":\\\"49\\\",\\\"pm25\\\":\\\"8\\\"},{\\\"name\\\":\\\"2019/9/18 12:00:00\\\",\\\"value\\\":\\\"23.1\\\",\\\"pm10\\\":\\\"26\\\",\\\"pm25\\\":\\\"6\\\"},{\\\"name\\\":\\\"2019/9/18 13:00:00\\\",\\\"value\\\":\\\"22.7\\\",\\\"pm10\\\":\\\"22\\\",\\\"pm25\\\":\\\"5\\\"},{\\\"name\\\":\\\"2019/9/18 14:00:00\\\",\\\"value\\\":\\\"29.4\\\",\\\"pm10\\\":\\\"17\\\",\\\"pm25\\\":\\\"5\\\"},{\\\"name\\\":\\\"2019/9/18 15:00:00\\\",\\\"value\\\":\\\"56.0\\\",\\\"pm10\\\":\\\"25\\\",\\\"pm25\\\":\\\"14\\\"},{\\\"name\\\":\\\"2019/9/18 16:00:00\\\",\\\"value\\\":\\\"47.8\\\",\\\"pm10\\\":\\\"23\\\",\\\"pm25\\\":\\\"11\\\"},{\\\"name\\\":\\\"2019/9/18 8:00:00\\\",\\\"value\\\":\\\"26.7\\\",\\\"pm10\\\":\\\"60\\\",\\\"pm25\\\":\\\"16\\\"},{\\\"name\\\":\\\"2019/9/18 9:00:00\\\",\\\"value\\\":\\\"25.4\\\",\\\"pm10\\\":\\\"59\\\",\\\"pm25\\\":\\\"15\\\"},{\\\"name\\\":\\\"2019/9/18 10:00:00\\\",\\\"value\\\":\\\"39.2\\\",\\\"pm10\\\":\\\"51\\\",\\\"pm25\\\":\\\"20\\\"},{\\\"name\\\":\\\"2019/9/18 11:00:00\\\",\\\"value\\\":\\\"35.0\\\",\\\"pm10\\\":\\\"40\\\",\\\"pm25\\\":\\\"14\\\"},{\\\"name\\\":\\\"2019/9/18 12:00:00\\\",\\\"value\\\":\\\"35.0\\\",\\\"pm10\\\":\\\"40\\\",\\\"pm25\\\":\\\"14\\\"},{\\\"name\\\":\\\"2019/9/18 13:00:00\\\",\\\"value\\\":\\\"24.3\\\",\\\"pm10\\\":\\\"37\\\",\\\"pm25\\\":\\\"9\\\"},{\\\"name\\\":\\\"2019/9/18 14:00:00\\\",\\\"value\\\":\\\"19.4\\\",\\\"pm10\\\":\\\"31\\\",\\\"pm25\\\":\\\"6\\\"},{\\\"name\\\":\\\"2019/9/18 15:00:00\\\",\\\"value\\\":\\\"48.0\\\",\\\"pm10\\\":\\\"25\\\",\\\"pm25\\\":\\\"12\\\"}," +
//                "{\\\"name\\\":\\\"唐山古冶区水韵花苑\\\",\\\"stationID\\\":null,\\\"type\\\":\\\"line\\\",\\\"smooth\\\":false,\\\"itemStyle\\\":{\\\"normal\\\":{\\\"color\\\":\\\"#4B1116\\\",\\\"lineStyle\\\":{\\\"width\\\":4,\\\"type\\\":\\\"solid\\\"}}},\\\"showAllSymbol\\\":\\\"true\\\",\\\"data\\\":[" +
//                "   {\\\"name\\\":\\\"2019/9/17 20:00:00\\\",\\\"value\\\":\\\"49.6\\\",\\\"pm10\\\":\\\"133\\\",\\\"pm25\\\":\\\"66\\\"}]}]\"";
//        MonitorTipsmap = JedisUtil6500.getMonitorTipsMapAll(5);
//        commandList = JedisUtil6500.getAllCommdMap();
//        ExecutorService executor = Executors.newFixedThreadPool(1);
//        executor.submit(() -> {
//
//            Jedis jedis = JedisUtil6500.getJedis();
//            jedis.select(1);
//            Pipeline p = jedis.pipelined();
//
//            while (1 == 1) {
//                if (HttpController.queue.size() > 2000) {
//                    HttpController.now = new Date().getTime();
//                }
//                try {
//                    if (HttpController.queue.size() > 0) {
//                        MonitorTips monitorTips = HttpController.queue.poll();
//                        System.err.println("LocHandler.queue.size()  "+HttpController.queue.size() +"-->"+ monitorTips.getDeviceNumber() ) ;
//                        p.set(monitorTips.getDeviceNumber(), JSON.toJSONString(monitorTips));
////					    if(null==jedis) {
////					    	jedis = JedisUtil6491.getJedis();
////							jedis.select(1);
////							p = jedis.pipelined();
////					    }
////
////						jedis.set(monitorTips.getDeviceNumber(), JSON.toJSONString(monitorTips));
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        });
    }

}
