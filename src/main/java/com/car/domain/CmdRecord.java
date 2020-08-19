package com.car.domain;

/*
 * {"Status":1,"Result":{"data":[{"CmdName":"设置IP/域名端口","CmdKey":"BP64","Result":"cn01.360gps.net:6100","ResultText":"cn01.360gps.net:6100","DeviceName":"181219400018900","CmdStatus":3,"CmdStatusText":"已应答","Content":"TRVBP64,000001,cn01.360gps.net,6100#","Message":"cn01.360gps.net:6100","ResponseTime":"2019-12-05T15:43:15.62","LastSendTime":"2019-12-05T15:43:14.677","Operator":"林晓帆"}],"page":1,"total":1,"limit":15,"IsExcel":false},"ErrorMessage":null}
 */
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CmdRecord {


	private String CmdName;
	private String CmdKey;
	private String Result;
	private String ResultText;
	private String DeviceName;
	private int CmdStatus;
	private String CmdStatusText;
	private String Content;
	private String Message;
	private String ResponseTime;
	private String LastSendTime;
	private String Operator;
	private String Duration;//持续时间
	private String Speed;
	private String Minutes;//上發間隔時間




	public String getMinutes() {
		return Minutes;
	}

	public void setMinutes(String minutes) {
		Minutes = minutes;
	}

	@JsonProperty("Duration")
	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}

	@JsonProperty("Speed")
	public String getSpeed() {
		return Speed;
	}

	public void setSpeed(String speed) {
		Speed = speed;
	}

	@JsonProperty("CmdName")
	public String getCmdName() {
		return CmdName;
	}

	public void setCmdName(String cmdName) {
		CmdName = cmdName;
	}
	@JsonProperty("CmdKey")
	public String getCmdKey() {
		return CmdKey;
	}

	public void setCmdKey(String cmdKey) {
		CmdKey = cmdKey;
	}
	@JsonProperty("Result")
	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}
	@JsonProperty("ResultText")
	public String getResultText() {
		return ResultText;
	}

	public void setResultText(String resultText) {
		ResultText = resultText;
	}
	@JsonProperty("DeviceName")
	public String getDeviceName() {
		return DeviceName;
	}

	public void setDeviceName(String deviceName) {
		DeviceName = deviceName;
	}
	@JsonProperty("CmdStatus")
	public int getCmdStatus() {
		return CmdStatus;
	}

	public void setCmdStatus(int cmdStatus) {
		CmdStatus = cmdStatus;
	}
	@JsonProperty("CmdStatusText")
	public String getCmdStatusText() {
		return CmdStatusText;
	}

	public void setCmdStatusText(String cmdStatusText) {
		CmdStatusText = cmdStatusText;
	}
	@JsonProperty("Content")
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	@JsonProperty("Message")
	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
	@JsonProperty("ResponseTime")
	public String getResponseTime() {
		return ResponseTime;
	}

	public void setResponseTime(String responseTime) {
		ResponseTime = responseTime;
	}
	@JsonProperty("LastSendTime")
	public String getLastSendTime() {
		return LastSendTime;
	}

	public void setLastSendTime(String lastSendTime) {
		LastSendTime = lastSendTime;
	}
	@JsonProperty("Operator")
	public String getOperator() {
		return Operator;
	}

	public void setOperator(String operator) {
		Operator = operator;
	}
}