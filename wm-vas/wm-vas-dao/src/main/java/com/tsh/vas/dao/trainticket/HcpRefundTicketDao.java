package com.tsh.vas.dao.trainticket;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.trainticket.HcpRefundTicketPo;

/**
 * 退票Dao
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Repository
@SuppressWarnings("all")
public class HcpRefundTicketDao extends HibernateDao<HcpRefundTicketPo, Integer> {
    /**
     * 新增退票接口对象
     * @param result
     * @param hcpRefundTicket
     * @return
     */
    public Result addHcpRefundTicket(Result result,HcpRefundTicketPo hcpRefundTicket){
        this.save(hcpRefundTicket);
        result.setData(hcpRefundTicket);
        return result;
    }


    public Result queryList(Result result, Page page, HcpRefundTicketPo refundTicketPo){
        Criteria criteria = this.getSession().createCriteria(HcpRefundTicketPo.class);
        if(refundTicketPo.getRefundStatus()!=null){
            criteria.add(Restrictions.eq("refundStatus", refundTicketPo.getRefundStatus()));
        }
        criteria.addOrder(Order.desc("createTime"));
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }


    /**
     * 批量删除退票接口对象
     * @param result
     * @param hcpRefundTicket
     * @return
     */
    public Result batchDelHcpRefundTicket(Result result, List<HcpRefundTicketPo> hcpRefundTicket_list){
        this.batchDelete(hcpRefundTicket_list);
        return result;
    }


    /**
     * 批量删除退票接口对象ById
     * @param result
     * @param hcpRefundTicket
     * @return
     */
    public Result batchDelHcpRefundTicketByIds(Result result,Integer[] ids){
        int count = 0;
        for(Integer id : ids){
            this.delete(id);
            count ++;
        }
        result.setData(count);
        return result;
    }






    /**
     * 批量更新 退票接口对象
     * @param result
     * @return
     */
    public Result batchUpdateHcpRefundTicket(Result result,List<HcpRefundTicketPo> hcpRefundTicket_list)  {
        this.batchUpdate(hcpRefundTicket_list);
        result.setData(null);
        return result;
    }


    /**
     * 根据ID获取 退票接口对象
     * @param result
     * @return
     */
    public Result getHcpRefundTicketById(Result result,Integer id) {
        HcpRefundTicketPo hcpRefundTicketPo = this.get(id);
        result.setData(hcpRefundTicketPo);
        return result;
    }


    /**
     * 根据条件获取 退票接口对象列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryHcpRefundTicketList(Result result,Page page,HcpRefundTicketPo hcpRefundTicketPo){
        Criteria criteria = this.getSession().createCriteria(HcpRefundTicketPo.class);
        if(null != hcpRefundTicketPo){
            if(hcpRefundTicketPo.getHcpOrderCode()!=null){
                criteria.add(Restrictions.eq("hcpOrderCode", hcpRefundTicketPo.getHcpOrderCode()));
            }
            if(hcpRefundTicketPo.getRefundCode()!=null){
                criteria.add(Restrictions.eq("refundCode", hcpRefundTicketPo.getRefundCode()));
            }
            if(hcpRefundTicketPo.getRealAmount()!=null){
                criteria.add(Restrictions.eq("realAmount", hcpRefundTicketPo.getRealAmount()));
            }
            if(hcpRefundTicketPo.getRefundAmount()!=null){
                criteria.add(Restrictions.eq("refundAmount", hcpRefundTicketPo.getRefundAmount()));
            }
            if(hcpRefundTicketPo.getSupplierRecord()!=null){
                criteria.add(Restrictions.eq("supplierRecord", hcpRefundTicketPo.getSupplierRecord()));
            }
            if(hcpRefundTicketPo.getMyRecord()!=null){
                criteria.add(Restrictions.eq("myRecord", hcpRefundTicketPo.getMyRecord()));
            }
            if(hcpRefundTicketPo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", hcpRefundTicketPo.getCreateTime()));
            }
            if(hcpRefundTicketPo.getRemark()!=null){
                criteria.add(Restrictions.eq("remark", hcpRefundTicketPo.getRemark()));
            }
            if(hcpRefundTicketPo.getRefundStatus()!=null){
                criteria.add(Restrictions.eq("refundStatus", hcpRefundTicketPo.getRefundStatus()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }

    
    
    /**
     * 根据订单号获取退票信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @
     */
    public Result queryByOrderCode(Result result, String orderCode){
        String hql = "from HcpRefundTicketPo where hcpOrderCode = ? ";
        HcpRefundTicketPo chargeInfo = this.findUnique (hql, orderCode);
        result.setData (chargeInfo);
        return result;
    }
    
    
    
    /**
     * 根据退票订单号获取退票信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @
     */
    public Result queryByReturnTicketCode(Result result, String returnTicketCode){
        String hql = "from HcpRefundTicketPo where refundCode = ? ";
        HcpRefundTicketPo chargeInfo = this.findUnique (hql, returnTicketCode);
        result.setData (chargeInfo);
        return result;
    }
    
    
    /**
     * 根据订单号修改退款状态
     * 
     * @param result
     * @param chargeCode
     * @param refundStatus
     * @return
     * @throws Exception
     */
    public Result updateStatusByOrderCode(Result result, String orderCode, Integer refundStatus){
        String hql = "update HcpRefundTicketPo set refundStatus = ? where hcpOrderCode = ? ";
        int ret = this.updateHql (hql, refundStatus, orderCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }

    /**
     * 判断用户是否退票成功
     * @param result
     * @param refundSuccessStatus
     * @param travelTime
     * @param trainCode
     * @param userName
     * @param idCardId
     * @return
     */
    public Result checkUserForBuyTicket(Result result, int refundSuccessStatus, String travelTime, String trainCode, String idCardId){
        StringBuilder sql = new StringBuilder("select t.hcp_order_code from hcp_refund_ticket t " +
                "inner join hcp_order_info o on t.hcp_order_code=o.hcp_order_code " +
                "where t.refund_status='" + refundSuccessStatus + "' " +
                "and o.travel_time='" + travelTime + "' " +
                "and o.train_code='" + trainCode + "' " +
                "and o.id_card_id='" + idCardId + "'");

        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql.toString());
        if(null != sqlQuery && null != sqlQuery.list() && sqlQuery.list().size() > 0){
            result.setData("1");
        } else {
            result.setData("0");
        }
        return result;
    }
    
    
    
}
