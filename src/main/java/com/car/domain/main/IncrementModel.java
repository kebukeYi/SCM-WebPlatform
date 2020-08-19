package com.car.domain.main;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  18:24
 * @Description
 */
public class IncrementModel {
	
    private String Date;
    private int Count;
    
    @JsonProperty("Date")
    public String getDate() {
		return Date;
	}


	public void setDate(String date) {
		Date = date;
	}

	 @JsonProperty("Count")
	public int getCount() {
		return Count;
	}


	public void setConut(int conut) {
		Count = conut;
	}


	public IncrementModel(String date, int conut) {
        Date = date;
		Count = conut;
    }
}
