package com.tsh.vas.model.phone;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "phone_goods_area")
public class PhoneGoodsAreaPo implements Serializable{


    /**
     * 
     */
    private static final Long serialVersionUID = -7385665698355632621L;
    
    
    /**  销售区域Id*/
    private Long id;
    
    /**  商品Id*/
    private Long goodsId;
    
    /**  区域ID
            全国位（-1）*/
    private String areaId;
    
    /**  区域名称*/
    private String areaName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =id;
    }
    @Column(name = "goods_id")
    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId =goodsId;
    }
    @Column(name = "area_id")
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId =areaId;
    }
    @Column(name = "area_name")
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName =areaName;
    }
}
