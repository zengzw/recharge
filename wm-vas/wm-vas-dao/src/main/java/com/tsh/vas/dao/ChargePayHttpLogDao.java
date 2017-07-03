package com.tsh.vas.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ChargePayHttpLog;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Repository
public class ChargePayHttpLogDao extends HibernateDao<ChargePayHttpLog, Long> {

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Result insert(Result result, ChargePayHttpLog chargePayHttpLog) throws Exception {
        this.save (chargePayHttpLog);
        result.setData (chargePayHttpLog);
        return result;
    }
}
