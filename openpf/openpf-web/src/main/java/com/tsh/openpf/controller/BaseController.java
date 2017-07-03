package com.tsh.openpf.controller;

import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.dtds.platform.web.controller.PlatformController;
import com.dtds.platform.web.userinfo.UserUtil;

/**
 * BaseController
 *
 * @author dengjd
 * @date 2016/10/11
 */
public class BaseController extends PlatformController {

    @Override
    public Result getResult() {
        UserInfo userInfo = UserUtil.getUserInfo(request);
        String teamName = "VAS-N";
        Result result = new Result(request, userInfo, teamName);

        return result;
    }
}
