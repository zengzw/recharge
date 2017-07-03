package com.tsh.vas.phone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.phone.PhoneValueDao;
import com.tsh.vas.model.phone.PhoneValuePo;
import com.tsh.vas.vo.phone.PhoneValueVo;


@Service
@SuppressWarnings("all")
public class PhoneValueService {
    @Autowired
    private PhoneValueDao phoneValueDao;

    /**
     * 新增
     * @param result
     * @param phoneValue
     * @return
     */
    public Result addPhoneValue(Result result,PhoneValueVo phoneValueVo)throws Exception{
        PhoneValuePo phoneValuePo = new PhoneValuePo();

        if (phoneValueVo != null) {
            if(phoneValueVo.getValue()!=null){
                phoneValuePo.setValue(phoneValueVo.getValue());
            }
            if(phoneValueVo.getCreateTime()!=null){
                phoneValuePo.setCreateTime(phoneValueVo.getCreateTime());
            }
        }

        result = phoneValueDao.addPhoneValue(result,phoneValuePo);
        return result;
    }



    /**
     * 保存  带User对象
     * @param result
     * @return
     */
    public Result savePhoneValue(Result result,PhoneValueVo phoneValueVo,UserInfo user) throws Exception {
        if(phoneValueVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phoneValueVo.getId();
        result = phoneValueDao.getPhoneValueById(result,id);
        PhoneValuePo phoneValuePo  = (PhoneValuePo)result.getData();

        if (phoneValuePo != null) {
            if(phoneValueVo.getValue()!=null){
                phoneValuePo.setValue(phoneValueVo.getValue());
            }
            if(phoneValueVo.getCreateTime()!=null){
                phoneValuePo.setCreateTime(phoneValueVo.getCreateTime());
            }
        }else{
            phoneValuePo = new PhoneValuePo();
            if(phoneValueVo.getValue()!=null){
                phoneValuePo.setValue(phoneValueVo.getValue());
            }
            if(phoneValueVo.getCreateTime()!=null){
                phoneValuePo.setCreateTime(phoneValueVo.getCreateTime());
            }
            result = phoneValueDao.addPhoneValue(result,phoneValuePo);
        }
        return result;
    }



    /**
     * 保存 
     * @param result
     * @return
     */
    public Result savePhoneValue(Result result,PhoneValueVo phoneValueVo) throws Exception {
        if(phoneValueVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phoneValueVo.getId();
        result = phoneValueDao.getPhoneValueById(result,id);
        PhoneValuePo phoneValuePo  = (PhoneValuePo)result.getData();

        if (phoneValuePo != null) {
            if(phoneValueVo.getValue()!=null){
                phoneValuePo.setValue(phoneValueVo.getValue());
            }
            if(phoneValueVo.getCreateTime()!=null){
                phoneValuePo.setCreateTime(phoneValueVo.getCreateTime());
            }
        }else{
            phoneValuePo = new PhoneValuePo();
            if(phoneValueVo.getValue()!=null){
                phoneValuePo.setValue(phoneValueVo.getValue());
            }
            if(phoneValueVo.getCreateTime()!=null){
                phoneValuePo.setCreateTime(phoneValueVo.getCreateTime());
            }
            result = phoneValueDao.addPhoneValue(result,phoneValuePo);
        }
        return result;
    }


    /**
     * 批量新增
     * @param result
     * @param phoneValue
     * @return
     */
    public Result batchSavePhoneValue(Result result, List<PhoneValueVo> phoneValue_list) throws Exception {
        List<PhoneValuePo> list = new ArrayList<PhoneValuePo>();
        result = phoneValueDao.batchSavePhoneValue(result,list);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneValue(Result result, Integer id) throws Exception {
        result = phoneValueDao.deletePhoneValue(result,id);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneValueById(Result result, Long id) throws Exception {
        result = phoneValueDao.deletePhoneValueById(result,id);
        return result;
    }


    /**
     * 批量删除
     * @param result
     * @param phoneValue
     * @return
     */
    public Result batchDelPhoneValue(Result result, List<PhoneValueVo> phoneValue_list)throws Exception{
        List<PhoneValuePo> list = new ArrayList<PhoneValuePo>(); 
        phoneValueDao.batchDelete(list);
        return result;
    }


    /**
     * 批量删除ByIds
     * @param result
     * @param phoneValue
     * @return
     */
    public Result batchDelPhoneValueByIds(Result result,Integer[] ids)throws Exception{
        phoneValueDao.batchDelPhoneValueByIds(result,ids);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneValueList(Result result,Page page,PhoneValueVo phoneValueVo){
        PhoneValuePo phoneValuePo = new PhoneValuePo();
        result = phoneValueDao.queryPhoneValueList(result,page,phoneValuePo);
        return result;
    }


    /**
     * 根据条件获取 列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneValueList(Result result,Page page,PhoneValueVo phoneValueVo,UserInfo user){
        PhoneValuePo phoneValuePo = new PhoneValuePo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = phoneValueDao.queryPhoneValueList(result,page,phoneValuePo);
        return result;
    }


    /**
     * 根据ID获取  带User对象
     * @param result
     * @return
     */
    public Result getPhoneValueById(Result result,Long id,UserInfo user) throws Exception{
        result = phoneValueDao.getPhoneValueById(result,id);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneValueById(Result result,Long id) throws Exception{
        result = phoneValueDao.getPhoneValueById(result,id);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhoneValue(Result result,PhoneValueVo phoneValueVo) throws Exception {
        Long id = phoneValueVo.getId();
        result = phoneValueDao.getPhoneValueById(result,id);
        PhoneValuePo phoneValuePo  = (PhoneValuePo)result.getData();
        if (phoneValuePo != null) {
            if(phoneValueVo.getValue()!=null){
                phoneValuePo.setValue(phoneValueVo.getValue());
            }
            if(phoneValueVo.getCreateTime()!=null){
                phoneValuePo.setCreateTime(phoneValueVo.getCreateTime());
            }
        }
        return result;
    }


    public Result getPhoneValueByPhoneValue(Result result, Integer phoneValue) {
    	if (phoneValue != null)
    	   return phoneValueDao.getPhoneValueByPhoneValue(result, phoneValue);
    	result.setData(0);
    	return result;
    }
    
    public Result getListPhoneValue(Result result){
        return phoneValueDao.getListPhoneValue(result);
    }
}
