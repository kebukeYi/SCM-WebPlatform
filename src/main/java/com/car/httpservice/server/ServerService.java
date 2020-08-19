package com.car.httpservice.server;

import com.car.httpservice.client.ClientService;
import com.car.httpservice.HttpSendCrtl;
import com.car.httpservice.bean.Packet;
import com.car.setting.CtrlSetting;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/7  16:22
 * @Description
 */
@Service
public class ServerService {

    @Autowired
    private ClientService clientService;

    static SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");


    /*
     客户端地址
     */
//    static final String URL = "http://127.0.0.1:61120/api/";

    /*
    处理 登录请求
     */
    public Object AP00(String deviceId, String ctrl) {
        String falg = "setSplitTime";
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return result;
    }

    /*
    处理 位置 请求
   */
    public Object AP01(String deviceId, String ctrl) {
        String falg = "location";
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String sendbt = "TRVBP01" + CtrlSetting.OVER_FLAG;
        return sendbt;
    }

    /*
    处理 upIMEI 请求
   */
    public Object YP02(String deviceId, String ctrl) {
        Object result = null;
        String falg = "upIMEI";
        String bt[] = ctrl.split(",");
        String re = bt[1].replaceAll(" ", "");
        result = CtrlSetting.TRV + CtrlSetting.ZP02 + CtrlSetting.OVER_FLAG;


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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }

    /*
   处理  报警 请求
   需要回复 TRVBP106df157335e0253575c71533a53576d7759279053003100300037003953f700200020
0068007400740070003a002f002f007700770077002e006700700073002e0063006f006d002f
006d00610070002e0061007300700078003f006c00610074003d00320033002e00310032003
30026006c006e0067003d003100310033002e003100320033#
   */
    public Object AP10(String deviceId, String ctrl) {
        Object result = null;
        String falg = "warn";
        String bt[] = ctrl.split(",");
        String re = bt[1].replaceAll(" ", "");
        result = CtrlSetting.TRV + CtrlSetting.ZP02 + CtrlSetting.OVER_FLAG;
//TODO 这里需要查询

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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
    回复 ARGS
 */
    public Object BP14(String deviceId, String ctrl) {
        String falg = "ARGS";
        Packet packet = new Packet();
        packet.setDeviceId(deviceId);
        packet.setCtrl(ctrl);
        Object result = "TRVBP14,23.113,113.123# ";
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
 （下发 ）上传时间指令
     */
    public Object AP07(String deviceId, String ctrl) {
        String falg = "setSplitTime";
        Packet packet = new Packet();
        packet.setDeviceId(deviceId);
        packet.setCtrl(ctrl);
        Object result = clientService.BP07(packet);
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
（下发）设备重启：	指令
   */
    public Object BP61(String deviceId, String ctrl) {
        String falg = "reStart";
        Packet packet = new Packet();
        packet.setDeviceId(deviceId);
        packet.setCtrl(ctrl);
        Object result = clientService.BP61(packet);
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
 （下发）设备恢复出厂设置
    */
    public Object BP62(String deviceId, String ctrl) {
        String falg = "reSet";
        Packet packet = new Packet();
        packet.setDeviceId(deviceId);
        packet.setCtrl(ctrl);
        Object result = clientService.BP62(packet);
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }

    /*
 （下发）设置工作模式：
    */
    public Object BP84(String deviceId, String ctrl) {
        String falg = "setModel";
        Packet packet = new Packet();
        packet.setDeviceId(deviceId);
        packet.setCtrl(ctrl);
        Object result = clientService.BP84(packet);
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
 （下发）设置防拆报警开关
   */
    public Object BP85(String deviceId, String ctrl) {
        String falg = "setWarn";
        Packet packet = new Packet();
        packet.setDeviceId(deviceId);
        packet.setCtrl(ctrl);
        Object result = clientService.BP85(packet);
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
 （下发）更改 IP 或域名 ：
    */
    public Object BP64(String deviceId, String ctrl) {
        String falg = "setIP";
        Packet packet = new Packet();
        packet.setDeviceId(deviceId);
        packet.setCtrl(ctrl);
        Object result = clientService.BP64(packet);
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


    /*
   (下发）设置数据上传时间点 ：
   */
    public Object BP88(String deviceId, String ctrl) {
        String falg = "setUpTime";
        Packet packet = new Packet();
        packet.setDeviceId(deviceId);
        packet.setCtrl(ctrl);
        Object result = clientService.BP88(packet);
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }

    /*
 （下发）主控号码(2 个) :TRV BP78 000001, 13510737108, 13410988189 #
   */
    public Object BP78(String deviceId, String ctrl) {
        String falg = "setMainNum";
        Packet packet = new Packet();
        packet.setDeviceId(deviceId);
        packet.setCtrl(ctrl);
        Object result = clientService.BP78(packet);
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
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }


}
