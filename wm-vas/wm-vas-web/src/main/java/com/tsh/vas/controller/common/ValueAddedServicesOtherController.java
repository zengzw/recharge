package com.tsh.vas.controller.common;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.bean.ReturnStatusEnum;
import com.dtds.platform.util.excel.ExcelUtil;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.dubbo.bis.api.ShopApi;
import com.tsh.dubbo.bis.vo.ShopVO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.model.BusinessStoreShare;
import com.tsh.vas.sdm.service.business.BusinessService;
import com.tsh.vas.sdm.service.charge.ChargeService;
import com.tsh.vas.sdm.service.common.BusinessStoreShareService;
import com.tsh.vas.vo.business.OrderReportVo;
import com.tsh.vas.vo.business.QueryOrderParamVo;

/**
 * 一些杂乱的方法定义
 */
@Controller
@RequestMapping(value = "/valueAddedServicesOther")
public class ValueAddedServicesOtherController extends BaseController {

    private static final String HEAD_MSG = "请求返回结果:";

    @Autowired
    private BusinessStoreShareService businessStoreShareService;
    @Autowired
    private ChargeService chargeService;

    @Autowired
    private BusinessService businessService;
    
    @Autowired
    private ShopApi shopApi;



    /**
     *
     * @return
     */
    @RequestMapping(value = "/getShopByAreaId.do")
    @ResponseBody
    public Object getShopByAreaId() {
        Result result = this.getResult ();
        logger.info("getShopByAreaId");
        try {
            UserInfo userInfo = result.getUserInfo ();
            if (3 == userInfo.getRoleType ()) {
                
                List<ShopVO> shopVOs = shopApi.getShopList (result, userInfo.getBizId ()).getData ();
                result.setData (shopVOs);
            }
        } catch (Exception e) {
            this.error (result, e);
            logger.error("根据县域id获取下面的所有网点信息失败", e);
        } finally {
            this.send (result);
        }
        return result.getData ();
    }

