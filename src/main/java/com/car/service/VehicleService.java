package com.car.service;

import com.alibaba.fastjson.JSONObject;
import com.car.domain.Car;
import com.car.redis.JedisUtil6800;
import com.car.setting.UserSetting;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/15  12:27
 * @Description
 */
@Service
public class VehicleService {




    public JSONObject CheckPlateNoOnly(Map<String, Object> map) {
        String PlateNo = (String) map.get("PlateNo");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(UserSetting.VALID, !JedisUtil6800.existsString13(PlateNo));
        return jsonObject;
    }


}
