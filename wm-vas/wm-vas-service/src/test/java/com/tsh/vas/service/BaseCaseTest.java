package com.tsh.vas.service;

import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class BaseCaseTest {

   protected static Logger logger = LoggerFactory.getLogger(BaseCaseTest.class);
    
   static ApplicationContext Context;

    public Result getResult() {
        Result result = new Result ();
        String token = "auc_jsession_8a5624504f1105c3c30515d9df13fa72275ca1595c32a678e378a5850eedde17f6097ee84caf8308";
        //        UserInfo userInfo = (UserInfo) SerializeUtil.unserialize (RedisSlave.getInstance ().get (token));
        UserInfo userInfo = new UserInfo ();
        userInfo.setBizId(24L);
        userInfo.setBelongId(145L);
        userInfo.setToken (token);
        userInfo.setSessionId (token);
        result.setUserInfo (userInfo);
        return result;
    }
}
