package com.car.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.bean.Organization;
import com.car.bean.UserOrg;
import com.car.domain.Children;
import com.car.redis.JedisUtil6800;
import com.car.service.GroupService;
import com.car.service.PermissionService;
import com.car.service.UserService;
import com.car.utils.RootVoUtils;
import com.car.vo.RootVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;


    /**
     * @return com.car.platform.VO.RootVo
     * @author mmy
     * @date 2020/2/1  14:59
     * @Description Request URL: http://60.2.213.22:51662/api/Group/ShowChildren  Request Method: OPTIONS
     * 已更改
     */
    @PostMapping("/Group/ShowChildren")
    public RootVo ShowChildren(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map = userService.getUserIdByRequest(request, map);
        String userId = (String) map.get("userId");
        List<Children> childrenList = groupService.getChildenAll(userId);
        return RootVoUtils.success(childrenList);
    }


    /**
     * 已更改 默认是用户的第一个组
     */
    @PostMapping("/Group/SearchCurrentGroupId")
    private RootVo SearchCurrentGroupId(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map = userService.getUserIdByRequest(request, map);
        String data = groupService.SearchCurrentGroupId(map);
        return RootVoUtils.success(data);
    }


    /**
     * Id: "168c3200-a1dd-473c-9d30-a8230120604d"    已更改
     */
    @PostMapping("/Group/SearchOrgName")
    private RootVo SearchOrgName(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        map = userService.getUserIdByRequest(request, map);
        Organization organization = groupService.getOrganizationNameByOrgId(map);
        return RootVoUtils.success(organization.getOrganization_name());
    }


    /**
     * 组管理
     * Id: "01a6eb66-a045-4869-92d0-ab4500f4ed9a"
     * Key: "ds"
     * WithChildren: "true"
     * limit: 15
     * page: 1
     * start: 0
     */
    @PostMapping("/Group/Search")
    public RootVo UserSearch(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("/ Group/Search " + map);
        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(groupService.Search(map));
    }

    @PostMapping("/Group/Add")
    public RootVo GroupAdd(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("/ Group/Add " + map);
//        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(groupService.Search(map));
    }

    @PostMapping("/Group/Remove")
    public RootVo GroupRemove(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("/ Group/Remove " + map);
//        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(groupService.Search(map));
    }

    @PostMapping("/Group/Update")
    public RootVo GroupUpdate(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        System.out.println("/ Group/Update " + map);
//        map = userService.getUserIdByRequest(request, map);
        return RootVoUtils.success(groupService.Search(map));
    }


}
