package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2020-01-19 7:48:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Fences {

	private List<Fence> data;

	private int page;

	private int total;

	private int limit;

	private boolean IsExcel;


	@JsonProperty("data")
	public List<Fence> getData() {
		return data;
	}

	public void setData(List<Fence> data) {
		this.data = data;
	}

	@JsonProperty("page")
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@JsonProperty("total")
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@JsonProperty("limit")
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@JsonProperty("IsExcel")
	public boolean isExcel() {
		return IsExcel;
	}

	public void setExcel(boolean excel) {
		IsExcel = excel;
	}
}