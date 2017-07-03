package com.tsh.vas.dao.phone;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhonePaymentRecordPo;

@Repository
@SuppressWarnings("all")
public class PhonePaymentRecordDao extends HibernateDao<PhonePaymentRecordPo, Long> {
    /**
     * 新增
     * @param result
     * @param phonePaymentRecord
     * @return
     */
    public Result addPhonePaymentRecord(Result result,PhonePaymentRecordPo phonePaymentRecord)throws Exception{
        this.save(phonePaymentRecord);
        return result;
    }

    /**
     * 批量新增
     * @param result
     * @param phonePaymentRecord
     * @return
     */
    public Result batchSavePhonePaymentRecord(Result result, List<PhonePaymentRecordPo> phonePaymentRecord_list) throws Exception {
        this.batchSave(phonePaymentRecord_list);
        result.setData(null);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhonePaymentRecord(Result result, Integer id) throws Exception {
        int count = this.updateHql("delete PhonePaymentRecordPo where id=?",id);
        result.setData(count);
        return result;
    }


    /**
     * 批量删除
     * @param result
     * @param phonePaymentRecord
     * @return
     */
    public Result batchDelPhonePaymentRecord(Result result, List<PhonePaymentRecordPo> phonePaymentRecord_list)throws Exception{
        this.batchDelete(phonePaymentRecord_list);
        return result;
    }


    /**
     * 批量删除ById
     * @param result
     * @param phonePaymentRecord
     * @return
     */
    public Result batchDelPhonePaymentRecordByIds(Result result,Integer[] ids)throws Exception{
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
    public Result clearPhonePaymentRecord(Result result) {
        String sql = " truncate table phone_payment_record ";
        int count = this.getSession().createSQLQuery(sql).executeUpdate();
        result.setData(count);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhonePaymentRecord(Result result,PhonePaymentRecordPo phonePaymentRecordPo) throws Exception {
        StringBuffer hql = new StringBuffer();
        hql.append("update PhonePaymentRecordPo set ");

        if(phonePaymentRecordPo.getPayType()!=null){
            hql.append("payType = ").append(phonePaymentRecordPo.getPayType());
        }
        if(phonePaymentRecordPo.getPhoneOrderCode()!=null){
            hql.append("phoneOrderCode = ").append(phonePaymentRecordPo.getPhoneOrderCode());
        }
        if(phonePaymentRecordPo.getPayAmount()!=null){
            hql.append("payAmount = ").append(phonePaymentRecordPo.getPayAmount());
        }
        if(phonePaymentRecordPo.getPayWay()!=null){
            hql.append("payWay = ").append(phonePaymentRecordPo.getPayWay());
        }
        if(phonePaymentRecordPo.getCreateTime()!=null){
            hql.append("createTime = ").append(phonePaymentRecordPo.getCreateTime());
        }
        if(phonePaymentRecordPo.getRemark()!=null){
            hql.append("remark = ").append(phonePaymentRecordPo.getRemark());
        }

        hql.append("where id = ?");
        int count = this.updateHql(hql.toString(),phonePaymentRecordPo.getId());
        result.setData(count);
        return result;
    }


    /**
     * 批量更新 
     * @param result
     * @return
     */
    public Result batchUpdatePhonePaymentRecord(Result result,List<PhonePaymentRecordPo> phonePaymentRecord_list) throws Exception {
        this.batchUpdate(phonePaymentRecord_list);
        result.setData(null);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhonePaymentRecordById(Result result,Long id) throws Exception{
        PhonePaymentRecordPo phonePaymentRecordPo = this.get(id);
        result.setData(phonePaymentRecordPo);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhonePaymentRecordList(Result result,Page page,PhonePaymentRecordPo phonePaymentRecordPo){
        Criteria criteria = this.getSession().createCriteria(PhonePaymentRecordPo.class);
        if(null != phonePaymentRecordPo){
            if(phonePaymentRecordPo.getPayType()!=null){
                criteria.add(Restrictions.eq("payType", phonePaymentRecordPo.getPayType()));
            }
            if(phonePaymentRecordPo.getPhoneOrderCode()!=null){
                criteria.add(Restrictions.eq("phoneOrderCode", phonePaymentRecordPo.getPhoneOrderCode()));
            }
            if(phonePaymentRecordPo.getPayAmount()!=null){
                criteria.add(Restrictions.eq("payAmount", phonePaymentRecordPo.getPayAmount()));
            }
            if(phonePaymentRecordPo.getPayWay()!=null){
                criteria.add(Restrictions.eq("payWay", phonePaymentRecordPo.getPayWay()));
            }
            if(phonePaymentRecordPo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", phonePaymentRecordPo.getCreateTime()));
            }
            if(phonePaymentRecordPo.getRemark()!=null){
                criteria.add(Restrictions.eq("remark", phonePaymentRecordPo.getRemark()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }

}
