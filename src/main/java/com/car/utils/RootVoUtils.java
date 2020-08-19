package com.car.utils;


import com.car.vo.RootVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/2/1  12:30
 * @Description
 */
public class RootVoUtils {

    public static RootVo success(Object object) {
        RootVo rootVo = new RootVo();
        rootVo.setResult(object);
        rootVo.setErrorMessage(null);
        rootVo.setStatus(1);
        return rootVo;
    }

    public static RootVo isNull() {
        RootVo rootVo = new RootVo();
        List list=new ArrayList();
        rootVo.setResult(list);
        rootVo.setErrorMessage(null);
        rootVo.setStatus(1);
        return rootVo;
    }


    public static RootVo error(Integer code, String msg) {
        RootVo resultVO = new RootVo();
        resultVO.setStatus(code);
        resultVO.setErrorMessage(msg);
        return resultVO;
    }

}
