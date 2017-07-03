package com.tsh.vas.trainticket.controller;

import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.bean.ReturnStatusEnum;
import com.dtds.platform.util.excel.ExcelUtil;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.trainticket.service.HcpService;

import com.tsh.vas.vo.trainticket.HcpOrderInfoReportsReturnVo;
import com.tsh.vas.vo.trainticket.HcpOrderInfoReportsVo;

/**
 * 火车票接口
 */
@SuppressWarnings(value="all")
@Controller
@RequestMapping(value = "/hcp")
public class HcpController extends BaseController{

    private static final String HEAD_MSG = "请求返回结果：";

	@Autowired
	private HcpService hcpService;

    /**
     *
     * @param hcpOrderInfoReportsVo
     * @return
     */
    @RequestMapping(value = "/getHcpOrderInfoReportsPage.do")
    @ResponseBody
	public Object getHcpOrderInfoReportsPage(HcpOrderInfoReportsVo hcpOrderInfoReportsVo){
    	Result result = this.getResult ();
        try {
        	UserInfo userInfo = result.getUserInfo();
        	if(userInfo.getRoleType().equals(3)){
        		hcpOrderInfoReportsVo.setCountryCode(String.valueOf(userInfo.getBizId()));
        	}
        	hcpService.getHcpOrderInfoReportsPage (result, hcpOrderInfoReportsVo);
        } catch (Exception e) {
        	logger.error("查询订单报表getHcpOrderInfoReportsPage 失败", e);
            this.error (result, e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
	}

    /**
     *
     * @param hcpOrderInfoReportsVo
     * @return
     */
    @RequestMapping(value = "/getHcpOrderInfosPage.do")
    @ResponseBody
	public Object getHcpOrderInfosPage(HcpOrderInfoReportsVo hcpOrderInfoReportsVo){
    	Result result = this.getResult ();
        try {
        	UserInfo userInfo = result.getUserInfo();
        	if(userInfo.getRoleType().equals(3)){
        		hcpOrderInfoReportsVo.setCountryCode(String.valueOf(userInfo.getBizId()));
        	}
        	hcpService.getHcpOrderInfosPage(result, hcpOrderInfoReportsVo);
        } catch (Exception e) {
        	logger.error("查询订单 getHcpOrderInfosPage 失败", e);
            this.error (result, e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
	}

    /**
     *
     * @param hcpOrderInfoReportsVo
     * @return
     */
    @RequestMapping(value = "/getHcpOrderInfoReportsPageExport.do")
    public ReturnDTO getHcpOrderInfoReportsPageExport(HcpOrderInfoReportsVo hcpOrderInfoReportsVo) {
    	
        Result result = this.getResult ();
        try {
            UserInfo userInfo = result.getUserInfo ();
            String[] fields = null;
            String[] titles = null;
            if (3 == userInfo.getRoleType ()) {
                //县域角色
            	hcpOrderInfoReportsVo.setCountryCode (String.valueOf (userInfo.getBizId ()));
                fields = new String[]{"hcpOrderCode", "supplierOrderId", "supplierName", "userName", "idCardId", "payStatusStr", "createTimeStr", "linkName","linkPhone","storeName","originalAmount", "costingAmount","refundStatusStr","statusStr", "realAmount", "refundAmountCode", "areaCommissionRatio", "areaCommissionRatioStr", "storeCommissionRatio", "storeCommissionRatioStr"};
                titles = new String[]{"订单编号", "供应商订单编号", "服务供应商", "乘车人", "乘车人身份证", "订单状态", "支付时间", "购票联系人姓名", "购票联系人电话", "购票网点", "支付金额", "票价","退票状态", "退款状态", "退款金额(元)", "退款单号", "县域佣金比(%)", "县域佣金(元)", "网点佣金比(%)", "网点佣金(元)"};
            } else {
                //平台角色
            	hcpOrderInfoReportsVo.setCountryCode (null);
                fields = new String[]{"hcpOrderCode", "supplierOrderId", "supplierName", "userName", "idCardId", "payStatusStr", "createTimeStr","linkName", "linkPhone","storeName","originalAmount", "costingAmount","refundStatusStr","statusStr", "realAmount", "refundAmountCode", "tdAmount", "platformDividedRatio", "platformDividedRatioStr", "areaDividedRatio", "areaCommissionRatio", "areaCommissionRatioStr", "storeCommissionRatio", "storeCommissionRatioStr"};
                titles = new String[]{"订单编号", "供应商订单编号", "服务供应商", "乘车人", "乘车人身份证", "订单状态", "支付时间", "购票联系人姓名", "购票联系人电话", "购票网点", "支付金额", "票价","退票状态", "退款状态", "退款金额(元)", "退款单号", "提点金额", "平台分成比(%)", "平台佣金(元)", "县域分成比(%)", "县域佣金比(%)", "县域佣金(元)", "网点佣金比(%)", "网点佣金(元)"};
            }
            List<HcpOrderInfoReportsReturnVo> hcpOrderInfoReportsReturnVos = hcpService.getHcpOrderInfoReportsPageExport(result, hcpOrderInfoReportsVo).getData();
            String head = "火车票订单报表";
            File file = ExcelUtil.export (null, head, fields, titles, hcpOrderInfoReportsReturnVos, null);
            downloadExcel(request, response, file, head);
            return ReturnDTO.OK ();
        } catch (Exception e) {
        	logger.error("火车票订单报表导出(平台+县域) getHcpOrderInfoReportsPageExport 失败", e);
            this.error (result, e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return ReturnDTO.NO (ReturnStatusEnum.Error.getValue (), "导出增值服务订单报表！");
    }

    /**
     *
     * @param hcpOrderInfoReportsVo
     * @return
     */
    @RequestMapping(value = "/exportOrderReportsByPlatform.do")
    public ReturnDTO exportOrderReportsByPlatform(HcpOrderInfoReportsVo hcpOrderInfoReportsVo) {
        Result result = this.getResult ();
        try {
            UserInfo userInfo = result.getUserInfo ();
            String[] fields = null;
            String[] titles = null;
            if (3 == userInfo.getRoleType ()) {
                //县域角色
            	hcpOrderInfoReportsVo.setCountryCode (String.valueOf (userInfo.getBizId ()));
                fields = new String[]{"hcpOrderCode", "supplierOrderId", "trainCode", "stationStartTime", "fromStation", "arriveStation", "seatTypeStr", "ticketTypeStr","userName",  "idCardId", "payStatusStr", "refundStatusStr", "statusStr", "createTimeStr", "cpDateTimeStr", "sqDateTimeStr", "originalAmount", "costingAmount", "linkName", "linkPhone", "storeName"};
                titles = new String[]{"订单编号", "供应商订单编号", "车次", "开车时间", "出发地", "目的地", "坐席", "票种", "乘车人", "乘车人身份证", "订单状态","退票状态", "退款状态", "支付时间", "出票时间", "申请退票时间", "支付金额", "票价", "购票联系人姓名", "购票联系人电话", "下单网点"};
            } else {
                //平台角色
            	hcpOrderInfoReportsVo.setCountryCode (null);
            	fields = new String[]{"hcpOrderCode", "supplierOrderId","supplierName", "trainCode", "stationStartTime", "fromStation", "arriveStation", "seatTypeStr", "ticketTypeStr","userName",  "idCardId", "payStatusStr","refundStatusStr", "statusStr", "createTimeStr", "cpDateTimeStr", "sqDateTimeStr", "originalAmount", "costingAmount", "linkName", "linkPhone","province","city","countryName", "storeName"};
                titles = new String[]{"订单编号", "供应商订单编号", "服务供应商", "车次", "开车时间", "出发地", "目的地", "坐席", "票种", "乘车人", "乘车人身份证", "订单状态", "退票状态","退款状态", "支付时间", "出票时间", "申请退票时间", "支付金额", "票价", "购票联系人姓名", "购票联系人电话", "省", "市", "下单县域","下单网点"};
            }
            
            List<HcpOrderInfoReportsReturnVo> hcpOrderInfoReportsVos = hcpService.getHcpOrderInfosPageExport(result, hcpOrderInfoReportsVo).getData();
            String head = "火车票订单";
            File file = ExcelUtil.export (null, head, fields, titles, hcpOrderInfoReportsVos, null);
            downloadExcel (request, response, file, head);
            return ReturnDTO.OK ();
        } catch (Exception e) {
        	logger.error("火车票订单导出(平台+县域) exportOrderReportsByPlatform 失败", e);
            this.error (result, e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return ReturnDTO.NO (ReturnStatusEnum.Error.getValue (), "导出增值服务订单报表！");
    }
}

