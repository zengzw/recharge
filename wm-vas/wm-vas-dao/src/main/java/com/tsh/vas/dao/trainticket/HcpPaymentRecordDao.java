package com.tsh.vas.dao.trainticket;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.model.trainticket.HcpPaymentRecordPo;

/**
 * 支付记录Dao
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Repository
@SuppressWarnings("all")
public class HcpPaymentRecordDao extends HibernateDao<HcpPaymentRecordPo, Integer> {
    /**
     * 新增支付记录接口对象
     * @param result
     * @param hcpPaymentRecord
     * @return
     */
    public Result addHcpPaymentRecord(Result result,HcpPaymentRecordPo hcpPaymentRecord)throws Exception{
        this.save(hcpPaymentRecord);
        result.setData(hcpPaymentRecord);
        return result;
    }

    /**
     * 批量新增支付记录接口对象
     * @param result
     * @param hcpPaymentRecord
     * @return
     */
    public Result batchSaveHcpPaymentRecord(Result result, List<HcpPaymentRecordPo> hcpPaymentRecord_list) throws Exception {
        this.batchSave(hcpPaymentRecord_list);
        result.setData(null);
        return result;
    }

    /**
     * 删除支付记录接口对象
     * @param id 支付记录接口对象标识
     * @return
     */
    public Result deleteHcpPaymentRecord(Result result, Integer id) throws Exception {
        int count = this.updateHql("delete HcpPaymentRecordPo where id=?",id);
        result.setData(count);
        return result;
    }


    /**
     * 批量删除支付记录接口对象
     * @param result
     * @param hcpPaymentRecord
     * @return
     */
    public Result batchDelHcpPaymentRecord(Result result, List<HcpPaymentRecordPo> hcpPaymentRecord_list)throws Exception{
        this.batchDelete(hcpPaymentRecord_list);
        return result;
    }


    /**
     * 批量删除支付记录接口对象ById
     * @param result
     * @param hcpPaymentRecord
     * @return
     */
    public Result batchDelHcpPaymentRecordByIds(Result result,Integer[] ids)throws Exception{
        int count = 0;
        for(Integer id : ids){
            this.delete(id);
            count ++;
        }
        result.setData(count);
        return result;
    }



    /**
     * 清空表 支付记录接口对象
     * @param result
     * @return
     */
    public Result clearHcpPaymentRecord(Result result) {
        String sql = " truncate table hcp_payment_record ";
        int count = this.getSession().createSQLQuery(sql).executeUpdate();
        result.setData(count);
        return result;
    }


    /**
     * 更新 支付记录接口对象
     * @param result
     * @return
     */
    public Result updateHcpPaymentRecord(Result result,HcpPaymentRecordPo hcpPaymentRecordPo) throws Exception {
        StringBuffer hql = new StringBuffer();
        hql.append("update HcpPaymentRecordPo set ");

        if(hcpPaymentRecordPo.getPayType()!=null){
            hql.append("payType = ").append(hcpPaymentRecordPo.getPayType());
        }
        if(hcpPaymentRecordPo.getHcpOrderCode()!=null){
            hql.append("hcpOrderCode = ").append(hcpPaymentRecordPo.getHcpOrderCode());
        }
        if(hcpPaymentRecordPo.getPayAmount()!=null){
            hql.append("payAmount = ").append(hcpPaymentRecordPo.getPayAmount());
        }
        if(hcpPaymentRecordPo.getPayRecord()!=null){
            hql.append("payRecord = ").append(hcpPaymentRecordPo.getPayRecord());
        }
        if(hcpPaymentRecordPo.getPayWay()!=null){
            hql.append("payWay = ").append(hcpPaymentRecordPo.getPayWay());
        }
        if(hcpPaymentRecordPo.getCreateTime()!=null){
            hql.append("createTime = ").append(hcpPaymentRecordPo.getCreateTime());
        }
        if(hcpPaymentRecordPo.getRemark()!=null){
            hql.append("remark = ").append(hcpPaymentRecordPo.getRemark());
        }

        hql.append("where id = ?");
        int count = this.updateHql(hql.toString(),hcpPaymentRecordPo.getId());
        result.setData(count);
        return result;
    }


    /**
     * 批量更新 支付记录接口对象
     * @param result
     * @return
     */
    public Result batchUpdateHcpPaymentRecord(Result result,List<HcpPaymentRecordPo> hcpPaymentRecord_list) throws Exception {
        this.batchUpdate(hcpPaymentRecord_list);
        result.setData(null);
        return result;
    }


    /**
     * 根据ID获取 支付记录接口对象
     * @param result
     * @return
     */
    public Result getHcpPaymentRecordById(Result result,Integer id) throws Exception{
        HcpPaymentRecordPo hcpPaymentRecordPo = this.get(id);
        result.setData(hcpPaymentRecordPo);
        return result;
    }

    
    
    /**
     * 根据条件获取 支付记录接口对象列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryHcpPaymentRecordList(Result result,Page page,HcpPaymentRecordPo hcpPaymentRecordPo){
        Criteria criteria = this.getSession().createCriteria(HcpPaymentRecordPo.class);
        if(null != hcpPaymentRecordPo){
            if(hcpPaymentRecordPo.getPayType()!=null){
                criteria.add(Restrictions.eq("payType", hcpPaymentRecordPo.getPayType()));
            }
            if(hcpPaymentRecordPo.getHcpOrderCode()!=null){
                criteria.add(Restrictions.eq("hcpOrderCode", hcpPaymentRecordPo.getHcpOrderCode()));
            }
            if(hcpPaymentRecordPo.getPayAmount()!=null){
                criteria.add(Restrictions.eq("payAmount", hcpPaymentRecordPo.getPayAmount()));
            }
            if(hcpPaymentRecordPo.getPayRecord()!=null){
                criteria.add(Restrictions.eq("payRecord", hcpPaymentRecordPo.getPayRecord()));
            }
            if(hcpPaymentRecordPo.getPayWay()!=null){
                criteria.add(Restrictions.eq("payWay", hcpPaymentRecordPo.getPayWay()));
            }
            if(hcpPaymentRecordPo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", hcpPaymentRecordPo.getCreateTime()));
            }
            if(hcpPaymentRecordPo.getRemark()!=null){
                criteria.add(Restrictions.eq("remark", hcpPaymentRecordPo.getRemark()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }

}
