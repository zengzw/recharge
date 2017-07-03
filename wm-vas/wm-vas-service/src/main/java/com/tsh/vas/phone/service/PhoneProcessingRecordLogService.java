package com.tsh.vas.phone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.phone.PhoneProcessingRecordLogDao;
import com.tsh.vas.model.phone.PhoneProcessingRecordLogPo;
import com.tsh.vas.vo.phone.PhoneProcessingRecordLogVo;


@Service
@SuppressWarnings("all")
public class PhoneProcessingRecordLogService {
    @Autowired
    private PhoneProcessingRecordLogDao phoneProcessingRecordLogDao;

    /**
     * 新增
     * @param result
     * @param phoneProcessingRecordLog
     * @return
     */
    public Result addPhoneProcessingRecordLog(Result result,PhoneProcessingRecordLogVo phoneProcessingRecordLogVo)throws Exception{
        PhoneProcessingRecordLogPo phoneProcessingRecordLogPo = new PhoneProcessingRecordLogPo();

        if (phoneProcessingRecordLogVo != null) {
            if(phoneProcessingRecordLogVo.getMsgid()!=null){
                phoneProcessingRecordLogPo.setMsgid(phoneProcessingRecordLogVo.getMsgid());
            }
            if(phoneProcessingRecordLogVo.getSuppliertype()!=null){
                phoneProcessingRecordLogPo.setSuppliertype(phoneProcessingRecordLogVo.getSuppliertype());
            }
            if(phoneProcessingRecordLogVo.getHttpType()!=null){
                phoneProcessingRecordLogPo.setHttpType(phoneProcessingRecordLogVo.getHttpType());
            }
            if(phoneProcessingRecordLogVo.getMethodname()!=null){
                phoneProcessingRecordLogPo.setMethodname(phoneProcessingRecordLogVo.getMethodname());
            }
            if(phoneProcessingRecordLogVo.getLogtype()!=null){
                phoneProcessingRecordLogPo.setLogtype(phoneProcessingRecordLogVo.getLogtype());
            }
            if(phoneProcessingRecordLogVo.getLogcontent()!=null){
                phoneProcessingRecordLogPo.setLogcontent(phoneProcessingRecordLogVo.getLogcontent());
            }
            if(phoneProcessingRecordLogVo.getCreatetime()!=null){
                phoneProcessingRecordLogPo.setCreatetime(phoneProcessingRecordLogVo.getCreatetime());
            }
            if(phoneProcessingRecordLogVo.getCost()!=null){
                phoneProcessingRecordLogPo.setCost(phoneProcessingRecordLogVo.getCost());
            }
        }

        result = phoneProcessingRecordLogDao.addPhoneProcessingRecordLog(result,phoneProcessingRecordLogPo);
        return result;
    }



