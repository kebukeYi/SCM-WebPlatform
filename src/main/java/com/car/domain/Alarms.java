package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2020-01-19 7:8:37
 * {"Status":1,"Result":{"data":[{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:48:17","DeviceName":"C18A-03034","Lng":"113.856946","Lat":"22.586444","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:48:18.837"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:48:07","DeviceName":"C18A-03034","Lng":"113.859977","Lat":"22.584019","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:48:08.253"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:47:57","DeviceName":"C18A-03034","Lng":"113.859977","Lat":"22.584019","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:47:58.3"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:41:02","DeviceName":"C18A-03034","Lng":"113.857071","Lat":"22.586648","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:41:04.847"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:40:52","DeviceName":"C18A-03034","Lng":"113.857034","Lat":"22.586801","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:40:54.683"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:40:42","DeviceName":"C18A-03034","Lng":"113.857066","Lat":"22.586724","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:40:44.65"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:40:22","DeviceName":"C18A-03034","Lng":"113.856893","Lat":"22.586391","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:40:24.307"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:40:13","DeviceName":"C18A-03034","Lng":"113.859977","Lat":"22.584019","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:40:14.163"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:02:24","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:02:24.88"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":2,"AlarmText":"断电报警","AlarmTime":"2018-05-10T20:02:24","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:02:24.88"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:02:10","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:02:10.64"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":2,"AlarmText":"断电报警","AlarmTime":"2018-05-10T20:02:10","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:02:10.637"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:01:44","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:01:45.937"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":2,"AlarmText":"断电报警","AlarmTime":"2018-05-10T20:01:44","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:01:45.933"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:00:18","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:00:19.983"}],"page":1,"total":18,"limit":15,"IsExcel":false},"ErrorMessage":null}
 *
 * @author bejson.com (i@bejson.com)
 * @website http://w
 * www.bejson.com/java2pojo/
 */
@Data
public class Alarms {


    private List<Alarm> data;
    private int page;
    private int total;
    private int limit;
    private boolean IsExcel;

    @JsonProperty("data")
    public List<Alarm> getData() {
        return data;
    }

    public void setData(List<Alarm> data) {
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