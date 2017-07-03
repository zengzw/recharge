package com.tsh.vas.service.area;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.sdm.service.area.AreaService;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.vo.area.BusinessStoreShareVo;

/**
 * AreaService Tester.
 *
 * @author Iritchie.ren
 * @version 1.0
 * @since <pre>九月 28, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class AreaServiceTest extends BaseCaseTest {

    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private AreaService areaService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: queryBusinessByArea(Result result, String countryCode)
     */
    @Test
    public void testQueryBusinessByArea() throws Exception {
        Result result = getResult ();
        String countryCode = "123";
        result = this.areaService.queryBusinessByAreaProfit (result, countryCode);
        System.err.println ("{countryCode:" + countryCode + "}");
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: updateBusinessByAreaProfit(Result result, BusinessStoreShareVo businessStoreShareVo)
     */
    @Test
    public void testUpdateBusinessByAreaProfit() throws Exception {
        Result result = getResult ();
        BusinessStoreShareVo businessStoreShareVo = new BusinessStoreShareVo ();
        businessStoreShareVo.setCountryCode ("123");
        businessStoreShareVo.setBusinessCode ("LTCZ");
        businessStoreShareVo.setCountryName ("宁乡运营中心");
        businessStoreShareVo.setCountryShareRatio (15.0);
        businessStoreShareVo.setStoreShareRatio (75.0);
        result = this.areaService.updateBusinessByAreaProfit (result, businessStoreShareVo);
        System.err.println (JSONObject.toJSONString (businessStoreShareVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }
}
