package com.tsh.vas.phone.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tsh.vas.phone.enums.EnumPhoneOrderInfoPayStatus;
import com.tsh.vas.phone.enums.EnumRequestOrderType;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.dao.phone.PhoneReportDao;
import com.tsh.vas.model.phone.PhoneReportPo;
import com.tsh.vas.phone.commons.PhoneRefundAmountEnum;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.vo.phone.PhoneReportVo;

@Service
@SuppressWarnings("all")
public class PhoneReportService {
    private static final Logger log = Logger.getLogger(PhoneReportService.class);

    @Autowired
    private PhoneReportDao phoneReportDao;


    public Result getPhoneOrderInfoReportsPage(Result result,PhoneReportVo phoneReportVo) throws Exception{
        phoneReportVo.setPage((phoneReportVo.getPage() - 1 ) * phoneReportVo.getRows());
        phoneReportVo.setRows(phoneReportVo.getRows());

        List<PhoneReportVo> phoneReportVos = new ArrayList<PhoneReportVo>();

        Pagination pagination = phoneReportDao.getPhoneOrderInfoReportsPage(result, phoneReportVo).getData();

        List<PhoneReportPo> phoneReportPos = (List<PhoneReportPo>) pagination.getRows();
        for(PhoneReportPo phoneReportPo : phoneReportPos){
            PhoneReportVo phoneReportVo3 = new PhoneReportVo();
            BeanUtils.copyProperties(phoneReportPo, phoneReportVo3);

            //订单状态+退款状态
            if(null != phoneReportVo3.getPayStatus()){
                String statusStr = EnumPhoneOrderInfoPayStatus.getEnume(Integer.valueOf(phoneReportVo3.getPayStatus())).getClientDesc();
                phoneReportVo3.setPayStatus(statusStr);
            }else {
                phoneReportVo3.setStatusStr("—");
            }
            //退票状态
            if(StringUtils.isNotBlank(phoneReportVo3.getStatus())){
                String refundStatusStr = PhoneRefundAmountEnum.getEnume(Integer.valueOf(phoneReportVo3.getStatus())).getClientDesc();
                phoneReportVo3.setStatus(refundStatusStr);
            }else {
                phoneReportVo3.setStatus("—");
            }
            /*String payStatusStr = EnumOrderInfoPayStatus.getEnume(Integer.valueOf(phoneReportVo3.getPayStatus())).getDesc();
			phoneReportVo3.setPayStatus(payStatusStr);*/
            phoneReportVo3 = countFc(phoneReportVo3);
            if(null != phoneReportVo3.getCreateTime()){
                phoneReportVo3.setCreateTime(phoneReportVo3.getCreateTime().substring(0, 19));
            }
            if(null != phoneReportVo3.getRechargeSuccssTime()){
                phoneReportVo3.setRechargeSuccssTime(phoneReportVo3.getRechargeSuccssTime().substring(0, 19));
            }
            phoneReportVo3.setPlatformDividedRatio(phoneReportVo3.getPlatformDividedRatio()+"%");
            phoneReportVo3.setAreaDividedRatio(phoneReportVo3.getAreaDividedRatio()+"%");
            phoneReportVo3.setAreaCommissionRatio(phoneReportVo3.getAreaCommissionRatio()+"%");
            phoneReportVo3.setStoreCommissionRatio(phoneReportVo3.getStoreCommissionRatio()+"%");
            phoneReportVos.add(phoneReportVo3);
        }
        pagination.setRows(phoneReportVos);
        result.setData(pagination);
        return result;
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
        //平台，县域，网点，分成比例0
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

    public Result exportOrderReportsByPlatform(Result result,PhoneReportVo phoneReportVo) throws Exception {

        List<PhoneReportVo> phoneReportVos = new ArrayList<PhoneReportVo>();

        Pagination pagination = phoneReportDao.exportOrderReportsByPlatform(result, phoneReportVo).getData();

        List<PhoneReportPo> phoneReportPos = (List<PhoneReportPo>) pagination.getRows();
        for(PhoneReportPo phoneReportPo : phoneReportPos){
            PhoneReportVo phoneReportVo3 = new PhoneReportVo();
            BeanUtils.copyProperties(phoneReportPo, phoneReportVo3);

            //订单状态+退款状态
            if(null != phoneReportVo3.getPayStatus()){
                String statusStr = EnumPhoneOrderInfoPayStatus.getEnume(Integer.valueOf(phoneReportVo3.getPayStatus())).getClientDesc();
                phoneReportVo3.setPayStatus(statusStr);
            }else {
                phoneReportVo3.setStatusStr("—");
            }
            //退票状态
            if(StringUtils.isNotBlank(phoneReportVo3.getStatus())){
                String refundStatusStr = PhoneRefundAmountEnum.getEnume(Integer.valueOf(phoneReportVo3.getStatus())).getClientDesc();
                phoneReportVo3.setStatus(refundStatusStr);
            }else {
                phoneReportVo3.setStatus("—");
            }
            /*String payStatusStr = EnumOrderInfoPayStatus.getEnume(Integer.valueOf(phoneReportVo3.getPayStatus())).getDesc();
				phoneReportVo3.setPayStatus(payStatusStr);*/
            phoneReportVo3 = countFc(phoneReportVo3);
            if(null != phoneReportVo3.getCreateTime()){
                phoneReportVo3.setCreateTime(phoneReportVo3.getCreateTime().substring(0, 19));
            }
            if(null != phoneReportVo3.getRechargeSuccssTime()){
                phoneReportVo3.setRechargeSuccssTime(phoneReportVo3.getRechargeSuccssTime().substring(0, 19));
            }

            if(StringUtils.isNoneBlank(phoneReportVo3.getSources())){
                EnumRequestOrderType orderSources = EnumRequestOrderType.getEnume(phoneReportVo3.getSources());
                if(orderSources != null){
                    phoneReportVo3.setSources(orderSources.getTypeName());
                }
            }

            phoneReportVo3.setTdAmount(phoneReportVo3.getProfit());
            phoneReportVo3.setPlatformDividedRatio(phoneReportVo3.getPlatformDividedRatio()+"%");
            phoneReportVo3.setAreaDividedRatio(phoneReportVo3.getAreaDividedRatio()+"%");
            phoneReportVo3.setAreaCommissionRatio(phoneReportVo3.getAreaCommissionRatio()+"%");
            phoneReportVo3.setStoreCommissionRatio(phoneReportVo3.getStoreCommissionRatio()+"%");

            double m = Double.valueOf(phoneReportVo3.getOriginalAmount())-Double.valueOf(phoneReportVo3.getRealAmount());
            if(m == 0){
                phoneReportVo3.setPlatformMinnus("—");
            } else {
                phoneReportVo3.setPlatformMinnus(String.valueOf(m));
            }

            phoneReportVos.add(phoneReportVo3);

        }
        result.setData(phoneReportVos);
        return result;
    }
}

