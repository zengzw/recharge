/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.manager;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.dubbo.bis.vo.ShopVO;
import com.tsh.phone.util.DateUtils;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.commoms.utils.PhoneUtils;
import com.tsh.vas.iservice.IMemberService;
import com.tsh.vas.iservice.IPayService;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreePo;
import com.tsh.vas.phone.constants.PhoneConstants;
import com.tsh.vas.phone.enums.EnumActivetyAuditStatus;
import com.tsh.vas.phone.enums.EnumActivetyRewardStatus;
import com.tsh.vas.phone.enums.EnumPhoneOrderInfoPayStatus;
import com.tsh.vas.phone.enums.EnumRequestOrderType;
import com.tsh.vas.phone.facade.APIAppPhoneService;
import com.tsh.vas.phone.service.PhoneOrderInfoService;
import com.tsh.vas.sdm.service.business.BusinessService;
import com.tsh.vas.service.ShopApiService;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.utils.GenerateOrderNumber;
import com.tsh.vas.vo.phone.MemberVo;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;
import com.tsh.vas.vo.phone.VasPhoneOneyuanFreeVo;
import com.tsh.vas.vo.phone.api.APISuccessOrderInfo;

/**
 * 故乡-订单管理服务类
 * 
 * @author zengzw
 * @date 2017年5月26日
 */
@Service("gxOrderInfoService")
public class GXOrderInfoMangerService extends AbstOrderInfoManagerService{

    private final static Logger logger = LoggerFactory.getLogger(APIAppPhoneService.class);

    /**
     * 业务服务
     */
    @Autowired
    private BusinessService businessService;

    /**
     * 商家-账户API
     */
    @Autowired 
    ShopApiService shopApiService;


   /**
    * 账户API
    */
    @Resource(name="gxPayServie")
    IPayService payService;

    /**
     * 话费订单服务类
     * 
     */
    @Autowired 
    private PhoneOrderInfoService orderInfoService;
    
    
    /**
     * 获取会员信息
     */
    @Autowired
    private IMemberService memberService;


