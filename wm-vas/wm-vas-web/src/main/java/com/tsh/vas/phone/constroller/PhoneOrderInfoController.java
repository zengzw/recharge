/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.constroller;

import java.math.BigDecimal;
import java.math.MathContext;
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
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.phone.service.PhoneOrderInfoService;
import com.tsh.vas.vo.phone.PhoneReportVo;

/**
 *
 * @author zengzw
 * @date 2016年12月5日
 */
@Controller
@RequestMapping("/vas/phone/order")
public class PhoneOrderInfoController extends BaseController{

    @Autowired
    PhoneOrderInfoService orderInfoService;
    
    
    /**
     * 账单详情
     * 
     *  话费，目前 平台、县域都不参与分润
     * 
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "billsDetails")
    @ResponseBody
    public ReturnDTO billsDetail(String orderCode) {
        Result result = this.getResult ();
        
        try {
            result = this.orderInfoService.details (result, orderCode);
            PhoneOrderInfoPo chargeInfo = result.getData();
            
            BigDecimal oneHundred = BigDecimal.valueOf(100);
            BillDetailInfo.CommissionInfo commissionInfo = new BillDetailInfo.CommissionInfo();
            commissionInfo.setSalePrice(String.valueOf(chargeInfo.getOriginalAmount())); //销售价
            
            //计算分成金额=支付金额 - 票价
            BigDecimal fcAmount = BigDecimal.valueOf(0);
            if(null != chargeInfo.getOriginalAmount() && null != chargeInfo.getCostingAmount()){
                fcAmount = chargeInfo.getOriginalAmount().subtract(chargeInfo.getCostingAmount());
            }

            
            //平台分成
            commissionInfo.setPlatformRatio(chargeInfo.getPlatformDividedRatio()+"%"); //平台比率
            String platformCommission = convertCommission(BigDecimal.valueOf(chargeInfo.getPlatformDividedRatio()),fcAmount);
            commissionInfo.setPlatformCommission(platformCommission); //平台佣金
            
            
            //销售县域佣金比例(平台比率 * 县域比率）
            BigDecimal areaCommissionRatio = BigDecimal.valueOf(chargeInfo.getAreaCommissionRatio ())
                    .multiply(BigDecimal.valueOf(chargeInfo.getAreaDividedRatio()))
                    .divide(oneHundred)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            commissionInfo.setCountyRatio(chargeInfo.getAreaDividedRatio()+"%");
            String areaCommission = convertCommission(areaCommissionRatio,fcAmount);
            commissionInfo.setCountyCommission(areaCommission);
            
            commissionInfo.setAreaDividedRatio(areaCommissionRatio+"%");
            commissionInfo.setCostingAmount(chargeInfo.getCostingAmount().toString()); //成本价

            
            //销售网点提点比例 （县域零售、全国零售时提供）（平台比率*网点比率）
            BigDecimal storeCommissionRatio =BigDecimal.valueOf(chargeInfo.getStoreCommissionRatio ())
                    .multiply(BigDecimal.valueOf(chargeInfo.getAreaDividedRatio()))
                    .divide(oneHundred)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            commissionInfo.setShopRatio(chargeInfo.getStoreCommissionRatio()+"%"); //佣金比率
           String storeCommission = convertCommission(storeCommissionRatio,fcAmount);
            commissionInfo.setShopCommission(storeCommission); //佣金
            
            
            
            BillDetailInfo billDetailInfo = new BillDetailInfo();
            billDetailInfo.setChargeCode(chargeInfo.getPhoneOrderCode());
            billDetailInfo.setShopName(chargeInfo.getStoreName());
            billDetailInfo.setPaymentAmount(String.valueOf(chargeInfo.getOriginalAmount()));
            billDetailInfo.setBusinessCodeName(chargeInfo.getBusinessName());
            billDetailInfo.setSupplierName(chargeInfo.getSupplierName());
            billDetailInfo.setSaleCountyName(chargeInfo.getCountryName());
            
            billDetailInfo.setCommissionInfoList(Arrays.asList(commissionInfo));

            result.setData(billDetailInfo);

        } catch (Exception ex) {
            logger.error ("查询[话费充值]单详情失败" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setStatus (500);
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        
        logger.info ("请求返回结果：" + JSONObject.toJSONString (result.DTO ()));

        return result.DTO ();
    }
    
    //分成金额计算+提点金额 价格加工
    private PhoneReportVo countFc(PhoneReportVo phoneReportVo){
        //计算平台+县域+网点佣金
        BigDecimal am = new BigDecimal(100);
        //计算分成金额=支付金额 - 票价
        BigDecimal fcAmount = BigDecimal.valueOf(0);
        if(null != phoneReportVo.getOriginalAmount() && null != phoneReportVo.getCostingAmount()){
            fcAmount = new BigDecimal(phoneReportVo.getOriginalAmount()).subtract(new BigDecimal(phoneReportVo.getCostingAmount()));
        }

        //提点金额
        phoneReportVo.setTdAmount(fcAmount.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        //平台，县域，网点，分成比例
        BigDecimal platFormRatio = new BigDecimal(phoneReportVo.getPlatformDividedRatio().toString()).divide(am);
        BigDecimal areaFormRatio = new BigDecimal(phoneReportVo.getAreaCommissionRatio().toString()).divide(am);
        BigDecimal stormFormRatio = new BigDecimal(phoneReportVo.getStoreCommissionRatio().toString()).divide(am);
        //平台，县域，网点分成金额
        BigDecimal platformRatioYuan = platFormRatio.multiply(fcAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal areaFormRatioYuan = areaFormRatio.multiply(fcAmount.subtract(platformRatioYuan)).setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal stormFormRatioYuan = new BigDecimal(0);
        if(areaFormRatioYuan.compareTo(new BigDecimal(0)) == 0){
            stormFormRatioYuan = fcAmount.subtract(platformRatioYuan);
        }else {
            stormFormRatioYuan = fcAmount.subtract(platformRatioYuan).subtract(areaFormRatioYuan);
        }
        phoneReportVo.setPlatformDividedRatioStr(platformRatioYuan.toString());
        phoneReportVo.setAreaCommissionRatioStr(areaFormRatioYuan.toString());
        phoneReportVo.setStoreCommissionRatioStr(stormFormRatioYuan.toString());
        return phoneReportVo;
    }

    private String convertCommission(BigDecimal commission,BigDecimal ratio){
        if(commission == null) commission = BigDecimal.valueOf(0);
        if(ratio == null) ratio = BigDecimal.valueOf(1);
        BigDecimal multiply = BigDecimal.valueOf(ratio.doubleValue() * 0.01);

       return  String.valueOf(commission.multiply(multiply).setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }

}
