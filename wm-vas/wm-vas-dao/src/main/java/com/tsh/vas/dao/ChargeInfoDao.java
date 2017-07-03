package com.tsh.vas.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.enume.ClientChargePayStatus;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.model.OrderReport;
import com.tsh.vas.vo.business.QueryOrderParamVo;
import com.tsh.vas.vo.charge.ChargeSearchVo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Iritchie.ren on 2016/9/14.
 */
@Repository
public class ChargeInfoDao extends HibernateDao<ChargeInfo, Long> {

    public Result insert(Result result, ChargeInfo chargeInfo) throws Exception {
        this.save (chargeInfo);
        result.setData (chargeInfo);
        return result;
    }

    public Result query(Result result, ChargeSearchVo chargeSearchVo) throws Exception {
        Page<ChargeInfo> page = new Page<> (chargeSearchVo.getPage (), chargeSearchVo.getRows ());
        Criteria criteria = createCriteria (ChargeInfo.class);
        if (StringUtils.isNotEmpty (chargeSearchVo.getStoreCode ())) {
            criteria.add (Restrictions.eq ("storeCode", chargeSearchVo.getStoreCode ()));
        }
        if (StringUtils.isNotEmpty (chargeSearchVo.getSearchInfo ())) {
            Criterion rest1 = Restrictions.ilike ("rechargeUserCode", chargeSearchVo.getSearchInfo ());
            Criterion rest2 = Restrictions.ilike ("chargeCode", chargeSearchVo.getSearchInfo ());
            Criterion rest3 = Restrictions.ilike ("mobile", chargeSearchVo.getSearchInfo ());
            criteria.add (Restrictions.or (rest1, rest2, rest3));
        }
        if (StringUtils.isNotBlank (chargeSearchVo.getBusinessCode ())) {
            criteria.add (Restrictions.eq ("subBusinessCode", chargeSearchVo.getBusinessCode ()));
        }
        if (chargeSearchVo.getPayStatus () != null) {
            criteria.add (Restrictions.in ("payStatus", ClientChargePayStatus.convertPayStatus (chargeSearchVo.getPayStatus ())));
        }
        criteria.addOrder (Order.desc ("createTime"));

        Pagination pagination = this.findPagination (page, criteria);
        result.setData (pagination);
        return result;
    }

    public Result queryByChargeCode(Result result, String chargeCode){
        String hql = "from ChargeInfo where chargeCode = ? ";
        ChargeInfo chargeInfo = this.findUnique (hql, chargeCode);
        result.setData (chargeInfo);
        return result;
    }

