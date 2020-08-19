package com.car.controller;

import com.alibaba.fastjson.JSONArray;
import com.car.domain.Car;
import com.car.mysql.impl.CarManagementImpl;
import com.car.service.UserService;
import com.car.service.VehicleService;
import com.car.utils.RootVoUtils;
import com.car.vo.RootVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/7  22:58
 * @Description
 */
@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private CarManagementImpl carManagement;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    UserService userService;

    /**
     * 增加车辆
     * api/Vehicle/Add
     */
    @PostMapping("/Vehicle/Add")
    public RootVo Add(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("Vehicle/Search: " + map);
        map = userService.getUserIdByRequest(request, map);
        carManagement.insertCar(map);
        return RootVoUtils.success(true);
    }

    /**
     * 更新车辆
     * api/Vehicle/Update
     */
    @PostMapping("/Vehicle/Update")
    public RootVo Update(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("Vehicle/Search: " + map);
        carManagement.update(map);
        return RootVoUtils.success(true);
    }


    /**
     * 车辆管理
     * Key: "14566"
     * OrgId: "01a6eb66-a045-4869-92d0-ab4500f4ed9a"
     * WithChildren: "true"
     * 默认
     * limit: 15
     * page: 1
     * start: 0
     */
    @PostMapping("/Vehicle/Search")
    public RootVo Search(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("Vehicle/Search: " + map.toString());
        map = userService.getUserIdByRequest(request, map);
//        map.put("OrgId", map.get("userId"));
        return RootVoUtils.success(carManagement.getCarsListFromMap(map));
    }

    /**
     * 删除车辆
     * api/Vehicle/Remove
     * 0: "3"
     */
    @PostMapping("/Vehicle/Remove")
    public RootVo Remove(@RequestBody JSONArray jsonArray, HttpServletRequest request) {
        System.out.println("Vehicle/Remove: " + jsonArray);
        carManagement.Remove(jsonArray);
        return RootVoUtils.success(true);
    }

    /**
     * api/Vehicle/CheckPlateNoOnly
     */
    @PostMapping("/Vehicle/CheckPlateNoOnly")
    public RootVo CheckPlateNoOnly(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("Vehicle/CheckPlateNoOnly: " + map.toString());
        return RootVoUtils.success(vehicleService.CheckPlateNoOnly(map));
    }


}