    /**
     * 创建订单
     */
    @Override
    public PhoneOrderInfoPo createOrder(Result result,PhoneOrderInfoVo orderInfoVo) throws Exception {
        UserInfo userInfo = result.getUserInfo();
        PhoneOrderInfoPo orderInfoPo = new PhoneOrderInfoPo();
        orderInfoPo.setSources(EnumRequestOrderType.GX.name());

        if(userInfo.getBizId() != null){
            //根据用户所属县域ID获取所属城市
            ShopVO shopVO =  shopApiService.getShop (result, userInfo.getBizId ());
            orderInfoPo.setStoreNo(shopVO.getShopNo()); //网点编号
            orderInfoPo.setStoreName (shopVO.getShopName ());
            orderInfoPo.setStoreCode(String.valueOf (userInfo.getBizId ()));
            orderInfoPo.setProvince (shopVO.getContactProvince ());
            orderInfoPo.setCity (shopVO.getContactCity ());
            orderInfoPo.setCountry (shopVO.getContactArea ());
            orderInfoPo.setCountryCode (String.valueOf (shopVO.getAreaId ()));
            orderInfoPo.setCountryName (shopVO.getAreaName ());
        }else{
            orderInfoPo.setStoreName (userInfo.getBizName());
            orderInfoPo.setStoreInfo ("");
            orderInfoPo.setProvince ("");
            orderInfoPo.setCity ("");
            orderInfoPo.setCountry ("");
            orderInfoPo.setCountryCode (userInfo.getBelongId() != null ? userInfo.getBelongId().toString() :"");
            orderInfoPo.setCountryName ("");
        }

        //获取支付会员信息
        MemberVo payMemeber = memberService.getMemberInfoByMemberId(userInfo.getOldUsrId());
        if(payMemeber != null){
            orderInfoPo.setMobile(payMemeber.getMobileNumber());
            orderInfoPo.setMemberMobile (payMemeber.getMobileNumber());
            orderInfoPo.setMemberName (payMemeber.getMemberName());
            orderInfoPo.setPayUserId(payMemeber.getUserID());
            orderInfoPo.setPayUserName(payMemeber.getMemberName());
        }

        //订单编号=业务编号+时间码
        String orderCode = orderInfoVo.getBusinessCode () + GenerateOrderNumber.getOrderNumber ();
        orderInfoPo.setPhoneOrderCode(orderCode);
        orderInfoPo.setBizId (userInfo.getBizId());
        orderInfoPo.setBizType (userInfo.getRoleType());

        orderInfoPo.setActivetyMoeny(orderInfoVo.getJoinMoney());

        orderInfoPo.setGoodsId(orderInfoVo.getGoodsId()); //商品ID
        orderInfoPo.setCreateTime (new Date ());
        orderInfoPo.setPayStatus (EnumOrderInfoPayStatus.NON_PAYMENT.getCode ());
        orderInfoPo.setPayUserName (userInfo.getUserName());
        orderInfoPo.setRechargePhone(orderInfoVo.getRechargePhone());//充值手机号码

        //获取充值手机会员Id
        orderInfoPo.setOpenUserId(orderInfoVo.getOpenUserId());
        MemberVo rechargeMember =  memberService.getMemberInfoForApp(orderInfoVo.getRechargePhone(), orderInfoVo.getRechargePhone(), userInfo.getToken());
        if(rechargeMember != null){
            orderInfoPo.setPhoneMemberId(rechargeMember.getId().toString()); 
        }

        //根据业务编码获取业务信息
        BusinessInfo businessInfo = this.businessService.getByBusinessCode (result, orderInfoVo.getSubBusinessCode()).getData ();
        //获取父业务名称
        BusinessInfo businessInfoParent = this.businessService.getByBusinessCode (result, orderInfoVo.getBusinessCode ()).getData ();
        String parentBusinessCode;
        String parentBusinessName;
        if (businessInfoParent != null) {
            parentBusinessCode = businessInfoParent.getBusinessCode ();
            parentBusinessName = businessInfoParent.getBusinessName ();
        } else {
            parentBusinessCode = businessInfo.getBusinessCode ();
            parentBusinessName = businessInfo.getBusinessName ();
        }

        orderInfoPo.setBusinessCode (parentBusinessCode);//父业务编号
        orderInfoPo.setBusinessName (parentBusinessName);
        orderInfoPo.setSubBusinessCode (businessInfo.getBusinessCode ()); //子业务编号
        orderInfoPo.setSubBusinessName (businessInfo.getBusinessName ());

        BigDecimal realAmount = orderInfoVo.getRealAmount();  //实际支付金额
        BigDecimal originalAmount = orderInfoVo.getOriginalAmount(); //应付金额

        //如果参与活动的话，应付金额、实付金额加上参与活动面额
        if(orderInfoVo.getJoinStatus() != null && orderInfoVo.getJoinStatus().intValue() == PhoneConstants.JOIN_STATUS){
            realAmount = realAmount.add(BigDecimal.valueOf(orderInfoVo.getJoinMoney()));

            if(orderInfoVo.getOriginalAmount() == null){ 
                originalAmount = realAmount; //应付金额为空，等于实付金额
            }else{
                originalAmount = originalAmount.add(BigDecimal.valueOf(orderInfoVo.getJoinMoney())); //应付金额
            }
        }

        orderInfoPo.setSaleAmount(orderInfoVo.getSaleAmount()); //面值
        orderInfoPo.setOriginalAmount(originalAmount); //应付金额
        orderInfoPo.setRealAmount (realAmount);//实际支付价格
        orderInfoPo.setJoinStatus(orderInfoVo.getJoinStatus());
        orderInfoService.addPhoneOrderInfo(result, orderInfoPo);
        
        
        return result.getData();
    }



    
    /**
     * 支付
     */
    @Override
    public Result pay(Result result, PhoneOrderInfoPo orderInfo) throws Exception {

        Result payResult = payService.pay(result, orderInfo);

        logger.info("#GX--->话费订单号：{} 支付完成",orderInfo.getPhoneOrderCode());

        if(payResult.getStatus() == HttpResponseConstants.SUCCESS){
            //订单状态为支付中，后面有个支付消息中间件回调继续下面的步骤
            this.orderInfoService.updateStatus (result, orderInfo.getPhoneOrderCode(), EnumPhoneOrderInfoPayStatus.PAIDING.getCode ());

            logger.info("##GX----话费订单号：{}获取微信支付地址成功，订单状态位：支付中【2】",orderInfo.getPhoneOrderCode());
            result.setStatus(HttpResponseConstants.SUCCESS);
            result.setCode(HttpResponseConstants.SUCCESS);
            result.setMsg("支付成功！");

            APISuccessOrderInfo appInfo = this.buildResult(orderInfo);
            appInfo.setWeixPayUrl(payResult.getData().toString());
            result.setData(appInfo);
            return result;
        }else{
            this.orderInfoService.updateStatus (result, orderInfo.getPhoneOrderCode(), EnumPhoneOrderInfoPayStatus.PAY_FAIL.getCode(),"gx-支付异常，"+result.getMsg());

            logger.info("#GX----话费订单：{}支付同步接口成功，订单状态位：支付异常【6】",orderInfo.getPhoneOrderCode());

            result.setStatus (HttpResponseConstants.SUCCESS);
            result.setCode(HttpResponseConstants.ERROR);
            result.setMsg("支付失败！");
            return result;
        }
    }



