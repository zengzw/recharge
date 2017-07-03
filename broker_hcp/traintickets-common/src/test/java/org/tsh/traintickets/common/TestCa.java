package org.tsh.traintickets.common;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/20 020.
 */
public class TestCa {
    public static void main(String[] args) {
        BigDecimal money = BigDecimal.valueOf(151);
        BigDecimal fee = BigDecimal.valueOf(23.353);

        fee = fee.setScale(2, BigDecimal.ROUND_HALF_UP);
        money = money.subtract(fee);


        String doubleValue = String.valueOf(money.doubleValue());
        if(StringUtils.isNotEmpty(doubleValue)){
            Float miniData = 0.00f;
            int index = doubleValue.indexOf('.');
            if(index > 0){
                String mini = doubleValue.substring(index + 1);
                if(StringUtils.isNotEmpty(mini)){
                    Integer miniValue = Integer.valueOf(mini);
                    if(miniValue < 25){
                        miniData = 0.0f;
                    }
                    if(miniValue >= 25 && miniValue < 75){
                        miniData = 0.5f;
                    }
                    if(miniValue > 75){
                        miniData = 1.00f;
                    }
                }
            }

            money = BigDecimal.valueOf(money.intValue());
            money = money.add(BigDecimal.valueOf(miniData));
        }


        System.out.println(money);
    }
}
