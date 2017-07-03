package com.tsh.vas.enume;

/**
 * Created by Iritchie.ren on 2016/10/27.
 */
public enum RoleType {
    /**
     * roleType 角色类型 2:平台管理 3:县域 4:网点，5：供应商 9：会员
     * */
    /**
     * 2:平台管理
     */
    PLATFORM ("平台管理", 2),

    /**
     * 3:县域
     */
    AREA ("县域", 3),
    /**
     * 4:网点
     */
    SHOP ("网点", 4),
    /**
     * 5：供应商
     */
    PLFSUPPLIER ("供应商", 5),
    /**
     * 9：会员
     */
    MEMBER ("会员", 9);

    private String desc;
    private Integer code;

    RoleType(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static RoleType getEnume(Integer code) {
        for (RoleType item : values ()) {
            if (item.getCode ().equals (code)) {
                return item;
            }
        }
        throw new IllegalArgumentException ("请求参数非法");
    }
}
