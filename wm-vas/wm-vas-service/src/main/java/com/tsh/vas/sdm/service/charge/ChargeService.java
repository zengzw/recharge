package com.tsh.vas.sdm.service.charge;


import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.google.common.collect.Lists;
import com.tsh.vas.dao.BusinessInfoDao;
import com.tsh.vas.dao.ChargeInfoDao;
import com.tsh.vas.dao.ChargePaymentInfoDao;
import com.tsh.vas.dao.ChargeRefundDao;
import com.tsh.vas.enume.ChargePayStatus;
import com.tsh.vas.enume.ChargeRefundStatus;
import com.tsh.vas.enume.DivideRule;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.model.ChargePaymentInfo;
import com.tsh.vas.model.ChargeRefund;
import com.tsh.vas.model.OrderReport;
import com.tsh.vas.utils.NumberUtil;
import com.tsh.vas.vo.business.OrderReportVo;
import com.tsh.vas.vo.business.QueryOrderParamVo;
import com.tsh.vas.vo.business.QueryOrderResultVo;
import com.tsh.vas.vo.charge.ChargeSearchVo;

/**
 * Created by Iritchie.ren on 2016/10/8.
 */
@Service
public class ChargeService {

    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private ChargeInfoDao chargeInfoDao;

    @Autowired
    private ChargePaymentInfoDao chargePaymentInfoDao;

    @Autowired
    private ChargeRefundDao chargeRefundDao;

    @Autowired
    private BusinessInfoDao businessInfoDao;

    public Result queryByChargeCode(Result result, String chargeCode){
        return chargeInfoDao.queryByChargeCode(result, chargeCode);
    }

    public Result getChargeRefundByChargeCode(Result result, String chargeCode){
        return chargeRefundDao.getChargeRefundByChargeCode(result, chargeCode);
    }


    /**
     * 屏端查询订单列表
     *
     * @param result
     * @param chargeSearchVo
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result query(Result result, ChargeSearchVo chargeSearchVo) throws Exception {
        Pagination pagination = this.chargeInfoDao.query (result, chargeSearchVo).getData ();
        List<ChargeInfo> rows = (List<ChargeInfo>) pagination.getRows ();

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (rows);
        return result;
    }

    /**
     * 查询订单详情
     *
     * @param result
     * @param chargeCode
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result details(Result result, String chargeCode) throws Exception {
        ChargeInfo chargeInfo = this.chargeInfoDao.queryByChargeCode (result, chargeCode).getData ();

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (chargeInfo);
        return result;
    }

    /**
     * 查询导出
     *
     * @param result
     * @param queryOrderParamVo
     * @return
     */
    public Result getChargeInfoByPageExport(Result result, QueryOrderParamVo queryOrderParamVo) throws Exception {
        List<OrderReport> orderReports = chargeInfoDao.getChargeInfoByPageExport (result, queryOrderParamVo).getData ();
        List<OrderReportVo> orderReportVos = Lists.newArrayList ();
        BigDecimal hundred = new BigDecimal (100);
        for (OrderReport orderReport : orderReports) {
            OrderReportVo orderReportVo = new OrderReportVo ();
            BeanUtils.copyProperties (orderReport, orderReportVo);
            //价格计算
            //话费充值-销售差额
            BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, orderReport.getBusinessCode ()).getData ();
            BigDecimal areaAmount = null;
            String liftCoefficientStr = null;
            BigDecimal storeAmount = null;
            if (DivideRule.MARGIN_AMOUNT.getCode ().intValue() == businessInfo.getDivideRule ().intValue()) {
                //应缴金额-成本金额
                BigDecimal allDecimal = orderReport.getRealAmount ().subtract (orderReport.getCostingAmount ());
                BigDecimal platformDividedRatio = BigDecimal.valueOf (orderReport.getPlatformDividedRatio ()).divide (hundred);
                BigDecimal areaDividedRatio = BigDecimal.valueOf (orderReport.getAreaDividedRatio ()).divide (hundred);
                BigDecimal areaBigDecimal = allDecimal.subtract (allDecimal.multiply (platformDividedRatio));
                storeAmount = allDecimal.subtract (areaBigDecimal);
                liftCoefficientStr = orderReport.getOriginalAmount ().subtract (orderReport.getCostingAmount ()).toString ();
                areaAmount = areaBigDecimal.multiply (areaDividedRatio);
            } else if (DivideRule.FULL_AMOUNT.getCode ().intValue() == businessInfo.getDivideRule ().intValue()) {
                //提点系数,分成金额
                BigDecimal liftCoefficient = new BigDecimal (orderReport.getLiftCoefficient ()).divide (hundred);
                BigDecimal fcAmount = orderReport.getCostingAmount ().multiply (liftCoefficient);

                //县域佣金比,县域佣金
                BigDecimal areaYj = new BigDecimal (orderReport.getAreaCommissionRatio ()).divide (hundred);
                areaAmount = NumberUtil.getTwo (fcAmount.multiply (areaYj));

                //网点佣金
                storeAmount = NumberUtil.getTwo (fcAmount.subtract (areaAmount));
                liftCoefficientStr = orderReport.getLiftCoefficient () + "%" + "(" + NumberUtil.getTwo (fcAmount) + ")";
            }
            if(areaAmount != null)
                orderReportVo.setAreaCommissionYuan (areaAmount.toString ()); //县域佣金（元）
            if(storeAmount != null)
                orderReportVo.setStoreCommissionYuan (storeAmount.toString ());    //网点佣金（元）
            orderReportVo.setLiftCoefficient (liftCoefficientStr);//提点系数
            orderReportVo.setPayStatusStr (ChargePayStatus.getEnume (orderReport.getPayStatus ()).getDesc ());
            orderReportVo.setRefundStatusStr (ChargeRefundStatus.getEnume (orderReport.getRefundStatus ()).getClientDesc ());
            orderReportVos.add (orderReportVo);
        }

