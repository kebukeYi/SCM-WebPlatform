package com.car.mysql.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.domain.Terminal;
import com.car.domain.Terminals;
import com.car.mapper.TerminalMapper;
import com.car.redis.JedisUtil6800;
import com.car.service.CommonService;
import com.car.service.ReportService;
import com.car.setting.UserSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/16  20:53
 * @Description
 */
@Service
public class Terminalmpl {

    @Autowired
    private TerminalMapper terminalMapper;

    @Autowired
    private CommonService commonService;


    /**
     * 终端管理
     */
    public Terminals getTerminalByMap(Map<String, Object> map) {
        map = commonService.getIdByMap(map);
        List<Terminal> terminalList = terminalMapper.getTerminalByMap(map);
        Terminals terminals = new Terminals();
        terminals.setData(terminalList);
        terminals.setTotal(terminalList.size());

        return terminals;
    }

    public void insertTerminal(Map<String, Object> map) {
        map.put("model_name", ReportService.getModelNameByModelId(map.get("ModeId") + ""));
        terminalMapper.insertTerminal(map);
    }

    public void insertTerminalForEach(List<Terminal> terminals) {
        terminalMapper.insertTerminalForEach(terminals);
    }

    //修改终端信息
    public boolean edit(Map<String, Object> map) {
        map = commonService.getIdByMap(map);
        String model_id = (String) map.get("ModeId");
        if (model_id != null && !"".equals(model_id)) {
            String ModeName = ReportService.getModelNameByModelId(model_id);
            System.out.println(ModeName);
            map.put("ModeName", ModeName);
        }
        terminalMapper.edit(map);
        return true;
    }


    //终端消去车牌号
    public void DisassociateVehicle(Map<String, Object> map) {
        terminalMapper.DisassociateVehicle(map);

    }

    //终端设置新车辆号
    public void AssociateVehicle(Map<String, Object> map) {
        terminalMapper.AssociateVehicle(map);
    }

    //删除终端
    @Transactional(rollbackFor = Exception.class)
    public void Remove(JSONArray jsonArray) {
        for (Object objId : jsonArray) {
            terminalMapper.RemoveVehicle(objId + "");
        }
    }

    /**
     * 是否存在此设备号
     */
    public JSONObject CheckDeviceNumberOnly(Map<String, Object> map) {
        String deviceNumber = (String) map.get("deviceNumber");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(UserSetting.VALID, !JedisUtil6800.existsString4(deviceNumber));
        return jsonObject;
    }
}
