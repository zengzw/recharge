package com.tsh.vas.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.VasOrderTransformHistory;
import com.tsh.vas.vo.QueryOrderChangeVO;

/**
 * Created by Administrator on 2017/4/14 014.
 */
@Repository
public class VasOrderTransformHistoryDao extends HibernateDao<VasOrderTransformHistory, Long> {

    public Result queryByOrderNo(Result result, String orderType, String orderNo) {
        String hql = " from VasOrderTransformHistory where orderType = ? and orderCode = ? order by updateTime desc";
        List<VasOrderTransformHistory> list = this.find (hql, orderType, orderNo);
        result.setData (list);
        return result;
    }
    
    
    

    /**
     * 根据条件获取 列表
     * 
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryOrderTransformHistoryList(Result result,Page page,QueryOrderChangeVO phoneOrderTransformHistoryPo){
        Criteria criteria = this.getSession().createCriteria(VasOrderTransformHistory.class);
        if(null != phoneOrderTransformHistoryPo){
            if(StringUtils.isNotBlank(phoneOrderTransformHistoryPo.getOrderNo())){
                criteria.add(Restrictions.eq("orderCode", phoneOrderTransformHistoryPo.getOrderNo()));
            }
            
            if(StringUtils.isNotBlank(phoneOrderTransformHistoryPo.getOrderType())){
                criteria.add(Restrictions.eq("orderType", phoneOrderTransformHistoryPo.getOrderType()));
            }
        }
        Order order = Order.desc("updateTime");
        criteria.addOrder(order);
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }

}
