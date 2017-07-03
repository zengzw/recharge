package com.tsh.traintickets.vo.response;

import java.util.List;

import com.traintickets.common.BaseSerializable;

public class QueryTicketsResponse extends BaseSerializable{

	private static final long serialVersionUID = 1L;

	private List<QueryTicketModel> dataList;

	private List<String> fromStationList;

	private List<String> arriveStationList;

	private List<String> trainTypeList;

	public List<QueryTicketModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<QueryTicketModel> dataList) {
		this.dataList = dataList;
	}

	public List<String> getFromStationList() {
		return fromStationList;
	}

	public void setFromStationList(List<String> fromStationList) {
		this.fromStationList = fromStationList;
	}

	public List<String> getArriveStationList() {
		return arriveStationList;
	}

	public void setArriveStationList(List<String> arriveStationList) {
		this.arriveStationList = arriveStationList;
	}

	public List<String> getTrainTypeList() {
		return trainTypeList;
	}

	public void setTrainTypeList(List<String> trainTypeList) {
		this.trainTypeList = trainTypeList;
	}
}
