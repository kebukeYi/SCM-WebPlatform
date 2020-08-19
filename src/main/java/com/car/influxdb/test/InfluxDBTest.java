package com.car.influxdb.test;


import com.alibaba.fastjson.JSONArray;
import com.car.domain.MonitorTips;
import com.car.influxdb.InfluxDBConnect;
import com.car.redis.JedisUtil6800;
import com.car.redis.bean.Point;
import com.car.utils.ReadJSONFile;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


public class InfluxDBTest {

    private InfluxDBConnect influxDB;
    private String username = "admin";//用户名
    private String password = "admin";//密码
    private String openurl = "http://127.0.0.1:8086";//连接地址
    //    private String database = "MonitorTips";//数据库
    private String database = "AndroidMonitorTips";//数据库
    private String measurement = "monitorTips_points";//表名
    //        private String measurement = "sys_code";//表名
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat formatGlz = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    static SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


    @Before
    public void setUp() {
        //创建 连接
        influxDB = new InfluxDBConnect(username, password, openurl, database);
        influxDB.influxDbBuild();
//        influxDB.createRetentionPolicy();
//        influxDB.deleteDB(database);
//        influxDB.createDB(database);
    }

    /*
    1.实现查询以给定字段开始的数据
    select fieldName from measurementName where fieldName=~/^给定字段/

    2.实现查询以给定字段结束的数据
    select fieldName from measurementName where fieldName=~/给定字段$/

    3.实现查询包含给定字段数据
    select fieldName from measurementName where fieldName=~/给定字段/

    selece packages,srcPort from flowStat where srcPort=~/^10/
     */

    @Test
    public void testInsert() {//测试数据插入
        String measurement = "sys_code";//表名
        Map<String, String> tags = new HashMap<String, String>();//索引
        Map<String, Object> fields = new HashMap<String, Object>();//字段值
        List<CodeInfo> list = new ArrayList<CodeInfo>();
        CodeInfo info1 = new CodeInfo();
        info1.setId(1L);
        info1.setName("44");
        info1.setCode("ABC");
        info1.setDescr("中国农业银行");
        info1.setDescrE("ABC");
        info1.setCreatedBy("system");
        info1.setCreatedAt(new Date().toString());

        CodeInfo info2 = new CodeInfo();
        info2.setId(2L);
        info2.setName("55");
        info2.setCode("BBC");
        info2.setDescr("中国建设银行");
        info2.setDescrE("CCB");
        info2.setCreatedBy("system");
        info2.setCreatedAt(sdf.format(new Date()));

        list.add(info1);
        list.add(info2);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.HOUR, -48);
        Date dt1 = rightNow.getTime();

        for (CodeInfo info : list) {
            tags.put("tagCode", info.getCode());
//            tags.put("TAG_TIME", formatUTC.format(new Date()));
            tags.put("tagTime", formatUTC.format(dt1));

            fields.put("id", info.getId());
            fields.put("name", info.getName());
            fields.put("code", info.getCode());
//            fields.put("descr", info.getDescr());
//            fields.put("descrE", info.getDescrE());
            fields.put("createdBy", info.getCreatedBy());
            fields.put("createdAt", info.getCreatedAt());

//            fields.put("time",formatUTC.format(dt1));
//            fields.put("time",formatGlz.format(new Date()));

//            fields.put("TIMETWO", sdf.format(dt1));
//            fields.put("TIMEONE", sdf.format(dt1));
//            fields.put("TIMEFOUR", formatGlz.format(new Date()));
//            fields.put("TIMETHREE", System.currentTimeMillis());

            influxDB.insert(measurement, tags, fields);
        }
    }


    @Test
    public void creatPoint() throws IOException, InterruptedException {
        Map<String, String> tags = new HashMap<String, String>();//索引
        Map<String, Object> fields = null;//字段值
        ClassPathResource classPathResource = new ClassPathResource("static/Location/points.json");
        InputStream inputStream = classPathResource.getInputStream();
        List<Point> pointList = JSONArray.parseArray(ReadJSONFile.readFile(inputStream), Point.class);
        MonitorTips monitorTips = JedisUtil6800.getMonitorTipsByDeviceId("1111100207");
        System.out.println(pointList.size());
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DATE, -1);
        Date dt1 = rightNow.getTime();
        for (Point point : pointList) {
            String date = formatGlz.format(dt1);
            rightNow.add(Calendar.SECOND, 30);
            dt1 = rightNow.getTime();
            monitorTips.setLat(point.getLat());
            monitorTips.setLng(point.getLng());
            monitorTips.setLocationTime(dt1.toString());
            fields = getMapByMonitorTips(monitorTips);
            tags.put("tagNum", monitorTips.getDeviceNumber());
            tags.put("TIME", date);
            Thread.sleep(20);
            influxDB.insert(measurement, tags, fields);
            tags.clear();
        }
        System.out.println("OVER");
    }

    public Map<String, Object> getMapByMonitorTips(MonitorTips monitorTips) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("AM", monitorTips.getAM());
        map.put("DeviceName", monitorTips.getDeviceName());
        map.put("DeviceNumber", monitorTips.getDeviceNumber());
        map.put("workMode", monitorTips.getWorkMode());
        map.put("Status", monitorTips.getStatus());
        map.put("statusMode", monitorTips.getStatusMode());
        map.put("ICCID", monitorTips.getICCID());
        map.put("LocationTime", monitorTips.getLocationTime());
        map.put("Signal", monitorTips.getSignal());
        map.put("Electricity", monitorTips.getElectricity());
