package com.tsh.traintickets.dal.service.kuyou;

import com.traintickets.common.HcpSupplierType;
import com.traintickets.common.LogType;
import com.traintickets.common.kuyou.method.KuyouCallbackMethod;
import com.traintickets.common.kuyou.method.KuyouSupplierMethod;
import com.traintickets.common.utils.JsonUtils;
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
import com.tsh.traintickets.dal.service.TicketHttpHandler;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.dal.service.log.LogProxyService;
import com.tsh.traintickets.dal.service.log.ProcessingRecordLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.lang.reflect.Proxy;

@Service
public class KuyouServiceImpl implements KuyouService {

	@Resource(name = "queryTicketsHandler")
	TicketHttpHandler queryTicketsHandler;

	@Resource(name = "checkTicketNumHandler")
	TicketHttpHandler checkTicketNumHandler;

	@Resource(name = "verifyUsersHandler")
	TicketHttpHandler verifyUsersHandler;

	@Resource(name = "querySubwayStationHandler")
	TicketHttpHandler querySubwayStationHandler;

	@Resource(name = "queryOrderInfoHandler")
	TicketHttpHandler queryOrderInfoHandler;

	@Resource(name = "createOrderHandler")
	TicketHttpHandler createOrderHandler;

	@Resource(name = "refundTicketHandler")
	TicketHttpHandler refundTicketHandler;

	@Resource(name = "orderCallBackHandler")
	TicketHttpHandler orderCallBackHandler;
	@Resource(name = "refundTicketCallBackHandler")
	TicketHttpHandler refundTicketCallBackHandler;

	@Resource
	ProcessingRecordLogService processingRecordLogService;

	@Override
	public QueryTicketsBO queryTickets(QueryTicketSupplierRequest supplierRequest) {
		LogProxyService proxyService = new LogProxyService(queryTicketsHandler);
		proxyService.setProcessingRecordLogService(processingRecordLogService);
		proxyService.setRequest(supplierRequest);
		proxyService.setSupplierType(HcpSupplierType.kuyou);
		proxyService.setLogType(LogType.HTTP);
		proxyService.setMethodName(KuyouSupplierMethod.queryLeftTicket.toString());

		TicketHttpHandler httpHandler = (TicketHttpHandler) Proxy.newProxyInstance(
				queryTicketsHandler.getClass().getClassLoader(),
				queryTicketsHandler.getClass().getInterfaces(),
				proxyService);

		String result = httpHandler.doHandler(supplierRequest);

		if(!StringUtils.isEmpty(result)){
			return JsonUtils.convert2Object(result, QueryTicketsBO.class);
		}
		return null;
	}

	@Override
	public CheckTicketNumBO queryTicketNum(CheckTicketNumSupplierRequest supplierRequest) {
		LogProxyService proxyService = new LogProxyService(checkTicketNumHandler);
		proxyService.setProcessingRecordLogService(processingRecordLogService);
		proxyService.setRequest(supplierRequest);
		proxyService.setSupplierType(HcpSupplierType.kuyou);
		proxyService.setLogType(LogType.HTTP);
		proxyService.setMethodName(KuyouSupplierMethod.checkTicketNum.toString());

		TicketHttpHandler httpHandler = (TicketHttpHandler) Proxy.newProxyInstance(
				checkTicketNumHandler.getClass().getClassLoader(),
				checkTicketNumHandler.getClass().getInterfaces(),
				proxyService);

		String result = httpHandler.doHandler(supplierRequest);

		if(!StringUtils.isEmpty(result)){
			return JsonUtils.convert2Object(result, CheckTicketNumBO.class);
		}
		return null;
	}

	@Override
	public BaseBO verifyUsers(VerifyUsersSupplierRequest supplierRequest) {
		LogProxyService proxyService = new LogProxyService(verifyUsersHandler);
		proxyService.setProcessingRecordLogService(processingRecordLogService);
		proxyService.setRequest(supplierRequest);
		proxyService.setSupplierType(HcpSupplierType.kuyou);
		proxyService.setLogType(LogType.HTTP);
		proxyService.setMethodName(KuyouSupplierMethod.verifyUsers.toString());

		TicketHttpHandler httpHandler = (TicketHttpHandler) Proxy.newProxyInstance(
				verifyUsersHandler.getClass().getClassLoader(),
				verifyUsersHandler.getClass().getInterfaces(),
				proxyService);

		String result = httpHandler.doHandler(supplierRequest);
		if(!StringUtils.isEmpty(result)){
			return JsonUtils.convert2Object(result, BaseBO.class);
		}
		return null;
	}

	@Override
	public QuerySubwayStationBO querySubwayStation(QuerySubwayStationSupplierRequest supplierRequest) {
		LogProxyService proxyService = new LogProxyService(querySubwayStationHandler);
		proxyService.setProcessingRecordLogService(processingRecordLogService);
		proxyService.setRequest(supplierRequest);
		proxyService.setSupplierType(HcpSupplierType.kuyou);
		proxyService.setLogType(LogType.HTTP);
		proxyService.setMethodName(KuyouSupplierMethod.querySubwayStation.toString());

		TicketHttpHandler httpHandler = (TicketHttpHandler) Proxy.newProxyInstance(
				querySubwayStationHandler.getClass().getClassLoader(),
				querySubwayStationHandler.getClass().getInterfaces(),
				proxyService);

		String result = httpHandler.doHandler(supplierRequest);
		if(!StringUtils.isEmpty(result)){
			return JsonUtils.convert2Object(result, QuerySubwayStationBO.class);
		}
		return null;
	}

