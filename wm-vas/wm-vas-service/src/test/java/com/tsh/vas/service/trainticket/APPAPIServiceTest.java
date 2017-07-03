/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service.trainticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.data.redis.RedisSlave;
import com.dtds.platform.util.SerializeUtil;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.traintickets.common.utils.JsonUtils;
import com.tsh.dubbo.bis.api.SupplierApi;
import com.tsh.dubbo.bis.vo.SupplierVO;
import com.tsh.traintickets.vo.response.CheckTicketNumResponse;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.enume.RoleType;
import com.tsh.vas.service.BaseCaseTest;
import com.tsh.vas.trainticket.commoms.HttpUtils;
import com.tsh.vas.trainticket.constants.HttpResponseConstants;
import com.tsh.vas.trainticket.constants.UrlConstants;
import com.tsh.vas.trainticket.service.APPAPIService;
import com.tsh.vas.trainticket.vo.QueryAccOrderStatusVO;
import com.tsh.vas.trainticket.vo.req.RequestOrderBackParam;
import com.tsh.vas.trainticket.vo.req.RequestQueryOrderParam;
import com.tsh.vas.trainticket.vo.req.RequestQuerySubwayStationParam;
import com.tsh.vas.trainticket.vo.req.RequestQueryTicketNumParam;
import com.tsh.vas.trainticket.vo.req.RequestQueryTicketParam;
import com.tsh.vas.trainticket.vo.req.RequestReturnTicketBackParam;
import com.tsh.vas.trainticket.vo.req.RequestReturnTicketParam;
import com.tsh.vas.trainticket.vo.resp.QueryAccOrderInfoStatusResult;
import com.tsh.vas.vo.trainticket.HcpOrderInfoVo;
import com.tsh.vas.vo.trainticket.TrainUserInfoVo;

