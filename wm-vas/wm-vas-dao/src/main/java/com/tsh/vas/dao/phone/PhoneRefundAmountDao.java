package com.tsh.vas.dao.phone;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneRefundAmountPo;
import com.tsh.vas.model.trainticket.HcpRefundAmountPo;

@Repository
@SuppressWarnings("all")
public class PhoneRefundAmountDao extends HibernateDao<PhoneRefundAmountPo, Long> {
    /**
     * 新增
     * @param result
     * @param phoneRefundAmount
     * @return
     */
    public Result addPhoneRefundAmount(Result result,PhoneRefundAmountPo phoneRefundAmount){
        this.save(phoneRefundAmount);
        result.setData(phoneRefundAmount);
        return result;
    }

    /**
     * 批量新增
     * @param result
     * @param phoneRefundAmount
     * @return
     */
    public Result batchSavePhoneRefundAmount(Result result, List<PhoneRefundAmountPo> phoneRefundAmount_list){
        this.batchSave(phoneRefundAmount_list);
        result.setData(null);
        return result;
    }






    /**
     * 批量更新
     * @param result
     * @return
     */
    public Result batchUpdatePhoneRefundAmount(Result result,List<PhoneRefundAmountPo> phoneRefundAmount_list) throws Exception {
        this.batchUpdate(phoneRefundAmount_list);
        result.setData(null);
        return result;
    }


    /**
     * 根据ID获取
     * @param result
     * @return
     */
    public Result getPhoneRefundAmountById(Result result,Long id) throws Exception{
        PhoneRefundAmountPo phoneRefundAmountPo = this.get(id);
        result.setData(phoneRefundAmountPo);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneRefundAmountList(Result result,Page page,PhoneRefundAmountPo phoneRefundAmountPo){
        Criteria criteria = this.getSession().createCriteria(PhoneRefundAmountPo.class);
        if(null != phoneRefundAmountPo){
            if(phoneRefundAmountPo.getRefundAmountCode()!=null){
                criteria.add(Restrictions.eq("refundAmountCode", phoneRefundAmountPo.getRefundAmountCode()));
            }
            if(phoneRefundAmountPo.getPhoneOrderCode()!=null){
                criteria.add(Restrictions.eq("phoneOrderCode", phoneRefundAmountPo.getPhoneOrderCode()));
            }
            if(phoneRefundAmountPo.getRefundType()!=null){
                criteria.add(Restrictions.eq("refundType", phoneRefundAmountPo.getRefundType()));
            }
            if(phoneRefundAmountPo.getRealAmount()!=null){
                criteria.add(Restrictions.eq("realAmount", phoneRefundAmountPo.getRealAmount()));
            }
            if(phoneRefundAmountPo.getStatus()!=null){
                criteria.add(Restrictions.eq("status", phoneRefundAmountPo.getStatus()));
            }
            if(phoneRefundAmountPo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", phoneRefundAmountPo.getCreateTime()));
            }
            if(phoneRefundAmountPo.getRefundTimes()!=null){
                criteria.add(Restrictions.eq("refundTimes", phoneRefundAmountPo.getRefundTimes()));
            }
            if(phoneRefundAmountPo.getRefundTime()!=null){
                criteria.add(Restrictions.eq("refundTime", phoneRefundAmountPo.getRefundTime()));
            }
            if(phoneRefundAmountPo.getRemark()!=null){
                criteria.add(Restrictions.eq("remark", phoneRefundAmountPo.getRemark()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
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
    public Result findByStatusAndCreateTIme(Result result,Date beforTime,int status){
        String hql = "from PhoneRefundAmountPo where status =? and createTime<=?";
        List<PhoneRefundAmountPo> lstRefundInfo = this.find(hql, status,beforTime);
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
        String hql = "from PhoneRefundAmountPo where status =? and refundTime<=?";
        List<PhoneRefundAmountPo> lstRefundInfo = this.find(hql, status,beforTime);
        result.setData (lstRefundInfo);
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
        String hql = "update PhoneRefundAmountPo set status = ? where refundAmountCode = ? ";
        int ret = this.updateHql (hql, refundStatus, refundCode);
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
        String hql = "update PhoneRefundAmountPo set refundTime = ? where refundAmountCode = ? ";
        int ret = this.updateHql (hql, date, refundCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
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
        String hql = "from PhoneRefundAmountPo where phoneOrderCode = ? ";
        PhoneRefundAmountPo chargeInfo = this.findUnique (hql, orderCode);
        result.setData (chargeInfo);
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
    public Result queryByReturnOrderCode(Result result, String refundAmountCode){
        String hql = "from PhoneRefundAmountPo where refundAmountCode = ? ";
        PhoneRefundAmountPo chargeInfo = this.findUnique (hql, refundAmountCode);
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
    public Result updateRefundStatusAndTime(Result result, String refundCode, Integer refundStatus,Date time) {
        String hql = "update PhoneRefundAmountPo set status = ?,refundTime=? where refundAmountCode = ? ";
        int ret = this.updateHql (hql, refundStatus,time, refundCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }


}