	@Override
	public QueryOrderBO queryOrderInfo(QueryOrderSupplierRequest supplierRequest) {
		LogProxyService proxyService = new LogProxyService(queryOrderInfoHandler);
		proxyService.setProcessingRecordLogService(processingRecordLogService);
		proxyService.setRequest(supplierRequest);
		proxyService.setSupplierType(HcpSupplierType.kuyou);
		proxyService.setLogType(LogType.HTTP);
		proxyService.setMethodName(KuyouSupplierMethod.queryOrderInfo.toString());


		TicketHttpHandler httpHandler = (TicketHttpHandler) Proxy.newProxyInstance(
				queryOrderInfoHandler.getClass().getClassLoader(),
				queryOrderInfoHandler.getClass().getInterfaces(),
				proxyService);

		String result = httpHandler.doHandler(supplierRequest);
		if(!StringUtils.isEmpty(result)){
			return JsonUtils.convert2Object(result, QueryOrderBO.class);
		}
		return null;
	}

	@Override
	public CreateOrderBO createOrder(CreateOrderSupplierRequest supplierRequest) {
		// 配置参数
		supplierRequest.setOrder_result_url(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_orderCallbackUrl));
		supplierRequest.setBx_invoice("0");

		LogProxyService proxyService = new LogProxyService(createOrderHandler);
		proxyService.setProcessingRecordLogService(processingRecordLogService);
		proxyService.setRequest(supplierRequest);
		proxyService.setSupplierType(HcpSupplierType.kuyou);
		proxyService.setLogType(LogType.HTTP);
		proxyService.setMethodName(KuyouSupplierMethod.createOrder.toString());


		TicketHttpHandler httpHandler = (TicketHttpHandler) Proxy.newProxyInstance(
				createOrderHandler.getClass().getClassLoader(),
				createOrderHandler.getClass().getInterfaces(),
				proxyService);

		String result = httpHandler.doHandler(supplierRequest);
		if(!StringUtils.isEmpty(result)){
			return JsonUtils.convert2Object(result, CreateOrderBO.class);
		}
		return null;
	}

	@Override
	public RefundTicketBO refundTicket(RefundTicketSupplierRequest supplierRequest) {
		// 配置参数
		supplierRequest.setRefund_result_url(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_refundCallbackUrl));


		LogProxyService proxyService = new LogProxyService(refundTicketHandler);
		proxyService.setProcessingRecordLogService(processingRecordLogService);
		proxyService.setRequest(supplierRequest);
		proxyService.setSupplierType(HcpSupplierType.kuyou);
		proxyService.setLogType(LogType.HTTP);
		proxyService.setMethodName(KuyouSupplierMethod.refundTicket.toString());

		TicketHttpHandler httpHandler = (TicketHttpHandler) Proxy.newProxyInstance(
				refundTicketHandler.getClass().getClassLoader(),
				refundTicketHandler.getClass().getInterfaces(),
				proxyService);

		String result = httpHandler.doHandler(supplierRequest);
		if(!StringUtils.isEmpty(result)){
			return JsonUtils.convert2Object(result, RefundTicketBO.class);
		}
		return null;
	}

	@Override
	public void dealOrderCallBack(OrderCallBackBO request) {
		LogProxyService proxyService = new LogProxyService(orderCallBackHandler);
		proxyService.setProcessingRecordLogService(processingRecordLogService);
		proxyService.setRequest(request);
		proxyService.setSupplierType(HcpSupplierType.vas);
		proxyService.setLogType(LogType.HTTP);
		proxyService.setMethodName(KuyouCallbackMethod.orderCallback.toString());

		TicketHttpHandler httpHandler = (TicketHttpHandler) Proxy.newProxyInstance(
				orderCallBackHandler.getClass().getClassLoader(),
				orderCallBackHandler.getClass().getInterfaces(),
				proxyService);

		httpHandler.doHandler(request);


	}

	@Override
	public void dealRefundTicketCallBack(RefundTicketCallBackBO request) {
		LogProxyService proxyService = new LogProxyService(refundTicketCallBackHandler);
		proxyService.setProcessingRecordLogService(processingRecordLogService);
		proxyService.setRequest(request);
		proxyService.setSupplierType(HcpSupplierType.vas);
		proxyService.setLogType(LogType.HTTP);
		proxyService.setMethodName(KuyouCallbackMethod.refundTicketCallback.toString());


		TicketHttpHandler httpHandler = (TicketHttpHandler) Proxy.newProxyInstance(
				refundTicketCallBackHandler.getClass().getClassLoader(),
				refundTicketCallBackHandler.getClass().getInterfaces(),
				proxyService);

		httpHandler.doHandler(request);
	}

}
