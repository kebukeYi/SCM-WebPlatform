package com.car.domain;

import lombok.Data;

import java.io.Serializable;

/*
 * {"Status":1,"Result":{
"Lng":126.65082547698,
"Lat":45.7885083268257,
"DeviceNumber":"181219310018619",
"DeviceName":"181219310018619",
"Status":"NotDisassembly, Saving, ACCOFF, OilON, EleON, EleConnected, Stopping",
"Alarm":"None",
"StopTime":"2020-01-18T09:33:49",
"ReceiveTime":"2020-01-19T07:23:53.1051837",
"LocationTime":"2020-01-19T04:50:12",
"LocationType":1,
"Online":true,
"Direct":181.78,
"Electricity":90,
"Satellites":0,
"Signal":100,
"Speed":0.0,
"ModelType":0,
"Mileage":0.0,
"OuterVoltage":null},
"ErrorMessage":null}
 */
@Data
public class MonitorTips implements Serializable {

    private String AM;
    private String DeviceName;
    private String DeviceNumber;//IMEI
    private String workMode;
    private String Status;//设备状态  在线/未拆卸/省电/ACC关  --NotDisassembly, ContinueLocation, ACCOFF, OilON, EleON
    private String statusMode;//防拆
    private String ICCID;
    private String LocationTime;//定位时间
    private int Signal;
    private int Electricity;
    private String OuterVoltage;
    private String temp;//温度
    private String humi;//湿度
    private String Direct;//方向角
    private String Lat;
    private String Lng;
    private String Speed;
    private String GPSDirect;
    private String MCC;
    private String MNC;//国家码,460 为中国,0:MNC,
    private String LAC;// 9520:LAC,
    private String CID;//十进制,3671, CID,十进制
    private String stationInfo;
    private String commond;//指令
    private String Language;
    private String IMSI;
    private String latlngarraylist;
    private String latlngdest;
    private String AP88;
    private String IP;
    private String Port;
    private String Alarm;// 设备 低电报警 类型
    private String FenceAlarmType;// 围栏报警 类型    0-无设置    1-进围栏  2-出围栏  3-进出围栏
    private boolean BindFence;// 是否关联围栏
    private String StopTime;//
    private String LocationType;// 1-北斗     2       3
    private boolean Online;//是否在线
    private int Satellites;//卫星数量
    private String ModelId;// 0-所有型号  1-R10
    private String ModelType;// 1 0 11 10  无线 有点 充电   设备类型
    private String ModelName;//R10
    private boolean Attention;// 是否关注
    private int Mileage;
    private String Re;
    //    private String PlateNo;//车牌号
    private String Aliase;//用于显示设备ID
    private String DeviceType;//先未用
    //    private String CarModel;//汽车类型
    private String OnlineList;//保存最近30天的数据
    private String LastUpdateTime;
    private String ReceiveTime;//信号时间
    private String Simcard;
    private boolean Disabled;//是否过期
    private boolean Activation;//是否激活
    private String DisabledTime;//过期时间
    private String OrgName;//所属组名字
    private String OrgnizationIds;//所属组名 G102-C5
    private String OrgnizationId;//所属组名 G102-C5
    private String CatalogueId;//所属目录id
    private String CateName;//目录名字
    private String FenceId;//所属围栏

    //新加字段
    private String definetimeString;
    private String Address;//地址
    private String province;//省份
    private String city;//城市

    /*
    influxdb 字段
     */
    private String tagTime;
    private String tagNum;

    @Override
    public String toString() {
        return "MonitorTips{" +
                "DeviceName='" + DeviceName + '\'' +
                ", DeviceNumber='" + DeviceNumber + '\'' +
                ", ICCID='" + ICCID + '\'' +
                ", IMSI='" + IMSI + '\'' +
                '}';
    }
}