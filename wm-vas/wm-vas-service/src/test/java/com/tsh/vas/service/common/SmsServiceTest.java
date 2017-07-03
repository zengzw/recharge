package com.tsh.vas.service.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.service.SmsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class SmsServiceTest {

    @Autowired
    private SmsService smsService;

    @Test
    public void testSendSms() throws Exception {
//        String mobile = "13715168307";
        String mobile = "15811824071";
        String content = "亲爱的18926452220，订单号:888888839,请到充值的网点领取。";
        smsService.sendSms(mobile, content);
    }
}