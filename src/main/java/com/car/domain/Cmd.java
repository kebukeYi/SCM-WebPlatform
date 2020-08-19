package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-01-19 7:21:25
 * {"Status":1,"Result":[{"CmdId":1,"CmdName":"远程设防/撤防","CmdKey":"BP02","ModelId":0,"IsChecked":false},{"CmdId":15,"CmdName":"远程设置最高速度","CmdKey":"BP74","ModelId":0,"IsChecked":false}],"ErrorMessage":null}
 * 
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Cmd {

	private int CmdId;
	private String CmdName;
	private String CmdKey;
	private int ModelId;
	private boolean IsChecked;

	public Cmd(int cmdId, String cmdName, String cmdKey, int modelId, boolean isChecked) {
		CmdId = cmdId;
		CmdName = cmdName;
		CmdKey = cmdKey;
		ModelId = modelId;
		IsChecked = isChecked;
	}

	@JsonProperty("CmdId")
	public int getCmdId() {
		return CmdId;
	}

	public void setCmdId(int cmdId) {
		CmdId = cmdId;
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
	@JsonProperty("ModelId")
	public int getModelId() {
		return ModelId;
	}

	public void setModelId(int modelId) {
		ModelId = modelId;
	}
	@JsonProperty("IsChecked")
	public boolean isChecked() {
		return IsChecked;
	}

	public void setChecked(boolean checked) {
		IsChecked = checked;
	}
}
