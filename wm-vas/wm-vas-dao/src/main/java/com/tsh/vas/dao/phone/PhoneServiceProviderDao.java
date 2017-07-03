package com.tsh.vas.dao.phone;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneServiceProviderPo;

@Repository
@SuppressWarnings("all")
public class PhoneServiceProviderDao extends HibernateDao<PhoneServiceProviderPo, Long> {
   
	/**
     * 新增
     * @param result
     * @param phoneServiceProvider
     * @return
     */
    public Result addPhoneServiceProvider(Result result,PhoneServiceProviderPo phoneServiceProvider)throws Exception{
        this.save(phoneServiceProvider);
        return result;
    }

    /**
     * 批量新增
     * @param result
     * @param phoneServiceProvider
     * @return
     */
    public Result batchSavePhoneServiceProvider(Result result, List<PhoneServiceProviderPo> phoneServiceProvider_list) throws Exception {
        this.batchSave(phoneServiceProvider_list);
        result.setData(null);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneServiceProvider(Result result, Integer id) throws Exception {
        int count = this.updateHql("delete PhoneServiceProviderPo where id=?",id);
        result.setData(count);
        return result;
    }


    /**
     * 批量删除
     * @param result
     * @param phoneServiceProvider
     * @return
     */
    public Result batchDelPhoneServiceProvider(Result result, List<PhoneServiceProviderPo> phoneServiceProvider_list)throws Exception{
        this.batchDelete(phoneServiceProvider_list);
        return result;
    }


    /**
     * 批量删除ById
     * @param result
     * @param phoneServiceProvider
     * @return
     */
    public Result batchDelPhoneServiceProviderByIds(Result result,Integer[] ids)throws Exception{
        int count = 0;
        for(Integer id : ids){
            this.delete(id);
            count ++;
        }
        result.setData(count);
        return result;
    }



    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneServiceProviderById(Result result,Long id) throws Exception{
        PhoneServiceProviderPo phoneServiceProviderPo = this.get(id);
        result.setData(phoneServiceProviderPo);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneServiceProviderList(Result result,Page page,PhoneServiceProviderPo phoneServiceProviderPo){
        Criteria criteria = this.getSession().createCriteria(PhoneServiceProviderPo.class);
        if(null != phoneServiceProviderPo){
            if(phoneServiceProviderPo.getProviderCode()!=null){
                criteria.add(Restrictions.eq("providerCode", phoneServiceProviderPo.getProviderCode()));
            }
            if(phoneServiceProviderPo.getProviderName()!=null){
                criteria.add(Restrictions.eq("providerName", phoneServiceProviderPo.getProviderName()));
            }
            if(phoneServiceProviderPo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", phoneServiceProviderPo.getCreateTime()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }
    
    /**
     * 批量查询
     * @param result
     * @param ids
     * @return
     */
    public Result batchQueryPhoneServiceProviderByIds(Result result, String ids) {
    	String hql = " from PhoneServiceProviderPo where id in("+ids+")";
    	result.setData(this.find(hql));
    	return result;
    }

}
