package com.car.androidbean;

import lombok.Data;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/26  20:18
 * @Description
 */
@Data
public class AndroidMonitorTip {


    private String am;
    private String imei;//     460071005183534
    private String iccid;//    89860308558948102419
    private String imsi;//      460071005183534
    private String number;//车牌号
    private String phone;//物联卡号码

    private String PlateNo;//车牌号码

    private String aliase;//用于显示设备ID  PC 端
    private String dev_number;//设备号码            有
    private String dev_name;//设备名称   "goome-22875"
    private String dev_type;//设备类型         GS03B
    private int alarm_type_id;//报警类型ID     有
    private int satellites;//卫星数量
    private String province;//省份
    private String city;//城市
    private String address;//当前地址             无
    private String model_id;// 0-所有型号  1-R10
    private String model_name;//R10
    private String model_type;// 1 0 11 10  无线 有点 充电   设备类型
    private boolean online;//
    private boolean attention;// 是否关注
    private boolean disabled;//是否过期
    private boolean activation;//是否激活
    private boolean bind_fence;// 是否关联围栏
    private String bind_time;// 绑定时间 list
    private boolean is_enable;//是否可用     有
    private boolean efence_support;//是否支持栅栏         true
    private String online_list;//保存最近30天的数据
    private String commond;//指令
    private String lng; //             有
    private String lat;  //               有
    private String lnglat;
    private String speed;   //              有
    private String owner;//联系人        无
    private String tel;//联系人电话       无
    private String user_id;
    private String group_id;//  0              有
    private String group_name; //
    private String catalogue_id;//所属目录id          有
    private String cata_name;
    private String fence_id;//所属 围栏id
    private String fence_alarm_type;// 围栏报警 类型    0-无设置    1-进围栏  2-出围栏  3-进出围栏
    private String receive_time;//信号时间
    private String status;// 设备状态  在线/未拆卸/省电/ACC关  --NotDisassembly, ContinueLocation, ACCOFF, OilON, EleON                                     有
    private String online_status;// Stop:24:5:7:21
    private String status_mode;//防拆
    private String battery_life;//  电池
    private String voltage;//电压
    private int signal;// 信号
    private String temp;//温度
    private String humi;//湿度
    private String direct;//方向角
    private String GPS_direct;
    private String MCC;
    private String MNC;//国家码,460 为中国,0:MNC,
    private String LAC;// 9520:LAC,
    private String CID;//十进制,3671, CID,十进制
    private String stationInfo;//备用
    private String latlng_arraylist;
    private int mileage;//路程
    private String gps_status;//gps状态                          无
    private String location_time;//定位时间
    private String location_type;// 定位方式
    private String last_update_time;
    private String alarm_time;// 报警时间

    private String speed_alarm;//超速警告

    private String gps_time;//gps时间                             无
    private String send_time;//报警时间                          无
    private String stop_time;//停留更新时间
    private String stop_minutes;//停留时间
    private String sys_time;//    0,                                       无
    private String heart_time;//                0,                       无
    private String server_time;//           0,                           无
    private String in_time;//           1556357223
    private String out_time;//过期时间                     有
    private String record_time;//                       0,                   无
    private String enable_time;//可用时间            单位/天
    private String remark;//备注                                            无
    private String client_product_type;//                                 无
    private String goome_card;//"",
    private String is_iot_card;//   0
    private String dir;//1
    private String device_info;//1
    private String device_info_new;//1
    private String seconds;// 0,
    private String course;// 0
    private String acc;//-1
    private String acc_seconds;//0
    private String voice_status;//0
    private String voice_gid;//0
    private String smart_record;//-1
    private String trickle_power;//-1
    private String record_len;//-1
    private String pos_accuracy;// -218121568
    private String srec_volume;// 0
    private String expire_date;//过期的日子
    private String mf_date;//出厂日期
    private String AP88;
    private String ip;
    private String port;

    //新加字段
    private String definetimeString;

    /*
    Android新增字段
     */
    private String area_validate_flag;//地区是否设置围栏
    private String area_validate_alarm;// 设置进出地区围栏提示
    private String efence_validate_flag;//电子围栏是否设置
    private String efence_validate_alarm;//电子围栏是否设置提示
    private String overspeed_flag;//是否设置超速警告
    private String citycode;//省市码


    /*
influxdb 字段
 */
    private String time;
    private String tagTime;
    private String tagNum;
    private String sequenceNo;//安卓接口 历史轨迹 顺序
    private String dev_number_1;


    @Override
    public String toString() {
        return "AndroidMonitorTip [dev_number=" + dev_number + ", lng=" + lng + ", lat=" + lat + "]";
    }


}
