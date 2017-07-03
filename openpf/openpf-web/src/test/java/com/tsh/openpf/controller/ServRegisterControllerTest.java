package com.tsh.openpf.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.HttpXmlClient;
import com.tsh.broker.utils.ObjectMapUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.openpf.po.ServiceRegister;
import com.tsh.openpf.utils.ServRegisterSignUtils;
import com.tsh.openpf.vo.request.BizzServiceRequest;
import com.tsh.openpf.vo.request.RegisterBizzServRequest;

public class ServRegisterControllerTest {

    @Test
    public void testCreateBusinessService() throws Exception {
        BizzServiceRequest bizzServiceRequest = new BizzServiceRequest();
        bizzServiceRequest.setBusinessId("ZZFW20161029152334397419286");
        bizzServiceRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = ServRegisterSignUtils.signCreateBusinessService(bizzServiceRequest, "123456");
        bizzServiceRequest.setSigned(signed);

        Map<String,String> map = ObjectMapUtils.toStringMap(bizzServiceRequest);
        String  response =  HttpXmlClient.post("http://testopenpf.tsh.cn/openpf/createBizzService.do", map);
        System.out.println(response);
    }

    @Test
    public void testQueryBizzService() throws Exception {
        BizzServiceRequest bizzServiceRequest = new BizzServiceRequest();
        bizzServiceRequest.setBusinessCode("HC2P");
        bizzServiceRequest.setBusinessId("ZZFW20161125171645598912922");
        bizzServiceRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = ServRegisterSignUtils.signQueryBizzService(bizzServiceRequest, "123456");
        bizzServiceRequest.setSigned(signed);

        Map<String,String> map = ObjectMapUtils.toStringMap(bizzServiceRequest);
        String  response =  HttpXmlClient.post("http://127.0.0.1:8202/openpf/queryBizzService.do", map);
        System.out.println(response);
        
        ResponseWrapper result = JSON.parseObject(response,ResponseWrapper.class);
        
        List<ServiceRegister> service = (List<ServiceRegister>)result.getData();
        System.out.println(CollectionUtils.isEmpty(service));
    }

    @Test
    public void testRegisterBizzService() throws Exception {
        RegisterBizzServRequest registerBizzServRequest = new RegisterBizzServRequest();
        registerBizzServRequest.setBusinessId("ZZFW20161029152334397419286");
        registerBizzServRequest.setServiceAddr("http://broker.tsh.com/sdm/salerwise/");
        registerBizzServRequest.setTimestamp(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String signed = ServRegisterSignUtils.signRegisterBizzService(registerBizzServRequest, "b429d7cefe48d55e");
        registerBizzServRequest.setSigned(signed);

        Map<String,String> map = ObjectMapUtils.toStringMap(registerBizzServRequest);
        String  response =  HttpXmlClient.post("http://openpf.tsh.com/openpf/registerBizzService.do", map);
        System.out.println(response);
        
     
    }
}