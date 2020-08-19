package com.car.controller;

import com.car.service.PermissionService;
import com.car.utils.RootVoUtils;
import com.car.vo.RootVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/7  23:16
 * @Description
 */
@RequestMapping("/api")
@RestController
public class PermissionController {

    @Autowired
    PermissionService permissionService;


    /**
     * 当前用户绑定的角色
     */
    @PostMapping("/Permission/ShowRoleOrgList")
    public RootVo ShowRoleOrgList(HttpServletRequest request) {
        System.out.println("/Permission/ShowRoleOrgList");
        HashMap<String, String> map = new HashMap<>();
        return RootVoUtils.success(permissionService.ShowRoleOrgList(map));
    }

    /**
     * 全部角色
     */
    @PostMapping("/Permission/ShowRoles")
    public RootVo ShowRoles(@RequestBody Map<String, String> map, HttpServletRequest request) {
        System.out.println("/Permission/ShowRoleOrgList" + map);
        return RootVoUtils.success(permissionService.ShowRole(map));
    }


}
