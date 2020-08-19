package com.car.controller;

import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.baiduMapRoute.HttpRoute;
import com.car.domain.*;
import com.car.domain.main.Refresh;
import com.car.dto.DeviceDTO;
import com.car.dto.DeviceDTOs;
import com.car.service.ExportService;
import com.car.service.MonitorService;
import com.car.service.UserService;
import com.car.utils.KeyUtil;
import com.car.utils.RootVoUtils;
import com.car.utils.TimeUtils;
import com.car.vo.RootVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Blob;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/20  23:53
 * @Description
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
@Api(value = "监控中心操作", tags = "监控中心")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private ExportService exportService;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(MonitorController.class);

    /**
     * 设备组      后台搜索关键字
     * C18                                   OrgId: "168c3200-a1dd-473c-9d30-a8230120604d"
     * C181106                           OrgId: "70e67992-ddc1-4b81-a9be-a82301207e3c"
     * WRT20171109000035      OrgId: "1a0c434c-4386-414d-ae29-a82700fac16b"
     * WRT2018011300006         OrgId: "8cf9857e-3f95-43e5-b7b4-a86701512cce"
     * WRT201801240147           OrgId: "8fb5d276-0ce6-47ba-82b7-a87700be4053"
     * C18-JT 20台                        OrgId: "ec411941-3faf-4844-a5f1-a94700bdf677"
     * WRT2018121200179          OrgId: "004074e3-a552-454e-8868-a9bc01208615"
     * C180731                               OrgId: "41f6a744-2a59-479b-b1dd-aa9b011ec7c2"
     * C180929                               OrgId: "b738ab5d-9197-4e74-be8a-a7fd00a54e72"
     * C181119                               OrgId: "9f3d2691-e76e-48b6-a45a-ab0a011ffacf"
     * WRT201805100179               OrgId: "4b0278b8-933e-41a8-a3cd-a8dc013c23dd"
     * S17050515                             OrgId: "e9ddf59f-d410-4881-958e-a8e100a96e6a"
     * C181018                               OrgId: "bb765b00-4d60-4de0-aebd-aaea00b7f809"
     */
    @PostMapping("/monitor/ShowMonitorDeviceList")
    public RootVo ShowMonitorDeviceList(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        map = userService.getUserIdByRequest(request, map);
        System.out.println("ShowMonitorDeviceList -> userId : " + map.get("userId"));
        DeviceDTOs deviceDTOs = monitorService.getGroupIdOrCataIdDeviceByMap(map);
        if (deviceDTOs == null) {
            return RootVoUtils.error(404, "查询无果");
//            return RootVoUtils.isNull();
        }
        return RootVoUtils.success(deviceDTOs);
    }

    @ApiOperation(value = "监控中心按钮", tags = "查看设备", notes = "查看设备笔记")
    @PostMapping("/monitor/ShowMonitorTips")
    public RootVo ShowMonitorTips(@RequestBody Map<String, Object> map) {
        System.out.println("monitor/ShowMonitorTips : " + map.toString());
        AndroidMonitorTip monitorTips = monitorService.getMonitorByNum(map);
        if (monitorTips != null) {
            DeviceDTO deviceDTO = new DeviceDTO();
            BeanUtils.copyProperties(monitorTips, deviceDTO);
            deviceDTO.setStatus("NotDisassembly, Saving, ACCOFF, OilON, EleON, EleConnected, Stopping");

            return RootVoUtils.success(deviceDTO);
        } else {
            return RootVoUtils.success(new DeviceDTO());
        }
    }


    /**
     * DeviceNumber: "181219310018261"
     */
    @PostMapping("/monitor/ShowCmdListByDevice")
    public RootVo ShowCmdListByDevice() {
        System.out.println("/monitor/ShowCmdListByDevice ");
        return RootVoUtils.success(MonitorService.ShowCmdList());
    }


    /**
     * /monitor/Send {"cmdInfos":["484459121697141"],"sendbody":{"CmdKey":"BP07","DeviceNumber":"484459121697141","Minutes":"30"}}
     */
    @PostMapping("/monitor/Send")
    public RootVo Send(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        String sessionId = request.getRequestedSessionId();
        String userId = UserService.sessionConutsMap.get(sessionId);
        jsonObject.put("userId", userId);
        boolean result = monitorService.sendCtrlByDeviceNum(jsonObject);
        return RootVoUtils.success(result);
    }

