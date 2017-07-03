package com.tsh.vas.netgold.service;

import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.dao.ChargeInfoDao;
import com.tsh.vas.dao.phone.PhoneOrderInfoDao;
import com.tsh.vas.dao.trainticket.HcpOrderInfoDao;
import com.tsh.vas.enume.ChargePayStatus;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.netgold.common.enums.NetgoldOrderStatus;
import com.tsh.vas.netgold.vo.NetgoldOrderInfo;
import com.tsh.vas.phone.constants.MQPhoneTopicConstants;
import com.tsh.vas.phone.enums.EnumPhoneOrderInfoPayStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.constants.MQTopicConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/4/10 010.
 */
@Service
public class NetgoldService {

    @Autowired
    private ChargeInfoDao chargeInfoDao;

    @Autowired
    private HcpOrderInfoDao hcpOrderInfoDao;

    @Autowired
    private PhoneOrderInfoDao phoneOrderInfoDao;

    /**
     * 查询订单信息
     * @param result
     * @param orderCode
     * @return
     */
    public Result queryOrder(Result result, String orderCode) throws Exception{
        NetgoldOrderInfo netgoldOrderInfo = null;
        if(StringUtils.isNotBlank(orderCode)){
            if(orderCode.startsWith("DJDF")){
                // 查询交电费订单信息
                chargeInfoDao.queryByChargeCode(result, orderCode);
                netgoldOrderInfo = this.setDJDFParams(result, netgoldOrderInfo);

            } else if(orderCode.startsWith("HCP")){
                // 查询火车票订单信息
                hcpOrderInfoDao.queryByOrderCode(result, orderCode);
                netgoldOrderInfo = this.setHCPParams(result, netgoldOrderInfo);

            } else if(orderCode.startsWith("MPCZ")){
                // 查询话费订单信息
                phoneOrderInfoDao.queryByOrderCode(result, orderCode);
                netgoldOrderInfo = this.setMPCZParams(result, netgoldOrderInfo);
            }
        }
        result.setData(netgoldOrderInfo);
        return result;
    }

