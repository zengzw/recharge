package com.tsh.vas.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dtds.platform.data.redis.RedisSlave;
import com.dtds.platform.sms.enums.SmsEnum;
import com.dtds.platform.sms.factory.SmsFactory;
import com.dtds.platform.sms.sendconfig.SmsResult;
import com.dtds.platform.util.bean.Result;
import com.github.ltsopensource.core.json.JSON;

/**
 * SmsService
 *
 * @author dengjd
 * @date 2016/10/20
 */
@Service
public class SmsService {

    private static Logger logger = LoggerFactory.getLogger(SmsService.class);
    
    
    private final static String bussins_name = "vas_wmvas";
    
    
    /**
     * 发送通知类型短信
     * 
     * @param mobile
     * @param content
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public SmsResult sendSms(String mobile,String content){
        logger.info("------------sms send mobile:" + mobile);
        logger.info("------------sms send content:" + content);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        map.put("content", content);

        String smsSign = SmsEnum.SIGN_TSH.getName();  //短信签名,需在动态配置中事先配置
        int smsType = SmsEnum.TYPE_NOTICE.getCode();  //短信类型:1验证码，2通知，3营销
        SmsResult smsResult = SmsFactory.getInstance(getClass(), bussins_name).send(smsSign, smsType, map);
        logger.info("------------sms send return:" + JSON.toJSONString(smsResult));
        
        return smsResult;
    }
    
    /**
     * 根据类型判断
     * 
     * @param mobile
     * @param content
     * @param sendType  //短信签名,需在动态配置中事先配置
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public SmsResult sendSms(String mobile,String content,SmsEnum sendType){
        logger.info("------------sms send mobile:" + mobile);
        logger.info("------------sms send content:" + content);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        map.put("content", content);
        

        int smsType = SmsEnum.TYPE_NOTICE.getCode();  //短信类型:1验证码，2通知，3营销
        SmsResult smsResult = SmsFactory.getInstance(getClass(), bussins_name).send(sendType.getName(), smsType, map);
        logger.info("------------sms send return:" + JSON.toJSONString(smsResult));
        
        return smsResult;
    }


    /**
     * 发送 验证码类型
     *  
     * @param mobile
     * @param content
     * @return  SmsResult
     *  [code=1, name=短信发送成功, isSuccess=true, smsPlatform=助通短信通道]
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public SmsResult sendVerificationCode(String mobile,String content){
        logger.info("#---sms sendVerificationCode mobile:{}",mobile);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        map.put("content", content);
        String smsSign = SmsEnum.SIGN_TSH.getName();  //短信签名,需在动态配置中事先配置
        int smsType = SmsEnum.TYPE_VERIFICATION_CODE.getCode();  //短信类型:1验证码，2通知，3营销

        SmsResult smsResult =   SmsFactory.getInstance(getClass(),bussins_name).send(smsSign, smsType, map);
        logger.info("#------------sms sendVerificationCode result:{}",JSON.toJSONString(smsResult));
        
        return smsResult;
    }
    
    
    
    /**
     * 手机号码校验
     * 
     * @param result
     * @param key
     * @param code
     * @return
     * @throws Exception
     */
    public Result checkValidateCode(Result result, String key, String code)
            throws Exception {
        String validateCode =  RedisSlave.getInstance().getString(key);
        if (StringUtils.isBlank(validateCode) || StringUtils.isBlank(key)) {
            result.setMsg("验证码验证失败");
            result.setData(false);

            result.setStatus(500);
            return result;
        }
        if (!validateCode.equals(code)) {
            result.setStatus(500);
            result.setMsg("验证码输入错误");
            result.setData(false);
            return result;
        }
        result.setMsg("验证成功");
        result.setData(true);
        result.setStatus(200);
        return result;
    }
}
