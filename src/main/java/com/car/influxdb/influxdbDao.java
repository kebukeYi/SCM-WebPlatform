package com.car.influxdb;

import com.alibaba.fastjson.JSONArray;
import com.car.androidbean.AndroidMonitorTip;
import com.car.baiduMapRoute.CitysMap;
import com.car.baiduMapRoute.HttpRoute;
import com.car.domain.MonitorTips;
import com.car.redis.JedisUtil6800;
import com.car.redis.bean.Point;
import com.car.utils.LocationUtils;
import com.car.utils.ReadJSONFile;
import com.car.utils.TimeUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/3/3  12:00
 * @Description
 */
public class influxdbDao {

    private static InfluxDB influxDB;
    private static String username = "admin";//用户名
    private static String password = "admin";//密码
    private static String openurl = "http://127.0.0.1:8086";//连接地址
    //    private static String database = "MonitorTips";//数据库
    private static String database = "AndroidMonitorTips";//数据库
    private static String testDatabase = "TestAndroidMonitorTips";//测试数据库
    private static String measurement = " android_monitorTips_points";//表名
    // private static String measurement = "sys_code";//表名
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static SimpleDateFormat formatGlz = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    static Random r = new Random();

    static {
        setUp();
    }


    public static void setUp() {
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(openurl, username, password);
//            String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT", "defalut", database, "30d", 1);
//            influxDB.query(new Query(command, testDatabase));//存储策略
        }
//        influxDB.deleteDatabase(database);
//        influxDB.createDatabase(database);
    }

    /**
     * 插入模拟数据
     * days - 几天前
     */
    public static void insertPointsByMonitorTips(String DeviceNumber, String beginLatLng, String destLatLng, int days) {
        Map<String, String> tags = new HashMap<String, String>();//索引
        Map<String, Object> fields = null;//字段值
        List<Point> pointList = HttpRoute.getPointsByStartAndEndLocation(beginLatLng, destLatLng);
        if (pointList == null) {
            System.out.println("路线失败，开始下一个城市");
            return;
        }
        MonitorTips monitorTips = JedisUtil6800.getMonitorTipsByDeviceId(DeviceNumber);
        int paths = pointList.size();
        int count = 0;
        System.out.println("总共坐标点是：" + paths + " 个");
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DATE, -days);
        Date dt1 = rightNow.getTime();
        System.out.println("第 " + days + " 天正在插入中...");
        for (int i = 0; i < paths; i++) {
            if (paths > 24000) {
                i = i + 80;
            } else if (paths > 23000) {
                i = i + 76;
            } else if (paths > 22000) {
                i = i + 73;
            } else if (paths > 21000) {
                i = i + 70;
            } else if (paths > 20000) {
                i = i + 67;
            } else if (paths > 19000) {
                i = i + 64;
            } else if (paths > 18000) {
                i = i + 61;
            } else if (paths > 17000) {
                i = i + 57;
            } else if (paths > 16000) {
                i = i + 53;
            } else if (paths > 15000) {
                i = i + 50;
            } else if (paths > 14000) {
                i = i + 46;
            } else if (paths > 13000) {
                i = i + 42;
            } else if (paths > 12000) {
                i = i + 38;
            } else if (paths > 11000) {
                i = i + 34;
            } else if (paths > 10000) {
                i = i + 31;
            } else if (paths > 9000) {
                i = i + 30;
            } else if (paths > 8000) {
                i = i + 26;
            } else if (paths > 7000) {
                i = i + 23;
            } else if (paths > 6000) {
                i = i + 20;
            } else if (paths > 5000) {
                i = i + 19;
            } else if (paths > 4000) {
                i = i + 12;
            } else if (paths > 3000) {
                i = i + 10;
            } else if (paths > 2000) {
                i = i + 6;
            } else if (paths > 1000) {
                i = i + 3;
            } else if (paths > 500) {
                i = i + 1;
            }
            i = i > paths ? paths - 1 : i - 1;
            Point point = pointList.get(i);
            SimpleDateFormat formatGlz = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String date = formatGlz.format(dt1);
            rightNow.add(Calendar.SECOND, 25);
            dt1 = rightNow.getTime();
            monitorTips.setLat(point.getLat());
            monitorTips.setLng(point.getLng());
            monitorTips.setLocationTime(date);
            tags.put("tagNum", monitorTips.getDeviceNumber());
            fields = getMapByMonitorTips(monitorTips);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            influxdbDao.insert(measurement, tags, fields);
            count++;
            tags.clear();
        }
        System.out.println("本次插入坐标点是：" + count + " 个");
    }

    /*
    测试插入数据
     */
    public static void main(String[] args) throws IOException, InterruptedException {
//        HashMap<String, String> tag = new HashMap<>();
//        HashMap<String, Object> filed = new HashMap<>();
//        int count = 0;
//        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
//        for (AndroidMonitorTip androidMonitorTip : androidMonitorTipList) {
//            count++;
//            if (count == 100) {
//                break;
//            }
//            tag.put("dev_number", androidMonitorTip.getDev_number());
//            filed = getFieldMapByAndroidMonitorTips(androidMonitorTip);
//            influxdbDao.insert(measurement, tag, filed);
//        }
//        System.out.println("结束");
        HashMap map = new HashMap();
        map.put("StartTime", "2019-10-18 00:00:00");
        map.put("EndTime", "2020-10-23 00:00:00");
        map.put("DeviceNumber", "618613949812014");
//        System.out.println(queryAndroidMonitorTipsByTime(map));
        randomPoint();

    }

    /**
     * 查询数据
     */
    public static List<MonitorTips> queryMonitorTips(Map<String, String> map) {
        String startTime = map.get("startTime");
        String endTime = map.get("endTime");
        String DeviceNumber = map.get("DeviceNumber");
//        startTime = "2020-03-04T09:00:00Z";
//        endTime = "2020-03-04T09:00:20Z";
//        DeviceNumber = "1111100001";
        // SELECT * FROM "monitorTips_points" where time >= '2019-10-18T00:00:00Z' and time < '2020-10-23T00:00:00Z' and DeviceNumber='1111100207'
        String command = "SELECT * FROM \"monitorTips_points\" where time >= '" + startTime + "' and time < '" + endTime + "' and DeviceNumber= '" + DeviceNumber + "' order by time asc";
        String message = "monitorTips_points" + DeviceNumber;
        String command2 = "SELECT * FROM " + "\"" + message + "\"" + "where time >= '" + startTime + "' and time < '" + endTime + "' and DeviceNumber= '" + DeviceNumber + "' order by time asc";

        QueryResult results = influxdbDao.query(command);
        if (results.getResults() == null) {
            return null;
        }
        List<MonitorTips> lists = new ArrayList<>(1024);
        for (QueryResult.Result result : results.getResults()) {
            List<QueryResult.Series> series = result.getSeries();
            for (QueryResult.Series serie : series) {
                Map<String, String> tags = serie.getTags();
                List<List<Object>> values = serie.getValues();
                List<String> columns = serie.getColumns();
                lists.addAll(getQueryDataMonitorTips(columns, values));
            }
        }
        System.out.println("共有轨迹记录 ：" + lists.size() + " 条");
        return lists;
    }


    /**
     * 最早的模拟历史轨迹数据
     */
    public static void randomPoint() throws IOException, InterruptedException {
        Map<String, String> tags = new HashMap<String, String>();//索引
        Map<String, Object> fields = null;//字段值
        ClassPathResource classPathResource = new ClassPathResource("static/Location/points.json");
        InputStream inputStream = classPathResource.getInputStream();
        List<Point> pointList = JSONArray.parseArray(ReadJSONFile.readFile(inputStream), Point.class);
        AndroidMonitorTip androidMonitorTips = JedisUtil6800.getAndroidMonitorTipsByDeviceId("100022857985892");
        System.out.println(pointList.size());
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DATE, -2);
        Date dt1 = rightNow.getTime();
        for (int i = 0; i < 100; i++) {
            Point point = pointList.get(i);
            String date = formatGlz.format(dt1);
            rightNow.add(Calendar.SECOND, 20);
            dt1 = rightNow.getTime();
            androidMonitorTips.setLat(point.getLat());
            androidMonitorTips.setLng(point.getLng());
            androidMonitorTips.setReceive_time(date);
            androidMonitorTips.setLocation_time(date);
            tags.put("tagNum", androidMonitorTips.getDev_number());
            fields = getFieldMapByAndroidMonitorTips(androidMonitorTips);
            Thread.sleep(50);
            influxdbDao.insert(measurement, tags, fields);
            tags.clear();
        }
        System.out.println("OVER");
    }

    /**
     * 模拟路线
     */
    public static void mainff(String[] args) {
        HashMap geoCoordMap = CitysMap.geoCoordMap;
        Iterator iterator = geoCoordMap.entrySet().iterator(); //11 个
        int days = 1;//天
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String lat = ((String) entry.getValue()).split(",")[1];
            String lng = (((String) entry.getValue()).split(",")[0]);
            Map.Entry entry2 = (Map.Entry) geoCoordMap.entrySet().toArray()[r.nextInt(geoCoordMap.size() - 1)];
            float deslat2 = Float.parseFloat(((String) entry2.getValue()).split(",")[1]);
            float deslng2 = Float.parseFloat(((String) entry2.getValue()).split(",")[0]);
            Map<String, String> jwdest = LocationUtils.randomLonLat(deslng2 - 1, deslng2 + 1, deslat2 - 1, deslat2 + 1);
            String lngdest = jwdest.get("J");
            String latdest = jwdest.get("W");
            String beginLatLng = lat + "," + lng;
            String destLatLng = latdest + "," + lngdest;
            String deviceNumber = "1111100001";
            try {
                Thread.sleep(2000);
                System.out.println("休息3秒钟,马上开始.....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            insertPointsByMonitorTips(deviceNumber, beginLatLng, destLatLng, days);
            days++;
        }
    }


    /**
     * 查询数据
     */
    public static List<AndroidMonitorTip> queryAndroidMonitorTipsByTime(Map<String, String> map) {
        String startTime = map.get("StartTime");
        String endTime = map.get("EndTime");
        String dev_number = map.get("DeviceNumber");
        startTime = TimeUtils.BeijingTimeToGlnzTime(startTime);
        endTime = TimeUtils.BeijingTimeToGlnzTime(endTime);
        System.out.println("StartTime : " + startTime + "EndTime :  " + endTime);
        // SELECT * FROM "monitorTips_points" where time >= '2019-10-18T00:00:00Z' and time < '2020-10-23T00:00:00Z' and dev_number='100107093144618'
        String command = "SELECT * FROM \" android_monitorTips_points\" where time >= '" + startTime + "' and time < '" + endTime + "' and dev_number= '" + dev_number + "' order by time asc";
        QueryResult results = influxdbDao.query(command);
        if (results.getResults() == null) {
            return null;
        }
        List<AndroidMonitorTip> lists = new ArrayList<>();
        for (QueryResult.Result result : results.getResults()) {
            List<QueryResult.Series> series = result.getSeries();
            if (series == null) {
                return null;
            }
            for (QueryResult.Series serie : series) {
                Map<String, String> tags = serie.getTags();
                List<List<Object>> values = serie.getValues();
                List<String> columns = serie.getColumns();
                lists.addAll(getQueryDataAndroidMonitorTips(columns, values));
            }
        }

        System.out.println("共有轨迹记录 ：" + lists.size() + " 条");
        for (AndroidMonitorTip androidMonitorTip : lists) {
            if (androidMonitorTip.getSequenceNo().equals("1")) {//第一条 存反了
                String temp = androidMonitorTip.getLat();//lng
                androidMonitorTip.setLat(androidMonitorTip.getLng());
                androidMonitorTip.setLng(temp);
            }
            androidMonitorTip.setSpeed(r.nextInt(180) + "");
        }
        return lists;
    }

    //   2020-03-03 17:40:14
    public static List<AndroidMonitorTip> queryAndroidMonitorTipsBySequenceNo(Map<String, String> map) {
//        String sequenceNo = map.get("sequenceNo");
        String startTime = map.get("StartTime");
        String endTime = map.get("EndTime");
        startTime = TimeUtils.BeijingTimeToGlnzTime(startTime);
        endTime = TimeUtils.BeijingTimeToGlnzTime(endTime);
        System.out.println("StartTime : " + startTime + "EndTime :  " + endTime);

//        startTime = "2020-03-15T09:00:00Z";
//        endTime = "2020-03-20T09:00:20Z";
        String dev_number = map.get("DeviceNumber");
        // SELECT * FROM "monitorTips_points" where time >= '2019-10-18T00:00:00Z' and time < '2020-10-23T00:00:00Z' and dev_number='100107093144618'
//        String command = "SELECT * FROM \"android_monitorTips_points\" where sequenceNo='" + sequenceNo + "'";
        String command = "SELECT * FROM \"android_monitorTips_points\" where time >= '" + startTime + "' and time < '" + endTime + "' and dev_number= '" + dev_number + "' order by time asc";
        QueryResult results = influxdbDao.query(command);
        if (results.getResults() == null) {
            return null;
        }
        List<AndroidMonitorTip> lists = new ArrayList<>();
        for (QueryResult.Result result : results.getResults()) {
            List<QueryResult.Series> series = result.getSeries();
            for (QueryResult.Series serie : series) {
                Map<String, String> tags = serie.getTags();
                List<List<Object>> values = serie.getValues();
                List<String> columns = serie.getColumns();
                lists.addAll(getQueryDataAndroidMonitorTips(columns, values));
            }
        }

        System.out.println("共有轨迹记录 ：" + lists.size() + " 条");
        for (AndroidMonitorTip androidMonitorTip : lists) {
            if (androidMonitorTip.getSequenceNo().equals("1")) {//第一条 存反了
                String temp = androidMonitorTip.getLat();//lng
                androidMonitorTip.setLat(androidMonitorTip.getLng());
                androidMonitorTip.setLng(temp);
            }
            androidMonitorTip.setSpeed(r.nextInt(180) + "");
        }
        return lists;
    }


    /**
     * AndroidMonitorTips 转map
     */
    public static HashMap<String, Object> getFieldMapByAndroidMonitorTips(AndroidMonitorTip androidMonitorTip) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("imei", androidMonitorTip.getImei());
        map.put("dev_number", androidMonitorTip.getDev_number());
        map.put("lng", androidMonitorTip.getLng());
        map.put("lat", androidMonitorTip.getLat());
        map.put("location_time", androidMonitorTip.getLocation_time());
        map.put("stop_minutes", androidMonitorTip.getStop_minutes());
        map.put("sequenceNo", androidMonitorTip.getSequenceNo());//每此更新递增
        map.put("receive_time", androidMonitorTip.getReceive_time());
        map.put("speed", androidMonitorTip.getSpeed());
        map.put("location_type", androidMonitorTip.getLocation_type());
        map.put("address", androidMonitorTip.getAddress());
        return map;
    }

    /**
     * MonitorTips 转map
     */
    public static Map<String, Object> getMapByMonitorTips(MonitorTips monitorTips) {
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


    /***整理列名、行数据***/
    private static List<MonitorTips> getQueryDataMonitorTips(List<String> columns, List<List<Object>> values) {
        List<MonitorTips> lists = new ArrayList<>();
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
    private static List<AndroidMonitorTip> getQueryDataAndroidMonitorTips(List<String> columns, List<List<Object>> values) {
        List<AndroidMonitorTip> lists = new ArrayList<>();
        for (List<Object> list : values) {
            AndroidMonitorTip monitorTips = new AndroidMonitorTip();
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


    /**
     * 插入
     *
     * @param measurement 表
     * @param tags        标签
     * @param fields      字段
     */
    public static void insert(String measurement, Map<String, String> tags, Map<String, Object> fields) {
        org.influxdb.dto.Point.Builder builder = org.influxdb.dto.Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);
        setUp();
        influxDB.write(testDatabase, "", builder.build());
    }

    /**
     * 查询
     *
     * @param command 查询语句
     * @return
     */
    public static QueryResult query(String command) {
        setUp();
        return influxDB.query(new Query(command, database));
    }


    /**
     * 删除
     *
     * @param command 删除语句
     * @return 返回错误信息
     */
    public static String deleteMeasurementData(String command) {
        QueryResult result = influxDB.query(new Query(command, database));
        return result.getError();
    }


}
