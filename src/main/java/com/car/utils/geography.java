package com.car.utils;

import com.car.baiduMapRoute.HttpRoute;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/4/14  10:23
 * @Description
 */
public class geography {

    public static double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
    public static double EARTH_RADIUS = 6378137.0;
    public static double PI = 3.1415926535897932384626;


    static double a(double d1) {
        boolean flag = false;
        if (d1 < 0.0) {
            d1 = -d1;
            flag = true;
        }
        double l = Math.floor((d1 / 6.2831853071795862));
        double d2 = d1 - l * 6.2831853071795862;
        if (d2 > 3.1415926535897931) {
            d2 -= 3.1415926535897931;
            flag = !flag;
        }
        double d3 = d1 = d2;
        double d4 = d1;
        d2 *= d2;
        d4 *= d2;
        d3 -= d4 * 0.16666666666666699;
        d4 *= d2;
        d3 += d4 * 0.0083333333333333297;
        d4 *= d2;
        d3 -= d4 * 0.00019841269841269801;
        d4 *= d2;
        d3 += d4 * 2.7557319223985901E-006;
        d4 *= d2;
        d3 -= d4 * 2.50521083854417E-008;
        if (flag) d3 = -d3;
        return d3;
    }


    static double aa(double d1, double d2) {
        double d3 = 0;
        d3 += 300 + 1.0 * d1 + 2 * d2 + 0.10000000000000001 * d1 * d1
                + 0.10000000000000001 * d1 * d2 + 0.10000000000000001 * Math.sqrt(Math.sqrt(d1 * d1));
        d3 += (20 * a(18.849555921538759 * d1) + 20 * a(6.2831853071795862 * d1)) * 0.66669999999999996;
        d3 += (20 * a(3.1415926535897931 * d1) + 40 * a((3.1415926535897931 * d1) / 3)) * 0.66669999999999996;
        d3 += (150 * a((3.1415926535897931 * d1) / 12) + 300 * a((3.1415926535897931 * d1) / 30)) * 0.66669999999999996;
        return d3;
    }

    static double bb(double d1, double d2) {
        double d3 = 0;
        d3 += -100 + 2 * d1 + 3 * d2 + 0.20000000000000001 * d2 * d2
                + 0.10000000000000001 * d1 * d2 + 0.20000000000000001 * Math.sqrt(Math.sqrt(d1 * d1));
        d3 += (20 * a(18.849555921538759 * d1) + 20 * a(6.2831853071795862 * d1)) * 0.66669999999999996;
        d3 += (20 * a(3.1415926535897931 * d2) + 40 * a((3.1415926535897931 * d2) / 3)) * 0.66669999999999996;
        d3 += (160 * a((3.1415926535897931 * d2) / 12) + 320 * a((3.1415926535897931 * d2) / 30)) * 0.66669999999999996;
        return d3;
    }


    static double cc(double d1, double d2) {
        double d3 = a((d1 * 3.1415926535897931) / 180);
        double d4 = Math.sqrt(1.0 - 0.0066934200000000003 * d3 * d3);
        d4 = (d2 * 180) / ((6378245 / d4) * Math.cos((d1 * 3.1415926535897931) / 180) * 3.1415926535897931);
        return d4;
    }


    static double dd(double d1, double d2) {
        double d3 = a((d1 * 3.1415926535897931) / 180);
        double d4 = 1.0 - 0.0066934200000000003 * d3 * d3;
        double d5 = 6335552.7273521004 / (d4 * Math.sqrt(d4));
        return (d2 * 180) / (d5 * 3.1415926535897931);
    }

    static double[] gps2Gcj(double d1, double d2) {
        double[] ad = {d1, d2};
        double d3 = aa(ad[0] - 105, ad[1] - 35);
        double d4 = bb(ad[0] - 105, ad[1] - 35);
        double result = ad[0] + cc(ad[1], d3);
        double result1 = ad[1] + dd(ad[1], d4);
        result = Math.floor((result + 0.00000005) * 1000000) / 1000000;
        result1 = Math.floor((result1 + 0.00000005) * 1000000) / 1000000;
        return new double[]{result, result1};
    }

    static double[] gcjToGps(double d1, double d2) {
        double[] ad = {d1, d2};
        double d3 = aa(ad[0] - 105, ad[1] - 35);
        double d4 = bb(ad[0] - 105, ad[1] - 35);
        double result = ad[0] - cc(ad[1], d3);
        double result1 = ad[1] - dd(ad[1], d4);
        result = Math.floor((result + 0.00000005) * 1000000) / 1000000;
        result1 = Math.floor((result1 + 0.00000005) * 1000000) / 1000000;
        return new double[]{result, result1};
    }

    static double[] gcjToBaidu(double d1, double d2) {
        double x = d1;
        double y = d2;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        double bdLon = z * Math.cos(theta) + 0.0065;
        double bdLat = z * Math.sin(theta) + 0.006;
        double result = Math.floor((bdLon + 0.00000005) * 1000000) / 1000000;
        double result1 = Math.floor((bdLat + 0.00000005) * 1000000) / 1000000;
        return new double[]{result, result1};
    }

    static double[] baiduToGcj(double d1, double d2) {
        double x = d1 - 0.0065;
        double y = d2 - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double ggLon = z * Math.cos(theta);
        double ggLat = z * Math.sin(theta);
        double result = Math.floor((ggLon + 0.00000005) * 1000000) / 1000000;
        double result1 = Math.floor((ggLat + 0.00000005) * 1000000) / 1000000;
        return new double[]{result, result1};
    }

    static double rad(double d) {
        return d * PI / 180.0;
    }

    static double distanceOfTwoPoint(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a1 = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = Math.sqrt(Math.sin(a1 / 2) * Math.sin(a1 / 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.sin(b / 2) * Math.sin(b / 2));
        s = 2 * s * EARTH_RADIUS;
        return s;
    }


    public static void main(String[] args) {
        double lng = 120.27285;//无锡
        double lat = 31.563755;
//            31.56375,120.2728
        //   118.0981  39.411823
//        double lng = 118.0981;//唐山
//        double lat = 39.411823;

        //gps84 -> Gcj02 ->baidu09     昨晚的博客代码
        //gps -> Gcj -> baidu               今天的js文件

        double[] mars = gps2Gcj(lng, lat);
        double[] bt = gcjToBaidu(mars[0], mars[1]);
//        double[] bt = gcjToBaidu(lng, lat);

        double[] gcj = baiduToGcj(lng, lat);
        double[] gps = gcjToGps(gcj[0], gcj[1]);
//        double[] gps = gcjToGps(lng, lat);

        System.out.println(bt[0] + "," + bt[1]);
//        List<String> arrayList = HttpRoute.getAddressByLocation(bt[1] + "", bt[0] + "");
        List<String> arrayList = HttpRoute.getAddressByLocation(gps[1] + "", gps[0] + "");
        System.out.println(arrayList);
    }


}
