package com.car.service;

import com.car.setting.CtrlSetting;

public class CtrlService {


    //下行协议开始

    /*设置上传时间间隔(下行协议号:BP07,响应:AP07)
     * TRVBP070000081440#
     * 终端回复 TRVAP070000080# 0:命令执行状态,0 成功,1 失败
     */
    public static String creatBP07(String Minutes) {
        return CtrlSetting.TRV + CtrlSetting.BP07 + CtrlSetting.FLUID + Minutes + CtrlSetting.OVER_FLAG;
    }


    /**
     * 设备重启(下行:BP61)
     * @return TRVAP61#
     */
    public static String createBP61() {
        String ctrl = CtrlSetting.TRV + CtrlSetting.BP61 + CtrlSetting.OVER_FLAG;
        return ctrl;
    }


    /**
     * 设置工作模式(下行协议号:BP84,响应:AP84) TRVBP8400000801#   01: 01 表示正常定位 02 表示连续定位 00 表示没有设置工作模式 03: 连续监控
     *
     * @param no     服务器下发流水号
     * @param status 0:命令执行状态,0 成功,1 失败
     * @return TRVAP840000080#
     */
    public static String createBP84( String status) {
        String ctrl = CtrlSetting.TRV + CtrlSetting.BP84 + CtrlSetting.FLUID + status + CtrlSetting.OVER_FLAG;
        return ctrl;
    }

    /**
     * 设置防拆报警开关(下行协议号:BP85,响应:AP85) TRVBP07 000008 1#
     *
     * @param no     服务器下发流水号
     * @param status 0:命令执行状态,0 成功,1 失败
     * @return TRVAP850000080#
     */
    public static String createBP85(String status) {
        String ctrl = CtrlSetting.TRV + CtrlSetting.BP85 + CtrlSetting.FLUID + status + CtrlSetting.OVER_FLAG;
        return ctrl;
    }


    /**
     * 主控号码(2 个) (下发 BP78， 回复 AP78) TRVBP78000001,13510737108,13410988189#
     * 下发一个主控号码
     * @param no     服务器下发的流水号
     * @param number 设备返回设置的主控号码
     * @return TRVAP78000001, 13510737108#
     */
    public static String createBP78( String number) {
        String ctrl = CtrlSetting.TRV + CtrlSetting.BP78 + CtrlSetting.FLUID + "," + number + CtrlSetting.OVER_FLAG;
        return ctrl;
    }

    /*
    （下发）设置数据上传时间点 ：		TRV BP88 , 345678, 05 20 01 #
                                            终端回复： TRV AP88,345678, 05 20 01 #
     */
    public static String createBP88( String TimeStamp) {
        String ctrl = CtrlSetting.TRV + CtrlSetting.BP88 + CtrlSetting.FLUID + "," + TimeStamp + CtrlSetting.OVER_FLAG;
        return ctrl;
    }


    /**
     * 更改 IP 或域名(上行 AP64,下行 BP64)
     *
     * @param no   流水号
     * @param ip   ip
     * @param port 端口
     * @return TRVBP64, 120440 , www.321gps.com, 8011#
     */
    public static String createBP64(String ip, String port) {
        String ctrl = CtrlSetting.TRV + CtrlSetting.BP64 + "," + CtrlSetting.FLUID + "," + ip + "," + port + CtrlSetting.OVER_FLAG;
        return ctrl;
    }


    /*
    设备恢复出厂设置
     */
    public static String createBP62() {
        String ctrl = CtrlSetting.TRV + CtrlSetting.BP62 + CtrlSetting.OVER_FLAG;
        return ctrl;
    }

}
