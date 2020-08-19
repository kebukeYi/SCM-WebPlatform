package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Auto-generated: 2020-01-19 6:56:0
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class StatInfo {

    @JsonProperty("Total")
    private int Total;

	@JsonProperty("Online")
    private int Online;

	@JsonProperty("Offline")
    private int Offline;

	@JsonProperty("Unknown")
    private int Unknown;

	@JsonProperty("Attention")
    private int Attention;

    public StatInfo() {
        this.Attention = 0;
        this.Total = 43;
        this.Online = 22;
        this.Offline = 21;
        this.Unknown = 0;
    }
}