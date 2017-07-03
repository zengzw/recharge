package com.tsh.vas.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.dubbo.bis.api.ShopApi;
import com.tsh.dubbo.bis.vo.ShopVO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.service.AreaApiService;

/**
 * 公共contorller
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController extends BaseController {

    @Autowired
    private AreaApiService commonService;
    @Autowired
    private ShopApi shopApi;

    /**
     * 根据省份名称和城市名称获取下面所有的县域
     *
     * @param provinceName
     * @param cityName
     * @return
     * @author Administrator <br>
     * @Date 2016年10月8日<br>
     */
    @RequestMapping(value = "/queryAreasByProvinceAndCityAndArea.do")
    @ResponseBody
    public Object queryAreasByProvinceAndCityAndArea(@RequestParam(value = "provinceName") String provinceName, @RequestParam(value = "cityName") String cityName) {
        Result result = getResult ();
        try {
            commonService.queryAreasByProvinceAndCityAndArea (result, provinceName, cityName);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("根据省份名称和城市名称获取下面所有的县域 失败", e);
        } finally {
            this.send (result);
        }
        logger.info ("请求返回结果:" + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
    }

    /**
     * 根据县域ID获取下面所有的网点
     *
     * @param areaId
     * @return
     * @author Administrator <br>
     * @Date 2016年10月8日<br>
     */
    @RequestMapping(value = "/queryStoresByAreaId.do")
    @ResponseBody
    public Object queryByAreaId(@RequestParam(value = "areaId") String areaId) {
        Result result = getResult ();
        try {
            commonService.queryStoresByAreaId (result, areaId);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("根据省份名称和城市名称获取下面所有的县域 失败", e);
        } finally {
            this.send (result);
        }
        logger.info ("请求返回结果：" + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
    }

    /**
     * 根据根据输入的县域id获取下面的网点
     * @param areaId
     * @return
     */
    @RequestMapping(value = "/getShopByInputAreaId.do")
    @ResponseBody
    public Object getShop(Integer areaId) {
        Result result = this.getResult ();
        try {
            List<ShopVO> shopVOs = shopApi.getShopList (result, areaId).getData ();
            result.setData(shopVOs);
        } catch (Exception e) {
            this.error (result, e);
            logger.error("根据根据输入的县域id获取下面的网点 失败", e);
        } finally {
            this.send (result);
        }
        return result.getData ();
    }

    /**
     * 获取登录信息
     *
     * @return
     * @author Administrator <br>
     * @Date 2016年10月13日<br>
     */
    @RequestMapping(value = "/getLoginInfo.do")
    @ResponseBody
    public Object getLoginInfo() {
        Result result = getResult ();
        try {
            UserInfo userInfo = result.getUserInfo ();
            if (null != userInfo && userInfo.getRoleType () == 3) {
                UserInfo rspUser = new UserInfo();
                rspUser.setRoleType(userInfo.getRoleType());
                result.setData(rspUser);
            } else {
                result.setData ("");
            }
        } catch (Exception e) {
            this.error (result, e);
            logger.error("获取登录信息 错误", e);
        } finally {
            this.send (result);
        }
        logger.info ("请求返回结果:" + JSONObject.toJSONString (result.DTO ()));
        return result.getData ();
    }
}

