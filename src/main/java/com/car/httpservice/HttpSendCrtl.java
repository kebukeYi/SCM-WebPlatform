package com.car.httpservice;

import java.util.ArrayList;
import java.util.List;


import com.car.domain.MonitorTips;
import com.car.utils.TimeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * HttpClient发送GET、POST请求
 *
 * @Author mmy
 * @CreateDate 2020.2.7 16:00
 */
public class HttpSendCrtl {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSendCrtl.class);

    /**
     * 返回成功状态码
     */
    private static final int SUCCESS_CODE = 200;

    static String POST_URL = "http://127.0.0.1:8080/request/body";
//    static String GET_URL = "http://60.2.213.22:51662/api/loc/";


    //tomcat
    static String GET_URL = "http://127.0.0.1:8080/api/";

    //netty
//    static String GET_URL = "http://127.0.0.1:51662/api/loc/";


    /**
     * 发送GET请求
     *
     * @param url               请求url
     * @param nameValuePairList 请求参数
     * @return JSON或者字符串
     * @throws Exception
     */
    public static JSONObject sendGet(String url, List<NameValuePair> nameValuePairList) throws Exception {
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            /**
             * 创建HttpClient对象
             */
            client = HttpClients.createDefault();
            /**
             * 创建URIBuilder
             */
            URIBuilder uriBuilder = new URIBuilder(url);
            /**
             * 设置参数
             */
            uriBuilder.setParameters(nameValuePairList);
            /**
             * 创建HttpGet
             */
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            /**
             * 设置请求头部编码
             */
            httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            /**
             * 设置返回编码
             */
            httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            /**
             * 请求服务
             */
            response = client.execute(httpGet);
            /**
             * 获取响应吗
             */
            int statusCode = response.getStatusLine().getStatusCode();

            if (SUCCESS_CODE == statusCode) {
                /**
                 * 获取返回对象
                 */
                HttpEntity entity = response.getEntity();
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString(entity, "UTF-8");
                /**
                 * 转换成json,根据合法性返回json或者字符串
                 */
                try {
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                } catch (Exception e) {
                    return jsonObject;
                }
            } else {
                LOGGER.error("HttpClientService-line: {}, errorMsg{}", 97, "GET请求失败！");
            }
        } catch (Exception e) {
            LOGGER.error("HttpClientService-line: {}, Exception: {}", 100, e);
        } finally {
            response.close();
            client.close();
        }
        return null;
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param nameValuePairList
     * @return JSON或者字符串
     * @throws Exception
     */
    public static JSONObject sendPost(String url, List<NameValuePair> nameValuePairList) throws Exception {
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            /**
             *  创建一个httpclient对象
             */
            client = HttpClients.createDefault();
            /**
             * 创建一个post对象
             */
            HttpPost post = new HttpPost(url);
            /**
             * 包装成一个Entity对象
             */
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            /**
             * 设置请求的内容
             */
            post.setEntity(entity);
            /**
             * 设置请求的报文头部的编码
             */
            post.setHeader(new BasicHeader("Content-Type", "application/json;charset=utf-8"));
            /**
             * 设置请求的报文头部的编码
             */
            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            /**
             * 执行post请求
             */
            response = client.execute(post);
            /**
             * 获取响应码
             */
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode) {
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                /**
                 * 转换成json,根据合法性返回json或者字符串
                 */
                try {
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                } catch (Exception e) {
                    return jsonObject;
                }
            } else {
                LOGGER.error("HttpClientService-line: {}, errorMsg：{}", 146, "POST请求失败！");
            }
        } catch (Exception e) {
            LOGGER.error("HttpClientService-line: {}, Exception：{}", 149, e);
        } finally {
            response.close();
            client.close();
        }
        return null;
    }


    /**
     * 组织请求参数{参数名和参数值 下标保持一致}
     *
     * @param params 参数名数组
     * @param values 参数值数组
     * @return 参数对象
     */
    public static List<NameValuePair> getParams(Object[] params, Object[] values) {
        /**
         * 校验参数合法性
         */
        boolean flag = params.length > 0 && values.length > 0 && params.length == values.length;
        if (flag) {
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for (int i = 0; i < params.length; i++) {
                nameValuePairList.add(new BasicNameValuePair(params[i].toString(), values[i].toString()));
            }
            return nameValuePairList;
        } else {
            LOGGER.error("HttpClientService-line: {}, errorMsg：{}", 197, "请求参数为空且参数长度不一致");
        }
        return null;
    }

    /*
    位置请求
    */
    public static Object sendloc(String s) {
        Object result = null;
        /**
         * 参数值
         */
        Object[] params = new Object[]{"ctrl"};
        /**
         * 参数名
         */
        Object[] values = new Object[]{""};
        values[0] = s;
        List<NameValuePair> paramsList = HttpSendCrtl.getParams(params, values);
        try {
//            result = HttpSendCrtl.sendPost(POST_URL, paramsList);
            result = HttpSendCrtl.sendGet(GET_URL, paramsList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static MonitorTips stringToBean(String str) {
        MonitorTips monitorTips = new MonitorTips();
        if (str.startsWith("(") && str.endsWith(")")) {
            System.out.println(str.length());
            str = str.replaceAll("\\(", "");
            str = str.replaceAll("\\)", "");
            System.out.println("str:" + str);
            monitorTips.setAM(str.substring(0, 1));
            monitorTips.setDeviceNumber(str.substring(1, 11));
            monitorTips.setWorkMode(str.substring(11, 12));
            monitorTips.setStatusMode(str.substring(12, 13));
            monitorTips.setICCID(str.substring(13, 33));
            System.out.println(monitorTips.toString());
            monitorTips.setLocationTime(str.substring(33, 45));
            System.out.println(monitorTips.toString());
            monitorTips.setSignal(Integer.parseInt(str.substring(45, 47)));
            monitorTips.setElectricity(Integer.parseInt(str.substring(47, 49)));
            monitorTips.setTemp(str.substring(49, 51));
            monitorTips.setHumi(str.substring(51, 53));
            monitorTips.setDirect(str.substring(53, 57));
            System.out.println(monitorTips.toString());
            monitorTips.setLat(str.substring(57, 67));
            monitorTips.setLng(str.substring(67, 78));
            monitorTips.setSpeed(str.substring(78, 83));
            monitorTips.setGPSDirect(str.substring(83, 86));
            monitorTips.setMCC(str.substring(86, 89));
            monitorTips.setMNC(str.substring(90, 92));
            monitorTips.setLAC(str.substring(93, 97));
            monitorTips.setCID(str.substring(98, 102));
            System.out.println(monitorTips.toString());
            monitorTips.setStationInfo(str.substring(103, 107));
            System.out.println(monitorTips.toString());
            monitorTips.setLastUpdateTime(TimeUtils.getGlnz());
        }
        return monitorTips;
    }


    public static void main(String[] args) {
        int start = 1111100000;
        for (int i = 1; i <= 2000; i++) {
            try {
//                Thread.sleep(50);
                String deviceId = start + i + "";
                String ctrl = "(A" + deviceId + "008986089860987654321020020810510599883070+180N22.123456E104.234567000.1356460,00,9234,1234,-100)";
                Object object = sendloc(ctrl);
                System.out.println(object.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
    