    /**
     * 保存  带User对象
     * @param result
     * @return
     */
    public Result savePhoneProcessingRecordLog(Result result,PhoneProcessingRecordLogVo phoneProcessingRecordLogVo,UserInfo user) throws Exception {
        if(phoneProcessingRecordLogVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phoneProcessingRecordLogVo.getId();
        result = phoneProcessingRecordLogDao.getPhoneProcessingRecordLogById(result,id);
        PhoneProcessingRecordLogPo phoneProcessingRecordLogPo  = (PhoneProcessingRecordLogPo)result.getData();

        if (phoneProcessingRecordLogPo != null) {
            if(phoneProcessingRecordLogVo.getMsgid()!=null){
                phoneProcessingRecordLogPo.setMsgid(phoneProcessingRecordLogVo.getMsgid());
            }
            if(phoneProcessingRecordLogVo.getSuppliertype()!=null){
                phoneProcessingRecordLogPo.setSuppliertype(phoneProcessingRecordLogVo.getSuppliertype());
            }
            if(phoneProcessingRecordLogVo.getHttpType()!=null){
                phoneProcessingRecordLogPo.setHttpType(phoneProcessingRecordLogVo.getHttpType());
            }
            if(phoneProcessingRecordLogVo.getMethodname()!=null){
                phoneProcessingRecordLogPo.setMethodname(phoneProcessingRecordLogVo.getMethodname());
            }
            if(phoneProcessingRecordLogVo.getLogtype()!=null){
                phoneProcessingRecordLogPo.setLogtype(phoneProcessingRecordLogVo.getLogtype());
            }
            if(phoneProcessingRecordLogVo.getLogcontent()!=null){
                phoneProcessingRecordLogPo.setLogcontent(phoneProcessingRecordLogVo.getLogcontent());
            }
            if(phoneProcessingRecordLogVo.getCreatetime()!=null){
                phoneProcessingRecordLogPo.setCreatetime(phoneProcessingRecordLogVo.getCreatetime());
            }
            if(phoneProcessingRecordLogVo.getCost()!=null){
                phoneProcessingRecordLogPo.setCost(phoneProcessingRecordLogVo.getCost());
            }
        }else{
            phoneProcessingRecordLogPo = new PhoneProcessingRecordLogPo();
            if(phoneProcessingRecordLogVo.getMsgid()!=null){
                phoneProcessingRecordLogPo.setMsgid(phoneProcessingRecordLogVo.getMsgid());
            }
            if(phoneProcessingRecordLogVo.getSuppliertype()!=null){
                phoneProcessingRecordLogPo.setSuppliertype(phoneProcessingRecordLogVo.getSuppliertype());
            }
            if(phoneProcessingRecordLogVo.getHttpType()!=null){
                phoneProcessingRecordLogPo.setHttpType(phoneProcessingRecordLogVo.getHttpType());
            }
            if(phoneProcessingRecordLogVo.getMethodname()!=null){
                phoneProcessingRecordLogPo.setMethodname(phoneProcessingRecordLogVo.getMethodname());
            }
            if(phoneProcessingRecordLogVo.getLogtype()!=null){
                phoneProcessingRecordLogPo.setLogtype(phoneProcessingRecordLogVo.getLogtype());
            }
            if(phoneProcessingRecordLogVo.getLogcontent()!=null){
                phoneProcessingRecordLogPo.setLogcontent(phoneProcessingRecordLogVo.getLogcontent());
            }
            if(phoneProcessingRecordLogVo.getCreatetime()!=null){
                phoneProcessingRecordLogPo.setCreatetime(phoneProcessingRecordLogVo.getCreatetime());
            }
            if(phoneProcessingRecordLogVo.getCost()!=null){
                phoneProcessingRecordLogPo.setCost(phoneProcessingRecordLogVo.getCost());
            }
            result = phoneProcessingRecordLogDao.addPhoneProcessingRecordLog(result,phoneProcessingRecordLogPo);
        }
        return result;
    }



    /**
     * 保存 
     * @param result
     * @return
     */
    public Result savePhoneProcessingRecordLog(Result result,PhoneProcessingRecordLogVo phoneProcessingRecordLogVo) throws Exception {
        if(phoneProcessingRecordLogVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phoneProcessingRecordLogVo.getId();
        result = phoneProcessingRecordLogDao.getPhoneProcessingRecordLogById(result,id);
        PhoneProcessingRecordLogPo phoneProcessingRecordLogPo  = (PhoneProcessingRecordLogPo)result.getData();

        if (phoneProcessingRecordLogPo != null) {
            if(phoneProcessingRecordLogVo.getMsgid()!=null){
                phoneProcessingRecordLogPo.setMsgid(phoneProcessingRecordLogVo.getMsgid());
            }
            if(phoneProcessingRecordLogVo.getSuppliertype()!=null){
                phoneProcessingRecordLogPo.setSuppliertype(phoneProcessingRecordLogVo.getSuppliertype());
            }
            if(phoneProcessingRecordLogVo.getHttpType()!=null){
                phoneProcessingRecordLogPo.setHttpType(phoneProcessingRecordLogVo.getHttpType());
            }
            if(phoneProcessingRecordLogVo.getMethodname()!=null){
                phoneProcessingRecordLogPo.setMethodname(phoneProcessingRecordLogVo.getMethodname());
            }
            if(phoneProcessingRecordLogVo.getLogtype()!=null){
                phoneProcessingRecordLogPo.setLogtype(phoneProcessingRecordLogVo.getLogtype());
            }
            if(phoneProcessingRecordLogVo.getLogcontent()!=null){
                phoneProcessingRecordLogPo.setLogcontent(phoneProcessingRecordLogVo.getLogcontent());
            }
            if(phoneProcessingRecordLogVo.getCreatetime()!=null){
                phoneProcessingRecordLogPo.setCreatetime(phoneProcessingRecordLogVo.getCreatetime());
            }
            if(phoneProcessingRecordLogVo.getCost()!=null){
                phoneProcessingRecordLogPo.setCost(phoneProcessingRecordLogVo.getCost());
            }
        }else{
            phoneProcessingRecordLogPo = new PhoneProcessingRecordLogPo();
            if(phoneProcessingRecordLogVo.getMsgid()!=null){
                phoneProcessingRecordLogPo.setMsgid(phoneProcessingRecordLogVo.getMsgid());
            }
            if(phoneProcessingRecordLogVo.getSuppliertype()!=null){
                phoneProcessingRecordLogPo.setSuppliertype(phoneProcessingRecordLogVo.getSuppliertype());
            }
            if(phoneProcessingRecordLogVo.getHttpType()!=null){
                phoneProcessingRecordLogPo.setHttpType(phoneProcessingRecordLogVo.getHttpType());
            }
            if(phoneProcessingRecordLogVo.getMethodname()!=null){
                phoneProcessingRecordLogPo.setMethodname(phoneProcessingRecordLogVo.getMethodname());
            }
            if(phoneProcessingRecordLogVo.getLogtype()!=null){
                phoneProcessingRecordLogPo.setLogtype(phoneProcessingRecordLogVo.getLogtype());
            }
            if(phoneProcessingRecordLogVo.getLogcontent()!=null){
                phoneProcessingRecordLogPo.setLogcontent(phoneProcessingRecordLogVo.getLogcontent());
            }
            if(phoneProcessingRecordLogVo.getCreatetime()!=null){
                phoneProcessingRecordLogPo.setCreatetime(phoneProcessingRecordLogVo.getCreatetime());
            }
            if(phoneProcessingRecordLogVo.getCost()!=null){
                phoneProcessingRecordLogPo.setCost(phoneProcessingRecordLogVo.getCost());
            }
            result = phoneProcessingRecordLogDao.addPhoneProcessingRecordLog(result,phoneProcessingRecordLogPo);
        }
        return result;
    }


    /**
     * 批量新增
     * @param result
     * @param phoneProcessingRecordLog
     * @return
     */
    public Result batchSavePhoneProcessingRecordLog(Result result, List<PhoneProcessingRecordLogVo> phoneProcessingRecordLog_list) throws Exception {
        List<PhoneProcessingRecordLogPo> list = new ArrayList<PhoneProcessingRecordLogPo>();
        result = phoneProcessingRecordLogDao.batchSavePhoneProcessingRecordLog(result,list);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneProcessingRecordLog(Result result, Integer id) throws Exception {
        result = phoneProcessingRecordLogDao.deletePhoneProcessingRecordLog(result,id);
        return result;
    }


    /**
     * 批量删除
     * @param result
     * @param phoneProcessingRecordLog
     * @return
     */
    public Result batchDelPhoneProcessingRecordLog(Result result, List<PhoneProcessingRecordLogVo> phoneProcessingRecordLog_list)throws Exception{
        List<PhoneProcessingRecordLogPo> list = new ArrayList<PhoneProcessingRecordLogPo>(); 
        phoneProcessingRecordLogDao.batchDelete(list);
        return result;
    }


    /**
     * 批量删除ByIds
     * @param result
     * @param phoneProcessingRecordLog
     * @return
     */
    public Result batchDelPhoneProcessingRecordLogByIds(Result result,Integer[] ids)throws Exception{
        phoneProcessingRecordLogDao.batchDelPhoneProcessingRecordLogByIds(result,ids);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneProcessingRecordLogList(Result result,Page page,PhoneProcessingRecordLogVo phoneProcessingRecordLogVo){
        PhoneProcessingRecordLogPo phoneProcessingRecordLogPo = new PhoneProcessingRecordLogPo();
        result = phoneProcessingRecordLogDao.queryPhoneProcessingRecordLogList(result,page,phoneProcessingRecordLogPo);
        return result;
    }


    /**
     * 根据条件获取 列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneProcessingRecordLogList(Result result,Page page,PhoneProcessingRecordLogVo phoneProcessingRecordLogVo,UserInfo user){
        PhoneProcessingRecordLogPo phoneProcessingRecordLogPo = new PhoneProcessingRecordLogPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = phoneProcessingRecordLogDao.queryPhoneProcessingRecordLogList(result,page,phoneProcessingRecordLogPo);
        return result;
    }


    /**
     * 根据ID获取  带User对象
     * @param result
     * @return
     */
    public Result getPhoneProcessingRecordLogById(Result result,Long id,UserInfo user) throws Exception{
        result = phoneProcessingRecordLogDao.getPhoneProcessingRecordLogById(result,id);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneProcessingRecordLogById(Result result,Long id) throws Exception{
        result = phoneProcessingRecordLogDao.getPhoneProcessingRecordLogById(result,id);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhoneProcessingRecordLog(Result result,PhoneProcessingRecordLogVo phoneProcessingRecordLogVo) throws Exception {
        Long id = phoneProcessingRecordLogVo.getId();
        result = phoneProcessingRecordLogDao.getPhoneProcessingRecordLogById(result,id);
        PhoneProcessingRecordLogPo phoneProcessingRecordLogPo  = (PhoneProcessingRecordLogPo)result.getData();
        if (phoneProcessingRecordLogPo != null) {
            if(phoneProcessingRecordLogVo.getMsgid()!=null){
                phoneProcessingRecordLogPo.setMsgid(phoneProcessingRecordLogVo.getMsgid());
            }
            if(phoneProcessingRecordLogVo.getSuppliertype()!=null){
                phoneProcessingRecordLogPo.setSuppliertype(phoneProcessingRecordLogVo.getSuppliertype());
            }
            if(phoneProcessingRecordLogVo.getHttpType()!=null){
                phoneProcessingRecordLogPo.setHttpType(phoneProcessingRecordLogVo.getHttpType());
            }
            if(phoneProcessingRecordLogVo.getMethodname()!=null){
                phoneProcessingRecordLogPo.setMethodname(phoneProcessingRecordLogVo.getMethodname());
            }
            if(phoneProcessingRecordLogVo.getLogtype()!=null){
                phoneProcessingRecordLogPo.setLogtype(phoneProcessingRecordLogVo.getLogtype());
            }
            if(phoneProcessingRecordLogVo.getLogcontent()!=null){
                phoneProcessingRecordLogPo.setLogcontent(phoneProcessingRecordLogVo.getLogcontent());
            }
            if(phoneProcessingRecordLogVo.getCreatetime()!=null){
                phoneProcessingRecordLogPo.setCreatetime(phoneProcessingRecordLogVo.getCreatetime());
            }
            if(phoneProcessingRecordLogVo.getCost()!=null){
                phoneProcessingRecordLogPo.setCost(phoneProcessingRecordLogVo.getCost());
            }
        }
        return result;
    }


    /**
     * 批量更新 
     * @param result
     * @return
     */
    public Result batchUpdatePhoneProcessingRecordLog(Result result,List<PhoneProcessingRecordLogVo> phoneProcessingRecordLog_list) throws Exception {
        List<PhoneProcessingRecordLogPo> list = new ArrayList<PhoneProcessingRecordLogPo>(); 
        phoneProcessingRecordLogDao.batchUpdatePhoneProcessingRecordLog(result,list);
        return result;
    }

}
