/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.vo;

import java.io.Serializable;
import java.util.List;

import com.tsh.phone.vo.PhoneLocationVo;
import com.tsh.vas.vo.phone.api.APIPhoneValue;

/**
 *
 * @author zengzw
 * @date 2017年5月17日
 */
public class APIPhoneSegmentVo implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 4569494396149383030L;

    
    /**
     * 归属地
     */
    private PhoneLocationVo locationVo;
    
    /**
     * 面值
     */
    private List<APIPhoneValue> lstPhoneValue;

    public PhoneLocationVo getLocationVo() {
        return locationVo;
    }

    public List<APIPhoneValue> getLstPhoneValue() {
        return lstPhoneValue;
    }

    public void setLocationVo(PhoneLocationVo locationVo) {
        this.locationVo = locationVo;
    }

    public void setLstPhoneValue(List<APIPhoneValue> lstPhoneValue) {
        this.lstPhoneValue = lstPhoneValue;
    }

}
