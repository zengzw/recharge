package com.tsh.vas.netgold.controller;

import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.netgold.service.NetgoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/4/10 010.
 */

@Controller
@RequestMapping(value = "/netgold")
public class QueryOrderController extends BaseController {

    @Autowired
    NetgoldService netgoldService;

    @RequestMapping(value = "/queryOrder", method = RequestMethod.GET)
    @ResponseBody
    public ReturnDTO queryOrderInfo(String orderCode){
        Result result = this.getResult ();

        try {
            netgoldService.queryOrder(result, orderCode);
        } catch (Exception ex) {
            logger.error ("网金获取订单信息失败：" + result.getMsg (), ex);
            return responseFail("网金获取订单信息失败");
        }

        return result.DTO();
    }
}
