package com.tsh.vas.dao.phone;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneProcessingRecordLogPo;

@Repository
@SuppressWarnings("all")
public class PhoneProcessingRecordLogDao extends HibernateDao<PhoneProcessingRecordLogPo, Long> {
    /**
     * 新增
     * @param result
     * @param phoneProcessingRecordLog
     * @return
     */
    public Result addPhoneProcessingRecordLog(Result result,PhoneProcessingRecordLogPo phoneProcessingRecordLog)throws Exception{
        this.save(phoneProcessingRecordLog);
        return result;
    }

    /**
     * 批量新增
     * @param result
     * @param phoneProcessingRecordLog
     * @return
     */
    public Result batchSavePhoneProcessingRecordLog(Result result, List<PhoneProcessingRecordLogPo> phoneProcessingRecordLog_list) throws Exception {
        this.batchSave(phoneProcessingRecordLog_list);
        result.setData(null);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneProcessingRecordLog(Result result, Integer id) throws Exception {
        int count = this.updateHql("delete PhoneProcessingRecordLogPo where id=?",id);
        result.setData(count);
        return result;
    }


    /**
     * 批量删除
     * @param result
     * @param phoneProcessingRecordLog
     * @return
     */
    public Result batchDelPhoneProcessingRecordLog(Result result, List<PhoneProcessingRecordLogPo> phoneProcessingRecordLog_list)throws Exception{
        this.batchDelete(phoneProcessingRecordLog_list);
        return result;
    }


    /**
     * 批量删除ById
     * @param result
     * @param phoneProcessingRecordLog
     * @return
     */
    public Result batchDelPhoneProcessingRecordLogByIds(Result result,Integer[] ids)throws Exception{
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
    public Result clearPhoneProcessingRecordLog(Result result) {
        String sql = " truncate table phone_processing_record_log ";
        int count = this.getSession().createSQLQuery(sql).executeUpdate();
        result.setData(count);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhoneProcessingRecordLog(Result result,PhoneProcessingRecordLogPo phoneProcessingRecordLogPo) throws Exception {
        StringBuffer hql = new StringBuffer();
        hql.append("update PhoneProcessingRecordLogPo set ");

        if(phoneProcessingRecordLogPo.getMsgid()!=null){
            hql.append("msgid = ").append(phoneProcessingRecordLogPo.getMsgid());
        }
        if(phoneProcessingRecordLogPo.getSuppliertype()!=null){
            hql.append("suppliertype = ").append(phoneProcessingRecordLogPo.getSuppliertype());
        }
        if(phoneProcessingRecordLogPo.getHttpType()!=null){
            hql.append("httpType = ").append(phoneProcessingRecordLogPo.getHttpType());
        }
        if(phoneProcessingRecordLogPo.getMethodname()!=null){
            hql.append("methodname = ").append(phoneProcessingRecordLogPo.getMethodname());
        }
        if(phoneProcessingRecordLogPo.getLogtype()!=null){
            hql.append("logtype = ").append(phoneProcessingRecordLogPo.getLogtype());
        }
        if(phoneProcessingRecordLogPo.getLogcontent()!=null){
            hql.append("logcontent = ").append(phoneProcessingRecordLogPo.getLogcontent());
        }
        if(phoneProcessingRecordLogPo.getCreatetime()!=null){
            hql.append("createtime = ").append(phoneProcessingRecordLogPo.getCreatetime());
        }
        if(phoneProcessingRecordLogPo.getCost()!=null){
            hql.append("cost = ").append(phoneProcessingRecordLogPo.getCost());
        }

        hql.append("where id = ?");
        int count = this.updateHql(hql.toString(),phoneProcessingRecordLogPo.getId());
        result.setData(count);
        return result;
    }


    /**
     * 批量更新 
     * @param result
     * @return
     */
    public Result batchUpdatePhoneProcessingRecordLog(Result result,List<PhoneProcessingRecordLogPo> phoneProcessingRecordLog_list) throws Exception {
        this.batchUpdate(phoneProcessingRecordLog_list);
        result.setData(null);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneProcessingRecordLogById(Result result,Long id) throws Exception{
        PhoneProcessingRecordLogPo phoneProcessingRecordLogPo = this.get(id);
        result.setData(phoneProcessingRecordLogPo);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneProcessingRecordLogList(Result result,Page page,PhoneProcessingRecordLogPo phoneProcessingRecordLogPo){
        Criteria criteria = this.getSession().createCriteria(PhoneProcessingRecordLogPo.class);
        if(null != phoneProcessingRecordLogPo){
            if(phoneProcessingRecordLogPo.getMsgid()!=null){
                criteria.add(Restrictions.eq("msgid", phoneProcessingRecordLogPo.getMsgid()));
            }
            if(phoneProcessingRecordLogPo.getSuppliertype()!=null){
                criteria.add(Restrictions.eq("suppliertype", phoneProcessingRecordLogPo.getSuppliertype()));
            }
            if(phoneProcessingRecordLogPo.getHttpType()!=null){
                criteria.add(Restrictions.eq("httpType", phoneProcessingRecordLogPo.getHttpType()));
            }
            if(phoneProcessingRecordLogPo.getMethodname()!=null){
                criteria.add(Restrictions.eq("methodname", phoneProcessingRecordLogPo.getMethodname()));
            }
            if(phoneProcessingRecordLogPo.getLogtype()!=null){
                criteria.add(Restrictions.eq("logtype", phoneProcessingRecordLogPo.getLogtype()));
            }
            if(phoneProcessingRecordLogPo.getLogcontent()!=null){
                criteria.add(Restrictions.eq("logcontent", phoneProcessingRecordLogPo.getLogcontent()));
            }
            if(phoneProcessingRecordLogPo.getCreatetime()!=null){
                criteria.add(Restrictions.eq("createtime", phoneProcessingRecordLogPo.getCreatetime()));
            }
            if(phoneProcessingRecordLogPo.getCost()!=null){
                criteria.add(Restrictions.eq("cost", phoneProcessingRecordLogPo.getCost()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }

}