//    @PostMapping("/monitor/Sends")
//    public RootVo Sends(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
//        System.out.println("/monitor/Sends "+jsonObject);
//        return RootVoUtils.success(true);
//    }

    /**
     * limit: 15
     * start: 0
     * page: 1
     * DeviceNumber: "181219310018261"
     * /monitor/ShowCmdRecord
     */
    @PostMapping("/monitor/ShowCmdRecord")
    public RootVo ShowCmdRecord(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        CmdRecords cmdRecords = monitorService.getCmdRecordsByMap(map);
//        if (cmdRecords.getData() == null) {
//            return RootVoUtils.error(404, "查询无果");
//        }
        return RootVoUtils.success(cmdRecords);
    }

    /**
     * LastTime: null
     * LastTime: "2020-01-18T15:08:55.0069359+08:00"
     */
    @PostMapping("/monitor/RoundAlarm")
    public RootVo RoundAlarm(HttpServletRequest request) {
        logger.info("monitor/RoundAlarm sessionId :{}" + request.getRequestedSessionId());
        RoundAlarm roundAlarm = new RoundAlarm();
        roundAlarm.setAlarms(null);
        roundAlarm.setTotal(0);
        roundAlarm.setLastTime(TimeUtils.getGlnz());
        return RootVoUtils.success(roundAlarm);
    }

    /**
     * http://www.321gps.com/api/monitor/FetchAddress?lngLat=null
     */
    @GetMapping("/monitor/FetchAddress")
    public RootVo FetchAddress(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String lngLat = (String) map.get("lngLat");
        String lng = lngLat.split(",")[0];
        String lat = lngLat.split(",")[1];
        System.out.println("FetchAddress : lngLat " + lngLat);
        List<String> list = HttpRoute.getAddressByLocation(lat, lng);
        System.out.println("FetchAddress : lngLat " + list);
        return RootVoUtils.success(list.get(0));
    }


    /**
     * @return com.car.platform.VO.RootVo
     * @author mmy
     * @date 2020/2/1  21:18
     * @Description 两个报警按钮
     * limit: 15
     * start: 0
     * page: 1
     * DeviceNumber: "181219310018261"
     */
    @PostMapping("/monitor/ShowAlarm")
    private RootVo ShowAlarm(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("/monitor/ShowAlarm " + map.toString());
        map = userService.getUserIdByRequest(request, map);
        Alarms alarms = monitorService.getAlarmsById(map);
        if (alarms != null) {
            return RootVoUtils.success(alarms);
        } else {
            return RootVoUtils.isNull();
        }
    }


    /**
     * LastTime: null
     * DeviceNumbers: ["181219400018891"]
     * 0: "181219400018891"
     * MapType: "0"
     */
    @PostMapping("/monitor/Refresh")
    public RootVo Refresh(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        Refresh refresh = new Refresh();
        System.out.println("/monitor/Refresh "+jsonObject);
        refresh.setLastTime(TimeUtils.getGlnz());
        return RootVoUtils.success(refresh);
    }


    /**
     * OK
     */
    @GetMapping("/Monitor/GetStat")
    public RootVo GetStat(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map = userService.getUserIdByRequest(request, map);
        String userId = (String) map.get("userId");
        StatInfo statInfo = new StatInfo();
        statInfo = userService.getAndroidMonitorTipsStatInfoByUserId(userId);
        return RootVoUtils.success(statInfo);
    }


    @PostMapping("/monitor/ShowMonitorTipLocation")
    public RootVo ShowMonitorTipsLocation(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        String deviceNum = (String) map.get("DeviceNumber");
        DeviceDTO deviceDTO = monitorService.getMontiorById(deviceNum);
        return RootVoUtils.success(deviceDTO);
    }

    /**
     * api/monitor/ShowHistory  稍后
     * 历史数据
     */
    @PostMapping("/monitor/ShowHistory")
    public RootVo ShowHistory(@RequestBody Map<String, Object> map) throws ParseException {
        System.out.println("/monitor/ShowHistory : " + map.toString());
        Historys historys = monitorService.getHistorysByNumAndTime(map);
        if (historys.getData() == null) {
            return RootVoUtils.error(404, "查询无果");
        }
        return RootVoUtils.success(historys);
    }


    /**
     * 获得下载key
     */
    @PostMapping("/monitor/ExportHistory")
    public RootVo exportHistory(@RequestBody JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = KeyUtil.genUniqueKey();
        ExportService.map.put(key, jsonObject);
        return RootVoUtils.success(key);
    }

    /**
     * 最终下载链接
     */
    @GetMapping("/downloadExcelT")
    private void downloadExcel(@RequestParam String key, HttpServletResponse response, HttpServletRequest request) throws IOException, ParseException {
        System.out.println(ExportService.map.get(key));
        JSONObject jsonObject = ExportService.map.get(key);
        jsonObject.put("key", key);
        exportService.downloadExcelThree(response, request, jsonObject);
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/5  22:32
     * @Description Monitor.js   3206
     */
    @PostMapping("/Monitor/UpdateAttention")
    public RootVo UpdateAttention(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        boolean result = monitorService.UpdateAttentionByNum(map);
        return RootVoUtils.success(result);
    }


}
