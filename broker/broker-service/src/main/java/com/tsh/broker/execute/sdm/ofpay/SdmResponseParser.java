package com.tsh.broker.execute.sdm.ofpay;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;
import com.tsh.broker.convertor.PayResultConvetor;
import com.tsh.broker.enumType.OfpayEnum;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.PayUnit;
import com.tsh.broker.vo.sdm.PaymentBill;
import com.tsh.broker.vo.sdm.request.PaymentBillRequest;

/**
 * SdmResponseParser
 *
 * @author dengjd
 * @date 2016/10/15
 */
@SuppressWarnings("all")
public class SdmResponseParser {

    public ResponseWrapper<List<PayUnit>> queryPayUnitParse(String response) throws DocumentException {
        if(StringUtils.isBlank(response))
            throw new IllegalArgumentException("response is empty");

        ResponseWrapper responseWrapper = new ResponseWrapper();
        StringReader sr = new StringReader(response);
        InputSource is = new InputSource(sr);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(is);

        Element root  = doc.getRootElement();
        Node resultNode  = root.selectSingleNode("//*/retcode");
        Node msgNode  = root.selectSingleNode("//*/err_msg");
        String resultText = resultNode.getText();
        String msgText = msgNode != null ? msgNode.getText() : "";

        if(resultText.equals("1")){
            List<PayUnit> payUnitList = new ArrayList<PayUnit>();
            List<Node> resultNodeList  = root.selectNodes("//*/payUnits/payUnit");
            if(CollectionUtils.isNotEmpty(resultNodeList)){
                for(Node node : resultNodeList){
                    PayUnit payUnit = new PayUnit();
                    Node payUnitIdNode =  node.selectSingleNode("payUnitId");
                    Node payUnitNameNode =  node.selectSingleNode("payUnitName");
                    String payUnitIdText = payUnitIdNode.getText();
                    String payUnitNameText = payUnitNameNode.getText();

                    payUnit.setUnitId(payUnitIdText);
                    payUnit.setUnitName(payUnitNameText);

                    payUnitList.add(payUnit);
                }
            }
            responseWrapper.setData(payUnitList);
            responseWrapper.setStatus(0);
        }else {
            responseWrapper.setStatus(10002);
            responseWrapper.setMessage(msgText);
        }

        return responseWrapper;
    }

    public ResponseWrapper<Integer> queryPayModeParse(String response) throws DocumentException {
        if(StringUtils.isBlank(response))
            throw new IllegalArgumentException("response is empty");

        ResponseWrapper responseWrapper = new ResponseWrapper();
        StringReader sr = new StringReader(response);
        InputSource is = new InputSource(sr);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(is);

        Element root  = doc.getRootElement();
        Node resultNode  = root.selectSingleNode("//*/retcode");
        Node msgNode  = root.selectSingleNode("//*/err_msg");
        String resultText = resultNode.getText();
        String msgText = msgNode != null ? msgNode.getText() : "";

        if(resultText.equals("1")){
            List<String> accountTypeTextList = new ArrayList<String>();

            List<Node> resultNodeList  = root.selectNodes("//*/payModes/payMode");
            if(CollectionUtils.isNotEmpty(resultNodeList)){
                for(Node node : resultNodeList){
                    Node payUnitIdNode =  node.selectSingleNode("payModeId");
                    String payUnitIdText = payUnitIdNode.getText();
                    accountTypeTextList.add(payUnitIdText);
                }
            }
            Integer accountType = 0;
            boolean[] accountFlags = new boolean[2];
            for(String  accountTypeText : accountTypeTextList){
                if(accountTypeText.equals(OfpayEnum.AccountTypeEnum.ACCOUNT_TYPE_NO.getName())){
                    accountFlags[0] = true;
                }else if(accountTypeText.equals(OfpayEnum.AccountTypeEnum.ACCOUNT_TYPE_BAR_CODE.getName())){
                    accountFlags[1] = true;
                }
            }
            if(accountFlags[0] && accountFlags[1]){
                accountType = 3;
            }else if (accountFlags[0]){
                accountType = 1;
            }else if(accountFlags[1]){
                    accountType = 2;
            }

            responseWrapper.setData(accountType);
            responseWrapper.setStatus(0);
        }else {
            responseWrapper.setStatus(10002);
            responseWrapper.setMessage(msgText);
        }

        return responseWrapper;
    }

    public ResponseWrapper<String> queryQueryClassIdParse(String response) throws DocumentException {
        if(StringUtils.isBlank(response))
            throw new IllegalArgumentException("response is empty");

        ResponseWrapper responseWrapper = new ResponseWrapper();
        StringReader sr = new StringReader(response);
        InputSource is = new InputSource(sr);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(is);

        Element root  = doc.getRootElement();
        Node resultNode  = root.selectSingleNode("//*/retcode");
        Node msgNode  = root.selectSingleNode("//*/err_msg");
        String resultText = resultNode.getText();
        String msgText = msgNode != null ? msgNode.getText() : "";
        if(resultText.equals("1")){
            Node productIdNode  = root.selectSingleNode("//*/cards/card[1]/productId");
            String productIdText = productIdNode.getText();
            responseWrapper.setData(productIdText);
            responseWrapper.setStatus(0);
        }else {
            responseWrapper.setStatus(50002);
            responseWrapper.setMessage(msgText);
        }

        return responseWrapper;
    }

