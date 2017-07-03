package com.tsh.vas.phone.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.phone.PhoneRefundAmountDao;
import com.tsh.vas.model.phone.PhoneRefundAmountPo;
import com.tsh.vas.phone.enums.EnumPhoneRefundOrderStatus;
import com.tsh.vas.vo.phone.PhoneRefundAmountVo;


@Service
@SuppressWarnings("all")
public class PhoneRefundAmountService {
    @Autowired
    private PhoneRefundAmountDao phoneRefundAmountDao;

    /**
     * 新增
     * @param result
     * @param phoneRefundAmount
     * @return
     */
    public Result addPhoneRefundAmount(Result result,PhoneRefundAmountVo phoneRefundAmountVo)throws Exception{
        PhoneRefundAmountPo phoneRefundAmountPo = new PhoneRefundAmountPo();

        if (phoneRefundAmountVo != null) {
            if(phoneRefundAmountVo.getRefundAmountCode()!=null){
                phoneRefundAmountPo.setRefundAmountCode(phoneRefundAmountVo.getRefundAmountCode());
            }
            if(phoneRefundAmountVo.getPhoneOrderCode()!=null){
                phoneRefundAmountPo.setPhoneOrderCode(phoneRefundAmountVo.getPhoneOrderCode());
            }
            if(phoneRefundAmountVo.getRefundType()!=null){
                phoneRefundAmountPo.setRefundType(phoneRefundAmountVo.getRefundType());
            }
            if(phoneRefundAmountVo.getRealAmount()!=null){
                phoneRefundAmountPo.setRealAmount(phoneRefundAmountVo.getRealAmount());
            }
            if(phoneRefundAmountVo.getStatus()!=null){
                phoneRefundAmountPo.setStatus(phoneRefundAmountVo.getStatus());
            }
            if(phoneRefundAmountVo.getCreateTime()!=null){
                phoneRefundAmountPo.setCreateTime(phoneRefundAmountVo.getCreateTime());
            }
            if(phoneRefundAmountVo.getRefundTimes()!=null){
                phoneRefundAmountPo.setRefundTimes(phoneRefundAmountVo.getRefundTimes());
            }
            if(phoneRefundAmountVo.getRefundTime()!=null){
                phoneRefundAmountPo.setRefundTime(phoneRefundAmountVo.getRefundTime());
            }
            if(phoneRefundAmountVo.getRemark()!=null){
                phoneRefundAmountPo.setRemark(phoneRefundAmountVo.getRemark());
            }
        }

        result = phoneRefundAmountDao.addPhoneRefundAmount(result,phoneRefundAmountPo);
        return result;
    }



