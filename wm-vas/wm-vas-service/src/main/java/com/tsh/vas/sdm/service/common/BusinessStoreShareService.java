package com.tsh.vas.sdm.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Result;
import com.tsh.vas.dao.BusinessStoreShareDao;
import com.tsh.vas.model.BusinessStoreShare;

@Service
public class BusinessStoreShareService {
	@Autowired
	private BusinessStoreShareDao businessStoreShareDao;
	
	public Result queryByCountryCodeAndBusinessCode(Result result,BusinessStoreShare businessStoreShare) throws Exception {
		return businessStoreShareDao.queryByCountryCodeAndBusinessCode(result, businessStoreShare.getCountryCode(), businessStoreShare.getBusinessCode());
    }

    public Result updateOrAddBusinessStoreShare(Result result, BusinessStoreShare businessStoreShare) throws Exception {
    	
    	BusinessStoreShare bss = businessStoreShareDao.queryByCountryCodeAndBusinessCode(result, businessStoreShare.getCountryCode(), businessStoreShare.getBusinessCode()).getData();
    	if(null != bss){
    		bss.setCountryShareRatio(businessStoreShare.getCountryShareRatio());
    		bss.setStoreShareRatio(businessStoreShare.getStoreShareRatio());
    	}else{
    		businessStoreShareDao.addBusinessStoreShare (result, businessStoreShare);
    	}
    	return result; 
    }
    
    
    public Result queryByCountryCodeAndBusinessCode(Result result, String countryCode, String businessCode) throws Exception {
        return businessStoreShareDao.queryByCountryCodeAndBusinessCode(result, countryCode, businessCode);
    }
}

