/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.trainticket.controller;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.BillDetailInfo;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.trainticket.service.HcpOrderInfoService;

/**
 *
 * @author zengzw
 * @date 2016年12月5日
 */
@Controller
@RequestMapping("/vas/train/order")
public class HCPOrderInfoController extends BaseController{

    @Autowired
    HcpOrderInfoService orderInfoService;

    /**
     *
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "billsDetails")
    @ResponseBody
    public ReturnDTO billsDetail(String orderCode) {
        logger.info ("查询用户购买火车票单详情" + orderCode);
        Result result = this.getResult ();
        logger.info ("用户登录信息:" + JSONObject.toJSONString (result));
        try {
            result = this.orderInfoService.details (result, orderCode);
            HcpOrderInfoPo chargeInfo = result.getData();

            BillDetailInfo.CommissionInfo commissionInfo = new BillDetailInfo.CommissionInfo();
            
            commissionInfo.setSalePrice(String.valueOf(chargeInfo.getOriginalAmount())); //销售价
            commissionInfo.setDifferential(chargeInfo.getServicePrice().toString()); //提点金额
            BigDecimal servicePrcie = BigDecimal.valueOf(chargeInfo.getServicePrice());
            
            //平台分成
            commissionInfo.setPlatformRatio(chargeInfo.getPlatformDividedRatio().toString()); //平台比率
            String platformCommission = convertCommission(BigDecimal.valueOf(chargeInfo.getPlatformDividedRatio()),servicePrcie);
            commissionInfo.setPlatformCommission(platformCommission); //平台佣金
            
            
            BigDecimal oneHundred = BigDecimal.valueOf(100);
            //销售县域佣金比例(平台比率 * 县域比率）
            BigDecimal areaCommissionRatio = new BigDecimal(chargeInfo.getAreaCommissionRatio ())
                    .multiply(new BigDecimal (chargeInfo.getAreaDividedRatio()))
                    .divide(oneHundred)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            commissionInfo.setCountyRatio(chargeInfo.getAreaDividedRatio().toString());
            String areaCommission = convertCommission(areaCommissionRatio,servicePrcie);
            commissionInfo.setCountyCommission(areaCommission);
            commissionInfo.setCostingAmount(chargeInfo.getCostingAmount().toString());
            commissionInfo.setAreaDividedRatio(chargeInfo.getAreaCommissionRatio().toString());

            //销售网点提点比例 （县域零售、全国零售时提供）（平台比率*网点比率）
            BigDecimal storeCommissionRatio = new BigDecimal(chargeInfo.getStoreCommissionRatio ())
                    .multiply(new BigDecimal (chargeInfo.getAreaDividedRatio()))
                    .divide(oneHundred)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            commissionInfo.setShopRatio(chargeInfo.getStoreCommissionRatio().toString());
            String storeCommission = convertCommission(storeCommissionRatio,servicePrcie);
            commissionInfo.setShopCommission(storeCommission);
            
            
            
            BillDetailInfo billDetailInfo = new BillDetailInfo();
            billDetailInfo.setChargeCode(chargeInfo.getHcpOrderCode());
            billDetailInfo.setShopName(chargeInfo.getStoreName());
            billDetailInfo.setPaymentAmount(String.valueOf(chargeInfo.getOriginalAmount()));
            billDetailInfo.setBusinessCodeName(chargeInfo.getBusinessName());
            billDetailInfo.setSupplierName(chargeInfo.getSupplierName());
            billDetailInfo.setSaleCountyName(chargeInfo.getCountryName());
            
            billDetailInfo.setCommissionInfoList(Arrays.asList(commissionInfo));

            result.setData(billDetailInfo);

        } catch (Exception ex) {
            logger.error ("查询用户缴费单详情失败" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setStatus (500);
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info ("请求返回结果：" + JSONObject.toJSONString (result.DTO ()));

        return result.DTO ();
    }

    /**
     *
     * @param commission
     * @param ratio
     * @return
     */
    private String convertCommission(BigDecimal commission, BigDecimal ratio){

        BigDecimal i = commission == null ? BigDecimal.valueOf(0) : commission ;
        BigDecimal j = ratio == null ? BigDecimal.valueOf(1) : ratio ;


        BigDecimal multiply =  BigDecimal.valueOf(j.doubleValue() * 0.01);

       return String.valueOf(i.multiply(multiply).setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }

}
