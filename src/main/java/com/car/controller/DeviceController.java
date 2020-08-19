package com.car.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.domain.DeviceDetail;
import com.car.domain.Fence;
import com.car.domain.Model;
import com.car.domain.Online;
import com.car.domain.main.DailyAlarm;
import com.car.domain.main.Distribution;
import com.car.domain.main.IncrementModel;
import com.car.mysql.impl.Terminalmpl;
import com.car.service.DeviceService;
import com.car.service.UserService;
import com.car.utils.ReadJSONFile;
import com.car.utils.RootVoUtils;
import com.car.utils.TimeUtils;
import com.car.vo.RootVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/20  23:00
 * @Description
 */
@RequestMapping("/api")
@RestController
@CrossOrigin
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    Terminalmpl terminalmpl;

    @Autowired
    UserService userService;

    //报警统计  前5天的就中
    //   1    2020-03-20T15:09:42  今天
    @GetMapping("/device/GetDailyAlarm")
    public RootVo GetDailyAlarm(HttpServletRequest request) {
        List<DailyAlarm> alarmList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map = userService.getUserIdByRequest(request, map);
        alarmList = deviceService.GetDailyAlarm(map);
        return RootVoUtils.success(alarmList);
    }

    //设备 增量统计  暂且这几个月
    @GetMapping("/device/GetIncrementModel")
    public RootVo GetIncrementModel(HttpServletRequest request) {
        List<IncrementModel> distributionList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map = userService.getUserIdByRequest(request, map);
        distributionList = deviceService.GetDevIncrementModel(map);
        return RootVoUtils.success(distributionList);
    }

    //设备到期统计
    @GetMapping("/device/GetExpireDate")
    public RootVo GetExpireDate(HttpServletRequest request) {
        List<IncrementModel> distributionList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map = userService.getUserIdByRequest(request, map);
        distributionList = deviceService.GetExpireDate(map);
        return RootVoUtils.success(distributionList);
    }


    /*
        "11": "beijing",
        "12": "tianjin",
        "13": "hebei",
        "14": "shanxi",
        "15": "neimongol",
        "16": "tangshan",
        "21": "liaoning",
        "22": "jilin",
        "23": "heilongjiang",
        "31": "shanghai",
        "32": "jiangsu",
        "33": "zhejiang",
        "34": "anhui",
        "35": "fujian",
        "36": "jiangxi",
        "37": "shandong",
        "41": "henan",
        "42": "hubei",
        "43": "hunan",
        "44": "guangdong",
        "45": "guangxi",
        "46": "hainan",
        "50": "chongqing",
        "51": "sichuan",
        "52": "guizhou",
        "53": "yunnan",
        "54": "xizang",
        "61": "shaanxi",
        "62": "gansu",
        "63": "qinghai",
        "64": "ningxia",
        "65": "xinjiang",
        "71": "taiwan",
        "81": "hongkong",
        "82": "macau"
*/
    //分布统计(top10)
    @GetMapping("/device/GetDistribution")
    public RootVo GetDistribution(Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> map1 = new HashMap<>();
        map = userService.getUserIdByRequest(request, map);
        System.out.println("device/GetDistribution " + map.toString());
        List<Distribution> distributionList = deviceService.getDistributionByLocations(map);
        return RootVoUtils.success(distributionList);
    }

    @PostMapping("/Device/ShowModelList")
    public RootVo ShowModelList() throws IOException {
        // 1-44
        ClassPathResource classPathResource = new ClassPathResource("static/json/ModelList.json");
        InputStream inputStream = classPathResource.getInputStream();
        List<Model> models = JSONObject.parseArray(ReadJSONFile.readFile(inputStream), Model.class);
        //TODO 展示设备类型种类 可自定义
        return RootVoUtils.success(models);
    }

    /**
     * deviceNumber: "181219310018261"
     */
    @PostMapping("/Device/ShowDeviceDetails")
    private RootVo ShowDeviceDetails(@RequestBody Map<String, String> map) {
        String deviceNumber = map.get("deviceNumber");
        DeviceDetail deviceDetail = deviceService.getDevicelById(deviceNumber);
        //TODO 展示设备详情
        return RootVoUtils.success(deviceDetail);
    }


    /*
    /api/Device/ShowFenceDetails
     */
    @PostMapping("/Device/ShowFenceDetails")
    private RootVo ShowFenceDetails(@RequestBody Map<String, String> map) {
        System.out.println(map.toString());
        Fence fence = deviceService.getFenceDetailsById(map);
        return RootVoUtils.success(fence);
    }


    /*
    DeviceNumber: "181219310018261"
    Start: "2020-01-05"
    End: "2020-02-05"
    */
    @PostMapping("/Device/OnlineDetail")
    public RootVo OnlineDetail(@RequestBody Map<String, String> map) {
        List<Online> onlineList = deviceService.getOnlineDetailListByNum(map);
        //TODO 需要一个在线数组
        return RootVoUtils.success(onlineList);
    }

    /**
     * @return
     * @author mmy
     * @date 2020/2/5  22:32
     * @Description Monitor.js   3127行
     */
    @PostMapping("/Device/UpdateDevicePosition")
    public RootVo UpdateDevicePosition() throws IOException {
        System.out.println("Device/UpdateDevicePosition");
        //todo
        return RootVoUtils.success(true);
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/5  22:32
     * @Description Monitor.js   3158
     */
    @PostMapping("/Device/ShowDevicePosition")
    public RootVo ShowDevicePosition() {
        System.out.println("Device/UpdateDevicePosition");
        return RootVoUtils.success(true);
    }

    /**
     * 终端管理
     * Id: "01a6eb66-a045-4869-92d0-ab4500f4ed9a"
     * ModeId: "所有型号"
     * ModelType: "设备类型"
     * OrderBys: [{Key: "a.LastUpdateTime", Value: "desc"}]
     * 0: {Key: "a.LastUpdateTime", Value: "desc"}
     * Key: "a.LastUpdateTime"
     * Value: "desc"
     * StartTime-->EndTime: ""
     * StartTime: "2020-01-20 14:01:08"
     * EndTime: "2020-02-18 14:01:08 23:59:59"
     * WithChildren: "true"
     * limit: 15
     * page: 1
     * start: 0
     */
    @PostMapping("/Device/Search")
    public RootVo Search(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("Device/UpdateDevicePosition" + map);
        map = userService.getUserIdByRequest(request, map);
        map.put("OrgId", map.get("Id"));
        return RootVoUtils.success(terminalmpl.getTerminalByMap(map));
    }

    /**
     * 终端修改
     * api/Device/Update
     * DevicePassword: "123456"
     * ICCID: "89860440573085972333"
     * Id: "3"
     * MFDate: "2020-03-21"
     * ModeId: "4"
     * Name: "162203372877781"
     * OrgnizationId: "1583500312479911110"
     * Remark: "1111"
     * Simcard: "460014206717022"
     */
    @PostMapping("/Device/Update")
    public RootVo Update(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("Device/Update" + map);
        return RootVoUtils.success(terminalmpl.edit(map));
    }

    /**
     * UpdateOrgnizationapi/
     * Device/UpdateOrgnization
     * Id: "1583500780272809574"
     * 可能是目录 /用户/组
     * Ids: ["7"]
     * 0: "7"
     */
    @PostMapping("/Device/UpdateOrgnization")
    public RootVo UpdateOrgnization(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("Device/Update" + map);
        return RootVoUtils.success(terminalmpl.edit(map));
    }

    /**
     * AssociateVehicle
     * 终端绑定车辆
     * 车辆id
     * Id: "4764"
     * 终端设备Ids
     * Ids: ["7", "8"]
     * 0: "7"
     * 1: "8"
     */
    @PostMapping("/Device/AssociateVehicle")
    public RootVo AssociateVehicle(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("Device/AssociateVehicle" + map);
        deviceService.AssociateVehicle(map);
        return RootVoUtils.success(true);
    }

    /**
     * 取消终端绑定
     * api/Device/DisassociateVehicle
     */
    @PostMapping("/Device/DisassociateVehicle")
    public RootVo DisassociateVehicle(@RequestBody JSONArray jsonArray, HttpServletRequest request) {
        System.out.println("Device/DisassociateVehicle" + jsonArray);
        deviceService.DisassociateVehicle(jsonArray);
        return RootVoUtils.success(true);
    }

    /**
     * 删除终端绑定
     * api/Device/Remove
     */
    @PostMapping("/Device/Remove")
    public RootVo Remove(@RequestBody JSONArray jsonArray, HttpServletRequest request) {
        System.out.println("Device/Remove" + jsonArray);
        terminalmpl.Remove(jsonArray);
        return RootVoUtils.success(true);
    }

    /**
     * 增加设备时 是否存在此设备
     * api/Device/CheckDeviceNumberOnly
     */
    @PostMapping("/Device/CheckDeviceNumberOnly")
    public RootVo CheckDeviceNumberOnly(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("Device/CheckDeviceNumberOnly" + map);
        return RootVoUtils.success(terminalmpl.CheckDeviceNumberOnly(map)
        );
    }

    /**
     * 增加设备
     * api/Device/Add
     */
    @PostMapping("/Device/Add")
    public RootVo Add(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("Device/CheckDeviceNumberOnly" + map);
        userService.getUserIdByRequest(request, map);
        terminalmpl.insertTerminal(map);
        return RootVoUtils.success(true);
    }

    /*
         ClassPathResource classPathResource = new ClassPathResource("static/json/IsOnline.json");
        InputStream inputStream = classPathResource.getInputStream();
        List<Online> onlineList = JSON.parseArray(ReadJSONFile.readFile(inputStream), Online.class);
     */

}
