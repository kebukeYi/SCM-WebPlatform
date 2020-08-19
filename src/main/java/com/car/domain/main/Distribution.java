package com.car.domain.main;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  18:06
 * @Description
 */

public class Distribution {
	
   
    private int AdCode;
    private int Count;
    
    @JsonProperty("AdCode")
    public int getAdCode() {
		return AdCode;
	}


	public void setAdCode(int adCode) {
		AdCode = adCode;
	}

	 @JsonProperty("Count")
	public int getCount() {
		return Count;
	}


	public void setCount(int count) {
		Count = count;
	}

	public Distribution() {
	}

	public Distribution(int adCode, int count) {
        AdCode = adCode;
        Count = count;
    }

	@Override
	public String toString() {
		return "Distribution{" +
				"AdCode=" + AdCode +
				", Count=" + Count +
				'}';
	}
}
