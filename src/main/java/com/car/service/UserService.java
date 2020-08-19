package com.car.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.androidbean.AndroidMonitorTip;
import com.car.bean.UserOrg;
import com.car.bean.UserOrgs;
import com.car.domain.StatInfo;
import com.car.domain.main.IncrementModel;
import com.car.redis.JedisUtil6800;
import com.car.setting.UserSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/21  0:00
 * @Description
 */
@Service
public class UserService {

    @Autowired
    private CommonService commonService;

    /**
     * 记录登录用户标识
     */
    public static Map<String, String> sessionConutsMap = new HashMap<>();

    /**
     * 登录用户Map
     */
    public static Map<String, UserOrg> userMap = new HashMap<>();


    /**
     * /user/GetUserIncrementModel
     */
    public List<IncrementModel> GetUserIncrementModel(Map<String, Object> map) {
        String userId = (String) map.get("userId");//1-100
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        List<IncrementModel> IncrementModel = new ArrayList<>();
        IncrementModel = JSONArray.parseArray(userOrg.getUserIncrementModel(), IncrementModel.class);
        return IncrementModel;
    }

    /**
     * 获得全部激活设备
     */
    public List<AndroidMonitorTip> getAndroidMonitorTipsByUserIdOrOrgIdOrCataId(Map<String, Object> map) {
        String orgIdOrCataIdOrUserId = (String) map.get("OrgId");//  用户id 组id 目录id
        List<AndroidMonitorTip> androidMonitorTipList = null;
        List<AndroidMonitorTip> androidMonitorTipList1 = new ArrayList<>();
        if (orgIdOrCataIdOrUserId == null) {//默认是用户的第一组的全部设备
            orgIdOrCataIdOrUserId = JSONArray.parseArray(JSONObject.parseObject(JedisUtil6800.getString9((String) map.get("userId")), UserOrg.class).getOrgnizationIds()).get(0) + "";
            androidMonitorTipList = commonService.getAndroidMonitorTipsByOrgId(orgIdOrCataIdOrUserId);//特定组的所有设备
            System.out.println("用户  " + orgIdOrCataIdOrUserId + "  特定一组下所有设备大小：" + androidMonitorTipList.size());
        } else {
            androidMonitorTipList = commonService.getAndroidMonitorTipsByUserIdOrOrgIdOrCataId(orgIdOrCataIdOrUserId);
        }
        System.out.println("用户  " + orgIdOrCataIdOrUserId + "   下所有设备大小：" + androidMonitorTipList.size());

        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
            if (androidMonitorTip.isActivation()) {
                androidMonitorTipList1.add(androidMonitorTip);
            }
        }
        return androidMonitorTipList1;
    }


    /**
     * 2/22 22:38
     * 3/8 18:11  更改为AndroidMonitorTip
     */
    public StatInfo getAndroidMonitorTipsStatInfoByUserId(String userId) {
        UserOrg userOrg = JSONObject.parseObject(JedisUtil6800.getString9(userId), UserOrg.class);
        System.out.println(userOrg.getStatInfo());
        StatInfo statInfo = JSONObject.parseObject(userOrg.getStatInfo(), StatInfo.class);
        return statInfo;
    }


    /**
     * 用户管理
     */
    public UserOrgs getUserOrgsByUserId(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        String Enabled = (String) map.get("Enabled");
        UserOrgs userOrgs = new UserOrgs();
        List<UserOrg> userOrgList = new ArrayList<>();
        if (UserSetting.FALSE.equals(Enabled)) {
            userOrgs.setTotal(userOrgList.size());
            userOrgs.setData(userOrgList);
            return userOrgs;
        }
        String str = JedisUtil6800.getString9(userId);
        if (str != null) {
            UserOrg userOrg = JSONObject.parseObject(str, UserOrg.class);
            JSONArray jsonArray = JSONArray.parseArray(userOrg.getSon_ids());
            for (Object obj : jsonArray) {
                UserOrg userOrg1 = JSONObject.parseObject(JedisUtil6800.getString9(obj + ""), UserOrg.class);
                userOrgList.add(userOrg1);
            }
        }

        userOrgs.setData(userOrgList);
        userOrgs.setTotal(userOrgList.size());
        return userOrgs;
    }


    public Map<String, Object> getUserIdByRequest(HttpServletRequest request, Map<String, Object> map) {
        String sessionId = request.getRequestedSessionId();
        String userId = sessionConutsMap.get(sessionId);
        if (userId != null) {
            map.put("userId", userId);
            return map;
        } else {
            map.put("userId", null);
            return map;
        }

    }

    public void removeUser(HttpServletRequest request) {
        String sessionId = request.getRequestedSessionId();
        String userId = sessionConutsMap.get(sessionId);
        sessionConutsMap.remove(sessionId);
        if (userId != null) {
            userMap.remove(userId);
        }
    }


    public JSONObject CheckUserNameOnly(Map<String, Object> map) {
        String userNmae = (String) map.get("username");
        JSONObject jsonObject = new JSONObject();
        Boolean result = false;
        List<UserOrg> userOrgList = JedisUtil6800.getUserOrgAll(9);
        for (UserOrg userOrg : userOrgList) {
            if (userNmae.equals(userOrg.getUserName())) {
                result = true;
            }
        }
        jsonObject.put(UserSetting.VALID, !result);
        return jsonObject;
    }
}