    public ResponseWrapper<List<PaymentBill>> queryPaymentBillParse(String response, PaymentBillRequest paymentBillRequest) throws DocumentException {
        if(StringUtils.isBlank(response))
            throw new IllegalArgumentException("response is empty");

        ResponseWrapper responseWrapper = new ResponseWrapper();
        StringReader sr = new StringReader(response);
        InputSource is = new InputSource(sr);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(is);

        Element root  = doc.getRootElement();
        Node resultNode  = root.selectSingleNode("//*/retcode");
        Node msgNode  = root.selectSingleNode("//*/err_msg");
        String resultText = resultNode.getText();
        String msgText = msgNode != null ? msgNode.getText() : "";
        if(resultText.equals("1")){
            List<PaymentBill> paymentBillList = new ArrayList<PaymentBill>();
            List<Node> balancesNodeList  = root.selectNodes("//*/balances/balance");
            if(CollectionUtils.isNotEmpty(balancesNodeList)){
                for(Node node : balancesNodeList){
                    PaymentBill paymentBill = new PaymentBill();
                    Node accountNameNode =  node.selectSingleNode("accountName");
                    Node balanceNode =  node.selectSingleNode("balance");
                    Node balanceAmountNode =  node.selectSingleNode("balanceAmount");
                    Node payMentDayNode =  node.selectSingleNode("payMentDay");
                    Node prepaidFlagNode =  node.selectSingleNode("prepaidFlag");
                    Node contractNoNode =  node.selectSingleNode("contractNo");
                    Node param1Node =  node.selectSingleNode("param1");

                    String accountNameText = accountNameNode.getText();
                    String balanceNodeText = balanceNode.getText();
                    String balanceAmountText = "";
                    if(null == balanceAmountNode){
                    	balanceAmountText = "0";
                    }else{
                    	balanceAmountText = balanceAmountNode.getText();
                    }
                    String payMentDayText = "";
                    String contractNoText = "";
                    String param1Text = "";
                    if(payMentDayNode != null) {
                        payMentDayText = payMentDayNode.getText();
                    }
                    if(contractNoNode != null){
                        contractNoText = contractNoNode.getText();
                    }
                    if(param1Node != null){
                        param1Text = param1Node.getText();
                    }
                    Integer payModel = null;
                    if(null != prepaidFlagNode){
                    	String prepaidFlagText = prepaidFlagNode.getText();
                    	 if("1".equals(prepaidFlagText)){
                             payModel = 1;
                         }else if("2".equals(prepaidFlagText)){
                             payModel = 2;
                         }
                    }else {
                    	//没有返回则默认为预付费
                    	payModel = 1;
					}
                    paymentBill.setUnitId(paymentBillRequest.getUnitId());
                    paymentBill.setAccount(paymentBillRequest.getAccount());
                    paymentBill.setYearmonth(payMentDayText);
                    paymentBill.setUsername(accountNameText);
                    paymentBill.setBills(new BigDecimal(balanceNodeText));
                    if(NumberUtils.isNumber(balanceAmountText))
                        paymentBill.setBalance(new BigDecimal(balanceAmountText));
                    Map<String,String> extContentMap = new HashMap<String,String>();
                    if(StringUtils.isNotBlank(contractNoText)){
                        extContentMap.put("contractNo",contractNoText);
                    }
                    if(StringUtils.isNotBlank(param1Text)){
                        extContentMap.put("param1",param1Text);
                    }
                    paymentBill.setExtContent(JSONObject.toJSONString(extContentMap));
                    
                    
                    paymentBill.setPayModel(payModel);
                    paymentBillList.add(paymentBill);
                }
            }
            responseWrapper.setData(paymentBillList);
            responseWrapper.setStatus(0);
        }else {
            responseWrapper.setStatus(50002);
            responseWrapper.setMessage(msgText);
        }

        return responseWrapper;
    }

    public ResponseWrapper<Integer> rechargeParse(String response) throws DocumentException {
        if(StringUtils.isBlank(response))
            throw new IllegalArgumentException("response is empty");

        ResponseWrapper responseWrapper = new ResponseWrapper();
        StringReader sr = new StringReader(response);
        InputSource is = new InputSource(sr);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(is);

        Element root  = doc.getRootElement();
        Node resultNode  = root.selectSingleNode("//*/retcode");
        Node msgNode  = root.selectSingleNode("//*/err_msg");
        String resultText = resultNode.getText();
        String msgText = msgNode != null ? msgNode.getText() : "";
        if(resultText.equals("1")){
            Node statusNode  = root.selectSingleNode("//*/status");
            String statusText = statusNode.getText().trim();

            Integer payResult = PayResultConvetor.convertPayResult(Integer.valueOf(statusText));
            responseWrapper.setData(payResult);
            responseWrapper.setStatus(0);
        }else {
            responseWrapper.setStatus(50002);
            responseWrapper.setMessage(msgText);
        }

        return responseWrapper;
    }

    public ResponseWrapper<Integer> queryRechargeResultParse(String response) {
        if(StringUtils.isBlank(response))
            throw new IllegalArgumentException("response is empty");
        ResponseWrapper<Integer> responseWrapper = new ResponseWrapper<Integer>();
        String status = response.trim();
        if("-1".equals(status)){
            responseWrapper.setStatus(30001);
            responseWrapper.setMessage("订单不存在");
            return responseWrapper;
        }
        //1充值成功，0充值中，9充值失败，-1找不到此订单
        Integer payResult = PayResultConvetor.convertPayResult(Integer.valueOf(status));
        responseWrapper.setStatus(0);
        responseWrapper.setData(payResult);

        return responseWrapper;
    }




}
