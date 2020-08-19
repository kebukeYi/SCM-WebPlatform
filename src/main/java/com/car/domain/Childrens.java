package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2020-01-19 7:3:40
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Childrens {

	@JsonProperty("Status")
	private int Status;

	@JsonProperty("Status")
	private List<Children> Children;

	@JsonProperty("Status")
	private String ErrorMessage;

}