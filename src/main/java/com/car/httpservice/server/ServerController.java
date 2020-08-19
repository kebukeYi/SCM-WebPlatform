package com.car.httpservice.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.car.domain.MonitorTips;
import com.car.httpservice.bean.Packet;
import com.car.redis.JedisUtil6800;
import com.car.setting.CtrlSetting;
import com.car.utils.test1;
import com.car.vo.RootVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/7  16:52
 * @Description
 */
@RestController
@RequestMapping("/api")
public class ServerController {

    @Autowired
    ServerService serverService;

    static Logger logger = LoggerFactory.getLogger(ServerController.class);




    /**
     * @return java.lang.String
     * @author mmy
     * @date 2020/2/7  17:54
     * @Description  主动处理上传ICCID
     * TRVYP02,460023136470163,898602B1191550255484#
     */
    public String YP02(Packet packet) {
        logger.info("接受IMEI DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        MonitorTips monitorTips = JSON.parseObject(JedisUtil6800.getString3(deviceId), MonitorTips.class);
        monitorTips.setCommond("TRVYP02");
        String bt[] = res.split(",");
        String re = bt[1].replaceAll(" ", "");
        return CtrlSetting.TRV + CtrlSetting.ZP02 + CtrlSetting.OVER_FLAG;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/7  17:54
     * @Description  处理 位置数据包:TRV AP01 080524 A 22 32.9806N 114 04.9355E 000.1 061830 323.87 060 009 080 0 01 02, 460, 0, 9520, 3671 #  平台回复  TRVBP01#
     */
    public String AP01(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        MonitorTips monitorTips = JSON.parseObject(JedisUtil6800.getString3(deviceId), MonitorTips.class);
        String res = packet.getCtrl();
        String bt[] = res.split(",");    // LBS
        String mCC = bt[1];
        String mNC = bt[2];
        String lAC = bt[3];
        String cID = bt[4].replaceAll("#", "");
        monitorTips.setMCC(mCC);
        monitorTips.setMNC(mNC);
        monitorTips.setLAC(lAC);
        monitorTips.setCID(cID);
        monitorTips.setOnline(true);
        String bt0 = bt[0].replaceAll("TRVAP01", "");
        if (bt[0].contains("TRVAP10")) {
            bt0 = bt[0].replaceAll("TRVAP10", "");
        }
        if (bt0.contains("A")) {
            String xs[] = bt0.split("A");
            SimpleDateFormat format0 = new SimpleDateFormat("yyMMdd");
            String adate = xs[0];

            String latlng[] = xs[1].split("N");
            String Latitude = latlng[0];
            String lng = latlng[1].split("E")[0];

            float fLatitude = Float.parseFloat(Latitude) / 100;
            float flng = Float.parseFloat(lng) / 100;

            monitorTips.setLat(fLatitude + "");
            monitorTips.setLng(flng + "");
            // TRVAP01 080524 A2232.9806N11404.9355E 000.1 061830
            // 323.8706000908000102,460,0,9520,3671#

            char[] ar = latlng[1].split("E")[1].toCharArray();

            String speed = "";
            for (int i = 0; i <= 4; i++) {
                speed = speed + ar[i];
            }

//            monitorTips.setSpeed(Double.valueOf(speed));
            String glnztime = "";
            for (int i = 5; i <= 10; i++) {
                glnztime = glnztime + ar[i];
            }
            String fulltime = adate + glnztime;
            SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmss");
            Date reportdate = new Date();
            try {
                Date ads = format1.parse(fulltime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(ads);
                calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
                reportdate = calendar.getTime();
                monitorTips.setReceiveTime(reportdate.getTime() + "");
                monitorTips.setLocationTime(reportdate.getTime() + "");
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String anglespeed = "";
            for (int i = 11; i <= 16; i++) {
                anglespeed = anglespeed + ar[i];
            }
//            monitorTips.setDirect(Float.valueOf(anglespeed));
            String gsm = "";
            for (int i = 17; i <= 19; i++) {
                gsm = gsm + ar[i];
            }
            monitorTips.setSignal(Integer.valueOf(gsm).intValue());

            String sn = "";
            for (int i = 20; i <= 22; i++) {
                sn = sn + ar[i];
            }
            monitorTips.setSatellites(Integer.valueOf(sn).intValue());

            String battery = "";
            for (int i = 23; i <= 25; i++) {
                battery = battery + ar[i];
            }
            monitorTips.setOuterVoltage(battery);

            String status = "";
            for (int i = 26; i <= 26; i++) {
                status = status + ar[i];
            }

            String statusmode = "";
            for (int i = 27; i <= 28; i++) {
                statusmode = statusmode + ar[i];
            }

            String workmode = "";
            for (int i = 29; i <= 30; i++) {
                workmode = workmode + ar[i];
            }
            // 06000908000102:060 为 GSM 信号,009 为参与定位的卫星数,080
            // 为电池电量,0,为拆卸状态,1:
            // 为拆卸，0 代表没有拆卸,01 为设防状态,02 为工作模式 ,(设防,工作模式如果为 00,则代表无
            // 或未设置)，工作模式：01 正常定位 02 连续定位 03 智能监控 04 定时定位 00 没有工
            // 作模式

            // "Status":"NotDisassembly, Saving, ACCOFF, OilON,
            // EleON, EleConnected, Stopping",

            monitorTips.setStatus(status);
            if (res.contains("TRVAP01")) {
                monitorTips.setCommond("TRVAP01");
                String sendbt = "TRVBP01" + "#";
                return sendbt;
            }
            if (res.contains("TRVAP10")) {
                monitorTips.setCommond("TRVAP10");
                String Alarm = bt[5];
                String language = bt[6];
                String re = bt[7].replaceAll("#", "");

                monitorTips.setAlarm(Alarm);
                monitorTips.setLanguage(language);
                monitorTips.setRe(re);
                String sendbt = "TRVBP10" + "#";
                if (re.startsWith("0")) {
                    sendbt = "TRVBP10" + "#";
                }
                // 00:第一个 0:是否需要回复地址信息,0:不回复,1 回复. 第二个
                // 0:地址信息中是否包含手机超链接,0 不包含,1 包含

                if (re.startsWith("1")) {
                    String add = getAdd(flng + "", fLatitude + "");
                    System.out.println(add);

                    if (re.startsWith("11")) {
                        add = add + "  http://www.gps.com/map.aspx?lat=" + fLatitude + "&lng=" + flng + "";
                    }
                    test1.str2unicode(add).replaceAll("\\\\u", "");
                    sendbt = "TRVBP10" + add + "#";
                }

                return sendbt;
            }
        }
        return null;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/7  17:54
     * @Description 设备报警, 回复位置: TRV AP10 080524 A 22 32.9806N 114 04.9355E 000.1 061830 323.87 060 009 080 0 01 02, 460, 0, 9520, 3671, 00, zh-cn, 0 0 #
     */
    public RootVo AP10(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        MonitorTips monitorTips = JSON.parseObject(JedisUtil6800.getString3(deviceId), MonitorTips.class);
        return null;
    }



    /**
     * @return
     * @author mmy
     * @date 2020/2/7  17:54
     * @Description AGPS : TRV AP14, 460, 0, 9520, 3671 #
     */
    public String AP14(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        MonitorTips monitorTips = JSON.parseObject(JedisUtil6800.getString3(deviceId), MonitorTips.class);
        return CtrlSetting.TRV;
    }

    /**
     * @return java.lang.String
     * @author mmy
     * @date 2020/2/7  19:33
     * @Description （下发）设置上传时间间隔：TRVBP070000081440#     终端回复：TRV AP07 000008 0 #
     */
    @GetMapping("/setSplitTime")
    public Object AP07(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        Object result = serverService.AP07(packet.getDeviceId(), packet.getCtrl());
        return result;
    }


    //（下发）设备重启：			TRVBP61#       终端回复：  TRV AP61 #
    @GetMapping("/reStart")
    public Object BP61(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        Object result = serverService.BP61(packet.getDeviceId(), packet.getCtrl());
        JedisUtil6800.setString6(deviceId + ":reStart", res);
        return result;
    }

    //（下发）设备恢复出厂设置 ：  		TRVBP62#        无需回复：
    @GetMapping("/reSet")
    public Object BP62(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        JedisUtil6800.setString6(deviceId + ":reSet", res);
        Object result = serverService.BP62(deviceId, res);
        return result;
    }

    //（下发）设置工作模式：		TRVBP8400000801#         终端回复：TRVAP840000080#
    @GetMapping("/setModel")
    public Object BP84(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        JedisUtil6800.setString6(deviceId + ":setModel", res);
        Object result = serverService.BP84(deviceId, res);
        return result;
    }

    //（下发）设置防拆报警开关：    		TRVBP850000081#        终端回复：TRV AP85 000008 0 #
    @GetMapping("/setWarn")
    public Object BP85(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        JedisUtil6800.setString6(deviceId + ":setWarn", res);
        Object result = serverService.BP85(deviceId, res);
        return result;
    }

    //  （下发）更改 IP 或域名 ： 	TRVBP64,120440,www.321gps.com,8011#    终端回复： TRV AP64,120440 , www.321gps.com , 8011 #
    @GetMapping("/setIP")
    public Object BP64(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        JedisUtil6800.setString6(deviceId + ":setIP", res);
        Object result = serverService.BP64(deviceId, res);
        return result;
    }

    // （下发）设置数据上传时间点 ：TRVBP88,345678,052001#     终端回复： TRV AP88,345678, 05 20 01 #
    @GetMapping("/setUpTime")
    public Object BP88(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        JedisUtil6800.setString6(deviceId + ":setUpTime", res);
        Object result = serverService.BP88(deviceId, res);
        return result;
    }

    /**
     * @return java.lang.String
     * @author mmy
     * @date 2020/2/8  10:17
     * @Description （下发）主控号码(2 个) :TRVBP78000001,13510737108,13410988189#     终端回复 ：TRV AP78 000001, 13510737108 #
     */
    @GetMapping("/setMainNum")
    public Object BP78(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        JedisUtil6800.setString6(deviceId + ":setMainNum", res);
        Object result = serverService.BP78(deviceId, res);
        return result;
    }


    public static String getAdd(String lng, String lat) {
        String urlString = "http://api.map.baidu.com/geocoder/v2/?ak=kYe8LL7iq2txmvGvNwL1vsz3RRj729CO"
                + "&output=json"
                + "&callback=renderReverse&location="
                + lat + "," + lng;
        String res = "";
        BufferedReader in = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = in.readLine()) != null) {
                //if (line.contains("formatted")) {
                res = res + line;
                //line.replaceAll("<formatted_address>", "").replaceAll("</formatted_address>", "")
                //.replaceAll(" ", "").replaceAll("		", "");
                //}

            }

            res = res.replaceAll("renderReverse\\&\\&renderReverse\\(", "");
            res = res.replaceAll("\\)", "");


            JSONObject ob = JSON.parseObject(res);
            JSONObject ob1 = (JSONObject) ob.get("result");
            JSONObject ob2 = (JSONObject) ob1.get("addressComponent");


            return (String) ob1.get("formatted_address");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

}
