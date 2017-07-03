package com.tsh.vas.sdm.charge;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.BillDetailInfo;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.sdm.service.charge.ChargeService;
import com.tsh.vas.vo.charge.ChargeSearchVo;

/**
 * Created by Iritchie.ren on 2016/10/8.
 */
@RestController
@RequestMapping("vas/charge/")
public class ChargeController extends BaseController {

    private static final String HEAD_MSG = "请求返回结果：";
    private static final String USER_MSG = "用户登录信息:";

    @Autowired
    private ChargeService chargeService;

    /**
     * 屏端查询订单列表
     * @param param
     * @return
     */
    @RequestMapping(value = "query")
    public ReturnDTO query(String param) {
        logger.info ("查询用户缴费单" + param);
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        ChargeSearchVo chargeSearchVo = JSONObject.toJavaObject (JSONObject.parseObject (param), ChargeSearchVo.class);
        try {
            chargeSearchVo.setStoreCode (String.valueOf (result.getUserInfo ().getBizId ()));
            result = this.chargeService.query (result, chargeSearchVo);
        } catch (Exception ex) {
            logger.error ("查询用户缴费单失败" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setStatus (500);
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 屏端查询订单详情
     * @param param
     * @return
     */
    @RequestMapping(value = "details")
    public ReturnDTO details(String param) {
        logger.info ("查询用户缴费单详情" + param);
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        JSONObject parmaJson = JSONObject.parseObject (param);
        String chargeCode = parmaJson.getString ("chargeCode");
        try {
            result = this.chargeService.details (result, chargeCode);
        } catch (Exception ex) {
            logger.error ("查询用户缴费单详情失败" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setStatus (500);
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     *
     * @param chargeCode
     * @return
     */
    @RequestMapping(value = "billsDetails")
    public ReturnDTO billsDetail(String chargeCode) {
        logger.info ("查询用户缴费单详情" + chargeCode);
        Result result = this.getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        try {
            result = this.chargeService.details (result, chargeCode);
            ChargeInfo chargeInfo = result.getData();

            BillDetailInfo.CommissionInfo commissionInfo = new BillDetailInfo.CommissionInfo();
            commissionInfo.setSalePrice(String.valueOf(chargeInfo.getOriginalAmount()));
            BigDecimal commission = this.calculateCommission(chargeInfo.getOriginalAmount(), new BigDecimal(chargeInfo.getLiftCoefficient()));

            commissionInfo.setCommissionRatio(String.valueOf(chargeInfo.getLiftCoefficient()));
            commissionInfo.setCountyRatio(String.valueOf(chargeInfo.getAreaCommissionRatio()));

            commissionInfo.setCountyCommission(convertCommission(commission,new BigDecimal(chargeInfo.getAreaCommissionRatio())));
            commissionInfo.setShopRatio(String.valueOf(chargeInfo.getStoreCommissionRatio()));
            commissionInfo.setShopCommission(convertCommission(commission,new BigDecimal(chargeInfo.getStoreCommissionRatio())));

            BillDetailInfo billDetailInfo = new BillDetailInfo();
            billDetailInfo.setChargeCode(chargeInfo.getChargeCode());
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
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));

        return result.DTO ();
    }

    /**
     *
     * @param amount
     * @param ratio
     * @return
     */
    private BigDecimal calculateCommission(BigDecimal amount,BigDecimal ratio){
        BigDecimal i = amount == null ? BigDecimal.valueOf(0) : amount ;
        BigDecimal j = ratio == null ? BigDecimal.valueOf(1) : ratio ;

        return  i.multiply(j).divide(BigDecimal.valueOf(100));
    }

    /**
     *
     * @param commission
     * @param ratio
     * @return
     */
    private String convertCommission(BigDecimal commission,BigDecimal ratio){
        BigDecimal i = commission == null ? BigDecimal.valueOf(0) : commission ;
        BigDecimal j = ratio == null ? BigDecimal.valueOf(1) : ratio ;

        BigDecimal multiply =  BigDecimal.valueOf(j.doubleValue() * 0.01);

       return  String.valueOf(i.multiply(multiply).setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }

}
