package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Auto-generated: 2020-01-19 7:37:41
 * {"Status":1,"Result":{"data":[{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:48:17","DeviceName":"C18A-03034","Lng":"113.856946","Lat":"22.586444","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:48:18.837"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:48:07","DeviceName":"C18A-03034","Lng":"113.859977","Lat":"22.584019","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:48:08.253"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:47:57","DeviceName":"C18A-03034","Lng":"113.859977","Lat":"22.584019","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:47:58.3"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:41:02","DeviceName":"C18A-03034","Lng":"113.857071","Lat":"22.586648","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:41:04.847"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:40:52","DeviceName":"C18A-03034","Lng":"113.857034","Lat":"22.586801","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:40:54.683"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:40:42","DeviceName":"C18A-03034","Lng":"113.857066","Lat":"22.586724","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:40:44.65"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:40:22","DeviceName":"C18A-03034","Lng":"113.856893","Lat":"22.586391","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:40:24.307"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:40:13","DeviceName":"C18A-03034","Lng":"113.859977","Lat":"22.584019","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:40:14.163"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:02:24","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:02:24.88"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":2,"AlarmText":"断电报警","AlarmTime":"2018-05-10T20:02:24","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:02:24.88"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:02:10","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:02:10.64"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":2,"AlarmText":"断电报警","AlarmTime":"2018-05-10T20:02:10","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:02:10.637"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:01:44","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:01:45.937"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":2,"AlarmText":"断电报警","AlarmTime":"2018-05-10T20:01:44","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:01:45.933"},{"DeviceNumber":"218180003034","OrgName":"WRT201805100179","AlarmType":128,"AlarmText":"低电报警","AlarmTime":"2018-05-10T20:00:18","DeviceName":"C18A-03034","Lng":"113.857131","Lat":"22.586848","Content":"","Aliase":"C18A-03034","PlateNo":null,"ReceiveTime":"2018-05-10T20:00:19.983"}],"page":1,"total":18,"limit":15,"IsExcel":false},"ErrorMessage":null}
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Entity
public class Alarm implements Serializable {

    @Id
    private String id;
    private String user_id;

    private String group_id;
    private String group_name;

    private String dev_name;
    private String dev_number;

    private int alarm_type_id;
    private String alarm_text;
    private String alarm_time;
    private String content;

    private String catalogue_id;//所属目录id          有
    private String cata_name;

    private String lng;
    private String lat;
    private String lnglat;

    private String aliase;
    private String receive_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getCatalogue_id() {
        return catalogue_id;
    }

    public void setCatalogue_id(String catalogue_id) {
        this.catalogue_id = catalogue_id;
    }

    @JsonProperty("CataName")
    public String getCata_name() {
        return cata_name;
    }

    public void setCata_name(String cata_name) {
        this.cata_name = cata_name;
    }

    @JsonProperty("DeviceNumber")
    public String getDev_number() {
        return dev_number;
    }

    public void setDev_number(String dev_number) {
        this.dev_number = dev_number;
    }

    @JsonProperty("OrgId")
    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    @JsonProperty("OrgName")
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @JsonProperty("AlarmType")
    public int getAlarm_type_id() {
        return alarm_type_id;
    }

    public void setAlarm_type_id(int alarm_type_id) {
        this.alarm_type_id = alarm_type_id;
    }

    @JsonProperty("AlarmText")
    public String getAlarm_text() {
        return alarm_text;
    }

    public void setAlarm_text(String alarm_text) {
        this.alarm_text = alarm_text;
    }

    @JsonProperty("AlarmTime")
    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }

    @JsonProperty("DeviceName")
    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    @JsonProperty("Lng")
    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @JsonProperty("Lat")
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("Content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("Aliase")
    public String getAliase() {
        return aliase;
    }

    public void setAliase(String aliase) {
        this.aliase = aliase;
    }

    @JsonProperty("ReceiveTime")
    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("LngLat")
    public String getLnglat() {
        return lnglat;
    }

    public void setLnglat(String lnglat) {
        this.lnglat = lnglat;
    }
}