    /**
     * 设置电费订单信息
     * @param result
     */
    private NetgoldOrderInfo setDJDFParams(Result result, NetgoldOrderInfo netgoldOrderInfo){
        if(null != result.getData()){
            netgoldOrderInfo = new NetgoldOrderInfo();
            ChargeInfo chargeInfo =  result.getData();
            netgoldOrderInfo.setOrderId(chargeInfo.getId());
            netgoldOrderInfo.setBizOrderNo(chargeInfo.getChargeCode());
            netgoldOrderInfo.setTotalMoney(chargeInfo.getRealAmount());
            netgoldOrderInfo.setOperateUserId(chargeInfo.getPayUserId());
            netgoldOrderInfo.setBizDetails("增值服务订单支付");
            netgoldOrderInfo.setBizIntro("增值服务");
            netgoldOrderInfo.setMsgTopic("vasPayChargeTopic");
            netgoldOrderInfo.setBizId(chargeInfo.getBizId());
            netgoldOrderInfo.setBizType(chargeInfo.getBizType());
            netgoldOrderInfo.setCountryCode(chargeInfo.getCountryCode());
            netgoldOrderInfo.setCountryName(chargeInfo.getCountryName());
            netgoldOrderInfo.setCreateTime(DateUtil.date2String(chargeInfo.getCreateTime()));
            if(chargeInfo.getPayStatus().intValue() == ChargePayStatus.NON_PAYMENT.getCode().intValue()){
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.WAITING.getCode());
            } else if(chargeInfo.getPayStatus().intValue() == ChargePayStatus.TRAD_SUCCESS.getCode().intValue()){
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.TRAD_SUCCESS.getCode());
            } else if(chargeInfo.getPayStatus().intValue() == ChargePayStatus.PAY_SUCCESS.getCode().intValue()){
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.PAY_SUCCESS.getCode());
            } else {
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.OTHER.getCode());
            }
        }
        return netgoldOrderInfo;
    }

    /**
     * 设置火车票订单信息
     * @param result
     */
    private NetgoldOrderInfo setHCPParams(Result result, NetgoldOrderInfo netgoldOrderInfo){
        if(null != result.getData()){
            netgoldOrderInfo = new NetgoldOrderInfo();
            HcpOrderInfoPo hcpOrderInfoPo =  result.getData();
            netgoldOrderInfo.setOrderId(hcpOrderInfoPo.getId().longValue());
            netgoldOrderInfo.setBizOrderNo(hcpOrderInfoPo.getHcpOrderCode());
            netgoldOrderInfo.setTotalMoney(hcpOrderInfoPo.getRealAmount());
            netgoldOrderInfo.setOperateUserId(hcpOrderInfoPo.getPayUserId().longValue());
            netgoldOrderInfo.setBizDetails("火车票增值服务订单支付");
            netgoldOrderInfo.setBizIntro("火车票增值服务");
            netgoldOrderInfo.setMsgTopic(MQTopicConstants.PAY_NOTIFY);
            netgoldOrderInfo.setBizId(hcpOrderInfoPo.getBizId().longValue());
            netgoldOrderInfo.setBizType(hcpOrderInfoPo.getBizType());
            netgoldOrderInfo.setCountryCode(hcpOrderInfoPo.getCountryCode());
            netgoldOrderInfo.setCountryName(hcpOrderInfoPo.getCountryName());
            netgoldOrderInfo.setCreateTime(DateUtil.date2String(hcpOrderInfoPo.getCreateTime()));
            if(hcpOrderInfoPo.getPayStatus().intValue() == EnumOrderInfoPayStatus.NON_PAYMENT.getCode().intValue()){
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.WAITING.getCode());
            } else if(hcpOrderInfoPo.getPayStatus().intValue() == EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode().intValue()){
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.TRAD_SUCCESS.getCode());
            } else if(hcpOrderInfoPo.getPayStatus().intValue() == EnumOrderInfoPayStatus.PAY_SUCCESS.getCode().intValue()){
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.PAY_SUCCESS.getCode());
            } else {
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.OTHER.getCode());
            }
        }
        return netgoldOrderInfo;
    }

    /**
     * 设置话费订单信息
     * @param result
     */
    private NetgoldOrderInfo setMPCZParams(Result result, NetgoldOrderInfo netgoldOrderInfo){
        if(null != result.getData()){
            netgoldOrderInfo = new NetgoldOrderInfo();
            PhoneOrderInfoPo phoneOrderInfoPo =  result.getData();
            netgoldOrderInfo.setOrderId(phoneOrderInfoPo.getId().longValue());
            netgoldOrderInfo.setBizOrderNo(phoneOrderInfoPo.getPhoneOrderCode());
            netgoldOrderInfo.setTotalMoney(phoneOrderInfoPo.getRealAmount());
            netgoldOrderInfo.setOperateUserId(phoneOrderInfoPo.getPayUserId().longValue());
            netgoldOrderInfo.setBizDetails("话费-增值服务订单支付");
            netgoldOrderInfo.setBizIntro("话费-增值服务");
            netgoldOrderInfo.setMsgTopic(MQPhoneTopicConstants.PAY_NOTIFY);
            netgoldOrderInfo.setBizId(phoneOrderInfoPo.getBizId().longValue());
            netgoldOrderInfo.setBizType(phoneOrderInfoPo.getBizType());
            netgoldOrderInfo.setCountryCode(phoneOrderInfoPo.getCountryCode());
            netgoldOrderInfo.setCountryName(phoneOrderInfoPo.getCountryName());
            netgoldOrderInfo.setCreateTime(DateUtil.date2String(phoneOrderInfoPo.getCreateTime()));
            if(phoneOrderInfoPo.getPayStatus().intValue() == EnumPhoneOrderInfoPayStatus.NON_PAYMENT.getCode().intValue()){
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.WAITING.getCode());
            } else if(phoneOrderInfoPo.getPayStatus().intValue() == EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getCode().intValue()){
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.TRAD_SUCCESS.getCode());
            } else if(phoneOrderInfoPo.getPayStatus().intValue() == EnumPhoneOrderInfoPayStatus.PAY_SUCCESS.getCode().intValue()){
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.PAY_SUCCESS.getCode());
            } else {
                netgoldOrderInfo.setPayStatus(NetgoldOrderStatus.OTHER.getCode());
            }
        }
        return netgoldOrderInfo;
    }

}
