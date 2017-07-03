package com.tsh.traintickets.bo.kuyou.verifyusers;

import com.traintickets.common.BaseSerializable;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class VerifyUser extends BaseSerializable{

    private String ids_type;    // 证件类型
    private String user_ids;    // 证件号
    private String user_name;   // 用户姓名

    public String getIds_type() {
        return ids_type;
    }

    public void setIds_type(String ids_type) {
        this.ids_type = ids_type;
    }

    public String getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(String user_ids) {
        this.user_ids = user_ids;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
