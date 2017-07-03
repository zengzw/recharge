package com.tsh.traintickets.dal.service.kuyou;

import com.traintickets.common.BizException;
import com.tsh.traintickets.bo.kuyou.BaseBO;
import com.tsh.traintickets.bo.kuyou.checkticket.CheckTicketNumBO;
import com.tsh.traintickets.bo.kuyou.checkticket.CheckTicketNumSupplierRequest;
import com.tsh.traintickets.bo.kuyou.createorder.CreateOrderBO;
import com.tsh.traintickets.bo.kuyou.createorder.CreateOrderSupplierRequest;
import com.tsh.traintickets.bo.kuyou.ordercallback.OrderCallBackBO;
import com.tsh.traintickets.bo.kuyou.queryTicket.QueryTicketSupplierRequest;
import com.tsh.traintickets.bo.kuyou.queryTicket.QueryTicketsBO;
import com.tsh.traintickets.bo.kuyou.queryorder.QueryOrderBO;
import com.tsh.traintickets.bo.kuyou.queryorder.QueryOrderSupplierRequest;
import com.tsh.traintickets.bo.kuyou.querysubwaystation.QuerySubwayStationBO;
import com.tsh.traintickets.bo.kuyou.querysubwaystation.QuerySubwayStationSupplierRequest;
import com.tsh.traintickets.bo.kuyou.refundcallback.RefundTicketCallBackBO;
import com.tsh.traintickets.bo.kuyou.refundticket.RefundTicketBO;
import com.tsh.traintickets.bo.kuyou.refundticket.RefundTicketSupplierRequest;
import com.tsh.traintickets.bo.kuyou.verifyusers.VerifyUsersSupplierRequest;

public interface KuyouService {

    /**
     * 查询站站火车票
	 * @param request
     * @return
     * @throws BizException
	 */
	public QueryTicketsBO queryTickets(QueryTicketSupplierRequest request);

	/**
	 * 查询车次余票信息
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public CheckTicketNumBO queryTicketNum(CheckTicketNumSupplierRequest request);

	/**
	 * 验证用户信息
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public BaseBO verifyUsers(VerifyUsersSupplierRequest request);

	/**
	 * 查询火车途径站点
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public QuerySubwayStationBO querySubwayStation(QuerySubwayStationSupplierRequest request);

	/**
	 * 查询购票订单信息
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public QueryOrderBO queryOrderInfo(QueryOrderSupplierRequest request);

	/**
	 * 创建购票订单并出票
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public CreateOrderBO createOrder(CreateOrderSupplierRequest request);

	/**
	 * 退票退款
	 * @param request
	 * @return
	 * @throws BizException
	 */
	public RefundTicketBO refundTicket(RefundTicketSupplierRequest request);

	/**
	 * 订单出票回调处理
	 * @param request
	 * @throws BizException
	 */
	public void dealOrderCallBack(OrderCallBackBO request);

	/**
	 * 退票回调处理
	 * @param request
	 * @throws BizException
	 */
	public void dealRefundTicketCallBack(RefundTicketCallBackBO request);
}