    /**
     * 根据订单查询条件分页
     *
     * @param queryOrderParamVo
     * @return
     * @throws ParseException
     */
    public Result getChargeInfoByPage(Result result, QueryOrderParamVo queryOrderParamVo) throws Exception {
        DateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Page page = new Page (queryOrderParamVo.getPage (), queryOrderParamVo.getRows ());
        Criteria criteria = createCriteria (ChargeInfo.class);
        if (StringUtils.isNotBlank (queryOrderParamVo.getBusinessCode ())) {
            criteria.add (Restrictions.eq ("businessCode", queryOrderParamVo.getBusinessCode ()));
        }
        if (StringUtils.isNotBlank (queryOrderParamVo.getStoreName ())) {
            criteria.add (Restrictions.eq ("storeName", queryOrderParamVo.getStoreName ()));
        }
        if (StringUtils.isNotBlank (queryOrderParamVo.getCountryCode ())) {
            criteria.add (Restrictions.eq ("countryCode", queryOrderParamVo.getCountryCode ()));
        }
        if (StringUtils.isNotBlank (queryOrderParamVo.getBusinessCode ())) {
            criteria.add (Restrictions.eq ("businessCode", queryOrderParamVo.getBusinessCode ().trim ()));
        }
        if (StringUtils.isNotBlank (queryOrderParamVo.getSubBusinessCode ())) {
            criteria.add (Restrictions.eq ("subBusinessCode", queryOrderParamVo.getSubBusinessCode ().trim ()));
        }
        if (StringUtils.isNotBlank (queryOrderParamVo.getChargeCode ())) {
            criteria.add (Restrictions.like ("chargeCode", queryOrderParamVo.getChargeCode ().trim (), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank (queryOrderParamVo.getStartDate ()) && StringUtils.isNotBlank (queryOrderParamVo.getEndDate ())) {
            Date startDate = df.parse (queryOrderParamVo.getStartDate () + " 00:00:00");
            Date endDate = df.parse (queryOrderParamVo.getEndDate () + " 23:59:59");
            criteria.add (Restrictions.between ("createTime", startDate, endDate));
        } else {
            if (StringUtils.isNotBlank (queryOrderParamVo.getStartDate ())) {
                Date startDate = df.parse (queryOrderParamVo.getStartDate () + " 00:00:00");
                criteria.add (Restrictions.gt ("createTime", startDate));
            }
            if (StringUtils.isNotBlank (queryOrderParamVo.getEndDate ())) {
                Date endDate = df.parse (queryOrderParamVo.getEndDate () + " 23:59:59");
                criteria.add (Restrictions.lt ("createTime", endDate));
            }
        }
        //充值缴费账号
        if (StringUtils.isNotBlank (queryOrderParamVo.getRechargeUserCode ())) {
            criteria.add (Restrictions.ilike ("rechargeUserCode", queryOrderParamVo.getRechargeUserCode ().trim (), MatchMode.ANYWHERE));
        }
        //充值缴费联系人姓名
        if (StringUtils.isNotBlank (queryOrderParamVo.getRechargeUserName ())) {
            criteria.add (Restrictions.ilike ("rechargeUserName", queryOrderParamVo.getRechargeUserName ().trim (), MatchMode.ANYWHERE));
        }
        //支付状态：1：待支付。2：支付中。3：缴费中。4：交易成功，5：交易失败，6：交易关闭，7：支付异常，8，缴费异常，9，撤销,10扣款成功
        if (queryOrderParamVo.getPayStatus () != null && !queryOrderParamVo.getPayStatus ().equals (-1)) {
            //List<Integer> list = ClientChargePayStatus.convertPayStatus (queryOrderParamVo.getPayStatus ());
            List<Integer> list = new ArrayList<Integer> ();
            list.add (queryOrderParamVo.getPayStatus ());
            criteria.add (Restrictions.in ("payStatus", list));
        }
        //省
        if (StringUtils.isNotBlank (queryOrderParamVo.getProvince ())) {
            criteria.add (Restrictions.eq ("province", queryOrderParamVo.getProvince ().trim ()));
        }
        //市
        if (StringUtils.isNotBlank (queryOrderParamVo.getCity ())) {
            criteria.add (Restrictions.eq ("city", queryOrderParamVo.getCity ().trim ()));
        }
        //县域中心
        if (StringUtils.isNotBlank (queryOrderParamVo.getCountryName ())) {
            criteria.add (Restrictions.eq ("countryName", queryOrderParamVo.getCountryName ().trim ()));
        }
        //服务供应商
        if (StringUtils.isNotBlank (queryOrderParamVo.getSupplierName ())) {
            criteria.add (Restrictions.like ("supplierName", queryOrderParamVo.getSupplierName ().trim (), MatchMode.ANYWHERE));
        }
        //退款状态
        if (null != queryOrderParamVo.getRefundStatus () && -1 != queryOrderParamVo.getRefundStatus ()) {
            criteria.add (Restrictions.eq ("refundStatus", queryOrderParamVo.getRefundStatus ()));
        }
        //充值缴费联系人电话
        if (StringUtils.isNotBlank (queryOrderParamVo.getMemberMobile ())) {
            criteria.add (Restrictions.like ("memberMobile", queryOrderParamVo.getMemberMobile ().trim (), MatchMode.ANYWHERE));
        }
        criteria.addOrder (Order.desc ("id"));
        Pagination pagination = this.findPagination (page, criteria);
        result.setData (pagination);
        return result;
    }

    /**
     * 查询导出
     *
     * @param result
     * @param queryOrderParamVo
     * @return
     */
    public Result getChargeInfoByPageExport(Result result, QueryOrderParamVo queryOrderParamVo) throws Exception {
        StringBuffer sql = new StringBuffer ();
        sql.append (" select * from ( SELECT ci.* ,cr.refund_amount ,cr.refund_code ");
        sql.append (" FROM charge_info ci LEFT JOIN charge_payment_info cpi ON ci.charge_code = cpi.charge_code ");
        sql.append (" LEFT JOIN charge_refund cr ON ci.charge_code = cr.charge_code) as t where 1 = 1 ");
        //增值服务类型
        if (StringUtils.isNotBlank (queryOrderParamVo.getBusinessCode ())) {
            sql.append (" and t.business_code = ").append ("'").append (queryOrderParamVo.getBusinessCode ().trim ()).append ("'");
        }
        //业务分类
        if (StringUtils.isNotBlank (queryOrderParamVo.getSubBusinessCode ())) {
            sql.append (" and t.sub_business_code = ").append ("'").append (queryOrderParamVo.getSubBusinessCode ().trim ()).append ("'");
        }
        //订单编号
        if (StringUtils.isNotBlank (queryOrderParamVo.getChargeCode ())) {
            sql.append (" and t.charge_code like'%").append (queryOrderParamVo.getChargeCode ().trim ()).append ("%' ");
        }
        //开始时间
        if (StringUtils.isNotBlank (queryOrderParamVo.getStartDate ())) {
            sql.append (" and t.create_time >= '").append (queryOrderParamVo.getStartDate ().trim () + " 00:00:00").append ("'");
        }
        //结束时间
        if (StringUtils.isNotBlank (queryOrderParamVo.getEndDate ())) {
            sql.append (" and t.create_time <= '").append (queryOrderParamVo.getEndDate ().trim () + " 23:59:59").append ("'");
        }
        //充值缴费账号
        if (StringUtils.isNotBlank (queryOrderParamVo.getRechargeUserCode ())) {
            sql.append (" and t.recharge_user_code like '%").append (queryOrderParamVo.getRechargeUserCode ().trim ()).append ("%'");
        }
        //网点名称
        if (StringUtils.isNotBlank (queryOrderParamVo.getStoreName ())) {
            sql.append (" and t.store_name = ").append ("'").append (queryOrderParamVo.getStoreName ()).append ("'");
        }
        //订单状态
        if (null != queryOrderParamVo.getPayStatus () && -1 != queryOrderParamVo.getPayStatus ()) {
            //List<Integer> payStatus = ClientChargePayStatus.convertPayStatus (queryOrderParamVo.getPayStatus ());
            //String inString = StringUtils.join (payStatus.toArray (), ",");
            String inString = String.valueOf (queryOrderParamVo.getPayStatus ());
            sql.append (" and t.pay_status in( ").append (inString).append (")");
        }
        //充值缴费联系人姓名
        if (StringUtils.isNotBlank (queryOrderParamVo.getRechargeUserName ())) {
            sql.append (" and t.recharge_user_name like '%").append (queryOrderParamVo.getRechargeUserName ().trim ()).append ("%' ");
        }
        //省
        if (StringUtils.isNotBlank (queryOrderParamVo.getProvince ())) {
            sql.append (" and t.province = '").append (queryOrderParamVo.getProvince ().trim ()).append ("'");
        }
        //市
        if (StringUtils.isNotBlank (queryOrderParamVo.getCity ())) {
            sql.append (" and t.city = '").append (queryOrderParamVo.getCity ().trim ()).append ("'");
        }
        //县域中心
        if (StringUtils.isNotBlank (queryOrderParamVo.getCountryName ())) {
            sql.append (" and t.country_name = '").append (queryOrderParamVo.getCountryName ().trim ()).append ("'");
        }
        //服务供应商
        if (StringUtils.isNotBlank (queryOrderParamVo.getSupplierName ())) {
            sql.append (" and t.supplier_name like '%").append (queryOrderParamVo.getSupplierName ().trim ()).append ("%'");
        }
        //退款状态
        if (queryOrderParamVo.getRefundStatus () != null && !queryOrderParamVo.getRefundStatus ().equals (-1)) {
            sql.append (" and t.refund_status = ").append (queryOrderParamVo.getRefundStatus ());
        }
        //充值缴费联系人电话
        if (StringUtils.isNotBlank (queryOrderParamVo.getMemberMobile ())) {
            sql.append (" and t.member_mobile like '%").append (queryOrderParamVo.getMemberMobile ().trim ()).append ("%' ");
        }
        //县域id
        if (StringUtils.isNotBlank (queryOrderParamVo.getCountryCode ())) {
            sql.append (" and t.country_code = '").append (queryOrderParamVo.getCountryCode ().trim ()).append ("'");
        }
        Session session = this.getSession ();
        SQLQuery sqlQuery = session.createSQLQuery (sql.toString ()).addEntity (OrderReport.class);
        result.setData (sqlQuery.list ());
        
        return result;
    }
    
    public Result updateStatus(Result result, String chargeCode, Integer payStatus) throws Exception {
        String hql = " update ChargeInfo set payStatus = ? where chargeCode = ? ";
        int ret = this.updateHql (hql, payStatus, chargeCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }

    public Result updateRefundStatus(Result result, String chargeCode, Integer refundStatus) throws Exception {
        String hql = "update ChargeInfo set refundStatus = ? where chargeCode = ? ";
        int ret = this.updateHql (hql, refundStatus, chargeCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }

    public Result findByPayStatusAndTime(Result result, Integer payStatus, Date beforTime) throws Exception {
        Criteria criteria = createCriteria (ChargeInfo.class);
        criteria.add (Restrictions.eq ("payStatus", payStatus));
        criteria.add (Restrictions.le ("createTime", beforTime));
        List<ChargeInfo> chargeInfoList = criteria.list ();
        if (chargeInfoList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (chargeInfoList);
        }
        return result;
    }

    public Result queryByOpenPlatformNo(Result result, String openPlatformNo) {
        String hql = "from ChargeInfo where openPlatformNo = ? ";
        ChargeInfo chargeInfo = this.findUnique (hql, openPlatformNo);
        result.setData (chargeInfo);
        return result;
    }

    public Result findByPayStatusAndTimeAndRefundStatus(Result result, Integer payStatus, Integer refundStatus, Date beforeDate) {
        Criteria criteria = createCriteria (ChargeInfo.class);
        criteria.add (Restrictions.eq ("payStatus", payStatus));
        criteria.add (Restrictions.eq ("refundStatus", refundStatus));
        criteria.add (Restrictions.le ("createTime", beforeDate));
        List<ChargeInfo> chargeInfoList = criteria.list ();
        if (chargeInfoList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (chargeInfoList);
        }
        return result;
    }
}
