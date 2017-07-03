package com.tsh.vas.dao;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.google.common.collect.Lists;
import com.tsh.vas.model.SupplierAreaBusiness;
import com.tsh.vas.vo.business.QueryAreaParam;
import com.tsh.vas.vo.business.SupplierOrderParamVo;
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
public class SupplierAreaBusinessDao extends HibernateDao<SupplierAreaBusiness, Long> {

    public Result findBySupplierCodeAndBusinessCode(Result result, String supplierCode, String businessCode) throws Exception {
        Criteria criteria = this.createCriteria (SupplierAreaBusiness.class);
        if (StringUtils.isNotEmpty (supplierCode)) {
            criteria.add (Restrictions.eq ("supplierCode", supplierCode));
        }
        if (StringUtils.isNotEmpty (supplierCode)) {
            criteria.add (Restrictions.eq ("businessCode", businessCode));
        }
        List<SupplierAreaBusiness> supplierBusinessList = criteria.list ();
        if (supplierBusinessList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (supplierBusinessList);
        }
        return result;
    }

    public Result findBusinessAreasByParam(Result result, QueryAreaParam queryAreaParam) throws Exception {
        Page<SupplierAreaBusiness> page = new Page<> (queryAreaParam.getPage (), queryAreaParam.getRows ());
        StringBuilder sqlQuery = new StringBuilder ("SELECT supplier_code as supplierCode, business_code as businessCode,city,country,country_code as countryCode,country_name as countryName,create_time as createTime,province FROM supplier_area_business where 1=1 ");
        StringBuilder sqlCount = new StringBuilder ("SELECT COUNT(1) FROM(SELECT business_code, city, country, country_code, create_time, province FROM supplier_area_business where 1 = 1 ");
        List<Object> params = Lists.newArrayList ();
        if (StringUtils.isNotBlank (queryAreaParam.getSupplierCode ())) {
            sqlQuery.append (" AND supplier_code = ? ");
            sqlCount.append (" AND supplier_code = ? ");
            params.add (queryAreaParam.getSupplierCode ().trim ());
        }
        if (StringUtils.isNotBlank (queryAreaParam.getBusinessCode ())) {
            sqlQuery.append (" AND business_code = ? ");
            sqlCount.append (" AND business_code = ? ");
            params.add (queryAreaParam.getBusinessCode ().trim ());
        }
        if (StringUtils.isNotBlank (queryAreaParam.getProvince ())) {
            sqlQuery.append (" AND province = ? ");
            sqlCount.append (" AND province = ? ");
            params.add (queryAreaParam.getProvince ().trim ());
        }
        if (StringUtils.isNotBlank (queryAreaParam.getCity ())) {
            sqlQuery.append (" AND city = ? ");
            sqlCount.append (" AND city = ? ");
            params.add (queryAreaParam.getCity ().trim ());
        }
        if (StringUtils.isNotBlank (queryAreaParam.getCountryCode ())) {
            sqlQuery.append (" AND country_code = ? ");
            sqlCount.append (" AND country_code = ? ");
            params.add (queryAreaParam.getCountryCode ().trim ());
        }
        if (StringUtils.isNotBlank (queryAreaParam.getCountryName ())) {
            sqlQuery.append (" AND country_name like ? ");
            sqlCount.append (" AND country_name like ? ");
            params.add ("%" + queryAreaParam.getCountryName ().trim () + "%");
        }
        sqlQuery.append (" GROUP BY business_code, province, city, country, country_code limit " + (page.getFirst () - 1) + " , " + page.getPageSize ());
        sqlCount.append (" GROUP BY business_code, province, city, country, country_code ) a ");
        Long total = this.queryForLong (sqlCount.toString (), params.toArray ());
        List resultMap = this.queryForList (sqlQuery.toString (), params.toArray ());
        List<SupplierAreaBusiness> supplierAreaBusinessList = JSONObject.parseArray (JSONObject.toJSONString (resultMap), SupplierAreaBusiness.class);
        Pagination pagination = new Pagination ();
        pagination.setRows (supplierAreaBusinessList);
        pagination.setTotal (total);
        result.setData (pagination);
        return result;
    }

    public Result findByBusinessCodeAndCountryCode(Result result, String businessCode, String countryCode) throws Exception {
        Criteria criteria = this.createCriteria (SupplierAreaBusiness.class);
        if (StringUtils.isNotEmpty (businessCode)) {
            criteria.add (Restrictions.eq ("businessCode", businessCode));
        }
        if (StringUtils.isNotEmpty (countryCode)) {
            criteria.add (Restrictions.eq ("countryCode", countryCode));
        }
        if (StringUtils.isNotEmpty (countryCode)) {
            criteria.addOrder (Order.asc ("supplierOrder"));
        }
        List<SupplierAreaBusiness> supplierAreaBusinessList = criteria.list ();
        if (supplierAreaBusinessList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (supplierAreaBusinessList);
        }
        return result;
    }

