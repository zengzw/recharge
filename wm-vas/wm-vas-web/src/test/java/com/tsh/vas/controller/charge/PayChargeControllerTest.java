package com.tsh.vas.controller.charge;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.tsh.broker.utils.HttpXmlClient;

/**
 * 测试类
 */
public class PayChargeControllerTest {

	private static final Logger LOG = Logger.getLogger(PayChargeControllerTest.class);

    /**
     * 测试1
     */
    @Test
    public void testGetOrganizationInfo() {

        try {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("param", "{\"businessCode\":\"DJDF\"}");
            paramsMap.put("token", "auc_jsession_9c64d5b548a009be04a0ebe5a3b42f82d848062a");
            String response = HttpXmlClient.post("http://testwmvas.tsh.cn/vas/charge/get/organization/info", paramsMap);
            LOG.info(response);
        } catch (Exception e){
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 测试2
     */
    @Test
    public void testGetBillInfo() {
        try {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("param", "{\"data\":\"{\\\"chargeOrgName\\\":\\\"国网浙江省电力公司\\\",\\\"county\\\":\\\"null\\\",\\\"province\\\":\\\"浙江\\\",\\\"accountType\\\":\\\"1\\\",\\\"payType\\\":\\\"2\\\",\\\"account\\\":\\\"9033841019\\\",\\\"chargeOrgCode\\\":\\\"v85002\\\",\\\"city\\\":\\\"杭州\\\",\\\"productId\\\":\\\"64156301\\\"}\",\"supplierToken\":\"b429d7cefe48d55e\",\"serverAddr\":\"http://172.16.3.54:8210/sdm/ofpay/mock/\",\"supplierCode\":\"100001\"}\n");
            paramsMap.put("token", "auc_jsession_f3c453c26ff87f1f1155541a535ca0506b376f1e");
            String response = HttpXmlClient.post("http://testwmvas.tsh.cn/vas/charge/get/bill/info", paramsMap);
            LOG.info(response);
        } catch (Exception e){
            LOG.error(e.getMessage(), e);
        }
    }
}