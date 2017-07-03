/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.bean.ReturnStatusEnum;
import com.dtds.platform.util.exception.FunctionException;
import com.google.common.collect.Maps;
import com.tsh.vas.commoms.constants.FundURLConstants;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.iservice.IMemberService;
import com.tsh.vas.trainticket.commoms.HttpUtils;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.vo.phone.MemberVo;

/**
 * 会员管理服务类
 * 
 * @author zengzw
 * @date 2017年5月27日
 */
@Service
public class MemberServiceImpl implements IMemberService{

    private final static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);


    @Override
    public MemberVo getMemberInfoForApp(String mobile, String memberName, String token) throws FunctionException {
        if(StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(memberName)
                || StringUtils.isEmpty(token)){
            return null;
        }
        Result result = new Result();
        Map<String, Object> params = new HashMap<>();
        params.put("mobile",mobile);
        params.put("memberName",mobile);
        params.put("token",token);

        String mbrUrl = TshDiamondClient.getInstance ().getConfig ("mbr-url") + FundURLConstants.ACC_MEMEBER_FOR_APP;
        String mbrResponse = HttpUtils.doPost(mbrUrl, params);

        logger.info("#----->获取充值会员的信息：{}", mbrResponse);

        if (StringUtils.isEmpty (mbrResponse)) {
            logger.error ("获取充值会员信息异常");
            throw new FunctionException (result, "获取充值手机会员信息异常");
        }

        ReturnDTO returnDTO = JSONObject.parseObject (mbrResponse, ReturnDTO.class);
        if (HttpResponseConstants.SUCCESS != returnDTO.getStatus()) {
            logger.error ("获取充值手机会员信息异常:",returnDTO.getMsg());
            throw new FunctionException (result, "获取充值手机会员信息异常:"+returnDTO.getMsg());
        }

        MemberVo memberVo = JSONObject.parseObject (JSONObject.toJSONString (returnDTO.getData ()), MemberVo.class);
        return memberVo;
    }


    @Override
    public MemberVo getMemberInfoByMemberId(String memberId) throws FunctionException {
        if(StringUtils.isEmpty(memberId)){
            return null;
        }

        Result result = new Result();
        Map<String, Object> params = new HashMap<>();
        params.put("memberId",memberId);


        String mbrUrl = TshDiamondClient.getInstance ().getConfig ("mbr-url") + FundURLConstants.ACC_MEMEBER_INFO_BY_ID;
        String mbrResponse = HttpUtils.doPost(mbrUrl, params);

        logger.info("#----->获取会员的信息：{}", mbrResponse);

        if (StringUtils.isEmpty (mbrResponse)) {
            logger.error ("获取会员信息异常");
            throw new FunctionException (result, "获取会员信息异常");
        }

        ReturnDTO returnDTO = JSONObject.parseObject (mbrResponse, ReturnDTO.class);
        if (HttpResponseConstants.SUCCESS != returnDTO.getStatus()) {
            logger.error ("获取会员信息异常");
            throw new FunctionException (result, "获取会员信息异常");
        }

        MemberVo memberVo = JSONObject.parseObject (JSONObject.toJSONString (returnDTO.getData ()), MemberVo.class);
        return memberVo;
    }



    @Override
    public MemberVo getMemberInfoByMobile(String mobile, String token) throws FunctionException {
        //获取会员信息
        Map<String, Object> mbrParam = Maps.newHashMap ();
        mbrParam.put ("reqSource", "b2c");
        mbrParam.put ("sysType", 2);
        mbrParam.put ("token", token);
        mbrParam.put ("mobile", mobile);
        String mbrUrl = TshDiamondClient.getInstance ().getConfig ("mbr-url") + FundURLConstants.ACC_MEMEBER_INFO;
        String mbrResponse = HttpUtils.doPost(mbrUrl, mbrParam);

        logger.info("----->获取会员的信息：{}", mbrResponse);

        Result result = new Result();
        if (StringUtils.isEmpty (mbrResponse)) {
            logger.error ("获取会员信息异常");
            throw new FunctionException (result, "获取会员信息异常");
        }
        ReturnDTO returnDTO = JSONObject.parseObject (mbrResponse, ReturnDTO.class);
        if (HttpResponseConstants.SUCCESS != returnDTO.getStatus()) {
            logger.error ("获取会员信息异常");
            throw new FunctionException (result, "获取会员信息异常");
        }

        MemberVo memberVo = JSONObject.parseObject (JSONObject.toJSONString (returnDTO.getData ()), MemberVo.class);

        return memberVo;
    }


    @Override
    public boolean validateStoreUser(String password, Long userId) throws FunctionException {
        logger.info("----->网点支付密码校验");
        boolean isValidate = false;

        //网点密码验证
        Map<String, Object> storeParam = Maps.newHashMap ();
        storeParam.put ("payPwd",password);
        storeParam.put ("userId",userId);
        String url = TshDiamondClient.getInstance ().getConfig ("acc-url") + FundURLConstants.ACC_VALIDATE_OUTPAY_PASSWORD;
        String response = HttpUtils.doPost(url, storeParam);

        Result result = new Result();
        if (StringUtils.isEmpty (response)) {
            logger.error ("------>网点密码验证失败");
            throw new FunctionException (result, "密码验证失败");
        }
        
        Result storeResult = JSONObject.parseObject (response, Result.class);
        if(null == storeResult || null == storeResult.getData()){
            logger.error ("---------->验证密码返回结果为空:" + JSON.toJSONString(response));
            throw new FunctionException (result, "验证密码返回结果为空");
        }
        
        if (storeResult.getData ()) {
            logger.info ("------>密码验证成功");
            isValidate = true;

        } else {
            logger.error ("---------->密码错误");
//            throw new FunctionException (result, "密码错误");
            isValidate = false;
        }

        return isValidate;
    }


    @Override
    public boolean validateMemberUser(String mobile,String password, Long memberId) throws FunctionException {
        logger.info("----->会员支付密码校验");
        
        boolean isValidate = false;
        String url = TshDiamondClient.getInstance ().getConfig ("mbr-url") +FundURLConstants.ACC_VALIDATE_MEMBERCARD;
        Map<String, Object> param = Maps.newHashMap ();
        param.put ("mobile",mobile);
        param.put ("memberId", memberId);
        param.put ("payPassword", password);
        String response = HttpUtils.doPost(url, param);
        
        Result result = new Result();
        if (StringUtils.isEmpty (response)) {
            logger.error ("------->会员密码验证错误");
            throw new FunctionException (result, "密码验证错误");
        }
        
        JSONObject resultJson = JSONObject.parseObject (response);
        int resultFlag = resultJson.getIntValue ("status");
        String message = resultJson.getString ("msg");
        if (resultFlag == ReturnStatusEnum.OK.getValue ()) {
            logger.info ("------>密码验证成功");
            
            isValidate = true;
            
        } else {
            
            logger.error ("---->密码验证错误：" + message);
            throw new FunctionException (result, message);
        }
        
        return isValidate;
    }

}
