package com.car.bean;

import lombok.Data;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/6  19:46
 * @Description
 */
@Data
public class Organization {

    String organization_id;
    String parent_id;//  父id  -> 用户id
    String organization_name;
    String catalogue_ids;//所拥有目录id     用list 保存
    String fence_ids;       //所绑定的围栏id  用list 保存
    String creat_time;
    String remark;

    String group_fence_ids;
    String group_fence_alarm_type;// 只是适用于 关联组级别的 显示
    String group_fence_alarm_type_text;
    String group_bind_time;

    @Override
    public String toString() {
        return "Organization{" +
                "organization_id='" + organization_id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", organization_name='" + organization_name + '\'' +
                ", catalogue_ids='" + catalogue_ids + '\'' +
                ", fence_ids='" + fence_ids + '\'' +
                ", group_fence_ids='" + group_fence_ids + '\'' +
                ", group_fence_alarm_type='" + group_fence_alarm_type + '\'' +
                ", group_bind_time='" + group_bind_time + '\'' +
                '}';
    }
}
