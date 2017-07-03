package com.tsh.vas.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.commons.utility.StringUtil;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.security.UserInfo;
import com.dtds.platform.web.controller.PlatformController;
import com.dtds.platform.web.userinfo.UserUtil;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;

/**
 * 扩展平台controller
 */
@Scope("prototype")
public class BaseController extends PlatformController {

    @Override
    public Result getResult() {
        UserInfo userInfo = UserUtil.getUserInfo (request);
        logger.info("-------------> userInfo:" + JSON.toJSONString(userInfo));
        String teamName = "wm-vas";
        return new Result (request, userInfo, teamName);
    }


    /**
     * 返回成功
     * @param msg
     * @param data
     * @return
     */
    public ReturnDTO responseSuccess(String msg,Object data){
        ReturnDTO dto = new ReturnDTO();
        dto.setStatus(HttpResponseConstants.SUCCESS);
        dto.setMsg(msg);
        dto.setData(data == null? "":data);

        return dto;

    }

    /**
     * 返回成功
     * @param msg
     * @param data
     * @return
     */
    public ReturnDTO responsePageSuccess(String msg,Object data,int page){
        ReturnDTO dto = new ReturnDTO();
        dto.setStatus(HttpResponseConstants.SUCCESS);
        dto.setMsg(msg);
        dto.setCode(page);
        dto.setData(data == null? "":data);

        return dto;

    }

    /**
     * 返回成功
     * @param msg
     * @return
     */
    public ReturnDTO responseSuccess(String msg){
        ReturnDTO dto = new ReturnDTO();
        dto.setStatus(HttpResponseConstants.SUCCESS);
        dto.setMsg(msg);
        dto.setData("");
        
        return dto;
        
    }
    
    
    /**
     * 返回成功
     * @param msg
     * @return
     */
    public ReturnDTO responseSuccess(String msg,int code){
        ReturnDTO dto = new ReturnDTO();
        dto.setStatus(HttpResponseConstants.SUCCESS);
        dto.setMsg(msg);
        dto.setCode(code);
        dto.setData("");
        
        return dto;
        
    }

    /**
     * 返回成功
     * @param msg
     * @return
     */
    public ReturnDTO responseSuccessWithErrorCode(String msg){
        ReturnDTO dto = new ReturnDTO();
        dto.setStatus(HttpResponseConstants.SUCCESS);
        dto.setMsg(msg);
        dto.setCode(HttpResponseConstants.ERROR);
        dto.setData("");

        return dto;

    }

    /**
     * 返回失败
     * @param msg
     * @param code
     * @return
     */
    public ReturnDTO responseFail(String msg,int code){
        ReturnDTO dto = new ReturnDTO();
        dto.setStatus(HttpResponseConstants.ERROR);
        dto.setCode(code);
        dto.setMsg(msg);
        dto.setData("");

        return dto;

    }

    /**
     * 返回失败
     * @param msg
     * @return
     */
    public ReturnDTO responseFail(String msg){
        ReturnDTO dto = new ReturnDTO();
        dto.setStatus(HttpResponseConstants.ERROR);
        dto.setMsg(msg);
        dto.setData("");

        return dto;

    }
    

    /**
     * 获取异常信息
     * 
     * @param ex
     * @return
     */
    public static  String getErrorMessage(Throwable ex) {
        if(ex == null) {
            return "";
        }

        Throwable next = ex;
        while (next.getCause() != null) {
            next = next.getCause();
        }

        return next.getMessage();
    }
    
    
    /**
     * 用户校验
     * @param userInfo
     * @return
     */
    public ReturnDTO validateUserInfo(UserInfo userInfo){
        if(userInfo == null){
            return this.responseFail("用户信息为空");
        }
        
        return null;
    }
    
    
    /**
     * 判断收入是否为空
     * 
     * @param params
     * @return
     */
    public ReturnDTO validateRequestParams(String params){
        if(StringUtils.isEmpty(params)){
            return this.responseFail("缺少必要参数");
        }
        
        return null;
    }
    
    /**
     * 下载Excel文件
     *
     * @param request
     * @param response
     * @param file
     * @param fileName
     * @throws IOException
     */
    protected void downloadExcel(HttpServletRequest request, HttpServletResponse response, File file, String fileName) throws IOException {
        byte[] data;
        OutputStream outputStream;
        data = FileUtils.readFileToByteArray (file);
        String userAgent = request.getHeader ("User-Agent");
        response.reset ();
        response.setHeader ("Content-Disposition", "attachment; filename=" + StringUtil.encodeFileName (fileName + ".xls", userAgent));
        response.addHeader ("Content-Length",String.valueOf(data.length));
        response.setContentType ("application/octet-stream;charset=UTF-8");
        outputStream = new BufferedOutputStream (response.getOutputStream ());
        outputStream.write (data);
        outputStream.flush ();
        FileUtils.deleteQuietly (file);
        outputStream.close();
    }

    
}
