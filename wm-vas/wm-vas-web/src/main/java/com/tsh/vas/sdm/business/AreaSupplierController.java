package com.tsh.vas.sdm.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.sdm.service.business.AreaSupplierService;
import com.tsh.vas.vo.business.QueryAreaParam;
import com.tsh.vas.vo.business.SupplierOrderParamVo;

/**
 * 供应商controller
 */
@RestController
@RequestMapping(value = "vas/area/supplier/")
public class AreaSupplierController extends BaseController {

    private static final String HEAD_MSG = "请求返回结果：";
    private static final String USER_MSG = "用户登录信息:";

    @Autowired
    private AreaSupplierService areaSupplierService;

    /**
     * 查询服务业务的服务区域列表
     * @param queryAreaParam
     * @return
     */
    @RequestMapping(value = "areas", method = RequestMethod.GET)
    public ReturnDTO findBusinessAreas(QueryAreaParam queryAreaParam) {
        logger.info ("/areas:" + JSONObject.toJSONString (queryAreaParam));
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        try {
            result = this.areaSupplierService.findBusinessAreas (result, queryAreaParam);
        } catch (Exception ex) {
            logger.error ("查询业务服务区域列表失败" + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + result.DTO ());
        return result.DTO ();
    }

    /**
     * 查询县域的供应商列表
     * @param businessCode
     * @param countryCode
     * @return
     */
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ReturnDTO findSuppliersByCountryCode(@RequestParam(value = "businessCode") String businessCode, @RequestParam(value = "countryCode") String countryCode) {
        Result result = getResult ();
        logger.info ("/details:businessCode" + businessCode + ",countryCode:" + countryCode);
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        boolean flag = false;
        if (businessCode.isEmpty ()) {
            logger.error ("请求参数错误，服务业务类型编号不能为空");
            result.setMsg ("服务业务类型编号不能为空");
            flag = true;
        }
        if (countryCode.isEmpty ()) {
            logger.error ("请求参数错误，供应商编号不能为空");
            result.setMsg ("供应商邮箱不能为空");
            flag = true;
        }
        if (flag) {
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setStatus (500);
            return result.DTO ();
        }
        try {
            result = this.areaSupplierService.queryBusinessAreaDetails (result, businessCode, countryCode);
        } catch (Exception ex) {
            logger.error ("查询服务商失败:" + result.getMsg (),ex);
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
     * 县域内供应商排序
     * @param param
     * @return
     */
    @RequestMapping(value = "update/order", method = RequestMethod.POST)
    public ReturnDTO updateOrder(String param) {
        logger.info ("update/order:" + param);
        Result result = getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        List<SupplierOrderParamVo> supplierOrderParamVos = JSONArray.parseArray (param, SupplierOrderParamVo.class);
        boolean flag = false;
        if (supplierOrderParamVos.isEmpty ()) {
            logger.error ("请求参数错误，排序数据不能为空");
            flag = true;
        }
        if (flag) {
            result.setStatus (500);
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            return result.DTO ();
        }
        try {
            result = this.areaSupplierService.updateSupplierOrder (result, supplierOrderParamVos);
        } catch (Exception ex) {
            logger.error ("更新服务商排序失败" + result.getMsg (),ex);
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
     * 上移
     * @param id
     * @param sortField
     * @param sortFieldUp
     * @return
     */
    @RequestMapping(value = "moveUp")
    @ResponseBody
    public Result moveUp(@RequestParam(value = "id") Long id, @RequestParam(value = "sortField") Integer sortField, @RequestParam(value = "sortFieldUp") Integer sortFieldUp) {
        Result result = this.getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        try {
            areaSupplierService.moveUp (result, id, sortField, sortFieldUp);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("上移失败", e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result;
    }

    /**
     * 下移
     * @param id
     * @param sortField
     * @param sortFieldDown
     * @return
     */
    @RequestMapping(value = "moveDown")
    @ResponseBody
    public Result moveDown(@RequestParam(value = "id") Long id, @RequestParam(value = "sortField") Integer sortField, @RequestParam(value = "sortFieldDown") Integer sortFieldDown) {
        Result result = this.getResult ();
        logger.info (USER_MSG + JSONObject.toJSONString (result));
        try {
            areaSupplierService.moveDown (result, id, sortField, sortFieldDown);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("下移失败", e);
        } finally {
            this.send (result);
        }
        logger.info (HEAD_MSG + JSONObject.toJSONString (result.DTO ()));
        return result;
    }
}
