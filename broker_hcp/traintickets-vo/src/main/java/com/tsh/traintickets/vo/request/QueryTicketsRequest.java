package com.tsh.traintickets.vo.request;

import com.tsh.traintickets.vo.BaseRequest;

public class QueryTicketsRequest extends BaseRequest{

	private static final long serialVersionUID = 1L;

	/**
	 * 乘车时间
	 */
	private String travelTime;
	/**
	 * 出发站
	 */
	private String fromStation;
	/**
	 * 终点站
	 */
	private String arriveStation;


	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getArriveStation() {
		return arriveStation;
	}

	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}

}
