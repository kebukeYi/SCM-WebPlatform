package com.car.redis;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.car.bean.Role;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.bean.Catalog;
import com.car.bean.Organization;
import com.car.bean.UserOrg;
import com.car.domain.Alarm;
import com.car.domain.Fence;
import com.car.domain.MonitorTips;
import com.car.setting.UserSetting;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author : mmy
 * @Creat Time : 2020/1/19  13:45
 * @Description
 */
public class JedisUtil6800 {

    protected static Logger logger = LogManager.getLogger(JedisUtil6800.class);

    //Redis服务器IP
    private static String ADDR_ARRAY = "127.0.0.1";

    //Redis的端口号
    private static int PORT = 6800;

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
     *
     * @param key
     * @param value
     */
    public static void setString0(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(0);

            jedis.set(key, value);
            returnResource(jedis);
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

    //*是否存在此组
    public static boolean existsString6(String key) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            jedis.select(6);
            result = jedis.exists(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        System.out.println(result);
        return result;
    }

    //*是否存在此目录
    public static boolean existsString7(String key) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            jedis.select(7);
            result = jedis.exists(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return result;
    }

    //*是否存在此用户
    public static boolean existsString9(String key) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            jedis.select(9);
            result = jedis.exists(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return result;
    }

    //*是否存在此设备号
    public static boolean existsString4(String key) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            jedis.select(4);
            result = jedis.exists(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return result;
    }

    //*是否存在此车牌号
    public static boolean existsString13(String key) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            jedis.select(13);
            result = jedis.exists(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return result;
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
            jedis = getJedis();
            jedis.select(1);
            jedis.set(key, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
    }

    /*
    增添时间
     */
    public static void setString1SetLoncationTime(String key, String field, LinkedList linkedList) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(1);
            jedis.hset(key, field, JSON.toJSONString(linkedList));
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
    }

    public static void flushDB1() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(1);
            jedis.flushDB();
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("flushDB error : " + e);
        }
    }

    public static String getString1(String key) {
        Jedis jedis = null;
        String str = null;
        try {
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
            jedis = getJedis();
            jedis.select(2);
            jedis.set(key, value);
            returnResource(jedis);
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
            jedis = getJedis();
            jedis.select(3);
            jedis.set(key, value);
            returnResource(jedis);
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


    /**
     * 设置 4库
     *
     * @param key
     * @param value
     */
    public static void setString4(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(4);
            jedis.set(key, value);
            returnResource(jedis);
            //addby wanglei
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString4(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(4);
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
     * 设置 5库
     *
     * @param key
     * @param value
     */
    public static void setString5(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            //getJedis().set(key, value);
            //addby wanglei
            //System.out.println("jedis set:"+Thread.currentThread().getId()+":"+key+":"+value);
            jedis = getJedis();
            jedis.select(5);
            jedis.set(key, value);
            returnResource(jedis);
            //addby wanglei
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
    }

    public static String getString5(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(5);
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
     * 设置 6库
     *
     * @param key
     * @param value
     */
    public static void setString6(String key, String value) {
        String x = key.substring(0, 10);
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(6);
            Set s = jedis.keys(x + ":*");
            Iterator it = s.iterator();
            int w = 0;
            while (it.hasNext()) {
                String key1 = (String) it.next();
                String value1 = jedis.get(key1);
                if (value1.contains(value)) {
                    w = 1;
                }
            }
            if (w == 0) {
                jedis.set(key, value);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
    }

    public static String getString6(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(6);
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
            jedis = getJedis();
            jedis.select(6);
            jedis.setex(key, t, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
    }

    public static String delString6(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(6);
            Set s = jedis.keys(key + "*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key1 = (String) it.next();
                //Todo 返回再删除
                jedis.del(key1);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return str;
    }

    public static List<String> getAllCommd6(String IMEI) {
        List<String> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(6);
            Set s = jedis.keys(IMEI + "*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                //Todo 返回再删除
//                 jedis.del(key);
                list.add(value);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getAllComd error : " + e);
        }
        return list;
    }

    /**
     * 设置 7库
     *
     * @param key
     * @param value
     */
    public static void setString7(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(7);
            jedis.set(key, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
    }

    public static String getString7(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(7);
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
     * 设置 8库
     *
     * @param key
     * @param value
     */
    public static void setString8(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(8);
            jedis.set(key, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
    }

    public static String getString8(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(8);
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
     * 设置 9库
     *
     * @param key
     * @param value
     */
    public static void setString9(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(9);
            jedis.set(key, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
    }

    public static String getString9(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(9);
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
     * 设置 10库
     *
     * @param key
     * @param value
     */
    public static void setString10(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(10);
            jedis.set(key, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString10(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(10);
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
     * 设置 11库
     *
     * @param key
     * @param value
     */
    public static void setString11(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(11);
            jedis.set(key, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString11(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(11);
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
     * 设置 12库
     *
     * @param key
     * @param value
     */
    public static void setString12(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(12);
            jedis.set(key, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString12(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(12);
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
     * 设置 13库
     *
     * @param key
     * @param value
     */
    public static void setString13(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(13);
            jedis.set(key, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString13(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(13);
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
     * 设置 14库
     *
     * @param key
     * @param value
     */
    public static void setString14(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(14);
            jedis.set(key, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString14(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(14);
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
     * 设置 15库
     *
     * @param key
     * @param value
     */
    public static void setString15(String key, String value) {
        Jedis jedis = null;
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis = getJedis();
            jedis.select(15);
            jedis.set(key, value);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);

        }
    }

    public static String getString15(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(15);
            str = jedis.get(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return str;
    }

    public static void delStringByKeyAndDbNum(String key, int dbNum) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            System.out.println("redis delete " + key);
            jedis.del(key);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
    }

    /*
     根据 用户Id 获取所有用设备
     */
    public static List<MonitorTips> getMonitorTipsByUserId(String userId) {
        List<MonitorTips> monitorTips = new ArrayList<>();
        if (UserSetting.ADMIN.equals(userId)) {
            monitorTips = JedisUtil6800.getMonitorTipsAll(5);
        } else {
            UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
            JSONArray jsonArray = JSONArray.parseArray(userOrg.getOrgnizationIds());
            for (Object obj : jsonArray) {
                List<MonitorTips> monitorTipsList = JedisUtil6800.getMonitorTipsByGroupId(obj + "");
                monitorTips.addAll(monitorTipsList);
            }
        }
        return monitorTips;
    }


    /*
    根据 组Id 获取所有用设备
   */
    public static List<MonitorTips> getMonitorTipsByGroupId(String groupId) {
        List<MonitorTips> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            //TODO 记得更改
            jedis.select(5);
            Set s = jedis.keys(groupId + ":*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                MonitorTips monitorTips = JSON.parseObject(json.toJSONString(), MonitorTips.class);
                list.add(monitorTips);
            }
            returnResource(jedis);
        } catch (
                Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getMonitorTips error : " + e);
        }
        return list;
    }

    /*
    根据 组Id 获取所有用设备
    */
    public static List<AndroidMonitorTip> getAndroidMonitorTipsByGroupId(String groupId) {
        List<AndroidMonitorTip> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            //TODO 记得更改
            jedis.select(4);
            Set s = jedis.keys(groupId + ":*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                AndroidMonitorTip monitorTips = JSON.parseObject(json.toJSONString(), AndroidMonitorTip.class);
                list.add(monitorTips);
            }
            returnResource(jedis);
        } catch (
                Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getMonitorTips error : " + e);
        }
        return list;
    }


    /*
      根据 目录 id 获取所有用设备
     */
    public static List<MonitorTips> getMonitorTipsByCatalogId(String CatalogId) {
        List<MonitorTips> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            //TODO 记得更改
            jedis.select(4);
            Set s = jedis.keys("*:" + CatalogId + ":*");
            Iterator it = s.iterator();
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

    /*
     根据具体设备号 获取
     */
    public static MonitorTips getMonitorTipsByDeviceId(String deviceId) {
        MonitorTips monitorTips = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            //TODO 记得更改
            jedis.select(5);
            Set set = jedis.keys("*" + deviceId);
//            System.out.println("getMonitorTipsByDeviceId ：" + set.size());
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                monitorTips = JSON.parseObject(json.toJSONString(), MonitorTips.class);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return monitorTips;
    }

    /*
    根据具体设备号 获取
  */
    public static AndroidMonitorTip getAndroidMonitorTipsByDeviceId(String deviceId) {
        AndroidMonitorTip monitorTips = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            //TODO 记得更改
            jedis.select(4);
            Set set = jedis.keys("*" + deviceId);
//            System.out.println("getMonitorTipsByDeviceId ：" + set.size());
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                monitorTips = JSON.parseObject(json.toJSONString(), AndroidMonitorTip.class);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return monitorTips;
    }


    /*
    根据具体设备号 获取历史轨迹
  */
    public static List<String> getAndroidMonitorTipsLngLatArrayLisByDeviceId(String deviceId) {
        List<String> lngLatArrayList = new ArrayList<String>();
        String listString = "";
        Jedis jedis = null;
        try {
            jedis = getJedis();
            //TODO 记得更改
            jedis.select(1);
            listString = jedis.hget(deviceId, "LatLng");
            lngLatArrayList = JSON.parseArray(listString, String.class);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("根据设备  " + deviceId + "  获取历史轨迹 错误数据 " + listString);
        }
        return lngLatArrayList;
    }

    /*
   根据具体设备号 获取历史轨迹时间点
 */
    public static List<String> getAndroidMonitorTipsLocationTimeListByDeviceId(String deviceId) {
        List<String> lngLatArrayList = new ArrayList<String>();
        String listString = "";
        Jedis jedis = null;
        try {
            jedis = getJedis();
            //TODO 记得更改
            jedis.select(1);
            listString = jedis.hget(deviceId, "LocationTime");
            lngLatArrayList = JSON.parseArray(listString, String.class);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("根据设备  " + deviceId + "  获取历史轨迹 错误数据 " + listString);
        }
        return lngLatArrayList;
    }


    /*
    根据具体设备号 获取报警设备
    */
    public static List<Alarm> getAlarmAndroidMonitorTipsByDeviceId(String deviceId) {
        List<Alarm> alarms = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            //TODO 记得更改
            jedis.select(5);
            Set set = jedis.keys("*" + deviceId);
//            System.out.println("getMonitorTipsByDeviceId ：" + set.size());
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                alarms = JSON.parseArray(value, Alarm.class);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return alarms;
    }


    public static List<Catalog> getCataloguesAll(int dbNum) {
        List<Catalog> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            Set s = jedis.keys("*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                Catalog androidMonitorTip = JSON.parseObject(json.toJSONString(), Catalog.class);
                list.add(androidMonitorTip);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getMonitorTips error : " + e);
        }
        return list;
    }

    /**
     * @return java.util.List<com.cars.redis.MonitorTips>
     * @author mmy
     * @date 2020/1/20  17:33
     * @Description
     */
    public static List<AndroidMonitorTip> getAndroidMonitorTipsAll(int dbNum) {
        List<AndroidMonitorTip> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            Set s = jedis.keys("*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                AndroidMonitorTip androidMonitorTip = JSON.parseObject(json.toJSONString(), AndroidMonitorTip.class);
                list.add(androidMonitorTip);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getMonitorTips error : " + e);
        }
        return list;
    }

    public static List<Alarm> getAlarmAndroidMonitorTipsAll(int dbNum) {
        List<Alarm> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            Set s = jedis.keys("*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                List<Alarm> alarms = JSON.parseArray(value, Alarm.class);
                list.addAll(alarms);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getMonitorTips error : " + e);
        }
        return list;
    }

    public static List<MonitorTips> getMonitorTipsAll(int dbNum) {
        List<MonitorTips> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            Set s = jedis.keys("*");
            Iterator it = s.iterator();
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

    public static List<UserOrg> getUserOrgAll(int dbNum) {
        List<UserOrg> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            Set s = jedis.keys("*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                UserOrg userOrg = (UserOrg) JSON.parseObject(json.toJSONString(), UserOrg.class);
                list.add(userOrg);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getUserOrgAll error : " + e);
        }
        return list;
    }


    public static List<Role> getRolesAll(int dbNum) {
        List<Role> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            Set s = jedis.keys("*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                Role userOrg = (Role) JSON.parseObject(json.toJSONString(), Role.class);
                list.add(userOrg);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getUserOrgAll error : " + e);
        }
        return list;
    }

    public static List<Organization> getOrganizationsAll(int dbNum) {
        List<Organization> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            Set s = jedis.keys("*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                Organization userOrg = (Organization) JSON.parseObject(json.toJSONString(), Organization.class);
                list.add(userOrg);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getUserOrgAll error : " + e);
        }
        return list;
    }

    /**
     * @return java.util.List<com.cars.redis.MonitorTips>
     * @author mmy
     * @date 2020/1/20  17:33
     * @Description
     */
    public static List<Fence> getFenceAll(int dbNum) {
        List<Fence> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            Set s = jedis.keys("*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                Fence fence = (Fence) JSON.parseObject(json.toJSONString(), Fence.class);
                list.add(fence);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getUserOrgAll error : " + e);
        }
        return list;
    }

    /*
   指令库 6
    */
    public static Map<String, String> getAllCommdMap() {
        Map<String, String> aMap = new HashMap<String, String>();
        List<String> list = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(6);
            Set s = jedis.keys("*");
            java.util.Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                key = key.split(":")[0];
                aMap.put(key, value);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getAllComd error : " + e);
        }
        return aMap;
    }

    /*
    指令库 6
     */
    public static String getAllCommd(String IMEI) {
        String xString = "";
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(6);
            xString = jedis.get(IMEI);
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getAllComd error : " + e);
        }
        return xString;
    }

    /*
    指令库 6
     */
    public static Map<String, MonitorTips> getMonitorTipsMapAll(int dbNum) {
        Map<String, MonitorTips> map = new HashMap<String, MonitorTips>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbNum);
            Set s = jedis.keys("*");
            java.util.Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String[] btStrings = key.split(":");//101:2:1111100001
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                MonitorTips monitorTips = JSON.parseObject(json.toJSONString(), MonitorTips.class);
                map.put(btStrings[2], monitorTips);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getMonitorTips error : " + e);
        }
        return map;
    }


    public static ConcurrentHashMap<String, MonitorTips> getDeviceMonitorTipsMapBy1() {
        ConcurrentHashMap<String, MonitorTips> map = new ConcurrentHashMap<String, MonitorTips>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(1);
            Set s = jedis.keys("*");
            java.util.Iterator it = s.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jedis.get(key);
                JSONObject json = JSON.parseObject(value);
                MonitorTips monitorTips = JSON.parseObject(json.toJSONString(), MonitorTips.class);
                map.put(key, monitorTips);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error(" getMonitorTips error : " + e);
        }
        return map;
    }

    public static String delString7(String key) {
        Jedis jedis = null;
        String str = null;
        try {
            jedis = getJedis();
            jedis.select(7);
            Set s = jedis.keys(key + "*");
            Iterator it = s.iterator();
            while (it.hasNext()) {
                String key1 = (String) it.next();
                //Todo 返回再删除
                jedis.del(key1);
            }
            returnResource(jedis);
        } catch (Exception e) {
            returnResource(jedis);
            e.printStackTrace();
            logger.error("Set key error : " + e);
        }
        return str;
    }
}
