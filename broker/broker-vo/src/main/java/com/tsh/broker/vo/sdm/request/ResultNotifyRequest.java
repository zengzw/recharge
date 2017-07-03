package com.tsh.broker.vo.sdm.request;

import com.tsh.broker.vo.common.BaseRequest;

/**
 * ResultNotifyRequest
 *
 * @author dengjd
 * @date 2016/9/28
 */
public class ResultNotifyRequest extends BaseRequest {

    private String inOrderNo;      //内部订单编码
    private String outOrderNo;      //外部订单编号
    private Integer payResult;      //订单结果
    private String resultMsg;    //通知结果描述信息

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Integer getPayResult() {
        return payResult;
    }

    public void setPayResult(Integer payResult) {
        this.payResult = payResult;
    }

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

	@Override
	public String toString() {
		return "ResultNotifyRequest [inOrderNo=" + inOrderNo + ", outOrderNo="
				+ outOrderNo + ", payResult=" + payResult + ", resultMsg="
				+ resultMsg + "]";
	}
    
}
