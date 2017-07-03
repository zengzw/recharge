package com.tsh.vas.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.SupplierInfo;
import com.tsh.vas.vo.supplier.QuerySupplierVo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Repository
public class SupplierInfoDao extends HibernateDao<SupplierInfo, Long> {

    /**
     * 根据供应商编码查询对象
     *
     * @param result
     * @param shopSupplierNo
     * @return
     * @throws Exception
     */
    public Result getObjectByShopSupplierNo(Result result, String shopSupplierNo) throws Exception {
        String hql = " from SupplierInfo where shopSupplierNo = ? ";
        SupplierInfo supplierInfo = this.findUnique (hql, shopSupplierNo);
        result.setData (supplierInfo);
        return result;
    }
    
    public Result add(Result result, SupplierInfo supplierInfo) throws Exception {
        this.save (supplierInfo);
        result.setData (supplierInfo);
        return result;
    }

    public Result query(Result result, QuerySupplierVo querySupplierVo) throws Exception {
        Page<SupplierInfo> page = new Page<> (querySupplierVo.getPage (), querySupplierVo.getRows ());
        Criteria criteria = this.createCriteria (SupplierInfo.class);
        if (StringUtils.isNotBlank (querySupplierVo.getCompany ())) {
            criteria.add (Restrictions.ilike ("company", querySupplierVo.getCompany ().trim (), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank (querySupplierVo.getSupplierCode ())) {
            criteria.add (Restrictions.ilike ("supplierCode", querySupplierVo.getSupplierCode ().trim (), MatchMode.ANYWHERE));
        }
        criteria.addOrder (Order.desc ("id"));
        Pagination pagination = this.findPagination (page, criteria);
        result.setData (pagination);
        return result;
    }

    public Result queryBySupplierCode(Result result, String supplierCode){
        String hql = "from SupplierInfo where supplierCode = ? ";
        SupplierInfo supplierInfo = this.findUnique (hql, supplierCode);
        result.setData (supplierInfo);
        return result;
    }

    public Result updateSupplierInfo(Result result, SupplierInfo supplierInfo) throws Exception {
        this.update (supplierInfo);
        result.setData (supplierInfo);
        return result;
    }

    public Result updateCheckStatus(Result result, String supplierCode, Integer checkStatus) throws Exception {
        Date now = new Date ();
        String hql = "update SupplierInfo set checkStatus = ?, checkTime = ? where supplierCode = ? ";
        int ret = this.updateHql (hql, checkStatus, now, supplierCode);
        result.setData (ret);
        return result;
    }

    public Result findBySupplierName(Result result, String supplierName) throws Exception {
        Criteria criteria = this.createCriteria (SupplierInfo.class);
        if (StringUtils.isNotBlank (supplierName)) {
            criteria.add (Restrictions.ilike ("company", supplierName.trim (), MatchMode.ANYWHERE));
        }
        criteria.addOrder (Order.desc ("id"));
        List<SupplierInfo> supplierInfoList = criteria.list ();
        if (supplierInfoList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (supplierInfoList);
        }
        return result;
    }
}
