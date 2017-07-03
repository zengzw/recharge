package com.tsh.vas.phone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.phone.PhoneServiceProviderDao;
import com.tsh.vas.model.phone.PhoneServiceProviderPo;
import com.tsh.vas.vo.phone.PhoneServiceProviderVo;


@Service
@SuppressWarnings("all")
public class PhoneServiceProviderService {
    @Autowired
    private PhoneServiceProviderDao phoneServiceProviderDao;

    @Autowired
    PhoneFareBuildHelper phoneFareBuildHelper;
    /**
     * 新增
     * @param result
     * @param phoneServiceProvider
     * @return
     */
    public Result addPhoneServiceProvider(Result result,PhoneServiceProviderVo phoneServiceProviderVo)throws Exception{
        PhoneServiceProviderPo phoneServiceProviderPo = new PhoneServiceProviderPo();

        if (phoneServiceProviderVo != null) {
            if(phoneServiceProviderVo.getProviderCode()!=null){
                phoneServiceProviderPo.setProviderCode(phoneServiceProviderVo.getProviderCode());
            }
            if(phoneServiceProviderVo.getProviderName()!=null){
                phoneServiceProviderPo.setProviderName(phoneServiceProviderVo.getProviderName());
            }
            if(phoneServiceProviderVo.getCreateTime()!=null){
                phoneServiceProviderPo.setCreateTime(phoneServiceProviderVo.getCreateTime());
            }
        }

        result = phoneServiceProviderDao.addPhoneServiceProvider(result,phoneServiceProviderPo);
        return result;
    }



