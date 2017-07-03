package com.tsh.traintickets.web.controller.kuyou.query;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Result;
import com.google.gson.reflect.TypeToken;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.TicketsConstants;
import com.traintickets.common.utils.JsonUtils;
import com.tsh.traintickets.bo.kuyou.BaseBO;
import com.tsh.traintickets.dal.service.kuyou.KuyouService;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.traintickets.vo.ResponseBuilder;
import com.tsh.traintickets.vo.convert.ConvertRequest2BO;
import com.tsh.traintickets.vo.request.VerifyUserModel;
import com.tsh.traintickets.vo.request.VerifyUsersRequest;

import com.tsh.traintickets.vo.response.VefifyUsersResponse;
import com.tsh.traintickets.vo.sign.KuyouRequestSign;
import com.tsh.traintickets.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 验证用户信息接口
 * Created by Administrator on 2016/11/19 019.
 */
@Controller
@RequestMapping("/traintickets/kuyou")
public class VerifyUsersController extends BaseController {

    @Autowired
    KuyouService kuyouService;

    @RequestMapping(value = "/verifyUsers.do", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse verifyUsers(VerifyUsersRequest request) {
        Result result = super.getResult();
        try {
            this.validateParam(request);

            BaseBO verifyResult = kuyouService.verifyUsers(ConvertRequest2BO.convertVerifyUsers(request));
            VefifyUsersResponse response = new VefifyUsersResponse();
            if (null != verifyResult && TicketsConstants.SUCCESS_CODE.equals(verifyResult.getReturn_code())) {
                response.setFailUserNameList(Collections.<String>emptyList());
                response.setValidateFailList(Collections.<String>emptyList());
                result.setData(ResponseBuilder.buildSuccess(response));
            } else {
                // 判断是否验证失败
                if(null != verifyResult && TicketsConstants.VERIFY_USER_FAIL.equals(verifyResult.getReturn_code())){
                    response.setFailUserNameList(this.getVerifyFailUserNameList(request, verifyResult.getMessage()));
                }
                // 判断是否验证重复
                if(null != verifyResult && TicketsConstants.VERIFY_USER_REPEAT.equals(verifyResult.getReturn_code())){
                    response.setFailUserNameList(this.getVerifyRepeatUserNameList(request));
                }
                response.setValidateFailList(Collections.<String>emptyList());
                result.setData(ResponseBuilder.buildSuccess(response));
            }
        } catch (BizException be) {
            logger.error(be.getMessage(), be);
            result.setData(ResponseBuilder.buildError(be.getCode(), be.getNotice()));
            this.error(result, be);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setData(ResponseBuilder.buildError(ResponseCode.SYSTEM_ERROR));
            this.error(result, e);
        } finally {
            this.send(result);
        }

        logger.info("-------------> verifyUserReturn：" + JSON.toJSONString(result.getData()));
        logger.info("-----------------> i see you  !!!!");
        return result.getData();
    }

    private List<String> getVerifyRepeatUserNameList(VerifyUsersRequest request){
        List<String> userNameList = new ArrayList();
        List<VerifyUserModel> userList = JsonUtils.convert2List(request.getUserList(), new TypeToken<List<VerifyUserModel>>(){}.getType());
        if(null != userList && !userList.isEmpty()){
            for(VerifyUserModel userModel : userList){
                userNameList.add(userModel.getUserName());
            }
        }
        return userNameList;
    }

    private List<String> getVerifyFailUserNameList(VerifyUsersRequest request, String returnMsg){
        List<String> userNameList = new ArrayList();
        List<VerifyUserModel> userList = JsonUtils.convert2List(request.getUserList(), new TypeToken<List<VerifyUserModel>>(){}.getType());
        if(null != userList && !userList.isEmpty() && StringUtils.isNotEmpty(returnMsg)){
            for(VerifyUserModel userModel : userList){
                if(returnMsg.contains(userModel.getUserName())){
                    userNameList.add(userModel.getUserName());
                }
            }
        }
        return userNameList;
    }

    private void validateParam(VerifyUsersRequest request) {
        if (StringUtils.isEmpty(request.getUserList())) {
            super.throwParamException("userList is null");
        }
        List<VerifyUserModel> userList = JsonUtils.convert2List(request.getUserList(), new TypeToken<List<VerifyUserModel>>(){}.getType());

        if (null == userList || userList.isEmpty()) {
            super.throwParamException("userList is null");
        }
        for (VerifyUserModel user : userList) {
            if (StringUtils.isEmpty(user.getIdType())) {
                super.throwParamException("idType is null");
            }
            if (StringUtils.isEmpty(user.getUserId())) {
                super.throwParamException("userId is null");
            }
            if (StringUtils.isEmpty(user.getUserName())) {
                super.throwParamException("userName is null");
            }
        }

        if("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.isValidateSign))) {
            logger.info("------------------> valiate sign");
            super.validateSign(request.getSignKey());

            String sign = KuyouRequestSign.signVerifyUsers(request, DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey));
            if(!request.getSignKey().equals(sign)){
                super.throwParamException("signKey validate error");
            }
        }
    }

}
