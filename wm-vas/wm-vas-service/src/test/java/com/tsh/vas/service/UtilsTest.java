package com.tsh.vas.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tsh.vas.commoms.exception.CouponRuntimeException;
import com.tsh.vas.vo.supplier.ApplyInfoVo;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Iritchie.ren on 2016/9/28.
 */
public class UtilsTest {

    @Test
    public void testApplyInfoVo() {
        ApplyInfoVo applyInfoVo = new ApplyInfoVo ();
        List<String> s = Lists.newArrayList ();
        s.add ("X");
        s.add ("F");
        applyInfoVo.setSupplierCode ("12");
        applyInfoVo.setApplyExplain ("1212");
        applyInfoVo.setBusinessType (12);
        applyInfoVo.setCompany ("xxxxx");
        applyInfoVo.setEmail ("980980@aacom");
        applyInfoVo.setMobile ("13854386438");
        applyInfoVo.setSupplierName ("xxxxxxxxxxxffff");
        applyInfoVo.setTelphone ("0755-2342234");
        applyInfoVo.setBusinessCodes (s);
        System.out.println (JSON.toJSONString (applyInfoVo));
    }

    @Test
    public void getYearMonthDay() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        String nowYearMonthDayStr = sdf.format (new Date ());
        Date nowYearMonthDay = sdf.parse (nowYearMonthDayStr);
        Calendar rightNow = Calendar.getInstance ();
        rightNow.setTime (nowYearMonthDay);
        rightNow.add (Calendar.DAY_OF_YEAR, -3);
        Date beforeDate = rightNow.getTime ();
        System.err.println (beforeDate);

    }

    @Test
    public void stringToList() {
        //        String notificationStr = "13684970375;18666998350;13826570180;";
        String notificationStr = "";
        String[] notificationArray = StringUtils.split (notificationStr.trim (), ";");
        System.err.println (notificationArray.length);
        for (String item : notificationArray) {
            System.err.println (item.trim ());
        }
    }
    
    
    public static void main(String[] args) {
        try{
        test();
        }catch(CouponRuntimeException e){
            System.out.println(e.getErrCode() +":"+e.getErrMsg());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void test(){
        throw new CouponRuntimeException("222", "errMsg");
    }
}