    /**
     * 保存  带User对象
     * @param result
     * @return
     */
    public Result savePhoneServiceProvider(Result result,PhoneServiceProviderVo phoneServiceProviderVo,UserInfo user) throws Exception {
        if(phoneServiceProviderVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phoneServiceProviderVo.getId();
        result = phoneServiceProviderDao.getPhoneServiceProviderById(result,id);
        PhoneServiceProviderPo phoneServiceProviderPo  = (PhoneServiceProviderPo)result.getData();

        if (phoneServiceProviderPo != null) {
            if(phoneServiceProviderVo.getProviderCode()!=null){
                phoneServiceProviderPo.setProviderCode(phoneServiceProviderVo.getProviderCode());
            }
            if(phoneServiceProviderVo.getProviderName()!=null){
                phoneServiceProviderPo.setProviderName(phoneServiceProviderVo.getProviderName());
            }
            if(phoneServiceProviderVo.getCreateTime()!=null){
                phoneServiceProviderPo.setCreateTime(phoneServiceProviderVo.getCreateTime());
            }
        }else{
            phoneServiceProviderPo = new PhoneServiceProviderPo();
            if(phoneServiceProviderVo.getProviderCode()!=null){
                phoneServiceProviderPo.setProviderCode(phoneServiceProviderVo.getProviderCode());
            }
            if(phoneServiceProviderVo.getProviderName()!=null){
                phoneServiceProviderPo.setProviderName(phoneServiceProviderVo.getProviderName());
            }
            if(phoneServiceProviderVo.getCreateTime()!=null){
                phoneServiceProviderPo.setCreateTime(phoneServiceProviderVo.getCreateTime());
            }
            result = phoneServiceProviderDao.addPhoneServiceProvider(result,phoneServiceProviderPo);
        }
        return result;
    }



    /**
     * 保存 
     * @param result
     * @return
     */
    public Result savePhoneServiceProvider(Result result,PhoneServiceProviderVo phoneServiceProviderVo) throws Exception {
        if(phoneServiceProviderVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phoneServiceProviderVo.getId();
        result = phoneServiceProviderDao.getPhoneServiceProviderById(result,id);
        PhoneServiceProviderPo phoneServiceProviderPo  = (PhoneServiceProviderPo)result.getData();

        if (phoneServiceProviderPo != null) {
            if(phoneServiceProviderVo.getProviderCode()!=null){
                phoneServiceProviderPo.setProviderCode(phoneServiceProviderVo.getProviderCode());
            }
            if(phoneServiceProviderVo.getProviderName()!=null){
                phoneServiceProviderPo.setProviderName(phoneServiceProviderVo.getProviderName());
            }
            if(phoneServiceProviderVo.getCreateTime()!=null){
                phoneServiceProviderPo.setCreateTime(phoneServiceProviderVo.getCreateTime());
            }
        }else{
            phoneServiceProviderPo = new PhoneServiceProviderPo();
            if(phoneServiceProviderVo.getProviderCode()!=null){
                phoneServiceProviderPo.setProviderCode(phoneServiceProviderVo.getProviderCode());
            }
            if(phoneServiceProviderVo.getProviderName()!=null){
                phoneServiceProviderPo.setProviderName(phoneServiceProviderVo.getProviderName());
            }
            if(phoneServiceProviderVo.getCreateTime()!=null){
                phoneServiceProviderPo.setCreateTime(phoneServiceProviderVo.getCreateTime());
            }
            result = phoneServiceProviderDao.addPhoneServiceProvider(result,phoneServiceProviderPo);
        }
        return result;
    }


    /**
     * 批量新增
     * @param result
     * @param phoneServiceProvider
     * @return
     */
    public Result batchSavePhoneServiceProvider(Result result, List<PhoneServiceProviderVo> phoneServiceProvider_list) throws Exception {
        List<PhoneServiceProviderPo> list = new ArrayList<PhoneServiceProviderPo>();
        result = phoneServiceProviderDao.batchSavePhoneServiceProvider(result,list);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneServiceProvider(Result result, Integer id) throws Exception {
        result = phoneServiceProviderDao.deletePhoneServiceProvider(result,id);
        return result;
    }


    /**
     * 批量删除
     * @param result
     * @param phoneServiceProvider
     * @return
     */
    public Result batchDelPhoneServiceProvider(Result result, List<PhoneServiceProviderVo> phoneServiceProvider_list)throws Exception{
        List<PhoneServiceProviderPo> list = new ArrayList<PhoneServiceProviderPo>(); 
        phoneServiceProviderDao.batchDelete(list);
        return result;
    }


    /**
     * 批量删除ByIds
     * @param result
     * @param phoneServiceProvider
     * @return
     */
    public Result batchDelPhoneServiceProviderByIds(Result result,Integer[] ids)throws Exception{
        phoneServiceProviderDao.batchDelPhoneServiceProviderByIds(result,ids);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneServiceProviderList(Result result,Page page,PhoneServiceProviderVo phoneServiceProviderVo){
        PhoneServiceProviderPo phoneServiceProviderPo = new PhoneServiceProviderPo();
        result = phoneServiceProviderDao.queryPhoneServiceProviderList(result,page,phoneServiceProviderPo);
        return result;
    }


    /**
     * 根据条件获取 列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneServiceProviderList(Result result,Page page,PhoneServiceProviderVo phoneServiceProviderVo,UserInfo user){
        PhoneServiceProviderPo phoneServiceProviderPo = new PhoneServiceProviderPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = phoneServiceProviderDao.queryPhoneServiceProviderList(result,page,phoneServiceProviderPo);
        return result;
    }


    /**
     * 根据ID获取  带User对象
     * @param result
     * @return
     */
    public Result getPhoneServiceProviderById(Result result,Long id,UserInfo user) throws Exception{
        result = phoneServiceProviderDao.getPhoneServiceProviderById(result,id);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneServiceProviderById(Result result,Long id) throws Exception{
        result = phoneServiceProviderDao.getPhoneServiceProviderById(result,id);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhoneServiceProvider(Result result,PhoneServiceProviderVo phoneServiceProviderVo) throws Exception {
        Long id = phoneServiceProviderVo.getId();
        result = phoneServiceProviderDao.getPhoneServiceProviderById(result,id);
        PhoneServiceProviderPo phoneServiceProviderPo  = (PhoneServiceProviderPo)result.getData();
        if (phoneServiceProviderPo != null) {
            if(phoneServiceProviderVo.getProviderCode()!=null){
                phoneServiceProviderPo.setProviderCode(phoneServiceProviderVo.getProviderCode());
            }
            if(phoneServiceProviderVo.getProviderName()!=null){
                phoneServiceProviderPo.setProviderName(phoneServiceProviderVo.getProviderName());
            }
            if(phoneServiceProviderVo.getCreateTime()!=null){
                phoneServiceProviderPo.setCreateTime(phoneServiceProviderVo.getCreateTime());
            }
        }
        return result;
    }
    
    /**
     * 批量查询运营商
     * @param ids - 以逗号分隔的字符串 譬如 "'1','2','3'"
     * @return
     */
    public Result batchQueryPhoneServiceProviderByIds(Result result, String ids) {
    	return phoneServiceProviderDao.batchQueryPhoneServiceProviderByIds(result, ids);
    }


 
}
