package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-01-19 7:48:23
 * {"Status":1,"Result":{"data":[{
 * "Id":"09bb7ee8-ef6a-4321-90f7-ab0e014bdc26",
 * "FenceData":"98.57759043574333|24.411150891092404,100.0","FenceName":"家","FenceType":1,"FenceTypeText":"圆形",
 * "IsEnabled":true
 * },
 * {"Id":"4504288a-c300-4f50-a7c3-ab0e01446a8a","FenceData":"98.60337987542154|24.448714729763026,100.0",
 * "FenceName":"公司","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"8cf4676f-f8d6-43e1-8eee-ab0800d47b96","FenceData":"115.64003795385362|23.84660419835911,100.0","FenceName":"船肚路伟兴装饰工程","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"9ac1922b-6aa5-4c46-868b-aafc017cc7ff","FenceData":"121.24276220798494|31.344094102316998,810.0","FenceName":"白银路","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"82e7df8d-87d2-46eb-9c3c-aafc003d23cd","FenceData":"121.25762701034547|31.461588325531906,5000.0","FenceName":"越","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"eaa8122e-9168-4a60-829f-aafb00cb438e","FenceData":"120.46251833438873|36.2603679841123,100.0","FenceName":"幼儿园","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"a7f742d5-807b-472e-87a8-aafa013d87a0","FenceData":"121.48843243718144|31.40854097730262,160.0","FenceName":"宝城一村家","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"466705c0-a05d-4f3f-b6e9-aafa0035723d","FenceData":"108.451721966844|21.68293394931527,300","FenceName":"不要报警","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"6d68a0f5-5ce0-4743-a3f5-aaf901310d00","FenceData":"118.13640609383582|41.052398411056856,100.0","FenceName":"承德市承德县三沟镇梁前村南梁","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"82b8f196-9c65-480e-898d-aade0142eaca","FenceData":"105.24553656578063|28.297821339359846,100.0","FenceName":"兴文县城","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"7442cbe1-f7c3-4afb-9958-aade0142c968","FenceData":"105.24553656578063|28.297821339359846,100.0","FenceName":"兴文县城","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"e9e0568a-8314-47b9-8f6e-aad400d45b01","FenceData":"126.96664854884145|45.55404471931998,100.0","FenceName":"阿城区","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"3b7622c3-fd46-4787-bf28-aad2014b23bd","FenceData":"103.93494510169|29.28024976570834,300","FenceName":"家","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"7ddfc670-a313-4a04-9aee-aac400acdcbe","FenceData":"124.79796588420866|46.13959902875796,100.0","FenceName":"高台子","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true},{"Id":"f497ae65-3678-4c97-9278-aac30084ecd2","FenceData":"112.81746625900269|34.916019703686175,1010.0","FenceName":"金紫阳","FenceType":1,"FenceTypeText":"圆形","IsEnabled":true}],"page":1,"total":19,"limit":15,"IsExcel":false},"ErrorMessage":null}
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Fence {


    private String Id;
    private String FenceData;
    private String FenceName;
    private int FenceType;
    private String FenceTypeText;
    private String AlarmTypeText;
    private boolean IsEnabled;

    private String UserId;//所属 用户
    private String UserName;//所属 用户
    private String OrganizationIds;//所属哪些
    private String OrganizationIdsName;//所属哪些
    private String CatalogueIds;//所属哪些目录
    private String CatalogueIdsName;//所属哪些目录
    private String CreatTime;//新建时间

    @JsonProperty("CreatTime")
    public String getCreatTime() {
        return CreatTime;
    }

    public void setCreatTime(String creatTime) {
        CreatTime = creatTime;
    }

    @Override
    public String toString() {
        return "Fence{" +
                "Id='" + Id + '\'' +
                ", FenceType=" + FenceType +
                ", FenceTypeText='" + FenceTypeText + '\'' +
                ", UserId='" + UserId + '\'' +
                ", OrganizationIds='" + OrganizationIds + '\'' +
                ", CatalogueIds='" + CatalogueIds + '\'' +
                '}';
    }


    @JsonProperty("UserName")
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @JsonProperty("OrgName")
    public String getOrganizationIdsName() {
        return OrganizationIdsName;
    }

    public void setOrganizationIdsName(String organizationIdsName) {
        OrganizationIdsName = organizationIdsName;
    }

    @JsonProperty("CataName")
    public String getCatalogueIdsName() {
        return CatalogueIdsName;
    }

    public void setCatalogueIdsName(String catalogueIdsName) {
        CatalogueIdsName = catalogueIdsName;
    }

    @JsonProperty("UserId")
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setOrganizationIds(String organizationIds) {
        OrganizationIds = organizationIds;
    }

    @JsonProperty("CatalogueIds")
    public String getCatalogueIds() {
        return CatalogueIds;
    }

    public void setCatalogueIds(String catalogueIds) {
        CatalogueIds = catalogueIds;
    }

    @JsonProperty("OrganizationIds")
    public String getOrganizationIds() {
        return OrganizationIds;
    }

    public void setOrganizationId(String organizationId) {
        OrganizationIds = organizationId;
    }

    @JsonProperty("Id")
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @JsonProperty("FenceData")
    public String getFenceData() {
        return FenceData;
    }

    public void setFenceData(String fenceData) {
        FenceData = fenceData;
    }

    @JsonProperty("FenceName")
    public String getFenceName() {
        return FenceName;
    }

    public void setFenceName(String fenceName) {
        FenceName = fenceName;
    }

    @JsonProperty("FenceType")
    public int getFenceType() {
        return FenceType;
    }

    public void setFenceType(int fenceType) {
        FenceType = fenceType;
    }

    @JsonProperty("FenceTypeText")
    public String getFenceTypeText() {
        return FenceTypeText;
    }

    public void setFenceTypeText(String fenceTypeText) {
        FenceTypeText = fenceTypeText;
    }

    @JsonProperty("IsEnabled")
    public boolean isEnabled() {
        return IsEnabled;
    }

    public void setEnabled(boolean enabled) {
        IsEnabled = enabled;
    }

    @JsonProperty("AlarmTypeText")
	public String getAlarmTypeText() {
		return AlarmTypeText;
	}

	public void setAlarmTypeText(String alarmTypeText) {
		AlarmTypeText = alarmTypeText;
	}
}