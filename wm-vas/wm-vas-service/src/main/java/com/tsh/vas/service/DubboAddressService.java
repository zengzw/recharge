/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Result;
import com.tsh.dubbo.share.api.AddressApi;
import com.tsh.share.vo.AreaVo;

/**
 * dubbo 地址服务 service
 * 
 * 
 * @author zengzw
 * @date 2017年3月7日
 */
@Service
public class DubboAddressService {
    
    private final static Logger logger = LoggerFactory.getLogger(DubboAddressService.class);
    
    @Autowired
    private AddressApi addressApi;
    

    /**
     * 根据省份名称模糊查询
     * 
     * @param result
     * @param name 省份名称
     * @return
     * @throws Exception 
     */
    public AreaVo getAreaByTelAreaName(Result result,String name){
        AreaVo areaVo = null;
        try {
            result = addressApi.getAreaByTelAreaName(result,name);
            areaVo = result.getData();

            logger.info("调用dubbo getAreaByTelAreaName,参数：{}，返回的数据：{}",name, areaVo);
        }catch(Exception e){
            logger.error("调用dubbo getAreaByTelAreaName返回的数据异常",e);
        }
        
        return areaVo;
    }
}
