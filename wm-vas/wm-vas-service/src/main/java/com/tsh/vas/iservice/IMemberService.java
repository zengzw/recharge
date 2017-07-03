/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.iservice;

import com.dtds.platform.util.exception.FunctionException;
import com.tsh.vas.vo.phone.MemberVo;

/**
 * 会员接口 
 *
 * @author zengzw
 * @date 2017年5月27日
 */
public interface IMemberService {
    
    
    
    /**
     * 领取优惠券获取会员信息（是会员就直接返回会员信息，不是就自动帮其号码注册，然后返回会员信息）
     * 
     * @param mobile
     * @param memberName
     * @param token
     * @return
     */
    public MemberVo getMemberInfoForApp(String mobile,String memberName,String token) throws FunctionException;
    
    
    
    /**
     * 根据会员Id 查找会员信息
     * 
     * @param memberId
     * @return
     */
    public MemberVo getMemberInfoByMemberId(String memberId) throws FunctionException;
    
    
    
    /**
     * 根据手机号码获取会员信息 
     * 
     * 
     * @param mobile 手机号码
     * @param token  登录token
     * @return  会员相关信息
     * @throws FunctionException 事务回滚
     */
    public MemberVo getMemberInfoByMobile(String mobile,String token)throws FunctionException;
    
    
    
    
    /**
     * 网点密码校验
     * 
     * 
     * @param password
     * @param userId
     * @return
     */
    public boolean validateStoreUser(String password,Long userId)throws FunctionException;
    
    
    /**
     * 会员密码校验
     * 
     * @param mobile 会员手机号码
     * @param password  密码
     * @param memberId  会员Id
     * @return
     */
    public boolean validateMemberUser(String mobile,String password,Long memberId)throws FunctionException;
    

}
