package com.tsh.vas.trainticket.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.trainticket.HcpRefundAmountDao;
import com.tsh.vas.model.trainticket.HcpRefundAmountPo;
import com.tsh.vas.vo.trainticket.HcpRefundAmountVo;


/**
 * 退款  服务类
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Service
@SuppressWarnings("all")
public class HcpRefundAmountService {
    @Autowired
    private HcpRefundAmountDao hcpRefundAmountDao;

    /**
     * 新增退款接口对象
     * @param result
     * @param hcpRefundAmount
     * @return
     */
    public Result addHcpRefundAmount(Result result,HcpRefundAmountVo hcpRefundAmountVo)throws Exception{
        HcpRefundAmountPo hcpRefundAmountPo = new HcpRefundAmountPo();

        if (hcpRefundAmountVo != null) {
            copyProperties(hcpRefundAmountVo, hcpRefundAmountPo);
        }

        result = hcpRefundAmountDao.addHcpRefundAmount(result,hcpRefundAmountPo);
        return result;
    }



    /**
     * @param hcpRefundAmountVo
     * @param hcpRefundAmountPo
     */
    private void copyProperties(HcpRefundAmountVo hcpRefundAmountVo, HcpRefundAmountPo hcpRefundAmountPo) {
        if(hcpRefundAmountVo.getHcpOrderCode()!=null){
            hcpRefundAmountPo.setHcpOrderCode(hcpRefundAmountVo.getHcpOrderCode());
        }
        if(hcpRefundAmountVo.getRefundAmountCode()!=null){
            hcpRefundAmountPo.setRefundAmountCode(hcpRefundAmountVo.getRefundAmountCode());
        }
        if(hcpRefundAmountVo.getRefundType()!=null){
            hcpRefundAmountPo.setRefundType(hcpRefundAmountVo.getRefundType());
        }
        if(hcpRefundAmountVo.getRealAmount()!=null){
            hcpRefundAmountPo.setRealAmount(hcpRefundAmountVo.getRealAmount());
        }
        if(hcpRefundAmountVo.getPayWay()!=null){
            hcpRefundAmountPo.setPayWay(hcpRefundAmountVo.getPayWay());
        }
        if(hcpRefundAmountVo.getCreateTime()!=null){
            hcpRefundAmountPo.setCreateTime(hcpRefundAmountVo.getCreateTime());
        }
        if(hcpRefundAmountVo.getUserCode()!=null){
            hcpRefundAmountPo.setUserCode(hcpRefundAmountVo.getUserCode());
        }
        if(hcpRefundAmountVo.getUserName()!=null){
            hcpRefundAmountPo.setUserName(hcpRefundAmountVo.getUserName());
        }
        if(hcpRefundAmountVo.getUserMobile()!=null){
            hcpRefundAmountPo.setUserMobile(hcpRefundAmountVo.getUserMobile());
        }
        if(hcpRefundAmountVo.getRefundTimes()!=null){
            hcpRefundAmountPo.setRefundTimes(hcpRefundAmountVo.getRefundTimes());
        }
        if(hcpRefundAmountVo.getRefundTime()!=null){
            hcpRefundAmountPo.setRefundTime(hcpRefundAmountVo.getRefundTime());
        }
        if(hcpRefundAmountVo.getRefundDesc()!=null){
            hcpRefundAmountPo.setRefundDesc(hcpRefundAmountVo.getRefundDesc());
        }
        if(hcpRefundAmountVo.getRemark()!=null){
            hcpRefundAmountPo.setRemark(hcpRefundAmountVo.getRemark());
        }
    }



    /**
     * 保存 退款接口对象 带User对象
     * @param result
     * @return
     */
    public Result saveHcpRefundAmount(Result result,HcpRefundAmountVo hcpRefundAmountVo,UserInfo user) throws Exception {
        if(hcpRefundAmountVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Integer id = hcpRefundAmountVo.getId();
        result = hcpRefundAmountDao.getHcpRefundAmountById(result,id);
        HcpRefundAmountPo hcpRefundAmountPo  = (HcpRefundAmountPo)result.getData();

        if (hcpRefundAmountPo != null) {
            copyProperties(hcpRefundAmountVo, hcpRefundAmountPo);
        }else{
            hcpRefundAmountPo = new HcpRefundAmountPo();
            copyProperties(hcpRefundAmountVo, hcpRefundAmountPo);
            result = hcpRefundAmountDao.addHcpRefundAmount(result,hcpRefundAmountPo);
        }
        return result;
    }



    /**
     * 保存 退款接口对象
     * @param result
     * @return
     */
    public Result saveHcpRefundAmount(Result result,HcpRefundAmountVo hcpRefundAmountVo) throws Exception {
        if(hcpRefundAmountVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Integer id = hcpRefundAmountVo.getId();
        result = hcpRefundAmountDao.getHcpRefundAmountById(result,id);
        HcpRefundAmountPo hcpRefundAmountPo  = (HcpRefundAmountPo)result.getData();

        if (hcpRefundAmountPo != null) {
            copyProperties(hcpRefundAmountVo, hcpRefundAmountPo);
        }else{
            hcpRefundAmountPo = new HcpRefundAmountPo();
            copyProperties(hcpRefundAmountVo, hcpRefundAmountPo);
            result = hcpRefundAmountDao.addHcpRefundAmount(result,hcpRefundAmountPo);
        }
        return result;
    }


    /**
     * 批量新增退款接口对象
     * @param result
     * @param hcpRefundAmount
     * @return
     */
    public Result batchSaveHcpRefundAmount(Result result, List<HcpRefundAmountVo> hcpRefundAmount_list) throws Exception {
        List<HcpRefundAmountPo> list = new ArrayList<HcpRefundAmountPo>();
        result = hcpRefundAmountDao.batchSaveHcpRefundAmount(result,list);
        return result;
    }

    /**
     * 删除退款接口对象
     * @param id 退款接口对象标识
     * @return
     */
    public Result deleteHcpRefundAmount(Result result, Integer id) throws Exception {
        result = hcpRefundAmountDao.deleteHcpRefundAmount(result,id);
        return result;
    }


    /**
     * 批量删除退款接口对象
     * @param result
     * @param hcpRefundAmount
     * @return
     */
    public Result batchDelHcpRefundAmount(Result result, List<HcpRefundAmountVo> hcpRefundAmount_list)throws Exception{
        List<HcpRefundAmountPo> list = new ArrayList<HcpRefundAmountPo>(); 
        hcpRefundAmountDao.batchDelete(list);
        return result;
    }


    /**
     * 批量删除退款接口对象ByIds
     * @param result
     * @param hcpRefundAmount
     * @return
     */
    public Result batchDelHcpRefundAmountByIds(Result result,Integer[] ids)throws Exception{
        hcpRefundAmountDao.batchDelHcpRefundAmountByIds(result,ids);
        return result;
    }


    /**
     * 根据条件获取 退款接口对象列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryHcpRefundAmountList(Result result,Page page,HcpRefundAmountVo hcpRefundAmountVo){
        HcpRefundAmountPo hcpRefundAmountPo = new HcpRefundAmountPo();
        result = hcpRefundAmountDao.queryHcpRefundAmountList(result,page,hcpRefundAmountPo);
        return result;
    }


    /**
     * 根据条件获取 退款接口对象列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryHcpRefundAmountList(Result result,Page page,HcpRefundAmountVo hcpRefundAmountVo,UserInfo user){
        HcpRefundAmountPo hcpRefundAmountPo = new HcpRefundAmountPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = hcpRefundAmountDao.queryHcpRefundAmountList(result,page,hcpRefundAmountPo);
        return result;
    }



    /**
     * 根据订单号获取订单信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryByOrderCode(Result result, String orderCode){
        return hcpRefundAmountDao.queryByOrderCode(result, orderCode);
    }


    /**
     * 根据退货订单号获取订单信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryByReturnOrderCode(Result result, String refundCode){
        return hcpRefundAmountDao.queryByReturnOrderCode(result, refundCode);

    }



    /**
     * 根据订单号修改退款状态
     * 
     * @param result
     * @param orderCode
     * @param refundStatus
     * @return
     * @throws Exception
     */
    public Result updateRefundStatus(Result result, String hcpRefundCode, Integer refundStatus) {
        return hcpRefundAmountDao.updateRefundStatus(result, hcpRefundCode, refundStatus);
    }

    
    
    /**
     * 根据订单号修改退款次数
     * 
     * @param result
     * @param chargeCode
     * @param refundStatus
     * @return
     * @throws Exception
     */
    public Result updateRefundRquestTime(Result result, String refundOrderCode, Integer refundCount) {
        return hcpRefundAmountDao.updateRefundRquestTime(result, refundOrderCode, refundCount);
    }

    
    /**
     *  根据退款单生成时间，类型查询需要退款的单
     *  
     * @param result
     * @param payStatus
     * @param beforTime
     * @param refundType
     * @return
     */
    public Result findByStatusWaitOrError(Result result, Date beforTime,int status,int refundType){
        return hcpRefundAmountDao.findByStatusWaitOrError(result,beforTime,status, refundType);
    }
    
    
    /**
     *  根据退款单生成时间，类型查询需要退款的单
     *  
     * @param result
     * @param payStatus
     * @param beforTime
     * @param refundType
     * @return
     */
    public Result findByStatus(Result result, Date beforTime,int status){
        return hcpRefundAmountDao.findByStatus(result,beforTime,status);
    }
    
    
    /**
     * 根据退款时间查询，状态查询
     * 
     * @param result
     * @param payStatus
     * @param beforTime
     * @return
     * @throws Exception
     */
    public Result findByStatusAndRefundTime(Result result,Date beforTime,int status){
      return hcpRefundAmountDao.findByStatusAndRefundTime(result, beforTime, status);
    }
    
}
