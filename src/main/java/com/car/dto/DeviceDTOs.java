package com.car.dto;

import com.car.domain.StatInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  12:18
 * @Description
 */
@Data
public class DeviceDTOs {

    private StatInfo statInfo;
    private List<DeviceDTO> deviceDTOList;

    @JsonProperty("StatInfo")
    public StatInfo getStatInfo() {
        return statInfo;
    }

    public void setStatInfo(StatInfo statInfo) {
        this.statInfo = statInfo;
    }


    @JsonProperty("DeviceList")
    public List<DeviceDTO> getDeviceDTOList() {
        return deviceDTOList;
    }

    public void setDeviceDTOList(List<DeviceDTO> deviceDTOList) {
        this.deviceDTOList = deviceDTOList;
    }
}