/**
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:wm-vas-service.xml"})
public class APPAPIServiceTest extends BaseCaseTest{

    @Autowired
    private APPAPIService appService;


    @Autowired
    private SupplierApi supplierApi;



    @Test
    public void testJedis(){
   
        ListOperations<String,Object> list = RedisSlave.getInstance().getRedistemplate().opsForList();
        list.leftPush("recharge", "value1");
        list.leftPush("recharge", "value2");
        
      System.out.println(RedisSlave.getInstance().length("recharge"));
        System.out.println(list.leftPop("recharge"));
        System.out.println(list.leftPop("recharge"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(RedisSlave.getInstance().length("recharge"));
        System.out.println(list.size("recharge"));
        System.out.println(list.leftPop("recharge"));
        System.out.println(list.leftPop("recharge"));
        

    }
    
    @Test
    public void testJedis2(){
        
        RedisSlave list = RedisSlave.getInstance();
        list.in("recharge", "value1");
        list.in("recharge", "value2");
        
        System.out.println(list.out("recharge"));
        System.out.println(list.out("recharge"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(list.out("recharge"));
        System.out.println(list.out("recharge"));
        
    }
    
    
    @Test
    public void testJedis3(){
        
        RedisSlave list = RedisSlave.getInstance();
        for(int  i =0; i < 2; i++){
            list.in("recharge","value"+i);
        }
        list.in("recharge","value0");
        
        for(int  i =0; i < 3; i++){
            System.out.println(list.out("recharge"));
        }
      
    
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(list.pop("recharge"));
        System.out.println(list.pop("recharge"));
        
    }


    @Test
    public void testGetSuuplier() throws Exception{
        Result result = getResult();
        //获取供应商信息Dubbo
        SupplierVO supplierVO = supplierApi.getSupplier(result,41).getData();
        System.out.println(JSON.toJSONString(supplierVO));

    }

    /**
     * 火车票查询
     */
    @Test
    public void testQueryTickets(){
        String travelTime = "2016-12-15";
        String from = "深圳";
        String to = "长沙";
        RequestQueryTicketParam param = new RequestQueryTicketParam();
        param.setTravelTime(travelTime);
        param.setFromStation(from);
        param.setArriveStation(to);
        param.setSupplierCode("ZZFW20161129095457360057588");
        Result result = new Result();
        appService.queryTrainList(result,param);

        System.out.println("-->"+JSON.toJSONString(result.getData()));
    }


    /**
     * 获取用户所在城市 
     */
    @Test
    public void testUserLocation() throws Exception{
        Result result = this.getResult();
        appService.getUserCityByUser(result, result.getUserInfo());

        System.out.println(result.getData());
    }



    /**
     * 查询订单详情
     */
    @Test
    public void testQueryOrderInfo(){
        Result result = this.getResult();
        RequestQueryOrderParam param = new RequestQueryOrderParam();
        param.setMerchantOrderId("");
        param.setOrderId("");

        appService.queryOrderInfoForRefund(result, param);
        System.out.println("==>"+JSON.toJSONString(result.getData()));
    }


    /**
     * 查询途经点
     */
    @Test
    public void testQuerySubwayStation(){

        Result result = this.getResult();
        String trainCode = "K9018";

        RequestQuerySubwayStationParam queryTicketParam = new RequestQuerySubwayStationParam();
        queryTicketParam.setTrainCode(trainCode);
        queryTicketParam.setSupplierCode("ZZFW20161129095457360057588");

        appService.querySubwayStation(result, queryTicketParam);

        System.out.println("===>"+JSON.toJSONString(result.getData()));
    }



    /**
     * 查询车次余票
     */
    @Test
    public void testQueryTicketNum(){
        Result result = this.getResult();
        RequestQueryTicketNumParam param = new RequestQueryTicketNumParam();
        param.setTravelTime("2016-11-26 11:00:00");
        param.setFromStation("深圳");
        param.setArriveStation("长沙");
        param.setTrainCode("K9018");

        appService.queryTicketNum(result, param);

        System.out.println("===>"+JSON.toJSONString(result.getData()));
    }



    /**
     * 校验用户信息
     * 
     */
    @Test
    public void verifyUserInfo(){
        Result result = this.getResult();
        /*
        List<VerifyUserModel> lstUser = new ArrayList<>();
        VerifyUserModel model1 = new VerifyUserModel();
        model1.setIdType(EnumCertificateType.ID_Card2.getCode()+"");
        model1.setUserId("44512111857455696");
        model1.setUserName("小明");

        lstUser.add(model1);
//        appService.verifyUsers(result, lstUser);


        System.out.println("--->:"+JSON.toJSONString(result.getData()));*/
    }




    /**
     *  支付下单
     */
    @Test
    public void testPayChargeInfo() throws Exception {
        //网点代付
        Result result = getResult ();
        UserInfo userInfo = new UserInfo ();
        userInfo.setUserId (163L);
        userInfo.setBizId (51L);
        result.setUserInfo (userInfo);
        HcpOrderInfoVo orderInfoVo = new HcpOrderInfoVo();
        //欧飞100001
        orderInfoVo.setSupplierCode ("ZZFW20161129095457360057588");
        orderInfoVo.setBusinessCode ("HCP");
        orderInfoVo.setMobile ("15811824071");
        orderInfoVo.setOriginalAmount (new BigDecimal (50));
        orderInfoVo.setRealAmount (new BigDecimal (60));
        orderInfoVo.setBizType (RoleType.SHOP.getCode ());
        orderInfoVo.setUserName("zengzw");
        orderInfoVo.setPayPassword ("123456");
        orderInfoVo.setSupplierToken ("31bcc20dfee13328");
        orderInfoVo.setServerAddr ("http://brokerhcp.tsh.com/traintickets/kuyou/");

        orderInfoVo.setFromStation("深圳");
        orderInfoVo.setArriveStation("长沙");
        orderInfoVo.setCostingAmount(new BigDecimal(100));
        orderInfoVo.setRealAmount(new BigDecimal(105));
        orderInfoVo.setOriginalAmount(new BigDecimal(105));
        orderInfoVo.setStationStartTime("20:09");
        orderInfoVo.setStationArriveTime("06:10");
        orderInfoVo.setSeatType(8);
        orderInfoVo.setTrainCode("K9018");
        orderInfoVo.setTicketPrice(100D);
        orderInfoVo.setTravelTime("2016-12-07");
        orderInfoVo.setServicePrice(5D);
        orderInfoVo.setTicketType("0");


        List<TrainUserInfoVo> userDetailList = new ArrayList<>();
        TrainUserInfoVo user = new TrainUserInfoVo();
        user.setIdType(1);
        user.setTicketType("0"); //成人票
        user.setUserId("5858784712341358998");
        user.setUserName("zengzw");
        userDetailList.add(user);
        TrainUserInfoVo user1 = new TrainUserInfoVo();
        user1.setIdType(1);
        user1.setTicketType("0"); //成人票
        user1.setUserId("5858784712341358998");
        user1.setUserName("zengzw");
        userDetailList.add(user1);

        TrainUserInfoVo user2 = new TrainUserInfoVo();
        user2.setIdType(1);
        user2.setTicketType("0"); //成人票
        user2.setUserId("5858784712341358998");
        user2.setUserName("zengzw");
        userDetailList.add(user2);
        orderInfoVo.setUserDetailList(userDetailList);


        result = this.appService.payOdrerInfo(result, orderInfoVo);
        System.err.println (JSONObject.toJSONString (orderInfoVo));
        System.err.println (JSONObject.toJSONString (result.getData ()));
    }



    /**
     * 购票回调
     * @throws  
     */
    @Test
    public void testOrderBack(){
        Result result = this.getResult();
        RequestOrderBackParam params = new RequestOrderBackParam();
        params.setMerchantOrderId("SPHCPKUYOU201612061759358240");//订单编号
        params.setTradeNo("zf-SPHCPKUYOU201612061801034208");
        params.setStatus("FAILURE"); //SUCCESS/FAILURE
        params.setAmount("105.00");//支付金额
        params.setRefundAmount("");//refundAmount 退款金额
        params.setRefundType("");//退款类型:  001：差额退款   002：出票失败退款
        params.setOrderId("sp-SPHCPKUYOU201612061801034208");
        params.setOutTicketBillno("ot-SPHCPKUYOU201612061801034208");
        params.setOutTicketTime("2016-12-8 10:01:01");
        params.setTrainBox("z-91");
        params.setSeatNo("20D");
        params.setSeatType("0"); //座位类型
        /*    0、商务座1、特等座2、一等座3、二等座
        4、高级软卧5、软卧6、硬卧7、软座
        8、硬座9、无座10、其他*/

        appService.orderCallBack(result, params);
    }


    /**
     * 退票测试
     */
    @Test
    public  void testReturnTicket(){
        Result result = getResult();
        RequestReturnTicketParam params = new RequestReturnTicketParam();
        params.setSupplierCode("ZZFW20161129095457360057588");
        params.setOrderNo("HCP20161214174518001697699");
        appService.returnTicket(result, params);

        System.out.println("-------------"+JSON.toJSONString(result.DTO()));
    }



    /**
     * 退票回调
     */
    @Test
    public void testReturnTicketCallback(){
        Result result = getResult();
        RequestReturnTicketBackParam params = new RequestReturnTicketBackParam();
        params.setRequestId("TPHCP20161206180114120124903");//退票单号
        params.setMerchantOrderId("SPHCPKUYOU201612061801034208"); //对外订单号
        params.setRefundTotalAmount("50");
        params.setOrderId("sp-SPHCPKUYOU201612061801034208"); //供应商订单号
        params.setStatus("SUCCESS"); //FAILURE,PART
        params.setRefundType("1"); //1：用户线上退款 2：用户车站退款（包括车站改签退款）


        appService.returnTicketCallBack(result, params);

        System.out.println("--------------"+JSON.toJSONString(result));
    }




    /**
     * 查询订单状态
     * 
     */
    @Test
    public void testQueryOrderStatus(){
        QueryAccOrderStatusVO vo =   queryOrderStatus("HCP20161206101147043308896");
        System.out.println("-----------"+JSON.toJSONString(vo));
    }


    /**
     * 查询订单状态
     * 
     */
    /*  @Test
    public void testSpecialCity(){
        System.out.println("---"+appService.handleSpecialCity("湘西土"));
    }
     */




    /**
     * 查询订单状态
     * 
     * @param order
     */
    private QueryAccOrderStatusVO queryOrderStatus(String orderNo) {
        String url =TshDiamondClient.getInstance ().getConfig ("acc-url") + UrlConstants.ACC_QUERY_ORDER_STATUS;
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo",orderNo);
        String responseMessage = HttpUtils.doGet(url,params);

        if(StringUtils.isEmpty(responseMessage)){
            System.out.println("--->查询账户系统订单状态异常,reponseMessage为空");
        }
        try{
            QueryAccOrderInfoStatusResult result =  JSON.parseObject(responseMessage,QueryAccOrderInfoStatusResult.class);
            if(result.getStatus() != HttpResponseConstants.SUCCESS){
                System.out.println("---> 获取订单状态失败{}"+responseMessage);
            }else{
                return result.getData();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }


    @Test
    public void testUser(){
        UserInfo loginInfo = (UserInfo) SerializeUtil.unserialize(RedisSlave.getInstance().get("auc_jsession_5123719f47c99a4177c36b00362ca8c6d3da1800"));

        System.out.println("-----"+JSON.toJSONString(loginInfo));

    }


    @Test
    public void checkTicketNum(){
        Result result = getResult();
        RequestQueryTicketNumParam params = new RequestQueryTicketNumParam();
        params.setFromStation("阜阳");
        params.setArriveStation("广州");
        params.setTrainCode("T179");
        params.setTravelTime("2016-12-16");
        params.setSupplierCode("ZZFW20161129095457360057588");

        String checkTicket =  JSON.toJSONString(appService.queryTicketNum(result, params).getData());
        System.out.println(checkTicket);

        CheckTicketNumResponse queryTicketsResponse = JsonUtils.convert2Object(checkTicket, CheckTicketNumResponse.class);
        System.out.println(JSON.toJSONString(queryTicketsResponse));
    }
}
