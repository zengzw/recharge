package com.tsh.vas.vo.trainticket.locationbean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5 005.
 */
public class CityBean {

    private String name;
    private List<DistrictBean> district;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictBean> getDistrict() {
        return district;
    }

    public void setDistrict(List<DistrictBean> district) {
        this.district = district;
    }
}
