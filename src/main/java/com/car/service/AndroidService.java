package com.car.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.*;
import com.car.bean.UserOrg;
import com.car.domain.Fence;
import com.car.redis.JedisUtil6800;
import com.car.utils.KeyUtil;
import com.car.utils.ProvinceAndCity;
import com.car.utils.TimeUtils;
import com.car.vo.DevinfoVo;
import com.car.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.car.service.FenceService.getFenceNameByFenceType;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/4  18:20
 * @Description
 */
@Service
public class AndroidService {

    @Autowired
    CommonService commonService;

    /**
     * 首页设备接口
     * access_type  inner
     * account  迁西中关村
     * (method=null, timestamp=null, imei=null, login_type=null, access_type=inner, account=迁西中关村, map_type=null, target=null, access_token=2000738528711015826102223267d34fd09dcfd394338c61b3ff19e1570000010014010, time=null, sign=null, n=null, appver=null, appid=null, os=null, lang=null, source=null, http_seq=null, apptype=null, lat=null, lng=null, vercode=null)
     */
    public DevinfoVo getDevinfoVoByAndroidRequestBean(Map<String, String> map) {
        String account = map.get("account");
        String access_type = map.get("access_type");
        DevinfoVo devinfoVo = new DevinfoVo();
        List<Children> childrenList = new ArrayList<>();
        List<AndroidMonitorTip> androidMonitorTips = null;
        List<Device> deviceList = null;
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(account), UserOrg.class);
        JSONArray sonArray = JSONArray.parseArray(userOrg.getSon_ids());
        for (Object obj : sonArray) {
            Children children = new Children();
            children.setHaschild(false);
            children.setId(obj + "");
            children.setName("迁西第" + obj + "车队");
            children.setShowname("迁西第" + obj + "车队");
            childrenList.add(children);
        }
        deviceList = getAndroidDeviceByUserId(account);
        if (childrenList.size() == 0 || deviceList.size() == 0) {
            devinfoVo.setErrcode(404);
            devinfoVo.setSuccess(false);
            devinfoVo.setBeyondLimit(false);
            devinfoVo.setCustomer_id(userOrg.getUser_id() + "");
            devinfoVo.setMsg("ERROR");
            devinfoVo.setBeyondLimit(false);
            devinfoVo.setChildren(childrenList);
            devinfoVo.setData(deviceList);
            return devinfoVo;
        }
        devinfoVo.setErrcode(0);
        devinfoVo.setSuccess(true);
        devinfoVo.setCustomer_id(userOrg.getUser_id() + "");
        devinfoVo.setMsg("OK");
        devinfoVo.setBeyondLimit(false);
        devinfoVo.setChildren(childrenList);
        devinfoVo.setData(deviceList);
        return devinfoVo;
    }

    /**
     * 已更改
     */
    public List<Device> getAndroidDeviceByUserId(String account) {
        List<AndroidMonitorTip> androidMonitorTipList = commonService.getAndroidMonitorTipsByUserId(account);
        List<Device> deviceList = new ArrayList<>();
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            Device device = new Device();
            BeanUtils.copyProperties(androidMonitorTip, device);
            device.setName(androidMonitorTip.getDev_name());
            deviceList.add(device);
        }
        return deviceList;
    }


    /**
     * 设备状态接口
     * access_type inner
     * account  迁西中关村
     * map_type  BAIDU    已更改
     */
    public DevinfoVo getMonitorByAndroidRequestBean(Map<String, String> map) {
        String account = map.get("account");
        String access_type = map.get("access_type");
        String map_type = map.get("map_type");
        List<DeviceStatus> deviceList = null;
        deviceList = getAndroidDeviceStatusByUserId(account);
        DevinfoVo devinfoVo = new DevinfoVo();
        devinfoVo.setCustomer_id(account);
        if (deviceList.size() == 0) {
            devinfoVo.setErrcode(404);
            devinfoVo.setSuccess(false);
            devinfoVo.setBeyondLimit(false);
            devinfoVo.setMsg("ERROR");
            devinfoVo.setBeyondLimit(false);
            devinfoVo.setData(deviceList);
            return devinfoVo;
        }
        devinfoVo.setErrcode(0);
        devinfoVo.setSuccess(true);
        devinfoVo.setMsg("OK");
        devinfoVo.setBeyondLimit(false);
        devinfoVo.setData(deviceList);
        return devinfoVo;
    }

    /**
     * 已更改
     */
    public List<DeviceStatus> getAndroidDeviceStatusByUserId(String account) {
        List<AndroidMonitorTip> androidMonitorTipList = commonService.getAndroidMonitorTipsByUserId(account);
        List<DeviceStatus> deviceList = new ArrayList<>();
        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            DeviceStatus deviceStatus = new DeviceStatus();
            if (!androidMonitorTip.getLat().equals("0.0") && !androidMonitorTip.getLng().equals("0.0")) { //过滤经纬度为0 的 数据
                BeanUtils.copyProperties(androidMonitorTip, deviceStatus);
                deviceStatus.setHeart_time("0");
                deviceStatus.setServer_time("0");
                deviceStatus.setRecord_time("0"); //这三个属性 数据库bean没有 先都置为0
                String vol = "";
                String battery_life = "";
                String angle = "";
                String gps_status = "";
                String enable = "";
                if (androidMonitorTip.getVoltage() != null) {
                    vol = Integer.toHexString(Integer.valueOf(androidMonitorTip.getVoltage()));
                    if (vol.length() == 1) {
                        vol = "000" + vol;
                    } else if (vol.length() == 2) {
                        vol = "00" + vol;
                    } else if (vol.length() == 3) {
                        vol = "0" + vol;
                    }
                } else {
                    vol = "0000";
                }
                if (androidMonitorTip.getBattery_life() != null) {
                    battery_life = Integer.toHexString(Integer.valueOf(androidMonitorTip.getBattery_life()));
                    if (battery_life.length() == 1) {
                        battery_life = "0" + battery_life;
                    }
                } else {
                    battery_life = "00";
                }
                if (androidMonitorTip.getDirect() != null) {
                    angle = Integer.toHexString(Integer.valueOf(androidMonitorTip.getDirect()));
                    if (angle.length() == 1) {
                        angle = "0" + angle;
                    }
                } else {
                    angle = "00";
                }
                if (androidMonitorTip.getGps_status() != null) {
                    gps_status = Integer.toHexString(Integer.valueOf(androidMonitorTip.getGps_status()));
                    if (gps_status.length() == 1) {
                        gps_status = "0" + gps_status;
                    }
                } else {
                    gps_status = "00";
                }
                if (androidMonitorTip.is_enable() == true) {
                    enable = "01";
                } else {
                    enable = "00";
                }
                deviceStatus.setStatus("000604ea00" + battery_life + vol + "00" + angle + "00" + gps_status + enable);
                deviceList.add(deviceStatus);
            }
        }
        return deviceList;
    }


    /**
     * 报警信息列表          待定参数  imei=13245666
     * http://localhost:51663/1/tool/get_alarminfo?method=getAlarmOverview&access_type=inner&account=迁西中关村&timestamp=0&login_type=user&map_type=BAIDU
     * method    getAlarmOverview
     * access_type   inner
     * account      迁西中关村
     * timestamp    0
     * imei           12333245656
     * login_type    user
     * map_type      BAIDU
     * <p>
     * 报警详细信息列表   待定参数  imei=13245666
     * http://localhost:51663/1/tool/get_alarminfo?method=getAlarmDetail&access_type=inner&account=迁西中关村&timestamp=0&login_type=user&map_type=BAIDU&page_dir=next&pagesize=15&alarm_type=8
     */
    public DevinfoVo getAlarmsByIMEI(Map<String, String> map) {
        String method = map.get("method");// getAlarmOverview    getAlarmDetail
        String account = map.get("account");
        DevinfoVo devinfoVo = new DevinfoVo();
        devinfoVo.setErrcode(0);
        devinfoVo.setSuccess(true);
        devinfoVo.setMsg("OK");
        if ("getAlarmOverview".equals(method)) {
            List<Alarm> alarms = getAlarmOverview(map);
            if (alarms.size() == 0) {
                devinfoVo.setErrcode(1);
                devinfoVo.setSuccess(false);
                devinfoVo.setMsg("ERROR");
                devinfoVo.setData(null);
            }
            devinfoVo.setData(alarms);
            return devinfoVo;
        } else if ("getAlarmDetail".equals(method)) {
            List<AlarmDetail> alarmDetails = getAlarmDetailsByUserId(map);
            if (alarmDetails.size() == 0) {
                devinfoVo.setErrcode(1);
                devinfoVo.setSuccess(false);
                devinfoVo.setMsg("ERROR");
                devinfoVo.setData(null);
            }
            devinfoVo.setData(alarmDetails);
        }
        return devinfoVo;
    }

    /**
     * 已更改
     */
    public List<Alarm> getAlarmOverview(Map<String, String> map) {
        String account = map.get("account");
        return getAlarmsByUserId(account);
    }

    /**
     * 已更改
     */
    public List<Alarm> getAlarmsByUserId(String account) {
        List<AndroidMonitorTip> androidMonitorTipList = commonService.getAndroidMonitorTipsByUserId(account);
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(account), UserOrg.class);
        List<com.car.domain.Alarm> alarms = commonService.getAlarmAndroidMonitorTipsByUserId(account);
        Map<Integer, Integer> integerMap = getAlarmTypeSumByandroidMonitorTips(alarms);
        List<Alarm> alarmList = new ArrayList<>();
        for (Map.Entry entry : integerMap.entrySet()) {
            Alarm alarm = new Alarm();
            alarm.setAlarm_type_id((Integer) entry.getKey());
            alarm.setAlarm_num((Integer) entry.getValue());
            alarm.setAlarm_type(getAlarmNameByAlarmType(entry.getKey() + ""));
            alarm.setSend_time(System.currentTimeMillis() + "");
            // alarm.setUser_name(userOrg.getOrgName());
            String name = userOrg.getUserName();
            alarm.setUser_name(name);
            alarmList.add(alarm);
        }
        return alarmList;
    }

    /**
     已更改
     */
