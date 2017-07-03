package com.tsh.vas.trainticket.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.dubbo.bis.api.AreaApi;
import com.tsh.vas.dao.trainticket.HcpOrderInfoDao;
import com.tsh.vas.model.trainticket.HcpOrderInfoReports;
import com.tsh.vas.model.trainticket.HcpOrderInfos;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumRefundOrderStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumReturnTicketStatus;
import com.tsh.vas.trainticket.commoms.enums.EnumSeatType;
import com.tsh.vas.trainticket.commoms.enums.EnumTicketType;
import com.tsh.vas.vo.trainticket.HcpOrderInfoReportsReturnVo;
import com.tsh.vas.vo.trainticket.HcpOrderInfoReportsVo;
/**
 * 火车票Service
 * @version 1.0.0 2016年11月29日<br>
 * @see 
 * @since JDK 1.7.0
 */
@SuppressWarnings(value="all")
@Service
public class HcpService {
	private static final Logger LOGGER = Logger.getLogger (HcpService.class);
	@Autowired
    private AreaApi areaApi;
	@Autowired
	private HcpOrderInfoDao hcpOrderInfoDao;
	
	/**
	 * 火车票订单导出(平台+县域)
	 * @param result
	 * @param hcpOrderInfoReportsVo
	 * @return
	 * @throws Exception
	 */
	public Result getHcpOrderInfosPageExport(Result result,HcpOrderInfoReportsVo hcpOrderInfoReportsVo) throws Exception{
		
		List<HcpOrderInfoReportsReturnVo> hcpOrderInfoReportsReturnVos = new ArrayList<HcpOrderInfoReportsReturnVo>();
		
		List<HcpOrderInfos> hcpOrderInfos = hcpOrderInfoDao.getHcpOrderInfosPageExport(result, hcpOrderInfoReportsVo).getData();
		for(HcpOrderInfos hcpOrderInfos2 : hcpOrderInfos){
			HcpOrderInfoReportsReturnVo hcpOrderInfoReportsReturnVo = new HcpOrderInfoReportsReturnVo();
			BeanUtils.copyProperties(hcpOrderInfos2, hcpOrderInfoReportsReturnVo);
			hcpOrderInfoReportsReturnVo.setCreateTimeStr(DateUtil.getTime(hcpOrderInfoReportsReturnVo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			hcpOrderInfoReportsReturnVo.setCpDateTimeStr(hcpOrderInfos2.getCpDateTime());
			hcpOrderInfoReportsReturnVo.setSqDateTimeStr(hcpOrderInfos2.getSqDateTime());
			hcpOrderInfoReportsReturnVo = countFc(hcpOrderInfoReportsReturnVo);
			
			//订单状态+退款状态
			if(null != hcpOrderInfoReportsReturnVo.getStatus()){
				String statusStr = EnumRefundOrderStatus.getEnume(hcpOrderInfoReportsReturnVo.getStatus()).getDesc();
				hcpOrderInfoReportsReturnVo.setStatusStr(statusStr);
			}else {
				hcpOrderInfoReportsReturnVo.setStatusStr("—");
			}
			
			//退票状态
			if(null != hcpOrderInfoReportsReturnVo.getRefundStatus()){
				String refundStatusStr = EnumReturnTicketStatus.getEnume(hcpOrderInfoReportsReturnVo.getRefundStatus()).getDesc();
				hcpOrderInfoReportsReturnVo.setRefundStatusStr(refundStatusStr);
			}else {
				hcpOrderInfoReportsReturnVo.setRefundStatusStr("—");
			}
			String payStatusStr = EnumOrderInfoPayStatus.getEnume(hcpOrderInfoReportsReturnVo.getPayStatus()).getDesc();
			hcpOrderInfoReportsReturnVo.setSeatTypeStr(EnumSeatType.getEnume(hcpOrderInfos2.getSeatType()).getName());
			hcpOrderInfoReportsReturnVo.setTicketTypeStr(EnumTicketType.getEnume(hcpOrderInfos2.getTicketType()).getName());
			hcpOrderInfoReportsReturnVo.setPayStatusStr(payStatusStr);
			hcpOrderInfoReportsReturnVo.setStationStartTime(hcpOrderInfos2.getTravelTime() + " " + hcpOrderInfos2.getStationStartTime());
			
			hcpOrderInfoReportsReturnVos.add(hcpOrderInfoReportsReturnVo);
		}
		result.setData(hcpOrderInfoReportsReturnVos);
		return result;
	}
	/**
	 * 火车票订单(平台+县域)
	 * @param result
	 * @param hcpOrderInfoReportsVo
	 * @return
	 * @throws Exception
	 */
	public Result getHcpOrderInfosPage(Result result,HcpOrderInfoReportsVo hcpOrderInfoReportsVo) throws Exception{
		hcpOrderInfoReportsVo.setPage((hcpOrderInfoReportsVo.getPage() - 1 ) * hcpOrderInfoReportsVo.getRows());
		hcpOrderInfoReportsVo.setRows(hcpOrderInfoReportsVo.getRows());
		List<HcpOrderInfoReportsReturnVo> hcpOrderInfoReportsReturnVos = new ArrayList<HcpOrderInfoReportsReturnVo>();
		
		Pagination pagination = hcpOrderInfoDao.getHcpOrderInfosPage(result, hcpOrderInfoReportsVo).getData();
		List<HcpOrderInfos> hcpOrderInfos = (List<HcpOrderInfos>) pagination.getRows();
		for(HcpOrderInfos hcpOrderInfos2 : hcpOrderInfos){
			HcpOrderInfoReportsReturnVo hcpOrderInfoReportsReturnVo = new HcpOrderInfoReportsReturnVo();
			BeanUtils.copyProperties(hcpOrderInfos2, hcpOrderInfoReportsReturnVo);
			hcpOrderInfoReportsReturnVo.setCreateTimeStr(DateUtil.getTime(hcpOrderInfoReportsReturnVo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			hcpOrderInfoReportsReturnVo.setCpDateTimeStr(hcpOrderInfos2.getCpDateTime());
			hcpOrderInfoReportsReturnVo.setSqDateTimeStr(hcpOrderInfos2.getSqDateTime());
			hcpOrderInfoReportsReturnVo.setSeatTypeStr(EnumSeatType.getEnume(hcpOrderInfos2.getSeatType()).getName());
			hcpOrderInfoReportsReturnVo.setTicketTypeStr(EnumTicketType.getEnume(hcpOrderInfos2.getTicketType()).getName());
			hcpOrderInfoReportsReturnVo.setStationStartTime(hcpOrderInfos2.getTravelTime() + " " + hcpOrderInfos2.getStationStartTime());
			
			
			//订单状态+退款状态
			if(null != hcpOrderInfoReportsReturnVo.getStatus()){
				String statusStr = EnumRefundOrderStatus.getEnume(hcpOrderInfoReportsReturnVo.getStatus()).getDesc();
				hcpOrderInfoReportsReturnVo.setStatusStr(statusStr);
			}else {
				hcpOrderInfoReportsReturnVo.setStatusStr("—");
			}
			
			//退票状态
			if(null != hcpOrderInfoReportsReturnVo.getRefundStatus()){
				String refundStatusStr = EnumReturnTicketStatus.getEnume(hcpOrderInfoReportsReturnVo.getRefundStatus()).getDesc();
				hcpOrderInfoReportsReturnVo.setRefundStatusStr(refundStatusStr);
			}else {
				hcpOrderInfoReportsReturnVo.setRefundStatusStr("—");
			}
			String payStatusStr = EnumOrderInfoPayStatus.getEnume(hcpOrderInfoReportsReturnVo.getPayStatus()).getDesc();
			hcpOrderInfoReportsReturnVo.setPayStatusStr(payStatusStr);
			hcpOrderInfoReportsReturnVo = countFc(hcpOrderInfoReportsReturnVo);
			hcpOrderInfoReportsReturnVos.add(hcpOrderInfoReportsReturnVo);
		}
		pagination.setRows(hcpOrderInfoReportsReturnVos);
		result.setData(pagination);
		return result;
	}
	/**
	 * 火车票订单报表导出(平台+县域)
	 * @param result
	 * @param hcpOrderInfoReportsVo
	 * @return
	 * @throws Exception
	 */
	public Result getHcpOrderInfoReportsPageExport(Result result,HcpOrderInfoReportsVo hcpOrderInfoReportsVo) throws Exception{
		
		List<HcpOrderInfoReportsReturnVo> hcpOrderInfoReportsReturnVos = new ArrayList<HcpOrderInfoReportsReturnVo>();
		List<HcpOrderInfoReports> hcpOrderInfoReports = hcpOrderInfoDao.getHcpOrderInfoReportsPageExport(result, hcpOrderInfoReportsVo).getData();
		
		for(HcpOrderInfoReports hcpOrderInfoReports2 : hcpOrderInfoReports){
			HcpOrderInfoReportsReturnVo hcpOrderInfoReportsReturnVo = new HcpOrderInfoReportsReturnVo();
			BeanUtils.copyProperties(hcpOrderInfoReports2, hcpOrderInfoReportsReturnVo);
			hcpOrderInfoReportsReturnVo.setCreateTimeStr(DateUtil.getTime(hcpOrderInfoReportsReturnVo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			//计算佣金
			hcpOrderInfoReportsReturnVo = countFc(hcpOrderInfoReportsReturnVo);
			//订单状态+退款状态
			if(null != hcpOrderInfoReportsReturnVo.getStatus()){
				String statusStr = EnumRefundOrderStatus.getEnume(hcpOrderInfoReportsReturnVo.getStatus()).getDesc();
				hcpOrderInfoReportsReturnVo.setStatusStr(statusStr);
			}else {
				hcpOrderInfoReportsReturnVo.setStatusStr("—");
			}
			
			//退票状态
			if(null != hcpOrderInfoReportsReturnVo.getRefundStatus()){
				String refundStatusStr = EnumReturnTicketStatus.getEnume(hcpOrderInfoReportsReturnVo.getRefundStatus()).getDesc();
				hcpOrderInfoReportsReturnVo.setRefundStatusStr(refundStatusStr);
			}else {
				hcpOrderInfoReportsReturnVo.setRefundStatusStr("—");
			}
			
			String payStatusStr = EnumOrderInfoPayStatus.getEnume(hcpOrderInfoReportsReturnVo.getPayStatus()).getDesc();
			hcpOrderInfoReportsReturnVo.setPayStatusStr(payStatusStr);
			hcpOrderInfoReportsReturnVos.add(hcpOrderInfoReportsReturnVo);
		}
		result.setData(hcpOrderInfoReportsReturnVos);
		return result;
	}
	
	/**
	 * 火车票订单报表(平台+县域)
	 * @param result
	 * @param hcpOrderInfoReportsVo
	 * @return
	 * @throws Exception
	 */
	public Result getHcpOrderInfoReportsPage(Result result,HcpOrderInfoReportsVo hcpOrderInfoReportsVo) throws Exception{
		hcpOrderInfoReportsVo.setPage((hcpOrderInfoReportsVo.getPage() - 1 ) * hcpOrderInfoReportsVo.getRows());
		hcpOrderInfoReportsVo.setRows(hcpOrderInfoReportsVo.getRows());
		
		
		List<HcpOrderInfoReportsReturnVo> hcpOrderInfoReportsReturnVos = new ArrayList<HcpOrderInfoReportsReturnVo>();
		
		Pagination pagination = hcpOrderInfoDao.getHcpOrderInfoReportsPage(result, hcpOrderInfoReportsVo).getData();
		List<HcpOrderInfoReports> hcpOrderInfoReports = (List<HcpOrderInfoReports>) pagination.getRows();
		for(HcpOrderInfoReports hcpOrderInfoReports2 : hcpOrderInfoReports){
			HcpOrderInfoReportsReturnVo hcpOrderInfoReportsReturnVo = new HcpOrderInfoReportsReturnVo();
			BeanUtils.copyProperties(hcpOrderInfoReports2, hcpOrderInfoReportsReturnVo);
			hcpOrderInfoReportsReturnVo.setCreateTimeStr(DateUtil.getTime(hcpOrderInfoReportsReturnVo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			
			//订单状态+退款状态
			if(null != hcpOrderInfoReportsReturnVo.getStatus()){
				String statusStr = EnumRefundOrderStatus.getEnume(hcpOrderInfoReportsReturnVo.getStatus()).getDesc();
				hcpOrderInfoReportsReturnVo.setStatusStr(statusStr);
			}else {
				hcpOrderInfoReportsReturnVo.setStatusStr("—");
			}
			//退票状态
			if(null != hcpOrderInfoReportsReturnVo.getRefundStatus()){
				String refundStatusStr = EnumReturnTicketStatus.getEnume(hcpOrderInfoReportsReturnVo.getRefundStatus()).getDesc();
				hcpOrderInfoReportsReturnVo.setRefundStatusStr(refundStatusStr);
			}else {
				hcpOrderInfoReportsReturnVo.setRefundStatusStr("—");
			}
			
			String payStatusStr = EnumOrderInfoPayStatus.getEnume(hcpOrderInfoReportsReturnVo.getPayStatus()).getDesc();
			hcpOrderInfoReportsReturnVo.setPayStatusStr(payStatusStr);
			hcpOrderInfoReportsReturnVo = countFc(hcpOrderInfoReportsReturnVo);
			
			hcpOrderInfoReportsReturnVos.add(hcpOrderInfoReportsReturnVo);
		}
		pagination.setRows(hcpOrderInfoReportsReturnVos);
		result.setData(pagination);
		return result;
	}
	
	//分成金额计算+提点金额 价格加工
	private HcpOrderInfoReportsReturnVo countFc(HcpOrderInfoReportsReturnVo hcpOrderInfoReportsReturnVo){
		//计算平台+县域+网点佣金
		BigDecimal am = new BigDecimal(100);
		//计算分成金额=支付金额 - 票价
		BigDecimal fcAmount = new BigDecimal(hcpOrderInfoReportsReturnVo.getOriginalAmount()).subtract(new BigDecimal(hcpOrderInfoReportsReturnVo.getCostingAmount()));
		//提点金额
		hcpOrderInfoReportsReturnVo.setTdAmount(fcAmount.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
		//平台，县域，网点，分成比例
		BigDecimal platFormRatio = new BigDecimal(hcpOrderInfoReportsReturnVo.getPlatformDividedRatio().toString()).divide(am);
		BigDecimal areaFormRatio = new BigDecimal(hcpOrderInfoReportsReturnVo.getAreaCommissionRatio().toString()).divide(am);
		BigDecimal stormFormRatio = new BigDecimal(hcpOrderInfoReportsReturnVo.getStoreCommissionRatio().toString()).divide(am);
		//平台，县域，网点分成金额
		BigDecimal platformRatioYuan = platFormRatio.multiply(fcAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
		BigDecimal areaFormRatioYuan = areaFormRatio.multiply(fcAmount.subtract(platformRatioYuan)).setScale(2,BigDecimal.ROUND_HALF_UP);
		BigDecimal stormFormRatioYuan = new BigDecimal(0);
		if(areaFormRatioYuan.compareTo(new BigDecimal(0)) == 0){
			stormFormRatioYuan = fcAmount.subtract(platformRatioYuan);
		}else {
			stormFormRatioYuan = fcAmount.subtract(platformRatioYuan).subtract(areaFormRatioYuan);
		}
		hcpOrderInfoReportsReturnVo.setPlatformDividedRatioStr(platformRatioYuan.toString());
		hcpOrderInfoReportsReturnVo.setAreaCommissionRatioStr(areaFormRatioYuan.toString());
		hcpOrderInfoReportsReturnVo.setStoreCommissionRatioStr(stormFormRatioYuan.toString());
		return hcpOrderInfoReportsReturnVo;
	}
	
}

