package com.tsh.broker.po;

import javax.persistence.*;
import java.io.Serializable;

/**
 * AreaMappingInfo
 *
 * @author dengjd
 * @date 2016/10/12
 */
@Entity
@Table(name = "area_mapping_info")
public class AreaMappingInfo  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "origin_province")
    private String originProvince;      //内部省名称
    @Column(name = "origin_city")
    private String originCity;      //内部市名称
    @Column(name = "origin_county")
    private String originCounty;      //内部县名称
    @Column(name = "target_province")
    private String targetProvince;      //内部省名称
    @Column(name = "target_city")
    private String targetCity;      //供应商市名称
    @Column(name = "target_county")
    private String targetCounty;      //供应商县名称
    @Column(name = "target_province_no")
    private String targetProvinceNo;      //内部省编号
    @Column(name = "target_city_no")
    private String targetCityNo;      //供应商市编号
    @Column(name = "target_county_no")
    private String targetCountyNo;      //供应商县编号

    @Column(name = "bizzType")
    private Integer bizzType;      //供应商

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginProvince() {
        return originProvince;
    }

    public void setOriginProvince(String originProvince) {
        this.originProvince = originProvince;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getOriginCounty() {
        return originCounty;
    }

    public void setOriginCounty(String originCounty) {
        this.originCounty = originCounty;
    }

    public String getTargetProvince() {
        return targetProvince;
    }

    public void setTargetProvince(String targetProvince) {
        this.targetProvince = targetProvince;
    }

    public String getTargetCity() {
        return targetCity;
    }

    public void setTargetCity(String targetCity) {
        this.targetCity = targetCity;
    }

    public String getTargetCounty() {
        return targetCounty;
    }

    public void setTargetCounty(String targetCounty) {
        this.targetCounty = targetCounty;
    }

    public String getTargetProvinceNo() {
        return targetProvinceNo;
    }

    public void setTargetProvinceNo(String targetProvinceNo) {
        this.targetProvinceNo = targetProvinceNo;
    }

    public String getTargetCityNo() {
        return targetCityNo;
    }

    public void setTargetCityNo(String targetCityNo) {
        this.targetCityNo = targetCityNo;
    }

    public String getTargetCountyNo() {
        return targetCountyNo;
    }

    public void setTargetCountyNo(String targetCountyNo) {
        this.targetCountyNo = targetCountyNo;
    }

    public Integer getBizzType() {
        return bizzType;
    }

    public void setBizzType(Integer bizzType) {
        this.bizzType = bizzType;
    }

}
