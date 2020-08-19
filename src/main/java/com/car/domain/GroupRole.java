package com.car.domain;

import com.car.bean.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/15  16:16
 * @Description
 */
@Data
public class GroupRole {

    String createTime;
    private String id;
    String imgName;
    int level;
    String orgName;
    String parentId;
    String parentName;
    String remark;
    int seq;
    private List<Role> RoleOrgModels;

    @JsonProperty("CreateTime")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("ImgName")
    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @JsonProperty("OrgName")
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @JsonProperty("ParentId")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @JsonProperty("ParentName")
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @JsonProperty("Remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonProperty("Seq")
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }


    @JsonProperty("RoleOrgModels")
    public List<Role> getRoleOrgModels() {
        return RoleOrgModels;
    }

    public void setRoleOrgModels(List<Role> roleOrgModels) {
        RoleOrgModels = roleOrgModels;
    }
}
