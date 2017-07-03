package com.tsh.vas.sdm.charge;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.broker.vo.sdm.request.ResultNotifyRequest;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.sdm.service.charge.PayChargeService;
import com.tsh.vas.vo.charge.BillInfoVo;
import com.tsh.vas.vo.charge.ChargeInfoVo;
import com.tsh.vas.vo.charge.RechargeParams;

/**
 * 充值缴费接口
 */
@RestController
@RequestMapping(value = "/vas/charge/")
public class PayChargeController extends BaseController {


    private static final String HEAD_MSG = "请求返回结果：";
    private static final String USER_MSG = "用户登录信息result:";
    private static final String BACK_MSG = "开发平台充值，回调失败:";

    @Autowired
    private PayChargeService payChargeService;

    /**
     * 查询缴费机构信息
     * @param param
     * @return
     */
    @RequestMapping(value = "get/organization/info")
    public ReturnDTO getOrganizationInfo(@RequestParam(value = "param") String param) {
        logger.info ("通过开放平台获取服务商信息" + param);
        Result result = this.getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        JSONObject json = JSONObject.parseObject (param);
        String businessCode = json.getString ("businessCode");
        try {
            result = this.payChargeService.getOrganizationInfo (result, businessCode);
        } catch (Exception ex) {
            logger.error ("通过开放平台获取服务商信息失败" + result.getMsg (),ex);
            result.setStatus (500);
            result.setData ("");
            result.setMsg (ex.getMessage ());
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 获取用户账单信息
     * @param param
     * @return
     */
    @RequestMapping(value = "get/bill/info")
    public ReturnDTO getBillInfo(@RequestParam(value = "param") String param) {
        logger.info ("获取用户账单信息" + param);
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        JSONObject jsonObject = JSONObject.parseObject (param);
        RechargeParams<BillInfoVo> rechargeParams = new RechargeParams<BillInfoVo> ();
        rechargeParams.setSupplierCode (jsonObject.getString ("supplierCode"));
        rechargeParams.setSupplierToken (jsonObject.getString ("supplierToken"));
        rechargeParams.setServerAddr (jsonObject.getString ("serverAddr"));
        String dataJSONObject = jsonObject.getString ("data");
        BillInfoVo billInfoVo = JSONObject.parseObject (dataJSONObject, BillInfoVo.class);
        rechargeParams.setData (billInfoVo);

        try {
            result = this.payChargeService.getBillInfo (result, rechargeParams);
        } catch (Exception ex) {
            logger.error ("获取用户账单信息失败" + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setData (null);
            result.setMsg (ex.getMessage ());
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 新增订单接口,缴费订单密码支付
     * @param param
     * @return
     */
    @RequestMapping(value = "/pay")
    public ReturnDTO payChargeInfo(@RequestParam(value = "param") String param) {   
        logger.info ("用户缴费，新增缴费单" + param);
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        ChargeInfoVo chargeInfoVo = JSONObject.parseObject (param, ChargeInfoVo.class);
        boolean flag = this.validChargeInfo (result, chargeInfoVo);
        if (flag) {
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setStatus (500);
            return result.DTO ();
        }
        try {
            result = this.payChargeService.payChargeInfo (result, chargeInfoVo);
        } catch (Exception ex) {
            logger.error ("用户缴费，新增缴费单失败:" + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 开放平台充值完后回调
     * @param resultNotifyRequest
     * @return
     */
    @RequestMapping(value = "recharge/back")
    public ReturnDTO rechargeBack(ResultNotifyRequest resultNotifyRequest) {
        logger.info ("开发平台充值，回调" + JSONObject.toJSONString (resultNotifyRequest));
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        try {
            result = this.payChargeService.rechargeBack (result, resultNotifyRequest);
        } catch (Exception ex) {
            logger.error (BACK_MSG + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        }


        //调用，调用结算接口
        if (result.getStatus () == 200) {
            try {
                
                ChargeInfo chargeInfo = payChargeService.queryByOpenPlatformNo(result, resultNotifyRequest.getOutOrderNo ()).getData();
                boolean isOldBusiness = payChargeService.isOldBusiness(chargeInfo.getCreateTime());
                if(isOldBusiness){
                    result = this.payChargeService.paySettle (result, resultNotifyRequest.getOutOrderNo ());
                }else{
                    //调用确认结算接口 add by zengzw #2016-11-18
                    result = this.payChargeService.confirmSettle(result, resultNotifyRequest.getOutOrderNo (), false);
                }
                result.setStatus (200);

            } catch (Exception ex) {
                logger.error (BACK_MSG + result.getMsg (),ex);
                result.setStatus (200);
                result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
                result.setMsg (ex.getMessage ());
            } finally {
                send (result);
            }
        } else {
            logger.error (BACK_MSG + result.getMsg ());
            result.setStatus (200);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg ("充值回调返回错误，无法结算");
        }

        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }


    /**
     *
     * @param result
     * @param chargeInfoVo
     * @return
     */
    private boolean validChargeInfo(Result result, ChargeInfoVo chargeInfoVo) {
        boolean flag = false;
        if (StringUtils.isEmpty (chargeInfoVo.getSupplierCode ())) {
            logger.error ("请求参数错误，供应商编号不能为空");
            result.setMsg ("供应商编号不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (chargeInfoVo.getRechargeUserCode ())) {
            logger.error ("请求参数错误，户号不能为空");
            result.setMsg ("户号不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (chargeInfoVo.getRechargeUserName ())) {
            logger.error ("请求参数错误，用户姓名不能为空");
            result.setMsg ("用户姓名不能为空");
            flag = true;
        }
        if (chargeInfoVo.getRealAmount () == null || chargeInfoVo.getRealAmount ().compareTo (new BigDecimal (0)) <= 0) {
            logger.error ("请求参数错误，缴费金额不能为空");
            result.setMsg ("缴费金额不能为空");
            flag = true;
        }
        if (chargeInfoVo.getBizType () == null || chargeInfoVo.getBizType ().equals (0)) {
            logger.error ("缴费用户账号类型，付费用户账户角色类型biz_type：不能为空");
            //角色类型 2:平台管理 3:县域 4:网点，5：供应商 9：会员
            result.setMsg ("缴费用户账号类型，付费用户账户角色类型biz_type：不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (chargeInfoVo.getMobile ())) {
            logger.error ("请求参数错误，付费用户为会员时候手机号码不能为空");
            result.setMsg ("付费用户为会员时候手机号码不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (chargeInfoVo.getBusinessCode ())) {
            logger.error ("请求参数错误，业务编码不能为空");
            result.setMsg ("业务编码不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (chargeInfoVo.getChargeOrgCode ())) {
            logger.error ("请求参数错误，缴费机构编码不能为空");
            result.setMsg ("缴费机构编码不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (chargeInfoVo.getChargeOrgName ())) {
            logger.error ("请求参数错误，缴费机构名称不能为空");
            result.setMsg ("缴费机构名称不能为空");
            flag = true;
        }
        if (chargeInfoVo.getOriginalAmount () == null) {
            logger.error ("请求参数错误，应缴金额不能为空");
            result.setMsg ("应缴金额不能为空");
            flag = true;
        }
        if (chargeInfoVo.getRealAmount () == null) {
            logger.error ("请求参数错误，实缴金额不能为空");
            result.setMsg ("实缴金额不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (chargeInfoVo.getPayPassword ())) {
            logger.error ("请求参数错误，支付密码不能为空");
            result.setMsg ("支付密码不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (chargeInfoVo.getSupplierToken ())) {
            logger.error ("请求参数错误，开放平台供应商秘钥不能为空");
            result.setMsg ("开放平台供应商秘钥不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (chargeInfoVo.getServerAddr ())) {
            logger.error ("请求参数错误，供应商服务器地址不能为空");
            result.setMsg ("供应商服务器地址不能为空");
            flag = true;
        }
        if (chargeInfoVo.getRechargeUserType () == null || chargeInfoVo.getRechargeUserType ().equals (0)) {
            logger.error ("请求参数错误，缴费账户类型，缴费账户类型不能为空");
            result.setMsg ("缴费账户类型，缴费账户类型不能为空");
            flag = true;
        }

        return flag;
    }

    /**
     *
     * @param chargeCode
     * @return
     */
    @RequestMapping(value = "/print/charge/info")
    public ReturnDTO printChargeInfo(String chargeCode) {
        logger.info ("订单打印请求参数:chargeCode=" + chargeCode);
        Result result = getResult ();
        logger.info ("用户登录信息result:" + JSONObject.toJSONString (result));

        boolean flag = false;
        if (StringUtils.isEmpty (chargeCode)) {
            logger.error ("请求参数错误，缴费订单编号不能为空");
            result.setMsg ("缴费订单编号不能为空");
            flag = true;
        }
        if (flag) {
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setStatus (500);
            return result.DTO ();
        }
        try {
            result = this.payChargeService.printChargeInfo (result, chargeCode);
        } catch (Exception ex) {
            logger.error ("打印订单失败" + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }
}
