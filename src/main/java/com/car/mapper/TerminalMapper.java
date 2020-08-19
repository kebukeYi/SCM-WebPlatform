package com.car.mapper;

import com.car.domain.Terminal;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/16  20:38
 * @Description
 */
public interface TerminalMapper {


    List<Terminal> getTerminalByMap(Map<String, Object> map);

    void insertTerminal(Map<String, Object> map);

    void insertTerminalForEach(List<Terminal> terminals);

    boolean edit(Map<String, Object> map);

    //绑定车牌号
    void AssociateVehicle(Map<String, Object> map);

    //终端去除车牌号
    void DisassociateVehicle(Map<String, Object> map);

    //删除终端设备
    void RemoveVehicle(String id);

}
