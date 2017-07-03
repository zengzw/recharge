/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.cache;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dtds.platform.data.redis.RedisSlave;
import com.dtds.platform.util.bean.Result;
import com.tsh.openpf.vo.ServiceRegisterVo;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.service.CommomService;

/**
 *  服务商提供方，缓存类
 *  
 *  
 * @author zengzw
 * @date 2017年3月6日
 */
@Service
public class CachePhoneProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(CachePhoneProvider.class);

    @Autowired
    CommomService commomService;

    private  final static String CACHE_KEY = "wmvas_phone_provider";


    /**
     * 获取缓冲中所有的注册服务商
     * 
     * 
     * @return
     */
    public List<ServiceRegisterVo> queryCacheServiceRegister(){
        String value = RedisSlave.getInstance().getString(CACHE_KEY);
        List<ServiceRegisterVo> lstResult = Collections.emptyList();

        if(StringUtils.isEmpty(value)){
            Result result = new Result();
            try {
                long liveTime = 60 * 10;
                lstResult =  commomService.getListServiceRegisterVo(result, EnumBusinessCode.MPCZ);
                RedisSlave.getInstance().set(CACHE_KEY, JSON.toJSONString(lstResult), liveTime);

                LOGGER.info("#cache -- redis注册服务商为空，从接口获取数据，设置到缓存成功");

            } catch (Exception e) {
                LOGGER.error("获取注册服务商失败",e);
            }

        }else{
            LOGGER.info("#cache-- redis 注册服务商不为空，直接获取返回。");

            lstResult = JSON.parseObject(value,new TypeReference< List<ServiceRegisterVo>>(){});
        }

        return lstResult;

    }
}
