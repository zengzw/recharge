/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.data.redis.RedisSlave;
import com.dtds.platform.util.bean.Result;
import com.tsh.dubbo.bis.vo.ShopVO;
import com.tsh.openpf.vo.ServiceRegisterVo;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.model.SupplierAreaBusiness;
import com.tsh.vas.sdm.service.supplier.SupplierService;
import com.tsh.vas.service.CommomService;
import com.tsh.vas.service.ShopApiService;

/**
 *  注册服务商管理类
 *  
 *  获取可用供应商，按照优先级存放到redis队列中。
 *      leftPush : "先进后出".
 *      rightPush: "先进先出".
 *  
 *  根据这两个特性，使用时 查询供应商优先级时得注意。 
 *   
 * @author zengzw
 * @date 2017年3月3日
 */

@Service
public class QueueRegisterSupplier {

    private final static Logger LOGGER = LoggerFactory.getLogger(QueueRegisterSupplier.class);

    /**
     * 公共接口
     */
    @Autowired
    private CommomService commomService;


    @Autowired 
    SupplierService supplierService; 


    /**
     *  网点Dubbo 接口
     */
    @Autowired
    private ShopApiService shopApiService;



    RedisSlave redisSlave = RedisSlave.getInstance();


    /**
     * 根据网点的Id 查出可用的注册服务商。将注释存放到redis队列中。
     * 
     * 
     * @param key
     * @param bizId 网点ID
     * @throws Exception 
     */
//    @Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
    public synchronized List<ServiceRegisterVo> setAvaliableSupplier(String key,Long bizId) throws Exception{
        //获取本网点支持的所有可用供应商列表. order 小的优先
        List<SupplierAreaBusiness> lstSupplierAreaBusiness = getListOrganizationInfo(bizId); 
        if(CollectionUtils.isEmpty(lstSupplierAreaBusiness)){
            throw new BusinessRuntimeException("", "没有可用供应商");
        }


        //根据供应商编码，获取可用注册供应商列表（调用地址，签名等）
        List<ServiceRegisterVo> lstServiceRegister =  getListServiceRegister(lstSupplierAreaBusiness);
        if(CollectionUtils.isEmpty(lstSupplierAreaBusiness)){
            throw new BusinessRuntimeException("", "没有可用服务注册商");
        }

        
        //添加前，将队列清空。
        this.cleanQueue(key);

        
        //将服务商存储到Redis中，已队列的形式，先进后出。设计到排序的问题。所以优先级高的要放在list后面
        for(ServiceRegisterVo serviceRegisterVo : lstServiceRegister){
            String jsonValue = JSON.toJSONString(serviceRegisterVo);
            
            LOGGER.info("#---- set register servcie to key:{}, businessid:{}",key,serviceRegisterVo.getBusinessId());
            
            redisSlave.in(key, jsonValue);
            
            Long length = redisSlave.length(key);
            LOGGER.info("#---- key:{} push success, queue length:{}",key,length);
        }

        return lstServiceRegister;
    }





    /**
     * 获取可用服务商
     * 
     * 
     * @param key
     * @return
     */
    public synchronized ServiceRegisterVo getAvaliableSupplier(String key){
        Long length = redisSlave.length(key);
        
        LOGGER.info("#-- get register service redis key:{},length:{}",key,length);
        
        if((length != null && length.intValue() > 0)){
            String result =  redisSlave.pop(key);
            ServiceRegisterVo  serviceRegisterVo = JSON.parseObject(result,ServiceRegisterVo.class);
            
            LOGGER.info("#-- get register service from redis.key:{},value:{}",key,serviceRegisterVo.getBusinessId());
            
            return serviceRegisterVo;
        }

        return null;
    }
    
    

    /**
     * 删除key
     * 
     * @param key
     */
    @SuppressWarnings("unchecked")
    public synchronized void cleanQueue(String key){
        Long length = redisSlave.length(key);
        
        LOGGER.info("#-- remove register service .key:{}.{} from -1",key,length+1);
        
        //将存在的数据剪切掉 
        RedisSlave.getRedistemplate().opsForList().trim(key, length+1, -1);
       
        LOGGER.info("#-- remove register service key from redis:{}",key);
    }





    /**
     * 获取当前网点 可用供应商列表
     * 
     * @param storeId 网点ID
     * @return
     * @throws Exception
     */
    private List<SupplierAreaBusiness> getListOrganizationInfo(Long storeId){
        Result result = new Result();
        //根据网点id获取省市县信息
        ShopVO shopVO = shopApiService.getShop (result, storeId);
        LOGGER.info("获取网点信息是：" + shopVO);


        //供应商编号，对应增值服务的supplierCode;后台根据权重判断获取获取
        List<SupplierAreaBusiness> lstSupplierAreaBusiness = this.supplierService.findByBusinessCode (result, String.valueOf (shopVO.getAreaId ()), EnumBusinessCode.MPCZ.name()).getData ();
        if(CollectionUtils.isEmpty(lstSupplierAreaBusiness)){
            return Collections.emptyList();
        }

        LOGGER.info("supplierAreaBusinessDao.findByBusinessCode 返回的list是：" + lstSupplierAreaBusiness);

        return lstSupplierAreaBusiness;
    }


    
    /**
     * 获取服务商的调用地址信息
     * 
     * 
     * @param lstSupplierAreaBusiness
     * @return
     */
    private List<ServiceRegisterVo> getListServiceRegister(List<SupplierAreaBusiness> lstSupplierAreaBusiness){
        List<ServiceRegisterVo> lstServiceRegisterVo = new ArrayList<>();

        for(int i = 0 ; i<lstSupplierAreaBusiness.size();  i++){
            SupplierAreaBusiness supplierAreaBusiness  = lstSupplierAreaBusiness.get(i);
            String supplierCode = supplierAreaBusiness.getSupplierCode ();
            //获取开放平台供应商信息
            try {
                Result result = new Result();
                ServiceRegisterVo serviceRegisterVo = getServiceRegisterVo (result, supplierCode);
                lstServiceRegisterVo.add(serviceRegisterVo);
            } catch (Exception e) {
                LOGGER.error("获取ope 注册供应商出错",e);   
            }
        }

        return lstServiceRegisterVo;
    }



//    @Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
    public ServiceRegisterVo getServiceRegisterVo(Result result,String supplierCode) throws Exception {
        return commomService.getServiceRegisterVo(result, supplierCode, EnumBusinessCode.MPCZ);
    }

}
