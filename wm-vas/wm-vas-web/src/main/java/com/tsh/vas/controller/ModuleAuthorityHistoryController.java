package com.tsh.vas.controller;

import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.phone.service.ModuleAuthorityHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限校验修改历史 controller
 *
 * 主要用户判断当前县域是否开通网点
 */
@Controller
@RequestMapping("/vas/auth/history/")
public class ModuleAuthorityHistoryController extends BaseController {

    @Autowired
    private ModuleAuthorityHistoryService moduleAuthorityHistoryService;

    /**
     * 查询活动修改历史列表
     * @param areaId
     * @return
     */
    @RequestMapping(value = "list.do")
    @ResponseBody
    public ReturnDTO queryCityInfoPage(Integer areaId){
        Result result = this.getResult();
        result =  moduleAuthorityHistoryService.queryList(result, areaId);
        return result.DTO();
    }


}
