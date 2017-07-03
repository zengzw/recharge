package com.tsh.vas.utils;

import java.math.BigDecimal;

/**
 * @see
 * @since JDK 1.7.0
 */
public class NumberUtil {

    public static BigDecimal getTwo(BigDecimal bigDecimal) {
        bigDecimal = bigDecimal.setScale (2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal;
    }
}

