package com.tsh.vas.enume;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public enum SupplierChargeStoreable {
    /**
     * 1:可预存，
     */
    STOREABEL ("可预存", 1),
    /**
     * 2：不可预存
     */
    DISSTOREABEL ("不可预存", 2);

    private String desc;
    private Integer code;

    SupplierChargeStoreable(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static SupplierChargeStoreable getEnume(Integer code) {
        for (SupplierChargeStoreable item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
