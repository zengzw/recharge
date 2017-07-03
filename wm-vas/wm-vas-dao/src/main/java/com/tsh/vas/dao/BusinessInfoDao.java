package com.tsh.vas.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.BusinessInfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Repository
public class BusinessInfoDao extends HibernateDao<BusinessInfo, Long> {

    public Result getByBusinessInfos(Result result, List<String> businessCodes) throws Exception {
        Criteria criteria = this.createCriteria (BusinessInfo.class);
        if (!businessCodes.isEmpty ()) {
            criteria.add (Restrictions.in ("businessCode", businessCodes));
        }
        List<BusinessInfo> businessInfoList = criteria.list ();
        if (businessInfoList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (businessInfoList);
        }
        return result;
    }

    public Result getByBusinessCode(Result result, String businessCode) throws Exception {
        String hql = "from BusinessInfo where businessCode = ? ";
        BusinessInfo businessInfoList = this.findUnique (hql, businessCode);
        result.setData (businessInfoList);
        return result;
    }

    public Result findAll(Result result) throws Exception {
    	String hql = "from BusinessInfo order by id desc";
    	List<BusinessInfo> businessInfoList = this.find(hql);
        result.setData (businessInfoList);
        return result;
    }

    /**
     * 获取父级服务
     *
     * @param result
     * @return
     */
    public Result getBusinessInfoParent(Result result) throws Exception {
        String hql = "from BusinessInfo where parentCode = ? ";
        List<BusinessInfo> businessInfoList = this.find (hql, "");
        if (businessInfoList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (businessInfoList);
        }
        return result;
    }

    /**
     * 根据父级获取子级服务
     *
     * @param result
     * @return
     * @Date 2016年10月14日<br>
     */
    public Result getBusinessInfoByParent(Result result, String parentCode) throws Exception {
        String hql = "from BusinessInfo where parentCode = ? ";
        BusinessInfo businessInfo = this.findUnique (hql, parentCode);
        result.setData (businessInfo);
        return result;
    }
}
