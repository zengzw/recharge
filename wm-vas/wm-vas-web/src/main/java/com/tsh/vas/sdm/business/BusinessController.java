package com.tsh.vas.sdm.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.sdm.service.business.BusinessService;
import com.tsh.vas.vo.business.BusinessSupplierQueryVo;
import com.tsh.vas.vo.business.SupplierBusinessVo;

/**
 * 业务controller
 */
@RestController
@RequestMapping(value = "vas/business/")
public class BusinessController extends BaseController {

    private static final String HEAD_MSG = "请求返回结果：";
    private static final String USER_MSG = "用户登录信息:";

    @Autowired
    private BusinessService businessService;

    /**
     * 查询服务业务所属供应商的列表
     * @param businessSupplierQueryVo
     * @return
     */
    @RequestMapping(value = "suppliers", method = RequestMethod.GET)
    public ReturnDTO findBusinessSuppliers(BusinessSupplierQueryVo businessSupplierQueryVo) {
        Result result = getResult ();
        logger.info ("business/suppliers参数列表" + JSONObject.toJSONString (businessSupplierQueryVo));
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        boolean flag = false;
        if (businessSupplierQueryVo.getBusinessCode ().isEmpty ()) {
            logger.error ("请求参数错误，服务业务类型编号不能为空.");
            result.setMsg ("服务业务类型编号不能为空.");
            flag = true;
        }
        if (flag) {
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setStatus (500);
            return result.DTO ();
        }
        try {
            result = this.businessService.findBusinessSuppliers (result, businessSupplierQueryVo);
        } catch (Exception ex) {
            logger.error ("查询业务服务供应商列表失败" + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 修改业务分成
     * @param supplierBusinessVo
     * @return
     */
    @RequestMapping(value = "update/profit", method = RequestMethod.POST)
    public ReturnDTO updateProfit(SupplierBusinessVo supplierBusinessVo) {
        Result result = getResult ();
        logger.info ("update/profit参数列表" + JSONObject.toJSONString (supplierBusinessVo));
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        boolean flag = false;
        if (supplierBusinessVo.getBusinessCode ().isEmpty ()) {
            logger.error ("请求参数错误，服务业务类型编号不能为空。");
            result.setMsg ("服务业务类型编号不能为空。");
            flag = true;
        }
        if (supplierBusinessVo.getSupplierCode ().isEmpty ()) {
            logger.error ("请求参数错误.，供应商编号不能为空.");
            result.setMsg ("供应商编号不能为空.");
            flag = true;
        }
        if (flag) {
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setStatus (500);
            return result.DTO ();
        }
        try {
            result = this.businessService.updateProfit (result, supplierBusinessVo);
        } catch (Exception ex) {
            logger.error ("修改供应商利润失败" + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 查询所有的业务
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "find/all/business")
    public ReturnDTO findBusiness() throws Exception {
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        try {
            result = this.businessService.findAllBusiness (result);
        } catch (Exception ex) {
            logger.error ("查询所有的业务失败" + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }
}
