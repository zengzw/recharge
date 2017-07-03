/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.constroller.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.dubbo.hlq.vo.ex.CouponVO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.phone.facade.APIAppPhoneService;
import com.tsh.vas.phone.manager.CouponComparetor;


/**
 * 话费 APP api 接口
 *
 * @author zengzw
 * @date 2017年3月7日
 */
@Controller
@RequestMapping(value = "/mock/app/vas/phone")
public class MockAPPPhoneAPIController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(MockAPPPhoneAPIController.class);

    /**
     * api service
     */
    @Autowired
    private APIAppPhoneService apiService;


    /**
     * 根据手机号码 获取号码段
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/coupon/list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO queryListCoupon(String mobile,String price){
        logger.info("#url:/coupon/list-- req params mobile:{},price:{}",mobile,price);

        Result result = this.getResult();
        UserInfo userInfo  = result.getUserInfo();
//        ReturnDTO validate = combineValidate(userInfo,mobile,price);
//        if(validate != null){
//            return validate;
//        }
        List<CouponVO> listResult  = new ArrayList<>();

        CouponVO couponVO = new CouponVO();
        couponVO.setCouponStatus(1);
        couponVO.setRecordID(11L);
        couponVO.setMoney(BigDecimal.valueOf(15));
        couponVO.setUseBeginTime("2017-06-20");
        couponVO.setUseEndTime("2017-08-20");
        couponVO.setUseScope("全场通用");
        couponVO.setUseLinmit("无满额限制");
        couponVO.setCouponType("满减券");
        listResult.add(couponVO);
        
        CouponVO couponVO1 = new CouponVO();
        couponVO1.setCouponStatus(1);
        couponVO1.setRecordID(12L);
        couponVO1.setMoney(BigDecimal.valueOf(10));
        couponVO1.setUseBeginTime("2017-06-20");
        couponVO1.setUseEndTime("2017-07-06");
        couponVO1.setUseScope("全场通用");
        couponVO1.setUseLinmit("无满额限制");
        couponVO1.setCouponType("满减券");
        listResult.add(couponVO1);
        
        
        CouponVO couponVO2 = new CouponVO();
        couponVO2.setCouponStatus(1);
        couponVO2.setRecordID(14L);
        couponVO2.setMoney(BigDecimal.valueOf(2));
        couponVO2.setUseBeginTime("2017-06-20");
        couponVO2.setUseEndTime("2017-07-03");
        couponVO2.setUseScope("全场通用");
        couponVO2.setUseLinmit("无满额限制");
        couponVO2.setCouponType("满减券");
        listResult.add(couponVO2);

        CouponVO couponVO3 = new CouponVO();
        couponVO3.setCouponStatus(1);
        couponVO3.setRecordID(17L);
        couponVO3.setMoney(BigDecimal.valueOf(6));
        couponVO3.setUseBeginTime("2017-06-20");
        couponVO3.setUseEndTime("2017-07-03");
        couponVO3.setUseScope("全场通用");
        couponVO3.setUseLinmit("无满额限制");
        couponVO3.setCouponType("满减券");
        listResult.add(couponVO3);
        
        Collections.sort(listResult,new CouponComparetor());
        
        return responseSuccess("获取成功", listResult);

    }


    /**
     * @param params
     * @param userInfo
     */
    private ReturnDTO combineValidate(UserInfo userInfo,String... params) {
        ReturnDTO validate = super.validateUserInfo(userInfo);
        if(validate != null){
            return validate;
        }

        for(String param : params){
            validate = super.validateRequestParams(param);
            if(validate != null){
                return validate;
            }
        }

        return null;
    }




}
