package com.car.httpservice.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.car.domain.MonitorTips;
import com.car.httpservice.HttpSendCrtl;
import com.car.httpservice.bean.Packet;
import com.car.httpservice.server.ServerService;
import com.car.redis.JedisUtil6800;
import com.car.setting.CtrlSetting;
import com.car.utils.TimeUtils;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/7  16:22
 * @Description
 */
@Service
public class ClientService {

    @Autowired
    ServerService serverService;

    static Logger logger = LoggerFactory.getLogger(ClientService.class);
    static SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");


    /*
     服务器地址
     */
//    static final String URL = "http://127.0.0.1:61119/api/";


    /*
     登录请求
     */
    public Object sendAP00(String deviceId, String ctrl) {
        String falg = "login";
        Object result = serverService.AP00(deviceId, ctrl);
        /**
         * 参数值
         */
        Object[] params = new Object[]{"deviceId", "ctrl"};
        /**
         * 参数名
         */
        Object[] values = new Object[]{"", ""};
        values[0] = deviceId;
        values[1] = ctrl;
        List<NameValuePair> paramsList = HttpSendCrtl.getParams(params, values);
//        try {
//            result = HttpSendCrtl.sendGet(URL + falg, paramsList);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
    位置包 请求
 */
    public Object sendAP01(String deviceId, String ctrl) {
        String falg = "location";
        Object result = serverService.AP01(deviceId, ctrl);
        /**
         * 参数值
         */
        Object[] params = new Object[]{"deviceId", "ctrl"};
        /**
         * 参数名
         */
        Object[] values = new Object[]{"", ""};
        values[0] = deviceId;
        values[1] = ctrl;
        List<NameValuePair> paramsList = HttpSendCrtl.getParams(params, values);
//        try {
//            result = HttpSendCrtl.sendGet(URL + falg, paramsList);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
位置包 请求 2.0
(A88888888880 0 33333333333333333333 200208105105 99 88 30 70 +180 N22.677777 E104.232222 000.1 356460,00,9234,1234,-100)
*/
    public Object test(String str) {
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
            return "OK" + format.format(new Date()) + r;
        } else {
            return "Error" + format.format(new Date());
        }
    }


    /*
   upIMEI包请求
 */
    public Object sendYP02(String deviceId, String ctrl) {
        String falg = "upIMEI";
        Object result = serverService.YP02(deviceId, ctrl);
        /**
         * 参数值
         */
        Object[] params = new Object[]{"deviceId", "ctrl"};
        /**
         * 参数名
         */
        Object[] values = new Object[]{"", ""};
        values[0] = deviceId;
        values[1] = ctrl;
        List<NameValuePair> paramsList = HttpSendCrtl.getParams(params, values);
//        try {
//            result = HttpSendCrtl.sendGet(URL + falg, paramsList);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }

    /*
    上传报警请求
     */
    public Object sendAP10(String deviceId, String ctrl) {
        String falg = "warn";
        Object result = serverService.AP10(deviceId, ctrl);
        /**
         * 参数值
         */
        Object[] params = new Object[]{"deviceId", "ctrl"};
        /**
         * 参数名
         */
        Object[] values = new Object[]{"", ""};
        values[0] = deviceId;
        values[1] = ctrl;
        List<NameValuePair> paramsList = HttpSendCrtl.getParams(params, values);
//        try {
//            result = HttpSendCrtl.sendGet(URL + falg, paramsList);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
    上传心跳请求
     */
    public Object sendCP01(String deviceId, String ctrl) {
        String falg = "heart";
        Object result = null;
        /**
         * 参数值
         */
        Object[] params = new Object[]{"deviceId", "ctrl"};
        /**
         * 参数名
         */
        Object[] values = new Object[]{"", ""};
        values[0] = deviceId;
        values[1] = ctrl;
        List<NameValuePair> paramsList = HttpSendCrtl.getParams(params, values);
//        try {
//            result = HttpSendCrtl.sendGet(URL + falg, paramsList);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
     上传AGPS请求
      */
    public Object sendAP14(String deviceId, String ctrl) {
        String falg = "AGRS";
        //TRVBP14,23.113,113.123#
        Object result = serverService.BP14(deviceId, ctrl);
        /**
         * 参数值
         */
        Object[] params = new Object[]{"deviceId", "ctrl"};
        /**
         * 参数名
         */
        Object[] values = new Object[]{"", ""};
        values[0] = deviceId;
        values[1] = ctrl;
        List<NameValuePair> paramsList = HttpSendCrtl.getParams(params, values);
//        try {
//            result = HttpSendCrtl.sendGet(URL + falg, paramsList);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 处理 设置时间命令  并且 回执
     */
//    @GetMapping("/setSplitTime")
    public String BP07(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        JedisUtil6800.setString6(deviceId + ":setSplitTime", res);
        res.replaceAll("TRVBP07", "");
        String count = "000008";
        return CtrlSetting.TRV + CtrlSetting.AP07 + count + "0" + CtrlSetting.OVER_FLAG;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 设备重启：TRV BP61 #       终端回复：  TRV AP61 #
     */
//    @GetMapping("/reStart")
    public String BP61(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        return CtrlSetting.TRV + CtrlSetting.AP61 + CtrlSetting.OVER_FLAG;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 设备恢复出厂设置：
     */
//    @GetMapping("/reSet")
    public String BP62(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        return CtrlSetting.TRV;
    }

    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 设置工作模式：：
     */
//    @GetMapping("/setModel")
    public String BP84(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        String result = "0";
        return CtrlSetting.TRV + CtrlSetting.AP84 + result + CtrlSetting.OVER_FLAG;
    }

    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 设置防拆报警开关：
     */
//    @GetMapping("/setWarn")
    public String BP85(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        String result = "0";
        return CtrlSetting.TRV + CtrlSetting.AP85 + result + CtrlSetting.OVER_FLAG;
    }

    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 更改 IP 或域名 ： 	TRV BP64 , 120440 , www.321gps.com , 8011 #       终端回复： TRV AP64,120440 , www.321gps.com , 8011 #
     */
//    @GetMapping("/setIP")
    public String BP64(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        res.replaceAll("TRVBP64", "TRVAP64");
        return res;
    }

    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description // （下发）设置数据上传时间点 ：TRV BP88 , 345678, 05 20 01 #                                            终端回复： TRV AP88,345678, 05 20 01 #
     */
//    @GetMapping("/setUpTime")
    public String BP88(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        res.replaceAll("TRVBP88", "TRVAP88");
        return res;
    }

    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description
     * @Description （下发）主控号码(2 个) :TRV BP78 000001, 13510737108, 13410988189 #     终端回复 ：TRV AP78 000001, 13510737108 #
     */
//    @GetMapping("/setMainNum")
    public String BP78(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        String[] bt = res.split(",");
        bt[0].replaceAll("TRVBP78", "TRVAP78");
        return bt[0] + CtrlSetting.SPILE + bt[1] + CtrlSetting.OVER_FLAG;
    }

}
