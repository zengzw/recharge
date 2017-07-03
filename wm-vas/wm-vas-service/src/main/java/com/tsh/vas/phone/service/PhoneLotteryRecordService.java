package com.tsh.vas.phone.service;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.ReturnDTO;
import com.github.ltsopensource.core.json.JSON;
import com.google.common.collect.Maps;
import com.tsh.dubbo.hlq.api.CouponApi;
import com.tsh.dubbo.hlq.enumType.CouponReceiveTypeEnum;
import com.tsh.dubbo.hlq.enumType.SysTypeEnum;
import com.tsh.dubbo.hlq.enumType.UseSceneTypeEnum;
import com.tsh.dubbo.hlq.vo.coupon.GiveCouponDTO;
import com.tsh.dubbo.hlq.vo.coupon.QueryCouponParamVO;
import com.tsh.dubbo.hlq.vo.ex.CouponVO;
import com.tsh.vas.commoms.constants.FundURLConstants;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.dao.phone.PhoneOrderInfoDao;
import com.tsh.vas.dao.phone.VasPhoneLotteryRecordDao;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.VasPhoneLotteryRecordPo;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreeStatisPo;
import com.tsh.vas.phone.commons.PhoneLotteryStatus;
import com.tsh.vas.phone.constants.SMSPhoneMessageTemplateConstants;
import com.tsh.vas.phone.enums.EnumLotteryType;
import com.tsh.vas.service.SmsService;
import com.tsh.vas.trainticket.commoms.HttpUtils;
import com.tsh.vas.trainticket.vo.MemberResultVo;
import com.tsh.vas.vo.charge.MemberVo;
import com.tsh.vas.vo.phone.CashCouponVo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.phone.VasPhoneOneyuanFreeDao;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreePo;
import com.tsh.vas.vo.phone.QueryActivityStatisticsVo;
import com.tsh.vas.vo.phone.VasPhoneOneyuanFreeVo;


@Service
@SuppressWarnings("all")
public class PhoneLotteryRecordService {

    @Autowired
    private VasPhoneLotteryRecordDao lotteryRecordDao;
    
    
    /**
     *  根据订单号 优化卷信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryLotterRecordByOrderNo(Result result, String orderCode){
        return lotteryRecordDao.queryLotterRecordByOrderNo(result, orderCode);
    }
    
    

}
