package com.tsh.traintickets.vo.convert;

import com.google.gson.reflect.TypeToken;
import com.traintickets.common.utils.JsonUtils;
import com.tsh.traintickets.bo.kuyou.checkticket.CheckTicketNumSupplierRequest;
import com.tsh.traintickets.bo.kuyou.createorder.CreateOrderSupplierRequest;
import com.tsh.traintickets.bo.kuyou.ordercallback.OrderCallBackBO;
import com.tsh.traintickets.bo.kuyou.queryTicket.QueryTicketSupplierRequest;
import com.tsh.traintickets.bo.kuyou.queryorder.BookDetail;
import com.tsh.traintickets.bo.kuyou.queryorder.QueryOrderSupplierRequest;
import com.tsh.traintickets.bo.kuyou.querysubwaystation.QuerySubwayStationSupplierRequest;
import com.tsh.traintickets.bo.kuyou.refundcallback.RefundTicketCallBackBO;
import com.tsh.traintickets.bo.kuyou.refundticket.RefundTicketSupplierRequest;
import com.tsh.traintickets.bo.kuyou.verifyusers.VerifyUser;
import com.tsh.traintickets.bo.kuyou.verifyusers.VerifyUsersSupplierRequest;
import com.tsh.traintickets.vo.request.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ConvertRequest2BO {
	
	public static QueryTicketSupplierRequest convertQueryTickets(QueryTicketsRequest request){
		QueryTicketSupplierRequest supplierRequest = null;
		if(null != request){
			supplierRequest = new QueryTicketSupplierRequest();
			supplierRequest.setArrive_station(request.getArriveStation());
			supplierRequest.setFrom_station(request.getFromStation());
			supplierRequest.setTravel_time(request.getTravelTime());
		}
		return supplierRequest;
	}

	public static CheckTicketNumSupplierRequest convertCheckTicketNum(CheckTicketNumRequest request){
		CheckTicketNumSupplierRequest supplierRequest = null;
		if(null != request){
			supplierRequest = new CheckTicketNumSupplierRequest();
			supplierRequest.setArrive_station(request.getArriveStation());
			supplierRequest.setFrom_station(request.getFromStation());
			supplierRequest.setTrain_code(request.getTrainCode());
			supplierRequest.setTravel_time(request.getTravelTime());
		}
		return supplierRequest;
	}

	public static VerifyUsersSupplierRequest convertVerifyUsers(VerifyUsersRequest request){
		VerifyUsersSupplierRequest supplierRequest = null;
		if(StringUtils.isNotEmpty(request.getUserList())){
			List<VerifyUserModel> requestUserList = JsonUtils.convert2List(request.getUserList(), new TypeToken<List<VerifyUserModel>>(){}.getType());
			if(null != requestUserList && requestUserList.size() > 0) {
				supplierRequest = new VerifyUsersSupplierRequest();
				List<VerifyUser> userList = new ArrayList<VerifyUser>();
				for (VerifyUserModel user : requestUserList) {
					VerifyUser requestUser = new VerifyUser();
					requestUser.setIds_type(user.getIdType());
					requestUser.setUser_ids(user.getUserId());
					requestUser.setUser_name(user.getUserName());
					userList.add(requestUser);
				}
				supplierRequest.setDetail_list(userList);
			}
		}
		return supplierRequest;
	}

	public static QuerySubwayStationSupplierRequest convertQuerySubwayStation(QuerySubwayStationRequest request){
		QuerySubwayStationSupplierRequest supplierRequest = null;
		if(null != request){
			supplierRequest = new QuerySubwayStationSupplierRequest();
			supplierRequest.setTrain_code(request.getTrainCode());
		}
		return  supplierRequest;
	}

	public static QueryOrderSupplierRequest convertQueryOrderInfo(QueryOrderInfoRequest request){
		QueryOrderSupplierRequest supplierRequest = null;
		if(null != request){
			supplierRequest = new QueryOrderSupplierRequest();
			supplierRequest.setOrder_id(request.getOrderId());
			supplierRequest.setMerchant_order_id(request.getMerchantOrderId());
		}
		return supplierRequest;
	}

	public static CreateOrderSupplierRequest convertCreateOrder(CreateOrderRequest request){
		CreateOrderSupplierRequest supplierRequest = null;
		if(null != request){
			supplierRequest = new CreateOrderSupplierRequest();
			supplierRequest.setMerchant_order_id(request.getMerchantOrderId());
			supplierRequest.setOrder_level(request.getOrderLevel());
			supplierRequest.setArrive_station(request.getArriveStation());
			supplierRequest.setArrive_time(request.getArriveTime());
			if(StringUtils.isNotEmpty(request.getUserDetailList())) {
				List<UserModel> userList = JsonUtils.convert2List(request.getUserDetailList(), new TypeToken<List<UserModel>>() {
				}.getType());
				if (null != userList && userList.size() > 0) {
					List<BookDetail> bookDetailList = new ArrayList<BookDetail>();
					for (UserModel userModel : userList) {
						BookDetail bookDetail = new BookDetail();
						bookDetail.setBx(userModel.getBx());
						bookDetail.setIds_type(userModel.getIdType());
						bookDetail.setTicket_type(userModel.getTicketType());
						bookDetail.setUser_ids(userModel.getUserId());
						bookDetail.setUser_name(userModel.getUserName());
						bookDetailList.add(bookDetail);
					}
					supplierRequest.setBook_detail_list(bookDetailList);
				}
			}
			if(StringUtils.isEmpty(request.getBxInvoice())){
				supplierRequest.setBx_invoice("0");
			} else {
				supplierRequest.setBx_invoice(request.getBxInvoice());
			}
			supplierRequest.setFrom_station(request.getFromStation());
			supplierRequest.setFrom_time(request.getFromTime());
			supplierRequest.setLink_address(request.getLinkAddress());
			supplierRequest.setLink_mail(request.getLinkMail());
			supplierRequest.setLink_name(request.getLinkName());
			supplierRequest.setLink_phone(request.getLinkPhone());
			supplierRequest.setSeat_type(request.getSeatType());
			supplierRequest.setSms_notify(request.getSmsNotify());
			supplierRequest.setSum_amount(request.getSumAmount());
			supplierRequest.setTicket_price(request.getTicketPrice());
			supplierRequest.setTrain_code(request.getTrainCode());
			supplierRequest.setTravel_time(request.getTravelTime());
			if(StringUtils.isEmpty(request.getWzExt())){
				supplierRequest.setWz_ext("0");
			} else {
				supplierRequest.setWz_ext(request.getWzExt());
			}

		}
		return supplierRequest;
	}

	public static RefundTicketSupplierRequest convertRefundTicket(RefundTicketRequest request){
		RefundTicketSupplierRequest supplierRequest = null;
		if(null != request){
			supplierRequest = new RefundTicketSupplierRequest();
			supplierRequest.setComment(request.getComment());
			supplierRequest.setMerchant_order_id(request.getMerchantOrderId());
			supplierRequest.setOrder_id(request.getOrderId());
			supplierRequest.setRefund_result_url(request.getRefundResultUrl());
			supplierRequest.setRefund_type(request.getRefundType());
			supplierRequest.setRequest_id(request.getRequestId());
//			if(null != request.getUserList() && request.getUserList().size() >  0){
//				List<RefundUserInfo> userInfoList = new ArrayList<RefundUserInfo>();
//				for(UserModel userModel : request.getUserList()){
//					RefundUserInfo refundUserInfo = new RefundUserInfo();
//					refundUserInfo.setUser_name(userModel.getUserName());
//					refundUserInfo.setUser_ids(userModel.getUserId());
//					refundUserInfo.setTicket_type(userModel.getTicketType());
//					refundUserInfo.setId_type(userModel.getIdType());
//					userInfoList.add(refundUserInfo);
//				}
//				supplierRequest.setRefundinfo(userInfoList);
//			}
		}
		return supplierRequest;
	}

	public static OrderCallBackBO convertDealOrderCallBack(DealOrderCallBackRequest callbackRequest){
		OrderCallBackBO callBackBO = null;
		if(null != callbackRequest){
			callBackBO = new OrderCallBackBO();
			callBackBO.setMerchantOrderId(callbackRequest.getMerchant_order_id());
			callBackBO.setTradeNo(callbackRequest.getTrade_no());
			callBackBO.setStatus(callbackRequest.getStatus());
			callBackBO.setFailReason(callbackRequest.getFail_reason());
			callBackBO.setAmount(callbackRequest.getAmount());
			callBackBO.setRefundAmount(callbackRequest.getRefund_amount());
			callBackBO.setRefundType(callbackRequest.getRefund_type());
			callBackBO.setOrderId(callbackRequest.getOrder_id());
			callBackBO.setOutTicketBillno(callbackRequest.getOut_ticket_billno());
			callBackBO.setOutTicketTime(callbackRequest.getOut_ticket_time());
			callBackBO.setBxPayMoney(callbackRequest.getBx_pay_money());

			if(null != callbackRequest.getOrder_userinfo() && callbackRequest.getOrder_userinfo().size() > 0){
				OrderUserInfoModel orderUser = callbackRequest.getOrder_userinfo().get(0);
				if(null != orderUser){
					callBackBO.setIdType(orderUser.getIds_type());
					callBackBO.setTicketType(orderUser.getTicket_type());
					callBackBO.setUserId(orderUser.getUser_ids());
					callBackBO.setUserName(orderUser.getUser_name());
					callBackBO.setTrainBox(orderUser.getTrain_box());
					callBackBO.setSeatNo(orderUser.getSeat_no());
					callBackBO.setSeatType(orderUser.getSeat_type());
				}
			}
		}
		return callBackBO;
	}

	public static RefundTicketCallBackBO convertRefundTicketCallBack(RefundTicketCallbackRequest callbackRequest){
		RefundTicketCallBackBO callBackBO = null;
		if(null != callbackRequest){
			callBackBO = new RefundTicketCallBackBO();
			callBackBO.setRequestId(callbackRequest.getRequest_id());
			callBackBO.setTripNo(callbackRequest.getTrip_no());
			callBackBO.setMerchantOrderId(callbackRequest.getMerchant_order_id());
			callBackBO.setStatus(callbackRequest.getStatus());
			callBackBO.setRefundTotalAmount(callbackRequest.getRefund_total_amount());
			callBackBO.setRefundType(callbackRequest.getRefund_type());
			callBackBO.setOrderId(callbackRequest.getOrder_id());

			if(null != callbackRequest.getOrder_userinfo() && callbackRequest.getOrder_userinfo().size() > 0){
				RefundOrderUserInfo refundOrderUserInfo = callbackRequest.getOrder_userinfo().get(0);
				if(null != refundOrderUserInfo){
					callBackBO.setIdType(refundOrderUserInfo.getIds_type());
					callBackBO.setTicketType(refundOrderUserInfo.getTicket_type());
					callBackBO.setUserId(refundOrderUserInfo.getUser_ids());
					callBackBO.setUserName(refundOrderUserInfo.getUser_name());
					callBackBO.setFailReason(refundOrderUserInfo.getFail_reason());
				}
			}
		}
		return callBackBO;
	}
}
