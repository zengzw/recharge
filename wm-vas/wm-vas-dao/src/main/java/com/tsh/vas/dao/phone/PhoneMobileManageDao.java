package com.tsh.vas.dao.phone;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneMobileManagePo;
import com.tsh.vas.vo.phone.PhoneMobileManagerVo;

/**
 * Created by Administrator on 2017/5/2 002.
 */
@Repository
public class PhoneMobileManageDao extends HibernateDao<PhoneMobileManagePo, Long> {

    /**
     * 分页查询列表
     * @param result
     * @param page
     * @param managerVo
     * @return
     */
    public Result pageList(Result result, Page page, PhoneMobileManagerVo managerVo) {
        Criteria criteria = this.getSession().createCriteria(PhoneMobileManagePo.class);
        if(null != managerVo) {
            if (StringUtils.isNotBlank(managerVo.getMobileSupplier()) && !"-1".equals(managerVo.getMobileSupplier())) {
                criteria.add(Restrictions.eq("mobileSupplier", managerVo.getMobileSupplier()));
            }

            if(StringUtils.isNotBlank(managerVo.getMobileProvince())){

                if(managerVo.getMobileProvince().endsWith("省") ||
                        managerVo.getMobileProvince().endsWith("市") ||
                        managerVo.getMobileProvince().endsWith("自治区") ){

                    String newProvinceName = managerVo.getMobileProvince().replace("省", "").replace("市", "").replace("自治区", "");
                    criteria.add(Restrictions.like("mobileProvince", newProvinceName+"%"));
                } else {
                    criteria.add(Restrictions.like("mobileProvince", managerVo.getMobileProvince()+"%"));
                }
            }

            if(StringUtils.isNotBlank(managerVo.getMobileCity()) &&
                    !"直辖区".equals(managerVo.getMobileCity()) &&
                    !"直辖县".equals(managerVo.getMobileCity())){

                if(managerVo.getMobileCity().endsWith("市") ||
                        managerVo.getMobileCity().endsWith("区")||
                        managerVo.getMobileCity().endsWith("地区") ||
                        managerVo.getMobileCity().endsWith("县")){

                    String newCityName = managerVo.getMobileCity().replace("市", "").replace("县", "").replace("地区", "").replace("区", "");
                    criteria.add(Restrictions.like("mobileCity", newCityName+"%"));
                } else {
                    criteria.add(Restrictions.like("mobileCity", managerVo.getMobileCity()+"%"));
                }

            }

            if(StringUtils.isNotBlank(managerVo.getMobileNum())){
                criteria.add(Restrictions.like("mobileNum", managerVo.getMobileNum()+"%"));
            }

            if(StringUtils.isNotBlank(managerVo.getMobileShort())){
                criteria.add(Restrictions.eq("mobileShort", managerVo.getMobileShort()));
            }
        }

        criteria.addOrder(Order.desc("createtime"));
        criteria.addOrder(Order.asc("id"));
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);

        return result;
    }

    
    /**
     *
     * @param result
     * @param mobileNum
     * @return
     */
    public Result queryByMobilenum(Result result, String mobileNum) {
        String hql = "from PhoneMobileManagePo where mobileNum = ? ";
        PhoneMobileManagePo managerPo = this.findUnique (hql, mobileNum);
        result.setData (managerPo);
        return result;
    }
    
    
    /**
     * 新增
     * 
     * @param result
     * @param phoneGoods
     * @return  添加对象
     */
    public Result addMobileSegmentNo(Result result,PhoneMobileManagePo mobileManagePo)throws Exception{
        this.save(mobileManagePo);
        
        result.setData(mobileManagePo);
        return result;
    }

}
