package com.car.controller;

import com.alibaba.fastjson.JSONObject;
import com.car.domain.Fence;
import com.car.domain.FenceDevices;
import com.car.domain.Fences;
import com.car.domain.Groups;
import com.car.service.FenceService;
import com.car.service.UserService;
import com.car.setting.UserSetting;
import com.car.utils.RootVoUtils;
import com.car.vo.RootVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/20  23:52
 * @Description
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class Fencecontroller {

    @Autowired
    FenceService fenceService;

    @Autowired
    UserService userService;

    /**
     * limit: 15
     * start: 0
     * page: 1
     * OrgId: "168c3200-a1dd-473c-9d30-a8230120604d"
     * WithChildren: "true"
     * FenceName: "坎坎坷坷"    只有在第一个界面搜索时 才带有此字段
     */
    @PostMapping("/fence/FenceListByOrg")
    private RootVo FenceListByOrg(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        map = userService.getUserIdByRequest(request, map);
        Fences fences = fenceService.FenceListByOrg(map);
        return RootVoUtils.success(fences);
    }


    /**
     * @return
     * @author mmy
     * @date 2020/2/1  23:01
     * @Description 点击 关联设备
     */
    @PostMapping("/fence/DeviceFenceList")
    private RootVo DeviceFenceList(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        map = userService.getUserIdByRequest(request, map);
        FenceDevices fences = fenceService.getDeviceFenceListById(map);
        if (fences == null) {
            return RootVoUtils.error(404, "查询无果");
        }
        return RootVoUtils.success(fences);
    }

    /**
     * limit: 15
     * start: 0
     * page: 1
     * OrgId: "168c3200-a1dd-473c-9d30-a8230120604d"
     * WithChildren: "true"
     * Key: "2"
     * IsBind: "false"
     * FenceId: "9ac1922b-6aa5-4c46-868b-aafc017cc7ff"
     */
    @PostMapping("/fence/GroupFenceList")
    private RootVo GroupFenceList(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        map = userService.getUserIdByRequest(request, map);
        Groups groups = fenceService.getGroupFenceList(map);
        if (groups == null) {
            return RootVoUtils.error(404, "查询无果");
        }
        return RootVoUtils.success(groups);
    }

    /**
     * @return com.car.platform.VO.RootVo
     * @author mmy
     * @date 2020/2/5  22:37
     * @Description Fence.js   185行
     * /fence/BindFence  {"FenceId":"1583500780272918471","FenceAlarmTypeText":"1","Ids":["361054524214541","257425461946757"]}
     */
    @PostMapping("/fence/BindFence")
    private RootVo BindFence(@RequestBody JSONObject object) {
        System.out.println(" /fence/BindFence  " + object);
        boolean result = fenceService.setBindFenceByFenceId(object);
        return RootVoUtils.success(result);
    }


    /**
     * @return com.car.platform.VO.RootVo
     * @author mmy
     * @date 2020/2/5  22:37
     * @Description Fence.js   216
     * /fence/UnBindFence  {"FenceId":"1583500780272918471","Ids":["361054524214541"]}
     */
    @PostMapping("/fence/UnBindFence")
    private RootVo UnBindFence(@RequestBody JSONObject object) {
        System.out.println(" /fence/UnBindFence  " + object);
        boolean result = fenceService.setUnBindFenceByFenceId(object);
        return RootVoUtils.success(result);
    }

    /**
     * @return com.car.platform.VO.RootVo
     * @author mmy
     * @date 2020/2/5  22:37
     * @Description Fence.js   324
     * FenceAlarmTypeText: "1"
     * Ids: ["1583500780272267472"]
     * 0: "1583500780272267472"
     * FenceId: "1583500780272918471"
     */
    @PostMapping("/fence/BindFenceByGroup")
    private RootVo BindFenceByGroup(@RequestBody JSONObject jsonObject) {
        boolean result = fenceService.setBindFenceByGroup(jsonObject);

        return RootVoUtils.success(result);
    }

    /**
     * @return com.car.platform.VO.RootVo
     * @author mmy
     * @date 2020/2/5  22:37
     * @Description Fence.js   355
     * Ids: ["1583500780278930392"]
     * 0: "1583500780278930392"
     * FenceId: "1583500780278798428"
     */
    @PostMapping("/fence/UnBindFenceByGroup")
    private RootVo UnBindFenceByGroup(@RequestBody JSONObject jsonObject) {
        boolean result = fenceService.setUnBindFenceByGroup(jsonObject);
        return RootVoUtils.success(result);
    }

    /**
     * @return com.car.platform.VO.RootVo
     * @author mmy
     * @date 2020/2/5  22:37
     * @Description Fence.js   88
     * FenceData: "118.143883|39.765006,2174"
     * FenceType: "1"
     * FenceName: "测试"
     */
    @PostMapping("/fence/Add")
    private RootVo Add(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        map = userService.getUserIdByRequest(request, map);
        System.out.println("Fence/Add " + map.toString());
        boolean result = fenceService.getAddFenceResultByAdd(map);
        return RootVoUtils.success(result);
    }


    /**
     * FenceName: "兴文县城2"
     * Id: "10"
     */
    @PostMapping("/fence/Update")
    private RootVo Update(@RequestBody Map<String, Object> map) {
        System.out.println("Fence/Update " + map.toString());
        boolean result = fenceService.getUpdateFenceResultByUpdate(map);
        return RootVoUtils.success(result);
    }


    /**
     * 0: "10"
     * 1: "11"
     * 2: "12"
     */
    @PostMapping("/fence/Remove")
    private RootVo Remove(@RequestBody List<String> list, HttpServletRequest request) {
        System.out.println("Fence/Remove " + list.toString());
        Map<String, Object> map = new HashMap();
        map = userService.getUserIdByRequest(request, map);
        boolean result = fenceService.getRemoveFenceResultByRemove(map, list);
        return RootVoUtils.success(result);
    }

    /**
     * /api/fence/FencesByGroupId
     * fence/FencesByGroupId
     * 可能是目录/组/用户Id
     * Id: "1583500312155733731"
     * 查找组管理中相关的围栏
     */
    @PostMapping("/fence/FencesByGroupId")
    public RootVo FencesByGroupId(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        userService.getUserIdByRequest(request, map);
        List<Fence> fenceList = fenceService.FencesByGroupId(map);
        if (fenceList == null) {
            RootVoUtils.error(404, "没有关联围栏");
        }
        return RootVoUtils.success(fenceList);
    }


    /**
     * 查看终端设备相关围栏
     * FencesByTerminalId
     * http://127.0.0.1:51665/api/fence/FencesByTerminalId
     * 设备id
     * Id: "162203372877781"
     */
    @PostMapping("/fence/FencesByTerminalId")
    public RootVo FencesByTerminalId(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        userService.getUserIdByRequest(request, map);
        List<Fence> fenceList = fenceService.FencesByTerminalId(map);
        if (fenceList == null) {
            RootVoUtils.success(new ArrayList<>());
        }
        return RootVoUtils.success(fenceList);
    }

    /**
     * 风险点
     * WithChildren: false
     * limit: 100
     * start: 0
     * page: 1
     * OrgId: "1583500312155511924"
     */
    @PostMapping("/DangerPoint/FenceListByOrg")
    public RootVo getDangerPointFenceListByOrg(@RequestBody Map<String, String> map) {
        return RootVoUtils.isNull();
    }


}
