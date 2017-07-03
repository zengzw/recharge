package com.tsh.vas.phone.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.dubbo.bis.vo.AreaInfoVO;
import com.tsh.dubbo.bis.vo.ShopVO;
import com.tsh.vas.commoms.enums.EnumActivityStatus;
import com.tsh.vas.commoms.enums.EnumAuthBusiness;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.dao.ModuleAuthorityDao;
import com.tsh.vas.model.ModuleAuthorityPo;
import com.tsh.vas.service.AreaApiService;
import com.tsh.vas.service.ShopApiService;
import com.tsh.vas.vo.ModuleAuthorityHistoryVo;
import com.tsh.vas.vo.ModuleAuthorityVo;
import com.tsh.vas.vo.QueryModuleAuthorityVo;
import com.tsh.vas.vo.ReceiveShopVo;

/**
 * 权限控制服务类
 * 
 * 针对于是否开发‘增值服务’功能控制
 *
 * @author zengzw
 * @date 2017年6月6日
 */
@Service
@SuppressWarnings("all")
public class ModuleAuthorityService {


    private static final Logger log = LoggerFactory.getLogger(ModuleAuthorityService.class);

    @Autowired
    private ModuleAuthorityDao moduleAuthorityDao;


    @Autowired
    ShopApiService shopApiService;

    @Autowired
    private ModuleAuthorityHistoryService historyService;


    /**
     * 县域dubbo接口
     */
    @Autowired
    private AreaApiService areaService;




    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryByCombindTerm(Result result,ModuleAuthorityVo moduleAuthorityVo){
        if(StringUtils.isEmpty(moduleAuthorityVo.getProjectCode())
                || StringUtils.isEmpty(moduleAuthorityVo.getBusinessCode())){
            throw new BusinessRuntimeException("","缺少必要参数");
        }

        ModuleAuthorityPo moduleAuthorityPo = new ModuleAuthorityPo();
        BeanUtils.copyProperties(moduleAuthorityVo, moduleAuthorityPo);

        //获取县域信息
        ShopVO shopVO = shopApiService.getShop(result, result.getUserInfo().getBizId());
        if(shopVO == null){
            throw new BusinessRuntimeException("", "县域信息为空");
        }

        log.info("#-->shopVO:{}",JSON.toJSONString(shopVO));

        moduleAuthorityPo.setAreaId(Integer.parseInt(shopVO.getAreaId()+""));
        ModuleAuthorityPo resultPo = moduleAuthorityDao.queryByCombindTerm(result, moduleAuthorityPo).getData();
        if(resultPo != null){
            ModuleAuthorityVo resultVo = new ModuleAuthorityVo();
            BeanUtils.copyProperties(resultPo, resultVo);

            Date currentDate = new Date();
            //判断活动时间
            if(resultVo.getBeginTime() != null && resultVo.getEndTime() != null){
                //开始时间小于等于当前时间
                if(resultVo.getBeginTime().getTime() <= currentDate.getTime()
                        && resultVo.getEndTime().getTime() > currentDate.getTime()){
                    resultVo.setActivityStatus(true);
                }
            }

            //查询接口时间
            if(resultVo.getCheckEndTime() != null){
                if(resultVo.getCheckEndTime().getTime() > currentDate.getTime()){
                    resultVo.setQueryStatus(true);
                }
            }

            result.setData(resultVo);
        }

        return result;
    }


    /**
     * 查询是否 未结束话费活动
     * 
     * 
     * @param result
     * @param areaId
     * @return
     */
    public Result queryPhoneActivityByAreaId(Result result,Long storeId){
        if(storeId == null){
            throw new BusinessRuntimeException("", "网点信息为空");
        }

        //获取县域信息
        ShopVO shopVO = shopApiService.getShop(result, result.getUserInfo().getBizId());
        if(shopVO == null){
            throw new BusinessRuntimeException("", "县域信息为空");
        }

        result = moduleAuthorityDao.queryByCombindTermAndNotEnding(result,  EnumAuthBusiness.YYMD.getProjectCode(), 
                EnumAuthBusiness.YYMD.getBusinessCode(), Integer.parseInt(shopVO.getAreaId()+""));
        return result;
    }