    /**
     * 保存  带User对象
     * @param result
     * @return
     */
    public Result savePhoneRefundAmount(Result result,PhoneRefundAmountVo phoneRefundAmountVo,UserInfo user) throws Exception {
        if(phoneRefundAmountVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phoneRefundAmountVo.getId();
        result = phoneRefundAmountDao.getPhoneRefundAmountById(result,id);
        PhoneRefundAmountPo phoneRefundAmountPo  = (PhoneRefundAmountPo)result.getData();

        if (phoneRefundAmountPo != null) {
            if(phoneRefundAmountVo.getRefundAmountCode()!=null){
                phoneRefundAmountPo.setRefundAmountCode(phoneRefundAmountVo.getRefundAmountCode());
            }
            if(phoneRefundAmountVo.getPhoneOrderCode()!=null){
                phoneRefundAmountPo.setPhoneOrderCode(phoneRefundAmountVo.getPhoneOrderCode());
            }
            if(phoneRefundAmountVo.getRefundType()!=null){
                phoneRefundAmountPo.setRefundType(phoneRefundAmountVo.getRefundType());
            }
            if(phoneRefundAmountVo.getRealAmount()!=null){
                phoneRefundAmountPo.setRealAmount(phoneRefundAmountVo.getRealAmount());
            }
            if(phoneRefundAmountVo.getStatus()!=null){
                phoneRefundAmountPo.setStatus(phoneRefundAmountVo.getStatus());
            }
            if(phoneRefundAmountVo.getCreateTime()!=null){
                phoneRefundAmountPo.setCreateTime(phoneRefundAmountVo.getCreateTime());
            }
            if(phoneRefundAmountVo.getRefundTimes()!=null){
                phoneRefundAmountPo.setRefundTimes(phoneRefundAmountVo.getRefundTimes());
            }
            if(phoneRefundAmountVo.getRefundTime()!=null){
                phoneRefundAmountPo.setRefundTime(phoneRefundAmountVo.getRefundTime());
            }
            if(phoneRefundAmountVo.getRemark()!=null){
                phoneRefundAmountPo.setRemark(phoneRefundAmountVo.getRemark());
            }
        }else{
            phoneRefundAmountPo = new PhoneRefundAmountPo();
            if(phoneRefundAmountVo.getRefundAmountCode()!=null){
                phoneRefundAmountPo.setRefundAmountCode(phoneRefundAmountVo.getRefundAmountCode());
            }
            if(phoneRefundAmountVo.getPhoneOrderCode()!=null){
                phoneRefundAmountPo.setPhoneOrderCode(phoneRefundAmountVo.getPhoneOrderCode());
            }
            if(phoneRefundAmountVo.getRefundType()!=null){
                phoneRefundAmountPo.setRefundType(phoneRefundAmountVo.getRefundType());
            }
            if(phoneRefundAmountVo.getRealAmount()!=null){
                phoneRefundAmountPo.setRealAmount(phoneRefundAmountVo.getRealAmount());
            }
            if(phoneRefundAmountVo.getStatus()!=null){
                phoneRefundAmountPo.setStatus(phoneRefundAmountVo.getStatus());
            }
            if(phoneRefundAmountVo.getCreateTime()!=null){
                phoneRefundAmountPo.setCreateTime(phoneRefundAmountVo.getCreateTime());
            }
            if(phoneRefundAmountVo.getRefundTimes()!=null){
                phoneRefundAmountPo.setRefundTimes(phoneRefundAmountVo.getRefundTimes());
            }
            if(phoneRefundAmountVo.getRefundTime()!=null){
                phoneRefundAmountPo.setRefundTime(phoneRefundAmountVo.getRefundTime());
            }
            if(phoneRefundAmountVo.getRemark()!=null){
                phoneRefundAmountPo.setRemark(phoneRefundAmountVo.getRemark());
            }
            result = phoneRefundAmountDao.addPhoneRefundAmount(result,phoneRefundAmountPo);
        }
        return result;
    }



    /**
     * 保存 
     * @param result
     * @return
     */
    public Result savePhoneRefundAmount(Result result,PhoneRefundAmountVo phoneRefundAmountVo) throws Exception {
        if(phoneRefundAmountVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phoneRefundAmountVo.getId();
        result = phoneRefundAmountDao.getPhoneRefundAmountById(result,id);
        PhoneRefundAmountPo phoneRefundAmountPo  = (PhoneRefundAmountPo)result.getData();

        if (phoneRefundAmountPo != null) {
            if(phoneRefundAmountVo.getRefundAmountCode()!=null){
                phoneRefundAmountPo.setRefundAmountCode(phoneRefundAmountVo.getRefundAmountCode());
            }
            if(phoneRefundAmountVo.getPhoneOrderCode()!=null){
                phoneRefundAmountPo.setPhoneOrderCode(phoneRefundAmountVo.getPhoneOrderCode());
            }
            if(phoneRefundAmountVo.getRefundType()!=null){
                phoneRefundAmountPo.setRefundType(phoneRefundAmountVo.getRefundType());
            }
            if(phoneRefundAmountVo.getRealAmount()!=null){
                phoneRefundAmountPo.setRealAmount(phoneRefundAmountVo.getRealAmount());
            }
            if(phoneRefundAmountVo.getStatus()!=null){
                phoneRefundAmountPo.setStatus(phoneRefundAmountVo.getStatus());
            }
            if(phoneRefundAmountVo.getCreateTime()!=null){
                phoneRefundAmountPo.setCreateTime(phoneRefundAmountVo.getCreateTime());
            }
            if(phoneRefundAmountVo.getRefundTimes()!=null){
                phoneRefundAmountPo.setRefundTimes(phoneRefundAmountVo.getRefundTimes());
            }
            if(phoneRefundAmountVo.getRefundTime()!=null){
                phoneRefundAmountPo.setRefundTime(phoneRefundAmountVo.getRefundTime());
            }
            if(phoneRefundAmountVo.getRemark()!=null){
                phoneRefundAmountPo.setRemark(phoneRefundAmountVo.getRemark());
            }
        }else{
            phoneRefundAmountPo = new PhoneRefundAmountPo();
            if(phoneRefundAmountVo.getRefundAmountCode()!=null){
                phoneRefundAmountPo.setRefundAmountCode(phoneRefundAmountVo.getRefundAmountCode());
            }
            if(phoneRefundAmountVo.getPhoneOrderCode()!=null){
                phoneRefundAmountPo.setPhoneOrderCode(phoneRefundAmountVo.getPhoneOrderCode());
            }
            if(phoneRefundAmountVo.getRefundType()!=null){
                phoneRefundAmountPo.setRefundType(phoneRefundAmountVo.getRefundType());
            }
            if(phoneRefundAmountVo.getRealAmount()!=null){
                phoneRefundAmountPo.setRealAmount(phoneRefundAmountVo.getRealAmount());
            }
            if(phoneRefundAmountVo.getStatus()!=null){
                phoneRefundAmountPo.setStatus(phoneRefundAmountVo.getStatus());
            }
            if(phoneRefundAmountVo.getCreateTime()!=null){
                phoneRefundAmountPo.setCreateTime(phoneRefundAmountVo.getCreateTime());
            }
            if(phoneRefundAmountVo.getRefundTimes()!=null){
                phoneRefundAmountPo.setRefundTimes(phoneRefundAmountVo.getRefundTimes());
            }
            if(phoneRefundAmountVo.getRefundTime()!=null){
                phoneRefundAmountPo.setRefundTime(phoneRefundAmountVo.getRefundTime());
            }
            if(phoneRefundAmountVo.getRemark()!=null){
                phoneRefundAmountPo.setRemark(phoneRefundAmountVo.getRemark());
            }
            result = phoneRefundAmountDao.addPhoneRefundAmount(result,phoneRefundAmountPo);
        }
        return result;
    }


    /**
     * 批量新增
     * @param result
     * @param phoneRefundAmount
     * @return
     */
    public Result batchSavePhoneRefundAmount(Result result, List<PhoneRefundAmountVo> phoneRefundAmount_list) throws Exception {
        List<PhoneRefundAmountPo> list = new ArrayList<PhoneRefundAmountPo>();
        result = phoneRefundAmountDao.batchSavePhoneRefundAmount(result,list);
        return result;
    }

   


    /**
     * 批量删除
     * @param result
     * @param phoneRefundAmount
     * @return
     */
    public Result batchDelPhoneRefundAmount(Result result, List<PhoneRefundAmountVo> phoneRefundAmount_list)throws Exception{
        List<PhoneRefundAmountPo> list = new ArrayList<PhoneRefundAmountPo>(); 
        phoneRefundAmountDao.batchDelete(list);
        return result;
    }




    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneRefundAmountList(Result result,Page page,PhoneRefundAmountVo phoneRefundAmountVo){
        PhoneRefundAmountPo phoneRefundAmountPo = new PhoneRefundAmountPo();
        result = phoneRefundAmountDao.queryPhoneRefundAmountList(result,page,phoneRefundAmountPo);
        return result;
    }


    /**
     * 根据条件获取 列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneRefundAmountList(Result result,Page page,PhoneRefundAmountVo phoneRefundAmountVo,UserInfo user){
        PhoneRefundAmountPo phoneRefundAmountPo = new PhoneRefundAmountPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = phoneRefundAmountDao.queryPhoneRefundAmountList(result,page,phoneRefundAmountPo);
        return result;
    }


    /**
     * 根据ID获取  带User对象
     * @param result
     * @return
     */
    public Result getPhoneRefundAmountById(Result result,Long id,UserInfo user) throws Exception{
        result = phoneRefundAmountDao.getPhoneRefundAmountById(result,id);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneRefundAmountById(Result result,Long id) throws Exception{
        result = phoneRefundAmountDao.getPhoneRefundAmountById(result,id);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhoneRefundAmount(Result result,PhoneRefundAmountVo phoneRefundAmountVo) throws Exception {
    	Long id = phoneRefundAmountVo.getId();
        result = phoneRefundAmountDao.getPhoneRefundAmountById(result,id);
        PhoneRefundAmountPo phoneRefundAmountPo  = (PhoneRefundAmountPo)result.getData();
        if (phoneRefundAmountPo != null) {
            if(phoneRefundAmountVo.getRefundAmountCode()!=null){
                phoneRefundAmountPo.setRefundAmountCode(phoneRefundAmountVo.getRefundAmountCode());
            }
            if(phoneRefundAmountVo.getPhoneOrderCode()!=null){
                phoneRefundAmountPo.setPhoneOrderCode(phoneRefundAmountVo.getPhoneOrderCode());
            }
            if(phoneRefundAmountVo.getRefundType()!=null){
                phoneRefundAmountPo.setRefundType(phoneRefundAmountVo.getRefundType());
            }
            if(phoneRefundAmountVo.getRealAmount()!=null){
                phoneRefundAmountPo.setRealAmount(phoneRefundAmountVo.getRealAmount());
            }
            if(phoneRefundAmountVo.getStatus()!=null){
                phoneRefundAmountPo.setStatus(phoneRefundAmountVo.getStatus());
            }
            if(phoneRefundAmountVo.getCreateTime()!=null){
                phoneRefundAmountPo.setCreateTime(phoneRefundAmountVo.getCreateTime());
            }
            if(phoneRefundAmountVo.getRefundTimes()!=null){
                phoneRefundAmountPo.setRefundTimes(phoneRefundAmountVo.getRefundTimes());
            }
            if(phoneRefundAmountVo.getRefundTime()!=null){
                phoneRefundAmountPo.setRefundTime(phoneRefundAmountVo.getRefundTime());
            }
            if(phoneRefundAmountVo.getRemark()!=null){
                phoneRefundAmountPo.setRemark(phoneRefundAmountVo.getRemark());
            }
        }
        return result;
    }

    
    
    
    /**
     * 查看所有未退款的 对款单
     * 
     * @param result
     * @param time
     * @return
     */
    public Result findAllUnRefundOrder(Result result,Date time){
        
       return  phoneRefundAmountDao.findByStatusAndCreateTIme(result, time, EnumPhoneRefundOrderStatus.NON_REFUND.getCode());
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
        return phoneRefundAmountDao.queryByReturnOrderCode(result, refundCode);

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
        return phoneRefundAmountDao.updateRefundStatus(result, hcpRefundCode, refundStatus);
    }

    /**
     * 新增
     * @param result
     * @param phoneRefundAmount
     * @return
     */
    public Result addPhoneRefundAmount(Result result,PhoneRefundAmountPo phoneRefundAmount){
        return phoneRefundAmountDao.addPhoneRefundAmount(result, phoneRefundAmount);
    }

    
    
    public Result queryByOrderCode(Result result,String orderCode){
        return phoneRefundAmountDao.queryByOrderCode(result, orderCode);
    }
    
    public Result updateRefundStatusAndTime(Result result, String refundCode, Integer refundStatus,Date time) {
        return phoneRefundAmountDao.updateRefundStatusAndTime(result, refundCode, refundStatus, time);
    }
}
