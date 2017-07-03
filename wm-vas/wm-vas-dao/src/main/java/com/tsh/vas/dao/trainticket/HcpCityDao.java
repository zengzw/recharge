package com.tsh.vas.dao.trainticket;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.tsh.vas.model.trainticket.HcpCityInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/12/3 003.
 */
@Repository
public class HcpCityDao extends HibernateDao<HcpCityInfo, Integer> {

    public List<HcpCityInfo> queryListOrderByLevel(){
        String hql = "from HcpCityInfo order by level ";
        return this.find(hql);
    }
}
