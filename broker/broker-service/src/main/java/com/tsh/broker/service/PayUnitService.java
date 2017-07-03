package com.tsh.broker.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tsh.broker.convertor.PayUnitConvertor;
import com.tsh.broker.dao.AreaMappingInfoDao;
import com.tsh.broker.dao.SwPayUnitDao;
import com.tsh.broker.enumType.BizzTypeEnum;
import com.tsh.broker.po.AreaMappingInfo;
import com.tsh.broker.po.SwPayUnit;
import com.tsh.broker.vo.sdm.PayUnit;

/**
 * PayUnitService
 *
 * @author dengjd
 * @date 2016/9/30
 */
@Service
public class PayUnitService {
	private static final Logger log = Logger.getLogger(PayUnitService.class);
	
    @Resource(name = "swPayUnitDao")
    private SwPayUnitDao swPayUnitDao;
    @Resource(name = "areaMappingInfoDao")
    private AreaMappingInfoDao areaMappingInfoDao;

    public List<PayUnit> findEnableSwPayUnitList(String province, String city, Integer rechargeType){
        List<SwPayUnit> swPayUnitList = null;
        log.info("findEnableSwPayUnitList：" + BizzTypeEnum.OF_PAY.getBizzType());
        AreaMappingInfo areaMappingInfo = areaMappingInfoDao.findAreaMappingInfoByOrigin(province, city, "", BizzTypeEnum.SALER_WISE.getBizzType());

        if(areaMappingInfo != null) {
            swPayUnitList = swPayUnitDao.findEnablePayUnitList(
                    areaMappingInfo.getTargetProvince(),
                     areaMappingInfo.getTargetCity(),
                    rechargeType);
        }else {
            swPayUnitList = swPayUnitDao.findEnablePayUnitList(province, city, rechargeType);
        }

        return PayUnitConvertor.convertPayUnitList(swPayUnitList);
    }

    public AreaMappingInfo findAreaMappingInfoByOrigin(String province, String city){
    	log.info("findAreaMappingInfoByOrigin：" + BizzTypeEnum.OF_PAY.getBizzType());
    	
        return areaMappingInfoDao.findAreaMappingInfoByOrigin(province, city, "", BizzTypeEnum.OF_PAY.getBizzType());
    }

    public AreaMappingInfo findAreaMappingInfoByTarget(String province, String city){
    	log.info("findAreaMappingInfoByTarget：" + BizzTypeEnum.OF_PAY.getBizzType());
    	
        return areaMappingInfoDao.findAreaMappingInfoByTarget(province, city, "", BizzTypeEnum.OF_PAY.getBizzType());
    }
    
    public SwPayUnit getSwPayUnitByPayUnitId(String payUnitId)throws Exception{
    	return swPayUnitDao.getSwPayUnitByPayUnitId(payUnitId);
    }

}


