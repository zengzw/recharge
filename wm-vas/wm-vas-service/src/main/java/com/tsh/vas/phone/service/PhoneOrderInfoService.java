package com.tsh.vas.phone.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.phone.PhoneOrderInfoDao;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.phone.enums.EnumPhoneOrderInfoPayStatus;
import com.tsh.vas.vo.phone.PhoneOrderInfoVo;


@Service
@SuppressWarnings("all")
public class PhoneOrderInfoService {
    @Autowired
    private PhoneOrderInfoDao phoneOrderInfoDao;

    /**
     * 新增
     * @param result
     * @param phoneOrderInfo
     * @return
     */
    public Result addPhoneOrderInfo(Result result,PhoneOrderInfoPo phoneOrderInfo)throws Exception{

        result = phoneOrderInfoDao.addPhoneOrderInfo(result,phoneOrderInfo);
        return result;
    }
    
    
    /**
     * 新增
     * @param result
     * @param phoneOrderInfo
     * @return
     */
    public Result updateOrderInfo(Result result,PhoneOrderInfoPo phoneOrderInfo)throws Exception{
        phoneOrderInfoDao.update(phoneOrderInfo);
        result.setData(phoneOrderInfo);
        return result;
    }




    /**
     * 批量删除
     * @param result
     * @param phoneOrderInfo
     * @return
     */
    public Result batchDelPhoneOrderInfo(Result result, List<PhoneOrderInfoVo> phoneOrderInfo_list)throws Exception{
        List<PhoneOrderInfoPo> list = new ArrayList<PhoneOrderInfoPo>(); 
        phoneOrderInfoDao.batchDelete(list);
        return result;
    }





    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneOrderInfoList(Result result,Page page,PhoneOrderInfoVo phoneOrderInfoVo){
        PhoneOrderInfoPo phoneOrderInfoPo = new PhoneOrderInfoPo();
        result = phoneOrderInfoDao.queryPhoneOrderInfoList(result,page,phoneOrderInfoPo);
        return result;
    }


