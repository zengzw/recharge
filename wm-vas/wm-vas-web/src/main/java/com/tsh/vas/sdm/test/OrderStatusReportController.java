package com.tsh.vas.sdm.test;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.excel.ExcelUtil;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;

/**
 * 订单报表contorller
 */
@Controller
@RequestMapping("/vas/order/")
public class OrderStatusReportController extends BaseController {

}
