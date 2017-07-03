package com.tsh.openpf.utils;

import com.tsh.broker.enumType.BizzTypeEnum;
import com.tsh.broker.utils.SignUtils;
import com.tsh.openpf.vo.request.BizzServiceRequest;
import com.tsh.openpf.vo.request.RegisterBizzServRequest;

import java.util.LinkedHashMap;

/**
 * SdmRequestSignUtils
 *
 * @author dengjd
 * @date 2016/10/10
 */
public class ServRegisterSignUtils {

    public static  String signCreateBusinessService(BizzServiceRequest bizzServiceRequest,String signKey){
        LinkedHashMap<String,String> queryPayParams = new LinkedHashMap<String,String>();
        queryPayParams.put("businessId", bizzServiceRequest.getBusinessId());
        queryPayParams.put("timestamp", bizzServiceRequest.getTimestamp());

        return SignUtils.bizzSign(BizzTypeEnum.DEFAULT, queryPayParams, signKey);
    }

    public static  String signQueryBizzService(BizzServiceRequest bizzServiceRequest,String signKey){
        LinkedHashMap<String,String> queryPayParams = new LinkedHashMap<String,String>();
        queryPayParams.put("businessId", bizzServiceRequest.getBusinessId());
        queryPayParams.put("timestamp", bizzServiceRequest.getTimestamp());

        return SignUtils.bizzSign(BizzTypeEnum.DEFAULT, queryPayParams, signKey);
    }

    public static  String signRegisterBizzService(RegisterBizzServRequest registerBizzServRequest,String signKey){
        LinkedHashMap<String,String> queryPayParams = new LinkedHashMap<String,String>();
        queryPayParams.put("businessId", registerBizzServRequest.getBusinessId());
        queryPayParams.put("serviceAddr", registerBizzServRequest.getServiceAddr());
        queryPayParams.put("timestamp", registerBizzServRequest.getTimestamp());


        return SignUtils.bizzSign(BizzTypeEnum.SALER_WISE, queryPayParams, signKey);
    }


}
