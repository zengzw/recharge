package com.tsh.vas.enume;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public enum SupplierCheckStatus {

    /**
     * 1.待审核。
     */
    CHECK_PENDING ("待审核", 1),
    /**
     * 2.已审核。
     */
    CHECKED ("已审核", 2),
    /**
     * 3.已终止服务
     */
    TERMINATION_SERVICES ("已终止服务", 3),

    /**
     * 4.已经删除
     */
    DELETED ("已删除", 4);

    private String desc;
    private Integer code;

    SupplierCheckStatus(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static SupplierCheckStatus getEnume(Integer code) {
        for (SupplierCheckStatus item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
