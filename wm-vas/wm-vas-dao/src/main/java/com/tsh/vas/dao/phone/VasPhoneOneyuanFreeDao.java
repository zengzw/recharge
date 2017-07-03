package com.tsh.vas.dao.phone;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.ActivityStatisticsPo;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreePo;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreeStatisPo;
import com.tsh.vas.model.trainticket.HcpOrderInfos;
import com.tsh.vas.vo.phone.QueryActivityStatisticsVo;
import com.tsh.vas.vo.phone.VasPhoneOneyuanFreeVo;
import com.tsh.vas.vo.trainticket.HcpOrderInfoReportsVo;

@Repository
@SuppressWarnings("all")
public class VasPhoneOneyuanFreeDao extends HibernateDao<VasPhoneOneyuanFreePo, Integer> {
    /**
     * 新增
     * @param result
     * @param vasPhoneOneyuanFree
     * @return
     */
    public Result addVasPhoneOneyuanFree(Result result,VasPhoneOneyuanFreePo vasPhoneOneyuanFree){
        this.save(vasPhoneOneyuanFree);
        result.setData(vasPhoneOneyuanFree);
        return result;
    }

    /**
     * 批量新增
     * @param result
     * @param vasPhoneOneyuanFree
     * @return
     */
    public Result batchSaveVasPhoneOneyuanFree(Result result, List<VasPhoneOneyuanFreePo> vasPhoneOneyuanFree_list) throws Exception {
        this.batchSave(vasPhoneOneyuanFree_list);
        result.setData(null);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deleteVasPhoneOneyuanFree(Result result, Integer id) throws Exception {
        int count = this.updateHql("delete VasPhoneOneyuanFreePo where id=?",id);
        result.setData(count);
        return result;
    }

    /**
     *
     * @param result
     * @param idArray
     * @param status
     * @param lotterynam
     * @return
     */
    public Result update2Lottery(Result result, String [] idArray, int status, String lotterynam, String batchId){
        StringBuffer idsString = new StringBuffer("(");
        for(int i=0;i<idArray.length;i++){
            if(i == idArray.length-1){
                idsString.append(idArray[i]);
            } else {
                idsString.append(idArray[i] + ",");
            }
        }
        idsString.append(")");
        String sql = "update vas_phone_oneyuan_free set lottery_status = "+ status+", " +
                " batch_id = " + batchId + "," +
                "lottery_time='"+ DateUtil.date2String(new Date())+
                "' , lotteryman = '"+lotterynam+"' where id in " + idsString.toString();
        this.updateSql(sql);
        return result;
    }

    /**
     * 批量删除
     * @param result
     * @param vasPhoneOneyuanFree
     * @return
     */
    public Result batchDelVasPhoneOneyuanFree(Result result, List<VasPhoneOneyuanFreePo> vasPhoneOneyuanFree_list)throws Exception{
        this.batchDelete(vasPhoneOneyuanFree_list);
        return result;
    }


    /**
     * 批量删除ById
     * @param result
     * @param vasPhoneOneyuanFree
     * @return
     */
    public Result batchDelVasPhoneOneyuanFreeByIds(Result result,Integer[] ids)throws Exception{
        int count = 0;
        for(Integer id : ids){
            this.delete(id);
            count ++;
        }
        result.setData(count);
        return result;
    }



    /**
     * 清空表 
     * @param result
     * @return
     */
    public Result clearVasPhoneOneyuanFree(Result result) {
        String sql = " truncate table vas_phone_oneyuan_free ";
        int count = this.getSession().createSQLQuery(sql).executeUpdate();
        result.setData(count);
        return result;
    }





    /**
     * 批量更新 
     * @param result
     * @return
     */
    public Result batchUpdateVasPhoneOneyuanFree(Result result,List<VasPhoneOneyuanFreePo> vasPhoneOneyuanFree_list) throws Exception {
        this.batchUpdate(vasPhoneOneyuanFree_list);
        result.setData(null);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getVasPhoneOneyuanFreeById(Result result,Integer id){
        VasPhoneOneyuanFreePo po = this.findUnique("from VasPhoneOneyuanFreePo where id = ?", id);
        result.setData(po);
        return result;
    }

    /**
     *
     * @param result
     * @param lotteryCount
     * @param chargeAmount
     * @param lotteryStatus
     * @param checkStatus
     * @return
     */
    public Result queryVasPhoneOneyuanFreeList(Result result,
            String lotteryCount,
            String chargeAmount,
            int lotteryStatus,
            int checkStatus){

        StringBuffer sql = new StringBuffer();
        sql.append("select *, (select count(1) from ");
        sql.append("    (select * from vas_phone_oneyuan_free where check_status=" + checkStatus);
        sql.append("     and lottery_status = "+lotteryStatus);

        if(StringUtils.isNotBlank(chargeAmount)){
            sql.append(" and charge_amount = '" + chargeAmount + "' ");
        }

        sql.append("     order by create_time limit "+lotteryCount+" ) t ");

        sql.append(" where t.store_id=r.store_id ) as count ");
        sql.append(" from vas_phone_oneyuan_free r " );
        sql.append(" where check_status = " + checkStatus );
        sql.append(" and lottery_status = "+ lotteryStatus );

        if(StringUtils.isNotBlank(chargeAmount)){
            sql.append(" and charge_amount = '" + chargeAmount + "' ");
        }

        sql.append(" order by r.create_time limit " + lotteryCount);

        List<VasPhoneOneyuanFreeStatisPo> list = this.getSession().createSQLQuery(sql.toString()).addEntity(VasPhoneOneyuanFreeStatisPo.class).list();
        result.setData(list);
        return result;
    }

    /**
     *
     * @param result
     * @return
     */
    public Result queryMaxBatchId(Result result){
        String sql = "select max(batch_id) from vas_phone_oneyuan_free";
        Integer maxBatchId = (Integer) this.getSession().createSQLQuery(sql).uniqueResult();
        result.setData(maxBatchId);
        return result;
    }

    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryVasPhoneOneyuanFreePageList(Result result,Page page,VasPhoneOneyuanFreeVo queryVo){
        Criteria criteria = this.getSession().createCriteria(VasPhoneOneyuanFreePo.class);
        if(null != queryVo){
            if(StringUtils.isNotBlank(queryVo.getOrderCode())){
                criteria.add(Restrictions.eq("orderCode", queryVo.getOrderCode()));
            }
            if(StringUtils.isNotBlank(queryVo.getChargeMobile())){
                criteria.add(Restrictions.eq("chargeMobile", queryVo.getChargeMobile()));
            }
            if(null != queryVo.getChargeAmount() && queryVo.getChargeAmount().intValue() > 0){
                criteria.add(Restrictions.eq("chargeAmount", queryVo.getChargeAmount()));
            }
            if(queryVo.getActivityAmount()!=null && queryVo.getActivityAmount()>0){
                criteria.add(Restrictions.eq("activityAmount", queryVo.getActivityAmount()));
            }
            if(StringUtils.isNotBlank(queryVo.getWinningAmount()) && !queryVo.getWinningAmount().equals("-1")){
                criteria.add(Restrictions.eq("winningAmount", queryVo.getWinningAmount()));
            }
            if(queryVo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", queryVo.getCreateTime()));
            }
            
            if(StringUtils.isNotBlank(queryVo.getStartCreateTime())){
                criteria.add(Restrictions.gt("createTime", DateUtil.str2Date(queryVo.getStartCreateTime() + " 00:00:00")));
            }
            if(StringUtils.isNotBlank(queryVo.getEndCreateTime())){
                criteria.add(Restrictions.lt("createTime", DateUtil.str2Date(queryVo.getEndCreateTime() + " 23:59:59")));
            }
            
            //开奖时间
            if(StringUtils.isNotBlank(queryVo.getLotteryStartTime())){
                criteria.add(Restrictions.gt("lotteryTime", DateUtil.str2Date(queryVo.getLotteryStartTime() + " 00:00:00")));
            }
            if(StringUtils.isNotBlank(queryVo.getLotteryEndTime())){
                criteria.add(Restrictions.lt("lotteryTime", DateUtil.str2Date(queryVo.getLotteryEndTime() + " 23:59:59")));
            }
            
            if(queryVo.getCheckStatus()!=null && queryVo.getCheckStatus() >= 0){
                criteria.add(Restrictions.eq("checkStatus", queryVo.getCheckStatus()));
            }
            if(queryVo.getCheckTime()!=null){
                criteria.add(Restrictions.eq("checkTime", queryVo.getCheckTime()));
            }
            if(queryVo.getLotteryStatus()!=null && queryVo.getLotteryStatus() != -1){
                criteria.add(Restrictions.eq("lotteryStatus", queryVo.getLotteryStatus()));
            }
            if(queryVo.getLotteryTime()!=null){
                criteria.add(Restrictions.eq("lotteryTime", queryVo.getLotteryTime()));
            }
            if(StringUtils.isNotBlank(queryVo.getLotteryman())){
                criteria.add(Restrictions.eq("lotteryman", queryVo.getLotteryman()));
            }
            if(StringUtils.isNotBlank(queryVo.getLuckyNumber())){
                criteria.add(Restrictions.eq("luckyNumber", queryVo.getLuckyNumber()));
            }
            if(queryVo.getBizType()!=null){
                criteria.add(Restrictions.eq("bizType", queryVo.getBizType()));
            }
            if(queryVo.getAreaId()!=null && queryVo.getAreaId() > 0){
                criteria.add(Restrictions.eq("areaId", queryVo.getAreaId()));
            }
            if(StringUtils.isNotBlank(queryVo.getAreaName())){
                criteria.add(Restrictions.eq("areaName", queryVo.getAreaName()));
            }
            if(queryVo.getStoreId()!=null && queryVo.getStoreId()>0){
                criteria.add(Restrictions.eq("storeId", queryVo.getStoreId()));
            }
            if(StringUtils.isNotBlank(queryVo.getStoreName())){
                criteria.add(Restrictions.eq("storeName", queryVo.getStoreName()));
            }
            if(StringUtils.isNotBlank(queryVo.getLotteryType())){
                criteria.add(Restrictions.eq("lotteryType", queryVo.getLotteryType()));
            }
            if(StringUtils.isNotBlank(queryVo.getCashCoupon())){
                criteria.add(Restrictions.eq("cashCoupon", queryVo.getCashCoupon()));
            }
        }
        criteria.addOrder(Order.desc("createTime"));
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }

    
    public Result queryLotteryAmountList(Result result){
        String sql = "select distinct winning_amount from vas_phone_oneyuan_free where  lottery_status = 1 and  winning_amount is not null order by CONVERT(winning_amount,SIGNED)";
        List<String> list = this.getSession().createSQLQuery(sql.toString()).list();
        result.setData(list);
        return result;
    }

    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryVasPhoneOneyuanFreePageList(Result result,Page page,VasPhoneOneyuanFreeVo queryVo,Order order){
        Criteria criteria = this.getSession().createCriteria(VasPhoneOneyuanFreePo.class);
        if(null != queryVo){
            if(StringUtils.isNotBlank(queryVo.getOrderCode())){
                criteria.add(Restrictions.eq("orderCode", queryVo.getOrderCode()));
            }
            if(StringUtils.isNotBlank(queryVo.getChargeMobile())){
                criteria.add(Restrictions.eq("chargeMobile", queryVo.getChargeMobile()));
            }
            if(queryVo.getActivityAmount()!=null && queryVo.getActivityAmount()>0){
                criteria.add(Restrictions.eq("activityAmount", queryVo.getActivityAmount()));
            }
            if(queryVo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", queryVo.getCreateTime()));
            }
            if(queryVo.getCheckStatus()!=null){
                criteria.add(Restrictions.eq("checkStatus", queryVo.getCheckStatus()));
            }
            if(queryVo.getCheckTime()!=null){
                criteria.add(Restrictions.eq("checkTime", queryVo.getCheckTime()));
            }
            if(queryVo.getLotteryStatus()!=null){
                criteria.add(Restrictions.eq("lotteryStatus", queryVo.getLotteryStatus()));
            }
            if(queryVo.getLotteryTime()!=null){
                criteria.add(Restrictions.eq("lotteryTime", queryVo.getLotteryTime()));
            }
            if(StringUtils.isNotBlank(queryVo.getLotteryman())){
                criteria.add(Restrictions.eq("lotteryman", queryVo.getLotteryman()));
            }
            if(queryVo.getLuckyNumber()!=null ){
                criteria.add(Restrictions.eq("luckyNumber", queryVo.getLuckyNumber()));
            }
            if(queryVo.getBizType()!=null){
                criteria.add(Restrictions.eq("bizType", queryVo.getBizType()));
            }
            if(queryVo.getAreaId()!=null && queryVo.getAreaId() > 0){
                criteria.add(Restrictions.eq("areaId", queryVo.getAreaId()));
            }
            if(StringUtils.isNotBlank(queryVo.getAreaName())){
                criteria.add(Restrictions.eq("areaName", queryVo.getAreaName()));
            }
            if(queryVo.getStoreId()!=null && queryVo.getStoreId()>0){
                criteria.add(Restrictions.eq("storeId", queryVo.getStoreId()));
            }
            if(StringUtils.isNotBlank(queryVo.getStoreName())){
                criteria.add(Restrictions.eq("storeName", queryVo.getStoreName()));
            }
        }
        criteria.addOrder(order);
        criteria.addOrder(Order.asc("id"));

        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }



    /**
     * 根据 订单号查询活动单
     *
     * @param orderCode
     * @return
     */
    public VasPhoneOneyuanFreePo queryByOrderNo(String orderCode){
        String hql = " from VasPhoneOneyuanFreePo where orderCode=?";   
        VasPhoneOneyuanFreePo vasPhoneOneyuanFreePo = this.findUnique(hql,orderCode);
        return vasPhoneOneyuanFreePo;
    }


    /**
     * 根据活动表审核状态，根据ID
     *
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateAuditStatus(Result result, String orderCode, Date date,Integer payStatus){
        String hql = " update VasPhoneOneyuanFreePo set checkStatus = ? ,checkTime=? where orderCode = ? ";
        int ret = this.updateHql (hql, payStatus, date,orderCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }

    /**
     * 根据活动表审核状态，根据ID
     *
     * @param result
     * @param orderCode
     * @param checkStatus
     * @return
     * @throws Exception
     */
    public Result updateAuditStatusById(Result result, Long id, Date date, Integer checkStatus){
        String hql = " update VasPhoneOneyuanFreePo set checkStatus = ? ,checkTime=? where id = ? ";
        int ret = this.updateHql (hql, checkStatus, date, id);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }

    /**
     * 修改幸运号
     * 
     * @param result
     * @param id
     * @param luckNumber
     * @return
     */
    public Result updateLuckNumById(Result result,int id,String luckNumber){
        String hql = " update VasPhoneOneyuanFreePo set luckyNumber = ? where id = ? ";
        int ret = this.updateHql (hql,luckNumber,id);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }



    /**
     * 查看活动参与统计
     * 
     * @param result
     * @param id
     * @param luckNumber
     * @return
     */
    public Result queryPageActivityStatistics(Result result,QueryActivityStatisticsVo param){
        StringBuffer baseSql = buildActivityStatisticsSQL(param);

        StringBuffer sqlCount = new StringBuffer();
        sqlCount.append("select count(*) from (");
        sqlCount.append(baseSql);
        sqlCount.append(") temp");

        Session session = this.getSession ();
        Pagination pagination = new Pagination();
        
        //查询总数
        BigInteger total = (BigInteger) session.createSQLQuery(sqlCount.toString()).uniqueResult();
        if(total.intValue() > 0){
         
            //分页sql组装
            StringBuffer limtTerm = new StringBuffer();
            limtTerm.append(" limit ").append(param.getPage()).append(",").append(param.getRows());
            
            //查询数据列表
            String resultSql = baseSql.append(limtTerm).toString();
            SQLQuery sqlQuery = session.createSQLQuery (resultSql).addEntity (ActivityStatisticsPo.class);
            pagination.setRows(sqlQuery.list ());
        }else{
            pagination.setRows(new ArrayList<>());
        }

      
        pagination.setTotal(total.longValue());
        result.setData(pagination);
        return result;
    }

    /**
     * @param param
     * @return
     */
    private StringBuffer buildActivityStatisticsSQL(QueryActivityStatisticsVo param) {
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select id,store_name , count(*) store_count,b.area_name, b.bcount area_count  from vas_phone_oneyuan_free a,");
        baseSql.append(buildStoreSql(param));
        baseSql.append(" where a.area_id = b.area_id and a.check_status=1"); //只查有效的

        //县域ID
        if(param.getStoreId() != null && param.getStoreId() != 0){
            baseSql.append(" and a.store_id="+param.getStoreId());
        }
/*
        //网点ID
        if(param.getAreaId() != null){
            baseSql.append(" and a.area_id="+param.getAreaId());
        }*/

        //开始时间
        if (StringUtils.isNotBlank (param.getBeginTime())) {
            baseSql.append (" and a.create_time >= '").append(param.getBeginTime().trim () + " 00:00:00").append ("'");
        }
        //结束时间
        if (StringUtils.isNotBlank (param.getEndTime())) {
            baseSql.append (" and a.create_time <= '").append(param.getEndTime().trim () + " 23:59:59").append ("'");
        }
        
        baseSql.append(" group by store_id");
        
        //根据报名排序，降序
        baseSql.append(" order by store_count desc ");
        return baseSql;
    }
    
    
    private String buildStoreSql(QueryActivityStatisticsVo param){
        StringBuffer sql = new StringBuffer("(select area_id , area_name,count(*) bcount from vas_phone_oneyuan_free where check_status=1");

        //网点ID
        if(param.getAreaId() != null && param.getAreaId() != 0){
            sql.append(" and area_id="+param.getAreaId());
        }

        //开始时间
        if (StringUtils.isNotBlank (param.getBeginTime())) {
            sql.append (" and create_time >= '").append(param.getBeginTime().trim () + " 00:00:00").append ("'");
        }
        //结束时间
        if (StringUtils.isNotBlank (param.getEndTime())) {
            sql.append (" and create_time <= '").append(param.getEndTime().trim () + " 23:59:59").append ("'");
        }
        
        sql.append(" group by area_id) b");
   
        return sql.toString();
        
    }
    
    
    /**
     * 统计导出
     * 
     * @param result
     * @param param
     * @return
     */
    public Result queryExportActivityStatistics(Result result,QueryActivityStatisticsVo param){
        StringBuffer baseSql = buildActivityStatisticsSQL(param);
        
        Session session = this.getSession ();
        String selectSql = baseSql.toString();
        SQLQuery sqlQuery = session.createSQLQuery (selectSql).addEntity (ActivityStatisticsPo.class);
        result.setData (sqlQuery.list ());
        return result;
    }



}
