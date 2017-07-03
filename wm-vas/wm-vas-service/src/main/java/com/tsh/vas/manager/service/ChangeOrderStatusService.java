package com.tsh.vas.manager.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Pagination;
import com.tsh.openpf.vo.ServiceRegisterVo;
import com.tsh.phone.vo.OrderInfoVo;
import com.tsh.vas.dao.phone.VasPhoneOneyuanFreeDao;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreePo;
import com.tsh.vas.phone.commons.PhoneLotteryStatus;
import com.tsh.vas.phone.constants.PhoneConstants;
import com.tsh.vas.phone.facade.QueueRegisterSupplier;
import com.tsh.vas.phone.service.APIPhoneBrokerService;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.commoms.enums.EnumBusinessCode;
import com.tsh.vas.commoms.enums.ModifyOrderStatus;
import com.tsh.vas.dao.ChargeInfoDao;
import com.tsh.vas.dao.ChargeRefundDao;
import com.tsh.vas.dao.VasOrderTransformHistoryDao;
import com.tsh.vas.dao.phone.PhoneOrderInfoDao;
import com.tsh.vas.dao.phone.PhoneRefundAmountDao;
import com.tsh.vas.enume.ChargePayStatus;
import com.tsh.vas.enume.ChargeRefundStatus;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.model.ChargeRefund;
import com.tsh.vas.model.VasOrderTransformHistory;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneRefundAmountPo;
import com.tsh.vas.phone.enums.EnumPhoneOrderInfoPayStatus;
import com.tsh.vas.phone.enums.EnumPhoneRefundOrderStatus;
import com.tsh.vas.phone.enums.EnumPhoneRefundType;
import com.tsh.vas.trainticket.commoms.enums.EnumRefundOrderStatus;
import com.tsh.vas.vo.ChangeOrderInfo;
import com.tsh.vas.vo.QueryOrderChangeVO;

/**
 * Created by Administrator on 2017/4/14 014.
 */
