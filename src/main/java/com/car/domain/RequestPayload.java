package com.car.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  15:34
 * @Description
 */
@Data
public class RequestPayload implements Serializable {

    private static final long serialVersionUID = 8294180014912103005L;


    /*监控中心页面 默认 页面
        WithChildren: false   包括自组 时 true
        Key: ""   车牌号 181219400018893 SIM号 车牌号 搜索关键字（带着OrgId）
        Flag:  默认 ：0  在线：1 离线 ：2   关注：3
        limit: 15
        start: 0
        page: 1
       默认无OrgId； 点击 在线 离线 关注有  : "168c3200-a1dd-473c-9d30-a8230120604d"
     */


    boolean WithChildren = false;
    private String Key;
    int Flag;
    int limit;
    int start;
    int page;
    String OrgId;

    @Override
    public String toString() {
        return "RequestPayload{" +
                "limit=" + limit +
                ", start=" + start +
                ", page=" + page +
                '}';
    }
}
