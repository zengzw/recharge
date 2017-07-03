package com.tsh.vas.trainticket.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.dao.trainticket.HcpOrderInfoDao;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.trainticket.HcpOrderInfoPo;
import com.tsh.vas.trainticket.commoms.enums.EnumOrderInfoPayStatus;
import com.tsh.vas.vo.trainticket.HcpOrderInfoVo;


/***
 * 订单服务接口
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Service
@SuppressWarnings("all")
public class HcpOrderInfoService {
    @Autowired
    private HcpOrderInfoDao hcpOrderInfoDao;


    /**
     * 修改
     * @param result
     * @param orderInfo
     * @return
     */
    public Result updateOrderInfo(Result result,HcpOrderInfoPo orderInfo){
        hcpOrderInfoDao.update(orderInfo);
        return result; 
    }


    /**
     * 根据条件获取 招工接口对象列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryHcpOrderInfoList(Result result,Page page,HcpOrderInfoVo hcpOrderInfoVo){
        HcpOrderInfoPo hcpOrderInfoPo = new HcpOrderInfoPo();
        result = hcpOrderInfoDao.queryHcpOrderInfoList(result,page,hcpOrderInfoPo);
        return result;
    }


    /**
     * 根据条件获取 招工接口对象列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryHcpOrderInfoList(Result result,Page page,HcpOrderInfoVo hcpOrderInfoVo,UserInfo user){
        HcpOrderInfoPo hcpOrderInfoPo = new HcpOrderInfoPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = hcpOrderInfoDao.queryHcpOrderInfoList(result,page,hcpOrderInfoPo);
        return result;
    }


    /**
     * 根据ID获取 招工接口对象 带User对象
     * @param result
     * @return
     */
    public Result getHcpOrderInfoById(Result result,Integer id,UserInfo user) throws Exception{
        result = hcpOrderInfoDao.getHcpOrderInfoById(result,id);
        return result;
    }


    /**
     * 根据ID获取 招工接口对象
     * @param result
     * @return
     */
    public Result getHcpOrderInfoById(Result result,Integer id) throws Exception{
        result = hcpOrderInfoDao.getHcpOrderInfoById(result,id);
        return result;
    }



    /**
     * 批量更新 招工接口对象
     * @param result
     * @return
     */
    public Result batchUpdateHcpOrderInfo(Result result,List<HcpOrderInfoVo> hcpOrderInfo_list) throws Exception {
        List<HcpOrderInfoPo> list = new ArrayList<HcpOrderInfoPo>(); 
        hcpOrderInfoDao.batchUpdateHcpOrderInfo(result,list);
        return result;
    }
    
    
    
    /**
     *  根据开放平台查找订单记录
     *  
     * @param result
     * @param openPlatformNo
     * @return
     */
    public Result queryByOpenPlatformNo(Result result, String openPlatformNo) {
      return hcpOrderInfoDao.queryByOpenPlatformNo(result, openPlatformNo);
    }
    
    
    /**
     * 根据订单号获取退款信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryByOrderCode(Result result, String orderCode){
       
    
        return hcpOrderInfoDao.queryByOrderCode(result, orderCode);
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
        HcpOrderInfoPo orderInfo = this.hcpOrderInfoDao.queryByOrderCode(result, orderCode).getData ();

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (orderInfo);
        return result;
    }
    
    

    /**
     * 根据下单时间，支付状态查找订单记录
     * 
     * @param result
     * @param payStatus
     * @param beforTime
     * @return
     * @throws Exception
     */
    public Result findByPayStatusAndTime(Result result, Integer payStatus, Date  beforTime) {
        if(beforTime == null){
            throw new BusinessRuntimeException("", "beforeTime参数为空！");
        }
        if(payStatus == null){
            throw new BusinessRuntimeException("", "payStatus 为空!");
        }
        
        return hcpOrderInfoDao.findByPayStatusAndTime(result, payStatus, beforTime);
    }

    /**
     * 修改订单状态
     * 
     * @param result
     * @param orderCode
     * @param payStatus
     * @return
     */
    
    public Result updateStatus(Result result, String orderCode, Integer payStatus){
        if(StringUtils.isEmpty(orderCode)){
            throw new BusinessRuntimeException("", "orderCode 为空!");
        }
        
        if(payStatus == null){
            throw new BusinessRuntimeException("", "payStatus 为空!");
        }
        
        
        return hcpOrderInfoDao.updateStatus(result, orderCode, payStatus);
    }
    /**
     * 火车出发前一天短信提醒乘车人
     * @return
     */
    public List<HcpOrderInfoPo> getStartToRemindOrder(){
    	return hcpOrderInfoDao.getStartToRemindOrder();
    }
    
    
    

    /**
     *  查询当前乘车人在同个时间是否已经购买过车
     *  
     * @param result
     * @param fromStation 触发站
     * @param arriveStation 终点站
     * @param travelTime 乘车时间
     * @param idCardId 身份证
     * @return
     */
      public Result queryCurrentDaySameTrainNum(Result result, String fromStation,String travelTime,String idCardId) {
         return hcpOrderInfoDao.queryCurrentDaySameTrainNum(result, fromStation, travelTime, idCardId,EnumOrderInfoPayStatus.TRAD_SUCCESS.getCode());
      }
}
