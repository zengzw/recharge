package com.tsh.vas.sdm.supplier;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.SupplierBusiness;
import com.tsh.vas.sdm.service.supplier.SupplierService;
import com.tsh.vas.vo.supplier.ApplyInfoVo;
import com.tsh.vas.vo.supplier.QuerySupplierVo;
import com.tsh.vas.vo.supplier.SupplierBusinessAreaVo;

/**
 * Created by Iritchie.ren on 2016/9/19.
 */
@RestController
@RequestMapping(value = "/vas/supplier/")
public class SupplierController extends BaseController {

    private static final String HEAD_MSG = "请求返回结果：";
    private static final String USER_MSG = "用户登录信息:";

    @Autowired
    private SupplierService supplierService;

    /**
     * 供应商是否存在验证
     * @param shopSupplierNo
     * @return
     */
    @RequestMapping(value = "getObjectByShopSupplierNo")
    public ReturnDTO getObjectByShopSupplierNo(@RequestParam("shopSupplierNo") String shopSupplierNo) {
        Result result = new Result ();
        try {
            supplierService.getObjectByShopSupplierNo (result, shopSupplierNo);
        } catch (Exception ex) {
            this.error (result, ex);
            logger.error("供应商是否存在验证 失败", ex);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 供应商申请入驻
     * @param param
     * @return
     */
    @RequestMapping(value = "apply", method = RequestMethod.POST)
    public ReturnDTO apply(String param) {
        logger.info ("add请求参数:" + param);
        Result result = this.getResult ();
        ApplyInfoVo applyInfoVo = JSONObject.toJavaObject (JSONObject.parseObject (param), ApplyInfoVo.class);
        applyInfoVo.setApplyExplain ("备注信息");
        boolean flag = checkoutParam (result, applyInfoVo);
        if (StringUtils.isEmpty (applyInfoVo.getApplyExplain ())) {
            logger.error ("请求参数错误，供应商申请说明不能为空");
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setMsg ("供应商申请说明不能为空");
            flag = true;
        }
        if (flag) {
            return result.DTO ();
        }

        try {
            result = this.supplierService.addSupplier (result, applyInfoVo);
        } catch (Exception ex) {
            logger.error ("新增供应商失败" + result.getMsg (),ex);
            result.setCode (500);
            result.setMsg (ex.getMessage ());
        } finally {
            send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 供应商类表查询
     * @param querySupplierVo
     * @return
     */
    @RequestMapping(value = "query/suppliers", method = RequestMethod.GET)
    public ReturnDTO query(QuerySupplierVo querySupplierVo) {
        logger.info ("请求参数：" + JSONObject.toJSONString (querySupplierVo));
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        try {
            result = this.supplierService.findSuppliers (result, querySupplierVo);
        } catch (Exception ex) {
            logger.error ("查询供应商失败" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 获取供应商的服务区域信息
     * @param supplierCode
     * @param businessCode
     * @return
     */
    @RequestMapping(value = "find/areas", method = RequestMethod.GET)
    public ReturnDTO findAreas(String supplierCode, String businessCode) {
        logger.info ("请求参数：supplierCode:" + supplierCode + ";businessCode:" + businessCode);
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        try {
            result = this.supplierService.findAreas (result, supplierCode, businessCode);
        } catch (Exception ex) {
            logger.error ("查询供应商服务区域" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 获取供应商详细信息
     * @param supplierCode
     * @return
     */
    @RequestMapping(value = "get/supplierinfo", method = RequestMethod.GET)
    public ReturnDTO querySupplierInfo(String supplierCode) {
        logger.info ("请求参数：supplierCode" + supplierCode);
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        try {
            result = this.supplierService.querySupplierInfo (result, supplierCode);
        } catch (Exception ex) {
            logger.error ("查询供应商信息失败" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 修改供应商信息
     * @param param
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ReturnDTO updateSupplier(String param) {
        logger.info ("请求参数updateSupplier:" + param);
        Result result = this.getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        ApplyInfoVo applyInfoVo = JSONObject.parseObject (param, ApplyInfoVo.class);
        boolean flag = checkoutParam (result, applyInfoVo);
        if (flag) {
            return result.DTO ();
        }
        try {
            result = this.supplierService.updateSupplierInfo (result, applyInfoVo);
        } catch (Exception ex) {
            logger.error ("修改供应商失败" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 供应商信息，新增，修改的参数校验
     * @param result
     * @param applyInfoVo
     * @return
     */
    private boolean checkoutParam(Result result, ApplyInfoVo applyInfoVo) {
        boolean flag = false;
        if (StringUtils.isEmpty (applyInfoVo.getSupplierName ())) {
            logger.error ("请求参数错误，供应商姓名不能为空");
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setMsg ("供应商姓名不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (applyInfoVo.getCompany ())) {
            logger.error ("请求参数错误，供应商公司名称不能为空");
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setMsg ("供应商公司名称不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (applyInfoVo.getEmail ())) {
            logger.error ("请求参数错误，供应商邮箱不能为空");
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setMsg ("供应商邮箱不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (applyInfoVo.getMobile ())) {
            logger.error ("请求参数错误，供应商手机号码不能为空");
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setMsg ("供应商手机号码不能为空");
            flag = true;
        }
        return flag;
    }

    /**
     * 供应商审核
     * @param param
     * @return
     */
    @RequestMapping(value = "checkout", method = RequestMethod.POST)
    public ReturnDTO checkoutSupplier(String param) {
        logger.info ("updateSupplier:" + param);
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        JSONObject jsonObject = JSONObject.parseObject (param);
        String supplierCode = jsonObject.getString ("supplierCode");
        Integer checkStatus = jsonObject.getInteger ("checkStatus");
        boolean flag = false;
        if (StringUtils.isEmpty (supplierCode)) {
            logger.error ("请求参数错误，供应商编号不能为空");
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setMsg ("供应商编号不能为空");
            flag = true;
        }
        if (checkStatus == null || checkStatus == 0) {
            logger.error ("请求参数错误，审核状态不能为空");
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setMsg ("审核状态不能为空");
            flag = true;
        }
        if (flag) {
            return result.DTO ();
        }
        try {
            result = this.supplierService.checkoutSupplier (result, supplierCode, checkStatus);
        } catch (Exception ex) {
            logger.error ("供应商审核失败" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 配置供应商业务的服务区域
     * @param param
     * @return
     */
    @RequestMapping(value = "business/add/area", method = RequestMethod.POST)
    public ReturnDTO addArea(String param) {
        logger.info ("请求参数business/add/area:" + param);
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        SupplierBusinessAreaVo supplierBusinessAreaVo = JSONObject.toJavaObject (JSONObject.parseObject (param), SupplierBusinessAreaVo.class);
        boolean flag = false;
        if (StringUtils.isEmpty (supplierBusinessAreaVo.getSupplierCode ())) {
            logger.error ("请求参数错误，供应商编号不能为空.");
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setMsg ("业务编号不能为空");
            flag = true;
        }
        if (StringUtils.isEmpty (supplierBusinessAreaVo.getBusinessCode ())) {
            logger.error ("请求参数错误，业务编号不能为空");
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setMsg ("业务编号不能为空");
            flag = true;
        }
        if (flag) {
            return result.DTO ();
        }
        try {
            result = this.supplierService.businessAddArea (result, supplierBusinessAreaVo);
        } catch (Exception ex) {
            logger.error ("配置供应商业务区域失败" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 根据供应商编号查询业务编码
     * @param supplierCode
     * @return
     */
    @RequestMapping(value = "getBusinessCodeBySupplierCode")
    public ReturnDTO getBusinessCodeBySupplierCode(String supplierCode) {
        logger.info ("请求参数getBusinessCodeBySupplierCode:" + supplierCode);
        Result result = this.getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        try {
        	List<SupplierBusiness> supplierBusinesses = supplierService.getBusinessCodeBySupplierCode(supplierCode);
        	result.setCode (ResponseCode.OK_CODE.getCode());
            result.setMsg ("");
            result.setData(supplierBusinesses);
        } catch (Exception ex) {
            logger.error ("根据供应商编号查询业务编码失败" + result.getMsg (),ex);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }
}
