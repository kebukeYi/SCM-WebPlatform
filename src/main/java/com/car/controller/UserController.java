package com.car.controller;

import com.car.domain.main.IncrementModel;
import com.car.service.UserService;
import com.car.utils.RootVoUtils;
import com.car.vo.RootVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户增量统计
     */
    @GetMapping("/user/GetIncrementModel")
    public RootVo GetIncrementModel(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map = userService.getUserIdByRequest(request, map);
        List<IncrementModel> distributionList = new ArrayList<>();
        distributionList = userService.GetUserIncrementModel(map);
        return RootVoUtils.success(distributionList);
    }

    /**
     * @return com.car.platform.VO.RootVo
     * @author mmy
     * @date 2020/2/1  21:28
     * @Description EnabledAlarmAudio: true
     */
    @PostMapping("/User/SaveAlarmAudio")
    public RootVo SaveAlarmAudio() {
        boolean result = true;
        return RootVoUtils.success(result);
    }

    /**
     * /api/User/SaveMapView
     * MapView: "118.179932,39.639538,11"
     */
    @PostMapping("/User/SaveMapView")
    public RootVo SaveMapView(@RequestBody Map<String, String> map) {
        return RootVoUtils.success(true);
    }


    /**
     * @return com.car.platform.VO.RootVo
     * @author mmy
     * @Description Monitor.js   2915行
     */
    @PostMapping("/Vehicle/NewCheckPlateNoOnly")
    private RootVo NewCheckPlateNoOnly() {
        return RootVoUtils.success(null);
    }

    @PostMapping("/user/UpdatePassword")
    public RootVo UpdatePassword(@RequestBody Map<String, String> map) {
        return RootVoUtils.success(null);
    }


    /**
     * 用户管理
     * Enabled: "true"  ""
     * EndTime: "2020-02-18 14:01:08 23:59:59"
     * 当前组织的所属组 Id: "01a6eb66-a045-4869-92d0-ab4500f4ed9a"
     * Key: "dd"    ""
     * StartTime: "2020-01-20 14:01:08"
     * WithChildren: "true"
     * 默认
     * limit: 15
     * page: 1
     * start: 0
     */
    @PostMapping("/User/Search")
    public RootVo UserSearch(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("User/Search " + map);
        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(userService.getUserOrgsByUserId(map));
    }

    /**
   api/User/CheckUserNameOnly
     */
    @PostMapping("/User/CheckUserNameOnly")
    public RootVo CheckUserNameOnly(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("User/CheckUserNameOnly " + map);
        return RootVoUtils.success(userService.CheckUserNameOnly(map));
    }

    @PostMapping("/User/Add")
    public RootVo UserAdd(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("User/Add " + map);
//        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(true);
    }

    @PostMapping("/User/Remove")
    public RootVo UserRemove(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("User/Remove" + map);
//        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(true);
    }


}