        result.setData (orderReportVos);
        return result;
    }

    /**
     * 分页查询订单报表
     *
     * @param result
     * @param queryOrderParamVo
     * @return
     * @throws Exception
     */
    public Result getChargeInfoByPage(Result result, QueryOrderParamVo queryOrderParamVo) throws Exception {
        Pagination pagination = chargeInfoDao.getChargeInfoByPage (result, queryOrderParamVo).getData ();
        List<ChargeInfo> chargeInfoList = (List<ChargeInfo>) pagination.getRows ();
        List<QueryOrderResultVo> queryOrderResultVos = Lists.newArrayList ();

        for (ChargeInfo chargeInfo : chargeInfoList) {
            QueryOrderResultVo queryOrderResultVo = new QueryOrderResultVo ();
            //查询缴费单支付信息表
            ChargePaymentInfo chargePaymentInfo = chargePaymentInfoDao.getChargePaymentInfoByChargeCode (result, chargeInfo.getChargeCode ()).getData ();
            if (null != chargePaymentInfo) {
                queryOrderResultVo.setPayWay (chargePaymentInfo.getPayWay ());
            }
            //查询该单是否有退款信息
            ChargeRefund chargeRefund = chargeRefundDao.getChargeRefundByChargeCode (result, chargeInfo.getChargeCode ()).getData ();
            if (null != chargeRefund) {
                queryOrderResultVo.setRefundAmount (chargeRefund.getRefundAmount ());
                queryOrderResultVo.setRefundCode (chargeRefund.getRefundCode ());
            }
            queryOrderResultVo.setBusinessCode (chargeInfo.getBusinessCode ());
            queryOrderResultVo.setBusinessName (chargeInfo.getBusinessName ());
            queryOrderResultVo.setChargeCode (chargeInfo.getChargeCode ());
            queryOrderResultVo.setChargeOrgName (chargeInfo.getChargeOrgName ());
            queryOrderResultVo.setCity (chargeInfo.getCity ());
            queryOrderResultVo.setCostingAmount (chargeInfo.getCostingAmount ());
            queryOrderResultVo.setCountry (chargeInfo.getCountry ());
            queryOrderResultVo.setCountryCode (chargeInfo.getCountryCode ());
            queryOrderResultVo.setCountryName (chargeInfo.getCountryName ());
            queryOrderResultVo.setCreateTime (chargeInfo.getCreateTime ());
            queryOrderResultVo.setOriginalAmount (chargeInfo.getOriginalAmount ());
            queryOrderResultVo.setPayStatus (chargeInfo.getPayStatus ());
            queryOrderResultVo.setProvince (chargeInfo.getProvince ());
            queryOrderResultVo.setRealAmount (chargeInfo.getRealAmount ());
            queryOrderResultVo.setRechargeUserName (chargeInfo.getRechargeUserName ());
            queryOrderResultVo.setMobile (chargeInfo.getMemberMobile ());
            queryOrderResultVo.setPayUserName (chargeInfo.getMemberName ());
            queryOrderResultVo.setRechargeUserCode (chargeInfo.getRechargeUserCode ());
            queryOrderResultVo.setSubBusinessCode (chargeInfo.getSubBusinessCode ());
            queryOrderResultVo.setSubBusinessName (chargeInfo.getSubBusinessName ());
            queryOrderResultVo.setSupplierCode (chargeInfo.getSupplierCode ());
            queryOrderResultVo.setSupplierName (chargeInfo.getSupplierName ());
            queryOrderResultVo.setCreateTimeStr (chargeInfo.getCreateTimeStr ());
            queryOrderResultVo.setRefundStatus (chargeInfo.getRefundStatus ());
            queryOrderResultVo.setStoreName (chargeInfo.getStoreName ());
            queryOrderResultVo.setRealAmount (chargeInfo.getRealAmount ()); //实缴金额
            //比例
            queryOrderResultVo.setPlatformDividedRatio (chargeInfo.getPlatformDividedRatio ());
            queryOrderResultVo.setAreaDividedRatio (chargeInfo.getAreaDividedRatio ());
            queryOrderResultVo.setAreaCommissionRatio (chargeInfo.getAreaCommissionRatio ());
            queryOrderResultVo.setStoreCommissionRatio (chargeInfo.getStoreCommissionRatio ());
            queryOrderResultVo.setId (chargeInfo.getId ());

            queryOrderResultVo.setMemberMobile (chargeInfo.getMemberMobile ());
            queryOrderResultVo.setMemberName (chargeInfo.getMemberName ());

            BigDecimal hundred = new BigDecimal (100);
            //话费充值-销售差额
            BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, queryOrderResultVo.getBusinessCode ()).getData ();
            BigDecimal areaAmount = null;
            String liftCoefficientStr = null;
            BigDecimal storeAmount = null;
            if (DivideRule.MARGIN_AMOUNT.getCode ().intValue() == businessInfo.getDivideRule ().intValue()) {
                //应缴金额-成本金额
                BigDecimal allDecimal = chargeInfo.getRealAmount ().subtract (chargeInfo.getCostingAmount ());
                BigDecimal platformDividedRatio = BigDecimal.valueOf (chargeInfo.getPlatformDividedRatio ()).divide (hundred);
                areaAmount = allDecimal.subtract (allDecimal.multiply (platformDividedRatio));
                storeAmount = allDecimal.subtract (areaAmount);
                liftCoefficientStr = chargeInfo.getOriginalAmount ().subtract (chargeInfo.getCostingAmount ()).toString ();
            } else if (DivideRule.FULL_AMOUNT.getCode ().intValue() == businessInfo.getDivideRule ().intValue()) {
                //提点系数
                BigDecimal liftCoefficient = new BigDecimal (chargeInfo.getLiftCoefficient ()).divide (hundred);
                //分成金额
                BigDecimal fcAmount = chargeInfo.getCostingAmount ().multiply (liftCoefficient);
                //县域佣金比
                BigDecimal areaYj = new BigDecimal (chargeInfo.getAreaCommissionRatio ()).divide (hundred);
                //县域佣金
                areaAmount = NumberUtil.getTwo (fcAmount.multiply (areaYj));
                //网点佣金
                storeAmount = NumberUtil.getTwo (fcAmount.subtract (areaAmount));
                liftCoefficientStr = chargeInfo.getLiftCoefficient () + "%" + "(" + NumberUtil.getTwo (fcAmount) + ")";
            }
            queryOrderResultVo.setLiftCoefficient (liftCoefficientStr);
            if(areaAmount != null)
                queryOrderResultVo.setAreaCommissionYuan (areaAmount.toString ());
            if(storeAmount != null)
                queryOrderResultVo.setStoreCommissionYuan (storeAmount.toString ());

            queryOrderResultVos.add (queryOrderResultVo);
        }
        pagination.setRows (queryOrderResultVos);
        result.setData (pagination);
        return result;
    }


}