    /**
     *
     * @param businessStoreShare
     * @return
     */
    @RequestMapping(value = "/addBusinessStoreShare.do")
    @ResponseBody
    public Object add(BusinessStoreShare businessStoreShare) {
        Result result = this.getResult ();
        try {
            businessStoreShareService.updateOrAddBusinessStoreShare (result, businessStoreShare);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("修改佣金占比 失败", e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
    }

    /**
     * @param businessStoreShare
     * @return
     */
    @RequestMapping(value = "/queryByCountryCodeAndBusinessCode.do")
    @ResponseBody
    public Object query(BusinessStoreShare businessStoreShare) {
        Result result = this.getResult ();
        try {
            BusinessStoreShare businessStoreShareDb = businessStoreShareService.queryByCountryCodeAndBusinessCode (result, businessStoreShare).getData ();
            result.setData (businessStoreShareDb);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("查询佣金占比 失败", e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
    }

    /**
     *
     * @param queryOrderParamVo
     * @return
     */
    @RequestMapping(value = "/getOrderReportsByPlatForm.do")
    @ResponseBody
    public Object getPlatForm(QueryOrderParamVo queryOrderParamVo) {
        Result result = this.getResult ();
        try {
            chargeService.getChargeInfoByPage (result, queryOrderParamVo);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("查询平台订单报表", e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
    }

    /**
     *
     * @param queryOrderParamVo
     * @return
     */
    @RequestMapping(value = "/getOrderReportsByArea.do")
    @ResponseBody
    public Object getByArea(QueryOrderParamVo queryOrderParamVo) {
        Result result = this.getResult ();
        try {
            UserInfo userInfo = result.getUserInfo ();
            if (3 == userInfo.getRoleType ()) {
                queryOrderParamVo.setCountryCode (String.valueOf (userInfo.getBizId ()));
            } else {
                queryOrderParamVo.setCountryCode (null);
            }
            chargeService.getChargeInfoByPage (result, queryOrderParamVo);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("查询县域订单报表查询", e);
        } finally {
            this.send (result);
        }
        return result.getData ();
    }

    /**
     *
     * @param queryOrderParamVo
     * @return
     */
    @RequestMapping(value = "/exportOrderReportsByPlatform.do")
    public ReturnDTO exportByPlatform(QueryOrderParamVo queryOrderParamVo) {
        Result result = this.getResult ();
        try {
            UserInfo userInfo = result.getUserInfo ();
            String[] fields = null;
            String[] titles = null;
            if (3 == userInfo.getRoleType ()) {
                queryOrderParamVo.setCountryCode (String.valueOf (userInfo.getBizId ()));
                fields = new String[]{"id", "businessName", "subBusinessName", "chargeCode", "rechargeUserCode", "payStatusStr", "createTimeStr", "rechargeUserName", "memberMobile","memberName",  "storeName", "supplierName", "originalAmount", "realAmount", "refundStatusStr", "refundAmount", "refundCode", "areaCommissionRatio", "areaCommissionYuan", "storeCommissionRatio", "storeCommissionYuan"};
                titles = new String[]{"序号", "增值服务类型", "服务分类", "订单编号", "充值缴费账号", "订单状态", "下单时间", "缴费户主姓名", "充值缴费联系人电话", "充值缴费联系人姓名", "下单网点", "服务供应商", "应缴金额（元）", "实缴金额（元）", "退款状态", "退款金额（元）", "退款单号", "县域佣金比(%)", "县域佣金（元）", "网点佣金比(%)", "网点佣金（元）"};
            } else {
                queryOrderParamVo.setCountryCode (null);
                fields = new String[]{"id", "businessName", "subBusinessName", "chargeCode", "rechargeUserCode", "payStatusStr", "createTimeStr", "rechargeUserName","memberMobile", "memberName",  "storeName", "supplierName", "originalAmount", "realAmount", "refundStatusStr", "refundAmount", "refundCode", "costingAmount", "liftCoefficient", "areaCommissionRatio", "areaCommissionYuan", "storeCommissionRatio", "storeCommissionYuan"};
                titles = new String[]{"序号", "增值服务类型", "服务分类", "订单编号", "充值缴费账号", "订单状态", "下单时间", "缴费户主姓名", "充值缴费联系人电话", "充值缴费联系人姓名", "下单网点", "服务供应商", "应缴金额（元）", "实缴金额（元）", "退款状态", "退款金额（元）", "退款单号", "成本价（元）", "提点系数（金额）/销售差额（元）", "县域佣金比(%)", "县域佣金（元）", "网点佣金比(%)", "网点佣金（元）"};
            }
            List<OrderReportVo> orderReportVos = chargeService.getChargeInfoByPageExport (result, queryOrderParamVo).getData ();
            String head = "增值服务订单报表";
            File file = ExcelUtil.export (null, head, fields, titles, orderReportVos, null);
            downloadExcel (request, response, file, head);
            return ReturnDTO.OK ();
        } catch (Exception e) {
            this.error (result, e);
            logger.error("导出平台或县域报表", e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return ReturnDTO.NO (ReturnStatusEnum.Error.getValue (), "导出增值服务订单报表！");
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/getBusinessInfoParent.do")
    @ResponseBody
    public Object getBusinessInfo() {
        Result result = this.getResult ();
        try {
            businessService.getBusinessInfoParent (result);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("获取父级服务", e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
    }

    /**
     *
     * @param parentCode
     * @return
     */
    @RequestMapping(value = "/getBusinessInfoByParentByParent.do")
    @ResponseBody
    public Object getByParent(@RequestParam(value = "parentCode") String parentCode) {
        Result result = this.getResult ();
        try {
            businessService.getBusinessInfoByParentByParent (result, parentCode);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("根据父级获取子级服务", e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
    }
}

