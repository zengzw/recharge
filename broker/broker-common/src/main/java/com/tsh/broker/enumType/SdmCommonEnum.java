package com.tsh.broker.enumType;

/**
 * SdmCommonEnum
 *
 * @author dengjd
 * @date 2016/10/15
 */
public class SdmCommonEnum {

    public static class RechargeEnum extends BaseEnum {
        public static final RechargeEnum DEFAULT = new RechargeEnum(0, "默认");
        public static final RechargeEnum WATER_RECHARGE = new RechargeEnum(1,"水费");      //水费充值
        public static final RechargeEnum ELECTRIC_RECHARGE = new RechargeEnum(2,"电费");    //电费充值
        public static final RechargeEnum GAS_RECHARGE = new RechargeEnum(3,"燃气费");        //燃气费充值

        public RechargeEnum(int value, String name) {
            super(value, name);
        }
    }

    public static class  StatementTypeEnum extends BaseEnum {
        public static final StatementTypeEnum DEFAULT = new StatementTypeEnum(0,"默认");
        public static final StatementTypeEnum PREPAYMENT = new StatementTypeEnum(1,"预付费");
        public static final StatementTypeEnum POSTPAID = new StatementTypeEnum(2,"后付费");

        public StatementTypeEnum(int value, String name) {
            super(value, name);
        }
    }

    public static class  UnitStatusEnum extends BaseEnum {
        public static final UnitStatusEnum  DEFAULT  = new UnitStatusEnum(0,"默认");
        public static final UnitStatusEnum  NORMAL  = new UnitStatusEnum(1,"正常");
        public static final UnitStatusEnum  MAINTAIN  = new UnitStatusEnum(2,"维护");

        public UnitStatusEnum(int value, String name) {
            super(value, name);
        }
    }

    /**
     *  账户类型枚举
     * 	1.户号 2.条形码 3.都支持(户号和条形码)
     */
    public static class  AccountTypeEnum extends BaseEnum {
        public static final AccountTypeEnum  DEFAULT  = new AccountTypeEnum(0,"默认");
        public static final AccountTypeEnum  ACCOUNT_TYPE_NO  = new AccountTypeEnum(1,"户号");
        public static final AccountTypeEnum  ACCOUNT_TYPE_BAR_CODE  = new AccountTypeEnum(2,"条形码");
        public static final AccountTypeEnum  ACCOUNT_TYPE_All  = new AccountTypeEnum(3,"全部");

        public AccountTypeEnum(int value, String name) {
            super(value, name);
        }
    }


    public static void main(String args[]){
        System.out.println(RechargeEnum.DEFAULT.getName());
        System.out.println(UnitStatusEnum.DEFAULT.nameOf("维护").getValue());
    }

}
