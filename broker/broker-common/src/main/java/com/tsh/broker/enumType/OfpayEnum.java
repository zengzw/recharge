package com.tsh.broker.enumType;

/**
 * OfpayEnum
 *
 * @author dengjd
 * @date 2016/10/15
 */
public class OfpayEnum {

    /**
     * 欧飞缴费类型枚举
     */
    public static class PayProjectEnum extends BaseEnum {
        public static final PayProjectEnum DEFAULT = new PayProjectEnum(0, "");
        public static final PayProjectEnum WATER_PAY_PROJECT = new PayProjectEnum(1,"c2670");      //水费充值
        public static final PayProjectEnum ELECTRIC_PAY_PROJECT = new PayProjectEnum(2,"c2680");   //电费充值
        public static final PayProjectEnum GAS_PAY_PROJECT = new PayProjectEnum(3,"c2681");        //燃气费充值

        public PayProjectEnum(int value, String name) {
            super(value, name);
        }
    }


    /**
     * 欧飞查询账单的枚缴费类型（001：水费、002：电费、003：燃气费）
     */
    public static class ChargeTypeEnum extends BaseEnum {
        public static final ChargeTypeEnum DEFAULT = new ChargeTypeEnum(0, "");
        public static final ChargeTypeEnum WATER_CHARGE_TYPE = new ChargeTypeEnum(1,"001");      //水费充值
        public static final ChargeTypeEnum ELECTRIC_CHARGE_TYPE = new ChargeTypeEnum(2,"002");   //电费充值
        public static final ChargeTypeEnum GAS_CHARGE_TYPE = new ChargeTypeEnum(3,"003");        //燃气费充值

        public ChargeTypeEnum(int value, String name) {
            super(value, name);
        }
    }

    /**
     * 欧飞账单查询接口，充值类型
     */
    public static class RechargeTypeEnum extends BaseEnum {
        public static final RechargeTypeEnum DEFAULT = new RechargeTypeEnum(0, "");
        public static final RechargeTypeEnum RECHARGE_WATER = new RechargeTypeEnum(1,"001");      //水费充值
        public static final RechargeTypeEnum RECHARGE_ELECTRIC = new RechargeTypeEnum(2,"002");   //电费充值
        public static final RechargeTypeEnum RECHARGE_GAS = new RechargeTypeEnum(3,"003");        //燃气费充值

        public RechargeTypeEnum(int value, String name) {
            super(value, name);
        }
    }

    /**
     * 欧飞账户类型
     */
    public static class AccountTypeEnum extends BaseEnum {
        public static final AccountTypeEnum DEFAULT = new AccountTypeEnum(0, "");
        public static final AccountTypeEnum ACCOUNT_TYPE_NO = new AccountTypeEnum(1,"v2620");         //户号
        public static final AccountTypeEnum ACCOUNT_TYPE_BAR_CODE = new AccountTypeEnum(2,"v2580");   //条形码编号

        public AccountTypeEnum(int value, String name) {
            super(value, name);
        }
    }

}
