package com.car.controller;

import com.alibaba.fastjson.JSONObject;
import com.car.domain.DeviceLocationInfos;
import com.car.domain.StopDeviceDetails;
import com.car.domain.main.WiredOffline;
import com.car.mysql.impl.*;
import com.car.service.ReportService;
import com.car.service.UserService;
import com.car.utils.RootVoUtils;
import com.car.utils.TimeUtils;
import com.car.vo.RootVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/20  23:53
 * @Description
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private AlarmReportImpl alarmReport;

    @Autowired
    private OfflineReportImpl offlineReport;

    @Autowired
    private OnlineReportImpl onlineReport;

    @Autowired
    private StopReportImpl stopReport;

    @Autowired
    private MileageReportImpl mileageReport;

    @Autowired
    private ReportService reportService;

    @Autowired
    UserService userService;

    static Random random = new Random();


    //有线离线
    @PostMapping("/Report/ShowWiredOffline")
    public RootVo ShowWiredOffline(HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map = userService.getUserIdByRequest(request, map);
        WiredOffline wiredOffline = new WiredOffline();
        wiredOffline = reportService.getShowWiredOffline(map);
        return RootVoUtils.success(wiredOffline);
    }

    //无线离线
    @PostMapping("/Report/ShowWirelessOffline")
    public RootVo ShowWirelessOffline(HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map = userService.getUserIdByRequest(request, map);
        WiredOffline wiredOffline = new WiredOffline();
        wiredOffline = reportService.getShowWirelessOffline(map);
        return RootVoUtils.success(wiredOffline);
    }

    //无线上线
    @PostMapping("/Report/ShowWirelessOnline")
    public RootVo ShowWirelessOnline(HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map = userService.getUserIdByRequest(request, map);
        WiredOffline wiredOffline = new WiredOffline();
//        List<Integer> data = new ArrayList<>();
//        data.add(1);
//        data.add(1);
//        data.add(1);
//        data.add(1);
//        data.add(1);
//        data.add(1);
//        data.add(1);
//        wiredOffline.setData(data);
//
//        int result = 0;
//        for (int num : data) {
//            result += num;
//        }
//        wiredOffline.setTotal(result);
        wiredOffline = reportService.getShowWirelessOnline(map);
        System.out.println(wiredOffline);
        return RootVoUtils.success(wiredOffline);
    }


    /**
     * 5/16 得修改
     * 已更改
     */
    @PostMapping("/Report/ShowDeviceLocationInfo")
    private RootVo ShowDeviceLocationInfo(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("/Report/ShowDeviceLocationInfo : " + map.toString());
        map = userService.getUserIdByRequest(request, map);
        DeviceLocationInfos deviceLocationInfos = reportService.getDeviceLocationInfoByMap(map);
        if (deviceLocationInfos == null) {
            return RootVoUtils.error(404, "查询无果");
        }
        return RootVoUtils.success(deviceLocationInfos);
    }

    /**
    api/Report/Search
    */
    @PostMapping("/Report/Search")
    private RootVo Search(@RequestBody JSONObject jsonObject) {
        return RootVoUtils.success(null);
    }


    /**
     * Monitor.js   3010行
     * limit: 30
     * start: 0
     * page: 1
     * Interval: "2880"   "" 默认搜按最近的停留    "-1" 自定义区间
     * MinInterval: "0"
     * MaxInterval: "4"
     * DeviceNumber: "312409444442954"
     */
    @PostMapping("/report/ShowStopListByDeviceNumber")
    private RootVo ShowStopListByDeviceNumber(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("report/ShowStopListByDeviceNumber  " + map.toString());
        map = userService.getUserIdByRequest(request, map);
        StopDeviceDetails stopDeviceDetails = reportService.ShowStopListByDeviceNumber(map);
        if (stopDeviceDetails == null) {
            return RootVoUtils.error(404, "查询无果");
        }
        return RootVoUtils.success(stopDeviceDetails);
    }


    /**
     * 报警列表
     * Key: "f"
     * AlarmType: ""
     * EndTime: "2020-05-07 20:59"
     * ModelType: "设备类型"
     * StartTime-->EndTime: ""
     * OrgId: "01a6eb66-a045-4869-92d0-ab4500f4ed9a"
     * StartTime: "2020-05-07 00:00"
     * WithChildren: "true"
     * limit: 15
     * page: 1
     * start: 0
     */
    @PostMapping("/report/ShowAlarmList")
    private RootVo ShowAlarmList(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("report/ShowAlarmList : " + map.toString());
        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(alarmReport.getAlarmListFromMap(map));
    }

    /**
     * 离线列表
     * Days: ""
     * Key: "f"
     * MaxDays: "3"
     * MinDays: "1"
     * ModeId: "1"
     * ModelType: "1"
     * OrgId: "01a6eb66-a045-4869-92d0-ab4500f4ed9a"
     * WithChildren: "true"
     * limit: 15
     * page: 1
     * start: 0
     */
    @PostMapping("/report/ShowOfflineList")
    private RootVo ShowOfflineList(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("report/ShowOfflineList : " + map.toString());
        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(offlineReport.getOfflineListFromMap(map));
    }

    /**
     * 在线列表
     * Key: "d"
     * ModeId: "2"
     * ModelType: "1"
     * OrgId: "01a6eb66-a045-4869-92d0-ab4500f4ed9a"
     * WithChildren: "true"
     * limit: 15
     * page: 1
     * start: 0
     */
    @PostMapping("/report/ShowOnlineList")
    private RootVo ShowOnlineList(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("report/ShowOnlineList : " + map.toString());
        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(onlineReport.getOfflineListFromMap(map));
    }

    /**
     * 停留列表
     * 停留时间  Interval: ""
     * 自定义最长  MaxInterval: "4"
     * 最少    MinInterval: "0"
     * ModelType: "设备类型"
     * OrgId: "01a6eb66-a045-4869-92d0-ab4500f4ed9a"
     * StartTime-->EndTime: ""
     * EndTime: "2020-05-06 23:59:59"
     * WithChildren: "true"
     * limit: 15
     * page: 1
     * start: 0
     */
    @PostMapping("/report/ShowStopList")
    private RootVo ShowStopList(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("report/ShowStopList : " + map.toString());
        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(stopReport.getOfflineListFromMap(map));
    }


    /**
     * 里程列表
     * EndTime: "2020-05-07 23:59:59"
     * Key: "ff"
     * ModelType: "0"
     * OrgId: "01a6eb66-a045-4869-92d0-ab4500f4ed9a"
     * StartTime: "2020-05-01"
     * WithChildren: "true"
     * limit: 15
     * page: 1
     * start: 0
     */
    @PostMapping("/report/MileageReportList")
    private RootVo MileageReportList(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("report/MileageReportList : " + map.toString());
        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(mileageReport.getOfflineListFromMap(map));
    }


}
