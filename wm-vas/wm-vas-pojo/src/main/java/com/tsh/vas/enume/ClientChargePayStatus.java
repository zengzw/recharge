package com.tsh.vas.enume;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Iritchie.ren on 2016/9/20.
 */
public enum ClientChargePayStatus {
    /**
     * 1：等待结果
     */
    WAITING ("等待结果", 1),
    /**
     * 2：已成功
     */
    SUCCESS ("已成功", 2),
    /**
     * 3：已失败
     */
    FAIL ("已失败", 3),
    /**
     * 4：已撤销
     */
    UNDO ("已撤销", 4);

    private String desc;
    private Integer code;

    ClientChargePayStatus(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static ClientChargePayStatus getEnume(Integer code) {
        for (ClientChargePayStatus item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }

    public static List<Integer> convertPayStatus(Integer clientPayStatusCode) {
        List<Integer> resultList = Lists.newArrayList ();
        switch (getEnume (clientPayStatusCode)) {
            case WAITING:
                //1,2,3,10
                resultList.add (ChargePayStatus.NON_PAYMENT.getCode ());
                resultList.add (ChargePayStatus.PAIDING.getCode ());
                resultList.add (ChargePayStatus.CHARGING.getCode ());
                resultList.add (ChargePayStatus.PAY_SUCCESS.getCode ());
                return resultList;
            case SUCCESS:
                //4
                resultList.add (ChargePayStatus.TRAD_SUCCESS.getCode ());
                return resultList;
            case FAIL:
                //5,6,7,8
                resultList.add (ChargePayStatus.TRAD_FAIL.getCode ());
                resultList.add (ChargePayStatus.TRAD_CLOSE.getCode ());
                resultList.add (ChargePayStatus.PAY_FAIL.getCode ());
                resultList.add (ChargePayStatus.CHARGE_FAIL.getCode ());
                return resultList;
            case UNDO:
                //9
                resultList.add (ChargePayStatus.REVOCATION.getCode ());
                return resultList;
            default:
                throw new IllegalArgumentException ("枚举非法参数");
        }
    }
}
