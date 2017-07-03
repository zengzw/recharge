package com.tsh.vas.phone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.phone.PhonePaymentRecordDao;
import com.tsh.vas.model.phone.PhonePaymentRecordPo;
import com.tsh.vas.vo.phone.PhonePaymentRecordVo;


@Service
@SuppressWarnings("all")
public class PhonePaymentRecordService {
    @Autowired
    private PhonePaymentRecordDao phonePaymentRecordDao;

    /**
     * 新增
     * @param result
     * @param phonePaymentRecord
     * @return
     */
    public Result addPhonePaymentRecord(Result result,PhonePaymentRecordVo phonePaymentRecordVo)throws Exception{
        PhonePaymentRecordPo phonePaymentRecordPo = new PhonePaymentRecordPo();

        if (phonePaymentRecordVo != null) {
            if(phonePaymentRecordVo.getPayType()!=null){
                phonePaymentRecordPo.setPayType(phonePaymentRecordVo.getPayType());
            }
            if(phonePaymentRecordVo.getPhoneOrderCode()!=null){
                phonePaymentRecordPo.setPhoneOrderCode(phonePaymentRecordVo.getPhoneOrderCode());
            }
            if(phonePaymentRecordVo.getPayAmount()!=null){
                phonePaymentRecordPo.setPayAmount(phonePaymentRecordVo.getPayAmount());
            }
            if(phonePaymentRecordVo.getPayWay()!=null){
                phonePaymentRecordPo.setPayWay(phonePaymentRecordVo.getPayWay());
            }
            if(phonePaymentRecordVo.getCreateTime()!=null){
                phonePaymentRecordPo.setCreateTime(phonePaymentRecordVo.getCreateTime());
            }
            if(phonePaymentRecordVo.getRemark()!=null){
                phonePaymentRecordPo.setRemark(phonePaymentRecordVo.getRemark());
            }
        }

        result = phonePaymentRecordDao.addPhonePaymentRecord(result,phonePaymentRecordPo);
        return result;
    }



