package com.tsh.vas.service.supplier;

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
import com.tsh.vas.sdm.service.supplier.SupplierService;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.vo.supplier.ApplyInfoVo;
import com.tsh.vas.vo.supplier.QuerySupplierVo;
import com.tsh.vas.vo.supplier.SupplierBusinessAreaVo;

/**
 * SupplierService Tester.
 *
 * @author Iritchie.ren
 * @version 1.0
 * @since <pre>九月 20, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class SupplierServiceTest extends BaseCaseTest {
    
    private Logger logger = Logger.getLogger (getClass ());
    
    @Autowired
    private SupplierService supplierService;
    
    @Before
    public void before() throws Exception {
    }
    
    @After
    public void after() throws Exception {
    }
    
    /**
     * Method: addSupplier(Result result, ApplyInfoVo applyInfoVo)
     */
    @Test
    public void testAddSupplier() throws Exception {
        Result result = getResult ();
        ApplyInfoVo applyInfoVo = new ApplyInfoVo ();
        applyInfoVo.setShopSupplierId (112L);
        applyInfoVo.setShopSupplierNo ("tset11112");
        applyInfoVo.setSupplierName ("任欢");
        applyInfoVo.setCompany ("深圳市深海梦想合伙人有限公司");
        applyInfoVo.setEmail ("469656844@qq.com");
        applyInfoVo.setMobile ("18676823505");
        applyInfoVo.setTelphone ("07361510");
        applyInfoVo.setApplyExplain ("完成增值服务项目，为农村提供便利服务");
        List<String> businessCodes = Lists.newArrayList ();
        businessCodes.add ("DJSF");
        businessCodes.add ("DJDF");
        applyInfoVo.setBusinessCodes (businessCodes);
        result = this.supplierService.addSupplier (result, applyInfoVo);
        System.err.println (JSONObject.toJSONString (applyInfoVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
        System.err.println (JSONObject.toJSONString (result));
    }
    
    /**
     * Method: getSupplierInfo(ApplyInfoVo applyInfoVo)
     */
    @Test
    public void testFindSuppliers() throws Exception {
        Result result = getResult ();
        QuerySupplierVo queryParamVo = new QuerySupplierVo ();
        queryParamVo.setSupplierName ("任");
        queryParamVo.setSupplierCode ("111");
        queryParamVo.setPage (1);
        queryParamVo.setRows (5);
        result = this.supplierService.findSuppliers (result, queryParamVo);
        System.err.println (JSONObject.toJSONString (queryParamVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
        System.err.println (JSONObject.toJSONString (result));
    }
    
    /**
     * Method: getAreas(Result result, String supplierCode)
     */
    @Test
    public void testFindAreas() throws Exception {
        Result result = getResult ();
        String supplierCode = "111";
        String businessCode = "DJDF";
        result = this.supplierService.findAreas (result, supplierCode, businessCode);
        System.err.println ("{suplierCode:" + supplierCode + "}");
        System.err.println (JSONObject.toJSONString (result.getData ()));
        System.err.println (JSONObject.toJSONString (result));
    }
    
    /**
     * Method: getSupplierInfo(ApplyInfoVo applyInfoVo)
     */
    @Test
    public void testQuerySupplierInfo() throws Exception {
        Result result = getResult ();
        String supplierCode = "113";
        result = this.supplierService.querySupplierInfo (result, supplierCode);
        System.err.println ("{suplierCode:" + supplierCode + "}");
        System.err.println (JSONObject.toJSONString (result.getData ()));
        System.err.println (JSONObject.toJSONString (result));
    }
    
    /**
     * Method: updateSupplierInfo(Result result, ApplyInfoVo applyInfoVo)
     */
    @Test
    public void testUpdateSupplierInfo() throws Exception {
        Result result = getResult ();
        ApplyInfoVo applyInfoVo = new ApplyInfoVo ();
        applyInfoVo.setSupplierCode ("111");
        applyInfoVo.setSupplierName ("任欢");
        applyInfoVo.setCompany ("深圳市深海梦想合伙人有限公司");
        applyInfoVo.setEmail ("2318784005@qq.com");
        applyInfoVo.setMobile ("18676823505");
        applyInfoVo.setTelphone ("07361510");
        applyInfoVo.setApplyExplain ("完成增值服务项目，为农村提供便利服务");
        List<String> businesCodes = Lists.newArrayList ();
        businesCodes.add ("MPCZ");
        businesCodes.add ("DJSF");
        applyInfoVo.setBusinessCodes (businesCodes);
        result = this.supplierService.updateSupplierInfo (result, applyInfoVo);
        System.err.println (JSONObject.toJSONString (applyInfoVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
        System.err.println (JSONObject.toJSONString (result));
    }
    
    /**
     * Method: checkoutSupplier(Result result, String supplierCode, Integer checkStatus)
     */
    @Test
    public void testCheckoutSupplier() throws Exception {
        Result result = getResult ();
        String supplierCode = "111";
        Integer checkStatus = 2;
        result = this.supplierService.checkoutSupplier (result, supplierCode, checkStatus);
        System.err.println ("{supplierCode:" + supplierCode + ",checkStatus:" + checkStatus + "}");
        System.err.println (JSONObject.toJSONString (result));
    }
    
    /**
     * Method: getSupplierBusinesses(ApplyInfoVo applyInfoVo, SupplierInfo supplierInfo)
     */
    @Test
    public void testBusinessAddArea() throws Exception {
        Result result = getResult ();
        SupplierBusinessAreaVo supplierBusinessareaVo = new SupplierBusinessAreaVo ();
        supplierBusinessareaVo.setSupplierCode ("ZZFW20161101180528710487823");
        supplierBusinessareaVo.setBusinessCode ("DJDF");
        List<String> supplierAreas = Lists.newArrayList ();
        supplierAreas.add ("47");
        supplierAreas.add ("168");
        supplierAreas.add ("164");
        supplierAreas.add ("163");
        supplierBusinessareaVo.setSupplierAreas (supplierAreas);
        result = this.supplierService.businessAddArea (result, supplierBusinessareaVo);
        System.err.println (JSONObject.toJSONString (supplierBusinessareaVo));
        System.err.println (JSONObject.toJSONString (result));
    }

}