    /**
     * 根据条件获取 列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneOrderInfoList(Result result,Page page,PhoneOrderInfoVo phoneOrderInfoVo,UserInfo user){
        PhoneOrderInfoPo phoneOrderInfoPo = new PhoneOrderInfoPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = phoneOrderInfoDao.queryPhoneOrderInfoList(result,page,phoneOrderInfoPo);
        return result;
    }


    /**
     * 根据ID获取  带User对象
     * @param result
     * @return
     */
    public Result getPhoneOrderInfoById(Result result,Integer id,UserInfo user) throws Exception{
        result = phoneOrderInfoDao.getPhoneOrderInfoById(result,id);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneOrderInfoById(Result result,Integer id) throws Exception{
        result = phoneOrderInfoDao.getPhoneOrderInfoById(result,id);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhoneOrderInfo(Result result,PhoneOrderInfoVo phoneOrderInfoVo) throws Exception {
        long id = phoneOrderInfoVo.getId();
        result = phoneOrderInfoDao.getPhoneOrderInfoById(result,id);
        PhoneOrderInfoPo phoneOrderInfoPo  = (PhoneOrderInfoPo)result.getData();
        if (phoneOrderInfoPo != null) {
            if(phoneOrderInfoVo.getPhoneOrderCode()!=null){
                phoneOrderInfoPo.setPhoneOrderCode(phoneOrderInfoVo.getPhoneOrderCode());
            }
            if(phoneOrderInfoVo.getSupplierOrderId()!=null){
                phoneOrderInfoPo.setSupplierOrderId(phoneOrderInfoVo.getSupplierOrderId());
            }
            if(phoneOrderInfoVo.getSupplierCode()!=null){
                phoneOrderInfoPo.setSupplierCode(phoneOrderInfoVo.getSupplierCode());
            }
            if(phoneOrderInfoVo.getSupplierName()!=null){
                phoneOrderInfoPo.setSupplierName(phoneOrderInfoVo.getSupplierName());
            }
            if(phoneOrderInfoVo.getCity()!=null){
                phoneOrderInfoPo.setCity(phoneOrderInfoVo.getCity());
            }
            if(phoneOrderInfoVo.getProvince()!=null){
                phoneOrderInfoPo.setProvince(phoneOrderInfoVo.getProvince());
            }
            if(phoneOrderInfoVo.getCountry()!=null){
                phoneOrderInfoPo.setCountry(phoneOrderInfoVo.getCountry());
            }
            if(phoneOrderInfoVo.getCountryCode()!=null){
                phoneOrderInfoPo.setCountryCode(phoneOrderInfoVo.getCountryCode());
            }
            if(phoneOrderInfoVo.getCountryName()!=null){
                phoneOrderInfoPo.setCountryName(phoneOrderInfoVo.getCountryName());
            }
            if(phoneOrderInfoVo.getMemberMobile()!=null){
                phoneOrderInfoPo.setMemberMobile(phoneOrderInfoVo.getMemberMobile());
            }
            if(phoneOrderInfoVo.getMemberName()!=null){
                phoneOrderInfoPo.setMemberName(phoneOrderInfoVo.getMemberName());
            }
            if(phoneOrderInfoVo.getMobile()!=null){
                phoneOrderInfoPo.setMobile(phoneOrderInfoVo.getMobile());
            }
            if(phoneOrderInfoVo.getPayUserName()!=null){
                phoneOrderInfoPo.setPayUserName(phoneOrderInfoVo.getPayUserName());
            }
            if(phoneOrderInfoVo.getPayUserId() > 0){
                phoneOrderInfoPo.setPayUserId(phoneOrderInfoVo.getPayUserId());
            }
            if(phoneOrderInfoVo.getBizId() >0){
                phoneOrderInfoPo.setBizId(phoneOrderInfoVo.getBizId());
            }
            if(phoneOrderInfoVo.getBizType()> 0){
                phoneOrderInfoPo.setBizType(phoneOrderInfoVo.getBizType());
            }
            if(phoneOrderInfoVo.getStoreCode()!=null){
                phoneOrderInfoPo.setStoreCode(phoneOrderInfoVo.getStoreCode());
            }
            if(phoneOrderInfoVo.getStoreName()!=null){
                phoneOrderInfoPo.setStoreName(phoneOrderInfoVo.getStoreName());
            }
            if(phoneOrderInfoVo.getCostingAmount()!=null){
                phoneOrderInfoPo.setCostingAmount(phoneOrderInfoVo.getCostingAmount());
            }
            if(phoneOrderInfoVo.getSaleAmount()!=null){
                phoneOrderInfoPo.setSaleAmount(phoneOrderInfoVo.getSaleAmount());
            }
            if(phoneOrderInfoVo.getOriginalAmount()!=null){
                phoneOrderInfoPo.setOriginalAmount(phoneOrderInfoVo.getOriginalAmount());
            }
            if(phoneOrderInfoVo.getRealAmount()!=null){
                phoneOrderInfoPo.setRealAmount(phoneOrderInfoVo.getRealAmount());
            }
            if(phoneOrderInfoVo.getCreateTime()!=null){
                phoneOrderInfoPo.setCreateTime(phoneOrderInfoVo.getCreateTime());
            }
            if(phoneOrderInfoVo.getPaySuccssTime()!=null){
                phoneOrderInfoPo.setPaySuccssTime(phoneOrderInfoVo.getPaySuccssTime());
            }
            if(phoneOrderInfoVo.getPayStatus() > 0){
                phoneOrderInfoPo.setPayStatus(phoneOrderInfoVo.getPayStatus());
            }
            if(phoneOrderInfoVo.getStoreInfo()!=null){
                phoneOrderInfoPo.setStoreInfo(phoneOrderInfoVo.getStoreInfo());
            }
            if(phoneOrderInfoVo.getBillYearMonth()!=null){
                phoneOrderInfoPo.setBillYearMonth(phoneOrderInfoVo.getBillYearMonth());
            }

            if(phoneOrderInfoVo.getGoodsId()!=null){
                phoneOrderInfoPo.setGoodsId(phoneOrderInfoVo.getGoodsId());
            }
            if(phoneOrderInfoVo.getRechargePhone()!=null){
                phoneOrderInfoPo.setRechargePhone(phoneOrderInfoVo.getRechargePhone());
            }
            if(phoneOrderInfoVo.getSources()!=null){
                phoneOrderInfoPo.setSources(phoneOrderInfoVo.getSources());
            }
        }
        return result;
    }

    
    public Result queryByOrderCode(Result result,String orderCode){
        return phoneOrderInfoDao.queryByOrderCode(result, orderCode);    
    }

    
    /**
     * 
     * 根据时间、状态 查询位订单
     * 
     * @param result
     * @param time  时间，分钟
     * @param status
     * @return
     */
    public Result queryAllOrderInfoRechargeing(Result result,int time){
        
        return phoneOrderInfoDao.queryAllOrderInfoRechargeing(result, time, EnumPhoneOrderInfoPayStatus.TRADING.getCode());
    }
    
    
    /**
     * 
     * 查询支付中的订单
     * 
     * @param result
     * @param time  时间，分钟
     * @param status
     * @return
     */
    public Result queryAllOrderInfoPaiding(Result result,int time){
        
        return phoneOrderInfoDao.queryAllOrderInfoRechargeing(result, time, EnumPhoneOrderInfoPayStatus.PAIDING.getCode());
    }

    
    /**
     * 查询订单详情
     *
     * @param result
     * @param chargeCode
     * @return
     * @throws Exception
     */
    public Result details(Result result, String orderCode) throws Exception {
        return phoneOrderInfoDao.queryByOrderCode(result, orderCode);
    }
 
    
    /**
     * 根据订单号修改支付状态
     * 
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatus(Result result, String orderCode, Integer payStatus){
       return phoneOrderInfoDao.updateStatus(result, orderCode, payStatus);
               
    }
    /**
     * 根据订单号修改支付状态
     * 
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatus(Result result, String orderCode, Integer payStatus,String remark){
        return phoneOrderInfoDao.updateStatus(result, orderCode, payStatus,remark);
        
    }

    /**
     * 根据订单号修改支付状态
     * 
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatusAndSuccessTime(Result result, String orderCode, Integer payStatus,Date successTime){
       return phoneOrderInfoDao.updateStatusAndSuccessTime(result, orderCode, payStatus, successTime);
    }

    /**
     * 根据订单号修改支付状态
     *
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     * @throws Exception
     */
    public Result updateStatusAndFailReason(Result result, String orderCode, Integer payStatus, String failReason){
      return phoneOrderInfoDao.updateStatusAndFailReason(result, orderCode, payStatus, failReason);
    }



    /**
     *  根据开放平台查找订单记录
     *  
     * @param result
     * @param openPlatformNo 公司编号
     * @return
     */
    public Result queryByOpenPlatformNo(Result result, String openPlatformNo) {
      return phoneOrderInfoDao.queryByOpenPlatformNo(result, openPlatformNo);
              
    }

    
    public Result queryOrderInfoListPage(Result result,PhoneOrderInfoVo phoneOrderInfoVo){
        return phoneOrderInfoDao.queryOrderInfoListPage(result, phoneOrderInfoVo);
    }

}
