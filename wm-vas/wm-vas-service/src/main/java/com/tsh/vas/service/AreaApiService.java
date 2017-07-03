package com.tsh.vas.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tsh.dubbo.bis.api.ShopApi;
import com.tsh.dubbo.bis.vo.ShopVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Result;
import com.tsh.dubbo.bis.api.AreaApi;
import com.tsh.dubbo.bis.vo.AreaInfoVO;

@Service
public class AreaApiService {

    private static final Logger LOGGER = Logger.getLogger (AreaApiService.class);

    @Autowired
    private AreaApi areaApi;
    
    
    @Autowired
    private ShopApi shopApi;

    public Result queryAreasByProvinceAndCityAndArea(Result result, String provinceName, String cityName) {
        try {
            result.setData (null);
            List<AreaInfoVO> areaInfoVOs = areaApi.queryAreasByProvinceAndCityAndArea (result, provinceName, cityName, "").getData ();
            result.setData (areaInfoVOs);
            LOGGER.info ("调用 queryAreasByProvinceAndCityAndArea方法返回的数据是：" + areaInfoVOs);
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return result;
    }

    
    /**
     * 查询所有县域
     * 
     * 
     * @param result
     * @return  List<AreaInfoVO> 
     */
    public  List<AreaInfoVO> queryListAreas(Result result) {
        try {
            result.setData (null);
            List<AreaInfoVO> areaInfoVOs = areaApi.queryAllAreaList(result).getData();
            LOGGER.info ("调用 queryListAreas方法返回的数据是：" + areaInfoVOs);
            
            return areaInfoVOs;
            
        } catch (Exception e) {
          LOGGER.error("查询所有县域失败",e);
        }
        
        return Collections.emptyList();
        
    }
    
    
    
    
    public Result queryStoresByAreaId(Result result, String areaId) {
        try {
            List<Long> areaList = new ArrayList<>();
            areaList.add(Long.valueOf(areaId));
            List<ShopVO> shopVOs = shopApi.getShopListByAreaIds(result, areaList).getData();
            result.setData (shopVOs);
            LOGGER.info ("调用 queryAreasByProvinceAndCityAndArea方法返回的数据是：" + shopVOs);
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return result;
    }



}

