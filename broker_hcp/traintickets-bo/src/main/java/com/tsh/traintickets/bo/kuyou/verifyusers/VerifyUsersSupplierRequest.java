package com.tsh.traintickets.bo.kuyou.verifyusers;

import com.traintickets.common.BaseSerializable;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19 019.
 */
public class VerifyUsersSupplierRequest extends BaseSerializable {

    private List<VerifyUser> detail_list;

    public List<VerifyUser> getDetail_list() {
        return detail_list;
    }

    public void setDetail_list(List<VerifyUser> detail_list) {
        this.detail_list = detail_list;
    }
}
