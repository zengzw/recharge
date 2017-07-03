package com.tsh.vas.phone.service;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.dao.ModuleAuthorityHistoryDao;
import com.tsh.vas.model.ModuleAuthorityHistoryPo;
import com.tsh.vas.vo.ModuleAuthorityHistoryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
@SuppressWarnings("all")
public class ModuleAuthorityHistoryService {


    private static final Logger log = LoggerFactory.getLogger(ModuleAuthorityHistoryService.class);

    @Autowired
    private ModuleAuthorityHistoryDao moduleAuthorityHistoryDao;


    public Result queryList(Result result, Integer areaId){
        List<ModuleAuthorityHistoryPo> historyPoList = moduleAuthorityHistoryDao.queryByAreaId(result, areaId).getData();
        List<ModuleAuthorityHistoryVo> voList = new ArrayList<>();

        if(null != historyPoList){
            for(ModuleAuthorityHistoryPo po : historyPoList){
                ModuleAuthorityHistoryVo vo = new ModuleAuthorityHistoryVo();
                vo.setStartTimeStr(DateUtil.date2String(po.getStartTime()));
                vo.setEndTimeStr(DateUtil.date2String(po.getEndTime()));
                vo.setShowEndTimeStr(DateUtil.date2String(po.getShowEndTime()));
                vo.setCreateTimeStr(DateUtil.date2String(po.getCreateTime()));
                vo.setAreaName(po.getAreaName());
                voList.add(vo);
            }
        }
        result.setData(voList);
        return result;
    }
    
    
    
    /**
     * 保存历史记录
     * 
     * 
     * @param result
     * @param moduleAuthorityHistoryVo
     * @return
     */
    public Result saveHistory(Result result,ModuleAuthorityHistoryVo moduleAuthorityHistoryVo){
        if(moduleAuthorityHistoryVo == null){
            throw new BusinessRuntimeException("", "参数为空");
        }
        
        ModuleAuthorityHistoryPo moduleAuthorityHistoryPo = new ModuleAuthorityHistoryPo();
        BeanUtils.copyProperties(moduleAuthorityHistoryVo, moduleAuthorityHistoryPo);
        moduleAuthorityHistoryDao.save(moduleAuthorityHistoryPo);
        
        return result;
    }
}
