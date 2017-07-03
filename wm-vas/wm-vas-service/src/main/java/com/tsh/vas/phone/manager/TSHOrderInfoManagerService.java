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

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.dubbo.bis.vo.ShopVO;
import com.tsh.phone.util.DateUtils;
import com.tsh.vas.enume.RoleType;
import com.tsh.vas.iservice.IMemberService;
import com.tsh.vas.iservice.IPayService;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.phone.constants.PhoneConstants;
import com.tsh.vas.phone.enums.EnumPhoneOrderInfoPayStatus;
import com.tsh.vas.phone.enums.EnumRequestOrderType;
import com.tsh.vas.phone.facade.APIAppPhoneService;
import com.tsh.vas.phone.service.PhoneOrderInfoService;
import com.tsh.vas.sdm.service.business.BusinessService;
import com.tsh.vas.service.ShopApiService;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.trainticket.vo.MemberResultVo;
import com.tsh.vas.utils.GenerateOrderNumber;
import com.tsh.vas.vo.phone.MemberVo;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;
import com.tsh.vas.vo.phone.api.APISuccessOrderInfo;

/**
 * 淘实惠-订单管理服务类
 * 
 * 
 * @author zengzw
 * @date 2017年5月26日
 */
@Service("tshOrderInfoService")
public class TSHOrderInfoManagerService extends AbstOrderInfoManagerService{

    private final static Logger logger = LoggerFactory.getLogger(APIAppPhoneService.class);

    @Autowired
    private BusinessService businessService;

    @Autowired 
    ShopApiService shopApiService;

    
    @Resource(name="tshPayService")
    private IPayService payService;

    
    @Autowired
    private IMemberService memberService;
    

    /**
     * 话费订单服务类
     * 
     */
    @Autowired 
    private PhoneOrderInfoService orderInfoService;



    @Override
    public PhoneOrderInfoPo createOrder(Result result, PhoneOrderInfoVo orderInfoVo) throws Exception {
        //得到用户登录信息
        UserInfo userInfo = result.getUserInfo ();
        //根据用户所属县域ID获取所属城市
        ShopVO shopVO =  shopApiService.getShop (result, userInfo.getBizId ());


        //获取会员信息,校验密码
        MemberResultVo memberResult = this.memberVerification(result, orderInfoVo.getMobile(), orderInfoVo.getBizType(), orderInfoVo.getPayPassword());
        if (orderInfoVo.getBizType ().equals (RoleType.SHOP.getCode ())) {
            memberResult.setUserId(Integer.parseInt(shopVO.getUserId()+""));
            memberResult.setUserName(shopVO.getContact ());
            memberResult.setMobiel(shopVO.getContactTel ());
        }


        JSONObject storeInfoJson = new JSONObject ();
        storeInfoJson.put ("contact", shopVO.getContact ());
        storeInfoJson.put ("contactTel", shopVO.getContactTel ());
        //订单编号=业务编号+时间码
        String orderCode = orderInfoVo.getBusinessCode () + GenerateOrderNumber.getOrderNumber ();
        PhoneOrderInfoPo orderInfoPo = new PhoneOrderInfoPo();
        orderInfoPo.setSources(EnumRequestOrderType.TSH.name());
        
        orderInfoPo.setPhoneOrderCode(orderCode);
        orderInfoPo.setStoreNo(shopVO.getShopNo()); //网点编号
        orderInfoPo.setStoreCode (String.valueOf (userInfo.getBizId ())); //网点ID
        orderInfoPo.setStoreName (shopVO.getShopName ());
        orderInfoPo.setStoreInfo (storeInfoJson.toJSONString ());
        orderInfoPo.setProvince (shopVO.getContactProvince ());
        orderInfoPo.setCity (shopVO.getContactCity ());
        orderInfoPo.setCountry (shopVO.getContactArea ());
        orderInfoPo.setCountryCode (String.valueOf (shopVO.getAreaId ()));
        orderInfoPo.setCountryName (shopVO.getAreaName ());
        orderInfoPo.setBizId (Long.parseLong(memberResult.getBizId().toString()));
        orderInfoPo.setBizType (memberResult.getBizType());
        orderInfoPo.setPayUserId (Long.parseLong(memberResult.getUserId().toString()));
        orderInfoPo.setMobile (memberResult.getMobiel());
        orderInfoPo.setLinkName(orderInfoVo.getPayUserName());  //联系人
        orderInfoPo.setLinkPhone(orderInfoVo.getMobile());  //联系电话
        orderInfoPo.setActivetyMoeny(orderInfoVo.getJoinMoney());

        
        //添加充值手机会员Id
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

        orderInfoPo.setRechargePhone(orderInfoVo.getRechargePhone());//充值手机号码
        orderInfoPo.setSaleAmount(orderInfoVo.getSaleAmount()); //面值
        orderInfoPo.setOriginalAmount(originalAmount); //应付金额
        orderInfoPo.setRealAmount (realAmount);//实际支付价格

        orderInfoPo.setGoodsId(orderInfoVo.getGoodsId()); //商品ID
        orderInfoPo.setCreateTime (new Date ());
        orderInfoPo.setPayStatus (EnumOrderInfoPayStatus.NON_PAYMENT.getCode ());
        orderInfoPo.setPayUserName (memberResult.getUserName());
        orderInfoPo.setMemberMobile (orderInfoVo.getMobile ());
        orderInfoPo.setMemberName (memberResult.getMemberName());
        orderInfoPo.setJoinStatus(orderInfoVo.getJoinStatus());
        
        orderInfoService.addPhoneOrderInfo(result, orderInfoPo);

        
        return result.getData();
    }


    

