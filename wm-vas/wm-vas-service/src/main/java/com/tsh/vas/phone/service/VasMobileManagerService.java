package com.tsh.vas.phone.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.dao.phone.PhoneMobileManageDao;
import com.tsh.vas.model.phone.PhoneMobileManagePo;
import com.tsh.vas.vo.phone.PhoneMobileManagerVo;

/**
 * Created by Administrator on 2017/5/2 002.
 */

@Service
public class VasMobileManagerService {

    private static final Logger logger = LoggerFactory.getLogger(VasMobileManagerService.class);

    @Autowired
    private PhoneMobileManageDao phoneMobileManageDao;

    /**
     *
     * @param result
     * @param page
     * @param managerVo
     * @return
     */
    public Result pageList(Result result, Page page, PhoneMobileManagerVo managerVo){
        Pagination pagination = phoneMobileManageDao.pageList(result, page, managerVo).getData();
        if(null != pagination && null != pagination.getRows() && !pagination.getRows().isEmpty()){
            List<PhoneMobileManagePo> poList = (List<PhoneMobileManagePo>) pagination.getRows();
            List<PhoneMobileManagerVo> voList = new ArrayList<>();
            for(PhoneMobileManagePo po : poList){
                voList.add(this.po2Vo(po));
            }
            pagination.setRows(voList);
        }
        return result;
    }

    /**
     *
     * @param po
     * @return
     */
    private PhoneMobileManagerVo po2Vo(PhoneMobileManagePo po) {
        PhoneMobileManagerVo vo = null;
        if(null != po){
            vo = new PhoneMobileManagerVo();
            vo.setId(po.getId()) ;
            vo.setMobileShort(po.getMobileShort()) ;
            vo.setMobileNum(po.getMobileNum()) ;
            vo.setMobileProvince(po.getMobileProvince()) ;
            if("直辖县".equals(po.getMobileCity()) || "直辖区".equals(po.getMobileCity())){
                vo.setMobileCity(po.getMobileProvince()) ;
            } else {
                vo.setMobileCity(po.getMobileCity()) ;
            }
            vo.setMobileSupplier(po.getMobileSupplier()) ;
            vo.setCitycode(po.getCitycode()) ;
            vo.setPostcode(po.getPostcode()) ;
            vo.setCreatetime(po.getCreatetime()) ;
            vo.setCreateTimeStr(DateUtil.date2String(po.getCreatetime()));
            vo.setCreateby(po.getCreateby()) ;
        }
        return vo;
    }

    /**
     *
     * @param result
     * @param mobileManagerVo
     * @return
     */
    public Result save(Result result, PhoneMobileManagerVo mobileManagerVo) throws Exception {
        PhoneMobileManagePo poExist = phoneMobileManageDao.queryByMobilenum(result, mobileManagerVo.getMobileNum()).getData();
        if(null != poExist){
            throw new Exception("该号段已经存在");
        }

        PhoneMobileManagePo po = new PhoneMobileManagePo();
        po.setMobileShort(mobileManagerVo.getMobileShort()) ;
        po.setMobileNum(mobileManagerVo.getMobileNum()) ;
        po.setMobileProvince(mobileManagerVo.getMobileProvince()) ;
        po.setMobileCity(mobileManagerVo.getMobileCity()) ;
        po.setMobileSupplier(mobileManagerVo.getMobileSupplier()) ;
        po.setCreatetime(new Date()) ;
        UserInfo userInfo = result.getUserInfo();
        if(null != userInfo && StringUtils.isNotBlank(userInfo.getLoginName())){
            po.setCreateby(userInfo.getLoginName()) ;
        } else {
            po.setCreateby("空") ;
        }
        phoneMobileManageDao.save(po);
        return result;
    }


    /**
     *
     * @param result
     * @param ids
     * @return
     */
    public Result delete(Result result, String ids) {
        if(StringUtils.isNotBlank(ids)){
            String [] idArray = ids.split(",");
            for(String id : idArray){
                phoneMobileManageDao.delete(Long.valueOf(id));
            }
        }
        return result;
    }


    /**
     * 根据手机号码 查询号码 归属地
     * 
     * @param result
     * @param mobileNum
     * @return
     */
    public Result queryByMobilenum(Result result, String mobileNum) {
        if(StringUtils.isEmpty(mobileNum)){
            throw new BusinessRuntimeException("", "mobileNum参数为空");
        }

        String belongNum = mobileNum.substring(0,7); //根据前面7位，获取归属地
        PhoneMobileManagePo phoneMobileManagePo = phoneMobileManageDao.queryByMobilenum(result, belongNum).getData();

        logger.info("#-->mobileNum:{},查询归属地结果：{}",mobileNum,JSON.toJSONString(phoneMobileManagePo));

        PhoneMobileManagerVo phoneMobileManagerVo = new PhoneMobileManagerVo();
        if(phoneMobileManagePo != null){
            BeanUtils.copyProperties(phoneMobileManagePo, phoneMobileManagerVo);
            result.setData(phoneMobileManagerVo);
            return result;
        }

        return result;
    }


    /**
     * 新增
     * 
     * @param result
     * @param phoneGoods
     * @return 
     * @return  添加对象
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public Result addMobileSegmentNo(Result result,PhoneMobileManagerVo mobileManageVo)throws Exception{
        if(mobileManageVo  == null){
            throw new BusinessRuntimeException("", "对象为空");
        }
        
        PhoneMobileManagePo phoneMobileManage = phoneMobileManageDao.queryByMobilenum(result, mobileManageVo.getMobileNum()).getData();
        if(phoneMobileManage != null){
            logger.info("#-->数据已经存在");
            return result;
        }
        
        PhoneMobileManagePo phoneMobileManagePo = new PhoneMobileManagePo();
        BeanUtils.copyProperties(mobileManageVo, phoneMobileManagePo);

        phoneMobileManageDao.addMobileSegmentNo(result, phoneMobileManagePo);
        return result;
    }

}
