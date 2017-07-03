package com.tsh.vas.dao.phone;

import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.VasPhoneLotteryRecordPo;

/**
 * Created by Administrator on 2017/5/24 024.
 */
@Repository
public class VasPhoneLotteryRecordDao extends HibernateDao<VasPhoneLotteryRecordPo, Integer> {


    /**
     *  根据订单号 优化卷信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryLotterRecordByOrderNo(Result result, String orderCode){
        String hql = "from VasPhoneLotteryRecordPo where orderCode = ? ";
        VasPhoneLotteryRecordPo lotteryRecord = this.findUnique (hql, orderCode);
        result.setData (lotteryRecord);
        return result;
    }
    
    
    public Result queryByOrderCode(Result result, String orderCode){
        VasPhoneLotteryRecordPo lotteryRecordPo = this.findUnique("from VasPhoneLotteryRecordPo where orderCode = ?", orderCode);
        result.setData(lotteryRecordPo);
        return result;
    }

}
