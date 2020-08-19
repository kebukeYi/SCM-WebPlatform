package com.car.domain.main;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  19:11
 * @Description
 */

public class ExpireDate {

		private String Days;
		private int Count;
    
    @JsonProperty("Days")
	public String getDays() {
		return Days;
	}
	public void setDays(String days) {
		Days = days;
	}
	 @JsonProperty("Count")
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
    
}