//        map.put("OuterVoltage", monitorTips.getOuterVoltage());
        map.put("temp", monitorTips.getTemp());
        map.put("humi", monitorTips.getHumi());
        map.put("Direct", monitorTips.getDirect());
        map.put("Lat", monitorTips.getLat());
        map.put("Lng", monitorTips.getLng());
//        map.put("Speed", monitorTips.getSpeed());
//        map.put("GPSDirect", monitorTips.getGPSDirect());
        map.put("MCC", monitorTips.getMCC());
        map.put("MNC", monitorTips.getMNC());
        map.put("LAC", monitorTips.getLAC());
        map.put("CID", monitorTips.getCID());
//        map.put("stationInfo", monitorTips.getStationInfo());
//        map.put("commond", monitorTips.getCommond());
        map.put("IMSI", monitorTips.getIMSI());
//        map.put("latlngarraylist", monitorTips.getLatlngdest());
        map.put("latlngdest", monitorTips.getLatlngdest());
//        map.put("AP88", monitorTips.getAP88());
//        map.put("IP", monitorTips.getIP());
//        map.put("Port", monitorTips.getPort());
        map.put("Alarm", monitorTips.getAlarm());
        map.put("StopTime", monitorTips.getStopTime());
//        map.put("LocationType", monitorTips.getLocationType());
        map.put("Online", monitorTips.isOnline());
//        map.put("Satellites", monitorTips.getSatellites());
        map.put("ModelId", monitorTips.getModelId());
        map.put("ModelType", monitorTips.getModelType());
        map.put("ModelName", monitorTips.getModelName());
        map.put("Attention", monitorTips.isAttention());
//        map.put("Mileage", monitorTips.getMileage());
//        map.put("language", monitorTips.getLanguage());
//        map.put("re", monitorTips.getRe());
        map.put("Aliase", monitorTips.getAliase());
        map.put("DeviceType", monitorTips.getDeviceType());
//        map.put("OnlineList", monitorTips.getOnlineList());
        map.put("LastUpdateTime", monitorTips.getLastUpdateTime());
        map.put("ReceiveTime", monitorTips.getReceiveTime());
        map.put("Simcard", monitorTips.getSimcard());
        map.put("Disabled", monitorTips.isDisabled());
        map.put("Activation", monitorTips.isActivation());
        map.put("OrgName", monitorTips.getOrgName());
        map.put("OrgnizationIds", monitorTips.getOrgnizationIds());
        map.put("OrgnizationId", monitorTips.getOrgnizationId());
        map.put("CatalogueId", monitorTips.getCatalogueId());
