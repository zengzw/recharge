package com.tsh.broker.convertor;

import com.tsh.broker.po.SwPayUnit;
import com.tsh.broker.vo.sdm.PayUnit;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * PayUnitConvertor
 *
 * @author dengjd
 * @date 2016/9/30
 */
public class PayUnitConvertor {

    public static List<PayUnit> convertPayUnitList(List<SwPayUnit> swPayUnitList){
        List<PayUnit> payUnitList = new ArrayList<PayUnit>();
        if(CollectionUtils.isEmpty(swPayUnitList))
            return payUnitList;

        for(SwPayUnit swPayUnit :  swPayUnitList){
            PayUnit payUnit = new PayUnit();
            payUnit.setUnitId(swPayUnit.getPayUnitId());
            payUnit.setUnitName(swPayUnit.getPayUnitName());
            payUnit.setProvince(swPayUnit.getProvince());
            payUnit.setCity(swPayUnit.getCity());
            payUnit.setPayModel(swPayUnit.getStatementType());
            payUnit.setPayType(swPayUnit.getRechargeType());
            payUnit.setUnitStatus(swPayUnit.getUnitStatus());
            //供应商产品id设置为默认的"0"
            payUnit.setProductId("0");
            payUnitList.add(payUnit);
        }

        return payUnitList;
    }
}
