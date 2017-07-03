package com.tsh.vas.dao;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ModuleAuthorityPo;
import com.tsh.vas.vo.QueryModuleAuthorityVo;

@Repository
@SuppressWarnings("all")
public class ModuleAuthorityDao extends HibernateDao<ModuleAuthorityPo, Integer> {
    /**
     * 新增
     * @param result
     * @param moduleAuthority
     * @return
     */
    public Result addModuleAuthority(Result result,ModuleAuthorityPo moduleAuthority){
        this.save(moduleAuthority);
        return result;
    }




    /**
     * 根据
     * @param result
     * @param moduleAuthorityPo
     * @return
     */
    public Result queryByCombindTerm(Result result, ModuleAuthorityPo moduleAuthorityPo) {
        String hql = "from ModuleAuthorityPo where projectCode = ? and businessCode=? and areaId=?";
        ModuleAuthorityPo moduleAuthority = this.findUnique (hql, moduleAuthorityPo.getProjectCode(),moduleAuthorityPo.getBusinessCode(),moduleAuthorityPo.getAreaId());
        result.setData (moduleAuthority);
        return result;
    }


    /**
     * 根据县域Id,项目名称，业务编码 查询是否有参加活动
     * 
     * 
     * @param result
     * @param areaId
     * @return
     */
    public Result queryByCombindTermAndNotEnding(Result result,String projectCode,String businessCode,Integer areaId) {
        String hql = "from ModuleAuthorityPo where projectCode = ? and businessCode=? and areaId=?"
                + " and beginTime <= now() and endTime > now()";
        ModuleAuthorityPo moduleAuthority = this.findUnique (hql, projectCode,businessCode,areaId);
        result.setData (moduleAuthority);
        return result;
    }


    /**
     * 
     * 分页查询活动地区设置
     * 
     * @param result
     * @param page
     * @param 
     * @return
     */
    public Result queryPageActivityArea(Result result,Page page,QueryModuleAuthorityVo params){
        Criteria criteria = this.getSession().createCriteria(ModuleAuthorityPo.class);
        if(null != params){
            //项目编码
            criteria.add(Restrictions.eq("projectCode", params.getProjectCode()));

            //模块编码
            criteria.add(Restrictions.eq("businessCode", params.getBusinessCode()));

            //标题模糊搜索
            if(params.getAreaId() != null){
                criteria.add(Restrictions.eq("areaId",params.getAreaId()));
            }

            if(params.getSelectStatus() != null){
                Date currentTime = new Date();
                //查询位开始的活动   
                //假释：b:开始时间(begin)，e:结束时间(end)，n:当前时间(now)
                if(params.getSelectStatus() == 0){
                    //b > n
                    criteria.add(Restrictions.gt("beginTime", currentTime));
                }

                //查询进行中的
                if(params.getSelectStatus() == 1){
                    //b <= n & e >= n
                    criteria.add(Restrictions.le("beginTime", currentTime));
                    criteria.add(Restrictions.gt("endTime", currentTime));
                }

                //查询已结束
                if(params.getSelectStatus() == 2){
                    // e <= n
                    criteria.add(Restrictions.le("endTime", currentTime));
                }
            }

            criteria.addOrder(Order.desc("endTime"));
        }

        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }


}
