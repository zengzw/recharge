package com.tsh.vas.trainticket.vo.resp;

import java.io.Serializable;
import java.util.List;

import com.tsh.vas.trainticket.vo.QueryTicketModel;

public class QueryTicketsResponse implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 4278386449990463024L;
    

    /**
     * 车次列表
     */
    private List<QueryTicketModel> dataList;
    

    /**
     * 始发站列表
     */
	private List<String> fromStationList;

	/**
	 * 终点站列表
	 */
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
