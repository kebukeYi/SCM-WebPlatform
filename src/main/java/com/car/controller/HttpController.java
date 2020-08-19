package com.car.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;

import com.car.CarbdsApplication;
import com.car.domain.MonitorTips;
import com.car.redis.JedisUtil6800;
import com.car.utils.TimeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/8 17:34
 * @Description
 */
@RestController
@RequestMapping("/api")
public class HttpController {

    static SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
    static SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmss");

    public static ConcurrentLinkedQueue<MonitorTips> queue = new ConcurrentLinkedQueue();
    public static long now = 0l;

    public static int n = 0;

    // 设备上发
    // http://localhost:51662/api/loc?s=(A88888888880033333333333333333333320020810510599883070+180N22.677777E104.232222000.1356460,00,9234,1234,-100)
    @GetMapping("/loc")
    public Object s(@RequestParam("s") String s) {

        if (s.length() < 20) {
            System.out.println("dac-->" + s);
            deviceToServer(s);
            return null;
        } else {
            System.out.println("loc-->" + s);
            Object result = test(s);
            result = "(" + result + ")";
            return result;
        }

    }

    @PostMapping("/locpost")
    public Object sp(@RequestBody String map) {

        // String s = request.getParameter("s");
        // String Content_Length = request.getParameter("Content-Length");

        String sbodyJSON = map;
        String bodyJSON = "";
        // if(s!=null) {
        // bodyJSON = (String) map.get("s");
        // }else {
        bodyJSON = map;
        // }

        try {
            bodyJSON = URLDecoder.decode(bodyJSON, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Object object = null;
        if (bodyJSON != null) {
            if (bodyJSON.startsWith("(")) {
                System.out.println("bodyJSON-->" + bodyJSON);
            }
        }

        bodyJSON = bodyJSON.substring(bodyJSON.indexOf("("), bodyJSON.length());
//        if (s != null) {
//            if (s.startsWith("(")) {
//                System.out.println("parmJSON : " + s);
//            }
//        }
        System.out.println("loc-->" + bodyJSON);
        if (bodyJSON != null && bodyJSON.length() > 0) {
            object = testpost(bodyJSON);
        }

        return object;
    }

    // http://localhost:51662/api/locok?s=(OK200208174256B88888888882002081051050200208115200S=www.128.com:1203)
    @GetMapping("/locok")
    public void locok(@RequestParam("s") String s) {
        System.out.println("locok-->" + s);
        if (s.contains("O")) {
            String IMEI = s.substring(1, 11);
            JedisUtil6800.delString6(IMEI);
        }
    }

    // http://localhost:51662/api/commondstodevice?s=(B88888888882002081051050200208115200S=www.128.com:1203)
    @GetMapping("/commondstodevice")
    public Object toDevice(@RequestParam("s") String s) {
        System.out.println("commondstodevice-->" + s);
        return toDevices(s);
    }

    // http://localhost:51662/api/dac?s=(OK9876543210)
    @GetMapping("/dac")
    public void deviceServer(@RequestParam("s") String s) {
        System.out.println("dac-->" + s);
        deviceToServer(s);
    }

    public Object toDevices(String str) {
        if (str.startsWith("(") && str.endsWith(")")) {
            str = str.replaceAll("\\(", "");
            str = str.replaceAll("\\)", "");
            String b = str.substring(0, 1);
            String IMEI = str.substring(1, 11);
            if (!b.equals("B")) {
                return "服务器需要B 开头";
            }

            String savestr = str.replaceAll(IMEI, "");

            String firString = "B" + format.format(new Date());
            savestr = firString + savestr.substring(11, savestr.length());

            // MonitorTips monitorTips = JedisUtil6491.getmonitorTips1(IMEI);
            // monitorTips.setCommond(stringToHexString(savestr) );
            // JedisUtil6491.setString1(IMEI, JSON.toJSONString(monitorTips));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            CarbdsApplication.commandList.put(IMEI, savestr);
            JedisUtil6800.setString6(IMEI, savestr);
            return "已经加入列表：" + savestr;
        } else {
            return "错误" + format.format(new Date());
        }
    }

    //分割10K body 數據包  2/9/23：00
    public static List<String> splitString(String str) {
        String[] bt = null;
        bt = str.split("\\(");
        for (String s : bt) {
            s = s.replaceAll("\\)", "");
        }
        List<String> list = Arrays.asList(bt);
        return list;
    }

    public static String stringToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 16进制字符串转换为字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "gbk");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public void deviceToServer(String str) {
//        String srt="(OK8888888888)";
        if (str.startsWith("(") && str.endsWith(")")) {
            str = str.replaceAll("\\(", "");
            str = str.replaceAll("\\)", "");

            String IMEI = str.substring(1, 11);

            System.out.println("deviceToServer-->" + str);
//            MonitorTips monitorTips  = JedisUtil6491.getmonitorTips1(IMEI);
//            if(null!= monitorTips) {
//            	  monitorTips.setCommond("OK" + monitorTips.getCommond());
//                  JedisUtil6491.setString1(monitorTips.getDeviceNumber(), JSON.toJSONString(monitorTips));
//                  try {
//      				Thread.sleep(50);
//      			} catch (InterruptedException e) {
//      				// TODO Auto-generated catch block
//      				e.printStackTrace();
//      			}
//            }

            JedisUtil6800.delString6(IMEI);

        }
    }

    /*
     * 位置包 请求 2.0 (A88888888880 0 33333333333333333333 200208105105 99 88 30 70 +180
     * N22.677777 E104.232222 000.1 356460,00,9234,1234,-100)
     */
    public Object test(String str) {
        if (str.startsWith("(") && str.endsWith(")")) {
            System.out.println(str.length() + "--->" + str);
            str = str.replaceAll("\\(", "");
            str = str.replaceAll("\\)", "");

            String imeiString = str.substring(1, 11);
            MonitorTips monitorTips = CarbdsApplication.MonitorTipsmap.get(imeiString);
            if (null == monitorTips) {
                monitorTips = new MonitorTips();
                monitorTips.setCommond("");
                System.out.println("---------------->NEW" + str);
            }

            int i = 0;
            monitorTips.setAM(str.substring(i, i + 1));
            i = i + 1;
            monitorTips.setDeviceNumber(str.substring(i, i + 10));
            i = i + 10;
            monitorTips.setWorkMode(str.substring(i, i + 1));
            i = i + 1;
            monitorTips.setDefinetimeString(str.substring(i, i + 4));
            i = i + 4;
            monitorTips.setStatusMode(str.substring(i, i + 1));
            i = i + 1;
            monitorTips.setICCID(str.substring(i, i + 20));
            i = i + 20;
            monitorTips.setLocationTime(str.substring(i, i + 12));
            i = i + 12;
            monitorTips.setSignal(Integer.parseInt(str.substring(i, i + 2)));
            i = i + 2;
            monitorTips.setElectricity(Integer.parseInt(str.substring(i, i + 2)));
            i = i + 2;
            monitorTips.setTemp(str.substring(i, i + 2));
            i = i + 2;
            monitorTips.setHumi(str.substring(i, i + 2));
            i = i + 2;

            monitorTips.setDirect(str.substring(i, i + 4));
            i = i + 4;
            monitorTips.setLat(str.substring(i, i + 10));
            i = i + 10;
            monitorTips.setLng(str.substring(i, i + 11));
            i = i + 11;
            monitorTips.setSpeed(str.substring(i, i + 5));
            i = i + 5;
            monitorTips.setGPSDirect(str.substring(i, i + 3));
            i = i + 3;
            monitorTips.setMCC(str.substring(i, i + 3));
            i = i + 3;
            i = i + 1;
            monitorTips.setMNC(str.substring(i, i + 2));
            i = i + 2;
            i = i + 1;
            monitorTips.setLAC(str.substring(i, i + 4));
            i = i + 4;
            i = i + 1;
            monitorTips.setCID(str.substring(i, i + 4));
            i = i + 4;
            i = i + 1;
            monitorTips.setStationInfo(str.substring(i, i + 4));

            monitorTips.setLastUpdateTime(TimeUtils.getGlnz());

            System.out.println("---------------->NEW" + JSON.toJSONString(monitorTips));

            if (now == 0) {
                now = new Date().getTime();
            }
            queue.offer(monitorTips);

            String commandList = CarbdsApplication.commandList.get(monitorTips.getDeviceNumber());// 库中的 都是15位长度 设备ID

            CarbdsApplication.commandList.remove(monitorTips.getDeviceNumber());

//            String r = "";
//            if(null!=commandList) {
//            	for (String strd : commandList) {
//                    r = r + strd;
//                }
//            }

//
            if (null == commandList) {
                commandList = "";
            }

            if (str.substring(0, 1).equals("A")) {
                return "O" + format1.format(new Date()) + commandList;
            } else {
                return "K" + format1.format(new Date()) + commandList;
            }
        } else {
            return "Error" + format1.format(new Date());
        }
    }

    public Object testpost(String stree) {
        String ssString[] = stree.split("\\(");
        String DeviceNumber = "";
        for (int w = 0; w < ssString.length; w++) {
            String str = ssString[w];
            if (str.length() > 5 && str.endsWith(")")) {

                str = str.replaceAll("\\(", "");
                str = str.replaceAll("\\)", "");
                System.out.println(str.length() + " tpost---->" + str);
                String imeiString = str.substring(1, 11);
                MonitorTips monitorTips = JedisUtil6800.getMonitorTipsByDeviceId(imeiString);
                if (null == monitorTips) {
                    monitorTips = new MonitorTips();
                    monitorTips.setCommond("");
                    System.out.println("---------------->NEW");
                }

                int i = 0;
                monitorTips.setAM(str.substring(i, i + 1));
                i = i + 1;
                monitorTips.setDeviceNumber(str.substring(i, i + 10));
                i = i + 10;
                monitorTips.setWorkMode(str.substring(i, i + 1));
                i = i + 1;
                monitorTips.setDefinetimeString(str.substring(i, i + 4));
                i = i + 4;
                monitorTips.setStatusMode(str.substring(i, i + 1));
                i = i + 1;
                monitorTips.setICCID(str.substring(i, i + 20));
                i = i + 20;
                monitorTips.setLocationTime(str.substring(i, i + 12));
                i = i + 12;
                monitorTips.setSignal(Integer.parseInt(str.substring(i, i + 2)));
                i = i + 2;
                monitorTips.setElectricity(Integer.parseInt(str.substring(i, i + 2)));
                i = i + 2;
                monitorTips.setTemp(str.substring(i, i + 2));
                i = i + 2;
                monitorTips.setHumi(str.substring(i, i + 2));
                i = i + 2;

                monitorTips.setDirect(str.substring(i, i + 4));
                i = i + 4;
                monitorTips.setLat(str.substring(i, i + 10));
                i = i + 10;
                monitorTips.setLng(str.substring(i, i + 11));
                i = i + 11;
                monitorTips.setSpeed(str.substring(i, i + 5));
                i = i + 5;
                monitorTips.setGPSDirect(str.substring(i, i + 3));
                i = i + 3;
                monitorTips.setMCC(str.substring(i, i + 3));
                i = i + 3;
                i = i + 1;
                monitorTips.setMNC(str.substring(i, i + 2));
                i = i + 2;
                i = i + 1;
                monitorTips.setLAC(str.substring(i, i + 4));
                i = i + 4;
                i = i + 1;
                monitorTips.setCID(str.substring(i, i + 4));
                i = i + 4;
                i = i + 1;
                monitorTips.setStationInfo(str.substring(i, i + 4));

                System.out.println("---------------->NEW" + JSON.toJSONString(monitorTips));

                monitorTips.setLastUpdateTime(TimeUtils.getGlnz());
                DeviceNumber = monitorTips.getDeviceNumber();
                JedisUtil6800.setString1(monitorTips.getDeviceNumber(), JSON.toJSONString(monitorTips));
            }
        }

        String stringList = JedisUtil6800.getAllCommd(DeviceNumber);

        // return "(K" + format1.format(new Date()) + stringList+")";
        return "(K" + format1.format(new Date()) + ")";

    }

    private byte[] body;

    /**
     * 获取请求Body
     *
     * @param request request
     * @return String
     */
    public String getBodyString(HttpServletRequest request) {
        try {
            return inputStream2String(request.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求Body
     *
     * @return String
     */
    public String getBodyString() {
        final InputStream inputStream = new ByteArrayInputStream(body);

        return inputStream2String(inputStream);
    }

    /**
     * 将inputStream里的数据读取出来并转换成字符串
     *
     * @param inputStream inputStream
     * @return String
     */
    private String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {

            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
        return sb.toString();
    }

    /*
位置包 请求 2.0
(A88888888880 0 33333333333333333333 200208105105 99 88 30 70 +180 N22.677777 E104.232222 000.1 356460,00,9234,1234,-100)
*/
    public Object lastTest(String str) {
        if (str.startsWith("(") && str.endsWith(")")) {
            System.out.println(str.length());
            str = str.replaceAll("\\(", "");
            str = str.replaceAll("\\)", "");
            MonitorTips monitorTips = new MonitorTips();
            monitorTips.setAM(str.substring(0, 1));
            monitorTips.setDeviceNumber(str.substring(1, 11));
            monitorTips.setWorkMode(str.substring(11, 12));
            monitorTips.setStatusMode(str.substring(12, 13));
            monitorTips.setICCID(str.substring(13, 34));
            monitorTips.setLocationTime(str.substring(34, 46));
            monitorTips.setSignal(Integer.parseInt(str.substring(46, 48)));
            monitorTips.setElectricity(Integer.parseInt(str.substring(48, 50)));
            monitorTips.setTemp(str.substring(50, 52));
            monitorTips.setHumi(str.substring(52, 54));
            monitorTips.setDirect(str.substring(54, 58));
            monitorTips.setLat(str.substring(58, 68));
            monitorTips.setLng(str.substring(68, 79));
            monitorTips.setSpeed(str.substring(79, 84));
            monitorTips.setGPSDirect(str.substring(84, 87));
            monitorTips.setMCC(str.substring(87, 90));
            monitorTips.setMNC(str.substring(91, 93));
            monitorTips.setLAC(str.substring(94, 98));
            monitorTips.setCID(str.substring(99, 103));
            monitorTips.setStationInfo(str.substring(104, 108));
            monitorTips.setLastUpdateTime(TimeUtils.getGlnz());
            JedisUtil6800.setString1(monitorTips.getDeviceNumber(), JSON.toJSONString(monitorTips));
            List<String> stringList = JedisUtil6800.getAllCommd6(monitorTips.getDeviceNumber());
            String r = "";
            for (String strd : stringList) {
                r = r + strd;
            }

            return "OK:" + format.format(new Date()) + "待执行指令: " + r;
        } else {
            return "Error" + format.format(new Date());
        }
    }
}
