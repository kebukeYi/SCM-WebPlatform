package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
 * {"Status":1,"Result":[{"Id":1,"ModelName":"R01","ModelType":1,"Remark":null,"ProtoId":0},
 * {"Id":2,"ModelName":"T16","ModelType":0,"Remark":null,"ProtoId":0},{"Id":3,"ModelName":"R06","ModelType":1,"Remark":null,"ProtoId":0},{"Id":4,"ModelName":"W30","ModelType":1,"Remark":null,"ProtoId":0},{"Id":5,"ModelName":"C12","ModelType":0,"Remark":null,"ProtoId":0},{"Id":6,"ModelName":"S01","ModelType":1,"Remark":null,"ProtoId":0},{"Id":7,"ModelName":"X11","ModelType":0,"Remark":null,"ProtoId":0},{"Id":8,"ModelName":"C06","ModelType":0,"Remark":"红将军专用","ProtoId":0},{"Id":10,"ModelName":"C09","ModelType":1,"Remark":null,"ProtoId":0},{"Id":11,"ModelName":"S12","ModelType":1,"Remark":null,"ProtoId":1},{"Id":12,"ModelName":"JT808有线","ModelType":0,"Remark":null,"ProtoId":1},{"Id":13,"ModelName":"S09","ModelType":1,"Remark":null,"ProtoId":1},{"Id":14,"ModelName":"C14","ModelType":0,"Remark":null,"ProtoId":0},{"Id":15,"ModelName":"C13","ModelType":0,"Remark":null,"ProtoId":0},{"Id":17,"ModelName":"W36","ModelType":0,"Remark":null,"ProtoId":0},{"Id":18,"ModelName":"JT808无线","ModelType":1,"Remark":null,"ProtoId":1},{"Id":19,"ModelName":"S06","ModelType":1,"Remark":null,"ProtoId":1},{"Id":20,"ModelName":"W30-JT","ModelType":1,"Remark":null,"ProtoId":1},{"Id":21,"ModelName":"无线部标测试","ModelType":1,"Remark":null,"ProtoId":1},{"Id":22,"ModelName":"C12-JT","ModelType":0,"Remark":null,"ProtoId":1},{"Id":23,"ModelName":"C11-JT","ModelType":0,"Remark":null,"ProtoId":1},{"Id":24,"ModelName":"C06-TRV","ModelType":0,"Remark":null,"ProtoId":0},{"Id":29,"ModelName":"S11","ModelType":1,"Remark":null,"ProtoId":0},{"Id":30,"ModelName":"C17B","ModelType":0,"Remark":null,"ProtoId":0},{"Id":31,"ModelName":"D01","ModelType":1,"Remark":"星际通S01设备专用型号","ProtoId":0},{"Id":32,"ModelName":"D05","ModelType":0,"Remark":"星际通C12专用型号","ProtoId":0},{"Id":33,"ModelName":"W38","ModelType":1,"Remark":null,"ProtoId":0},{"Id":34,"ModelName":"C18-JT","ModelType":0,"Remark":null,"ProtoId":1},{"Id":36,"ModelName":"C18-TRV","ModelType":0,"Remark":null,"ProtoId":0},{"Id":38,"ModelName":"S11-JT","ModelType":1,"Remark":null,"ProtoId":1},{"Id":40,"ModelName":"S01-JT","ModelType":1,"Remark":null,"ProtoId":1},{"Id":41,"ModelName":"S06-jt测试","ModelType":0,"Remark":"测试专用","ProtoId":1},{"Id":42,"ModelName":"C18A-JT","ModelType":0,"Remark":"带电池，具有断油电功能","ProtoId":1},{"Id":43,"ModelName":"C11-JT-NEW","ModelType":0,"Remark":"带断油电，位移报警等功能","ProtoId":1},{"Id":44,"ModelName":"S06-NEW","ModelType":1,"Remark":"18005","ProtoId":1},{"Id":45,"ModelName":"X5","ModelType":1,"Remark":"实际是S1701","ProtoId":1},{"Id":46,"ModelName":"C16","ModelType":0,"Remark":null,"ProtoId":1},{"Id":47,"ModelName":"APPCS01","ModelType":0,"Remark":"APP测试调试用","ProtoId":0},{"Id":48,"ModelName":"APPCS02","ModelType":0,"Remark":"APP测试调试用","ProtoId":1},{"Id":49,"ModelName":"SG2000-G01E","ModelType":0,"Remark":null,"ProtoId":2},{"Id":50,"ModelName":"C11-JT-4P","ModelType":0,"Remark":null,"ProtoId":1},{"Id":51,"ModelName":"W36-JT","ModelType":0,"Remark":null,"ProtoId":1}],"ErrorMessage":null}
 */
@Data
public class Model {

	@JsonProperty("Id")
	private int Id;
	@JsonProperty("ModelName")
	private String ModelName;
	@JsonProperty("ModelType")
	private int ModelType;
	@JsonProperty("Remark")
	private String Remark;
	@JsonProperty("ProtoId")
	private int ProtoId;

}