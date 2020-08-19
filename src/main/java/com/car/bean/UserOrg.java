package com.car.bean;

import com.car.domain.main.Distribution;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/2  21:08
 * @Description
 */
public class UserOrg {

    String user_id;
    String son_ids;//所拥有子用户 id 组
    String imgName;
    String realName;
    int level;
    String orgName;
    String userName;

    String orgnizationIds;
    String catalogueIds;
    String fence_ids;// 所绑定的围栏id   用list 保存

    String parentId;
    String orgParentId;
    String parentName;
    String roleOrgModels;
    String role_name;
    String password;
    String phone;
    String createTime;
    String exprie_time;//到期日期
    String email;
    boolean disable;//状态
    String EnabledText;//状态字数
    int seq;
    String Remark;


    //list 存储
    String WirelessOffline;//无线下线
    String WirelessOnline;//无线上线
    String WiredOffline;//有线下线

    //list 存储
    String DailyAlarm;//报警统计
    String DevIncrementModel;//设备增量统计
    String ExpireDateModel;//到期统计
    String UserIncrementModel;//子用户增量统计
    String statInfo;//统计在线 离线数量
    String distributions;//统计设备地理位置


    @JsonProperty("RealName")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }








    @JsonProperty("Remark")
    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    @JsonProperty("EnabledText")
    public String getEnabledText() {
        return EnabledText;
    }

    public void setEnabledText(String enabledText) {
        EnabledText = enabledText;
    }

    @JsonProperty("Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("RoleName")
    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @JsonProperty("UserName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("OrgName")
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }


    @JsonProperty("Tel")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("CreateTime")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @JsonProperty("ExpireDate")
    public String getExprie_time() {
        return exprie_time;
    }

    public void setExprie_time(String exprie_time) {
        this.exprie_time = exprie_time;
    }


    @JsonProperty("Seq")
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSon_ids() {
        return son_ids;
    }

    public void setSon_ids(String son_ids) {
        this.son_ids = son_ids;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getOrgnizationIds() {
        return orgnizationIds;
    }

    public void setOrgnizationIds(String orgnizationIds) {
        this.orgnizationIds = orgnizationIds;
    }

    public String getCatalogueIds() {
        return catalogueIds;
    }

    public void setCatalogueIds(String catalogueIds) {
        this.catalogueIds = catalogueIds;
    }

    public String getFence_ids() {
        return fence_ids;
    }

    public void setFence_ids(String fence_ids) {
        this.fence_ids = fence_ids;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrgParentId() {
        return orgParentId;
    }

    public void setOrgParentId(String orgParentId) {
        this.orgParentId = orgParentId;
    }

    public String getRoleOrgModels() {
        return roleOrgModels;
    }

    public void setRoleOrgModels(String roleOrgModels) {
        this.roleOrgModels = roleOrgModels;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getWirelessOffline() {
        return WirelessOffline;
    }

    public void setWirelessOffline(String wirelessOffline) {
        WirelessOffline = wirelessOffline;
    }

    public String getWirelessOnline() {
        return WirelessOnline;
    }

    public void setWirelessOnline(String wirelessOnline) {
        WirelessOnline = wirelessOnline;
    }

    public String getWiredOffline() {
        return WiredOffline;
    }

    public void setWiredOffline(String wiredOffline) {
        WiredOffline = wiredOffline;
    }

    public String getDailyAlarm() {
        return DailyAlarm;
    }

    public void setDailyAlarm(String dailyAlarm) {
        DailyAlarm = dailyAlarm;
    }

    public String getDevIncrementModel() {
        return DevIncrementModel;
    }

    public void setDevIncrementModel(String devIncrementModel) {
        DevIncrementModel = devIncrementModel;
    }

    public String getExpireDateModel() {
        return ExpireDateModel;
    }

    public void setExpireDateModel(String expireDateModel) {
        ExpireDateModel = expireDateModel;
    }

    public String getUserIncrementModel() {
        return UserIncrementModel;
    }

    public void setUserIncrementModel(String userIncrementModel) {
        UserIncrementModel = userIncrementModel;
    }

    public String getStatInfo() {
        return statInfo;
    }

    public void setStatInfo(String statInfo) {
        this.statInfo = statInfo;
    }

    public String getDistributions() {
        return distributions;
    }

    public void setDistributions(String distributions) {
        this.distributions = distributions;
    }

    @Override
    public String toString() {
        return "UserOrg{" +
                "user_id='" + user_id + '\'' +
                ", WirelessOffline='" + WirelessOffline + '\'' +
                ", WirelessOnline='" + WirelessOnline + '\'' +
                ", WiredOffline='" + WiredOffline + '\'' +
                '}';
    }
}
