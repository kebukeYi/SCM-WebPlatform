package com.car.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.domain.MonitorTips;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author : mmy
 * @Creat Time : 2020/1/19  13:45
 * @Description
 */
public class JedisUtil6491 {

    protected static Logger logger = LogManager.getLogger(JedisUtil6491.class);

    //Redis服务器IP
    private static String ADDR_ARRAY = "127.0.0.1";

    //Redis的端口号
    private static int PORT = 6491;

    //访问密码
    //private static String AUTH = "";
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 100;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 50;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 3000;

    //超时时间
    private static int TIMEOUT = 2000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = false;

    private static JedisPool jedisPool = initialPool();

    /**
     * redis过期时间,以秒为单位
     */
    public final static int EXRP_HOUR = 60 * 60;                         //一小时
    public final static int EXRP_DAY = 60 * 60 * 24;                    //一天
    public final static int EXRP_MONTH = 60 * 60 * 24 * 30;     //一个月

    /**
     * 初始化Redis连接池
     */
    private static JedisPool initialPool() {
        JedisPool jedisPool = null;
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[0], PORT, TIMEOUT);

        } catch (Exception e) {
            logger.error("First create JedisPool error : " + e);
            try {
                //如果第一个IP异常，则访问第二个IP
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(MAX_ACTIVE);
                config.setMaxIdle(MAX_IDLE);
                config.setMaxWaitMillis(MAX_WAIT);
                config.setTestOnBorrow(TEST_ON_BORROW);
                jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[1], PORT, TIMEOUT);
            } catch (Exception e2) {
                logger.error("Second create JedisPool error : " + e2);
            }
        }
        return jedisPool;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */

    public synchronized static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedis.close();
        }
    }

    public synchronized static Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    /**
     * 设置 0 库
     * @param key
     * @param value
     */
    public static void setString0(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            //getJedis().set(key, value);
            //addby wanglei
            //System.out.println("jedis set:"+Thread.currentThread().getId()+":"+key+":"+value);
            jedis = getJedis();
            jedis.select(0);
            jedis.set(key, value);
            returnResource(jedis);
            //addby wanglei
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString0(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            //getJedis().set(key, value);
            //addby wanglei
            //System.out.println("jedis set:"+Thread.currentThread().getId()+":"+key+":"+value);
            jedis = getJedis();
            jedis.select(0);
            str = jedis.get(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return str;
    }

    /**
     * 设置 1库
     *
     * @param key
     * @param value
     */
    public static void setString1(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            //getJedis().set(key, value);
            //addby wanglei
            //System.out.println("jedis set:"+Thread.currentThread().getId()+":"+key+":"+value);
            jedis = getJedis();
            jedis.select(1);
            jedis.set(key, value);
            returnResource(jedis);
            //addby wanglei
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString1(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            //getJedis().set(key, value);
            //addby wanglei
            //System.out.println("jedis set:"+Thread.currentThread().getId()+":"+key+":"+value);
            jedis = getJedis();
            jedis.select(1);
            str = jedis.get(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return str;
    }

    /**
     * 设置 2库
     *
     * @param key
     * @param value
     */
    public static void setString2(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            //getJedis().set(key, value);
            //addby wanglei
            //System.out.println("jedis set:"+Thread.currentThread().getId()+":"+key+":"+value);
            jedis = getJedis();
            jedis.select(2);
            jedis.set(key, value);
            returnResource(jedis);
            //addby wanglei
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString2(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            //getJedis().set(key, value);
            //addby wanglei
            //System.out.println("jedis set:"+Thread.currentThread().getId()+":"+key+":"+value);
            jedis = getJedis();
            jedis.select(2);
            str = jedis.get(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return str;
    }


    /**
     * 设置 3库
     *
     * @param key
     * @param value
     */
    public static void setString3(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            //getJedis().set(key, value);
            //addby wanglei
            //System.out.println("jedis set:"+Thread.currentThread().getId()+":"+key+":"+value);
            jedis = getJedis();
            jedis.select(3);
            jedis.set(key, value);
            returnResource(jedis);
            //addby wanglei
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString3(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            //getJedis().set(key, value);
            //addby wanglei
            //System.out.println("jedis set:"+Thread.currentThread().getId()+":"+key+":"+value);
            jedis = getJedis();
            jedis.select(3);
            str = jedis.get(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return str;
    }


    public static void setString6Ex(String key, int t, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            //getJedis().set(key, value);
            //addby wanglei
            //System.out.println("jedis set:"+Thread.currentThread().getId()+":"+key+":"+value);
            jedis = getJedis();
            jedis.select(6);
            jedis.setex(key, t, value);
            returnResource(jedis);
            //addby wanglei
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    /**
     * @return java.util.List<com.cars.redis.MonitorTips>
     * @author mmy
     * @date 2020/1/20  17:33
     * @Description
     */
    public static List<MonitorTips> getMonitorTipsAll(int dbNum) {
        List<MonitorTips> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            Set s = jedis.keys("*");
            java.util.Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                MonitorTips monitorTips = (MonitorTips) JSON.parseObject(json.toJSONString(), MonitorTips.class);
                list.add(monitorTips);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getMonitorTips error : " + e);
        }
        return list;
    }


}
