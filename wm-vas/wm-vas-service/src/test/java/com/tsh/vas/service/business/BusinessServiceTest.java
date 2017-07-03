package com.tsh.vas.service.business;

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
import com.tsh.vas.enume.BusinessShareWay;
import com.tsh.vas.sdm.service.business.BusinessService;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.vo.business.BusinessSupplierQueryVo;
import com.tsh.vas.vo.business.SupplierBusinessVo;

/**
 * BusinessService Tester.
 *
 * @author Iritchie.ren
 * @version 1.0
 * @since <pre>九月 27, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class BusinessServiceTest extends BaseCaseTest {

    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private BusinessService businessService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: findBusinessSuppliers(Result result, BusinessSupplierQueryVo businessSupplierQueryVo)
     */
    @Test
    public void testFindBusinessSuppliers() throws Exception {
        Result result = getResult ();
        BusinessSupplierQueryVo businessSupplierQueryVo = new BusinessSupplierQueryVo ();
        businessSupplierQueryVo.setBusinessCode ("LTCZ");
        businessSupplierQueryVo.setSupplierName ("永");
        result = this.businessService.findBusinessSuppliers (result, businessSupplierQueryVo);
        System.err.println (JSONObject.toJSONString (businessSupplierQueryVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: updateProfit(Result result, SupplierBusinessVo supplierBusinessVo)
     */
    @Test
    public void testUpdateProfit() throws Exception {
        Result result = getResult ();
        SupplierBusinessVo supplierbusinessVo = new SupplierBusinessVo ();
        supplierbusinessVo.setSupplierCode ("112");
        supplierbusinessVo.setBusinessCode ("LTCZ");
        supplierbusinessVo.setShareWay (2);
        supplierbusinessVo.setShareWay (BusinessShareWay.DISABLE.getCode ());
        supplierbusinessVo.setTotalShareRatio (10.0);
        supplierbusinessVo.setPlatformShareRatio (4.0);
        supplierbusinessVo.setPlatformShareRatio (6.0);
        result = this.businessService.updateProfit (result, supplierbusinessVo);
        System.err.println (JSONObject.toJSONString (supplierbusinessVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: findAllBusiness(Result result)
     */
    @Test
    public void testFindAllBusiness() throws Exception {
        Result result = getResult ();
        result = this.businessService.findAllBusiness (result);
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }
}
