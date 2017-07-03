package com.tsh.vas.model.trainticket;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/12/3 003.
 */
@Entity
@Table(name = "hcp_city")
public class HcpCityInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "parent_id")
    private int parent_id;

    @Column(name = "level")
    private int level;

    @Column(name = "area_code")
    private String area_code;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String short_name;

    @Column(name = "owner_name")
    private String owner_name;

    @Column(name = "pinyin")
    private String pinyin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
