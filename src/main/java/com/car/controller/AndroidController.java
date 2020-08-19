package com.car.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.bean.UserOrg;
import com.car.redis.JedisUtil6800;
import com.car.service.AndroidService;
import com.car.service.MonitorService;
import com.car.utils.JwtUtil;
import com.car.vo.DevinfoVo;
import com.car.vo.LogInInfo;
import com.car.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/26  14:43
 * @Description
 */
@RestController
@RequestMapping("/ad")
public class AndroidController {

    final static Logger logger = LoggerFactory.getLogger(AndroidController.class);

    @Autowired
    private AndroidService androidService;
    @Autowired
    private MonitorService monitorService;
    static Random random = new Random();

    //登陆接口   http://localhost:51665/ad/account/login?account=&password=
    @GetMapping("/account/login")
    public LogInInfo login(@RequestParam Map<String, String> map) throws UnsupportedEncodingException {
        LogInInfo logInInfo = new LogInInfo();
        String username = map.get("account");
        String password = map.get("password");
        String userStr = JedisUtil6800.getString9(username);
        if (userStr != null && !"".equals(userStr)) {
            UserOrg userOrg = JSON.parseObject(userStr, UserOrg.class);
            if (password.equals(userOrg.getPassword())) {
                logger.info(userOrg.getOrgName() + "  登录成功");
                String token = JwtUtil.createToken(userOrg);
                logInInfo.setData(token);
                logInInfo.setSuccess(true);
                logInInfo.setMsg("OK");
            } else {
                logger.info(userOrg.getOrgName() + "登陆失败");
                logInInfo.setSuccess(false);
                logInInfo.setMsg("OK");
                logInInfo.setData("");
            }
        }
        return logInInfo;
    }

    /*
    首页设备接口 / 设置超速警告
    access_type  inner
    account  迁西中关村
    http://localhost:51665/ad/1/account/devinfo?access_type=inner&account=%E8%BF%81%E8%A5%BF%E4%B8%AD%E5%85%B3%E6%9D%91&access_token=2000738528711015826102223267d34fd09dcfd394338c61b3ff19e1570000010014010&method=null
     */
    @GetMapping("/1/account/devinfo")
    public DevinfoVo getDevinfo(@RequestParam Map<String, String> map) {
        DevinfoVo devinfoVo = new DevinfoVo();
        System.out.println(map.toString());
        String method = map.get("method");
        if (method.equals("modifyUser")) {
            DevinfoVo devinfoV = androidService.SetOverSpeedAlarm(map);// http://localhost:51665/ad/1/account/devinfo?method=modifyUser&speed=50&overspeed_flag=0&imei=100022857985892&access_type=inner&account=迁西中关村
            devinfoVo = devinfoV;
        } else {
            DevinfoVo devinfoVo1 = androidService.getDevinfoVoByAndroidRequestBean(map);
            devinfoVo = devinfoVo1;
        }
        return devinfoVo;
    }


    /*
    设备状态接口
    access_type inner
    account  迁西中关村
    map_type  BAIDU
    http://localhost:51665/ad/1/account/monitor?access_type=inner&account=%E8%BF%81%E8%A5%BF%E4%B8%AD%E5%85%B3%E6%9D%91&access_token=2000738528711015826102223267d34fd09dcfd394338c61b3ff19e1570000010014010
     */
    @GetMapping("/1/account/monitor")
    public DevinfoVo getMonitor(@RequestParam Map<String, String> map) throws IOException {
        System.out.println(map.toString());
        DevinfoVo devinfoVo = androidService.getMonitorByAndroidRequestBean(map);
        return devinfoVo;
    }

    /*
    报警信息列表          待定参数  imei=13245666
    http://localhost:51665/ad/1/tool/get_alarminfo?method=getAlarmOverview&access_type=inner&account=迁西中关村&timestamp=0&login_type=user&map_type=BAIDU

    报警详细信息列表   待定参数  imei=13245666
    http://localhost:51665/ad/1/tool/get_alarminfo?method=getAlarmDetail&access_type=inner&account=迁西中关村&timestamp=0&login_type=user&map_type=BAIDU&page_dir=next&pagesize=15&alarm_type=8
     */
    @GetMapping("/1/tool/get_alarminfo")
    public DevinfoVo getAlarminfo(@RequestParam Map<String, String> map) {
        System.out.println(map.toString());
        String method = map.get("method");// getAlarmOverview    getAlarmDetail
        DevinfoVo devinfoVo = androidService.getAlarmsByIMEI(map);
        return devinfoVo;
    }

