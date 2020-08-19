package com.car.controller;

import com.alibaba.fastjson.JSON;
import com.car.bean.User;
import com.car.bean.UserOrg;
import com.car.exception.PageNotExistException;
import com.car.redis.JedisUtil6800;
import com.car.service.UserService;
import com.car.vo.RootVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/20  23:53
 * @Description
 */
@Controller
public class JumpController {

    @Autowired
    UserService userService;

    private static Logger logger = LoggerFactory.getLogger(JumpController.class);


    @GetMapping("/test/404")
    public RootVo test404Error() {
        throw new PageNotExistException();
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }


    @PostMapping("/isLogin")
    public String isLogin(User user, HttpSession session, Map<String, Object> map) {
        System.out.println("UserCount : " + user.getCount() + " |  Password : " + user.getPassword() + "  |   UserType : " + user.getUserType());
        String userStr = JedisUtil6800.getString9(user.getCount());
        if (userStr != null && !"".equals(userStr)) {
            UserOrg userOrg = JSON.parseObject(userStr, UserOrg.class);
            if (user.getPassword().equals(userOrg.getPassword())) {
                session.setAttribute("loginUser", userOrg.getUserName());
                System.out.println("jsessionid : " + session.getId());
                logger.info(userOrg.getOrgName() + "  登录成功");
                UserService.sessionConutsMap.put(session.getId(), user.getCount());//  sdsdsd   101  或者  fdsfdsf   admin
                UserService.userMap.put(user.getCount(), userOrg);
                return "redirect:/home";
            }
            logger.warn("登录失败");
            map.put("msg", "请核对账号密码");
            return "login";
        } else {
            logger.warn("登录失败");
            map.put("msg", "请核对账号密码");
            return "login";
        }
    }


    @GetMapping("/Logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        userService.removeUser(request);
        session.setAttribute("loginUser", null);
        return "login";
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }


    @GetMapping("/monitor")
    public String monitor() {
        return "monitor";
    }

    @GetMapping("/TerminalMonitor")
    public String terminalMonitor() {
        return "TerminalMonitor";
    }

    /**
     * 历史轨迹
     */
    @GetMapping("/playback")
    public String playback(@RequestParam("key") String key, @RequestParam("name") String name, @RequestParam("mt") String mt, HttpSession session) {
        session.setAttribute("key", key);
        session.setAttribute("name", name);
        session.setAttribute("mt", mt);
        return "playback";
    }

    /**
     * 当前位置
     */
    @GetMapping("/follow")
    public String follow(@RequestParam("key") String key, @RequestParam("name") String name, @RequestParam("mt") String mt, HttpSession session) {
        session.setAttribute("key", key);
        session.setAttribute("name", name);
        session.setAttribute("mt", mt);
        return "follow";
    }

    @GetMapping("/Fence")
    public String fence() {
        return "Fence";
    }

    @GetMapping("/EleFence")
    public String eleFence() {
        return "EleFence";
    }

    @GetMapping("/sendCtrl")
    public String sendCtrl() {
        return "send";
    }

    @GetMapping("/map")
    public String map() {
        return "map";
    }

    @GetMapping("/route")
    public String route() {
        return "route";
    }


    /**
     * 报警报表
     */
    @GetMapping("/AlarmReport")
    public String alarmReport() {
        return "AlarmReport";
    }


    /**
     * 离线报表
     */
    @GetMapping("/OfflineReport")
    public String offlineReport() {
        return "OfflineReport";
    }

    /**
     * 停留报表
     */
    @GetMapping("/StopReport")
    public String stopReport() {
        return "StopReport";
    }

    /**
     * 里程报表
     */
    @GetMapping("/MileageReport")
    public String mileageReport() {
        return "MileageReport";
    }

    /**
     * 车辆管理
     */
    @GetMapping("/CarManagement")
    public String carManagement() {
        return "CarManagement";
    }

    /**
     * 报警报表
     */
    @GetMapping("/OnlineReport")
    public String onlineReport() {
        return "OnlineReport";
    }

    /**
     * 用户管理
     */
    @GetMapping("/UserManagement")
    public String userManagement() {
        return "UserManagement";
    }

    /**
     * 组管理
     */
    @GetMapping("/GroupManagement")
    public String groupManagement() {
        return "GroupManagement";
    }


}