    /**
     * 话费分页查询活动地区设置
     * 
     * @param result
     * @param page
     *          分页参数对象
     * @param params
     *          查询参数对象
     * @return
     *           分页对象
     */
    public Result queryPagePhoneActivityArea(Result result,Page page,QueryModuleAuthorityVo params){
        params.setProjectCode(EnumAuthBusiness.YYMD.getProjectCode());
        params.setBusinessCode(EnumAuthBusiness.YYMD.getBusinessCode());
        Pagination pagination =  moduleAuthorityDao.queryPageActivityArea(result, page, params).getData();

        List<ModuleAuthorityVo> listResult = new ArrayList<>();
        if(pagination != null && CollectionUtils.isNotEmpty(pagination.getRows())){

            List<ModuleAuthorityPo> lstMAPO = (List<ModuleAuthorityPo>) pagination.getRows();
            for(ModuleAuthorityPo mop:lstMAPO){
                ModuleAuthorityVo maVo = new ModuleAuthorityVo();
                BeanUtils.copyProperties(mop,maVo);

                //设置活动状态
                EnumActivityStatus activityStatus = getAcvitityStatus(maVo.getBeginTime(),maVo.getEndTime());
                if(activityStatus != null){
                    maVo.setStrActivityStatus(activityStatus.getDesc());
                }

                listResult.add(maVo);
            }

            pagination.setRows(listResult);
        }

        result.setData(pagination);
        return result;
    }



    /**
     * 设置活动状态
     * 
     * @param maVo
     */
    private EnumActivityStatus getAcvitityStatus(Date beginTime,Date endTime) {
        long nowTimer = new Date().getTime();
        if(beginTime == null || endTime == null ){
            return null;
        }

        if(beginTime.getTime() > nowTimer){
            return EnumActivityStatus.NOT_START;
        }
        else if(beginTime.getTime() < nowTimer 
                && endTime.getTime() > nowTimer){
            return EnumActivityStatus.STARTING;

        }else if(endTime.getTime() <= nowTimer){
            return EnumActivityStatus.ENDED;
        }

        return null;
    }


    /**
     * 根据 县域ID 查询是否已经有一元免单
     * 
     * @param result
     * @param moduleAuthorityPo
     * @return
     */
    public Result queryYYMDByBusiness(Result result,Integer areaId) {
        ModuleAuthorityPo moduleAuthorityPo = new ModuleAuthorityPo();
        moduleAuthorityPo.setAreaId(areaId);
        moduleAuthorityPo.setProjectCode(EnumAuthBusiness.YYMD.getProjectCode());
        moduleAuthorityPo.setBusinessCode(EnumAuthBusiness.YYMD.getBusinessCode());
        return  moduleAuthorityDao.queryByCombindTerm(result, moduleAuthorityPo);

    }


    /**
     * 根据 县域ID 查询是否已经有一元免单
     * 
     * @param result
     * @param moduleAuthorityPo
     * @return
     */
    public Result queryByBusiness(Result result,String projectCode,String businessCode ,Integer areaId) {
        ModuleAuthorityPo moduleAuthorityPo = new ModuleAuthorityPo();
        moduleAuthorityPo.setAreaId(areaId);
        moduleAuthorityPo.setProjectCode(projectCode);
        moduleAuthorityPo.setBusinessCode(businessCode);
        return  moduleAuthorityDao.queryByCombindTerm(result, moduleAuthorityPo);

    }



    /**
     * 保存’一元免单‘ 活动
     * 
     * 
     * @param result
     * @param moduleAuthorityVo
     * @return
     */
    public Result saveYYMDActivity(Result result,ModuleAuthorityVo moduleAuthorityVo){

        //参数校验
        validateParams(moduleAuthorityVo);

        //全部县域。将所有县域添加到数据库
        if(moduleAuthorityVo.getReceiveScore().equals("1")){
            List<AreaInfoVO> lstAreaVo =  areaService.queryListAreas(result);

            if(CollectionUtils.isNotEmpty(lstAreaVo)){
                for(AreaInfoVO area : lstAreaVo){
                    log.info("#-------添加当前县域位：{}",JSON.toJSONString(area));

                    this.addYYMDActivity(result, moduleAuthorityVo,area.getAreaName(), Integer.parseInt(area.getId()+""));
                }
            }
        }
        //自定义县域
        else if(moduleAuthorityVo.getReceiveScore().equals("2")){
            List<ReceiveShopVo> lstResult = moduleAuthorityVo.getLstReceiveShop();

            if(CollectionUtils.isNotEmpty(lstResult)){
                for(ReceiveShopVo area : lstResult){
                    this.addYYMDActivity(result, moduleAuthorityVo,area.getAreaName(), area.getAreaId());
                }
            }
        }


        return result;

    }


