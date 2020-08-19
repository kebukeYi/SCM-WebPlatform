package com.car.httpservice.client;

import com.alibaba.fastjson.JSON;
import com.car.domain.MonitorTips;
import com.car.httpservice.bean.Packet;
import com.car.httpservice.server.ServerService;
import com.car.redis.JedisUtil6800;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/7  19:46
 * @Description
 */
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    ServerService serverService;

    static SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");


    static Logger logger = LoggerFactory.getLogger(ClientController.class);

    /**
     * @return 废弃
     * @author mmy
     * @date 2020/2/7  20:04
     * @Description web 接口 发送登录包
     * http://localhost:51662/api/login?deviceId=100002089117142&ctrl=TRVAP00353456789012345#
     */
    @GetMapping("/sendLogin")
    public Object AP00(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        Object result = clientService.sendAP00(deviceId, res);
        return result;
    }

    /**
     * @return 废弃
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 发送心跳
     * 设备报警,回复位置：设备心跳包：			TRV CP01, 060 009 080 0 02 #
     * http://localhost:51662/api/sendHeart?deviceId=100002089117142&ctrl=TRVCP01,060009080002#
     */
    @GetMapping("/sendHeart")
    public Object CP01(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        Object result = clientService.sendCP01(deviceId, res);
        return result;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 发送位置包
     * http://localhost:51662/api/location?deviceId=100002089117142&ctrl=TRVAP01080524A2232.9806N11404.9355E000.1061830323.8706000908000102,460,0,9520,3671#
     */
    @GetMapping("/sendLocation")
    public Object AP01(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
//        String str="(A88888888880033333333333333333333320020810510599883070+180N22.677777E104.232222000.1356460,00,9234,1234,-100)";
        Object result = clientService.test(res);
        return result;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 发送位置包
     * http://localhost:51662/api/s?s=(A88888888880033333333333333333333320020810510599883070+180N22.677777E104.232222000.1356460,00,9234,1234,-100)
     */
    @GetMapping("/s")
    public Object s(@RequestParam("s") String s) {
//        String str="(A88888888880033333333333333333333320020810510599883070+180N22.677777E104.232222000.1356460,00,9234,1234,-100)";
        Object result = clientService.test(s);
        return result;
    }

    @GetMapping("/rOK")
    public void rOK(@RequestParam("s") String s) {
        if (s.contains("O")) {
//            String str = "(O8888888888)";
            String IMEI = s.substring(1, 11);
            JedisUtil6800.delString6(IMEI);
        }
    }

    @GetMapping("/toDevice")
    public Object toDevice(@RequestParam("s") String s) {
        //(B2002081051050200208115200S=www.128.com:1203)
        return toDevices(s);
    }

    @GetMapping("/deviceToServer")
    public void deviceServer(@RequestParam("s") String s) {
        //(B2002081051050200208115200S=www.128.com:1203)
        deviceToServer(s);
    }


    public Object toDevices(String str) {
        if (str.startsWith("(") && str.endsWith(")")) {
//            System.out.println(str.length());
            str = str.replaceAll("\\(", "");
            str = str.replaceAll("\\)", "");
            String b = str.substring(0, 1);
            String IMEI = str.substring(1, 11);
            if (!b.equals("B")) {
                return "服务器需要B 开头";
            }
            MonitorTips monitorTips = JSON.parseObject(JedisUtil6800.getString1(IMEI), MonitorTips.class);
            monitorTips.setCommond(str);
            JedisUtil6800.setString6(IMEI + ":" + new Date().getTime(), str);
            return "已经加入列表：" + format.format(new Date());
        } else {
            return "错误" + format.format(new Date());
        }
    }

    public void deviceToServer(String str) {
        if (str.startsWith("(") && str.endsWith(")")) {
            str = str.replaceAll("\\(", "");
            str = str.replaceAll("\\)", "");
            String b = str.substring(0, 2);
            String IMEI = str.substring(15, 25);
            if (!b.equals("OK")) {
                MonitorTips monitorTips = JSON.parseObject(JedisUtil6800.getString1(IMEI), MonitorTips.class);
                monitorTips.setCommond(str);
                JedisUtil6800.delString6(IMEI);
                JedisUtil6800.setString1(monitorTips.getDeviceNumber(), JSON.toJSONString(monitorTips));
            }
        }
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 发送IMEI
     * http://localhost:51662/api/sendUpIMEI?deviceId=100002089117142&ctrl=TRVYP02,460023136470163,898602B1191550255484#
     */
    @GetMapping("/sendUpIMEI")
    public Object YP02(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        Object result = clientService.sendYP02(deviceId, res);
        return result;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 发送报警
     * 设备报警,回复位置：TRV AP10 080524 A 22 32.9806N 114 04.9355E 000.1 061830 323.87 060 009 080 0 01 02, 460, 0, 9520, 3671, 00, zh-cn, 0 0 #
     * http://localhost:51662/api/sendWarn?deviceId=100002089117142&ctrl=TRVAP10080524A2232.9806N11404.9355E000.1061830323.8706000908000102,460,0,9520,3671,00,zh-cn,00#
     */
    @GetMapping("/sendWarn")
    public Object AP10(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        Object result = clientService.sendAP10(deviceId, res);
        return result;
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/7  19:46
     * @Description 发送AGPS
     * 设备报警,回复位置：AGPS ：TRV AP14, 460, 0, 9520, 3671 #
     * http://localhost:51662/api/sendAGRS?deviceId=100002089117142&ctrl=TRVAP14,460,0,9520,3671#
     */
    @GetMapping("/sendAGRS")
    public Object AP14(Packet packet) {
        logger.info("DeviceId: {} , Ctrl: {}", packet.getDeviceId(), packet.getCtrl());
        String deviceId = packet.getDeviceId();
        String res = packet.getCtrl();
        Object result = clientService.sendAP14(deviceId, res);
        return result;
    }


}
