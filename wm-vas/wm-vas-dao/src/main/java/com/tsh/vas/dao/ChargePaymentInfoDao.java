package com.tsh.vas.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ChargePaymentInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Repository
public class ChargePaymentInfoDao extends HibernateDao<ChargePaymentInfo, Long> {

    public Result insert(Result result, ChargePaymentInfo chargePaymentInfo) throws Exception {
        this.save (chargePaymentInfo);
        result.setData (chargePaymentInfo);
        return result;
    }
    
    /**
     * 根据缴费订单号查询支付信息
     *
     * @param chargeCode 缴费订单号
     * @return
     */
    public Result getChargePaymentInfoByChargeCode(Result result, String chargeCode) throws Exception {
        String hql = "from ChargePaymentInfo where chargeCode = ? ";
        ChargePaymentInfo chargePaymentInfo = this.findUnique (hql, chargeCode);
        result.setData (chargePaymentInfo);
        return result;
    }
}
