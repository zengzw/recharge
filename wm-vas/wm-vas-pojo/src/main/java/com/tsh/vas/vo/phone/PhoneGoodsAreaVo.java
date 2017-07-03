package com.tsh.vas.vo.phone;

import java.io.Serializable;


public class PhoneGoodsAreaVo implements Serializable{

    /**
     * 
     */
    private static final Long serialVersionUID = -908020764054339286L;
    
    
    /**  销售区域Id*/
    private Long id;
    
    
    /**  商品Id*/
    private Long goodsId;

    /**  区域ID  全国位（-1）*/
    private String areaId;

    /**  区域名称*/
    private String areaName;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId =goodsId;
    }
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId =areaId;
    }
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName =areaName;
    }
}