    /**
     * 修改
     * @param result
     * @param moduleAuthorityVo
     * @return
     */
    public Result updateYYMDActivity(Result result,ModuleAuthorityVo moduleAuthorityVo){

        if(StringUtils.isEmpty(moduleAuthorityVo.getUpdateIds())){
            throw new BusinessRuntimeException("","修改id为空！");
        }

        //时间参数校验
        validateParams(moduleAuthorityVo);

        String[] ids = moduleAuthorityVo.getUpdateIds().split(",");
        for(String id : ids){
            ModuleAuthorityPo po =  moduleAuthorityDao.get(Integer.parseInt(id));

            //状态判断
            /*  EnumActivityStatus activityStatus = getAcvitityStatus(po.getBeginTime(),po.getEndTime());
            if(activityStatus == null){
                log.info("当前id:{}数据有问题,不做操作。",po.getId());
                continue;
            }
            if(activityStatus.getStatus() == EnumActivityStatus.STARTING.getStatus()){
                log.info("当前id:{}状态正在进行中，不做操作。",po.getId());
                continue;
            }*/

            //修改日志记录
            insertActivityLog(result,po,moduleAuthorityVo);

            //修改
            if(moduleAuthorityVo.getBeginTime() != null){
                po.setBeginTime(moduleAuthorityVo.getBeginTime());
            }
            if(moduleAuthorityVo.getEndTime() != null){
                po.setEndTime(moduleAuthorityVo.getEndTime());
            }
            if(moduleAuthorityVo.getCheckEndTime() != null){
                po.setCheckEndTime(moduleAuthorityVo.getCheckEndTime());
            }

            moduleAuthorityDao.update(po);


        }

        return result;

    }

    /**
     *  添加修改历史
     * 
     * @param result
     * @param moduleAuthority
     */
    private void insertActivityLog(Result result, ModuleAuthorityPo moduleAuthority,ModuleAuthorityVo moduleAuthorityVo) {
        ModuleAuthorityHistoryVo moduleAuthorityHistoryVo = new ModuleAuthorityHistoryVo();
        moduleAuthorityHistoryVo.setAreaId(moduleAuthority.getAreaId());
        moduleAuthorityHistoryVo.setAreaName(moduleAuthority.getAreaName());
        if(moduleAuthorityVo.getBeginTime() != null){
            moduleAuthorityHistoryVo.setStartTime(moduleAuthorityVo.getBeginTime());
        }else{
            moduleAuthorityHistoryVo.setStartTime(moduleAuthority.getBeginTime());
        }

        if(moduleAuthorityVo.getEndTime() != null){
            moduleAuthorityHistoryVo.setEndTime(moduleAuthorityVo.getEndTime());
        }else{
            moduleAuthorityHistoryVo.setEndTime(moduleAuthority.getEndTime());
        }

        if(moduleAuthorityVo.getCheckEndTime() != null){
            moduleAuthorityHistoryVo.setShowEndTime(moduleAuthorityVo.getCheckEndTime());
        }else{
            moduleAuthorityHistoryVo.setShowEndTime(moduleAuthority.getCheckEndTime());
        }

        moduleAuthorityHistoryVo.setCreateTime(new Date());
        historyService.saveHistory(result, moduleAuthorityHistoryVo);
    }


    /*
     * 新增，如果存在，不进行操
     */
    private void addYYMDActivity(Result result,ModuleAuthorityVo moduleAuthorityVo,String areaName,Integer areaId){
        //如果存在，直接返回
        ModuleAuthorityPo moduleAuthorityPo = queryYYMDByBusiness(result, areaId).getData();
        if(moduleAuthorityPo != null){
            log.info("areaId:{},areaName:{} 已经存在。",areaId,areaName);
            return;
        }

        //设置参数
        ModuleAuthorityPo paramsPo = new ModuleAuthorityPo();
        BeanUtils.copyProperties(moduleAuthorityVo,paramsPo);
        paramsPo.setAreaId(areaId);
        paramsPo.setAreaName(areaName);
        paramsPo.setBusinessCode(EnumAuthBusiness.YYMD.getBusinessCode());
        paramsPo.setProjectCode(EnumAuthBusiness.YYMD.getProjectCode());

        moduleAuthorityDao.addModuleAuthority(result, paramsPo);


        //插入历史记录
        insertActivityLog(result, paramsPo, moduleAuthorityVo);
    }


    private void validateParams(ModuleAuthorityVo params){
        long nowTime = new Date().getTime();
        if(params.getBeginTime() != null){
            if(params.getBeginTime().getTime() < nowTime){
                throw new BusinessRuntimeException("","开始时间不能小于当前时间");
            }

            if(params.getEndTime().getTime() < params.getBeginTime().getTime()){
                throw new BusinessRuntimeException("","结束时间不能小于开始时间");
            }
        }

        if(params.getCheckEndTime().getTime() < nowTime ){
            throw new BusinessRuntimeException("","中奖显示截止时间不能小于开始时间");
        }

        if(params.getCheckEndTime().getTime() < params.getEndTime().getTime() ){
            throw new BusinessRuntimeException("","中奖显示截止时间不能小于结束时间");
        }
    }
}
