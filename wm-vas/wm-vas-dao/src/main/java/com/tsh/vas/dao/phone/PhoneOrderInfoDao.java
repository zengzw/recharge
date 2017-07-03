package com.tsh.vas.dao.phone;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.trainticket.HcpOrderInfos;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;

@Repository
@SuppressWarnings("all")
public class PhoneOrderInfoDao extends HibernateDao<PhoneOrderInfoPo, Long> {
    /**
     * 新增
     * @param result
     * @param phoneOrderInfo
     * @return
     */
    public Result addPhoneOrderInfo(Result result,PhoneOrderInfoPo phoneOrderInfo)throws Exception{
        this.save(phoneOrderInfo);
        result.setData(phoneOrderInfo);

        return result;
    }



    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneOrderInfoById(Result result,long id) throws Exception{
        PhoneOrderInfoPo phoneOrderInfoPo = this.get(id);
        result.setData(phoneOrderInfoPo);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneOrderInfoList(Result result,Page page,PhoneOrderInfoPo phoneOrderInfoPo){
        Criteria criteria = this.getSession().createCriteria(PhoneOrderInfoPo.class);
        if(null != phoneOrderInfoPo){
            if(phoneOrderInfoPo.getPhoneOrderCode()!=null){
                criteria.add(Restrictions.eq("phoneOrderCode", phoneOrderInfoPo.getPhoneOrderCode()));
            }
            if(phoneOrderInfoPo.getSupplierOrderId()!=null){
                criteria.add(Restrictions.eq("supplierOrderId", phoneOrderInfoPo.getSupplierOrderId()));
            }
            if(phoneOrderInfoPo.getSupplierCode()!=null){
                criteria.add(Restrictions.eq("supplierCode", phoneOrderInfoPo.getSupplierCode()));
            }
            if(phoneOrderInfoPo.getSupplierName()!=null){
                criteria.add(Restrictions.eq("supplierName", phoneOrderInfoPo.getSupplierName()));
            }
            if(phoneOrderInfoPo.getCity()!=null){
                criteria.add(Restrictions.eq("city", phoneOrderInfoPo.getCity()));
            }
            if(phoneOrderInfoPo.getProvince()!=null){
                criteria.add(Restrictions.eq("province", phoneOrderInfoPo.getProvince()));
            }
            if(phoneOrderInfoPo.getCountry()!=null){
                criteria.add(Restrictions.eq("country", phoneOrderInfoPo.getCountry()));
            }
            if(phoneOrderInfoPo.getCountryCode()!=null){
                criteria.add(Restrictions.eq("countryCode", phoneOrderInfoPo.getCountryCode()));
            }
            if(phoneOrderInfoPo.getCountryName()!=null){
                criteria.add(Restrictions.eq("countryName", phoneOrderInfoPo.getCountryName()));
            }
            if(phoneOrderInfoPo.getMemberMobile()!=null){
                criteria.add(Restrictions.eq("memberMobile", phoneOrderInfoPo.getMemberMobile()));
            }
            if(phoneOrderInfoPo.getMemberName()!=null){
                criteria.add(Restrictions.eq("memberName", phoneOrderInfoPo.getMemberName()));
            }
            if(phoneOrderInfoPo.getMobile()!=null){
                criteria.add(Restrictions.eq("mobile", phoneOrderInfoPo.getMobile()));
            }
            if(phoneOrderInfoPo.getPayUserName()!=null){
                criteria.add(Restrictions.eq("payUserName", phoneOrderInfoPo.getPayUserName()));
            }
            if(phoneOrderInfoPo.getPayUserId() !=null){
                criteria.add(Restrictions.eq("payUserId", phoneOrderInfoPo.getPayUserId()));
            }
            if(phoneOrderInfoPo.getBizId() !=null){
                criteria.add(Restrictions.eq("bizId", phoneOrderInfoPo.getBizId()));
            }
            if(phoneOrderInfoPo.getBizType()!=null){
                criteria.add(Restrictions.eq("bizType", phoneOrderInfoPo.getBizType()));
            }
            if(phoneOrderInfoPo.getStoreCode()!=null){
                criteria.add(Restrictions.eq("storeCode", phoneOrderInfoPo.getStoreCode()));
            }
            if(phoneOrderInfoPo.getStoreName()!=null){
                criteria.add(Restrictions.eq("storeName", phoneOrderInfoPo.getStoreName()));
            }
            if(phoneOrderInfoPo.getCostingAmount()!=null){
                criteria.add(Restrictions.eq("costingAmount", phoneOrderInfoPo.getCostingAmount()));
            }
            if(phoneOrderInfoPo.getSaleAmount()!=null){
                criteria.add(Restrictions.eq("saleAmount", phoneOrderInfoPo.getSaleAmount()));
            }
            if(phoneOrderInfoPo.getOriginalAmount()!=null){
                criteria.add(Restrictions.eq("originalAmount", phoneOrderInfoPo.getOriginalAmount()));
            }
            if(phoneOrderInfoPo.getRealAmount()!=null){
                criteria.add(Restrictions.eq("realAmount", phoneOrderInfoPo.getRealAmount()));
            }
            if(phoneOrderInfoPo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", phoneOrderInfoPo.getCreateTime()));
            }
            if(phoneOrderInfoPo.getPaySuccssTime()!=null){
                criteria.add(Restrictions.eq("paySuccssTime", phoneOrderInfoPo.getPaySuccssTime()));
            }
            if(phoneOrderInfoPo.getPayStatus() !=null){
                criteria.add(Restrictions.eq("payStatus", phoneOrderInfoPo.getPayStatus()));
            }
            if(phoneOrderInfoPo.getStoreInfo()!=null){
                criteria.add(Restrictions.eq("storeInfo", phoneOrderInfoPo.getStoreInfo()));
            }
            if(phoneOrderInfoPo.getBillYearMonth()!=null){
                criteria.add(Restrictions.eq("billYearMonth", phoneOrderInfoPo.getBillYearMonth()));
            }

            if(phoneOrderInfoPo.getGoodsId()!=null){
                criteria.add(Restrictions.eq("goodsId", phoneOrderInfoPo.getGoodsId()));
            }
            if(phoneOrderInfoPo.getRechargePhone()!=null){
                criteria.add(Restrictions.eq("rechargePhone", phoneOrderInfoPo.getRechargePhone()));
            }
            if(phoneOrderInfoPo.getSources()!=null){
                criteria.add(Restrictions.eq("sources", phoneOrderInfoPo.getSources()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }


    /**
     * 根据订单号修改支付状态
     * 
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatus(Result result, String orderCode, Integer payStatus){
        String hql = " update PhoneOrderInfoPo set payStatus = ? where phoneOrderCode = ? ";
        int ret = this.updateHql (hql, payStatus, orderCode);
        return result;
    }
    
    /**
     * 根据订单号修改支付状态
     * 
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatus(Result result, String orderCode, Integer payStatus,String remark){
        String hql = " update PhoneOrderInfoPo set payStatus = ?,remark=? where phoneOrderCode = ? ";
        int ret = this.updateHql (hql, payStatus, remark,orderCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }

    /**
     * 根据订单号修改支付状态
     * 
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatusAndSuccessTime(Result result, String orderCode, Integer payStatus,Date successTime){
        String hql = " update PhoneOrderInfoPo set payStatus = ?,rechargeSuccessTime=? where phoneOrderCode = ? ";
        int ret = this.updateHql (hql, payStatus, successTime,orderCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }

    /**
     * 根据订单号修改支付状态
     *
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatusAndFailReason(Result result, String orderCode, Integer payStatus, String failReason){
        String hql = " update PhoneOrderInfoPo set payStatus = ? where phoneOrderCode = ? ";
        int ret = this.updateHql (hql, payStatus,orderCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }



    /**
     *  根据开放平台查找订单记录
     *  
     * @param result
     * @param openPlatformNo 公司编号
     * @return
     */
    public Result queryByOpenPlatformNo(Result result, String openPlatformNo) {
        String hql = "from PhoneOrderInfoPo where openPlatformNo = ? ";
        PhoneOrderInfoPo orderInfo = this.findUnique (hql, openPlatformNo);
        result.setData (orderInfo);
        return result;
    }







    /**
     *  根据订单号 获取订单信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryByOrderCode(Result result, String orderCode){
        String hql = "from PhoneOrderInfoPo where phoneOrderCode = ? ";
        PhoneOrderInfoPo chargeInfo = this.findUnique (hql, orderCode);
        result.setData (chargeInfo);
        return result;
    }


    /**
     * 
     * 根据时间、状态 查询位订单
     * 
     * @param result
     * @param time  时间，分钟
     * @param status
     * @return
     */
    public Result queryAllOrderInfoRechargeing(Result result,int time,int status){
        String sql = "SELECT * FROM phone_order_info d where NOW() >= date_add(create_time,INTERVAL "+time 
                +" MINUTE) and d.pay_status="+status+"  order BY d.create_time asc";
        Session session = this.getSession();
        SQLQuery sqlQuery = (SQLQuery) session.createSQLQuery(sql).addEntity(PhoneOrderInfoPo.class);
        List<PhoneOrderInfoPo> lstDeposit = sqlQuery.list();

        result.setData(lstDeposit);
        return result;
    }



    /**
     * 根据条件查询 订单列表
     * 
     * 
     * @param result
     * @param phoneOrderInfoVo
     * @return
     */
    public Result queryOrderInfoListPage(Result result,PhoneOrderInfoVo phoneOrderInfoVo){
        StringBuffer beforeSql = new StringBuffer ();
        beforeSql.append (" SELECT * FROM phone_order_info poi where 1=1");
        
        if(StringUtils.isNotBlank(phoneOrderInfoVo.getStoreCode())){
            beforeSql.append(" and poi.store_code="+phoneOrderInfoVo.getStoreCode());
        }
        
        if(StringUtils.isNotEmpty(phoneOrderInfoVo.getStatusIn())){
            beforeSql.append(" and poi.pay_status in ("+phoneOrderInfoVo.getStatusIn()+")");
        }

        if(StringUtils.isNotBlank(phoneOrderInfoVo.getSources())){
            beforeSql.append(" and poi.sources = '"+phoneOrderInfoVo.getSources()+"'");
        }

        if(null != phoneOrderInfoVo.getPayUserId() && phoneOrderInfoVo.getPayUserId().intValue() > 0){
            beforeSql.append(" and poi.pay_user_id = "+phoneOrderInfoVo.getPayUserId());
        }

        if(StringUtils.isNotBlank(phoneOrderInfoVo.getOpenUserId())){
            beforeSql.append(" and poi.open_user_id = '"+phoneOrderInfoVo.getOpenUserId()+"'");
        }

        if(StringUtils.isNotBlank(phoneOrderInfoVo.getRechargePhone())){
            beforeSql.append(" and poi.recharge_phone in ("+phoneOrderInfoVo.getRechargePhone()+")");
        }
        if(StringUtils.isNotBlank(phoneOrderInfoVo.getSearchInfo())){
            beforeSql.append(" and ( poi.recharge_phone like '"+phoneOrderInfoVo.getSearchInfo()+"%'");
            beforeSql.append(" or poi.phone_order_code like '"+phoneOrderInfoVo.getSearchInfo()+"%'");
            beforeSql.append(" or poi.member_mobile like '"+phoneOrderInfoVo.getSearchInfo()+"%' )");
        }

        if(StringUtils.isNotBlank(phoneOrderInfoVo.getRemark())){
            beforeSql.append(" and  (poi.remark != '"+phoneOrderInfoVo.getRemark()+"' or poi.remark is null)");
            beforeSql.append(" and poi.pay_status != 2 ");
        }

        StringBuffer afterBuffer = new StringBuffer();
        afterBuffer.append(" order by create_time desc ");
        afterBuffer.append(" limit " + phoneOrderInfoVo.getPage() + " ," + phoneOrderInfoVo.getRows());



        Session session = this.getSession ();
        String selectSql = beforeSql.toString() + afterBuffer.toString();

        logger.info("---------> query sql【2】:" + selectSql);

        SQLQuery sqlQuery = session.createSQLQuery (selectSql).addEntity (PhoneOrderInfoPo.class);
        result.setData (sqlQuery.list ());
        return result;
    }
}
