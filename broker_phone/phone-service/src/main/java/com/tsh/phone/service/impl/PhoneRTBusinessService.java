package com.tsh.phone.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.phone.dao.PhoneRTBusinessDao;
import com.tsh.phone.po.PhoneRTBusinessPo;
import com.tsh.phone.vo.PhoneRTBusinessVo;


/**
 * 瑞通 业务编码查询
 *
 * @author zengzw
 * @date 2017年5月3日
 */
@Service
@SuppressWarnings("all")
public class PhoneRTBusinessService {
    
    @Autowired
    private PhoneRTBusinessDao phoneRTBusinessDao;
    
    
    /**
     * 根据省份，服务商类型 查询businessCode
     * 
     * @param province
     * @param supplierType
     * @return
     */
    public PhoneRTBusinessPo queryBusinessCode(String province,String supplierType){
        List<PhoneRTBusinessPo>  listResult = phoneRTBusinessDao.queryBusinessCode(province, supplierType);
        if(CollectionUtils.isNotEmpty(listResult)){
            return listResult.get(0);
        }
        
        return null;
    }


}
