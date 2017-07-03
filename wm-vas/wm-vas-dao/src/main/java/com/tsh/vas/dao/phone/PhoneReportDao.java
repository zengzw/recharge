package com.tsh.vas.dao.phone;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneReportPo;
import com.tsh.vas.model.phone.PhoneServiceProviderPo;
import com.tsh.vas.model.trainticket.HcpOrderInfoReports;
import com.tsh.vas.model.trainticket.HcpOrderInfos;
import com.tsh.vas.vo.phone.PhoneReportVo;
import com.tsh.vas.vo.trainticket.HcpOrderInfoReportsVo;
@Repository
@SuppressWarnings("all")
public class PhoneReportDao extends HibernateDao<PhoneServiceProviderPo, Long> {
	/**
     * 话费表格查询
     * @param result
     * @param queryOrderParamVo
     * @return
     */
    public Result getPhoneOrderInfoReportsPage(Result result, PhoneReportVo phoneReportVo) throws Exception {
        StringBuffer sqlBase = new StringBuffer ();
        StringBuffer sqlcommon = new StringBuffer ();
        
        sqlBase.append (" select poi.phone_order_code as phone_order_code,poi.id as id, ")
                .append(" poi.sources as sources, ")
        		.append(" poi.supplier_order_id as supplier_order_id, ")
        		.append(" poi.supplier_name as supplier_name, ")
        		.append(" poi.open_platform_no as open_platform_no, ")
				.append(" poi.member_mobile as member_mobile, ")
				.append(" poi.pay_status as pay_status, ")
                .append(" poi.recharge_phone as recharge_phone, ")
                .append(" poi.recharge_success_time as recharge_success_time, ")
				.append(" poi.create_time as create_time, ")
				.append(" poi.pay_succss_time as pay_succss_time, ")
				.append(" poi.link_name as pay_user_name, ")
				.append(" poi.link_phone as mobile, ")
                .append(" poi.country_name as country_name, ")
                .append(" poi.country_code as country_code, ")
				.append(" poi.store_name as store_name, ")
                .append(" poi.store_code as store_code, ")
                .append(" poi.store_no as store_no, ")
				.append(" poi.original_amount as original_amount, ")
                .append(" poi.sale_amount as sale_amount, ")
				.append(" poi.real_amount as real_amount, ")
				.append(" poi.costing_amount as costing_amount, ")
				.append(" pra.status as status, ")
				.append(" pra.real_amount as pra_real_amount, ")
				.append(" pra.refund_amount_code as refund_amount_code, ")
				.append(" poi.platform_divided_ratio as platform_divided_ratio, ")
				.append(" poi.area_divided_ratio as area_divided_ratio, ")
				.append(" poi.area_commission_ratio as area_commission_ratio, ")
				.append(" poi.store_commission_ratio as store_commission_ratio ")
				.append(" from phone_order_info poi LEFT JOIN phone_refund_amount pra on poi.phone_order_code = pra.phone_order_code ")
        		.append(" where 1 = 1 ");
        //订单编号
        if (StringUtils.isNotBlank (phoneReportVo.getPhoneOrderCode())) {
        	sqlcommon.append (" and poi.phone_order_code like'%").append(phoneReportVo.getPhoneOrderCode().trim ()).append ("%' ");
        }
        //供应商订单编号
        if (StringUtils.isNotBlank (phoneReportVo.getSupplierOrderId())) {
        	sqlcommon.append (" and poi.supplier_order_id like'%").append(phoneReportVo.getSupplierOrderId().trim ()).append ("%' ");
        }
        //服务供应商
        if (StringUtils.isNotBlank (phoneReportVo.getSupplierName())) {
        	sqlcommon.append (" and poi.supplier_name like'%").append(phoneReportVo.getSupplierName().trim ()).append ("%' ");
        }
        //支付支付时间-开始时间
        if (StringUtils.isNotBlank (phoneReportVo.getStartDate())) {
        	sqlcommon.append (" and poi.create_time >= '").append(phoneReportVo.getStartDate().trim () + " 00:00:00").append ("'");
        }
        //支付时间-结束时间
        if (StringUtils.isNotBlank (phoneReportVo.getEndDate())) {
        	sqlcommon.append (" and poi.create_time <= '").append(phoneReportVo.getEndDate().trim () + " 23:59:59").append ("'");
        }
        //充值手机号
        if (StringUtils.isNotBlank (phoneReportVo.getMemberMobile())) {
        	sqlcommon.append (" and poi.recharge_phone like '%").append(phoneReportVo.getMemberMobile().trim ()).append ("%'");
        }
        //充值联系人姓名
        if (StringUtils.isNotBlank (phoneReportVo.getPayUserName())) {
        	sqlcommon.append (" and poi.link_name like '%").append(phoneReportVo.getPayUserName().trim ()).append ("%'");
        }
        //充值联系人电话
        if (StringUtils.isNotBlank (phoneReportVo.getMobile())) {
        	sqlcommon.append (" and poi.link_phone like '%").append(phoneReportVo.getMobile().trim ()).append ("%'");
        }
        //订单状态
        if (null != phoneReportVo.getPayStatus() && !"-1".equals(phoneReportVo.getPayStatus())) {
            String inString = phoneReportVo.getPayStatus();
            sqlcommon.append (" and poi.pay_status in( ").append (inString).append (")");
        }
        //省
        if (StringUtils.isNotBlank (phoneReportVo.getProvince())) {
        	sqlcommon.append (" and poi.province = '").append(phoneReportVo.getProvince().trim ()).append ("'");
        }
        //市
        if (StringUtils.isNotBlank (phoneReportVo.getCity())) {
        	sqlcommon.append (" and poi.city = '").append(phoneReportVo.getCity().trim ()).append ("'");
        }
        //县域中心
        if (StringUtils.isNotBlank (phoneReportVo.getCountryCode()) && !"-1".equals(phoneReportVo.getCountryCode())) {
        	sqlcommon.append (" and poi.country_code = '").append(phoneReportVo.getCountryCode().trim ()).append ("'");
        }
        //网点名称
        if (StringUtils.isNotBlank (phoneReportVo.getStoreName())) {
        	sqlcommon.append (" and poi.store_name = '").append(phoneReportVo.getStoreName().trim ()).append ("'");
        }
        //退款状态

        if (phoneReportVo.getRefundStatus()!= null && !phoneReportVo.getRefundStatus().equals ("-1")) {
        	sqlcommon.append (" and pra.status = ").append (phoneReportVo.getRefundStatus());
        }
        
        if(StringUtils.isNotBlank(phoneReportVo.getStoreCode())){
            sqlcommon.append (" and poi.store_code = '").append (phoneReportVo.getStoreCode()).append ("'");
        }

        if(null != phoneReportVo.getSaleAmount() &&
                !"-1".equals(phoneReportVo.getSaleAmount()) &&
                !"请选择".equals(phoneReportVo.getSaleAmount())){
            sqlcommon.append (" and poi.sale_amount = ").append (phoneReportVo.getSaleAmount());
        }
        
        //订单来源
        if(StringUtils.isNotBlank(phoneReportVo.getSources())){
            sqlcommon.append (" and poi.sources = '").append (phoneReportVo.getSources()).append("'");
        }
        
        
        StringBuffer ss = new StringBuffer();
        ss.append(" order by poi.id desc ")
        .append(" limit " + phoneReportVo.getPage() + " ," + phoneReportVo.getRows());
        
        Session session = this.getSession ();
        String selectSql = sqlBase.toString() + sqlcommon.toString() + ss.toString();
        logger.info("------------------> querysql:" + selectSql);
        SQLQuery sqlQuery = session.createSQLQuery (selectSql).addEntity (PhoneReportPo.class);
        result.setData (sqlQuery.list ());
        String sqlCount = " select count(*) as id from phone_order_info poi LEFT JOIN phone_refund_amount pra on poi.phone_order_code = pra.phone_order_code where 1=1 " + sqlcommon.toString();
        
        BigInteger total = (BigInteger) session.createSQLQuery(sqlCount).uniqueResult();
        
        Pagination pagination = new Pagination();
       
        pagination.setRows(sqlQuery.list ());
        pagination.setTotal(total.longValue());
        result.setData(pagination);
        return result;
    }

