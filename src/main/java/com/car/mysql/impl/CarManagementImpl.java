package com.car.mysql.impl;

import com.alibaba.fastjson.JSONArray;
import com.car.domain.Car;
import com.car.domain.Cars;
import com.car.mapper.CarManagementMapper;
import com.car.service.CommonService;
import com.car.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:49
 * @Description
 */
@Service
public class CarManagementImpl {

    @Autowired
    private CarManagementMapper carManagementMapper;

    @Autowired
    private CommonService commonService;

    /**
     * Brand: "0"
     * CarColor: "0"
     * CarType: ""
     * Id: "4765"
     * Model: "0"
     * OrgId: "1583500780272267472"
     * Owner: "王先生"
     * Phone: "18732569857"
     * PlateNo: "D-125744"
     * VIN: "123222222"
     */
    public void insertCar(Map<String, Object> map) {
        map = commonService.getIdByMap(map);
        String CarColor = (String) map.get("CarColor");
        String Brand = (String) map.get("Brand");
        String CarType = (String) map.get("CarType");
        String Model = (String) map.get("Model");


        map.put("CarColor", ReportService.getCarColorById(CarColor));
        map.put("Brand", ReportService.getCarBrandById(Brand));
        map.put("CarType", ReportService.getCarCarTypeNameByCarModel(CarType));
        map.put("Model", ReportService.getCarModelById(Model));

        carManagementMapper.insertCar(map);
    }

    /**
     * Brand: "0"
     * CarColor: "0"
     * CarType: ""
     * Id: "4765"
     * Model: "0"
     * OrgId: "1583500780272267472"
     * Owner: "王先生"
     * Phone: "18732569857"
     * PlateNo: "D-125744"
     * VIN: "123222222"
     */
    public void update(Map<String, Object> map) {
        map = commonService.getIdByMap(map);
        String CarColor = (String) map.get("CarColor");
        String Brand = (String) map.get("Brand");
        String CarType = (String) map.get("CarType");
        String Model = (String) map.get("Model");


        map.put("CarColor", ReportService.getCarColorById(CarColor));
        map.put("Brand", ReportService.getCarBrandById(Brand));
        map.put("CarType", ReportService.getCarCarTypeNameByCarModel(CarType));
        map.put("Model", ReportService.getCarModelById(Model));

        carManagementMapper.update(map);
    }

    //车辆管理
    public Cars getCarsListFromMap(Map<String, Object> map) {
        Cars cars = new Cars();
        map = commonService.getIdByMap(map);
        List<Car> carList = carManagementMapper.getCarListFromMap(map);
        cars.setTotal(carList.size());
        cars.setLimit((Integer) map.get("limit"));
        cars.setPage((Integer) map.get("page"));
        cars.setData(carList);
        return cars;
    }

    public void insertCar(Car car) {
        carManagementMapper.insertCar(car);
    }

    public void insertCarForEach(List<Car> cars) {
        carManagementMapper.insertCarForEach(cars);
    }

    public void AssociateVehicle(Map<String, Object> map) {
        carManagementMapper.AssociateVehicle(map);
    }

    public void DisassociateVehicle(Map<String, Object> map) {
        carManagementMapper.DisassociateVehicle(map);
    }

    //删除车辆
    @Transactional(rollbackFor = Exception.class)
    public void Remove(JSONArray jsonArray) {
        for (Object Id : jsonArray) {
            carManagementMapper.del(Id + "");
        }
    }
}
