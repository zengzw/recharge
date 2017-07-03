package com.tsh.vas.service.business;

import java.util.List;

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
import com.google.common.collect.Lists;
import com.tsh.vas.sdm.service.business.AreaSupplierService;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.vo.business.QueryAreaParam;
import com.tsh.vas.vo.business.SupplierOrderParamVo;

/**
 * AreaSupplierService Tester.
 *
 * @author Iritchie.ren
 * @version 1.0
 * @since <pre>九月 23, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class AreaSupplierServiceTest extends BaseCaseTest {

    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private AreaSupplierService areaSupplierService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: findBusinessAreaSuppliers(Result result, QueryParam queryParam)
     */
    @Test
    public void testFindBusinessAreas() throws Exception {
        Result result = getResult ();
        QueryAreaParam queryAreaParam = new QueryAreaParam ();
        //        queryAreaParam.setSupplierCode ("111");
        queryAreaParam.setBusinessCode ("DJDF");
        //        queryAreaParam.setProvince ("湖南省");
        //        queryAreaParam.setCity ("长沙市");
        //        queryAreaParam.setCountryCode ("123");
        //        queryAreaParam.setCountryName ("运营");
        queryAreaParam.setPage (1);
        queryAreaParam.setRows (5);
        result = this.areaSupplierService.findBusinessAreas (result, queryAreaParam);
        System.err.println (JSONObject.toJSONString (queryAreaParam));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

    /**
     * Method: queryBusinessAreaDetails(Result result, String businessCode, String countryCode)
     */
    @Test
    public void testQueryBusinessAreaDetails() throws Exception {
        Result result = getResult ();
        String countryCode = "123";
        String businessCode = "LTCZ";
        result = this.areaSupplierService.queryBusinessAreaDetails (result, businessCode, countryCode);
        System.err.println ("{countryCode:" + countryCode + "}");
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }


    /**
     * Method: updateSupplierOrder(Result result, List<SupplierAreaBusiness> supplierAreaBusinesses)
     */
    @Test
    public void testUpdateSupplierOrder() throws Exception {
        Result result = getResult ();
        List<SupplierOrderParamVo> supplierOrderParamVoList = Lists.newArrayList ();
        SupplierOrderParamVo SupplierOrderParamVo1 = new SupplierOrderParamVo ();
        SupplierOrderParamVo1.setBusinessCode ("DJDF");
        SupplierOrderParamVo1.setCountryCode ("124");
        SupplierOrderParamVo1.setSupplierCode ("111");
        SupplierOrderParamVo1.setSupplierOrder (1);
        supplierOrderParamVoList.add (SupplierOrderParamVo1);
        result = this.areaSupplierService.updateSupplierOrder (result, supplierOrderParamVoList);
        System.err.println (JSONObject.toJSONString (supplierOrderParamVoList));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }

}
