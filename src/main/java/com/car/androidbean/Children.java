package com.car.androidbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/26  15:24
 * @Description
 */
public class Children {

    String id;//子账号id
    String name;
    String showname;
    boolean haschild;//是否有子账户

    public Children() {
    }

    public Children(String id, String name, String showname, boolean haschild) {
        this.id = id;
        this.name = name;
        this.showname = showname;
        this.haschild = haschild;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("showname")
    public String getShowname() {
        return showname;
    }

    public void setShowname(String showname) {
        this.showname = showname;
    }

    @JsonProperty("haschild")
    public boolean isHaschild() {
        return haschild;
    }

    public void setHaschild(boolean haschild) {
        this.haschild = haschild;
    }
}







