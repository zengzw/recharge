package com.tsh.vas.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.excel.ExcelUtil;
import com.github.ltsopensource.core.json.JSON;
import com.tsh.vas.commoms.enums.EnumOrderType;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.VasOrderTransformHistory;
import com.tsh.vas.manager.service.ChangeOrderStatusService;
import com.tsh.vas.vo.QueryOrderChangeVO;

/**
 * 订单状态controller
 */
@Controller
@RequestMapping("/vas/order/")
public class OrderStatusController extends BaseController {

    @Autowired
    private ChangeOrderStatusService changeOrderStatusService;

    /**
     *
     * @param orderType
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ReturnDTO getOrderStatus(String orderType, String orderNo) {
        Result result = this.getResult();
        try {
            changeOrderStatusService.queryOrderInfo(result, orderType, orderNo);
        } catch (Exception e) {
            logger.error ("查询订单状态异常:" + result.getMsg (),e);
            result.setStatus (200);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg ("操作失败");
        } finally {
            send(result);
        }
        return result.DTO();
    }

    /**
     *
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ReturnDTO getOrderChangeStatus(String orderType, String orderNo) {
        Result result = this.getResult();
        try {
            changeOrderStatusService.queryChangeHistory(result, orderType, orderNo);
        } catch (Exception e) {
            logger.error ("订单状态变更查询异常:" + result.getMsg (),e);
            result.setStatus (200);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg ("操作失败");
        }finally{
            send(result);
        }
        return result.DTO();
    }

    /**
     *
     * @param orderNo
     * @param remarks
     * @param modifyOrderStatus
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ReturnDTO updateOrderStatus(String orderNo, String remarks, String modifyOrderStatus) {
        Result result = this.getResult();
        try {
            changeOrderStatusService.updateOrderStatus(result, orderNo, remarks, modifyOrderStatus);
        } catch (Exception e) {
            logger.error ("更新订单状态异常:" + result.getMsg (),e);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (e.getMessage());
        }finally{
            send(result);
        }
        return result.DTO();
    }


    /**
     *
     * @param page
     * @param rows
     * @param jobInfoJson
     * @return 返回站内广告列表数据
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/queryChangeOrderPage")
    @ResponseBody
    public Pagination queryChangeOrderPage(int page,int rows,String searchInfo){
        Result result = this.getResult();
        Pagination pagination = null;
        Page pageInfo = new Page(page,rows);
        QueryOrderChangeVO orderInfo = JSONObject.parseObject(searchInfo, QueryOrderChangeVO.class);
        if(orderInfo == null) orderInfo = new QueryOrderChangeVO();

        result =  changeOrderStatusService.queryChangeOrderList(result, pageInfo, orderInfo);
        pagination = result.getData();

        logger.info("--->查询结果: " + JSON.toJSONString(pagination));

        return pagination;
    }


    /**
     * 导出
     * 
     * @param request
     * @param orderNo
     * @param orderType
     * @param page
     * @param rows
     */
    @ResponseBody
    @RequestMapping(value = "/exportOrderChangeReports")
    public void exportOrderChangeReports(HttpServletRequest request, String orderNo, 
            String orderType ,Integer page, Integer rows) {
        Result result = this.getResult();
        try {
            Pagination pagination = null;
            Page pageInfo = new Page(page,rows);
            QueryOrderChangeVO orderInfo = new QueryOrderChangeVO();
            orderInfo.setOrderNo(orderNo);
            orderInfo.setOrderType(orderType);
            result =  changeOrderStatusService.queryChangeOrderList(result, pageInfo, orderInfo);
            pagination = result.getData();

            List<VasOrderTransformHistory> lstResult = (List<VasOrderTransformHistory>) pagination.getRows();
            //设置名称
            for(VasOrderTransformHistory history:lstResult){
                EnumOrderType enumOrderType = EnumOrderType.getByName(history.getOrderType());
                if(enumOrderType != null){
                    history.setOrderType(enumOrderType.getBusinessName());
                }
            }

            String[] fields = {"orderType","orderCode","payMoney","beforeStatus","afterStatus",
                    "remark","creater","updateTime"};
            String[] titles = {"订单类型","订单号", "支付金额(元)", "原订单状态", "变更后订单状态","变更理由", "操作人", "操作时间"};
            String head = "订单状态变更报表";
            File file = ExcelUtil.export (null, head, fields, titles, lstResult, null);
            downloadExcel(request, response, file, head);

        } catch (Exception ex) {
            logger.error ("订单状态变更报表导出:" + result.getMsg (),ex);
            result.setStatus (200);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        }finally {
            send(result);
        }
    }

}
