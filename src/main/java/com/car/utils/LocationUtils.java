package com.car.utils;

import com.alibaba.fastjson.JSON;
import com.car.domain.MonitorTips;
import com.car.redis.JedisUtil6800;
import com.car.redis.bean.Point;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/2  19:00
 * @Description
 */
public class LocationUtils {


    static Random random = new Random();
    static SimpleDateFormat formatGlz = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    /**
     * @param MinLng：最小经度 MaxLng：最大经度
     *                    MinLat：最小纬度
     *                    MaxLat：最大纬度
     * @Description: 在矩形内随机生成经纬度
     */
    public static Map<String, String> randomLonLat(double MinLng, double MaxLng, double MinLat, double MaxLat) {
        BigDecimal db = new BigDecimal(Math.random() * (MaxLng - MinLng) + MinLng);
        String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        Map<String, String> map = new HashMap<String, String>();
        map.put("E", lon);
        map.put("N", lat);
        return map;
    }

    /*
    生成2点之间的10个 路程点
     */
    public static ArrayList generate10(float deslat2, float deslng2, float lat, float lng, int count) {
        ArrayList<Point> al = new ArrayList();
        float lattemp = lat;
        float lngtemp = lng;
        for (int qi = 0; qi < count; qi++) {
            if (deslat2 > lat) {
                lattemp = lat + (deslat2 - lat) / 9 * qi;
            } else {
                lattemp = lat - (lat - deslat2) / 9 * qi;
            }

            if (deslng2 > lng) {
                lngtemp = lng + (deslng2 - lng) / 9 * qi;
            } else {
                lngtemp = lng - (lng - deslng2) / 9 * qi;
            }
            Point point = new Point(lattemp + "", lngtemp + "");
            al.add(point);
        }
        Point point = new Point(deslat2 + "", deslng2 + "");
        al.add(point);
        return al;
    }

    /*
    52.8273745400,122.1604914200   黑龙江省大兴安岭地区漠河市西林吉镇
    46.5868661800,132.3117967900   黑龙江省双鸭山市宝清县五九七农场
    45.6123911900,126.2479369500   黑龙江省哈尔滨市道里区立权路
44.3064810000,112.6245243600   内蒙古自治区锡林郭勒盟苏尼特左旗赛罕高毕苏木

39.3678634300,76.1101083400   新疆维吾尔自治区喀什地区疏勒县塔孜洪乡
46.9190977300,88.0630500800    新疆维吾尔自治区阿勒泰地区福海县喀拉玛盖镇
44.4014501000,81.3838192400  新疆维吾尔自治区伊犁哈萨克自治州伊宁县麻扎乡

    41.9329432200,111.3065089000   内蒙古自治区乌兰察布市四子王旗红格尔苏木
    39.2654955300,95.4038105700         甘肃省酒泉市肃北蒙古族自治县党城湾镇

34.8873419800,79.2751335100    新疆维吾尔自治区和田地区和田县朗如乡
28.8482285000,84.6366702900  西藏自治区日喀则市吉隆县贡当乡

28.3851938900,89.4263356900 西藏自治区日喀则市康马县萨玛达乡
28.1495032115,96.1962890625 云南省德宏傣族景颇族自治州芒市中山乡
24.2099715700,98.6130981800 云南省德宏傣族景颇族自治州芒市中山乡
22.9207380800,104.2801173700 云南省文山壮族苗族自治州马关县仁和镇
22.0274593400,112.6269112100 广东省江门市台山市端芬镇
27.1796637300,120.1860805100 福建省宁德市福鼎市白琳镇
31.7302226200,121.1529796400 上海市上海市崇明区
35.8534373600,118.7346426500 山东省临沂市沂水县道托镇

31.8421859200,111.4393084200  湖北省襄阳市南漳县长坪镇
31.9917300000,100.9847574400 四川省阿坝藏族羌族自治州壤塘县吾伊乡
27.8425286600,104.8069449800 云南省昭通市威信县长安镇
37.4745665000,93.3382338600 青海省海西蒙古族藏族自治州格尔木市乌图美仁乡
     */


    public static void main(String[] args) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DATE, -5);//5天前的日期
        Date date = rightNow.getTime();//5天前的日期
        rightNow.setTime(date);
        List<Point> list = generate10(52.8273745f, 122.16049142f, 37.4745665f, 93.33823386f, 10);
        MonitorTips monitorTips = JedisUtil6800.getMonitorTipsByDeviceId("101:1:1111100000");
        int length = list.size();
        for (int i = 0; i <length; i++) {
            rightNow.add(Calendar.MINUTE, 1);//分钟递增
            Date date1 = rightNow.getTime();
            Point point = list.get(i);
            monitorTips.setLat(point.getLat());
            monitorTips.setLng(point.getLng());
            monitorTips.setLocationTime(formatGlz.format(date1));
            monitorTips.setSpeed((random.nextFloat()+56)+"");
            JedisUtil6800.setString8(i+"",JSON.toJSONString(monitorTips));
        }
    }

    public static String getIMEI() {// calculator IMEI
        int r1 = 1000000 + new Random().nextInt(9000000);
        int r2 = 1000000 + new Random().nextInt(9000000);
        String input = r1 + "" + r2;
        char[] ch = input.toCharArray();
        int a = 0, b = 0;
        for (int i = 0; i < ch.length; i++) {
            int tt = Integer.parseInt(ch[i] + "");
            if (i % 2 == 0) {
                a = a + tt;
            } else {
                int temp = tt * 2;
                b = b + temp / 10 + temp % 10;
            }
        }
        int last = (a + b) % 10;
        if (last == 0) {
            last = 0;
        } else {
            last = 10 - last;
        }
        return input + last;
    }

    public static String getImsi() {
        // 460022535025034
        String title = "4600";
        int second = 0;
        do {
            second = new Random().nextInt(8);
        } while (second == 4);
        int r1 = 10000 + new Random().nextInt(90000);
        int r2 = 10000 + new Random().nextInt(90000);
        return title + "" + second + "" + r1 + "" + r2;
    }

    public static String getMac() {
        char[] char1 = "abcdef".toCharArray();
        char[] char2 = "0123456789".toCharArray();
        StringBuffer mBuffer = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int t = new Random().nextInt(char1.length);
            int y = new Random().nextInt(char2.length);
            int key = new Random().nextInt(2);
            if (key == 0) {
                mBuffer.append(char2[y]).append(char1[t]);
            } else {
                mBuffer.append(char1[t]).append(char2[y]);
            }

            if (i != 5) {
                mBuffer.append(":");
            }
        }
        return mBuffer.toString();
    }




}