//    public List<AlarmDetail> getAlarmDetail(Map<String, String> map) {
//        String account = map.get("account");
//        return getAlarmDetailsByUserId(account);
//    }

    /**
     * 已更改
     */
    public List<AlarmDetail> getAlarmDetailsByUserId(Map<String, String> map) {
        String account = map.get("account");
        String alarmType = map.get("alarm_type");
        List<AlarmDetail> alarms = new ArrayList<>();
        List<com.car.domain.Alarm> alarmList = commonService.getAlarmAndroidMonitorTipsByUserId(account);
        for (com.car.domain.Alarm alarm : alarmList) {
            if (alarm.getAlarm_type_id() == Integer.valueOf(alarmType)) {
                AlarmDetail alarmDetail = new AlarmDetail();
                BeanUtils.copyProperties(alarm, alarmDetail);
                alarmDetail.setId(alarm.getDev_number());
                alarmDetail.setAlarm_type(getAlarmNameByAlarmType(alarm.getAlarm_type_id() + ""));
                AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(alarm.getDev_number());
                alarmDetail.setGps_time(androidMonitorTip.getGps_time());
                alarmDetail.setDev_type(androidMonitorTip.getDev_type());
                alarmDetail.setGps_status(androidMonitorTip.getGps_status());
                alarmDetail.setDir(androidMonitorTip.getDir());
                alarmDetail.setSpeed(Integer.parseInt(androidMonitorTip.getSpeed()));
                try {
                    URL url = new URL(" http://api.map.baidu.com/reverse_geocoding/v3/?ak=8Ab44gMcaknS587GCGnugV04MrdEA6Of&output=json&coordtype=wgs84ll&location=" + alarm.getLat() + "," + alarm.getLng() + "");
                    HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
                    ucon.connect();
                    InputStream in = ucon.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String str = reader.readLine();
                    JSONObject jsonObject = JSONObject.parseObject(str);
                    String res = jsonObject.getString("result");
                    JSONObject js = JSONObject.parseObject(res);
                    String add = js.getString("formatted_address");
                    alarmDetail.setAddress(add);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alarms.add(alarmDetail);
            }
        }
        return alarms;

    }


    /**
     * 获取报警类型数量
     */
    public static Map<Integer, Integer> getAlarmTypeSumByandroidMonitorTips(List<com.car.domain.Alarm> alarms) {
        /*分组， 按照字段中某个属性将list分组*/
        Map<Integer, List<com.car.domain.Alarm>> map = alarms.stream().collect(Collectors.groupingBy(t -> t.getAlarm_type_id()));
        Map<Integer, Integer> integerMap = new HashMap<>();
        System.out.println("按报警类型分组" + map);
        /*然后再对map处理，这样就方便取出自己要的数据*/
        for (Map.Entry<Integer, List<com.car.domain.Alarm>> entry : map.entrySet()) {
//            System.out.println("key:" + entry.getKey());
//            System.out.println("value:" + entry.getValue().size());
            integerMap.put(entry.getKey(), entry.getValue().size());
        }
        return integerMap;
    }

    /**
     * 根据报警类型 获取对应名称
     */
    public String getAlarmNameByAlarmType(String AlarmType) {
        String alarmName = "";
        switch (AlarmType) {
            case "1":
                alarmName = "振动报警 ";
                break;
            case "2":
                alarmName = "关机  ";
                break;
            case "3":
                alarmName = "电池低 ";
                break;
            case "4":
                alarmName = "sos  ";
                break;
            case "5":
                alarmName = "超速  ";
                break;
            case "6":
                alarmName = "离线 ";
                break;
            case "8":
                alarmName = "超出栅栏 ";
                break;
            case "9":
                alarmName = "移动  ";
                break;
            case "24":
                alarmName = "进入栅栏 ";
                break;
            case "31":
                alarmName = "超出区域 ";
                break;
            case "32":
                alarmName = "拆开 ";
                break;
            case "33":
                alarmName = "灯光 ";
                break;
            case "34":
                alarmName = "使用 ";
                break;
            case "36":
                alarmName = "蓝牙 ";
                break;
            case "40":
                alarmName = "进入危险区域 ";
                break;
            case "41":
                alarmName = "离开危险区域 ";
                break;
            case "42":
                alarmName = "长时间危险区域 ";
                break;
            case "44":
                alarmName = "相撞 ";
                break;
            case "45":
                alarmName = "加速 ";
                break;
            case "46":
                alarmName = "减速 ";
                break;
            case "47":
                alarmName = "翻倒 ";
                break;
            case "48":
                alarmName = "急转弯 ";
                break;
            case "49":
                alarmName = "下拉电阻 ";
                break;
            default:
                break;
        }
        return alarmName;
    }

    /**
     * 获取设置信息
     */
    public Result getSettings(Map<String, String> map) {
        String dev_number = map.get("imei");
        Result result = new Result();
        Map<String, String> sp = new HashMap<>();
        Efence efence = new Efence();
        Area area = new Area();
        OverSpeed overSpeed = new OverSpeed();
        Map<String, Object> res = new HashMap<>();
        AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(dev_number); //根据设备号查询设备
        if (androidMonitorTip == null) {
            result.setMsg("设备未找到");
            result.setSuccess(false);
            result.setErrcode(1);
            result.setData(null);
            return result;
        } else if (androidMonitorTip.getFence_id() != null) {  //围栏可能为空
            String fenceid = androidMonitorTip.getFence_id().substring(2, 21);  //
            Fence fence = commonService.getFencebyId(fenceid);//根据设备查到的围栏id查询围栏信息
            efence.setId(fence.getId());//围栏id
            efence.setAlarm_type(androidMonitorTip.getFence_alarm_type().substring(2, 3));
            efence.setPhone_num(androidMonitorTip.getPhone());
            efence.setUser_id(androidMonitorTip.getUser_id());
            efence.setCreate_time(fence.getCreatTime()); //围栏创建时间
            efence.setShape_type(String.valueOf(fence.getFenceType()));
            String shape_param = fence.getFenceData().replace('|', ',');
            efence.setShape_param(shape_param);
            efence.setValidate_flag(androidMonitorTip.getEfence_validate_flag());
            efence.setRemark(androidMonitorTip.getRemark());

            //area部分属性不知道怎么设置 暂时设置为和fence一样的属性
            sp.put("province", androidMonitorTip.getProvince());
            sp.put("city", androidMonitorTip.getCity());
            area.setShape_param(sp);
            area.setId(fence.getId());
            area.setUser_id(androidMonitorTip.getUser_id());
            area.setAlarm_type(androidMonitorTip.getFence_alarm_type().substring(2, 3));
            area.setValidate_flag(androidMonitorTip.getArea_validate_flag());
            area.setCreate_time(fence.getCreatTime());
            area.setPhone_num(androidMonitorTip.getPhone());
            area.setRemark(androidMonitorTip.getRemark());
            overSpeed.setSpeed(androidMonitorTip.getSpeed());
            overSpeed.setOverspeed_flag(androidMonitorTip.getOverspeed_flag());
        } else {
            efence = null;
            area = null;
            overSpeed.setSpeed(androidMonitorTip.getSpeed());
            overSpeed.setOverspeed_flag(androidMonitorTip.getOverspeed_flag());
        }
        res.put("efence", efence);
        res.put("area", area);
        res.put("overspeed", overSpeed);
        result.setErrcode(0);
        result.setMsg("ok");
        result.setSuccess(true);
        result.setData(res);
        return result;
    }

    /**
     * 设置超速警告
     */
    public DevinfoVo SetOverSpeedAlarm(Map<String, String> map) {
        String dev_num = map.get("imei");
        String flag = map.get("overspeed_flag");
        String speed = map.get("speed");
        DevinfoVo devinfoVo = new DevinfoVo();
        Map<String, String> info = new HashMap<>();
        List<Map<String, String>> res = new ArrayList<>();
        AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(dev_num); //查询设备
        if (androidMonitorTip != null) {
            androidMonitorTip.setOverspeed_flag(flag); //更新数据
            androidMonitorTip.setSpeed(speed);
            JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip)); //返回数据库

            Random random = new Random();//超速id 这里返回随机数
            int i = random.nextInt(9999999 - 1000000 + 1) + 1000000;
            String id = String.valueOf(i);
            info.put("id", id);
            info.put("isCarNoExist", "false");
            res.add(info);
            devinfoVo.setErrcode(0);
            devinfoVo.setMsg("ok");
            devinfoVo.setSuccess(true);
            devinfoVo.setData(res);
        } else {
            Random random = new Random();//超速id 这里返回随机数
            int i = random.nextInt(9999999 - 1000000 + 1) + 1000000;
            String id = String.valueOf(i);
            info.put("id", id);
            info.put("isCarNoExist", "true");
            res.add(info);
            devinfoVo.setErrcode(1);
            devinfoVo.setMsg("设备未找到");
            devinfoVo.setSuccess(false);
            devinfoVo.setData(res);
        }
        return devinfoVo;
    }

    public Result getCities(Map<String, String> map) {
        String id = map.get("province");
        ProvinceAndCity provinceAndCity = new ProvinceAndCity();
        Map<String, List> res = new HashMap<>();
        List<Map<String, String>> list = provinceAndCity.getcities(id);
        res.put("result", list);
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg("ok");
        result.setErrcode(0);
        result.setData(res);
        return result;
    }

    public Result set_area_fence(Map<String, String> map) {
        String imei = map.get("imei");
        String flag = map.get("validate_flag");
        String code = map.get("citycode");
        Map<String, String> res = new HashMap<>();
        Result result = new Result();
        AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(imei);
        if (androidMonitorTip != null) {
            androidMonitorTip.setArea_validate_flag(flag); //更新数据
            androidMonitorTip.setCitycode(code);
            String id = androidMonitorTip.getAliase();
            JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));// 再存入数据库
            res.put("id", id);
            result.setSuccess(true);
            result.setMsg("ok");
            result.setErrcode(0);
            result.setData(res);
        } else {
            result.setData(null);
            result.setErrcode(1);
            result.setSuccess(false);
            result.setMsg("设备未找到");
        }
        return result;
    }

    public Result closeOutAlarm(Map<String, String> map) {        //关闭出省或市提示 将area设置为0
        String imei = map.get("imei");
        String flag = map.get("validate_flag");
        Map<String, String> res = new HashMap<>();
        Result result = new Result();
        AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(imei);//查询
        if (androidMonitorTip != null) {
            String id = androidMonitorTip.getAliase();
            androidMonitorTip.setArea_validate_alarm(flag); //更新数据
            JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//存入
            res.put("id", id);
            result.setSuccess(true);
            result.setMsg("ok");
            result.setErrcode(0);
            result.setData(res);
        } else {
            result.setData(null);
            result.setErrcode(1);
            result.setSuccess(false);
            result.setMsg("设备未找到");
        }
        return result;
    }

    //开启关闭电子围栏
    public Result switchEfence(Map<String, String> map) {
        String flag = map.get("validate_flag");
        String eid = map.get("id");//电子围栏id
        String dev_num = map.get("imei");
        Result result = new Result();
        Map<String, String> res = new HashMap<>();
        AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(dev_num);//查询
        if (androidMonitorTip != null) {
            if (eid.equals(androidMonitorTip.getFence_id())) {
                String id = androidMonitorTip.getAliase();
                androidMonitorTip.setEfence_validate_flag(flag); //更新
                JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));
                res.put("id", id);
                result.setSuccess(true);
                result.setMsg("ok");
                result.setErrcode(0);
                result.setData(res);
            } else {
                res.put("id", null);
                result.setSuccess(false);
                result.setMsg("围栏id错误");
                result.setErrcode(1);
                result.setData(res);
            }
        } else {
            result.setMsg("设备未找到");
            result.setErrcode(1);
            res.put("id", null);
            result.setSuccess(false);
            result.setData(res);
        }
        return result;
    }

    public Result setEfence(Map<String, String> map) {
        String flag = map.get("validate_flag");
        String shape_type = map.get("share_type");
        String shape_param = map.get("share_param");
        String t = map.get("t");
        String ticket = map.get("ticket");
        String userId = map.get("account");
        String fenceId = KeyUtil.genUniqueKey();
        Map<String, String> res = new HashMap<>();
        List<String> stringList = getFenceNameByFenceType(shape_type, shape_param);
        String fenceTypeText = stringList.get(0);
        shape_param = stringList.get(1);
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        JSONArray fenceArray = JSONArray.parseArray(userOrg.getFence_ids());
        fenceArray.add(fenceId);
        userOrg.setFence_ids(JSON.toJSONString(fenceArray));
        JedisUtil6800.setString9(userId, JSON.toJSONString(userOrg));
        Fence fence = new Fence();
        fence.setUserId(userId);
        fence.setId(fenceId);
        fence.setFenceTypeText(fenceTypeText);
        fence.setEnabled(false);
        fence.setFenceType(Integer.parseInt(shape_type));
        // fence.setFenceName(FenceName);
        fence.setFenceData(shape_param);
        fence.setCreatTime(TimeUtils.getGlnz());
        JedisUtil6800.setString8(fenceId, JSONObject.toJSONString(fence));
        res.put("id", fenceId); //返回围栏id
        Result result = new Result();//设置电子围栏
        result.setSuccess(true);
        result.setMsg("ok");
        result.setErrcode(0);
        result.setData(res);
        return result;
    }

    public Result setMessage(Map<String, String> map) {
        Result result = new Result();//设置短信
        result.setSuccess(true);
        result.setMsg("ok");
        result.setErrcode(0);
        result.setData(null);
        return result;
    }

    public Result getProvinces(Map<String, String> map) {
        ProvinceAndCity provinceAndCity = new ProvinceAndCity();
        Map<String, List> res = new HashMap<>();
        List<Map<String, String>> list = provinceAndCity.getProvince();
        res.put("result", list);
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg("ok");
        result.setErrcode(0);
        result.setData(res);
        return result;
    }

    public Result editinfo(Map<String, String> map) {
        String type = map.get("type");
        String dev_number = map.get("imei");
        String content = map.get("content");
        Result result = new Result();
        AndroidMonitorTip androidMonitorTip = commonService.getAndroidMonitorTipByDevId(dev_number);
        if (androidMonitorTip != null) {
            if (type.equals("0")) {
                androidMonitorTip.setDev_type(content);
                JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//存入
            } else if (type.equals("1")) {
                androidMonitorTip.setDev_name(content);
                JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//存入;
            } else if (type.equals("2")) {
                androidMonitorTip.setNumber(content);
                JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//存入
            } else if (type.equals("3")) {
                androidMonitorTip.setRemark(content);
                JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//存入
            } else if (type.equals("4")) {
                androidMonitorTip.setPhone(content);
                JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//存入
            } else if (type.equals("5")) {
                androidMonitorTip.setOwner(content);
                JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//存入
            } else if (type.equals("6")) {
                androidMonitorTip.setTel(content);
                JedisUtil6800.setString4(androidMonitorTip.getDev_number(), JSON.toJSONString(androidMonitorTip));//存入
            }
            result.setMsg("ok");
            result.setSuccess(true);
            result.setErrcode(0);
        } else {
            result.setMsg("设备未找到");
            result.setSuccess(false);
            result.setErrcode(1);
        }

        return result;
    }


}
