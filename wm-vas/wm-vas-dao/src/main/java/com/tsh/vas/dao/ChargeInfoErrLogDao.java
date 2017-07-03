package com.tsh.vas.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ChargeInfoErrLog;
import org.springframework.stereotype.Repository;

/**
 * Created by Iritchie.ren on 2016/11/3.
 */
@Repository
public class ChargeInfoErrLogDao extends HibernateDao<ChargeInfoErrLog, Long> {

    public Result addLog(Result result, ChargeInfoErrLog chargeInfoErrLog) throws Exception {
        this.save (chargeInfoErrLog);
        result.setData (chargeInfoErrLog);
        return result;
    }
}
