package com.car.baiduMapRoute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.redis.JedisUtil6800;
import com.car.redis.bean.Point;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/4  13:40
 * @Description
 */
public class HttpRoute {

    /*
    调用百度驾车规划路线接口：http://lbsyun.baidu.com/index.php?title=webapi/directionlite-v1
    服务器端密钥   x2dp5PxaSgNTy8ckiEOEagGtLIGUqXLt
    http://api.map.baidu.com/directionlite/v1/driving?origin=40.01116,116.339303&destination=39.936404,116.452562&ak=x2dp5PxaSgNTy8ckiEOEagGtLIGUqXLt
     */

    /*
    调用百度 全球逆地理编码服务：http://lbsyun.baidu.com/index.php?title=webapi/guide/webservice-geocoding-abroad
    服务器端密钥   x2dp5PxaSgNTy8ckiEOEagGtLIGUqXLt
    http://api.map.baidu.com/reverse_geocoding/v3/?ak=x2dp5PxaSgNTy8ckiEOEagGtLIGUqXLt&output=json&coordtype=wgs84ll&location=31.225696563611,121.49884033194  //GET请求
     */

    /**
     * 调用对方接口方法
     *
     * @param path 对方或第三方提供的路径
     * @param data 向对方或第三方发送的数据，大多数情况下给对方发送JSON数据让对方解析
     */
    public static String interfaceGET(String path, String data) {
        HttpURLConnection conn = null;
        InputStream is = null;
        String str = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(path);
            //打开和url之间的连接
            conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            /**设置URLConnection的参数和普通的请求属性****start***/
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            /**设置URLConnection的参数和普通的请求属性****end***/
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("GET");//GET和POST必须全大写
            /**GET方法请求*****start*/
            /**
             * 如果只是发送GET方式请求，使用connet方法建立和远程资源之间的实际连接即可；
             * 如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求参数。
             * */
            conn.connect();

            /**GET方法请求*****end*/

            /***POST方法请求****start*/

            /*out = new PrintWriter(conn.getOutputStream());//获取URLConnection对象对应的输出流

            out.print(data);//发送请求参数即数据

            out.flush();//缓冲数据
            */
            /***POST方法请求****end*/

            //获取URLConnection对象对应的输入流
            is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null) {
                result.append(new String(str.getBytes(), "UTF-8"));//解决中文乱码问题
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
        }
        return null;
    }

    public static String interfacePOST(String path, String data) {
        HttpURLConnection conn = null;
        InputStream is = null;
        String str = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(path);
            //打开和url之间的连接
            conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            /**设置URLConnection的参数和普通的请求属性****start***/
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            /**设置URLConnection的参数和普通的请求属性****end***/
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("POST");//GET和POST必须全大写
            /**GET方法请求*****start*/
            /**
             * 如果只是发送GET方式请求，使用connet方法建立和远程资源之间的实际连接即可；
             * 如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求参数。
             * */
            conn.connect();

            /**GET方法请求*****end*/

            /***POST方法请求****start*/

            out = new PrintWriter(conn.getOutputStream());//获取URLConnection对象对应的输出流

            out.print(data);//发送请求参数即数据

            out.flush();//缓冲数据

            /***POST方法请求****end*/

            //获取URLConnection对象对应的输入流
            is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null) {
                result.append(new String(str.getBytes(), "UTF-8"));//解决中文乱码问题
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
        }
        return null;
    }

    /**
     * 获取baidu 返回轨迹数组
     */
    public static List<Point> getPointsByStartAndEndLocation(String beginLatLng, String destLatLng) {
        List<Point> pointList = new ArrayList<>();
        String url = "http://api.map.baidu.com/directionlite/v1/driving?origin=" + beginLatLng + "&destination=" + destLatLng + "&ak=x2dp5PxaSgNTy8ckiEOEagGtLIGUqXLt";
        JSONObject map = null;
        map = JSONObject.parseObject(interfaceGET(url, ""));
        String result = map.getString("result");
        System.out.println("result -->" + result);
        if (result == null) {
            return null;
        }
        JSONObject re = JSONObject.parseObject(result);
        JSONArray roArray = JSONArray.parseArray(re.getString("routes"));
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) roArray.get(0);
        } catch (Exception e) {
            return null;
        }
        JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("steps"));
        System.out.println("steps -->" + jsonArray);
        System.out.println("起始经纬度: " + beginLatLng + "--> 目标经纬度：" + destLatLng);
        System.out.println("总共路段点是 ：" + jsonArray.size() + " 个");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            String[] paths = object.getString("path").split(";");
            for (String str : paths) {
                String lng = str.split(",")[0];
                String lat = str.split(",")[1];
                Point point = new Point(lat, lng);
                pointList.add(point);
            }
        }
        return pointList;
    }

    public static void main1(String[] args) {
        // 36.4212824400,112.2143554700      36.4389612400,110.8740234400
        String url = "http://api.map.baidu.com/directionlite/v1/driving?origin=37.35,120.38&destination=37.688247,103.478578&ak=x2dp5PxaSgNTy8ckiEOEagGtLIGUqXLt";
        JSONObject map = JSONObject.parseObject(interfaceGET(url, ""));
        System.out.println("map :  " + map);
        String result = map.getString("result");
        System.out.println("result -->" + result);
        JSONObject re = JSONObject.parseObject(result);
        JSONArray roArray = JSONArray.parseArray(re.getString("routes"));
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) roArray.get(0);
        } catch (Exception e) {
            return;
        }
        JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("steps"));
        System.out.println("steps -->" + jsonArray);
        System.out.println("总共坐标点是 ：" + jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            String[] paths = object.getString("path").split(";");
            for (String str : paths) {
                String lng = str.split(",")[0];
                String lat = str.split(",")[1];
                System.out.println(lng + " " + lat);
            }
        }


        //interfaceUtil("http://172.83.28.221:7001/NSRTRegistration/test/add.do","id=8888888&name=99999999");  //post请求
    }

    /**
     * result : {"formatted_address":"北京市朝阳区呼家楼西里七巷甲12号","business":"呼家楼,团结湖,京广桥","sematic_description":"","cityCode":131,"roads":[],"location":{"lng":116.47275273058478,"lat":39.92700019083837},"poiRegions":[],"pois":[],"
     * addressComponent":{"city_level":2,"country":"中国","town":"","distance":"93","city":"北京市","adcode":"110105","country_code_iso":"CHN","country_code_iso2":"CN","country_code":0,"town_code":"","province":"北京市","street":"呼家楼西里七巷","district":"朝阳区","street_number":"甲12号","direction":"西"}}
     */
    public static List<String> getAddressByLocation(String lat, String lng) {
        String address = "";
        String province = "";
        String city = "";
        String street = "";
        String district = "";
        List<String> stringList = new ArrayList<>();
        stringList.add(0, address);
        stringList.add(1, province);
        stringList.add(2, city);
        stringList.add(3, street);
        stringList.add(4, district);
//        String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=x2dp5PxaSgNTy8ckiEOEagGtLIGUqXLt&output=json&coordtype=wgs84ll&location=" + lat + "," + lng;
        String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=gZztmDtqpBGjxLjxz73SQksXGGqUbwwW\n&output=json&coordtype=wgs84ll&location=" + lat + "," + lng;
        JSONObject map = (JSONObject) JSONObject.parse(interfaceGET(url, null));
        JSONObject result = (JSONObject) map.get("result");
        System.out.println("result : " + result);
        try {
            JSONObject addressComponent = result.getJSONObject("addressComponent");
            address = result.getString("formatted_address");
            province = addressComponent.getString("province");
            city = addressComponent.getString("city");
            street = addressComponent.getString("street");
            district = addressComponent.getString("district");

            stringList.add(0, address);
            stringList.add(1, province);
            stringList.add(2, city);
            stringList.add(3, street);
            stringList.add(4, district);
        } catch (Exception e) {
            return stringList;
        }
        return stringList;
    }


    public static void main12(String[] args) throws InterruptedException {
        String lat = "22.998851590";
        String lng = "109.335937500";
        List<String> stringList = null;
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            lng = androidMonitorTip.getLat();
            lat = androidMonitorTip.getLng();
            Thread.sleep(50);
            stringList = getAddressByLocation(lat, lng);
            System.out.println("lat:  " + lat + "   lng: " + lng + "   " + stringList);
            androidMonitorTip.setAddress(stringList.get(0));
            androidMonitorTip.setProvince(stringList.get(1));
            androidMonitorTip.setCity(stringList.get(2));
            androidMonitorTip.setLat(lng);
            androidMonitorTip.setLng(lat);
            JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
        }
    }

    public static void main(String[] args) {
        String lat = "39.92";
        String lng = "116.46";
        System.out.println(getAddressByLocation(lat, lng));
    }

    /*
    {"success":true,"message":"操作成功！","code":0,"result":{"location":{"lat":"41.119883221047","lng":"122.07184792314"},"time":"2020-03-11 17:58:20"},"timestamp":1584007518143}
     */
    public static void main4(String[] args) {
        String url = "http://183.196.51.30:53617/car/location?deviceNumber=100124026422698";
        String success = interfaceGET(url, "");
        System.out.println(success);
        JSONObject jsonObject = JSON.parseObject(success);
        JSONObject result = jsonObject.getJSONObject("result");
        JSONObject location = result.getJSONObject("location");
        String lng = location.getString("lng");
        String lat = location.getString("lat");
        String time = result.getString("time");
        System.out.println(lat + "      " + lng + "      " + time);

    }


}

