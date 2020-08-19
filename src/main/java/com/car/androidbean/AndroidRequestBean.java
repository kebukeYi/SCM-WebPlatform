package com.car.androidbean;

import lombok.Data;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/4  18:02
 * @Description
 */
@Data
public class AndroidRequestBean {

    String method;//	是	string	方法名称	getAlarmOverview
    String timestamp;//是	string	时间	0
    String imei;//是	string	设别IMEI 获取固定设备报警信息
    String login_type;//是	string	登录用户类型	user
    String access_type;//	是	string	固定值inner	inner
    String account;//	是	string	账号	迁西中关村
    String map_type;//	是	string	地图类型	BAIDU
    String target;//	否	string	当前账号	迁西中关村
    String access_token;//否	string	登录用户token	2000738528711015826102223267d34fd09dcfd394338c61b3ff19e1570000010014010
    String time; //否	string	请求时秒数	1582610378
    String sign;//	否	string	接口签名	483a8ea15c37d9719cde885c1240c743
    String n;//	否	string	固定	E3A5E5C2B940CB1F3D2B6DDDD14B6C4D
    String appver;//否	string	应用版本号	3.2.2
    String appid;//	否	string	appId	1001
    String os;//	否	string	系统	android
    String lang;//	否	string	语言	zh-CN
    String source;//	否	string	来源	app1.2
    String http_seq;//	否	string	请求http编号	50757
    String apptype;//	否	string	apptype	goocar
    String lat;//	否	string	当前纬度	31.33048
    String lng;//否	string	当前经度	121.473128
    String vercode;//否	string	app版本号	260


}