    /**
     * 校验会员信息
     * 
     * @param mobile 支付手机号码
     * @param bizType 登录账户类型
     * @param password 支付密码
     * @param userId   登录ID
     * @return
     * @throws FunctionException 
     */
    private MemberResultVo  memberVerification(Result result,String mobile,Integer bizType,String password) throws FunctionException{
        MemberResultVo memberResultVo = new MemberResultVo();
        UserInfo userInfo = result.getUserInfo();

        //获取会员信息
        MemberVo memberVo = memberService.getMemberInfoByMobile(mobile, userInfo.getSessionId());
        if(memberVo ==  null){
            throw new FunctionException (result, "获取会员信息为空");
        }

        if (bizType.equals (RoleType.SHOP.getCode ())) {
            logger.info("----->网点支付密码校验");

            boolean isValidate =  memberService.validateStoreUser(password, userInfo.getUserId ());
            if (!isValidate) {
                throw new FunctionException (result, "密码错误");
            }

            memberResultVo.setBizId(userInfo.getBizId().intValue());
            memberResultVo.setBizType(RoleType.SHOP.getCode ());
            memberResultVo.setMemberName(memberVo.getMemberName());

        } else if (bizType.equals (RoleType.MEMBER.getCode ())) {
            logger.info("-----> 会员密码校验.");

            boolean isValidate = memberService.validateMemberUser(mobile, password, memberVo.getId ());
            if(!isValidate){
                throw new FunctionException (result, "校验会员密码错误");
            }

            memberResultVo.setBizId(memberVo.getId().intValue());
            memberResultVo.setUserId(memberVo.getUserID().intValue());
            memberResultVo.setBizType(RoleType.MEMBER.getCode ());
            memberResultVo.setUserName(memberVo.getMemberName());
            memberResultVo.setMobiel(mobile);
            memberResultVo.setMemberName(memberVo.getMemberName());
        } else {
            logger.error ("==>账户类型不存在");
            throw new FunctionException (result, "账户类型不存在");
        }

        return memberResultVo;
    }

    
    
    
    /**
     * 支付
     */
    public Result pay(Result result, PhoneOrderInfoPo orderInfo) throws Exception {

        payService.pay(result, orderInfo);
        
        if(result.getStatus() == HttpResponseConstants.SUCCESS){
            //订单状态为支付中，后面有个支付消息中间件回调继续下面的步骤
            this.orderInfoService.updateStatus (result, orderInfo.getPhoneOrderCode(), EnumPhoneOrderInfoPayStatus.PAIDING.getCode ());

            logger.info("#----【5】话费订单号：{}支付同步接口成功，订单状态位：支付中【2】",orderInfo.getPhoneOrderCode());

            result.setStatus(HttpResponseConstants.SUCCESS);
            result.setCode(HttpResponseConstants.SUCCESS);
            result.setMsg("支付成功！");
            result.setData(buildResult(orderInfo));
            return result;
        }else{
            this.orderInfoService.updateStatus (result, orderInfo.getPhoneOrderCode(), EnumPhoneOrderInfoPayStatus.PAY_FAIL.getCode ());

            logger.info("#----【6】话费订单：{}支付同步接口成功，订单状态位：支付异常【6】",orderInfo.getPhoneOrderCode());

            result.setStatus (HttpResponseConstants.SUCCESS);
            result.setCode(HttpResponseConstants.ERROR);
            result.setMsg("支付失败！");
            return result;
        }

    }


    /**
     * 结果构建
     */
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


}
