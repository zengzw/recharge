package com.tsh.traintickets.vo.convert;

import com.traintickets.common.TrainType;
import com.traintickets.common.utils.DateUtils;
import com.tsh.traintickets.bo.kuyou.checkticket.CheckTicketNumBO;
import com.tsh.traintickets.bo.kuyou.createorder.CreateOrderBO;
import com.tsh.traintickets.bo.kuyou.queryTicket.TrainData;

import com.tsh.traintickets.bo.kuyou.queryorder.BookDetail;
import com.tsh.traintickets.bo.kuyou.queryorder.TicketInfo;
import com.tsh.traintickets.bo.kuyou.querysubwaystation.QuerySubwayStationBO;
import com.tsh.traintickets.bo.kuyou.querysubwaystation.StationInfo;
import com.tsh.traintickets.bo.kuyou.refundticket.RefundTicketBO;
import com.tsh.traintickets.vo.request.QuerySubwayStationRequest;
import com.tsh.traintickets.vo.request.QueryTicketsRequest;
import com.tsh.traintickets.vo.response.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Convert2VO {
	private static Logger logger = LogManager.getLogger(Convert2VO.class);

	public static List<QueryTicketModel> convertQueryTicket(QueryTicketsRequest request, List<TrainData> dataList){
		List<QueryTicketModel> modelList = new ArrayList<QueryTicketModel>();
		if(null != dataList && dataList.size() > 0){
			String temp = DateUtils.format(DateUtils.addHour(new Date(), 1), DateUtils.DATE_PATTERN_2);
			for(TrainData data : dataList) {
				QueryTicketModel model = new QueryTicketModel();
				model.setArriveStation(data.getArrive_station());
				model.setArriveTime(data.getArrive_time());
				model.setCostTime(data.getCost_time());
				model.setEndStation(data.getEnd_station());
				model.setEndTime(data.getEnd_time());
				model.setFromStation(data.getFrom_station());
				model.setFromTime(data.getFrom_time());
				model.setGwNum(data.getGw_num());
				model.setGws(data.getGws());
				model.setGwx(data.getGwx());
				model.setRwNum(data.getRw_num());
				model.setRws(data.getRws());
				model.setRwx(data.getRwx());
				model.setRz(data.getRz());
				model.setRz1(data.getRz1());
				model.setRz1Num(data.getRz1_num());
				model.setRz2(data.getRz2());
				model.setRz2Num(data.getRz2_num());
				model.setRzNum(data.getRz_num());
				model.setStartStation(data.getStart_station());
				model.setStartTime(data.getStart_time());
				model.setSwz(data.getSwz());
				model.setSwzNum(data.getSwz_num());
				model.setTdz(data.getTdz());
				model.setTdzNum(data.getTdz_num());
				model.setTrainCode(data.getTrain_code());
				model.setTrainPro1(data.getTrain_pro1());
				model.setTrainPro2(data.getTrain_pro2());
				model.setWz(data.getWz());
				model.setWzNum(data.getWz_num());
				model.setYwNum(data.getYw_num());
				model.setYws(data.getYws());
				model.setYwx(data.getYwx());
				model.setYwz(data.getYwz());
				model.setYz(data.getYz());
				model.setYzNum(data.getYz_num());
				// 如果途径时间是5999，表示车次停运，直接不返回
				if("5999".equals(model.getCostTime())){
					continue;
				}

				// 获取车次开头字母
				if(StringUtils.isNotEmpty(data.getTrain_code())){
					if(null != TrainType.get(data.getTrain_code().substring(0,1))){
						model.setTrainType(TrainType.get(data.getTrain_code().substring(0,1)).getDesc());
					} else {
						model.setTrainType(TrainType.Q.getDesc());
						logger.error("---------> trainCode error!!!" + data.getTrain_code());
					}
				}


				// 如果查询日期是当天，出发时间 > 当前时间+1小时
				if(request.getTravelTime().equals(DateUtils.getCurrentDate())){
					if(data.getFrom_time().compareTo(temp) > 0){
						modelList.add(model);
					}
				} else {
					modelList.add(model);
				}

			}
		}
		return modelList;
	}

	public static CheckTicketNumModel convertCheckTicket(CheckTicketNumBO data){
		CheckTicketNumModel model = new CheckTicketNumModel();
		if(null != data) {
			model.setArriveStation(data.getArrive_station());
			model.setArriveTime(data.getArrive_time());
			model.setEndStation(data.getEnd_station());
			model.setEndTime(data.getEnd_time());
			model.setFromStation(data.getFrom_station());
			model.setFromTime(data.getFrom_time());
			model.setGwNum(data.getGw_num());
			model.setRwNum(data.getRw_num());
			model.setRz1Num(data.getRz1_num());
			model.setRz2Num(data.getRz2_num());
			model.setRzNum(data.getRz_num());
			model.setStartStation(data.getStart_station());
			model.setStartTime(data.getStart_time());
			model.setSwzNum(data.getSwz_num());
			model.setTdzNum(data.getTdz_num());
			model.setTrainCode(data.getTrain_code());
			model.setWzNum(data.getWz_num());
			model.setYwNum(data.getYw_num());
			model.setYzNum(data.getYz_num());
		}
		return model;
	}

	public static Map<String, Object> convertQuerySubwayStation(QuerySubwayStationRequest request, QuerySubwayStationBO data){
		Map<String, Object> resultMap = new HashedMap();
		String arriveDays = "";
		List<QuerySubwayStationModel> modelList = null;
		if(null != data && null != data.getTrain_stationinfo() && data.getTrain_stationinfo().size() > 0){
			modelList = new ArrayList<QuerySubwayStationModel>();
			String isMiddle = "0";// 默认不检查是否在出发站和到达站之间
			boolean isNeedCheckMiddle = false;
			// 出发站和到达站都不为空时校验
			if(StringUtils.isNotEmpty(request.getFromStation()) && StringUtils.isNotEmpty(request.getArriveStation())){
				isNeedCheckMiddle = true;
			} else {
				isMiddle = "1";// 默认全部在中间
			}
			for(StationInfo stationInfo: data.getTrain_stationinfo()) {
				QuerySubwayStationModel model = new QuerySubwayStationModel();
				model.setArrtime(stationInfo.getArrtime());
				model.setCosttime(stationInfo.getCosttime());
				model.setDistance(stationInfo.getDistance());
				model.setInterval(stationInfo.getInterval());
				model.setName(stationInfo.getName());
				model.setStarttime(stationInfo.getStarttime());
				model.setStationno(stationInfo.getStationno());
				if(isNeedCheckMiddle && StringUtils.isNotEmpty(request.getFromStation()) &&
						request.getFromStation().equals(stationInfo.getName())){
					isMiddle = "1";
				}
				model.setIsMiddle(isMiddle);
				if(StringUtils.isNotEmpty(request.getFromStation()) &&
						request.getArriveStation().equals(stationInfo.getName())){
					isMiddle = "0";
					arriveDays = stationInfo.getCosttime();
				}
				modelList.add(model);
			}
			if(StringUtils.isEmpty(arriveDays)){
				// 默认去最后一条的时间
				arriveDays = data.getTrain_stationinfo().get(data.getTrain_stationinfo().size()-1).getCosttime();
			}
		}
		resultMap.put("list", modelList);
		resultMap.put("arriveDays", arriveDays);
		return resultMap;
	}

	public static OrderInfoModel convertQueryOrderInfo(TicketInfo ticketInfo){
		OrderInfoModel model = null;
		if(null != ticketInfo){
			model = new OrderInfoModel();
			model.setArriveStation(ticketInfo.getArrive_station());
			model.setArriveTime(ticketInfo.getArrive_time());
			model.setFromStation(ticketInfo.getFrom_station());
			model.setFromTime(ticketInfo.getFrom_time());
			model.setOrderStatus(ticketInfo.getOrder_status());
			model.setOutTicketTime(ticketInfo.getOut_ticket_time());
			model.setPayMoney(ticketInfo.getPay_money());
			model.setPayTime(ticketInfo.getPay_time());
			model.setRefundStatus(ticketInfo.getRefund_status());
			model.setSeatType(ticketInfo.getSeat_type());
			model.setTicketPrice(ticketInfo.getTicket_price());
			model.setTrainCode(ticketInfo.getTrain_code());
			model.setTravelTime(ticketInfo.getTravel_time());
			if(null != ticketInfo.getBook_detail_list() && ticketInfo.getBook_detail_list().size() > 0){
				List<OrderUserInfoModel> users = new ArrayList<OrderUserInfoModel>();
				for(BookDetail detail : ticketInfo.getBook_detail_list()){
					OrderUserInfoModel userInfoModel = new OrderUserInfoModel();
					userInfoModel.setBx(detail.getBx());
					userInfoModel.setBxCode(detail.getBx_code());
					userInfoModel.setIdType(detail.getIds_type());
					userInfoModel.setTicketType(detail.getTicket_type());
					userInfoModel.setUserId(detail.getUser_ids());
					userInfoModel.setUserName(detail.getUser_name());
					users.add(userInfoModel);
				}
				model.setUsers(users);
			}


		}
		return model;
	}

	public static CreateOrderModel convertCreateOrder(CreateOrderBO createOrderBO){
		CreateOrderModel model = null;
		if(null != createOrderBO){
			model = new CreateOrderModel();
			model.setArriveStation(createOrderBO.getArrive_station());
			model.setArriveTime(createOrderBO.getArrive_time());
			model.setBxPayMoney(createOrderBO.getBx_pay_money());
			model.setFromStation(createOrderBO.getFrom_station());
			model.setFromTime(createOrderBO.getFrom_time());
			model.setMerchantOrderId(createOrderBO.getMerchant_order_id());
			model.setOrderId(createOrderBO.getOrder_id());
			model.setPayMoney(createOrderBO.getPay_money());
			model.setSeatType(createOrderBO.getSeat_type());
			model.setTicketPayMoney(createOrderBO.getTicket_pay_money());
			model.setTrainCode(createOrderBO.getTrain_code());
			model.setTravelTime(createOrderBO.getTravel_time());
		}
		return model;
	}

	public static RefundTicketModel convertRefundTicket(RefundTicketBO refundTicketBO){
		RefundTicketModel model = null;
		if(null != refundTicketBO){
			model = new RefundTicketModel();
			model.setFailReason(refundTicketBO.getFail_reason());
			model.setMerchantOrderId(refundTicketBO.getMerchant_order_id());
			model.setOrderId(refundTicketBO.getOrder_id());
			model.setTripNo(refundTicketBO.getTrip_no());
		}
		return model;
	}
}
