package com.tsh.vas.phone.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dtds.platform.util.bean.Result;
import com.tsh.vas.dao.phone.PhoneUseCouponRecordDao;
import com.tsh.vas.model.phone.PhoneUseCouponRecordPo;

/**
 * Created by Administrator on 2017/6/21 021.
 */
@Service
public class PhoneUseCouponRecordService {
    
    @Autowired
    PhoneUseCouponRecordDao phoneUseCouponRecordDao;
    
    
    /**
     * 新增
     * @param result
     * @param phoneRefundAmount
     * @return
     */
    public Result add(Result result,PhoneUseCouponRecordPo phoneUseCouponRecordPo){
        return phoneUseCouponRecordDao.add(result, phoneUseCouponRecordPo);
        
    }
    
    
    /**
     * 根据当前代金券使用状态
     * 
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public Result updateStatusAndSuccessTime(Result result, String orderCode, Integer couponStatus,Date updateTime){
        return phoneUseCouponRecordDao.updateStatusAndSuccessTime(result, orderCode, couponStatus, updateTime);
    }
    
    
    /**
     * 根据订单号获取优惠券信息
     * 
     * 
     * @param result
     * @param orderCode
     * @return
     */
    public Result queryByOrderCode(Result result, String orderCode){
        return phoneUseCouponRecordDao.queryByOrderCode(result, orderCode);
    }
}
