package com.tsh.broker.vo.sdm.request;

import com.tsh.broker.vo.common.BaseRequest;

/**
 * RechargeResultQuery
 *
 * @author dengjd
 * @date 2016/9/28
 */
public class RechargeResultRequest extends BaseRequest {

    private String inOrderNo;	//内部订单编码
    private String outOrderNo;	//外部订单编号

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getInOrderNo() {
        return inOrderNo;
    }

    public void setInOrderNo(String inOrderNo) {
        this.inOrderNo = inOrderNo;
    }


}