@Service
public class ChangeOrderStatusService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChangeOrderStatusService.class);

    @Autowired
    private ChargeInfoDao chargeInfoDao;

    @Autowired
    private ChargeRefundDao chargeRefundDao;

    @Autowired
    private VasOrderTransformHistoryDao historyDao;

    @Autowired  
    private PhoneOrderInfoDao phoneOrderInfoDao;

    @Autowired
    private PhoneRefundAmountDao phoneRefundAmountDao;

    @Autowired
    private APIPhoneBrokerService brokerService;

    @Autowired
    QueueRegisterSupplier queueRegisterSupplier;

    @Autowired
    private VasPhoneOneyuanFreeDao vasPhoneOneyuanFreeDao;

    /**
     *
     * @param result
     * @param type
     * @param orderCode
     */
    public Result queryOrderInfo(Result result, String type, String orderCode) throws Exception{
        ChangeOrderInfo changeOrderInfo = null;

        // 缴电费订单
        if(EnumBusinessCode.DJDF.toString().equals(type)){
            ChargeInfo chargerInfo = chargeInfoDao.queryByChargeCode(result, orderCode).getData();
            if(null != chargerInfo){
                changeOrderInfo = new ChangeOrderInfo();
                changeOrderInfo.setOrderType(EnumBusinessCode.DJDF.toString());
                changeOrderInfo.setOrderTypeName(EnumBusinessCode.DJDF.getBusinessName());
                changeOrderInfo.setOrderNo(chargerInfo.getChargeCode());
                changeOrderInfo.setPayBalance(chargerInfo.getRealAmount().toString());
                changeOrderInfo.setCreateTimeStr(DateUtil.date2String(chargerInfo.getCreateTime()));
                this.dealDJDFOrderStatus(changeOrderInfo, chargerInfo.getPayStatus());
                changeOrderInfo.setRefundStatus(String.valueOf(chargerInfo.getRefundStatus()));
                changeOrderInfo.setRefundStatusName(ChargeRefundStatus.getEnume(chargerInfo.getRefundStatus()).getClientDesc());

                if(ChargeRefundStatus.NORMAL_REFUND.getCode().intValue() != chargerInfo.getRefundStatus().intValue()){
                    changeOrderInfo.setRefundExists("1");
                }
            }
        }


        // 话费订单
        if(EnumBusinessCode.MPCZ.toString().equals(type)){

            PhoneOrderInfoPo chargerInfo = phoneOrderInfoDao.queryByOrderCode(result, orderCode).getData();
            if(null != chargerInfo){
                changeOrderInfo = new ChangeOrderInfo();
                changeOrderInfo.setOrderType(EnumBusinessCode.MPCZ.toString());
                changeOrderInfo.setOrderTypeName(EnumBusinessCode.MPCZ.getBusinessName());
                changeOrderInfo.setOrderNo(chargerInfo.getPhoneOrderCode());
                changeOrderInfo.setPayBalance(chargerInfo.getRealAmount().toString());
                this.dealMPCZOrderStatus(changeOrderInfo, chargerInfo.getPayStatus());
                changeOrderInfo.setCreateTimeStr(DateUtil.date2String(chargerInfo.getCreateTime()));

                PhoneRefundAmountPo refundInfo = phoneRefundAmountDao.queryByOrderCode(result, orderCode).getData();
                if(null != refundInfo){
                    changeOrderInfo.setRefundExists("1");
                    changeOrderInfo.setRefundStatus(String.valueOf(refundInfo.getStatus()));
                    changeOrderInfo.setRefundStatusName(EnumPhoneRefundOrderStatus.getEnume(refundInfo.getStatus()).getClientDesc());
                } else {
                    changeOrderInfo.setRefundStatusName("-");
                }

                // 如果是“充值中” 要查供应商那边的订单状态
                if(EnumPhoneOrderInfoPayStatus.TRADING.getCode().intValue() == chargerInfo.getPayStatus().intValue()) {

                    changeOrderInfo = querySupplierOrderStatus(result, chargerInfo, changeOrderInfo);

                }

            }

        }

        List<VasOrderTransformHistory> list  = historyDao.queryByOrderNo(result, type, orderCode).getData();
        if(null != list && !list.isEmpty()){
            changeOrderInfo.setChangeExists("1");
        }

        LOGGER.info("------> queryOrderStatus:" + JSON.toJSONString(changeOrderInfo));
        
        result.setData(changeOrderInfo);
        return result;
    }

    /**
     * 查询供应商订单状态
     * @param result
     * @param chargerInfo
     * @param changeOrderInfo
     * @return
     * @throws Exception
     */
    private ChangeOrderInfo querySupplierOrderStatus(Result result, PhoneOrderInfoPo chargerInfo, ChangeOrderInfo changeOrderInfo) {
        // 订单创建时间大于24小时才可以修改
        long diff = System.currentTimeMillis() - chargerInfo.getCreateTime().getTime();
        if(diff > 1000*60*60*24){

            // 获取供应商编号及请求地址信息
            ServiceRegisterVo serviceRegisterVo = null;
            try{
                serviceRegisterVo =  queueRegisterSupplier.getServiceRegisterVo(result, chargerInfo.getSupplierCode());
            } catch (Exception e){
                changeOrderInfo.setSupplierOrderStatus(ModifyOrderStatus.REQUEST_ERROR.toString());
                changeOrderInfo.setSupplierOrderStatusName(ModifyOrderStatus.REQUEST_ERROR.getDesc());
                return changeOrderInfo;
            }

            LOGGER.info("------> queryOrderStatus from supplier:" + JSON.toJSONString(serviceRegisterVo));

            brokerService.queryOrderInfo(result, chargerInfo.getOpenPlatformNo() ,serviceRegisterVo);

            LOGGER.info("------> queryOrderStatus from order:" + JSON.toJSONString(result.getData()));

            if(null != result && result.getStatus() == HttpResponseConstants.SUCCESS){
                OrderInfoVo orderInfoVo = JSON.parseObject(result.getData().toString(),OrderInfoVo.class);
                if(null != orderInfoVo){
                    if(PhoneConstants.OrderStatus.SUCCESS.equals(orderInfoVo.getOrderStatus())){
                        // 供应商返回订单成功
                        changeOrderInfo.setSupplierOrderStatus(ModifyOrderStatus.SUCCESS.toString());
                        changeOrderInfo.setSupplierOrderStatusName(ModifyOrderStatus.SUCCESS.getDesc());

                    } else if(PhoneConstants.OrderStatus.FAILED.equals(orderInfoVo.getOrderStatus())){
                        // 供应商返回订单失败
                        changeOrderInfo.setSupplierOrderStatus(ModifyOrderStatus.FAILURE.toString());
                        changeOrderInfo.setSupplierOrderStatusName(ModifyOrderStatus.FAILURE.getDesc());

                    } else {
                        // 供应商返回订单未知
                        changeOrderInfo.setSupplierOrderStatus(ModifyOrderStatus.UNKNLOW_STATUS.toString());
                        changeOrderInfo.setSupplierOrderStatusName(ModifyOrderStatus.UNKNLOW_STATUS.getDesc());

                    }
                }
            } else {
                changeOrderInfo.setSupplierOrderStatus(ModifyOrderStatus.REQUEST_ERROR.toString());
                changeOrderInfo.setSupplierOrderStatusName(ModifyOrderStatus.REQUEST_ERROR.getDesc());
            }

        } else {

            changeOrderInfo.setOrderStatus(ModifyOrderStatus.NOT_TIME.toString());
            changeOrderInfo.setOrderStatusName(EnumPhoneOrderInfoPayStatus.TRADING.getClientDesc());
        }

        return changeOrderInfo;
    }
    /**
     * 处理水电煤订单状态
     * @param orderInfo
     * @param payStatus
     */
    private void dealDJDFOrderStatus(ChangeOrderInfo orderInfo, int payStatus) {
        if(ChargePayStatus.TRAD_SUCCESS.getCode().intValue() == payStatus){
            orderInfo.setOrderStatus(ModifyOrderStatus.SUCCESS.toString());
            orderInfo.setOrderStatusName(ChargePayStatus.TRAD_SUCCESS.getClientDesc());
        } else if (ChargePayStatus.CHARGE_FAIL.getCode().intValue() == payStatus){
            orderInfo.setOrderStatus(ModifyOrderStatus.CHARGE_FAIL.toString());
            orderInfo.setOrderStatusName(ChargePayStatus.CHARGE_FAIL.getDesc());
        } else {
            orderInfo.setOrderStatus(String.valueOf(payStatus));
            orderInfo.setOrderStatusName(ChargePayStatus.getEnume(payStatus).getDesc());
        }
    }

    /**
     * 处理话费订单状态
     * @param orderInfo
     * @param payStatus
     */
    private void dealMPCZOrderStatus(ChangeOrderInfo orderInfo, int payStatus){
        if(EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getCode().intValue() == payStatus){
            orderInfo.setOrderStatus(ModifyOrderStatus.SUCCESS.toString());
            orderInfo.setOrderStatusName(EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getClientDesc());
        } else if (EnumPhoneOrderInfoPayStatus.PAY_FAIL.getCode().intValue() == payStatus){
            orderInfo.setOrderStatus(ModifyOrderStatus.CHARGE_FAIL.toString());
            orderInfo.setOrderStatusName(EnumPhoneOrderInfoPayStatus.PAY_FAIL.getClientDesc());
        } else if(EnumPhoneOrderInfoPayStatus.TRADING.getCode().intValue() == payStatus){
            orderInfo.setOrderStatus(ModifyOrderStatus.PHONE_CHARGING.toString());
            orderInfo.setOrderStatusName(EnumPhoneOrderInfoPayStatus.TRADING.getClientDesc());
        } else {
            orderInfo.setOrderStatus(String.valueOf(payStatus));
            orderInfo.setOrderStatusName(EnumPhoneOrderInfoPayStatus.getEnume(payStatus).getDesc());
        }
    }

    /**
     * 查找修改历史记录
     * @param result
     * @param orderNo
     */
    public Result queryChangeHistory(Result result,String orderType, String orderNo) {
        List<VasOrderTransformHistory> list = historyDao.queryByOrderNo(result, orderType, orderNo).getData();
        if(null != list && !list.isEmpty()){
            for(VasOrderTransformHistory history : list){
                history.setUpdateTime(history.getUpdateTime().substring(0, 19));
            }
        }
        result.setData(list);
        return result;
    }



    /**
     * 根据条件分页查询信息服务列表  （管理后台）
     * @param result
     * @param page
     * @param queryOrderChangeVO
     * @return
     */
    public Result queryChangeOrderList(Result result,Page page,QueryOrderChangeVO queryOrderChangeVO){

        Pagination pagination = historyDao.queryOrderTransformHistoryList(result, page, queryOrderChangeVO).getData();
        if(null != pagination && null != pagination.getRows() && !pagination.getRows().isEmpty()){
            List<VasOrderTransformHistory> list = (List<VasOrderTransformHistory>) pagination.getRows();
            for(VasOrderTransformHistory history : list){
                history.setUpdateTime(history.getUpdateTime().substring(0, 19));
            }
            pagination.setRows(list);
        }
        result.setData(pagination);
        return result;
    }




    /**
     * 修改订单状态
     * @param result
     * @param orderNo
     * @param remarks
     * @param modifyOrderStatus
     */
    public void updateOrderStatus(Result result, String orderNo, String remarks, String modifyOrderStatus) throws Exception{
        UserInfo userInfo = result.getUserInfo();
        if(StringUtils.isNotBlank(orderNo) &&
                StringUtils.isNotBlank(remarks) &&
                StringUtils.isNotBlank(modifyOrderStatus)){

            // 缴电费订单
            if(orderNo.startsWith(EnumBusinessCode.DJDF.toString())){
                this.doChangeDJDFOrderStatus(result, orderNo, remarks, modifyOrderStatus, userInfo);

            } // 话费订单
            else if(orderNo.startsWith(EnumBusinessCode.MPCZ.toString())){
                this.doChangeMPCZOrderStatus(result, orderNo, remarks, modifyOrderStatus, userInfo);
            } else {
                throw new Exception("订单编号错误");
            }

        } else {
            throw new Exception("参数错误");
        }
    }

    /**
     * 修改话费订单状态
     * @param result
     * @param orderNo
     * @param remarks
     * @param modifyOrderStatus
     * @param userInfo
     * @throws Exception
     */
    private void doChangeMPCZOrderStatus(Result result, String orderNo, String remarks, String modifyOrderStatus, UserInfo userInfo) throws Exception {
        PhoneOrderInfoPo chargeInfo = phoneOrderInfoDao.queryByOrderCode(result, orderNo).getData();
        if(null != chargeInfo){
            boolean validate = false;//校验标识
            boolean isAddRefund = false;//是否添加退款记录标识
            boolean isAutomaticRefund = false;//是否自动退款
            boolean isCheckActivity = false; // 是否检测活动表记录
            String beforeStatus = "";
            String afterStatus = "";
            int updateStatus = 0;
            
            // * --> 成功
            if(ModifyOrderStatus.SUCCESS.toString().equals(modifyOrderStatus)){
                // 充值中 --> 充值成功
                if(EnumPhoneOrderInfoPayStatus.TRADING.getCode().intValue() == chargeInfo.getPayStatus().intValue()){
                    validate = true;
                    isCheckActivity = true;
                    updateStatus = EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getCode().intValue();
                    beforeStatus = EnumPhoneOrderInfoPayStatus.TRADING.getClientDesc();
                    afterStatus = EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getClientDesc();
                }
            } 

            
            // * --> 失败
            if(ModifyOrderStatus.FAILURE.toString().equals(modifyOrderStatus)){
                //充值成功 --> 充值失败
                if(EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getCode().intValue() == chargeInfo.getPayStatus().intValue()){
                    validate = true;
                    isAddRefund = true;
                    updateStatus = EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getCode();
                    beforeStatus = EnumPhoneOrderInfoPayStatus.TRAD_SUCCESS.getClientDesc();
                    afterStatus = EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getClientDesc();
                }
                // 扣款失败 --> 充值失败
                if(EnumPhoneOrderInfoPayStatus.PAY_FAIL.getCode().intValue() == chargeInfo.getPayStatus().intValue()){
                    validate = true;
                    isAddRefund = true;
                    updateStatus = EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getCode().intValue();
                    beforeStatus = EnumPhoneOrderInfoPayStatus.PAY_FAIL.getClientDesc();
                    afterStatus = EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getClientDesc();
                }
                // 充值中 --> 充值失败
                if(EnumPhoneOrderInfoPayStatus.TRADING.getCode().intValue() == chargeInfo.getPayStatus().intValue()){
                    validate = true;
                    isAddRefund = true;
                    isAutomaticRefund = true;// 自动退款
                    updateStatus = EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getCode().intValue();
                    beforeStatus = EnumPhoneOrderInfoPayStatus.TRADING.getClientDesc();
                    afterStatus = EnumPhoneOrderInfoPayStatus.TRAIN_FAIL.getClientDesc();
                }
            }

            // 验证通过，对订单状态进行修改，并添加修改历史记录
            if(validate){

                this.check(result, EnumBusinessCode.MPCZ.toString(), orderNo);

                // 判断是否需要添加退款记录
                if(isAddRefund){
                    PhoneRefundAmountPo refundAmount = phoneRefundAmountDao.queryByOrderCode(result, orderNo).getData();
                    if(null != refundAmount){
                        throw new Exception("已存在退款记录");
                    }

                    this.doDealMPCZRefund(chargeInfo, isAutomaticRefund);
                }

                this.addChangeHistory(orderNo, EnumBusinessCode.MPCZ.toString(), beforeStatus,afterStatus, remarks,chargeInfo.getRealAmount(), userInfo);

                phoneOrderInfoDao.updateStatus(result, orderNo, updateStatus);
            }


            // 是否校验交易活动表记录
            if(isCheckActivity){
                this.doCheckActivity(result, chargeInfo);
            }
        }
    }


    /**
     *
     * @param result
     * @param chargeInfo
     */
    private void doCheckActivity(Result result, PhoneOrderInfoPo chargeInfo){

        VasPhoneOneyuanFreePo activityPo = vasPhoneOneyuanFreeDao.queryByOrderNo(chargeInfo.getPhoneOrderCode());
        // 判断是否为“待审核”状态，是则修改为“通过”
        if(null != activityPo && PhoneLotteryStatus.CHECK_WAIT == activityPo.getCheckStatus().intValue()){
            vasPhoneOneyuanFreeDao.updateAuditStatusById(result, Long.valueOf(activityPo.getId().intValue()), new Date(), PhoneLotteryStatus.CHECKED);
        }
    }

    /**
     * 处理话费退款
     * @param chargeInfo
     * @param isAutomaticRefund
     */
    private void doDealMPCZRefund(PhoneOrderInfoPo chargeInfo, boolean isAutomaticRefund){
        PhoneRefundAmountPo newRefundAmount = new PhoneRefundAmountPo();
        newRefundAmount.setPhoneOrderCode(chargeInfo.getPhoneOrderCode());
        newRefundAmount.setRealAmount(chargeInfo.getRealAmount());
        newRefundAmount.setRefundAmountCode("TD-"+chargeInfo.getPhoneOrderCode());
        newRefundAmount.setRefundType(EnumPhoneRefundType.RECHARGE_ERROR.getType());//购票退款
        newRefundAmount.setCreateTime(new Date());
        if(isAutomaticRefund){
            newRefundAmount.setStatus(EnumRefundOrderStatus.NON_REFUND.getCode());
            newRefundAmount.setRemark("缴费异常，供应商缴费失败，【自动】处理，操作时间:" + DateUtil.date2String(new Date()));
        } else {
            newRefundAmount.setStatus(EnumRefundOrderStatus.REFUND_SUCCESS.getCode());
            newRefundAmount.setRemark("缴费异常，供应商缴费失败，【手工】处理，操作时间:" + DateUtil.date2String(new Date()));
        }
        newRefundAmount.setRefundTimes(0);

        phoneRefundAmountDao.save(newRefundAmount);
    }

    /**
     * 处理缴电费状态变更
     * @param result
     * @param orderNo
     * @param remarks
     * @param modifyOrderStatus
     * @param userInfo
     * @throws Exception
     */
    private void doChangeDJDFOrderStatus(Result result, String orderNo, String remarks, String modifyOrderStatus, UserInfo userInfo) throws Exception {
        ChargeInfo chargeInfo = chargeInfoDao.queryByChargeCode(result, orderNo).getData();
        if(null != chargeInfo){
            boolean validate = false;//校验标识
            int updateStatus = 0;
            boolean isAddRefund = false;//是否添加退款记录标识
            String beStatus = "";
            String afStatus = "";

            // *  --> 成功
            if(ModifyOrderStatus.SUCCESS.toString().equals(modifyOrderStatus)){
                // 缴费异常 --> 交易成功
                if(ChargePayStatus.CHARGE_FAIL.getCode().intValue() == chargeInfo.getPayStatus().intValue()){
                    validate = true;
                    updateStatus = ChargePayStatus.TRAD_SUCCESS.getCode().intValue();
                    beStatus = ChargePayStatus.CHARGE_FAIL.getDesc();
                    afStatus = ChargePayStatus.TRAD_SUCCESS.getClientDesc();
                }
            } 

            // * --> 失败
            if(ModifyOrderStatus.FAILURE.toString().equals(modifyOrderStatus)){
                // 从交易成功--> 交易失败
                if(ChargePayStatus.TRAD_SUCCESS.getCode().intValue() == chargeInfo.getPayStatus().intValue()){
                    validate = true;
                    updateStatus = ChargePayStatus.TRAD_FAIL.getCode().intValue();
                    isAddRefund = true;

                    beStatus = ChargePayStatus.TRAD_SUCCESS.getClientDesc();
                    afStatus = ChargePayStatus.TRAD_FAIL.getDesc();
                }

                // 缴费异常 --> 交易失败
                if(ChargePayStatus.CHARGE_FAIL.getCode().intValue() == chargeInfo.getPayStatus().intValue()){
                    validate = true;
                    updateStatus = ChargePayStatus.TRAD_FAIL.getCode().intValue();
                    isAddRefund = true;
                    beStatus = ChargePayStatus.CHARGE_FAIL.getDesc();
                    afStatus = ChargePayStatus.TRAD_FAIL.getClientDesc();
                }

            }


            // 验证通过，并添加修改历史记录，对订单状态进行修改
            if(validate){

                this.check(result, EnumBusinessCode.DJDF.toString(), orderNo);

                // 判断是否需要添加退款记录
                if(isAddRefund){
                    ChargeRefund chargeRefund = chargeRefundDao.queryByOrderCode(result, orderNo).getData();
                    if(null != chargeRefund){
                        throw new Exception("已存在退款记录");
                    }

                    this.doDealDJDFRefund(result, orderNo, chargeInfo);
                }

                this.addChangeHistory(orderNo, EnumBusinessCode.DJDF.toString(), beStatus,afStatus, remarks,chargeInfo.getRealAmount(), userInfo);

                chargeInfoDao.updateStatus(result, orderNo, updateStatus);
            } else {
                throw new Exception("请重新确认订单状态");
            }



        }
    }

    /**
     * 缴电费退款处理
     * @param result
     * @param orderNo
     * @param chargeInfo
     */
    private void doDealDJDFRefund(Result result, String orderNo, ChargeInfo chargeInfo) throws Exception{
        // 添加退款记录
        ChargeRefund refund = new ChargeRefund();
        refund.setRefundCode("TD-" + chargeInfo.getChargeCode()) ;
        refund.setChargeCode(chargeInfo.getChargeCode()) ;
        refund.setRefundAmount(chargeInfo.getRealAmount()) ;
        refund.setRefundTime(new Date()) ;
        refund.setUserName("状态变更退款") ;
        refund.setUserMobile("");
        refund.setUserCode("") ;
        refund.setRemark("缴费异常，供应商缴费失败，手工处理，操作时间:" + DateUtil.date2String(new Date())) ;
        chargeRefundDao.save(refund);

        // 修改订单退款状态
        chargeInfoDao.updateRefundStatus(result, orderNo, ChargeRefundStatus.REFUND.getCode());
    }

    /**
     * 添加变更历史记录
     * @param orderNo
     * @param orderType
     * @param beforeStratus
     * @param afterStatus
     * @param remarks
     * @param payMoney
     * @param userInfo
     */
    private void addChangeHistory(String orderNo, String orderType, String beforeStratus,String afterStatus, String remarks,BigDecimal payMoney, UserInfo userInfo){
        VasOrderTransformHistory history = new VasOrderTransformHistory();
        history.setOrderType(orderType) ;
        history.setOrderCode(orderNo) ;
        history.setPayMoney(payMoney);
        if(null != userInfo && null != userInfo.getUserId()){
            history.setCreateId(Integer.valueOf(String.valueOf(userInfo.getUserId()))) ;
            history.setCreater(userInfo.getLoginName()) ;
        } else {
            history.setCreateId(0) ;
            history.setCreater("无法获取");
        }
        history.setUpdateTime(DateUtil.date2String(new Date())) ;
        history.setBeforeStatus(beforeStratus);
        history.setAfterStatus(afterStatus);
        history.setRemark(remarks);
        historyDao.save(history);
    }

    /**
     * 检测变更记录
     * @param result
     * @param orderType
     * @param orderNo
     * @throws Exception
     */
    private void check(Result result, String orderType, String orderNo) throws Exception{
        List<VasOrderTransformHistory> list = historyDao.queryByOrderNo(result, orderType, orderNo).getData();
        if(null != list && !list.isEmpty()){
            throw new Exception("已存在变更记录，不能再次变更");
        }
    }

}