    public Result updateOrder(Result result, List<SupplierAreaBusiness> supplierAreaBusinesses) throws Exception {
        this.batchUpdate (supplierAreaBusinesses);
        result.setData (supplierAreaBusinesses);
        return result;
    }

    public Result queryByBusinessCodeAndCountryCodeAndSupplierCode(Result result, SupplierOrderParamVo supplierOrderParamVo) throws Exception {
        String hql = "from SupplierAreaBusiness where businessCode = ? AND countryCode = ? AND supplierCode = ? ";
        SupplierAreaBusiness supplierAreaBusiness = this.findUnique (hql, supplierOrderParamVo.getBusinessCode (), supplierOrderParamVo.getCountryCode (), supplierOrderParamVo.getSupplierCode ());
        result.setData (supplierAreaBusiness);
        return result;
    }

    public Result queryBusinessByArea(Result result, String countryCode) throws Exception {
        String sql = "SELECT country_code as countryCode, country_name as countryName, business_code as businessCode from supplier_area_business where country_code = ? GROUP BY business_code ";
        List list = this.queryForList (sql, countryCode);
        result.setData (list);
        return result;
    }

    public Result deleteBySupplierCodeAndBusinessCode(Result result, String supplierCode, String businessCode) throws Exception {
        String hql = "delete from SupplierAreaBusiness where supplierCode = ? AND businessCode = ? ";
        int ret = this.batchExecute (hql, supplierCode, businessCode);
        result.setData (ret);
        return result;
    }

    /**
     * 添加
     *
     * @param result
     * @param supplierAreaBusiness
     * @return
     */
    public Result addSupplierAreaBusiness(Result result, SupplierAreaBusiness supplierAreaBusiness) {
        this.save (supplierAreaBusiness);
        result.setData (supplierAreaBusiness);
        return result;
    }

    public Result findByBusinessCode(Result result, String countryCode, String businessCode) {
    	String hql = " from SupplierAreaBusiness where (businessCode = ? and countryCode = ?) OR (businessCode=? AND countryCode = -1) order by supplierOrder asc";
    	
    	List<SupplierAreaBusiness> supplierInfoList = this.find(hql, businessCode,countryCode,businessCode);
        if (supplierInfoList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (supplierInfoList);
        }
        return result;
    }

    public Result findByBusinessCode(Result result, String businessCode) {
        String hql = " from SupplierAreaBusiness where businessCode = ? ";

        List<SupplierAreaBusiness> supplierInfoList = this.find(hql, businessCode);
        if (supplierInfoList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (supplierInfoList);
        }
        return result;
    }

    /**
     * 根据主键id更新排序字段
     *
     * @param id
     * @param supplierOrder
     * @return
     */
    public Result updateSortFieldById(Result result, Long id, Integer supplierOrder) {
        String hql = "update SupplierAreaBusiness set supplierOrder = ? where id = ?";
        this.updateHql (hql, supplierOrder, id);
        return result;
    }

    /**
     * 根据排序字段查询对象
     *
     * @param result
     * @param supplierOrder
     * @return
     */
    public Result getGovernmentBySortField(Result result, Integer supplierOrder) {
        String hql = "from SupplierAreaBusiness where supplierOrder = ?";
        SupplierAreaBusiness supplierAreaBusiness = this.findUnique (hql, supplierOrder);
        result.setData (supplierAreaBusiness);
        return result;
    }

    public Result deleteBySupplierCodeAndBusinessCodeAddCountryCode(Result result, String supplierCode, String businessCode, String countryCode) throws Exception {
        String hql = "delete from SupplierAreaBusiness where supplierCode = ? AND businessCode = ? and  countryCode = ? ";
        int ret = this.batchExecute (hql, supplierCode, businessCode, countryCode);
        result.setData (ret);
        return result;
    }

    public Result addSupplierAreaBusinessBatch(Result result, List<SupplierAreaBusiness> addSupplierAreaBusinesses) throws Exception {
        this.batchSave (addSupplierAreaBusinesses);
        result.setData (addSupplierAreaBusinesses);
        return result;
    }

    public Result findBySupplierCodeAndBusinessCodeInAreaId(Result result, String supplierCode, String businessCode, List<String> countryCodes) {
        Criteria criteria = this.createCriteria (SupplierAreaBusiness.class);
        if (StringUtils.isNotEmpty (supplierCode)) {
            criteria.add (Restrictions.eq ("supplierCode", supplierCode));
        }
        if (StringUtils.isNotEmpty (supplierCode)) {
            criteria.add (Restrictions.eq ("businessCode", businessCode));
        }
        if (!countryCodes.isEmpty ()) {
            criteria.add (Restrictions.in ("countryCode", countryCodes));
        }
        List<SupplierAreaBusiness> supplierBusinessList = criteria.list ();
        if (supplierBusinessList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (supplierBusinessList);
        }
        return result;
    }
}
