package com.car.mapper;

import com.car.domain.Car;

import java.util.List;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/8  21:12
 * @Description
 */
public interface CarManagementMapper {

    void insertCar(Map<String, Object> map);

    void del(String id);

    List<Car> getCarListFromMap(Map<String, Object> map);

    void insertCar(Car car);

    void update(Map<String, Object> map);

    //车辆绑定终端
    void AssociateVehicle(Map<String, Object> map);

    //车辆解除绑定
    void DisassociateVehicle(Map<String, Object> map);

    void insertCarForEach(List<Car> cars);

}
