package com.tsh.vas.dao.phone;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneValuePo;

@Repository
@SuppressWarnings("all")
public class PhoneValueDao extends HibernateDao<PhoneValuePo, Long> {
    /**
     * 新增
     * @param result
     * @param phoneValue
     * @return
     */
    public Result addPhoneValue(Result result,PhoneValuePo phoneValue)throws Exception{
        this.save(phoneValue);
        result.setData(phoneValue);
        return result;
    }

    /**
     * 批量新增
     * @param result
     * @param phoneValue
     * @return
     */
    public Result batchSavePhoneValue(Result result, List<PhoneValuePo> phoneValue_list) throws Exception {
        this.batchSave(phoneValue_list);
        result.setData(null);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneValue(Result result, Integer id) throws Exception {
        int count = this.updateHql("delete PhoneValuePo where id=?",id);
        result.setData(count);
        return result;
    }

    
    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneValueById(Result result, Long id) throws Exception {
        int count = this.updateHql("delete PhoneValuePo where id=?",id);
        result.setData(count);
        return result;
    }

    /**
     * 批量删除
     * @param result
     * @param phoneValue
     * @return
     */
    public Result batchDelPhoneValue(Result result, List<PhoneValuePo> phoneValue_list)throws Exception{
        this.batchDelete(phoneValue_list);
        return result;
    }


    /**
     * 批量删除ById
     * @param result
     * @param phoneValue
     * @return
     */
    public Result batchDelPhoneValueByIds(Result result,Integer[] ids)throws Exception{
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
    public Result getPhoneValueById(Result result,Long id) throws Exception{
        PhoneValuePo phoneValuePo = this.get(id);
        result.setData(phoneValuePo);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneValueList(Result result,Page page,PhoneValuePo phoneValuePo){
        Criteria criteria = this.getSession().createCriteria(PhoneValuePo.class);
        if(null != phoneValuePo){
            if(phoneValuePo.getValue()!=null){
                criteria.add(Restrictions.eq("value", phoneValuePo.getValue()));
            }
            if(phoneValuePo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", phoneValuePo.getCreateTime()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }
    
    
    
   
    /**
     * 获取所有面值 
     * 
     * 
     * @return
     */
    public Result getListPhoneValue(Result result){
        String hql = "from PhoneValuePo order by value asc";
        List<PhoneValuePo> lstResult = this.find(hql);
        result.setData(lstResult);
        return result;
    }
    
    /**
     * 匹配对应面值的记录数
     * @param result
     * @param value
     * @return
     */
    public Result getPhoneValueByPhoneValue(Result result, Integer value) {
    	String sql = "select count(*) from phone_value t where t.value=?";
    	result.setData(this.queryForInt(sql, value));
    	return result;
    }
}
