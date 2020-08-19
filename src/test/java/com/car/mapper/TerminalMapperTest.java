package com.car.mapper;

import com.car.androidbean.AndroidMonitorTip;
import com.car.domain.Alarm;
import com.car.domain.Terminal;
import com.car.mysql.impl.Terminalmpl;
import com.car.redis.JedisUtil6800;
import com.car.service.ReportService;
import com.car.utils.TimeUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/16  21:03
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class TerminalMapperTest {

    @Autowired
    Terminalmpl terminalmpl;

    @Test
    void getTerminalByMap() {
        Map<String, Object> map = new HashMap<>();
        terminalmpl.getTerminalByMap(map);
    }

    @Test
    void insertTerminal() {

    }

    @Test
    void insertTerminalForEach() {
        List<AndroidMonitorTip> androidMonitorTipList = JedisUtil6800.getAndroidMonitorTipsAll(4);
        System.out.println(androidMonitorTipList.size());
        List<Terminal> offlineList = new ArrayList<>();
        for (int i = 2500; i < 5000; i++) {
            AndroidMonitorTip androidMonitorTip = androidMonitorTipList.get(i);
            Terminal alarm = new Terminal();
            BeanUtils.copyProperties(androidMonitorTip, alarm);
            String modelname = ReportService.getModelNameByModelId(androidMonitorTip.getModel_id());
            alarm.setModel_name(modelname);
            String type = ReportService.getModelTypeNameByModeType(androidMonitorTip.getModel_type());
            alarm.setModel_type_name(type);
            System.out.println(modelname + "   " + type);
            alarm.setCreat_time(TimeUtils.getPreTimeByDay(-65, 1));
            alarm.setMf_date(TimeUtils.getPreTimeByDay(-56, 1));
            alarm.setDevice_password("123456");
            offlineList.add(alarm);
        }
        if (offlineList.size() != 0) {
            terminalmpl.insertTerminalForEach(offlineList);
        }

    }

    @Test
    void update() {
    }
}