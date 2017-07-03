package com.tsh.vas.sdm.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.sdm.service.area.AreaService;
import com.tsh.vas.vo.area.BusinessStoreShareVo;

/**
 * Created by Iritchie.ren on 2016/9/28.
 */
@RestController
@RequestMapping(value = "vas/area/")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;

    /**
     * 查询县域的服务业务
     *
     * @param param
     * @return
     * @author iritchie.ren
     */
    @RequestMapping(value = "query/business")
    public ReturnDTO queryBusinessByArea(String param) {
        logger.info ("query/business参数列表" + param);
        Result result = getResult ();
        logger.info ("用户登录信息:" + JSONObject.toJSONString (result));
        JSONObject json = JSONObject.parseObject (param);
        String countryCode = json.getString ("countryCode");
        boolean flag = false;
        if (countryCode.isEmpty ()) {
            logger.error ("请求参数错误，服务业务类型编号不能为空。");
            result.setMsg ("服务业务类型编号不能为空。");
            flag = true;
        }
        if (flag) {
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setStatus (500);
            return result.DTO ();
        }
        try {
            result = this.areaService.queryBusinessByAreaProfit (result, countryCode);
        } catch (Exception ex) {
            logger.error ("查询失败" + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info ("请求返回结果：" + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }

    /**
     * 修改县域，网点利润分成
     *
     * @param param
     * @return
     * @author iritchie.ren
     */
    @RequestMapping(value = "profit/update")
    public ReturnDTO updateBusinessByAreaProfit(String param) {
        logger.info ("query/business参数列表" + param);
        Result result = getResult ();
        logger.info ("用户登录信息:" + JSONObject.toJSONString (result));
        BusinessStoreShareVo businessStoreShareVo = JSONObject.toJavaObject (JSONObject.parseObject (param), BusinessStoreShareVo.class);
        boolean flag = false;
        if (businessStoreShareVo.getBusinessCode ().isEmpty ()) {
            logger.error ("请求参数错误，服务业务类型编号不能为空.。");
            result.setMsg ("服务业务类型编号不能为空.。");
            flag = true;
        }
        if (businessStoreShareVo.getCountryCode ().isEmpty ()) {
            logger.error ("请求参数错误，县域编号不能为空.。");
            result.setMsg ("县域编号不能为空.。");
            flag = true;
        }
        if (flag) {
            result.setCode (ResponseCode.INVALID_PARAM_CODE.getCode ());
            result.setStatus (500);
            return result.DTO ();
        }
        try {
            result = this.areaService.updateBusinessByAreaProfit (result, businessStoreShareVo);
        } catch (Exception ex) {
            logger.error ("修改县域利润分成失败" + result.getMsg (),ex);
            result.setStatus (500);
            result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
            result.setMsg (ex.getMessage ());
        } finally {
            this.send (result);
        }
        logger.info ("请求返回结果：" + JSONObject.toJSONString (result.DTO ()));
        return result.DTO ();
    }
}
