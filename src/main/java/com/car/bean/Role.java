package com.car.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/9  11:17
 * @Description 角色
 */
public class Role {

    public Role() {

    }

    private String id;
    private String group_id;
    private String group_name;
    private String parent_name;
    private String seq;
    private String createTime;
    private String remark;
    private String roleOrgModels;
    private String role_id;
    private String role_name;
    private String selected_roleld;

    @Override
    public String toString() {
        return "Role{" +
                "group_id='" + group_id + '\'' +
                ", group_name='" + group_name + '\'' +
                ", parent_name='" + parent_name + '\'' +
                ", CreateTime='" + createTime + '\'' +
                ", Remark='" + remark + '\'' +
                ", RoleOrgModels='" + roleOrgModels + '\'' +
                ", role_name='" + role_name + '\'' +
                '}';
    }

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("OrgId")
    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    @JsonProperty("OrgName")
    public String getGroup_name() {
        return group_name;
    }


    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @JsonProperty("ParentName")
    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    @JsonProperty("Seq")
    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    @JsonProperty("CreateTime")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @JsonProperty("Remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonProperty("RoleOrgModels")
    public String getRoleOrgModels() {
        return roleOrgModels;
    }

    public void setRoleOrgModels(String roleOrgModels) {
        this.roleOrgModels = roleOrgModels;
    }

    @JsonProperty("RoleId")
    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    @JsonProperty("RoleName")
    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    @JsonProperty("SelectedRoleld")
    public String getSelected_roleld() {
        return selected_roleld;
    }

    public void setSelected_roleld(String selected_roleld) {
        this.selected_roleld = selected_roleld;
    }
}
