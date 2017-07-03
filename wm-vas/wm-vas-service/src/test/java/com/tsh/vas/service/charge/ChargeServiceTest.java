package com.tsh.vas.service.charge;

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
import com.tsh.vas.enume.ClientChargePayStatus;
import com.tsh.vas.sdm.service.charge.ChargeService;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.vo.business.QueryOrderParamVo;
import com.tsh.vas.vo.charge.ChargeSearchVo;

/**
 * ChargeService Tester.
 *
 * @author Iritchie.ren
 * @version 1.0
 * @since <pre>十月 8, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class ChargeServiceTest extends BaseCaseTest {

    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private ChargeService chargeService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: query(Result result, ChargeSearchVo chargeSearchVo)
     */
    @Test
    public void testQuery() throws Exception {
        Result result = getResult ();
        ChargeSearchVo chargeSearchVo = new ChargeSearchVo ();
        /*chargeSearchVo.setStoreCode ("9932");*/
        chargeSearchVo.setPayStatus (ClientChargePayStatus.WAITING.getCode ());
        chargeSearchVo.setPage (1);
        chargeSearchVo.setRows (5);
        result = this.chargeService.query (result, chargeSearchVo);
        System.err.println (JSONObject.toJSONString (chargeSearchVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: details(Result result, String chargeCode)
     */
    @Test
    public void testDetails() throws Exception {
        Result result = getResult ();
        String chargeCode = "DJDF20161026162546350801945";
        result = this.chargeService.details (result, chargeCode);
        System.err.println ("{chargeCode:" + chargeCode + "}");
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: getChargeInfoByPageExport(Result result, QueryOrderParamVo queryOrderParamVo)
     */
    @Test
    public void testGetChargeInfoByPageExport() throws Exception {
        Result result = getResult ();
        QueryOrderParamVo queryOrderParamVo = new QueryOrderParamVo ();
        result = this.chargeService.getChargeInfoByPageExport (result, queryOrderParamVo);
        System.err.println (JSONObject.toJSONString (queryOrderParamVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: getChargeInfoByPage(Result result, QueryOrderParamVo queryOrderParamVo)
     */
    @Test
    public void testGetChargeInfoByPage() throws Exception {
        Result result = getResult ();
        QueryOrderParamVo queryOrderParamVo = new QueryOrderParamVo ();
        queryOrderParamVo.setPage (1);
        queryOrderParamVo.setRows (5);
        result = this.chargeService.getChargeInfoByPage (result, queryOrderParamVo);
        System.err.println (JSONObject.toJSONString (queryOrderParamVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

}
