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
import com.tsh.dubbo.bis.api.ShopApi;
import com.tsh.dubbo.bis.vo.ShopVO;

/**
 * 县域 网点 dubbo 服务接口
 * 
 * 
 * @author zengzw
 * @date 2017年3月3日
 */

@Service
public class ShopApiService {

    private final static Logger logger = LoggerFactory.getLogger(ShopApiService.class); 


    @Autowired
    private ShopApi shopApi;


    /**
     * 根据网点信息 获取 县域相关信息
     * 
     * 
     * @param result
     * @param bizId  网点ID
     * @return ShopVO 
     */
    public ShopVO getShop(Result result,Long bizId){
        //根据网点id获取省市县信息
        ShopVO shopVO = null;
        try {
            shopVO = this.shopApi.getShop (result, bizId).getData ();
        } catch (Exception e) {
            logger.error("获取dubbo接口shopApi getShop 出错",e);
        }

        return shopVO;
    }
}
