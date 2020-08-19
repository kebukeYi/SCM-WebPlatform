package com.car.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Auto-generated: 2020-01-19 7:27:52
 * {"Status":1,"Result":{"Data":[{"StopMinutes":null,"StopEndTime":null,"Lng":126.649950,"Lat":45.788304,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:08:27.23","LocationTime":"2020-01-01T18:08:24","LocationType":1,"Online":false,"Direct":316.65,"Electricity":90,"Satellites":9,"Signal":100,"Speed":10.60,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649734,"Lat":45.788412,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:08:37.233","LocationTime":"2020-01-01T18:08:34","LocationType":1,"Online":false,"Direct":262.05,"Electricity":90,"Satellites":11,"Signal":100,"Speed":0.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649481,"Lat":45.788768,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:08:57.23","LocationTime":"2020-01-01T18:08:54","LocationType":1,"Online":false,"Direct":340.40,"Electricity":90,"Satellites":11,"Signal":100,"Speed":16.60,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649305,"Lat":45.789151,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:09:07.31","LocationTime":"2020-01-01T18:09:04","LocationType":1,"Online":false,"Direct":358.27,"Electricity":90,"Satellites":12,"Signal":70,"Speed":10.10,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649115,"Lat":45.789507,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:09:17.25","LocationTime":"2020-01-01T18:09:14","LocationType":1,"Online":false,"Direct":340.93,"Electricity":90,"Satellites":12,"Signal":70,"Speed":10.30,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649432,"Lat":45.790094,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:09:27.25","LocationTime":"2020-01-01T18:09:24","LocationType":1,"Online":false,"Direct":42.41,"Electricity":90,"Satellites":12,"Signal":100,"Speed":22.60,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650061,"Lat":45.790608,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:09:37.27","LocationTime":"2020-01-01T18:09:34","LocationType":1,"Online":false,"Direct":40.62,"Electricity":90,"Satellites":12,"Signal":100,"Speed":13.40,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650286,"Lat":45.790633,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:09:47.273","LocationTime":"2020-01-01T18:09:44","LocationType":1,"Online":false,"Direct":64.62,"Electricity":90,"Satellites":12,"Signal":100,"Speed":0.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650179,"Lat":45.791017,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:10:27.293","LocationTime":"2020-01-01T18:10:24","LocationType":1,"Online":false,"Direct":322.32,"Electricity":90,"Satellites":13,"Signal":100,"Speed":27.80,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649655,"Lat":45.791534,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:10:37.293","LocationTime":"2020-01-01T18:10:34","LocationType":1,"Online":false,"Direct":340.73,"Electricity":90,"Satellites":13,"Signal":100,"Speed":11.80,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650218,"Lat":45.791993,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:10:47.437","LocationTime":"2020-01-01T18:10:44","LocationType":1,"Online":false,"Direct":43.48,"Electricity":90,"Satellites":13,"Signal":70,"Speed":25.40,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650794,"Lat":45.792359,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:10:57.38","LocationTime":"2020-01-01T18:10:54","LocationType":1,"Online":false,"Direct":36.03,"Electricity":90,"Satellites":13,"Signal":63,"Speed":13.80,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650758,"Lat":45.792532,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:11:07.28","LocationTime":"2020-01-01T18:11:05","LocationType":1,"Online":false,"Direct":284.27,"Electricity":90,"Satellites":13,"Signal":87,"Speed":5.30,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650325,"Lat":45.792605,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:11:17.517","LocationTime":"2020-01-01T18:11:15","LocationType":1,"Online":false,"Direct":239.83,"Electricity":90,"Satellites":13,"Signal":87,"Speed":3.90,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":4,"StopEndTime":"2020-01-01T18:15:35","Lng":126.650253,"Lat":45.792595,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:11:28.103","LocationTime":"2020-01-01T18:11:25","LocationType":1,"Online":false,"Direct":205.62,"Electricity":90,"Satellites":12,"Signal":77,"Speed":0.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650753,"Lat":45.792660,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:15:38.27","LocationTime":"2020-01-01T18:15:35","LocationType":1,"Online":false,"Direct":338.90,"Electricity":90,"Satellites":12,"Signal":75,"Speed":14.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650442,"Lat":45.792920,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:15:48.487","LocationTime":"2020-01-01T18:15:45","LocationType":1,"Online":false,"Direct":305.56,"Electricity":90,"Satellites":12,"Signal":75,"Speed":10.30,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650275,"Lat":45.793039,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:15:58.71","LocationTime":"2020-01-01T18:15:55","LocationType":1,"Online":false,"Direct":310.16,"Electricity":90,"Satellites":12,"Signal":63,"Speed":4.80,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649998,"Lat":45.792987,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:16:08.29","LocationTime":"2020-01-01T18:16:05","LocationType":1,"Online":false,"Direct":233.20,"Electricity":90,"Satellites":12,"Signal":63,"Speed":11.90,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649508,"Lat":45.792865,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:16:18.367","LocationTime":"2020-01-01T18:16:15","LocationType":1,"Online":false,"Direct":220.68,"Electricity":90,"Satellites":12,"Signal":100,"Speed":16.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649008,"Lat":45.792703,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:16:28.33","LocationTime":"2020-01-01T18:16:25","LocationType":1,"Online":false,"Direct":233.18,"Electricity":90,"Satellites":13,"Signal":100,"Speed":15.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.648711,"Lat":45.792405,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:16:38.29","LocationTime":"2020-01-01T18:16:35","LocationType":1,"Online":false,"Direct":180.34,"Electricity":90,"Satellites":13,"Signal":100,"Speed":12.40,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649058,"Lat":45.792068,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:16:49.013","LocationTime":"2020-01-01T18:16:45","LocationType":1,"Online":false,"Direct":142.73,"Electricity":90,"Satellites":12,"Signal":100,"Speed":13.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649230,"Lat":45.791647,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:16:58.27","LocationTime":"2020-01-01T18:16:55","LocationType":1,"Online":false,"Direct":146.66,"Electricity":90,"Satellites":12,"Signal":100,"Speed":15.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649693,"Lat":45.791371,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:17:08.273","LocationTime":"2020-01-01T18:17:05","LocationType":1,"Online":false,"Direct":139.96,"Electricity":90,"Satellites":13,"Signal":100,"Speed":15.60,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649967,"Lat":45.791095,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:17:18.327","LocationTime":"2020-01-01T18:17:15","LocationType":1,"Online":false,"Direct":142.21,"Electricity":90,"Satellites":14,"Signal":100,"Speed":7.10,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650049,"Lat":45.791073,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:17:28.307","LocationTime":"2020-01-01T18:17:26","LocationType":1,"Online":false,"Direct":303.61,"Electricity":90,"Satellites":14,"Signal":100,"Speed":0.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650293,"Lat":45.790905,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:18:08.31","LocationTime":"2020-01-01T18:18:05","LocationType":1,"Online":false,"Direct":148.28,"Electricity":90,"Satellites":13,"Signal":100,"Speed":13.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650741,"Lat":45.790743,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:18:18.313","LocationTime":"2020-01-01T18:18:15","LocationType":1,"Online":false,"Direct":85.85,"Electricity":90,"Satellites":15,"Signal":100,"Speed":20.40,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.651624,"Lat":45.790976,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:18:28.31","LocationTime":"2020-01-01T18:18:25","LocationType":1,"Online":false,"Direct":76.73,"Electricity":90,"Satellites":15,"Signal":100,"Speed":24.90,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.652575,"Lat":45.791330,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:18:38.33","LocationTime":"2020-01-01T18:18:36","LocationType":1,"Online":false,"Direct":73.62,"Electricity":90,"Satellites":15,"Signal":100,"Speed":23.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.653289,"Lat":45.791520,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:18:48.35","LocationTime":"2020-01-01T18:18:46","LocationType":1,"Online":false,"Direct":73.04,"Electricity":90,"Satellites":16,"Signal":100,"Speed":18.70,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.653882,"Lat":45.791644,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:18:58.333","LocationTime":"2020-01-01T18:18:56","LocationType":1,"Online":false,"Direct":69.17,"Electricity":90,"Satellites":16,"Signal":91,"Speed":16.90,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.654722,"Lat":45.791871,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:19:08.33","LocationTime":"2020-01-01T18:19:06","LocationType":1,"Online":false,"Direct":67.99,"Electricity":90,"Satellites":16,"Signal":91,"Speed":27.40,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.655560,"Lat":45.792190,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:19:20.427","LocationTime":"2020-01-01T18:19:16","LocationType":1,"Online":false,"Direct":62.53,"Electricity":90,"Satellites":16,"Signal":70,"Speed":27.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.656385,"Lat":45.792440,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:19:38.91","LocationTime":"2020-01-01T18:19:26","LocationType":1,"Online":false,"Direct":71.53,"Electricity":90,"Satellites":16,"Signal":70,"Speed":22.10,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.656676,"Lat":45.792492,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:19:39.49","LocationTime":"2020-01-01T18:19:36","LocationType":1,"Online":false,"Direct":174.82,"Electricity":90,"Satellites":15,"Signal":100,"Speed":2.10,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.656681,"Lat":45.792507,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:19:48.41","LocationTime":"2020-01-01T18:19:46","LocationType":1,"Online":false,"Direct":171.56,"Electricity":90,"Satellites":14,"Signal":100,"Speed":0.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.656881,"Lat":45.792571,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:20:28.513","LocationTime":"2020-01-01T18:20:26","LocationType":1,"Online":false,"Direct":67.52,"Electricity":90,"Satellites":16,"Signal":100,"Speed":15.30,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.657458,"Lat":45.792733,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:20:38.53","LocationTime":"2020-01-01T18:20:36","LocationType":1,"Online":false,"Direct":68.91,"Electricity":90,"Satellites":16,"Signal":100,"Speed":19.90,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.657722,"Lat":45.792799,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:20:48.293","LocationTime":"2020-01-01T18:20:46","LocationType":1,"Online":false,"Direct":110.12,"Electricity":90,"Satellites":15,"Signal":100,"Speed":0.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.657869,"Lat":45.792872,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:20:58.473","LocationTime":"2020-01-01T18:20:56","LocationType":1,"Online":false,"Direct":75.74,"Electricity":90,"Satellites":16,"Signal":100,"Speed":10.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.658469,"Lat":45.793122,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:21:08.313","LocationTime":"2020-01-01T18:21:06","LocationType":1,"Online":false,"Direct":69.87,"Electricity":90,"Satellites":16,"Signal":100,"Speed":26.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.659355,"Lat":45.793400,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:21:18.653","LocationTime":"2020-01-01T18:21:16","LocationType":1,"Online":false,"Direct":63.65,"Electricity":100,"Satellites":15,"Signal":99,"Speed":24.30,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.659849,"Lat":45.793557,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:21:30.43","LocationTime":"2020-01-01T18:21:26","LocationType":1,"Online":false,"Direct":68.71,"Electricity":90,"Satellites":15,"Signal":99,"Speed":3.40,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.660192,"Lat":45.793656,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:21:49.51","LocationTime":"2020-01-01T18:21:46","LocationType":1,"Online":false,"Direct":64.82,"Electricity":90,"Satellites":15,"Signal":85,"Speed":3.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.660190,"Lat":45.793651,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:21:59.08","LocationTime":"2020-01-01T18:21:56","LocationType":1,"Online":false,"Direct":189.73,"Electricity":90,"Satellites":15,"Signal":90,"Speed":0.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.660411,"Lat":45.793758,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:22:29.497","LocationTime":"2020-01-01T18:22:27","LocationType":1,"Online":false,"Direct":50.18,"Electricity":100,"Satellites":14,"Signal":95,"Speed":11.70,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.660648,"Lat":45.794052,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:22:47.53","LocationTime":"2020-01-01T18:22:37","LocationType":1,"Online":false,"Direct":329.85,"Electricity":100,"Satellites":14,"Signal":97,"Speed":10.10,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.660620,"Lat":45.794668,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:22:54.763","LocationTime":"2020-01-01T18:22:47","LocationType":1,"Online":false,"Direct":342.26,"Electricity":90,"Satellites":13,"Signal":97,"Speed":19.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.660326,"Lat":45.795308,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:22:59.607","LocationTime":"2020-01-01T18:22:57","LocationType":1,"Online":false,"Direct":346.27,"Electricity":90,"Satellites":15,"Signal":97,"Speed":27.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.659800,"Lat":45.796053,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:23:10.587","LocationTime":"2020-01-01T18:23:07","LocationType":1,"Online":false,"Direct":341.81,"Electricity":90,"Satellites":15,"Signal":36,"Speed":35.80,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.659771,"Lat":45.796821,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:23:20.29","LocationTime":"2020-01-01T18:23:17","LocationType":1,"Online":false,"Direct":49.49,"Electricity":90,"Satellites":15,"Signal":41,"Speed":28.60,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.660696,"Lat":45.796810,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:23:30.567","LocationTime":"2020-01-01T18:23:27","LocationType":1,"Online":false,"Direct":125.96,"Electricity":90,"Satellites":16,"Signal":41,"Speed":26.10,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.660899,"Lat":45.796155,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:23:40.287","LocationTime":"2020-01-01T18:23:37","LocationType":1,"Online":false,"Direct":195.12,"Electricity":90,"Satellites":16,"Signal":48,"Speed":25.60,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.660134,"Lat":45.795792,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:23:50.547","LocationTime":"2020-01-01T18:23:47","LocationType":1,"Online":false,"Direct":257.85,"Electricity":90,"Satellites":16,"Signal":48,"Speed":27.40,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.659307,"Lat":45.796202,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:24:00.287","LocationTime":"2020-01-01T18:23:57","LocationType":1,"Online":false,"Direct":329.30,"Electricity":90,"Satellites":15,"Signal":38,"Speed":30.10,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.658811,"Lat":45.796906,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:24:10.53","LocationTime":"2020-01-01T18:24:07","LocationType":1,"Online":false,"Direct":330.98,"Electricity":100,"Satellites":14,"Signal":76,"Speed":30.60,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.658334,"Lat":45.797645,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:24:20.353","LocationTime":"2020-01-01T18:24:17","LocationType":1,"Online":false,"Direct":335.93,"Electricity":100,"Satellites":15,"Signal":76,"Speed":30.70,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.657900,"Lat":45.798306,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:24:33.05","LocationTime":"2020-01-01T18:24:27","LocationType":1,"Online":false,"Direct":334.72,"Electricity":100,"Satellites":16,"Signal":50,"Speed":33.70,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.657370,"Lat":45.799106,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:24:52.943","LocationTime":"2020-01-01T18:24:37","LocationType":1,"Online":false,"Direct":336.13,"Electricity":90,"Satellites":16,"Signal":35,"Speed":39.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.656776,"Lat":45.800038,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:24:52.96","LocationTime":"2020-01-01T18:24:47","LocationType":1,"Online":false,"Direct":336.38,"Electricity":90,"Satellites":16,"Signal":91,"Speed":41.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.656067,"Lat":45.801113,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:25:00.35","LocationTime":"2020-01-01T18:24:58","LocationType":1,"Online":false,"Direct":335.20,"Electricity":100,"Satellites":16,"Signal":91,"Speed":44.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.655419,"Lat":45.802135,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:25:10.667","LocationTime":"2020-01-01T18:25:08","LocationType":1,"Online":false,"Direct":335.98,"Electricity":90,"Satellites":16,"Signal":91,"Speed":43.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.654814,"Lat":45.803129,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:25:20.503","LocationTime":"2020-01-01T18:25:18","LocationType":1,"Online":false,"Direct":337.36,"Electricity":90,"Satellites":16,"Signal":91,"Speed":45.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.654148,"Lat":45.804194,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:25:30.427","LocationTime":"2020-01-01T18:25:28","LocationType":1,"Online":false,"Direct":336.89,"Electricity":90,"Satellites":16,"Signal":100,"Speed":46.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.653510,"Lat":45.805298,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:25:40.387","LocationTime":"2020-01-01T18:25:38","LocationType":1,"Online":false,"Direct":338.07,"Electricity":90,"Satellites":16,"Signal":100,"Speed":45.10,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.652900,"Lat":45.806332,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:25:51.907","LocationTime":"2020-01-01T18:25:48","LocationType":1,"Online":false,"Direct":336.38,"Electricity":90,"Satellites":16,"Signal":100,"Speed":45.70,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.652247,"Lat":45.807397,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:26:00.723","LocationTime":"2020-01-01T18:25:58","LocationType":1,"Online":false,"Direct":337.36,"Electricity":90,"Satellites":16,"Signal":100,"Speed":45.80,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.651626,"Lat":45.808464,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:26:11.207","LocationTime":"2020-01-01T18:26:08","LocationType":1,"Online":false,"Direct":338.15,"Electricity":90,"Satellites":16,"Signal":100,"Speed":46.60,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650996,"Lat":45.809546,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:26:29.02","LocationTime":"2020-01-01T18:26:18","LocationType":1,"Online":false,"Direct":338.28,"Electricity":90,"Satellites":16,"Signal":100,"Speed":46.30,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.650386,"Lat":45.810628,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:26:31.06","LocationTime":"2020-01-01T18:26:28","LocationType":1,"Online":false,"Direct":338.59,"Electricity":90,"Satellites":16,"Signal":95,"Speed":46.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649808,"Lat":45.811672,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:26:41.423","LocationTime":"2020-01-01T18:26:38","LocationType":1,"Online":false,"Direct":339.83,"Electricity":90,"Satellites":16,"Signal":100,"Speed":43.70,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.649284,"Lat":45.812620,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:26:51.363","LocationTime":"2020-01-01T18:26:48","LocationType":1,"Online":false,"Direct":338.88,"Electricity":90,"Satellites":16,"Signal":100,"Speed":39.70,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.648786,"Lat":45.813548,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:27:01.423","LocationTime":"2020-01-01T18:26:58","LocationType":1,"Online":false,"Direct":338.57,"Electricity":90,"Satellites":16,"Signal":100,"Speed":40.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.648205,"Lat":45.814578,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:27:11.383","LocationTime":"2020-01-01T18:27:09","LocationType":1,"Online":false,"Direct":339.75,"Electricity":90,"Satellites":16,"Signal":100,"Speed":40.30,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.647657,"Lat":45.815534,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:27:23.743","LocationTime":"2020-01-01T18:27:19","LocationType":1,"Online":false,"Direct":338.17,"Electricity":90,"Satellites":16,"Signal":100,"Speed":42.30,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.647091,"Lat":45.816577,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:27:31.48","LocationTime":"2020-01-01T18:27:29","LocationType":1,"Online":false,"Direct":338.16,"Electricity":90,"Satellites":15,"Signal":100,"Speed":46.80,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.646580,"Lat":45.817596,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:27:49.903","LocationTime":"2020-01-01T18:27:39","LocationType":1,"Online":false,"Direct":339.43,"Electricity":90,"Satellites":15,"Signal":100,"Speed":42.30,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.646027,"Lat":45.818525,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:27:56.1","LocationTime":"2020-01-01T18:27:49","LocationType":1,"Online":false,"Direct":339.04,"Electricity":90,"Satellites":14,"Signal":70,"Speed":34.70,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.645551,"Lat":45.819391,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:28:01.46","LocationTime":"2020-01-01T18:27:59","LocationType":1,"Online":false,"Direct":341.67,"Electricity":90,"Satellites":14,"Signal":31,"Speed":39.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.645017,"Lat":45.820280,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:28:12.623","LocationTime":"2020-01-01T18:28:09","LocationType":1,"Online":false,"Direct":337.74,"Electricity":90,"Satellites":15,"Signal":100,"Speed":35.40,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.644591,"Lat":45.820957,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:28:22.723","LocationTime":"2020-01-01T18:28:19","LocationType":1,"Online":false,"Direct":335.17,"Electricity":90,"Satellites":15,"Signal":100,"Speed":26.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.644297,"Lat":45.821463,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:28:32.5","LocationTime":"2020-01-01T18:28:29","LocationType":1,"Online":false,"Direct":339.32,"Electricity":90,"Satellites":13,"Signal":100,"Speed":25.70,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.643977,"Lat":45.822126,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:28:42.567","LocationTime":"2020-01-01T18:28:39","LocationType":1,"Online":false,"Direct":344.00,"Electricity":90,"Satellites":15,"Signal":100,"Speed":29.10,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.643629,"Lat":45.822804,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:28:52.423","LocationTime":"2020-01-01T18:28:49","LocationType":1,"Online":false,"Direct":338.88,"Electricity":90,"Satellites":15,"Signal":100,"Speed":30.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.643200,"Lat":45.823541,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:29:02.667","LocationTime":"2020-01-01T18:28:59","LocationType":1,"Online":false,"Direct":337.84,"Electricity":90,"Satellites":15,"Signal":100,"Speed":30.10,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.642804,"Lat":45.824204,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:29:12.643","LocationTime":"2020-01-01T18:29:09","LocationType":1,"Online":false,"Direct":337.95,"Electricity":90,"Satellites":15,"Signal":100,"Speed":28.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.642434,"Lat":45.824936,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:29:23.307","LocationTime":"2020-01-01T18:29:19","LocationType":1,"Online":false,"Direct":343.55,"Electricity":90,"Satellites":15,"Signal":100,"Speed":32.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.642113,"Lat":45.825607,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:29:32.627","LocationTime":"2020-01-01T18:29:29","LocationType":1,"Online":false,"Direct":319.38,"Electricity":90,"Satellites":15,"Signal":100,"Speed":19.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.641702,"Lat":45.825550,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:29:42.847","LocationTime":"2020-01-01T18:29:39","LocationType":1,"Online":false,"Direct":248.50,"Electricity":90,"Satellites":15,"Signal":100,"Speed":8.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.641724,"Lat":45.825133,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:30:02.667","LocationTime":"2020-01-01T18:29:59","LocationType":1,"Online":false,"Direct":163.03,"Electricity":90,"Satellites":15,"Signal":100,"Speed":16.70,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.641971,"Lat":45.824566,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:30:13.39","LocationTime":"2020-01-01T18:30:10","LocationType":1,"Online":false,"Direct":160.98,"Electricity":90,"Satellites":15,"Signal":100,"Speed":28.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.642444,"Lat":45.823728,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:30:22.947","LocationTime":"2020-01-01T18:30:20","LocationType":1,"Online":false,"Direct":157.81,"Electricity":90,"Satellites":15,"Signal":100,"Speed":37.60,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.642936,"Lat":45.822883,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:30:32.49","LocationTime":"2020-01-01T18:30:30","LocationType":1,"Online":false,"Direct":159.26,"Electricity":90,"Satellites":15,"Signal":100,"Speed":35.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.643324,"Lat":45.822168,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:30:42.557","LocationTime":"2020-01-01T18:30:40","LocationType":1,"Online":false,"Direct":157.26,"Electricity":90,"Satellites":16,"Signal":100,"Speed":24.50,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.643320,"Lat":45.821805,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:30:53.503","LocationTime":"2020-01-01T18:30:50","LocationType":1,"Online":false,"Direct":232.18,"Electricity":90,"Satellites":16,"Signal":100,"Speed":19.00,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.642602,"Lat":45.821672,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:31:09.56","LocationTime":"2020-01-01T18:31:00","LocationType":1,"Online":false,"Direct":260.11,"Electricity":90,"Satellites":15,"Signal":100,"Speed":24.40,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.641650,"Lat":45.821499,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:31:18.263","LocationTime":"2020-01-01T18:31:10","LocationType":1,"Online":false,"Direct":248.56,"Electricity":90,"Satellites":16,"Signal":100,"Speed":26.20,"ModelType":0,"Mileage":null,"OuterVoltage":null},{"StopMinutes":null,"StopEndTime":null,"Lng":126.640638,"Lat":45.821195,"DeviceNumber":"181219310018619","DeviceName":null,"Status":"NotDisassembly,
 * ContinueLocation, ACCOFF, OilON,
 * EleON","Alarm":"None","StopTime":null,"ReceiveTime":"2020-01-01T18:31:25.3","LocationTime":"2020-01-01T18:31:20","LocationType":1,"Online":false,"Direct":246.04,"Electricity":90,"Satellites":16,"Signal":100,"Speed":32.10,"ModelType":0,"Mileage":null,"OuterVoltage":null}],"MaxDateTime":"2020-01-01
 * 18:31:20"},"ErrorMessage":null}
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class History {

    private String stop_minutes;
    private String StopEndTime;
    private String lng;
    private String lat;
    private String dev_number;
    private String dev_name;
    private String status;
    private String alarm_type_id;
    private String stop_time;
    private String receive_time;
    private String location_time;
    private String location_type;
    private boolean online;
    private double direct;
    private int battery_life;
    private int satellites;
    private int signal;
    private String speed;
    private String model_type;
    private String mileage;
    private String voltage;

    @JsonProperty("StopMinutes")
    public String getStop_minutes() {
        return stop_minutes;
    }

    public void setStop_minutes(String stop_minutes) {
        this.stop_minutes = stop_minutes;
    }
    @JsonProperty("StopEndTime")
    public String getStopEndTime() {
        return StopEndTime;
    }

    public void setStopEndTime(String stopEndTime) {
        StopEndTime = stopEndTime;
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
    @JsonProperty("DeviceNumber")
    public String getDev_number() {
        return dev_number;
    }

    public void setDev_number(String dev_number) {
        this.dev_number = dev_number;
    }
    @JsonProperty("DeviceName")
    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }
    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @JsonProperty("Alarm")
    public String getAlarm_type_id() {
        return alarm_type_id;
    }

    public void setAlarm_type_id(String alarm_type_id) {
        this.alarm_type_id = alarm_type_id;
    }
    @JsonProperty("StopTime")
    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }
    @JsonProperty("ReceiveTime")
    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }
    @JsonProperty("LocationTime")
    public String getLocation_time() {
        return location_time;
    }

    public void setLocation_time(String location_time) {
        this.location_time = location_time;
    }
    @JsonProperty("LocationType")
    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }
    @JsonProperty("Online")
    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
    @JsonProperty("Direct")
    public double getDirect() {
        return direct;
    }

    public void setDirect(double direct) {
        this.direct = direct;
    }
    @JsonProperty("Electricity")
    public int getBattery_life() {
        return battery_life;
    }

    public void setBattery_life(int battery_life) {
        this.battery_life = battery_life;
    }
    @JsonProperty("Satellites")
    public int getSatellites() {
        return satellites;
    }

    public void setSatellites(int satellites) {
        this.satellites = satellites;
    }
    @JsonProperty("Signal")
    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }
    @JsonProperty("Speed")
    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
    @JsonProperty("ModelType")
    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }
    @JsonProperty("Mileage")
    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }
    @JsonProperty("OuterVoltage")
    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }
}