package com.tsh.vas.vo.trainticket.locationbean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5 005.
 */
public class ProvinceBean {

    private String name;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }
}
