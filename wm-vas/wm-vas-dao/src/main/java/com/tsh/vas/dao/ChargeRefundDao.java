package com.tsh.vas.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ChargeRefund;
import org.springframework.stereotype.Repository;

@Repository
public class ChargeRefundDao extends HibernateDao<ChargeRefund, Long> {

    /**
     * 根据缴费订单编号查询退款信息
     *
     * @param chargeCode 缴费订单编号
     * @throws Exception
     */
    public Result getChargeRefundByChargeCode(Result result, String chargeCode){
        String hql = "from ChargeRefund where chargeCode = ?";
        ChargeRefund chargeRefund = this.findUnique (hql, chargeCode);
        result.setData (chargeRefund);
        return result;
    }

    public Result insert(Result result, ChargeRefund chargeRefund) throws Exception {
        this.save (chargeRefund);
        result.setData (chargeRefund);
        return result;
    }

    public Result queryByRefundCode(Result result, String refundCode) throws Exception {
        String hql = "from ChargeRefund where refundCode = ?";
        ChargeRefund chargeRefund = this.findUnique (hql, refundCode);
        result.setData (chargeRefund);
        return result;
    }

    public Result queryByOrderCode(Result result, String orderCode) {
        String hql = "from ChargeRefund where chargeCode = ?";
        ChargeRefund chargeRefund = this.findUnique (hql, orderCode);
        result.setData (chargeRefund);
        return result;
    }
}

