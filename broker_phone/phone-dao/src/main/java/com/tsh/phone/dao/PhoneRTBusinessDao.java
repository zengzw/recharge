package com.tsh.phone.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.phone.po.PhoneRTBusinessPo;
import com.tsh.phone.po.PhoneRechargeProduct;

@Repository
@SuppressWarnings("all")
public class PhoneRTBusinessDao extends HibernateDao<PhoneRTBusinessPo, Integer> {
    
  
    /**
     *  根据省份，服务商类型 查询businessCode
     *  
     *  
     * @param province   省份
     * @param supplierType  供应商类型
     * @return
     */
    public List<PhoneRTBusinessPo> queryBusinessCode(String province,String supplierType){
        String hql = "from PhoneRTBusinessPo where (province = ? and supplierType=?) or (province='全国') order by sort desc";
        return this.find(hql,province,supplierType);
    }
    
}
