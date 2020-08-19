package com.car.bean;

import lombok.Data;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/6  19:51
 * @Description 目录bean
 */
@Data
public class Catalog {

    String catalog_id;
    String catalog_name;
    String parent_id;// 父id  ->组id
    String device_ids;// 所拥有的设备们  用list 保存
    String fence_ids;// 所绑定的围栏id   用list 保存
    String creat_time;
    String remark;

    String group_fence_ids;
    String group_fence_alarm_type;// 只是适用于 关联组级别的 显示
    String group_fence_alarm_type_text;
    String group_bind_time;

    @Override
    public String toString() {
        return "Catalog{" +
                "catalog_id='" + catalog_id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", device_ids='" + device_ids + '\'' +
                ", fence_ids='" + fence_ids + '\'' +
                ", group_fence_ids='" + group_fence_ids + '\'' +
                ", group_fence_alarm_type='" + group_fence_alarm_type + '\'' +
                ", group_fence_alarm_type_text='" + group_fence_alarm_type_text + '\'' +
                ", group_bind_time='" + group_bind_time + '\'' +
                '}';
    }
}
