package com.tsh.traintickets.dal.service.kuyou.httphandler;

import com.traintickets.common.BizException;
import com.traintickets.common.TicketsConstants;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.utils.DateUtils;
import com.traintickets.common.utils.Md5Digest;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/11/16 016.
 */
public abstract class AbstractKuyouHttpHandler {

    private static Logger logger = LogManager.getLogger(AbstractKuyouHttpHandler.class);

    protected abstract String getMethodName();

    protected String getTimestamp(){
        return DateUtils.format(new Date(), DateUtils.DATE_PATTERN_1);
    }

    protected Map<String, String> init(String timestamp){
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("terminal", DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_terminal));
        paramsMap.put("merchant_id", DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_merchantId));
        paramsMap.put("timestamp", timestamp);
        paramsMap.put("type", this.getMethodName());
        paramsMap.put("version", DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_version));
        return paramsMap;
    }

    protected String getHeaderParamsString(String timestamp){
        // 加密字符串header
        StringBuilder headerBuild = new StringBuilder();
        headerBuild = headerBuild.append(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_terminal))
                .append(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_merchantId))
                .append(timestamp)
                .append(this.getMethodName())
                .append(DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_version));
        return headerBuild.toString();
    }

    protected String getEncrypt(String value){
        try {
            logger.info("encrypt :" + value);
            return Md5Digest.encryptMD5(StringEscapeUtils.unescapeJava(value + DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_hmac)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException(ResponseCode.ENCRYPT_ERROR);
        }
    }

    protected String getISO(String result){
        try {
            byte[] b = result.getBytes(TicketsConstants.ISO88591);
            return new String(b,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw new BizException(ResponseCode.ISO88591_ERROR);
        }
    }

    protected String getUtf8(String result)  {
        if(null != result){
            try {
                return new String(result.getBytes(TicketsConstants.ISO88591), TicketsConstants.UTF8);
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
                throw new BizException(ResponseCode.CODING_CHANGE_ERROR);
            }
        }
        return null;
    }


}
