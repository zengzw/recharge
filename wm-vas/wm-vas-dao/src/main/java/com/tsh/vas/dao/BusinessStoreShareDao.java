package com.tsh.vas.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.BusinessStoreShare;
import org.springframework.stereotype.Repository;

/**
 * Created by Iritchie.ren on 2016/9/21.
 */
@Repository
public class BusinessStoreShareDao extends HibernateDao<BusinessStoreShare, Long> {

    public Result queryByCountryCodeAndBusinessCode(Result result, String countryCode, String businessCode) throws Exception {
        String hql = "from BusinessStoreShare where countryCode = ? and businessCode = ? ";
        BusinessStoreShare businessStoreShare = this.findUnique (hql, countryCode, businessCode);
        result.setData (businessStoreShare);
        return result;
    }

    public Result updateOrAddBusinessStoreShare(Result result, BusinessStoreShare businessStoreShare) throws Exception {
        if (businessStoreShare.getId () == null || businessStoreShare.getId () == 0) {
            this.save (businessStoreShare);
        } else {
            this.update (businessStoreShare);
        }
        result.setData (businessStoreShare);
        return result;
    }

    public Result addBusinessStoreShare(Result result, BusinessStoreShare businessStoreShare) throws Exception {
        this.save (businessStoreShare);
        result.setData (businessStoreShare);
        return result;
    }
}
