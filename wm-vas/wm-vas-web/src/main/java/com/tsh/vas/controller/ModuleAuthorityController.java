package com.tsh.vas.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.phone.util.StringUtils;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.phone.service.ModuleAuthorityService;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.vo.ModuleAuthorityVo;
import com.tsh.vas.vo.QueryModuleAuthorityVo;

/**
 * 权限校验 controller
 * 
 * 主要用户判断当前县域是否开通网点
 */
@Controller
@RequestMapping("/app/vas/")
public class ModuleAuthorityController extends BaseController{
    
    private final static Logger Logger = LoggerFactory.getLogger(ModuleAuthorityController.class);
    

    @Autowired
    private ModuleAuthorityService moduleAuthorityService;


    /**
     * 根据ID查询的数据
     * @param moduleAuthorityVo
     * @return
     */
    @RequestMapping(value = "auth")
    @ResponseBody
    public Object getModuleAuthorityById(ModuleAuthorityVo moduleAuthorityVo){
        logger.info("#--auth req params:" + JSON.toJSONString(moduleAuthorityVo));
        
        Result result = this.getResult();
        UserInfo user = result.getUserInfo();

        ReturnDTO validate = validateUserInfo(user);
        if(validate != null){
            return validate;
        }

        try{
            moduleAuthorityService.queryByCombindTerm(result, moduleAuthorityVo);
        }catch(BusinessRuntimeException exception){
            logger.error(exception.getMessage(), exception);
            return responseFail(exception.getErrMsg());
        }
        Object msg = (null == result.getData())? "" : result.getData();
        return responseSuccess("获取成功", msg);
    }


    /**
     * 活动分页查询发布列表、
     * 
     * @param page
     * @param rows
     * @param params 查询对象
     * 
     * @return 返回活动列表
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "phone/activity/list")
    @ResponseBody
    public Pagination queryCityInfoPage(int page,int rows,String params){
        Result result = this.getResult();
        Pagination pagination = null;
        Page pageInfo = new Page(page,rows);

        QueryModuleAuthorityVo queryParams = JSONObject.parseObject(params, QueryModuleAuthorityVo.class);
        if(queryParams == null){
            queryParams = new QueryModuleAuthorityVo();
        }

        result =  moduleAuthorityService.queryPagePhoneActivityArea(result, pageInfo, queryParams);
        pagination = (Pagination)result.getData();

        return pagination;
    }



    /**
     * 活动分页查询发布列表、
     * 
     * @param page
     * @param rows
     * @param params 查询对象
     * 
     * @return 返回活动列表
     */
    @RequestMapping(value = "activity/save",method = RequestMethod.POST)
    @ResponseBody
    public Object saveActivity( @RequestBody ModuleAuthorityVo moduleAuthorityVo){
        Logger.info("#--->activity/save 请求参数:{}",JSON.toJSONString(moduleAuthorityVo));
        
        
        System.out.println(DateUtil.date2String(moduleAuthorityVo.getBeginTime()));
        
        Result result = this.getResult();
        try{
            
            moduleAuthorityService.saveYYMDActivity(result, moduleAuthorityVo);
            
        }catch(Exception e){
            logger.error("设置权限失败",e);
            
            return responseFail(getErrorMessage(e));
        }

        return result.DTO();
    }
    
    
    
    /**
     * 修改活动时间
     * 
     * @param params
     * @return
     */
    @RequestMapping(value = "/activity/update",method = RequestMethod.POST)
    @ResponseBody
    public Object updateActivity(@RequestBody ModuleAuthorityVo moduleAuthorityVo){
        Logger.info("#--->activity/update 请求参数:{}",JSON.toJSONString(moduleAuthorityVo));
        
        Result result = this.getResult();
        if(moduleAuthorityVo == null){
            result.setStatus(HttpResponseConstants.ERROR);
            result.setMsg("参数为空");
            return result.DTO();
        }
        
        try{
            moduleAuthorityService.updateYYMDActivity(result, moduleAuthorityVo);
            
        }catch(Exception e){
            logger.error("修改活动时间失败",e);
            
            return responseFail(getErrorMessage(e));
        }
        
        return result.DTO();
    }

}
