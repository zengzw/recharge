package com.tsh.vas.phone.constroller;

import java.io.File;
import java.util.List;

import com.github.ltsopensource.core.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.bean.ReturnStatusEnum;
import com.dtds.platform.util.excel.ExcelUtil;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.phone.service.PhoneReportService;
import com.tsh.vas.vo.phone.PhoneReportVo;

/**
 * 话费接口
 */
@RestController
@RequestMapping(value = "/phone")
public class PhoneReportController extends BaseController{
	
	@Autowired
	private PhoneReportService phoneReportService;

    /**
     *
     * @param phoneReportVo
     * @return
     */
    @RequestMapping(value = "/getPhoneOrderInfoReportsPageForPlatform.do")
    @ResponseBody
	public Object getForPlatform(PhoneReportVo phoneReportVo){
        logger.info("-----------------> params platform:" + JSON.toJSONString(phoneReportVo));
    	Result result = this.getResult ();
        try {
        	UserInfo userInfo = result.getUserInfo();
        	if(userInfo.getRoleType().equals(3)){
        		phoneReportVo.setCountryCode(String.valueOf(userInfo.getBizId()));
        	}
        	phoneReportService.getPhoneOrderInfoReportsPage (result, phoneReportVo);
        } catch (Exception e) {
            logger.error("查询订单报表getHcpOrderInfoReportsPage 失败", e);
            this.error (result, e);
        } finally {
            this.send (result);
        }
        logger.info ("platform return:" + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
	}

    /**
     *
     * @param phoneReportVo
     * @return
     */
    @RequestMapping(value = "/getPhoneOrderInfoReportsPageForArea.do")
    @ResponseBody
    public Object getForArea(PhoneReportVo phoneReportVo){
        logger.info("-----------------> params area:" + JSON.toJSONString(phoneReportVo));
        Result result = this.getResult ();
        try {
            UserInfo userInfo = result.getUserInfo();
            if(null != userInfo && userInfo.getRoleType().equals(3)){
                phoneReportVo.setCountryCode(String.valueOf(userInfo.getBizId()));
                phoneReportService.getPhoneOrderInfoReportsPage (result, phoneReportVo);
            }
        } catch (Exception e) {
            logger.error("查询订单报表getHcpOrderInfoReportsPage 失败", e);
            this.error (result, e);
        } finally {
            this.send (result);
        }
        logger.info ("area return:" + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
    }

    /**
     *
     * @param phoneReportVo
     * @return
     */
    @RequestMapping(value = "/exportOrderReportsByPlatform.do")
    public ReturnDTO export(PhoneReportVo phoneReportVo) {
        Result result = this.getResult ();
        try {
            UserInfo userInfo = result.getUserInfo ();
            String[] fields = null;
            String[] titles = null;
            if (3 == userInfo.getRoleType ()) {
                logger.info("-------------------------area export");
                //县域角色
            	phoneReportVo.setCountryCode (String.valueOf (userInfo.getBizId ()));
                fields = new String[]{"phoneOrderCode", "rechargePhone", "payStatus", "createTime", "rechargeSuccssTime", "payUserName", "mobile", "storeName", "storeNo",  "saleAmount", "originalAmount", "platformMinnus", "realAmount", "costingAmount", "tdAmount", "status", "praRealAmount", "refundAmountCode", "areaCommissionRatio","storeCommissionRatio"};
                titles = new String[]{"订单编号", "充值手机号", "订单状态", "支付时间", "订单成功时间", "充值联系人姓名", "充值联系人电话", "下单网点", "下单网点编号", "充值面额", "销售价", "平台优惠", "支付金额", "成本价", "提点金额", "退款状态", "退款金额（元）", "退款单号", "县域佣金比", "网点佣金比"};
            } else {
                logger.info("-------------------------platform export");
                //平台角色
            	fields = new String[]{"sources","phoneOrderCode","openPlatformNo", "supplierOrderId","supplierName", "rechargePhone", "payStatus", "createTime", "rechargeSuccssTime", "payUserName", "mobile", "countryName", "countryCode", "storeName", "storeNo", "saleAmount", "originalAmount", "platformMinnus", "realAmount", "costingAmount", "tdAmount", "status", "praRealAmount", "refundAmountCode", "platformDividedRatio", "platformDividedRatioStr", "areaDividedRatio", "areaCommissionRatio", "areaCommissionRatioStr", "storeCommissionRatio", "storeCommissionRatioStr"};
                titles = new String[]{"订单来源","订单编号","外部订单编号", "供应商订单编号", "服务供应商", "充值手机号", "订单状态", "支付时间", "订单成功时间", "充值联系人姓名", "充值联系人电话", "下单县域中心", "下单县域编号", "下单网点", "下单网点编号", "充值面额", "销售价", "平台优惠", "支付金额", "成本价", "提点金额", "退款状态", "退款金额（元）", "退款单号", "平台分成比(%)", "平台佣金（元）", "县域分成比(%)", "县域佣金比(%)", "县域佣金（元）", "网点佣金比(%)", "网点佣金（元）"};
            }
            
            List<PhoneReportVo> phoneReportVos = phoneReportService.exportOrderReportsByPlatform(result, phoneReportVo).getData();
            String head = "话费充值订单";
            File file = ExcelUtil.export (null, head, fields, titles, phoneReportVos, null);
            downloadExcel (request, response, file, head);
            return ReturnDTO.OK ();
        } catch (Exception e) {
            logger.error("话费充值(平台+县域) exportOrderReportsByPlatform 失败", e);
            this.error (result, e);
        } finally {
            this.send (result);
        }
        logger.info ("export return :" + JSONObject.toJSONString (result.DTO ()));
        return ReturnDTO.NO (ReturnStatusEnum.Error.getValue (), "导出话费充值订单报表！");
    }
}