//        map.put("Cate", monitorTips.getCate());
        map.put("definetimeString", monitorTips.getDefinetimeString());
        return map;
    }

    @Test
    public void testQueryMonitorTips() {//测试数据查询
        String command = "select * from monitorTips_points WHERE time > now() - 500m";
        QueryResult results = influxDB.query(command);
        if (results.getResults() == null) {
            return;
        }
        List<MonitorTips> lists = new ArrayList<>();
        for (Result result : results.getResults()) {
            List<Series> series = result.getSeries();
            for (Series serie : series) {
                Map<String, String> tags = serie.getTags();
                List<List<Object>> values = serie.getValues();
                List<String> columns = serie.getColumns();
                lists.addAll(getQueryDataMonitorTips(columns, values));
            }
        }
        System.out.println(lists);
//        Assert.assertTrue((!lists.isEmpty()));
//        Assert.assertEquals(2, lists.size());
    }

    @Test
    public void testQuery() {//测试数据查询
        String command = "select * from sys_code WHERE time > now() - 50m";
        QueryResult results = influxDB.query(command);
        if (results.getResults() == null) {
            return;
        }
        List<CodeInfo> lists = new ArrayList<CodeInfo>();
        for (Result result : results.getResults()) {
            List<Series> series = result.getSeries();
            for (Series serie : series) {
                Map<String, String> tags = serie.getTags();
                List<List<Object>> values = serie.getValues();
                List<String> columns = serie.getColumns();
                lists.addAll(getQueryData(columns, values));
            }
        }
        System.out.println(lists);
//        Assert.assertTrue((!lists.isEmpty()));
//        Assert.assertEquals(2, lists.size());
    }

    @Test
    public void testQueryWhere() {//tag 列名 区分大小写
        String command = "select * from android_monitorTips_points where dev_number ='100022857985892'  ";
        QueryResult results = influxDB.query(command);

        if (results.getResults() == null) {
            System.out.println("getResults 为null");
            return;
        }
        List<CodeInfo> lists = new ArrayList<CodeInfo>();
        for (Result result : results.getResults()) {
            List<Series> series = result.getSeries();
            if (series == null) {
                System.out.println("series 为null");
                return;
            }
            for (Series serie : series) {
                List<List<Object>> values = serie.getValues();
                List<String> columns = serie.getColumns();
                lists.addAll(getQueryData(columns, values));
            }
        }
        Assert.assertTrue((!lists.isEmpty()));
        Assert.assertEquals(1, lists.size());
        CodeInfo info = lists.get(0);
        Assert.assertEquals(info.getCode(), "ABC");
    }

    @Test
    public void deletMeasurementData() {
        String command = "delete from sys_code where TAG_CODE='ABC'";
        String err = influxDB.deleteMeasurementData(command);
        Assert.assertNull(err);
    }


    /***整理列名、行数据***/
    private List<MonitorTips> getQueryDataMonitorTips(List<String> columns, List<List<Object>> values) {
        List<MonitorTips> lists = new ArrayList<>();
        System.out.println("columns:  " + columns);
        System.out.println("values : " + values);
        for (List<Object> list : values) {
            MonitorTips monitorTips = new MonitorTips();
            BeanWrapperImpl bean = new BeanWrapperImpl(monitorTips);
            for (int i = 0; i < list.size(); i++) {
                String propertyName = columns.get(i);//字段名
                Object value = list.get(i);//相应字段值
                bean.setPropertyValue(propertyName, value);
            }
            lists.add(monitorTips);
        }
        return lists;
    }

    /***整理列名、行数据***/
    private List<CodeInfo> getQueryData(List<String> columns, List<List<Object>> values) {
        List<CodeInfo> lists = new ArrayList<CodeInfo>();
        for (List<Object> list : values) {
            CodeInfo info = new CodeInfo();
            BeanWrapperImpl bean = new BeanWrapperImpl(info);
            for (int i = 0; i < list.size(); i++) {
//                String propertyName = setColumns(columns.get(i));//字段名
                String propertyName = columns.get(i);//字段名
                Object value = list.get(i);//相应字段值
                bean.setPropertyValue(propertyName, value);
            }
            lists.add(info);
        }
        return lists;
    }

    /***转义字段***/
    private String setColumns(String column) {
        String[] cols = column.split("_");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cols.length; i++) {
            String col = cols[i].toLowerCase();
            if (i != 0) {
                String start = col.substring(0, 1).toUpperCase();
                String end = col.substring(1).toLowerCase();
                col = start + end;
            }
            sb.append(col);
        }
        return sb.toString();
    }


}