    /*
    根据经纬度获取地址
     http://localhost:51665/ad/1/tool/address?access_type=inner&account=迁西中关村&map_type=BAIDU&lat=&lng=
     */
    @GetMapping("/1/tool/address")
    public Result getAddressByLngLat(@RequestParam Map<String, String> map) {
        Result result = new Result();
        Map<String, String> adddress = new HashMap<>();
        String lat = map.get("lat");
        String lng = map.get("lng");
        try {
            URL url = new URL(" http://api.map.baidu.com/reverse_geocoding/v3/?ak=8Ab44gMcaknS587GCGnugV04MrdEA6Of&output=json&coordtype=wgs84ll&location=" + lat + "," + lng + "");
            HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
            ucon.connect();
            InputStream in = ucon.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String str = reader.readLine();
//            str = str.substring(str.indexOf("(") + 1, str.length()-1);
            System.out.println(str);
            JSONObject jsonObject = JSONObject.parseObject(str);
            System.out.println(jsonObject);
            String res = jsonObject.getString("result");
            JSONObject js = JSONObject.parseObject(res);
            String add = js.getString("formatted_address");
            adddress.put("address", add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setErrcode(0);
        result.setSuccess(true);
        result.setMsg("OK");
        result.setData(adddress);
        return result;
    }

    /*
    获取历史路径
     http://localhost:51665/ad/1/devices/history?access_type=inner&account=迁西中关村&map_type=BAIDU&imei=100202835053670&begin_time=&end_time=&limit=1000
     */
    @GetMapping("/1/devices/history")
    public Result getHistory(@RequestParam Map<String, String> map) {
        Result result = monitorService.getHistorysByNumAndTimeAndroid(map);
        return result;
    }

    /*
    获取车辆设置信息 efnce 根据method不同执行不同方法
    */
    @GetMapping("/1/tool/efence")
    public Result getEfence(@RequestParam Map<String, String> map) {
        String method = map.get("method");
        Result result = new Result();
        if (method.equals("get_switch_status")) {
            Result res = androidService.getSettings(map); //获取设置信息  http://localhost:51665/ad/1/tool/efence?method=get_switch_status&imei=100401637955336&map_type=BAIDU&account=迁西中关村
            result = res;
        } else if (method.equals("get_cities")) {
            Result res = androidService.getCities(map); //根据省获取市 http://localhost:51665/ad/1/tool/efence?method=get_cities&access_type=inner&account=迁西中关村&province=07
            result = res;
        } else if (method.equals("set_area_fence")) {
            Result res = androidService.set_area_fence(map); //设置出省或市  localhost:51665/ad/1/tool/efence?method=set_area_fence&citycode=0101&validate_flag=1&access_type=inner&imei=100022857985892&account=迁西中关村
            result = res;
        } else if (method.equals("switch_area_fence")) {
            Result res = androidService.closeOutAlarm(map); //关闭出省或市提示   http://localhost:51665/ad/1/tool/efence?method=switch_area_fence&imei=100022857985892&map_type=BAIDU&account=迁西中关村&id=&validate_flag=0
            result = res;
        } else if (method.equals("switch")) {
            Result res = androidService.switchEfence(map); //开启关闭电子围栏 http://localhost:51665/ad/1/tool/efence?method=switch&imei=100022857985892&account=迁西中关村&id=&validate_flag=1
            result = res;
        } else if (method.equals("set")) {
            Result res = androidService.setEfence(map); //设置电子围栏 http://localhost:51665/ad/1/tool/efence?method=set&imei=100022857985892&account=迁西中关村&id=&validate_flag=1&ticket=0&t=0&map_type=BAIDU&share_type=1&share_param=118.225594,39.770284,3075
            result = res;
        } else if (method.equals("saveAlarmPhoneNum")) {
            Result res = androidService.setMessage(map); //设置短信 http://localhost:51665/ad/1/tool/efence?method=saveAlarmPhoneNum&imei=&account=迁西中关村&id=&phonew_num=&access_type=inner
            result = res;
        } else if (method.equals("get_provinces")) {
            Result res = androidService.getProvinces(map); //获取省列表 http://localhost:51665/ad/1/tool/efence?method=get_provinces&account=迁西中关村&id=&access_type=inner
            return res;
        }
        return result;
    }

    @GetMapping("/1/tool/editinfo")
    public Result editinfo(@RequestParam Map<String, String> map) {
        Result res = androidService.editinfo(map);  //更改设置信息 http://localhost:51665/ad/1/tool/editinfo?account=迁西中关村&imei=100022857985892&access_type=inner&type=1&content=dev_name
        return res;
    }

}
