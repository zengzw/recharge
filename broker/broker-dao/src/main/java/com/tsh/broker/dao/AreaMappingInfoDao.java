package com.tsh.broker.dao;

import java.util.List;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.tsh.broker.po.AreaMappingInfo;

import org.springframework.stereotype.Repository;

/**
 * AreaMappingInfoDao
 *
 * @author dengjd
 * @date 2016/10/12
 */
@Repository
public class AreaMappingInfoDao extends HibernateDao<AreaMappingInfo, Long> {

    public AreaMappingInfo findAreaMappingInfoByOrigin(String originProvince,String originCity,String originCounty,Integer bizzType){
        String hql = "from AreaMappingInfo where originProvince=? and originCity=? and originCounty=? and bizzType= ?";
        List<AreaMappingInfo> areaMappingInfos = this.find(hql, originProvince,originCity,originCounty,bizzType);
        if(areaMappingInfos.size() > 0){
        	return areaMappingInfos.get(0);
        }else {
			return null;
		}
    }

    public AreaMappingInfo findAreaMappingInfoByTarget(String originProvince,String originCity,String originCounty,Integer bizzType){
        String hql = "from AreaMappingInfo where targetProvince=? and targetCity=? and targetCounty=? and bizzType= ?";
        List<AreaMappingInfo> areaMappingInfos = this.find(hql, originProvince,originCity,originCounty,bizzType);
        if(areaMappingInfos.size() > 0){
        	return areaMappingInfos.get(0);
        }else {
			return null;
		}
    }
}
