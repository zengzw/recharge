package com.tsh.vas.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.google.common.collect.Lists;
import com.tsh.vas.model.SupplierBusiness;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Repository
public class SupplierBusinessDao extends HibernateDao<SupplierBusiness, Long> {

    public Result addSupplierBusiness(Result result, List<SupplierBusiness> supplierBusinessList) throws Exception {
        supplierBusinessList = (List<SupplierBusiness>) this.batchSave (supplierBusinessList);
        if (supplierBusinessList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (supplierBusinessList);
        }
        return result;
    }

    public Result getBusinessesCodeBySupplierCode(Result result, String supplierCode) throws Exception {
        Criteria criteria = this.createCriteria (SupplierBusiness.class);
        if (StringUtils.isNotEmpty (supplierCode)) {
            criteria.add (Restrictions.eq ("supplierCode", supplierCode));
        }
        List supplierBusinessList = criteria.list ();
        List<String> businessCodes = Lists.newArrayList ();
        for (Object item : supplierBusinessList) {
            SupplierBusiness supplierBusiness = (SupplierBusiness) item;
            businessCodes.add (supplierBusiness.getBusinessCode ());
        }
        result.setData (businessCodes);
        return result;
    }

    public Result batchDeleteBySupplierCode(Result result, String supplierCode) throws Exception {
        String hql = "delete from SupplierBusiness where supplierCode = ? ";
        int ret = this.batchExecute (hql, supplierCode);
        result.setData (ret);
        return result;
    }

    public Result findByBusinessCodeAndSuppliers(Result result, String businessCode, List<String> supplierCodeList) throws Exception {
        Criteria criteria = this.createCriteria (SupplierBusiness.class);
        if (StringUtils.isNotEmpty (businessCode)) {
            criteria.add (Restrictions.eq ("businessCode", businessCode));
        }
        if (CollectionUtils.isNotEmpty (supplierCodeList)) {
            criteria.add (Restrictions.in ("supplierCode", supplierCodeList));
        }
        criteria.addOrder (Order.desc ("id"));

        List<SupplierBusiness> supplierBusinessList = criteria.list ();
        if (supplierBusinessList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (supplierBusinessList);
        }
        return result;
    }

    public Result queryBySupplierCodeAndBusinessCode(Result result, String supplierCode, String businessCode) throws Exception {
        String hql = "from SupplierBusiness where supplierCode = ? and businessCode = ? ";
        SupplierBusiness supplierBusiness = this.findUnique (hql, supplierCode, businessCode);
        result.setData (supplierBusiness);
        return result;
    }

    public Result updateSupplierBusiness(Result result, SupplierBusiness supplierBusiness) throws Exception {
        this.update (supplierBusiness);
        result.setData (supplierBusiness);
        return result;
    }

    public Result queryBySupplierCode(Result result, String supplierCode) {
        Criteria criteria = this.createCriteria (SupplierBusiness.class);
        if (StringUtils.isNotEmpty (supplierCode)) {
            criteria.add (Restrictions.eq ("supplierCode", supplierCode));
        }
        List<SupplierBusiness> supplierBusinessList = criteria.list ();
        if (supplierBusinessList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (supplierBusinessList);
        }
        return result;
    }

    public Result queryBySupplierCodeInBusiness(Result result, String supplierCode, List<String> businessCodes) {
        Criteria criteria = this.createCriteria (SupplierBusiness.class);
        if (StringUtils.isNotEmpty (supplierCode)) {
            criteria.add (Restrictions.eq ("supplierCode", supplierCode));
        }
        if (!businessCodes.isEmpty ()) {
            criteria.add (Restrictions.in ("businessCode", businessCodes));
        }
        List<SupplierBusiness> supplierBusinessList = criteria.list ();
        if (supplierBusinessList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (supplierBusinessList);
        }
        return result;
    }

    public Result deleteBySupplierCodeAndBusinessCode(Result result, String supplierCode, String businessCode) {
        String hql = "delete from SupplierBusiness where supplierCode = ? AND businessCode = ? ";
        int ret = this.batchExecute (hql, supplierCode, businessCode);
        result.setData (ret);
        return result;
    }
    /**
     * 根据供应商编号查询业务编码
     * @param supplierCode
     * @return
     */
    public List<SupplierBusiness> getBusinessCodeBySupplierCode(String supplierCode){
    	String hql = "from SupplierBusiness where supplierCode = ?";
    	List<SupplierBusiness> supplierBusinesses = this.find(hql, supplierCode);
    	return supplierBusinesses;
    }
}