	public Result exportOrderReportsByPlatform(Result result,PhoneReportVo phoneReportVo) {
		StringBuffer sqlBase = new StringBuffer ();
        StringBuffer sqlcommon = new StringBuffer ();
        
        sqlBase.append (" select poi.phone_order_code as phone_order_code,poi.id as id, ")
                .append(" poi.sources as sources, ")
        		.append(" poi.supplier_order_id as supplier_order_id, ")
        		.append(" poi.supplier_name as supplier_name, ")
        		.append(" poi.open_platform_no as open_platform_no, ")
				.append(" poi.member_mobile as member_mobile, ")
                .append(" poi.open_platform_no as open_platform_no, ")
				.append(" poi.pay_status as pay_status, ")
                .append(" poi.recharge_phone as recharge_phone, ")
                .append(" poi.recharge_success_time as recharge_success_time, ")
				.append(" poi.create_time as create_time, ")
				.append(" poi.pay_succss_time as pay_succss_time, ")
				.append(" poi.link_name as pay_user_name, ")
				.append(" poi.link_phone as mobile, ")
                .append(" poi.country_name as country_name, ")
                .append(" poi.country_code as country_code, ")
				.append(" poi.store_name as store_name, ")
                .append(" poi.store_code as store_code, ")
                .append(" poi.store_no as store_no, ")
				.append(" poi.original_amount as original_amount, ")
                .append(" poi.sale_amount as sale_amount, ")
				.append(" poi.real_amount as real_amount, ")
				.append(" poi.costing_amount as costing_amount, ")
				.append(" pra.status as status, ")
				.append(" pra.real_amount as pra_real_amount, ")
				.append(" pra.refund_amount_code as refund_amount_code, ")
				.append(" poi.platform_divided_ratio as platform_divided_ratio, ")
				.append(" poi.area_divided_ratio as area_divided_ratio, ")
				.append(" poi.area_commission_ratio as area_commission_ratio, ")
				.append(" poi.store_commission_ratio as store_commission_ratio ")
				.append(" from phone_order_info poi LEFT JOIN phone_refund_amount pra on poi.phone_order_code = pra.phone_order_code ")
        		.append(" where 1 = 1 ");
        //订单编号
        if (StringUtils.isNotBlank (phoneReportVo.getPhoneOrderCode())) {
        	sqlcommon.append (" and poi.phone_order_code like'%").append(phoneReportVo.getPhoneOrderCode().trim ()).append ("%' ");
        }
        //供应商订单编号
        if (StringUtils.isNotBlank (phoneReportVo.getSupplierOrderId())) {
        	sqlcommon.append (" and poi.supplier_order_id like'%").append(phoneReportVo.getSupplierOrderId().trim ()).append ("%' ");
        }
        //服务供应商
        if (StringUtils.isNotBlank (phoneReportVo.getSupplierName())) {
        	sqlcommon.append (" and poi.supplier_name like'%").append(phoneReportVo.getSupplierName().trim ()).append ("%' ");
        }
        //支付支付时间-开始时间
        if (StringUtils.isNotBlank (phoneReportVo.getStartDate())) {
        	sqlcommon.append (" and poi.create_time >= '").append(phoneReportVo.getStartDate().trim () + " 00:00:00").append ("'");
        }
        //支付时间-结束时间
        if (StringUtils.isNotBlank (phoneReportVo.getEndDate())) {
        	sqlcommon.append (" and poi.create_time <= '").append(phoneReportVo.getEndDate().trim () + " 23:59:59").append ("'");
        }
        //充值手机号
        if (StringUtils.isNotBlank (phoneReportVo.getMemberMobile())) {
        	sqlcommon.append (" and poi.recharge_phone like '%").append(phoneReportVo.getMemberMobile().trim ()).append ("%'");
        }
        //充值联系人姓名
        if (StringUtils.isNotBlank (phoneReportVo.getPayUserName())) {
        	sqlcommon.append (" and poi.link_name like '%").append(phoneReportVo.getPayUserName().trim ()).append ("%'");
        }
        //充值联系人电话
        if (StringUtils.isNotBlank (phoneReportVo.getMobile())) {
        	sqlcommon.append (" and poi.link_phone like '%").append(phoneReportVo.getMobile().trim ()).append ("%'");
        }
        //订单状态
        if (null != phoneReportVo.getPayStatus() && !"-1".equals(phoneReportVo.getPayStatus())) {
            String inString = phoneReportVo.getPayStatus();
            sqlcommon.append (" and poi.pay_status in( ").append (inString).append (")");
        }
        //省
        if (StringUtils.isNotBlank (phoneReportVo.getProvince())) {
        	sqlcommon.append (" and poi.province = '").append(phoneReportVo.getProvince().trim ()).append ("'");
        }
        //市
        if (StringUtils.isNotBlank (phoneReportVo.getCity())) {
        	sqlcommon.append (" and poi.city = '").append(phoneReportVo.getCity().trim ()).append ("'");
        }
        //县域中心
        if (StringUtils.isNotBlank (phoneReportVo.getCountryName())) {
        	sqlcommon.append (" and poi.country_name = '").append(phoneReportVo.getCountryName().trim ()).append ("'");
        }
        //县域中心
        if (StringUtils.isNotBlank (phoneReportVo.getCountryCode()) && !"-1".equals(phoneReportVo.getCountryCode())) {
            sqlcommon.append (" and poi.country_code = '").append(phoneReportVo.getCountryCode().trim ()).append ("'");
        }
        //网点名称
        if (StringUtils.isNotBlank (phoneReportVo.getStoreName())) {
        	sqlcommon.append (" and poi.store_name = '").append(phoneReportVo.getStoreName().trim ()).append ("'");
        }
        if (StringUtils.isNotBlank (phoneReportVo.getStoreCode())) {
            sqlcommon.append (" and poi.store_code = '").append(phoneReportVo.getStoreCode().trim ()).append ("'");
        }
        //退款状态
        if (phoneReportVo.getRefundStatus()!= null && !phoneReportVo.getRefundStatus().equals ("-1")) {
        	sqlcommon.append (" and pra.status = ").append (phoneReportVo.getRefundStatus());
        }
        if(null != phoneReportVo.getSaleAmount() &&
                !"-1".equals(phoneReportVo.getSaleAmount()) &&
                !"请选择".equals(phoneReportVo.getSaleAmount())){
            sqlcommon.append (" and poi.sale_amount = ").append (phoneReportVo.getSaleAmount());
        }
        
        //订单来源
        if(StringUtils.isNotBlank(phoneReportVo.getSources())){
            sqlcommon.append (" and poi.sources = '").append (phoneReportVo.getSources()).append("'");
        }
        
        
        if(StringUtils.isNotBlank(phoneReportVo.getShopName())){
            sqlcommon.append (" and poi.store_code = '").append (phoneReportVo.getShopName()).append ("'");
        }

        logger.info("--------------export sql :" + sqlcommon.toString());
        StringBuffer ss = new StringBuffer();
        ss.append(" order by poi.id desc ");
        Session session = this.getSession ();
        String selectSql = sqlBase.toString() + sqlcommon.toString() + ss.toString();
        SQLQuery sqlQuery = session.createSQLQuery (selectSql).addEntity (PhoneReportPo.class);
        Pagination pagination = new Pagination();
        pagination.setRows(sqlQuery.list ());
        result.setData(pagination);
        return result;
	}

}