    /**
     * 构建屏端返回结果对象
     */
    @Override
    public APISuccessOrderInfo buildResult(PhoneOrderInfoPo orderInfo) {
        APISuccessOrderInfo  successOrderInfo = new APISuccessOrderInfo();
        successOrderInfo.setOrderCode(orderInfo.getPhoneOrderCode());
        successOrderInfo.setCreateTime(DateUtils.dateToString(orderInfo.getCreateTime(),DateUtils.FORMAT_DEFAULT));
        successOrderInfo.setMobile(orderInfo.getMobile());
        successOrderInfo.setRechargeMobile(orderInfo.getRechargePhone());
        successOrderInfo.setRechargeValue(orderInfo.getSaleAmount().toString());
        successOrderInfo.setRealAmount(orderInfo.getRealAmount().toString());
        successOrderInfo.setOriginalAmount(orderInfo.getOriginalAmount().toString());

        return successOrderInfo;
    }



    /**
     * 活动添加，故乡活动没有时间限制，重写父类
     * 
     * @param orderinfo
     */
    @Override
    public  void addActivity(Result result,PhoneOrderInfoPo phoneOrderInfoPo) throws FunctionException{
        //保存活动信息
        VasPhoneOneyuanFreeVo activety = new VasPhoneOneyuanFreeVo();
        activety.setOrderCode(phoneOrderInfoPo.getPhoneOrderCode());
        activety.setChargeAmount(phoneOrderInfoPo.getSaleAmount());
        activety.setChargeMobile(phoneOrderInfoPo.getRechargePhone());
        activety.setActivityAmount(phoneOrderInfoPo.getActivetyMoeny());
        activety.setCreateTime(new Date());
        activety.setCheckStatus(EnumActivetyAuditStatus.DEFAULT.getType());
        activety.setLotteryStatus(EnumActivetyRewardStatus.DEFAULT.getType());
        activety.setBizType(EnumBusinessCode.MPCZ.getBusinessName());
        activety.setAreaId(Integer.parseInt(phoneOrderInfoPo.getCountryCode()));
        activety.setAreaName(phoneOrderInfoPo.getCountryName());
        activety.setStoreId(Integer.parseInt(phoneOrderInfoPo.getStoreCode()));
        activety.setStoreName(phoneOrderInfoPo.getStoreName());

        VasPhoneOneyuanFreePo vasPhone =  activityService.addVasPhoneOneyuanFree(result, activety).getData();

        logger.info("-> 故乡-添加参与活动对象：{}",JSON.toJSONString(activety));

        //修改幸运号
        activityService.updateLuckNumById(result,vasPhone.getId(),PhoneUtils.generateLuckNumber(vasPhone.getId()));
    }
    

}
