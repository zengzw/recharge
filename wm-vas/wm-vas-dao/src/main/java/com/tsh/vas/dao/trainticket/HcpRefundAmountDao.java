package com.tsh.vas.dao.trainticket;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.model.trainticket.HcpRefundAmountPo;

/**
 * 退款Dao
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Repository
@SuppressWarnings("all")
public class HcpRefundAmountDao extends HibernateDao<HcpRefundAmountPo, Integer> {
    /**
     * 新增退款接口对象
     * @param result
     * @param hcpRefundAmount
     * @return
     */
    public Result addHcpRefundAmount(Result result,HcpRefundAmountPo hcpRefundAmount){
        this.save(hcpRefundAmount);
        result.setData(hcpRefundAmount);
        return result;
    }

    /**
     * 批量新增退款接口对象
     * @param result
     * @param hcpRefundAmount
     * @return
     */
    public Result batchSaveHcpRefundAmount(Result result, List<HcpRefundAmountPo> hcpRefundAmount_list) throws Exception {
        this.batchSave(hcpRefundAmount_list);
        result.setData(null);
        return result;
    }

    /**
     * 删除退款接口对象
     * @param id 退款接口对象标识
     * @return
     */
    public Result deleteHcpRefundAmount(Result result, Integer id) throws Exception {
        int count = this.updateHql("delete HcpRefundAmountPo where id=?",id);
        result.setData(count);
        return result;
    }


    /**
     * 批量删除退款接口对象
     * @param result
     * @param hcpRefundAmount
     * @return
     */
    public Result batchDelHcpRefundAmount(Result result, List<HcpRefundAmountPo> hcpRefundAmount_list)throws Exception{
        this.batchDelete(hcpRefundAmount_list);
        return result;
    }


    /**
     * 批量删除退款接口对象ById
     * @param result
     * @param hcpRefundAmount
     * @return
     */
    public Result batchDelHcpRefundAmountByIds(Result result,Integer[] ids)throws Exception{
        int count = 0;
        for(Integer id : ids){
            this.delete(id);
            count ++;
        }
        result.setData(count);
        return result;
    }



 
    

    /**
     * 根据订单号获取订单信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryByOrderCode(Result result, String orderCode){
        String hql = "from HcpRefundAmountPo where hcpOrderCode = ? ";
        HcpRefundAmountPo chargeInfo = this.findUnique (hql, orderCode);
        result.setData (chargeInfo);
        return result;
    }
    
    
    /**
     * 根据退货订单号获取订单信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryByReturnOrderCode(Result result, String refundCode){
        String hql = "from HcpRefundAmountPo where refundAmountCode = ? ";
        HcpRefundAmountPo chargeInfo = this.findUnique (hql, refundCode);
        result.setData (chargeInfo);
        return result;
    }
    
    
    
    /**
     * 根据订单号修改退款状态
     * 
     * @param result
     * @param orderCode
     * @param refundStatus
     * @return
     * @throws Exception
     */
    public Result updateRefundStatus(Result result, String refundCode, Integer refundStatus) {
        String hql = "update HcpRefundAmountPo set status = ? where refundAmountCode = ? ";
        int ret = this.updateHql (hql, refundStatus, refundCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }
    
    /**
     * 根据订单号修改退款状态
     * 
     * @param result
     * @param orderCode
     * @param refundStatus
     * @return
     * @throws Exception
     */
    public Result updateRefundStatusAndTime(Result result, String refundCode, Integer refundStatus,Date time) {
        String hql = "update HcpRefundAmountPo set status = ?,refundTime=? where refundAmountCode = ? ";
        int ret = this.updateHql (hql, refundStatus,time, refundCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }
    
    /**
     * 修改退款时间
     * 
     * @param result
     * @param orderCode
     * @param refundStatus
     * @return
     * @throws Exception
     */
    public Result updateRefundTime(Result result, String refundCode,Date date) {
        String hql = "update HcpRefundAmountPo set refundTime = ? where refundAmountCode = ? ";
        int ret = this.updateHql (hql, date, refundCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }
    
    /**
     * 根据订单号修改退款次数
     * 
     * @param result
     * @param chargeCode
     * @param refundStatus
     * @return
     * @throws Exception
     */
    public Result updateRefundRquestTime(Result result, String refundOrderCode, Integer count){
        String hql = "update HcpRefundAmountPo set refundTimes = ? where refundAmountCode = ? ";
        int ret = this.updateHql (hql, count, refundOrderCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }

    
    /**
     * 根据状态、时间、类型 查找退款单
     * 
     * @param result
     * @param payStatus
     * @param beforTime
     * @return
     * @throws Exception
     */
    public Result findByStatusWaitOrError(Result result,Date beforTime,int status,int refundType){
        String hql = "from HcpRefundAmountPo where status =? and createTime<=? and refundType=?";
        List<HcpRefundAmountPo> lstRefundInfo = this.find(hql, status,beforTime,refundType);
        result.setData (lstRefundInfo);
        return result;
    }
    
    
    /**
     * 根据状态、时间、类型 查找退款单
     * 
     * @param result
     * @param payStatus
     * @param beforTime
     * @return
     * @throws Exception
     */
    public Result findByStatus(Result result,Date beforTime,int status){
        String hql = "from HcpRefundAmountPo where status =? and createTime<=?";
        List<HcpRefundAmountPo> lstRefundInfo = this.find(hql, status,beforTime);
        result.setData (lstRefundInfo);
        return result;
    }
    
    
    /**
     * 根据退款时间查询，状态查询
     * 
     * @param result
     * @param payStatus
     * @param beforTime
     * @return
     * @throws Exception
     */
    public Result findByStatusAndRefundTime(Result result,Date beforTime,int status){
        String hql = "from HcpRefundAmountPo where status =? and refundTime<=?";
        List<HcpRefundAmountPo> lstRefundInfo = this.find(hql, status,beforTime);
        result.setData (lstRefundInfo);
        return result;
    }
    
    
    

    /**
     * 批量更新 退款接口对象
     * @param result
     * @return
     */
    public Result batchUpdateHcpRefundAmount(Result result,List<HcpRefundAmountPo> hcpRefundAmount_list) throws Exception {
        this.batchUpdate(hcpRefundAmount_list);
        result.setData(null);
        return result;
    }


    /**
     * 根据ID获取 退款接口对象
     * @param result
     * @return
     */
    public Result getHcpRefundAmountById(Result result,Integer id) throws Exception{
        HcpRefundAmountPo hcpRefundAmountPo = this.get(id);
        result.setData(hcpRefundAmountPo);
        return result;
    }


    /**
     * 根据条件获取 退款接口对象列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryHcpRefundAmountList(Result result,Page page,HcpRefundAmountPo hcpRefundAmountPo){
        Criteria criteria = this.getSession().createCriteria(HcpRefundAmountPo.class);
        if(null != hcpRefundAmountPo){
            if(hcpRefundAmountPo.getHcpOrderCode()!=null){
                criteria.add(Restrictions.eq("hcpOrderCode", hcpRefundAmountPo.getHcpOrderCode()));
            }
            if(hcpRefundAmountPo.getRefundAmountCode()!=null){
                criteria.add(Restrictions.eq("refundAmountCode", hcpRefundAmountPo.getRefundAmountCode()));
            }
            if(hcpRefundAmountPo.getRefundType()!=null){
                criteria.add(Restrictions.eq("refundType", hcpRefundAmountPo.getRefundType()));
            }
            if(hcpRefundAmountPo.getRealAmount()!=null){
                criteria.add(Restrictions.eq("realAmount", hcpRefundAmountPo.getRealAmount()));
            }
            if(hcpRefundAmountPo.getPayWay()!=null){
                criteria.add(Restrictions.eq("payWay", hcpRefundAmountPo.getPayWay()));
            }
            if(hcpRefundAmountPo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", hcpRefundAmountPo.getCreateTime()));
            }
            if(hcpRefundAmountPo.getUserCode()!=null){
                criteria.add(Restrictions.eq("userCode", hcpRefundAmountPo.getUserCode()));
            }
            if(hcpRefundAmountPo.getUserName()!=null){
                criteria.add(Restrictions.eq("userName", hcpRefundAmountPo.getUserName()));
            }
            if(hcpRefundAmountPo.getUserMobile()!=null){
                criteria.add(Restrictions.eq("userMobile", hcpRefundAmountPo.getUserMobile()));
            }
            if(hcpRefundAmountPo.getRefundTimes()!=null){
                criteria.add(Restrictions.eq("refundTimes", hcpRefundAmountPo.getRefundTimes()));
            }
            if(hcpRefundAmountPo.getRefundTime()!=null){
                criteria.add(Restrictions.eq("refundTime", hcpRefundAmountPo.getRefundTime()));
            }
            if(hcpRefundAmountPo.getRefundDesc()!=null){
                criteria.add(Restrictions.eq("refundDesc", hcpRefundAmountPo.getRefundDesc()));
            }
            if(hcpRefundAmountPo.getRemark()!=null){
                criteria.add(Restrictions.eq("remark", hcpRefundAmountPo.getRemark()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }

}
