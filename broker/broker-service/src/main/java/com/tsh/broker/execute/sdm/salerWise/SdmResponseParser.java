package com.tsh.broker.execute.sdm.salerWise;

import com.tsh.broker.convertor.PayResultConvetor;
import com.tsh.broker.dao.SwPayUnitDao;
import com.tsh.broker.po.SwPayUnit;
import com.tsh.broker.utils.AmountUtils;
import com.tsh.broker.vo.common.ResponseWrapper;
import com.tsh.broker.vo.sdm.PaymentBill;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SdmResponseParser
 *
 * @author dengjd
 * @date 2016/10/9
 */
@SuppressWarnings(value="all")
public class SdmResponseParser {
	
    public ResponseWrapper rechargeParse(String response) throws Exception {
        if(StringUtils.isBlank(response))
            throw new IllegalArgumentException("response is empty");
        ResponseWrapper responseWrapper = new ResponseWrapper();

        StringReader sr = new StringReader(response);
        InputSource is = new InputSource(sr);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(is);
        Element root  = doc.getRootElement();
        Node resultNode  = root.selectSingleNode("//*/result");
        Node msgNode  = root.selectSingleNode("//*/msg");
        String resultText = resultNode.getText();
        String msgText = msgNode != null ? msgNode.getText() : "";

        if(resultText.equals("success")){
            responseWrapper.setStatus(0);
        }else if(resultText.equals("attrerr") || resultText.equals("hderr")){
            responseWrapper.setStatus(20001);
            responseWrapper.setMessage(msgText);
        }else if(resultText.equals("syserr")){
            responseWrapper.setStatus(20002);
            responseWrapper.setMessage(msgText);
        }else if(resultText.equals("signerr")){
            responseWrapper.setStatus(20003);
            responseWrapper.setMessage(msgText);
        }

        return responseWrapper;
    }

    public ResponseWrapper<Integer> queryRechargeResultParse(String response) throws Exception{
        if(StringUtils.isBlank(response))
            throw new IllegalArgumentException("response is empty");
        ResponseWrapper<Integer> responseWrapper = new ResponseWrapper<Integer>();

        StringReader sr = new StringReader(response);
        InputSource is = new InputSource(sr);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(is);
        Element root  = doc.getRootElement();
        Node resultNode  = root.selectSingleNode("//*/result");
        Node msgNode  = root.selectSingleNode("//*/msg");
        String resultText = resultNode.getText();
        String msgText = msgNode != null ? msgNode.getText() : "";

        if(resultText.equals("success")){
            Node payResultNode  = root.selectSingleNode("//*/payResult");
            String payResultText = payResultNode.getText();

            Integer payResult = -1;
            if(NumberUtils.isNumber(payResultText)){
                payResult = Integer.valueOf(payResultText);
            }
            responseWrapper.setData(PayResultConvetor.convertNotifyStatus(payResult));

            responseWrapper.setStatus(0);
            responseWrapper.setMessage(msgText);
        }else if(resultText.equals("attrerr") || resultText.equals("hderr")){
            responseWrapper.setStatus(30001);
            responseWrapper.setMessage(msgText);
        }else if(resultText.equals("syserr")){
            responseWrapper.setStatus(30002);
            responseWrapper.setMessage(msgText);
        }else if(resultText.equals("signerr")){
            responseWrapper.setStatus(30003);
            responseWrapper.setMessage(msgText);
        }

        return responseWrapper;
    }


    public ResponseWrapper<List<PaymentBill>> queryPaymentBillParse(String response) throws Exception {
        if(StringUtils.isBlank(response))
            throw new IllegalArgumentException("response is empty");
        ResponseWrapper<List<PaymentBill>> responseWrapper = new ResponseWrapper<List<PaymentBill>>();

        StringReader sr = new StringReader(response);
        InputSource is = new InputSource(sr);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(is);
        Element root  = doc.getRootElement();
        Node resultNode  = root.selectSingleNode("//*/result");
        Node msgNode  = root.selectSingleNode("//*/msg");
        String resultText = resultNode.getText();
        String msgText = msgNode != null ? msgNode.getText() : "";

        if(resultText.equals("success")){
            Node proidNode  = root.selectSingleNode("//*/proid");
            String proidText = proidNode.getText(); 
            Node accountNode  = root.selectSingleNode("//*/account");
            String accountText = accountNode.getText();
/*          Node yearmonthNode  = root.selectSingleNode("/*//*//*yearmonth");
            String yearmonthText = yearmonthNode.getText();*/
            Node usernameNode  = root.selectSingleNode("//*/username");
            String usernameText = usernameNode.getText();
            Node billsNode  = root.selectSingleNode("//*/bills");
            String billsText = billsNode.getText();
            
            List<PaymentBill> paymentBills = new ArrayList<PaymentBill>();
            
            PaymentBill paymentBill = new PaymentBill();
            paymentBill.setUnitId(proidText);
            paymentBill.setAccount(accountText);
            paymentBill.setYearmonth("");
            paymentBill.setUsername(usernameText);
            paymentBill.setBills(new BigDecimal(billsText).divide(new BigDecimal(100)));
            
            paymentBill.setExtContent("");
            paymentBill.setBalance(new BigDecimal(0));
            
            paymentBills.add(paymentBill);
            responseWrapper.setStatus(0);
            responseWrapper.setData(paymentBills);
        }else if(resultText.equals("attrerr") || resultText.equals("hderr")){
            responseWrapper.setStatus(50001);
            responseWrapper.setMessage(msgText);
        }else if(resultText.equals("syserr")){
            responseWrapper.setStatus(50002);
            responseWrapper.setMessage(msgText);
        }else if(resultText.equals("signerr")){
            responseWrapper.setStatus(50003);
            responseWrapper.setMessage(msgText);
        }else if(resultText.equals("fail")){
            responseWrapper.setStatus(50004);
            Node usernameNode  = root.selectSingleNode("//*/username");
            String usernameText = usernameNode != null ? usernameNode.getText() : "";
            responseWrapper.setMessage(usernameText);
        }

        return responseWrapper;
    }



}
