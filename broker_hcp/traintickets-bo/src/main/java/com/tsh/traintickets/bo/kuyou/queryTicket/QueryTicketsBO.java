package com.tsh.traintickets.bo.kuyou.queryTicket;

import com.tsh.traintickets.bo.kuyou.BaseBO;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15 015.
 */
public class QueryTicketsBO extends BaseBO {

    private List<TrainData> train_data;

    public List<TrainData> getTrain_data() {
        return train_data;
    }

    public void setTrain_data(List<TrainData> train_data) {
        this.train_data = train_data;
    }
}
