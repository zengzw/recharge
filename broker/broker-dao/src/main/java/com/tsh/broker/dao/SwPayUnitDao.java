package com.tsh.broker.dao;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.tsh.broker.po.SwPayUnit;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SwPayUnitDao
 *
 * @author dengjd
 * @date 2016/9/30
 */
@Repository
public class SwPayUnitDao extends HibernateDao<SwPayUnit, Long> {
	
	private static final Logger log = Logger.getLogger(SwPayUnitDao.class);
	
    /**
     * 查询可用的支付单位信息
     * @param province
     * @param city
     * @param rechargeType
     * @return
     * @throws Exception
     */
    public List<SwPayUnit> findEnablePayUnitList( String  province,String  city,Integer  rechargeType) {
        String hql = "from SwPayUnit where province=? and city=? and rechargeType=? and unitStatus= 1";
        List<SwPayUnit> swPayUnitList = this.find(hql, province,city,rechargeType);

        return swPayUnitList;
    }
    
    public SwPayUnit getSwPayUnitByPayUnitId(String payUnitId)throws Exception{
    	String hql = "from SwPayUnit where payUnitId = ?";
    	List<SwPayUnit> swPayUnits = this.find(hql, payUnitId);
    	
    	log.info("获取易赛缴费单位配置信息表 ：" + swPayUnits);
    	if(swPayUnits.size() > 0){
    		return swPayUnits.get(0);
    	}else{
    		log.error("获取易赛缴费单位配置信息表 ：" + swPayUnits);
    		return null;
    	}
    }
}
