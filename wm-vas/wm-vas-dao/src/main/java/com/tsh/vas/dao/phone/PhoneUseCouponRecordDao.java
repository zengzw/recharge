package com.tsh.vas.dao.phone;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.PhoneUseCouponRecordPo;

/**
 * Created by Administrator on 2017/6/21 021.
 */
@Repository
public class PhoneUseCouponRecordDao extends HibernateDao<PhoneUseCouponRecordPo, Long> {
    
    /**
     * 新增
     * @param result
     * @param phoneRefundAmount
     * @return
     */
    public Result add(Result result,PhoneUseCouponRecordPo phoneUseCouponRecordPo){
        this.save(phoneUseCouponRecordPo);
        result.setData(phoneUseCouponRecordPo);
        return result;
    }
    
    
    /**
     * 根据订单号修改支付状态
     * 
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatusAndSuccessTime(Result result, String orderCode, Integer couponStatus,Date updateTime){
        String hql = " update PhoneUseCouponRecordPo set couponStatus = ?,update_time=? where orderCode = ? ";
        int ret = this.updateHql (hql, couponStatus, updateTime,orderCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }
    
    
    /**
     *  根据订单号 获取优惠券信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryByOrderCode(Result result, String orderCode){
        String hql = "from PhoneUseCouponRecordPo where orderCode = ? ";
        PhoneUseCouponRecordPo chargeInfo = this.findUnique (hql, orderCode);
        result.setData (chargeInfo);
        return result;
    }


}
