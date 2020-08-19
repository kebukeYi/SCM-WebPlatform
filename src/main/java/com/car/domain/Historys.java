package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2020-01-19 7:29:18
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Historys {


	private List<History> Data;
	private String MaxDateTime;

	@JsonProperty("Data")
	public List<History> getData() {
		return Data;
	}

	public void setData(List<History> data) {
		Data = data;
	}


	/*
	最近时间
	 */
	@JsonProperty("MaxDateTime")
	public String getMaxDateTime() {
		return MaxDateTime;
	}

	public void setMaxDateTime(String maxDateTime) {
		MaxDateTime = maxDateTime;
	}
}