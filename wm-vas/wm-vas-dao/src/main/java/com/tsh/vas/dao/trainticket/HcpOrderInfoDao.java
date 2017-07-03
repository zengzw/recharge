package com.tsh.vas.dao.trainticket;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.tsh.vas.model.trainticket.HcpRefundTicketPo;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ChargeInfo;
import com.tsh.vas.model.OrderReport;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.model.trainticket.HcpOrderInfoReports;
import com.tsh.vas.model.trainticket.HcpOrderInfos;
import com.tsh.vas.vo.business.QueryOrderParamVo;
import com.tsh.vas.vo.trainticket.HcpOrderInfoReportsVo;

/**
 * 订单Dao
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Repository
@SuppressWarnings("all")
public class HcpOrderInfoDao extends HibernateDao<HcpOrderInfoPo, Integer> {
    /**
     * 新增订单接口对象
     * @param result
     * @param hcpOrderInfo
     * @return
     */
    public Result addHcpOrderInfo(Result result,HcpOrderInfoPo hcpOrderInfo)throws Exception{
        this.save(hcpOrderInfo);
        result.setData(hcpOrderInfo);
        return result;
    }

    /**
     * 批量删除订单接口对象
     * @param result
     * @param hcpOrderInfo
     * @return
     */
    public Result batchDelHcpOrderInfo(Result result, List<HcpOrderInfoPo> hcpOrderInfo_list)throws Exception{
        this.batchDelete(hcpOrderInfo_list);
        return result;
    }

    /**
     * 批量删除订单接口对象ById
     * @param result
     * @param hcpOrderInfo
     * @return
     */
    public Result batchDelHcpOrderInfoByIds(Result result,Integer[] ids)throws Exception{
        int count = 0;
        for(Integer id : ids){
            this.delete(id);
            count ++;
        }
        result.setData(count);
        return result;
    }


    /**
     * 批量新增订单接口对象
     * @param result
     * @param hcpOrderInfo
     * @return
     */
    public Result batchSaveHcpOrderInfo(Result result, List<HcpOrderInfoPo> hcpOrderInfo_list) throws Exception {
        this.batchSave(hcpOrderInfo_list);
        result.setData(null);
        return result;
    }


    /**
     * 批量更新 订单接口对象
     * @param result
     * @return
     */
    public Result batchUpdateHcpOrderInfo(Result result,List<HcpOrderInfoPo> hcpOrderInfo_list) throws Exception {
        this.batchUpdate(hcpOrderInfo_list);
        result.setData(null);
        return result;
    }



    /**
     * 清空表 订单接口对象
     * @param result
     * @return
     */
    public Result clearHcpOrderInfo(Result result) {
        String sql = " truncate table hcp_order_info ";
        int count = this.getSession().createSQLQuery(sql).executeUpdate();
        result.setData(count);
        return result;
    }


    /**
     * 删除订单接口对象
     * @param id 订单接口对象标识
     * @return
     */
    public Result deleteHcpOrderInfo(Result result, Integer id) throws Exception {
        int count = this.updateHql("delete HcpOrderInfoPo where id=?",id);
        result.setData(count);
        return result;
    }


    /**
     * 根据ID获取 订单接口对象
     * @param result
     * @return
     */
    public Result getHcpOrderInfoById(Result result,Integer id) throws Exception{
        HcpOrderInfoPo hcpOrderInfoPo = this.get(id);
        result.setData(hcpOrderInfoPo);
        return result;
    }

    /**
     * 根据屏端条件查询订单列表
     * @param result
     * @param page
     * @param hcpOrderInfoPo
     * @param statusList
     * @return
     */
    public Result queryHcpOrderInfoListForScreen(Result result,Page page, HcpOrderInfoPo hcpOrderInfoPo,
                                                 List<Integer> statusList, List<String> refundOrderCodeList, String type){
        StringBuilder querySql = new StringBuilder();
        querySql.append("select id, hcp_order_code, "+
                " open_platform_no, "+
                " supplier_order_id, supplier_code, supplier_name, "+
                " business_code, business_name, "+
                " sub_business_code, sub_business_name, "+
                " city, province, country, country_code, country_name, "+
                " mobile, pay_user_name, pay_user_id, "+
                " biz_id, biz_type, "+
                " store_code, store_name, "+
                " costing_amount, original_amount, real_amount, "+
                " create_time, pay_status, "+
                " out_ticktet_time, "+
                " bill_year_month, "+
                " fail_reason, "+
                " store_info, "+
                " lift_coefficient, platform_divided_ratio, area_divided_ratio, area_commission_ratio, store_commission_ratio, "+
                " product_id, "+
                " member_mobile, member_name, "+
                " travel_time, station_start_time, from_station, "+
                " station_arrive_time, arrive_station, "+
                " bx, id_type, id_card_id, user_name, seat_type, train_code, ticket_type, "+
                " train_box, seat_no, "+
                " service_price, "+
                " link_name, link_phone, costtime from hcp_order_info where 1=1  ");

        if(StringUtils.isNotEmpty(hcpOrderInfoPo.getHcpOrderCode())){
            querySql.append("and (hcp_order_code like '%"+hcpOrderInfoPo.getHcpOrderCode()+"%' or mobile = '"+hcpOrderInfoPo.getHcpOrderCode()+"') ");
        }
        if(StringUtils.isNotEmpty(hcpOrderInfoPo.getStoreCode())){
            querySql.append(" and store_code = '"+hcpOrderInfoPo.getStoreCode()+"' ");
        }
        if(null != statusList && !statusList.isEmpty()){
            String listString = "";
            for(Integer status : statusList){
                listString += String.valueOf(status) + ",";
            }
            listString = listString.substring(0, listString.length() -1);
            querySql.append(" and pay_status in ("+listString+")");
        }
        if(null != refundOrderCodeList && !refundOrderCodeList.isEmpty()){
            String listString = "";
            for(String code : refundOrderCodeList){
                listString += "'" + code + "' ,";
            }
            listString = listString.substring(0, listString.length() -1);
            querySql.append(" and hcp_order_code in ("+listString+")");
        }
        if(StringUtils.isNotEmpty(type) && "2".equals(type)){
            querySql.append(" and not exists (select t.hcp_order_code from hcp_refund_ticket t where t.refund_status = '13' and t.hcp_order_code = hcp_order_info.hcp_order_code) ");
        }
        querySql.append(" order by create_time desc");
        int startIndex = (page.getPageNo()-1) * page.getPageSize();
        if(startIndex < 0){
            startIndex = 0;
        }
        querySql.append(" limit "+ startIndex + "," + page.getPageSize());
        SQLQuery sqlQuery = this.getSession().createSQLQuery (querySql.toString()).addEntity (HcpOrderInfoPo.class);
        result.setData (sqlQuery.list ());

        return result;
    }

    /**
     * 根据条件获取 订单接口对象列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryHcpOrderInfoList(Result result,Page page,HcpOrderInfoPo hcpOrderInfoPo){
        Criteria criteria = this.getSession().createCriteria(HcpOrderInfoPo.class);
        if(null != hcpOrderInfoPo){
            if(hcpOrderInfoPo.getHcpOrderCode()!=null){
                criteria.add(Restrictions.eq("hcpOrderCode", hcpOrderInfoPo.getHcpOrderCode()));
            }
            if(hcpOrderInfoPo.getOpenPlatformNo()!=null){
                criteria.add(Restrictions.eq("openPlatformNo", hcpOrderInfoPo.getOpenPlatformNo()));
            }
            if(hcpOrderInfoPo.getSupplierCode()!=null){
                criteria.add(Restrictions.eq("supplierCode", hcpOrderInfoPo.getSupplierCode()));
            }
            if(hcpOrderInfoPo.getSupplierName()!=null){
                criteria.add(Restrictions.eq("supplierName", hcpOrderInfoPo.getSupplierName()));
            }
            if(hcpOrderInfoPo.getBusinessCode()!=null){
                criteria.add(Restrictions.eq("businessCode", hcpOrderInfoPo.getBusinessCode()));
            }
            if(hcpOrderInfoPo.getBusinessName()!=null){
                criteria.add(Restrictions.eq("businessName", hcpOrderInfoPo.getBusinessName()));
            }
            if(hcpOrderInfoPo.getSubBusinessCode()!=null){
                criteria.add(Restrictions.eq("subBusinessCode", hcpOrderInfoPo.getSubBusinessCode()));
            }
            if(hcpOrderInfoPo.getSubBusinessName()!=null){
                criteria.add(Restrictions.eq("subBusinessName", hcpOrderInfoPo.getSubBusinessName()));
            }
            if(hcpOrderInfoPo.getCity()!=null){
                criteria.add(Restrictions.eq("city", hcpOrderInfoPo.getCity()));
            }
            if(hcpOrderInfoPo.getProvince()!=null){
                criteria.add(Restrictions.eq("province", hcpOrderInfoPo.getProvince()));
            }
            if(hcpOrderInfoPo.getCountry()!=null){
                criteria.add(Restrictions.eq("country", hcpOrderInfoPo.getCountry()));
            }
            if(hcpOrderInfoPo.getCountryCode()!=null){
                criteria.add(Restrictions.eq("countryCode", hcpOrderInfoPo.getCountryCode()));
            }
            if(hcpOrderInfoPo.getCountryName()!=null){
                criteria.add(Restrictions.eq("countryName", hcpOrderInfoPo.getCountryName()));
            }
            if(hcpOrderInfoPo.getMobile()!=null){
                criteria.add(Restrictions.eq("mobile", hcpOrderInfoPo.getMobile()));
            }
            if(hcpOrderInfoPo.getPayUserName()!=null){
                criteria.add(Restrictions.eq("payUserName", hcpOrderInfoPo.getPayUserName()));
            }
            if(hcpOrderInfoPo.getPayUserId()!=null){
                criteria.add(Restrictions.eq("payUserId", hcpOrderInfoPo.getPayUserId()));
            }
            if(hcpOrderInfoPo.getBizId()!=null){
                criteria.add(Restrictions.eq("bizId", hcpOrderInfoPo.getBizId()));
            }
            if(hcpOrderInfoPo.getBizType()!=null){
                criteria.add(Restrictions.eq("bizType", hcpOrderInfoPo.getBizType()));
            }
            if(hcpOrderInfoPo.getStoreCode()!=null){
                criteria.add(Restrictions.eq("storeCode", hcpOrderInfoPo.getStoreCode()));
            }
            if(hcpOrderInfoPo.getStoreName()!=null){
                criteria.add(Restrictions.eq("storeName", hcpOrderInfoPo.getStoreName()));
            }
            if(hcpOrderInfoPo.getCostingAmount()!=null){
                criteria.add(Restrictions.eq("costingAmount", hcpOrderInfoPo.getCostingAmount()));
            }
            if(hcpOrderInfoPo.getOriginalAmount()!=null){
                criteria.add(Restrictions.eq("originalAmount", hcpOrderInfoPo.getOriginalAmount()));
            }
            if(hcpOrderInfoPo.getRealAmount()!=null){
                criteria.add(Restrictions.eq("realAmount", hcpOrderInfoPo.getRealAmount()));
            }
            if(hcpOrderInfoPo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", hcpOrderInfoPo.getCreateTime()));
            }
            if(hcpOrderInfoPo.getPayStatus()!=null){
                criteria.add(Restrictions.eq("payStatus", hcpOrderInfoPo.getPayStatus()));
            }
            if(hcpOrderInfoPo.getStoreInfo()!=null){
                criteria.add(Restrictions.eq("storeInfo", hcpOrderInfoPo.getStoreInfo()));
            }
            if(hcpOrderInfoPo.getLiftCoefficient()!=null){
                criteria.add(Restrictions.eq("liftCoefficient", hcpOrderInfoPo.getLiftCoefficient()));
            }
            if(hcpOrderInfoPo.getPlatformDividedRatio()!=null){
                criteria.add(Restrictions.eq("platformDividedRatio", hcpOrderInfoPo.getPlatformDividedRatio()));
            }
            if(hcpOrderInfoPo.getAreaDividedRatio()!=null){
                criteria.add(Restrictions.eq("areaDividedRatio", hcpOrderInfoPo.getAreaDividedRatio()));
            }
            if(hcpOrderInfoPo.getAreaCommissionRatio()!=null){
                criteria.add(Restrictions.eq("areaCommissionRatio", hcpOrderInfoPo.getAreaCommissionRatio()));
            }
            if(hcpOrderInfoPo.getStoreCommissionRatio()!=null){
                criteria.add(Restrictions.eq("storeCommissionRatio", hcpOrderInfoPo.getStoreCommissionRatio()));
            }
            if(hcpOrderInfoPo.getBillYearMonth()!=null){
                criteria.add(Restrictions.eq("billYearMonth", hcpOrderInfoPo.getBillYearMonth()));
            }
            if(hcpOrderInfoPo.getProductId()!=null){
                criteria.add(Restrictions.eq("productId", hcpOrderInfoPo.getProductId()));
            }
            if(hcpOrderInfoPo.getMemberMobile()!=null){
                criteria.add(Restrictions.eq("memberMobile", hcpOrderInfoPo.getMemberMobile()));
            }
            if(hcpOrderInfoPo.getMemberName()!=null){
                criteria.add(Restrictions.eq("memberName", hcpOrderInfoPo.getMemberName()));
            }
            if(hcpOrderInfoPo.getTravelTime()!=null){
                criteria.add(Restrictions.eq("travelTime", hcpOrderInfoPo.getTravelTime()));
            }
            if(hcpOrderInfoPo.getStationStartTime()!=null){
                criteria.add(Restrictions.eq("stationStartTime", hcpOrderInfoPo.getStationStartTime()));
            }
            if(hcpOrderInfoPo.getFromStation()!=null){
                criteria.add(Restrictions.eq("fromStation", hcpOrderInfoPo.getFromStation()));
            }
            if(hcpOrderInfoPo.getStationArriveTime()!=null){
                criteria.add(Restrictions.eq("stationArriveTime", hcpOrderInfoPo.getStationArriveTime()));
            }
            if(hcpOrderInfoPo.getArriveStation()!=null){
                criteria.add(Restrictions.eq("arriveStation", hcpOrderInfoPo.getArriveStation()));
            }
            if(hcpOrderInfoPo.getBx()!=null){
                criteria.add(Restrictions.eq("bx", hcpOrderInfoPo.getBx()));
            }
            if(hcpOrderInfoPo.getIdType()!=null){
                criteria.add(Restrictions.eq("idType", hcpOrderInfoPo.getIdType()));
            }
            if(hcpOrderInfoPo.getIdCardId()!=null){
                criteria.add(Restrictions.eq("idCardId", hcpOrderInfoPo.getIdCardId()));
            }
            if(hcpOrderInfoPo.getUserName()!=null){
                criteria.add(Restrictions.eq("userName", hcpOrderInfoPo.getUserName()));
            }
            if(hcpOrderInfoPo.getSeatType()!=null){
                criteria.add(Restrictions.eq("seatType", hcpOrderInfoPo.getSeatType()));
            }
            if(hcpOrderInfoPo.getTrainCode()!=null){
                criteria.add(Restrictions.eq("trainCode", hcpOrderInfoPo.getTrainCode()));
            }
            if(hcpOrderInfoPo.getServicePrice()!=null){
                criteria.add(Restrictions.eq("servicePrice", hcpOrderInfoPo.getServicePrice()));
            }
            if(hcpOrderInfoPo.getLinkName()!=null){
                criteria.add(Restrictions.eq("linkName", hcpOrderInfoPo.getLinkName()));
            }
            if(hcpOrderInfoPo.getLinkPhone()!=null){
                criteria.add(Restrictions.eq("linkPhone", hcpOrderInfoPo.getLinkPhone()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }

    /**
     * 根据订单号修改支付状态
     * 
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatus(Result result, String orderCode, Integer payStatus){
        String hql = " update HcpOrderInfoPo set payStatus = ? where hcpOrderCode = ? ";
        int ret = this.updateHql (hql, payStatus, orderCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }

    /**
     * 根据订单号修改支付状态
     *
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatusAndFailReason(Result result, String orderCode, Integer payStatus, String failReason){
        String hql = " update HcpOrderInfoPo set payStatus = ?, failReason=? where hcpOrderCode = ? ";
        int ret = this.updateHql (hql, payStatus, failReason, orderCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
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
    public Result updateRefundStatus(Result result, String orderCode, Integer refundStatus) throws Exception {
        String hql = "update HcpOrderInfoPo set refundStatus = ? where chargeCode = ? ";
        int ret = this.updateHql (hql, refundStatus, orderCode);
        if (ret <= 0) {
            result.setData (null);
        }
        result.setData (ret);
        return result;
    }
    
    
    

    /**
     * 根据下单时间，支付状态查找订单记录
     * 
     * @param result
     * @param payStatus
     * @param beforTime
     * @return
     * @throws Exception
     */
    public Result findByPayStatusAndTime(Result result, Integer payStatus, Date beforTime){
        Criteria criteria = createCriteria (HcpOrderInfoPo.class);
        criteria.add (Restrictions.eq ("payStatus", payStatus));
        criteria.add (Restrictions.le ("createTime", beforTime));
        List<HcpOrderInfoPo> orderInfoList = criteria.list ();
        if (orderInfoList.isEmpty ()) {
            result.setData (null);
        } else {
            result.setData (orderInfoList);
        }
        return result;
    }

    
    /**
     *  根据开放平台查找订单记录
     *  
     * @param result
     * @param openPlatformNo
     * @return
     */
    public Result queryByOpenPlatformNo(Result result, String openPlatformNo) {
        String hql = "from HcpOrderInfoPo where openPlatformNo = ? ";
        HcpOrderInfoPo orderInfo = this.findUnique (hql, openPlatformNo);
        result.setData (orderInfo);
        return result;
    }
    
    
    

  /**
   *  查询当前乘车人在同个时间是否已经购买过车
   *  
   * @param result
   * @param fromStation 触发站
   * @param arriveStation 终点站
   * @param travelTime 乘车时间
   * @param idCardId 身份证
   * @return
   */
    public Result queryCurrentDaySameTrainNum(Result result, String trainCode,String travelTime,String idCardId,int payStatus) {
        String hql = "from HcpOrderInfoPo where trainCode = ? and payStatus=? and travelTime=? and idCardId=?";
        HcpOrderInfoPo orderInfo = this.findUnique (hql, trainCode,payStatus,travelTime,idCardId);
        result.setData (orderInfo);
        return result;
    }
    
    
    /**
     * 根据订单号获取退款信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryByOrderCode(Result result, String orderCode){
        String hql = "from HcpOrderInfoPo where hcpOrderCode = ? ";
        HcpOrderInfoPo chargeInfo = this.findUnique (hql, orderCode);
        result.setData (chargeInfo);
        return result;
    }
    
    
    
    
    /**
     * 火车票订单报表导出(平台+县域)
     * @param result
     * @param queryOrderParamVo
     * @return
     */
    public Result getHcpOrderInfoReportsPageExport(Result result, HcpOrderInfoReportsVo hcpOrderInfoReportsVo) throws Exception {
        StringBuffer sqlBase = new StringBuffer ();
        StringBuffer sqlcommon = new StringBuffer ();
        
        sqlBase.append (" select hoi.supplier_order_id as supplier_order_id, hoi.id as id, hoi.hcp_order_code as hcp_order_code,hoi.open_platform_no as open_platform_no,hoi.supplier_name as supplier_name, ")
        		.append(" hoi.platform_divided_ratio as platform_divided_ratio,hoi.area_divided_ratio as area_divided_ratio,hoi.country_code as country_code, ")
        		.append(" hoi.user_name as user_name,hoi.id_card_id as id_card_id,hoi.pay_status as pay_status,hoi.create_time as create_time, ")
				.append(" hoi.link_name as link_name,hoi.link_phone as link_phone,hoi.store_name as store_name,hra.refund_amount_code as refund_amount_code,  ")
				.append(" hoi.original_amount as original_amount,hoi.costing_amount as costing_amount,hoi.area_commission_ratio as area_commission_ratio, ")
				.append(" hoi.store_commission_ratio as store_commission_ratio,hra.hcp_refund_ticket_code as hcp_refund_ticket_code,hra.real_amount as real_amount,hra.status as status, ")
				.append(" hrt.refund_status as refund_status ")
				.append(" from hcp_order_info hoi LEFT JOIN hcp_refund_amount hra on hoi.hcp_order_code=hra.hcp_order_code LEFT JOIN hcp_refund_ticket hrt on hoi.hcp_order_code = hrt.hcp_order_code ")
        		.append(" where 1 = 1 ");
        //订单编号
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getHcpOrderCode())) {
        	sqlcommon.append (" and hoi.hcp_order_code like'%").append(hcpOrderInfoReportsVo.getHcpOrderCode().trim ()).append ("%' ");
        }
        //供应商订单编号
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getOpenPlatformNo())) {
        	sqlcommon.append (" and hoi.open_platform_no like'%").append(hcpOrderInfoReportsVo.getOpenPlatformNo().trim ()).append ("%' ");
        }
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSupplierOrderId())) {
        	sqlcommon.append (" and hoi.supplier_order_id like'%").append(hcpOrderInfoReportsVo.getSupplierOrderId().trim ()).append ("%' ");
        }
        //服务供应商
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSupplierName())) {
        	sqlcommon.append (" and hoi.supplier_name like'%").append(hcpOrderInfoReportsVo.getSupplierName().trim()).append ("%' ");
        }
        //支付支付时间-开始时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getStartDate())) {
        	sqlcommon.append (" and hoi.create_time >= '").append(hcpOrderInfoReportsVo.getStartDate().trim () + " 00:00:00").append ("'");
        }
        //支付时间-结束时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getEndDate())) {
        	sqlcommon.append (" and hoi.create_time <= '").append(hcpOrderInfoReportsVo.getEndDate().trim () + " 23:59:59").append ("'");
        }
        //乘车人
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getUserName())) {
        	sqlcommon.append (" and hoi.user_name like '%").append(hcpOrderInfoReportsVo.getUserName().trim ()).append ("%'");
        }
        //乘车人身份证
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getIdCardId())) {
        	sqlcommon.append (" and hoi.id_card_id like '%").append(hcpOrderInfoReportsVo.getIdCardId().trim ()).append ("%'");
        }
        //购票联系人姓名
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getLinkName())) {
        	sqlcommon.append (" and hoi.link_name like '%").append(hcpOrderInfoReportsVo.getLinkName().trim ()).append ("%'");
        }
        //购票联系人电话
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getLinkPhone())) {
        	sqlcommon.append (" and hoi.link_phone like '%").append(hcpOrderInfoReportsVo.getLinkPhone().trim ()).append ("%'");
        }
        //订单状态
        if (null != hcpOrderInfoReportsVo.getPayStatus() && -1 != hcpOrderInfoReportsVo.getPayStatus()) {
            String inString = String.valueOf (hcpOrderInfoReportsVo.getPayStatus());
            sqlcommon.append (" and hoi.pay_status in( ").append (inString).append (")");
        }
        //省
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getProvince())) {
        	sqlcommon.append (" and hoi.province = '").append(hcpOrderInfoReportsVo.getProvince().trim ()).append ("'");
        }
        //市
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCity())) {
        	sqlcommon.append (" and hoi.city = '").append(hcpOrderInfoReportsVo.getCity().trim ()).append ("'");
        }
        //县域中心
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCountryName())) {
        	sqlcommon.append (" and hoi.country_name = '").append(hcpOrderInfoReportsVo.getCountryName().trim ()).append ("'");
        }
        //网点名称
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getStoreName())) {
        	sqlcommon.append (" and hoi.store_name = '").append(hcpOrderInfoReportsVo.getStoreName().trim ()).append ("'");
        }
        //退款状态
        if (hcpOrderInfoReportsVo.getStatus()!= null && !hcpOrderInfoReportsVo.getStatus().equals (-1)) {
        	sqlcommon.append (" and hra.status = ").append (hcpOrderInfoReportsVo.getStatus());
        }
        //县域id
        if(StringUtils.isNotBlank(hcpOrderInfoReportsVo.getCountryCode())){
        	sqlcommon.append (" and hoi.country_code = ").append (hcpOrderInfoReportsVo.getCountryCode());
        }
        
        StringBuffer ss = new StringBuffer();
        ss.append(" order by hoi.id desc ");
        
        Session session = this.getSession ();
        String selectSql = sqlBase.toString() + sqlcommon.toString() + ss.toString();
        SQLQuery sqlQuery = session.createSQLQuery (selectSql).addEntity (HcpOrderInfoReports.class);
        result.setData (sqlQuery.list ());
        return result;
    }
    
    /**
     * 火车票订单报表(平台+县域)
     * @param result
     * @param queryOrderParamVo
     * @return
     */
    public Result getHcpOrderInfoReportsPage(Result result, HcpOrderInfoReportsVo hcpOrderInfoReportsVo) throws Exception {
        StringBuffer sqlBase = new StringBuffer ();
        StringBuffer sqlcommon = new StringBuffer ();
        
        sqlBase.append (" select hoi.supplier_order_id as supplier_order_id,hoi.id as id, hoi.hcp_order_code as hcp_order_code,hoi.open_platform_no as open_platform_no,hoi.supplier_name as supplier_name, ")
        		.append(" hoi.platform_divided_ratio as platform_divided_ratio,hoi.area_divided_ratio as area_divided_ratio,hoi.country_code as country_code, ")
        		.append(" hoi.user_name as user_name,hoi.id_card_id as id_card_id,hoi.pay_status as pay_status,hoi.create_time as create_time, ")
				.append(" hoi.link_name as link_name,hoi.link_phone as link_phone,hoi.store_name as store_name,hra.refund_amount_code as refund_amount_code,  ")
				.append(" hoi.original_amount as original_amount,hoi.costing_amount as costing_amount,hoi.area_commission_ratio as area_commission_ratio, ")
				.append(" hoi.store_commission_ratio as store_commission_ratio,hra.hcp_refund_ticket_code as hcp_refund_ticket_code,hra.real_amount as real_amount,hra.status as status, ")
				.append(" hrt.refund_status as refund_status ")
				.append(" from hcp_order_info hoi LEFT JOIN hcp_refund_amount hra on hoi.hcp_order_code=hra.hcp_order_code LEFT JOIN hcp_refund_ticket hrt on hoi.hcp_order_code = hrt.hcp_order_code ")
        		.append(" where 1 = 1 ");
        //订单编号
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getHcpOrderCode())) {
        	sqlcommon.append (" and hoi.hcp_order_code like'%").append(hcpOrderInfoReportsVo.getHcpOrderCode().trim ()).append ("%' ");
        }
        //供应商订单编号
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getOpenPlatformNo())) {
        	sqlcommon.append (" and hoi.open_platform_no like'%").append(hcpOrderInfoReportsVo.getOpenPlatformNo().trim ()).append ("%' ");
        }
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSupplierOrderId())) {
        	sqlcommon.append (" and hoi.supplier_order_id like'%").append(hcpOrderInfoReportsVo.getSupplierOrderId().trim ()).append ("%' ");
        }
        //服务供应商
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSupplierName())) {
        	sqlcommon.append (" and hoi.supplier_name like'%").append(hcpOrderInfoReportsVo.getSupplierName().trim()).append ("%' ");
        }
        //支付支付时间-开始时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getStartDate())) {
        	sqlcommon.append (" and hoi.create_time >= '").append(hcpOrderInfoReportsVo.getStartDate().trim () + " 00:00:00").append ("'");
        }
        //支付时间-结束时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getEndDate())) {
        	sqlcommon.append (" and hoi.create_time <= '").append(hcpOrderInfoReportsVo.getEndDate().trim () + " 23:59:59").append ("'");
        }
        //乘车人
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getUserName())) {
        	sqlcommon.append (" and hoi.user_name like '%").append(hcpOrderInfoReportsVo.getUserName().trim ()).append ("%'");
        }
        //乘车人身份证
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getIdCardId())) {
        	sqlcommon.append (" and hoi.id_card_id like '%").append(hcpOrderInfoReportsVo.getIdCardId().trim ()).append ("%'");
        }
        //购票联系人姓名
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getLinkName())) {
        	sqlcommon.append (" and hoi.link_name like '%").append(hcpOrderInfoReportsVo.getLinkName().trim ()).append ("%'");
        }
        //购票联系人电话
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getLinkPhone())) {
        	sqlcommon.append (" and hoi.link_phone like '%").append(hcpOrderInfoReportsVo.getLinkPhone().trim ()).append ("%'");
        }
        //订单状态
        if (null != hcpOrderInfoReportsVo.getPayStatus() && -1 != hcpOrderInfoReportsVo.getPayStatus()) {
            String inString = String.valueOf (hcpOrderInfoReportsVo.getPayStatus());
            sqlcommon.append (" and hoi.pay_status in( ").append (inString).append (")");
        }
        //省
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getProvince())) {
        	sqlcommon.append (" and hoi.province = '").append(hcpOrderInfoReportsVo.getProvince().trim ()).append ("'");
        }
        //市
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCity())) {
        	sqlcommon.append (" and hoi.city = '").append(hcpOrderInfoReportsVo.getCity().trim ()).append ("'");
        }
        //县域中心
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCountryName())) {
        	sqlcommon.append (" and hoi.country_name = '").append(hcpOrderInfoReportsVo.getCountryName().trim ()).append ("'");
        }
        //网点名称
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getStoreName())) {
        	sqlcommon.append (" and hoi.store_name = '").append(hcpOrderInfoReportsVo.getStoreName().trim ()).append ("'");
        }
        //退款状态
        if (hcpOrderInfoReportsVo.getStatus()!= null && !hcpOrderInfoReportsVo.getStatus().equals (-1)) {
        	sqlcommon.append (" and hra.status = ").append (hcpOrderInfoReportsVo.getStatus());
        }
        //县域id
        if(StringUtils.isNotBlank(hcpOrderInfoReportsVo.getCountryCode())){
        	sqlcommon.append (" and hoi.country_code = ").append (hcpOrderInfoReportsVo.getCountryCode());
        }
        
        StringBuffer ss = new StringBuffer();
        ss.append(" order by hoi.id desc ")
        .append(" limit " + hcpOrderInfoReportsVo.getPage() + " ," + hcpOrderInfoReportsVo.getRows());
        
        Session session = this.getSession ();
        String selectSql = sqlBase.toString() + sqlcommon.toString() + ss.toString();
        SQLQuery sqlQuery = session.createSQLQuery (selectSql).addEntity (HcpOrderInfoReports.class);
        result.setData (sqlQuery.list ());
        String sqlCount = " select count(*) as id from hcp_order_info hoi LEFT JOIN hcp_refund_amount hra on hoi.hcp_order_code=hra.hcp_order_code LEFT JOIN hcp_refund_ticket hrt ON hoi.hcp_order_code = hrt.hcp_order_code  where 1=1 " + sqlcommon.toString();
        
        BigInteger total = (BigInteger) session.createSQLQuery(sqlCount).uniqueResult();
        
        Pagination pagination = new Pagination();
       
        pagination.setRows(sqlQuery.list ());
        pagination.setTotal(total.longValue());
        result.setData(pagination);
        return result;
    }

    /**
     * 火车票订单导出(平台+县域)
     * @param result
     * @param queryOrderParamVo
     * @return
     */
    public Result getHcpOrderInfosPageExport(Result result, HcpOrderInfoReportsVo hcpOrderInfoReportsVo) throws Exception {
        StringBuffer sqlBase = new StringBuffer ();
        StringBuffer sqlcommon = new StringBuffer ();
        
        sqlBase.append (" SELECT hoi.supplier_order_id as supplier_order_id," +
                "hoi.travel_time as travel_time, " +
                "hoi.id AS id,hoi.hcp_order_code AS hcp_order_code," +
                "hoi.open_platform_no AS open_platform_no," +
                "hoi.train_code AS train_code, ")
        		.append(" hoi.station_start_time AS station_start_time," +
                        "hoi.from_station AS from_station," +
                        "hoi.arrive_station AS arrive_station," +
                        "hoi.seat_type AS seat_type, ")
        		.append(" hoi.ticket_type AS ticket_type," +
                        "hoi.user_name AS user_name," +
                        "hoi.id_card_id AS id_card_id," +
                        "hoi.area_commission_ratio as area_commission_ratio, ")
        		.append(" hoi.area_divided_ratio as area_divided_ratio," +
                        "hra.hcp_refund_ticket_code as hcp_refund_ticket_code," +
                        "hoi.platform_divided_ratio as platform_divided_ratio, ")
        		.append(" hra.real_amount as real_amount," +
                        "hoi.store_commission_ratio as store_commission_ratio," +
                        "hoi.province as province,hoi.city as city, " +
                        "hoi.country_name as country_name, ")
        		.append(" hoi.pay_status AS pay_status," +
                        "hra. STATUS AS STATUS," +
                        "hoi.create_time AS create_time," +
                        "hoi.out_ticktet_time AS out_ticktet_time," +
                        "hrt.create_time AS refund_ticket_create_time, ")
				.append(" hoi.original_amount AS original_amount," +
                        "hoi.costing_amount AS costing_amount," +
                        "hoi.link_name AS link_name," +
                        "hoi.link_phone AS link_phone, ")
				.append(" hoi.store_name AS store_name," +
                        "hoi.supplier_name AS supplier_name," +
                        "hoi.province AS province," +
                        "hoi.city AS city," +
                        "hoi.country_name AS country_name, " +
                        "hrt.refund_status as refund_status ")
				.append(" FROM hcp_order_info hoi LEFT JOIN hcp_refund_amount hra ON hoi.hcp_order_code = hra.hcp_order_code LEFT JOIN hcp_refund_ticket hrt ON hoi.hcp_order_code = hrt.hcp_order_code ")
				.append(" where 1 = 1 ");
        //订单编号
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getHcpOrderCode())) {
        	sqlcommon.append (" and hoi.hcp_order_code like'%").append(hcpOrderInfoReportsVo.getHcpOrderCode().trim ()).append ("%' ");
        }
        //供应商订单编号
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getOpenPlatformNo())) {
        	sqlcommon.append (" and hoi.open_platform_no like'%").append(hcpOrderInfoReportsVo.getOpenPlatformNo().trim ()).append ("%' ");
        }
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSupplierOrderId())) {
        	sqlcommon.append (" and hoi.supplier_order_id like'%").append(hcpOrderInfoReportsVo.getSupplierOrderId().trim ()).append ("%' ");
        }
        //服务供应商
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSupplierName())) {
        	sqlcommon.append (" and hoi.supplier_name like'%").append(hcpOrderInfoReportsVo.getSupplierName().trim()).append ("%' ");
        }
        //乘车人
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getUserName())) {
        	sqlcommon.append (" and hoi.user_name like '%").append(hcpOrderInfoReportsVo.getUserName().trim ()).append ("%'");
        }
        //乘车人身份证
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getIdCardId())) {
        	sqlcommon.append (" and hoi.id_card_id like '%").append(hcpOrderInfoReportsVo.getIdCardId().trim ()).append ("%'");
        }
        //省
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getProvince())) {
        	sqlcommon.append (" and hoi.province = '").append(hcpOrderInfoReportsVo.getProvince().trim ()).append ("'");
        }
        //市
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCity())) {
        	sqlcommon.append (" and hoi.city = '").append(hcpOrderInfoReportsVo.getCity().trim ()).append ("'");
        }
        //县域中心
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCountryName())) {
        	sqlcommon.append (" and hoi.country_name = '").append(hcpOrderInfoReportsVo.getCountryName().trim ()).append ("'");
        }
        //网点名称
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getStoreName())) {
        	sqlcommon.append (" and hoi.store_name = '").append(hcpOrderInfoReportsVo.getStoreName().trim ()).append ("'");
        }
        
        
        //下单时间-开始时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getStartDate())) {
        	sqlcommon.append (" and hoi.create_time >= '").append(hcpOrderInfoReportsVo.getStartDate().trim () + " 00:00:00").append ("'");
        }
        //下单时间-结束时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getEndDate())) {
        	sqlcommon.append (" and hoi.create_time <= '").append(hcpOrderInfoReportsVo.getEndDate().trim () + " 23:59:59").append ("'");
        }
        
        //出票时间-开始时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCpStartDate())) {
        	sqlcommon.append (" and hoi.out_ticktet_time >= '").append(hcpOrderInfoReportsVo.getCpStartDate().trim() + " 00:00:00").append ("'");
        }
        //出票时间-结束时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCpEndDate())) {
        	sqlcommon.append (" and hoi.out_ticktet_time <= '").append(hcpOrderInfoReportsVo.getCpEndDate().trim() + " 23:59:59").append ("'");
        }
        
        
        //申请退票时间-开始时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSqStartDate())) {
        	sqlcommon.append (" and hrt.create_time >= '").append(hcpOrderInfoReportsVo.getSqStartDate().trim() + " 00:00:00").append ("'");
        }
        //申请退票时间-结束时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSqEndDate())) {
        	sqlcommon.append (" and hrt.create_time <= '").append(hcpOrderInfoReportsVo.getSqEndDate().trim() + " 23:59:59").append ("'");
        }
        
        //订单状态
        if (null != hcpOrderInfoReportsVo.getPayStatus() && -1 != hcpOrderInfoReportsVo.getPayStatus()) {
            String inString = String.valueOf (hcpOrderInfoReportsVo.getPayStatus());
            sqlcommon.append (" and hoi.pay_status in( ").append (inString).append (")");
        }
        //退款状态
        if (hcpOrderInfoReportsVo.getStatus()!= null && !hcpOrderInfoReportsVo.getStatus().equals (-1)) {
        	sqlcommon.append (" and hra.status = ").append (hcpOrderInfoReportsVo.getStatus());
        }
        //购票联系人姓名
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getLinkName())) {
        	sqlcommon.append (" and hoi.link_name like '%").append(hcpOrderInfoReportsVo.getLinkName().trim ()).append ("%'");
        }
        //购票联系人电话
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getLinkPhone())) {
        	sqlcommon.append (" and hoi.link_phone like '%").append(hcpOrderInfoReportsVo.getLinkPhone().trim ()).append ("%'");
        }
        //县域id
        if(StringUtils.isNotBlank(hcpOrderInfoReportsVo.getCountryCode())){
        	sqlcommon.append (" and hoi.country_code = ").append (hcpOrderInfoReportsVo.getCountryCode());
        }
        
        StringBuffer ss = new StringBuffer();
        ss.append(" order by hoi.id desc ");
        
        Session session = this.getSession ();
        String selectSql = sqlBase.toString() + sqlcommon.toString() + ss.toString();
        SQLQuery sqlQuery = session.createSQLQuery (selectSql).addEntity (HcpOrderInfos.class);
        result.setData (sqlQuery.list ());
        return result;
    }
    
    /**
     * 火车票订单(平台+县域)
     * @param result
     * @param queryOrderParamVo
     * @return
     */
    public Result getHcpOrderInfosPage(Result result, HcpOrderInfoReportsVo hcpOrderInfoReportsVo) throws Exception {
        StringBuffer sqlBase = new StringBuffer ();
        StringBuffer sqlcommon = new StringBuffer ();
        
        sqlBase.append (" SELECT hoi.supplier_order_id as supplier_order_id,hoi.travel_time as travel_time, hoi.id AS id,hoi.hcp_order_code AS hcp_order_code,hoi.open_platform_no AS open_platform_no,hoi.train_code AS train_code, ")
        		.append(" hoi.station_start_time AS station_start_time,hoi.from_station AS from_station,hoi.arrive_station AS arrive_station,hoi.seat_type AS seat_type, ")
        		.append(" hoi.ticket_type AS ticket_type,hoi.user_name AS user_name,hoi.id_card_id AS id_card_id,hoi.area_commission_ratio as area_commission_ratio, ")
        		.append(" hoi.area_divided_ratio as area_divided_ratio,hra.hcp_refund_ticket_code as hcp_refund_ticket_code,hoi.platform_divided_ratio as platform_divided_ratio, ")
        		.append(" hra.real_amount as real_amount,hoi.store_commission_ratio as store_commission_ratio,hoi.province as province,hoi.city as city, hoi.country_name as country_name, ")
        		.append(" hoi.pay_status AS pay_status,hra. STATUS AS STATUS,hoi.create_time AS create_time,hoi.out_ticktet_time AS out_ticktet_time,hra.create_time AS refund_ticket_create_time, ")
				.append(" hoi.original_amount AS original_amount,hoi.costing_amount AS costing_amount,hoi.link_name AS link_name,hoi.link_phone AS link_phone, ")
				.append(" hoi.store_name AS store_name,hoi.supplier_name AS supplier_name,hoi.province AS province,hoi.city AS city,hoi.country_name AS country_name,hrt.refund_status as refund_status ")
				.append(" FROM hcp_order_info hoi LEFT JOIN hcp_refund_amount hra ON hoi.hcp_order_code = hra.hcp_order_code LEFT JOIN hcp_refund_ticket hrt ON hoi.hcp_order_code = hrt.hcp_order_code ")
				.append(" where 1 = 1 ");
        //订单编号
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getHcpOrderCode())) {
        	sqlcommon.append (" and hoi.hcp_order_code like'%").append(hcpOrderInfoReportsVo.getHcpOrderCode().trim ()).append ("%' ");
        }
        //供应商订单编号
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getOpenPlatformNo())) {
        	sqlcommon.append (" and hoi.open_platform_no like'%").append(hcpOrderInfoReportsVo.getOpenPlatformNo().trim ()).append ("%' ");
        }
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSupplierOrderId())) {
        	sqlcommon.append (" and hoi.supplier_order_id like'%").append(hcpOrderInfoReportsVo.getSupplierOrderId().trim ()).append ("%' ");
        }
        //服务供应商
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSupplierName())) {
        	sqlcommon.append (" and hoi.supplier_name like'%").append(hcpOrderInfoReportsVo.getSupplierName().trim()).append ("%' ");
        }
        //乘车人
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getUserName())) {
        	sqlcommon.append (" and hoi.user_name like '%").append(hcpOrderInfoReportsVo.getUserName().trim ()).append ("%'");
        }
        //乘车人身份证
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getIdCardId())) {
        	sqlcommon.append (" and hoi.id_card_id like '%").append(hcpOrderInfoReportsVo.getIdCardId().trim ()).append ("%'");
        }
        //省
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getProvince())) {
        	sqlcommon.append (" and hoi.province = '").append(hcpOrderInfoReportsVo.getProvince().trim ()).append ("'");
        }
        //市
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCity())) {
        	sqlcommon.append (" and hoi.city = '").append(hcpOrderInfoReportsVo.getCity().trim ()).append ("'");
        }
        //县域中心
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCountryName())) {
        	sqlcommon.append (" and hoi.country_name = '").append(hcpOrderInfoReportsVo.getCountryName().trim ()).append ("'");
        }
        //网点名称
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getStoreName())) {
        	sqlcommon.append (" and hoi.store_name = '").append(hcpOrderInfoReportsVo.getStoreName().trim ()).append ("'");
        }
        
        
        //下单时间-开始时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getStartDate())) {
        	sqlcommon.append (" and hoi.create_time >= '").append(hcpOrderInfoReportsVo.getStartDate().trim () + " 00:00:00").append ("'");
        }
        //下单时间-结束时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getEndDate())) {
        	sqlcommon.append (" and hoi.create_time <= '").append(hcpOrderInfoReportsVo.getEndDate().trim () + " 23:59:59").append ("'");
        }
        
        //出票时间-开始时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCpStartDate())) {
        	sqlcommon.append (" and hoi.out_ticktet_time >= '").append(hcpOrderInfoReportsVo.getCpStartDate().trim () + " 00:00:00").append ("'");
        }
        //出票时间-结束时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getCpEndDate())) {
        	sqlcommon.append (" and hoi.out_ticktet_time <= '").append(hcpOrderInfoReportsVo.getCpEndDate().trim () + " 23:59:59").append ("'");
        }
        
        
        //申请退票时间-开始时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSqStartDate())) {
        	sqlcommon.append (" and hrt.create_time >= '").append(hcpOrderInfoReportsVo.getSqStartDate().trim () + " 00:00:00").append ("'");
        }
        //申请退票时间-结束时间
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getSqEndDate())) {
        	sqlcommon.append (" and hrt.create_time <= '").append(hcpOrderInfoReportsVo.getSqEndDate().trim () + " 23:59:59").append ("'");
        }
        
        //订单状态
        if (null != hcpOrderInfoReportsVo.getPayStatus() && -1 != hcpOrderInfoReportsVo.getPayStatus()) {
            String inString = String.valueOf (hcpOrderInfoReportsVo.getPayStatus());
            sqlcommon.append (" and hoi.pay_status in( ").append (inString).append (")");
        }
        //退款状态
        if (hcpOrderInfoReportsVo.getStatus()!= null && !hcpOrderInfoReportsVo.getStatus().equals (-1)) {
        	sqlcommon.append (" and hra.status = ").append (hcpOrderInfoReportsVo.getStatus());
        }
        //购票联系人姓名
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getLinkName())) {
        	sqlcommon.append (" and hoi.link_name like '%").append(hcpOrderInfoReportsVo.getLinkName().trim ()).append ("%'");
        }
        //购票联系人电话
        if (StringUtils.isNotBlank (hcpOrderInfoReportsVo.getLinkPhone())) {
        	sqlcommon.append (" and hoi.link_phone like '%").append(hcpOrderInfoReportsVo.getLinkPhone().trim ()).append ("%'");
        }
        //县域id
        if(StringUtils.isNotBlank(hcpOrderInfoReportsVo.getCountryCode())){
        	sqlcommon.append (" and hoi.country_code = ").append (hcpOrderInfoReportsVo.getCountryCode());
        }
        
        StringBuffer ss = new StringBuffer();
        ss.append(" order by hoi.id desc ")
        .append(" limit " + hcpOrderInfoReportsVo.getPage() + " ," + hcpOrderInfoReportsVo.getRows());
        
        Session session = this.getSession ();
        String selectSql = sqlBase.toString() + sqlcommon.toString() + ss.toString();
        SQLQuery sqlQuery = session.createSQLQuery (selectSql).addEntity (HcpOrderInfos.class);
        result.setData (sqlQuery.list ());
        String sqlCount = " select count(*) as id FROM hcp_order_info hoi LEFT JOIN hcp_refund_amount hra ON hoi.hcp_order_code = hra.hcp_order_code LEFT JOIN hcp_refund_ticket hrt ON hoi.hcp_order_code = hrt.hcp_order_code where 1=1 " + sqlcommon.toString();
        
        BigInteger total = (BigInteger) session.createSQLQuery(sqlCount).uniqueResult();
        
        Pagination pagination = new Pagination();
       
        pagination.setRows(sqlQuery.list ());
        pagination.setTotal(total.longValue());
        result.setData(pagination);
        return result;
    }
    /**
     * 火车出发前一天短信提醒乘车人
     * @return
     */
	public List<HcpOrderInfoPo> getStartToRemindOrder() {
		String now = DateUtil.getTime(new Date(), "yyyy-MM-dd HH");
		String start = new StringBuffer().append(now).append(" 00:00:00").toString();
		String end = new StringBuffer().append(now).append(" 23:59:59").toString();
		String hql = "from HcpOrderInfoPo where 1 = 1 and stationStartTime >= ? and stationStartTime <= ?";
		List<HcpOrderInfoPo> hcpOrderInfoPos = this.find(hql, start, end);
		return hcpOrderInfoPos;
	}

}
