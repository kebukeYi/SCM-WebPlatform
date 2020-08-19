package com.car.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description 时间工具类
 * @date 2019/5/31
 */
public class DateUtils {

    //日期转字符串
    public static String DateToString(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return sdf.format(cal.getTime());

    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        return date.getTime();//获取时间的时间戳
    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToShortStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(time);
        return date.getTime();//获取时间的时间戳
    }

    /**
     * 日期转换为字符串 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getShotDate(Date date) {
        if (date == null) {
            return "";
        }
        return dateStr(date, "yyyy-MM-dd");
    }

    /**
     * 日期转换为字符串 格式自定义
     *
     * @param date
     * @param f
     * @return
     */
    public static String dateStr(Date date, String f) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(f);
        String str = format.format(date);
        return str;
    }

    /**
     * 格林尼治时间
     *
     * @return
     */
    public static String[] strGlnz() {
        Date date = new Date();
        String f = "yyMMdd HHmmss";
        SimpleDateFormat format = new SimpleDateFormat(f);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        String str = format.format(calendar.getTime());
        String[] s = str.split(" ");
        return s;
    }

    // 时间戳转字符串  1503991612952 ==> 2017-08-29 15:26:52  时间戳 是long 类型
    public static String timeToString(long s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(sdf.format(new Date(s)));
            //Date date = sdf.parse(sdf.format(new Long(s)));// 等价于
            return sdf.format(date);
        } catch (NumberFormatException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获得当前日期
     *
     * @return
     */
    public static Date getNow() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    /**
     * 获得当前日期，精确到毫秒
     *
     * @return
     */
    public static Timestamp getNowInMillis() {
        Timestamp timeStamep = new Timestamp(getNow().getTime());
        return timeStamep;
    }

    public static Date getDateByStrTime(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return date1>date2时结果<0,  date1=date2时结果=0, date2>date1时结果>0
     */
    public static int daysBetween(Date date1, Date date2, String format) {
        DateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        try {
            Date d1 = sdf.parse(DateUtils.dateStr(date1, format));
            Date d2 = sdf.parse(DateUtils.dateStr(date2, format));
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf((time2 - time1) / 86400000L));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个日期之间相差的小时数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int hoursBetween(Date date1, Date date2) {
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        try {
            Date d1 = sdf.parse(DateUtils.dateStr(date1, "yyyyMMdd"));
            Date d2 = sdf.parse(DateUtils.dateStr(date2, "yyyyMMdd"));
            cal.setTime(d1);
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf((time2 - time1) / 3600000L));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个日期相差的时间
     */
    public static void getBetweenDate() {
        String dateStart = "2017111520";
        String dateStop = "2017111620";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //毫秒ms
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print("两个时间相差：");
            System.out.print(diffDays + " 天, ");
            System.out.print(diffHours + " 小时, ");
            System.out.print(diffMinutes + " 分钟, ");
            System.out.print(diffSeconds + " 秒.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 计算两个日期之间相差的小时数
     *
     * @param date1
     * @param date2
     * @return date1>date2时结果<0,  date1=date2时结果=0, date2>date1时结果>0
     */
    public static int hoursBetweenByDateStr(Date date1, Date date2) {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        Calendar cal = Calendar.getInstance();
        try {
            Date d1 = sdf.parse(DateUtils.dateStr(date1, "yyyyMMddHH"));
            Date d2 = sdf.parse(DateUtils.dateStr(date2, "yyyyMMddHH"));
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf(((time2 - time1) / 3600000L)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个日期之间相差的分钟数
     *
     * @param date1
     * @param date2
     * @return date1>date2时结果<0,  date1=date2时结果=0, date2>date1时结果>0
     */
    public static int minuteBetweenByDateStr(Date date1, Date date2) {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Calendar cal = Calendar.getInstance();
        try {
            Date d1 = sdf.parse(DateUtils.dateStr(date1, "yyyyMMddHHmm"));
            Date d2 = sdf.parse(DateUtils.dateStr(date2, "yyyyMMddHHmm"));
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf(((time2 - time1) / 60000L)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个日期之间相差的秒数
     *
     * @param date1
     * @param date2
     * @return date1>date2时结果<0,  date1=date2时结果=0, date2>date1时结果>0
     */
    public static int secondBetweenByDateStr(Date date1, Date date2) {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        try {
            Date d1 = sdf.parse(DateUtils.dateStr(date1, "yyyyMMddHHmmss"));
            Date d2 = sdf.parse(DateUtils.dateStr(date2, "yyyyMMddHHmmss"));
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf(((time2 - time1) / 1000L)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = 0;
        }
        return w;
    }

}
