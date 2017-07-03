package com.tsh.vas.enume;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public enum ClientChargeRefundStatus {
    /**
     * 0：未发生退款
     */
    NORMAL_REFUND ("未发生退款", 0),
    /**
     * 1：退款中
     */
    WAITING ("退款中", 1),
    /**
     * 2：退款成功
     */
    SUCCESS ("退款成功", 2);

    private String desc;
    private Integer code;

    ClientChargeRefundStatus(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static ClientChargeRefundStatus getEnume(Integer code) {
        for (ClientChargeRefundStatus item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }

    public static List<Integer> convertRefundStatus(Integer refundStatusCode) {
        List<Integer> resultList = Lists.newArrayList ();
        switch (getEnume (refundStatusCode)) {
            case NORMAL_REFUND:
                return resultList;
            case WAITING:
                resultList.add (ChargeRefundStatus.NORMAL_REFUND.getCode ());
                resultList.add (ChargeRefundStatus.WAIT_REFUND.getCode ());
                resultList.add (ChargeRefundStatus.REFUND_FAIL.getCode ());
                return resultList;
            case SUCCESS:
                resultList.add (ChargeRefundStatus.REFUND.getCode ());
                return resultList;
            default:
                throw new IllegalArgumentException ("枚举非法参数");
        }
    }

}