    /**
     * 保存  带User对象
     * @param result
     * @return
     */
    public Result savePhonePaymentRecord(Result result,PhonePaymentRecordVo phonePaymentRecordVo,UserInfo user) throws Exception {
        if(phonePaymentRecordVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phonePaymentRecordVo.getId();
        result = phonePaymentRecordDao.getPhonePaymentRecordById(result,id);
        PhonePaymentRecordPo phonePaymentRecordPo  = (PhonePaymentRecordPo)result.getData();

        if (phonePaymentRecordPo != null) {
            if(phonePaymentRecordVo.getPayType()!=null){
                phonePaymentRecordPo.setPayType(phonePaymentRecordVo.getPayType());
            }
            if(phonePaymentRecordVo.getPhoneOrderCode()!=null){
                phonePaymentRecordPo.setPhoneOrderCode(phonePaymentRecordVo.getPhoneOrderCode());
            }
            if(phonePaymentRecordVo.getPayAmount()!=null){
                phonePaymentRecordPo.setPayAmount(phonePaymentRecordVo.getPayAmount());
            }
            if(phonePaymentRecordVo.getPayWay()!=null){
                phonePaymentRecordPo.setPayWay(phonePaymentRecordVo.getPayWay());
            }
            if(phonePaymentRecordVo.getCreateTime()!=null){
                phonePaymentRecordPo.setCreateTime(phonePaymentRecordVo.getCreateTime());
            }
            if(phonePaymentRecordVo.getRemark()!=null){
                phonePaymentRecordPo.setRemark(phonePaymentRecordVo.getRemark());
            }
        }else{
            phonePaymentRecordPo = new PhonePaymentRecordPo();
            if(phonePaymentRecordVo.getPayType()!=null){
                phonePaymentRecordPo.setPayType(phonePaymentRecordVo.getPayType());
            }
            if(phonePaymentRecordVo.getPhoneOrderCode()!=null){
                phonePaymentRecordPo.setPhoneOrderCode(phonePaymentRecordVo.getPhoneOrderCode());
            }
            if(phonePaymentRecordVo.getPayAmount()!=null){
                phonePaymentRecordPo.setPayAmount(phonePaymentRecordVo.getPayAmount());
            }
            if(phonePaymentRecordVo.getPayWay()!=null){
                phonePaymentRecordPo.setPayWay(phonePaymentRecordVo.getPayWay());
            }
            if(phonePaymentRecordVo.getCreateTime()!=null){
                phonePaymentRecordPo.setCreateTime(phonePaymentRecordVo.getCreateTime());
            }
            if(phonePaymentRecordVo.getRemark()!=null){
                phonePaymentRecordPo.setRemark(phonePaymentRecordVo.getRemark());
            }
            result = phonePaymentRecordDao.addPhonePaymentRecord(result,phonePaymentRecordPo);
        }
        return result;
    }



    /**
     * 保存 
     * @param result
     * @return
     */
    public Result savePhonePaymentRecord(Result result,PhonePaymentRecordVo phonePaymentRecordVo) throws Exception {
        if(phonePaymentRecordVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phonePaymentRecordVo.getId();
        result = phonePaymentRecordDao.getPhonePaymentRecordById(result,id);
        PhonePaymentRecordPo phonePaymentRecordPo  = (PhonePaymentRecordPo)result.getData();

        if (phonePaymentRecordPo != null) {
            if(phonePaymentRecordVo.getPayType()!=null){
                phonePaymentRecordPo.setPayType(phonePaymentRecordVo.getPayType());
            }
            if(phonePaymentRecordVo.getPhoneOrderCode()!=null){
                phonePaymentRecordPo.setPhoneOrderCode(phonePaymentRecordVo.getPhoneOrderCode());
            }
            if(phonePaymentRecordVo.getPayAmount()!=null){
                phonePaymentRecordPo.setPayAmount(phonePaymentRecordVo.getPayAmount());
            }
            if(phonePaymentRecordVo.getPayWay()!=null){
                phonePaymentRecordPo.setPayWay(phonePaymentRecordVo.getPayWay());
            }
            if(phonePaymentRecordVo.getCreateTime()!=null){
                phonePaymentRecordPo.setCreateTime(phonePaymentRecordVo.getCreateTime());
            }
            if(phonePaymentRecordVo.getRemark()!=null){
                phonePaymentRecordPo.setRemark(phonePaymentRecordVo.getRemark());
            }
        }else{
            phonePaymentRecordPo = new PhonePaymentRecordPo();
            if(phonePaymentRecordVo.getPayType()!=null){
                phonePaymentRecordPo.setPayType(phonePaymentRecordVo.getPayType());
            }
            if(phonePaymentRecordVo.getPhoneOrderCode()!=null){
                phonePaymentRecordPo.setPhoneOrderCode(phonePaymentRecordVo.getPhoneOrderCode());
            }
            if(phonePaymentRecordVo.getPayAmount()!=null){
                phonePaymentRecordPo.setPayAmount(phonePaymentRecordVo.getPayAmount());
            }
            if(phonePaymentRecordVo.getPayWay()!=null){
                phonePaymentRecordPo.setPayWay(phonePaymentRecordVo.getPayWay());
            }
            if(phonePaymentRecordVo.getCreateTime()!=null){
                phonePaymentRecordPo.setCreateTime(phonePaymentRecordVo.getCreateTime());
            }
            if(phonePaymentRecordVo.getRemark()!=null){
                phonePaymentRecordPo.setRemark(phonePaymentRecordVo.getRemark());
            }
            result = phonePaymentRecordDao.addPhonePaymentRecord(result,phonePaymentRecordPo);
        }
        return result;
    }


    /**
     * 批量新增
     * @param result
     * @param phonePaymentRecord
     * @return
     */
    public Result batchSavePhonePaymentRecord(Result result, List<PhonePaymentRecordVo> phonePaymentRecord_list) throws Exception {
        List<PhonePaymentRecordPo> list = new ArrayList<PhonePaymentRecordPo>();
        result = phonePaymentRecordDao.batchSavePhonePaymentRecord(result,list);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhonePaymentRecord(Result result, Integer id) throws Exception {
        result = phonePaymentRecordDao.deletePhonePaymentRecord(result,id);
        return result;
    }


    /**
     * 批量删除
     * @param result
     * @param phonePaymentRecord
     * @return
     */
    public Result batchDelPhonePaymentRecord(Result result, List<PhonePaymentRecordVo> phonePaymentRecord_list)throws Exception{
        List<PhonePaymentRecordPo> list = new ArrayList<PhonePaymentRecordPo>(); 
        phonePaymentRecordDao.batchDelete(list);
        return result;
    }


    /**
     * 批量删除ByIds
     * @param result
     * @param phonePaymentRecord
     * @return
     */
    public Result batchDelPhonePaymentRecordByIds(Result result,Integer[] ids)throws Exception{
        phonePaymentRecordDao.batchDelPhonePaymentRecordByIds(result,ids);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhonePaymentRecordList(Result result,Page page,PhonePaymentRecordVo phonePaymentRecordVo){
        PhonePaymentRecordPo phonePaymentRecordPo = new PhonePaymentRecordPo();
        result = phonePaymentRecordDao.queryPhonePaymentRecordList(result,page,phonePaymentRecordPo);
        return result;
    }


    /**
     * 根据条件获取 列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhonePaymentRecordList(Result result,Page page,PhonePaymentRecordVo phonePaymentRecordVo,UserInfo user){
        PhonePaymentRecordPo phonePaymentRecordPo = new PhonePaymentRecordPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = phonePaymentRecordDao.queryPhonePaymentRecordList(result,page,phonePaymentRecordPo);
        return result;
    }


    /**
     * 根据ID获取  带User对象
     * @param result
     * @return
     */
    public Result getPhonePaymentRecordById(Result result,Long id,UserInfo user) throws Exception{
        result = phonePaymentRecordDao.getPhonePaymentRecordById(result,id);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhonePaymentRecordById(Result result,Long id) throws Exception{
        result = phonePaymentRecordDao.getPhonePaymentRecordById(result,id);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhonePaymentRecord(Result result,PhonePaymentRecordVo phonePaymentRecordVo) throws Exception {
        Long id = phonePaymentRecordVo.getId();
        result = phonePaymentRecordDao.getPhonePaymentRecordById(result,id);
        PhonePaymentRecordPo phonePaymentRecordPo  = (PhonePaymentRecordPo)result.getData();
        if (phonePaymentRecordPo != null) {
            if(phonePaymentRecordVo.getPayType()!=null){
                phonePaymentRecordPo.setPayType(phonePaymentRecordVo.getPayType());
            }
            if(phonePaymentRecordVo.getPhoneOrderCode()!=null){
                phonePaymentRecordPo.setPhoneOrderCode(phonePaymentRecordVo.getPhoneOrderCode());
            }
            if(phonePaymentRecordVo.getPayAmount()!=null){
                phonePaymentRecordPo.setPayAmount(phonePaymentRecordVo.getPayAmount());
            }
            if(phonePaymentRecordVo.getPayWay()!=null){
                phonePaymentRecordPo.setPayWay(phonePaymentRecordVo.getPayWay());
            }
            if(phonePaymentRecordVo.getCreateTime()!=null){
                phonePaymentRecordPo.setCreateTime(phonePaymentRecordVo.getCreateTime());
            }
            if(phonePaymentRecordVo.getRemark()!=null){
                phonePaymentRecordPo.setRemark(phonePaymentRecordVo.getRemark());
            }
        }
        return result;
    }


    /**
     * 批量更新 
     * @param result
     * @return
     */
    public Result batchUpdatePhonePaymentRecord(Result result,List<PhonePaymentRecordVo> phonePaymentRecord_list) throws Exception {
        List<PhonePaymentRecordPo> list = new ArrayList<PhonePaymentRecordPo>(); 
        phonePaymentRecordDao.batchUpdatePhonePaymentRecord(result,list);
        return result;
    }
    
    
    public Result addPhonePaymentRecord(Result result,PhonePaymentRecordPo phonePaymentRecord)throws Exception{
        return phonePaymentRecordDao.addPhonePaymentRecord(result, phonePaymentRecord);
    }

}
