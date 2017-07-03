package com.tsh.broker.enumType;

/**
 * BizzType
 *
 * @author dengjd
 * @date 2016/9/29
 */
public enum BizzTypeEnum {
    DEFAULT(0,"DEFAULT"),
    SALER_WISE(1,"SalerWise"),
    OF_PAY(2,"OF_PAY");

    private int bizzType;
    private String bizzName;

    private BizzTypeEnum(int bizzType, String bizzName){
        this.bizzType = bizzType;
        this.bizzName = bizzName;
    }

    public static BizzTypeEnum valueOf(int bizzType){
        for (BizzTypeEnum bizzTypeEnum : BizzTypeEnum.values()) {
            if (bizzTypeEnum.bizzType == bizzType) {
                return bizzTypeEnum;
            }
        }

        return DEFAULT;
    }

    public int getBizzType() {
        return bizzType;
    }

    public String getBizzName() {
        return bizzName;
    }

}
