/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.phone.manager;

import java.util.Comparator;

import com.tsh.dubbo.hlq.vo.ex.CouponVO;
import com.tsh.phone.util.DateUtils;

/**
 * 优惠券排序接口
 * 
 * @author zengzw
 * @date 2017年6月23日
 */
public class CouponComparetor implements Comparator<CouponVO>{



    @Override
    public int compare(CouponVO o1, CouponVO o2) {

        long endTime1 = DateUtils.parseDate(o1.getUseEndTime(), DateUtils.FORMAT_DAY).getTime();
        long endTime2 = DateUtils.parseDate(o2.getUseEndTime(), DateUtils.FORMAT_DAY).getTime();
        if(endTime1 > endTime2){
            return 1;
        } else if(endTime1 < endTime2){
            return -1;
        }
        
        double money1 = o1.getMoney().doubleValue();
        double money2 = o2.getMoney().doubleValue();
        if(money1 > money2){
            return -1;
        }else if(money1 < money2){
            return 1;
        }

        return 0;
    }

}
