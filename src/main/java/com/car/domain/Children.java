package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Children {

	@JsonProperty("ParentId")
	private String ParentId;
	@JsonProperty("Seq")
	private int Seq;
	@JsonProperty("RoleOrgModels")
	private String RoleOrgModels;
	@JsonProperty("Level")
	private int Level;
	@JsonProperty("Id")
	private String Id;
	@JsonProperty("ParentName")
	private String ParentName;
	@JsonProperty("OrgName")
	private String OrgName;
	@JsonProperty("Remark")
	private String Remark;
	@JsonProperty("ImgName")
	private String ImgName;
	@JsonProperty("CreateTime")
	private String CreateTime;
	@JsonProperty("OrganizationId")
	private String OrganizationId;

}