package com.tsh.broker.utils;

import com.tsh.broker.enumType.BizzTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Md5SignUtils
 *
 * @author dengjd
 * @date 2016/9/29
 */
public class SignUtils {

    private static Map<BizzTypeEnum,PairSign> bizzSignMap = new HashMap<BizzTypeEnum, PairSign>();
    static {
        bizzSignMap.put(BizzTypeEnum.DEFAULT,new DefaultSign());
        bizzSignMap.put(BizzTypeEnum.SALER_WISE,new SalerWiseSign());
        bizzSignMap.put(BizzTypeEnum.OF_PAY,new OfPaySign());
    }

    public static String bizzSign(BizzTypeEnum bizzTypeEnum,LinkedHashMap<String,String> params,String signKey)  {
        PairSign pairSign = bizzSignMap.get(bizzTypeEnum);
        if(pairSign == null)
            throw  new RuntimeException("pairSign type not exist" + bizzTypeEnum);

        try {
            return pairSign.sign(params,signKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static abstract class PairSign {
        public String build(Map<String,String> params) {
            if(params == null)
                throw  new  IllegalArgumentException("params argument is null");
            StringBuffer  buffer = new StringBuffer();
            for(String value :  params.values()){
                buffer.append(value);
            }
            return buffer.toString();
        }

        public abstract String sign(LinkedHashMap<String,String> params,String signKey) throws Exception;
    }

    public static class SalerWiseSign  extends PairSign {
        @Override
        public String sign(LinkedHashMap<String, String> params, String signKey) throws Exception {
            if(params == null)
                throw new IllegalArgumentException("params argument is null");
            if(StringUtils.isBlank(signKey))
                throw new IllegalArgumentException("signKey argument is blank");

            return Md5Digest.encryptMD5(build(params) + signKey).toUpperCase().substring(0, 16);
        }
    }

    public static class OfPaySign  extends PairSign {
        @Override
        public String sign(LinkedHashMap<String, String> params, String signKey) throws Exception {
            if(params == null)
                throw new IllegalArgumentException("params argument is null");
            if(StringUtils.isBlank(signKey))
                throw new IllegalArgumentException("signKey argument is blank");

            return Md5Digest.encryptMD5(build(params) + signKey);
        }
    }

    public static class DefaultSign extends PairSign {

        @Override
        public String sign(LinkedHashMap<String, String> params, String signKey) throws Exception {
            if(params == null)
                throw new IllegalArgumentException("params argument is null");
            if(StringUtils.isBlank(signKey))
                throw new IllegalArgumentException("signKey argument is blank");

            return Md5Digest.encryptMD5(build(params) + signKey).toUpperCase();
        }
    }


}
