/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.tsh.dubbo.hlq.api.CouponApi;
import com.tsh.dubbo.hlq.enumType.CouponStatusEnum;
import com.tsh.dubbo.hlq.vo.ex.CouponQueryParamVO;
import com.tsh.dubbo.hlq.vo.ex.CouponVO;
import com.tsh.dubbo.hlq.vo.vas.UseCouponDTO;
import com.tsh.phone.util.HttpUtils;
import com.tsh.vas.commoms.constants.URLServiceConstants;
import com.tsh.vas.commoms.enums.EnumSystemType;
import com.tsh.vas.commoms.exception.CouponRuntimeException;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.phone.constants.PhoneErrorCodes;
import com.tsh.vas.vo.HttpResponseResult;
import com.tsh.vas.vo.coupon.QueryCouponParams;

/**
 * 优惠价服务接口
 * 
 * Http + dubbo
 * 
 *  下单的时候：调用使用applyCoupon 接口。
 *  
 *  支付成功：更新优惠券状态： 1：已经使用
 *  支付失败，充值失败：更改优惠券状态位： 0：未使用
 *  
 * 
 * @author zengzw
 * @date 2017年6月22日
 */
@Service
public class CouponService {

    private static final Logger logger = LoggerFactory.getLogger(CouponService.class);


    /**
     * 优惠券，dubbo服务
     */
    @Autowired
    CouponApi couponApi;


    /**
     * 获取优惠券列表
     * 
     * 
     * @param result
     * @param queryParams
     * @return
     */
    public List<CouponVO> getListCoupon(Result result,QueryCouponParams queryParams){
        Map<String, Object>  reqParmas = new HashMap<>();
        queryParams.setUseScopeTypes(new Integer[]{2});// 使用范围类型，1：默认使用范围（商品类目等） 2：话费充值。支持多类型查询。
        reqParmas.put("orderInfo",JSON.toJSONString(queryParams));
        reqParmas.put("sysType", EnumSystemType.B2C.getCode());
        reqParmas.put("token",result.getUserInfo().getToken());
        reqParmas.put("reqSource", "mobile_b2c");

        String url = TshDiamondClient.getInstance().getConfig("url_coupon") + URLServiceConstants.Coupon.COUPONS;
        String respResult = HttpUtils.doGet(url,reqParmas);
        if(StringUtils.isEmpty(respResult)){
            logger.error("#--> 获取优惠券列表返回为空");
            return Collections.emptyList();
        }else{
            HttpResponseResult responseResult = JSON.parseObject(respResult,HttpResponseResult.class);
            if(responseResult.hasError()){
                logger.error("#--> 获取优惠券返回失败：{}",responseResult.getMsg());
                return Collections.emptyList();
            }
            
            //判断是否为空
            if(responseResult.getData() == null){
                return Collections.emptyList();
            }

            Map<String, List<CouponVO>> mapResult = JSON.parseObject(responseResult.getData().toString(),new TypeReference<Map<String, List<CouponVO>>>(){});
            return mapResult.get(queryParams.getOrderNo());
        }
    }


    /**
     * 使用优惠券
     * 
     * 下单的时候调用当前接口
     * 
     * @param reqQarams
     * @return
     * @throws FunctionException 
     */
    public List<UseCouponDTO> applyCoupon(Result result,QueryCouponParams reqParams){
        List<Long> couponIds = new ArrayList<>();
        for(Integer couponId : reqParams.getCouponIds()){
            couponIds.add(Long.parseLong(couponId.toString()));
        }
        CouponQueryParamVO params = new CouponQueryParamVO();
        params.setCouponIds(couponIds);
        params.setMoney(BigDecimal.valueOf(reqParams.getMoney()));
        params.setOrderNo(reqParams.getOrderNo());
        params.setShopId(reqParams.getShopId());
        params.setUserId(Long.parseLong(reqParams.getUserId()+""));
        params.setUseScopeTypes( Arrays.asList(2));


        try {
            logger.info("#-->req useCoupon params:"+JSON.toJSONString(params)); 
            Result respResult =  couponApi.useCoupon(result, params);
            logger.info("#-->req useCoupon result:"+JSON.toJSONString(respResult.getData()));

            List<UseCouponDTO> listResult = respResult.getData();
            if(CollectionUtils.isEmpty(listResult)){
                throw new CouponRuntimeException(PhoneErrorCodes.DubboAPI.COUPON_BIND_ERROR,"使用优惠券失败,返回结果为空！");
            }

            return listResult;

        } catch (Exception e) {
            logger.error("#-->使用优惠券失败",e);

            throw new CouponRuntimeException(PhoneErrorCodes.DubboAPI.COUPON_BIND_ERROR,"使用优惠券失败！"+e.getMessage());
        }

    }



    /**
     * 修改优惠券的状态
     * 
     * @throws Exception 
     */
    public void updateCouponStatusByIds(Result result,List<Long> couponIds,CouponStatusEnum status) throws Exception{
        Result respResult =  couponApi.updateCouponStatusByIds(result, couponIds, status);

        if(respResult.getStatus() != Result.STATUS_OK){
            throw new FunctionException(result, "更新优惠券信息失败！"+respResult.getMsg());
        }

    }


    /**
     * 修改优惠券的状态
     * 
     * @throws Exception 
     */
    public void updateCouponStatusById(Result result,Long couponId,CouponStatusEnum status) throws Exception{
        List<Long> couponIds = new ArrayList<>();
        couponIds.add(couponId);
        Result respResult =  couponApi.updateCouponStatusByIds(result, couponIds, status);

        if(respResult.getStatus() != Result.STATUS_OK){
            throw new FunctionException(result, "更新优惠券信息失败！"+respResult.getMsg());
        }

    }


}
