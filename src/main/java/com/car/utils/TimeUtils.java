package com.car.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  14:38
 * @Description
 */
public class TimeUtils {

    static SimpleDateFormat formatGlz = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat YEARMONTH = new SimpleDateFormat("yyyyMM");
    static SimpleDateFormat MONTHDAY = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static Random random = new Random();


    /**
     * @return
     * @author mmy
     * * @date 2020/2/1  16:35
     * @Description 格林治时间 2018-11-29T15:09:24
     */
    public static String getGlnz() {
        return formatGlz.format(new Date());
    }

    public static String getSdf() {
        return sdf.format(new Date());
    }


    /**
     * timeutils 加上两个方法
     * 时间带T转换
     *
     * @param date
     */
    public static String dateTomillis(String date) {
        Date d = null;
        try {
            d = formatGlz.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(d.getTime());

    }

    /**
     * 字符串转时间
     *
     * @param str
     */
    public static Date StrToDate(String str) {
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /*
    -8 小时 从influxdb 库中查询
     */
    public static String BeijingTimeToGlnzTime(String nowTime) {
        String time = "";
        try {
            Date date = format.parse(nowTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR, -8);
            date = calendar.getTime();
            time = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * @return
     * @author mmy
     * @date 2020/2/23  10:32
     * @Description 模拟 停留和停止时间  3/8 17:44 已更换
     */
    public static List<String> getStopAndOffLine() {
        List<String> stringList = new ArrayList<>();
        String OffLine = "OffLine:" + random.nextInt(50) + ":" + random.nextInt(25) + ":" + random.nextInt(60) + ":" + random.nextInt(60);
        String Stop = "Stop:" + random.nextInt(50) + ":" + random.nextInt(25) + ":" + random.nextInt(60) + ":" + random.nextInt(60);
        String OnLine = "OnLine:" + random.nextInt(50) + ":" + random.nextInt(25) + ":" + random.nextInt(60) + ":" + random.nextInt(60);
        stringList.add(0, OnLine);
        stringList.add(1, Stop);
        stringList.add(2, OffLine);

        return stringList;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/8  12:14
     * @Description 未来时间几个小时
     */
    public static String getPreTimeByHour(int hour, int flag) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.HOUR, hour);
        Date dt1 = rightNow.getTime();
        String reStr = null;
        if (1 == flag) {
            reStr = formatGlz.format(dt1);
        } else if (2 == flag) {
            reStr = sdf.format(dt1);
        }
        return reStr;
    }

    public static String getPreTimeByHourTwo(String startTime, int hour, int flag) {
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(format.parse(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rightNow.add(Calendar.HOUR, hour);
        Date dt1 = rightNow.getTime();
        String reStr = null;
        if (1 == flag) {
            reStr = formatGlz.format(dt1);
        } else if (2 == flag) {
            reStr = sdf.format(dt1);
        }
        return reStr;
    }


    /*
    过去月的日期
     */
    public static String getPreTimeByMonth(int month) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.MONTH, month);
        Date dt1 = rightNow.getTime();
        String reStr = null;
        return YEARMONTH.format(dt1);
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/8  12:14
     * @Description 未来时间几天
     */
    public static String getPreTimeByDay(int days, int flag) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DATE, days);
        Date dt1 = rightNow.getTime();
        String reStr = null;
        if (1 == flag) {
            reStr = formatGlz.format(dt1);
        } else if (2 == flag) {
            reStr = sdf.format(dt1);
        }
        return reStr;
    }

    /**
     * @return
     * @author mmy
     * @date 2020/2/8  12:14
     * @Description 未来时间几分钟
     */
    public static String getPreTimeByMinute(int mins, int flag) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.MINUTE, mins);
        Date dt1 = rightNow.getTime();
        String reStr = null;
        if (1 == flag) {
            reStr = formatGlz.format(dt1);
        } else if (2 == flag) {
            reStr = sdf.format(dt1);
        }
        return reStr;
    }

    /**
     * @return
     * @author mmy
     * @date 2020/2/8  12:14
     * @Description 未来时间几分钟
     */
    public static String getPreTimeBySecond(int seconds, int flag) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.SECOND, seconds);
        Date dt1 = rightNow.getTime();
        String reStr = null;
        if (1 == flag) {
            reStr = formatGlz.format(dt1);
        } else if (2 == flag) {
            reStr = sdf.format(dt1);
        }
        return reStr;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/8  12:14
     * @Description 未来时间几分钟
     */
    public static String getLastTimeBySecond(int seconds, int flag, Date date) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.SECOND, seconds);
        Date dt1 = rightNow.getTime();
        String reStr = null;
        if (1 == flag) {
            reStr = formatGlz.format(dt1);
        } else if (2 == flag) {
            reStr = sdf.format(dt1);
        }
        return reStr;
    }


    /**
     * @return java.lang.String
     * @author mmy
     * @date 2020/2/1  16:35
     * @Description redis 库中毫秒转 格林治时间
     */
    public static String millis2Glz(String millis) {
        return formatGlz.format(new Date(Long.parseLong(millis)));
    }

    public static String millis2Form(String millis) {
        return format.format(new Date(Long.parseLong(millis)));
    }


    public static void main(String[] args) {
//        System.out.println(getGlnz());
//        System.out.println(millis2Glz("1579501827311"));
//        System.out.println(BeijingTimeToGlnzTime("2005-12-15 15:30:23"));
//        System.out.println(getPreTimeBySecond(30, 1));
        System.out.println(getPreTimeByMonth(0));
    }


}
