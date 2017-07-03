package com.tsh.traintickets.dal.service.kuyou.httphandler.config;

import com.tsh.traintickets.dal.service.diamond.TshDiamondClient;
import org.apache.commons.lang.StringUtils;

/**
 * 动态获取配置参数
 * Created by Administrator on 2016/12/15 015.
 */
public class DynamicParamsUtils {

    private DynamicParamsUtils(){}

    public static String getParam(DynamicParamsEnums enums){
        String value = TshDiamondClient.getInstance().getConfig(enums.toString());
        if(StringUtils.isNotEmpty(value)){
            return value;
        }
        return "";
    }
}
