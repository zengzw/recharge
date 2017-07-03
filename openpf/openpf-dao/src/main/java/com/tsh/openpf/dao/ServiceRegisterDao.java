package com.tsh.openpf.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.tsh.openpf.po.ServiceRegister;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * ServiceRegisterDao
 *
 * @author dengjd
 * @date 2016/10/11
 */
@Repository
public class ServiceRegisterDao extends HibernateDao<ServiceRegister, Long> {


    public boolean isRegisterServiceExists(String bizzNo) {
        String hql = "from ServiceRegister where businessId = ?";
        ServiceRegister serviceRegister  = this.findUnique(hql,bizzNo);

        return serviceRegister != null;
    }

    public ServiceRegister findRegisterServiceByBizzNo(String bizzNo) {
        String hql = "from ServiceRegister where businessId = ?";
        ServiceRegister serviceRegister  = this.findUnique(hql,bizzNo);

        return serviceRegister;
    }
    
    
    
    
    /**
     * 根据业务Id  跟业务编码 查询供应商信息
     * 
     * 场景：一个供应商提供多种业务。如：话费、电费、流量等
     * 
     * 
     * @param bizzNo  供应商编号
     * @param bizCode 业务编码
     * @return
     */
    public List<ServiceRegister> findListRegisterServiceByBizzNo(String bizzNo,String bizzCode) {
        String sql = "select * from service_register where business_code='"+bizzCode+"'";
        if(StringUtils.isNotBlank(bizzNo)){
            sql += " and business_id ='"+bizzNo+"'";
        }
        
        Session session = this.getSession();
        SQLQuery sqlQuery = (SQLQuery) session.createSQLQuery(sql).addEntity(ServiceRegister.class);
        List<ServiceRegister> lstDeposit = sqlQuery.list();
        
        return lstDeposit;
    }
 
    
    
    /**
     * 根据业务Id  跟业务编码 查询供应商信息
     * 
     * 场景：一个供应商提供多种业务。如：话费、电费、流量等
     * 
     * 
     * @param bizzNo  供应商编号
     * @param bizCode 业务编码
     * @return
     */
    public ServiceRegister findRegisterServiceByBizzNo(String bizzNo,String bizzCode) {
        String hql = "from ServiceRegister where businessId = ? and businessCode = ?";
        ServiceRegister serviceRegister  = this.findUnique(hql,bizzNo,bizzCode);
        
        return serviceRegister;
    }
    
    
}
