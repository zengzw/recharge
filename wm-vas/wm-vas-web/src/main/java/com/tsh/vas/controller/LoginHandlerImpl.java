package com.tsh.vas.controller;

import com.dtds.platform.commons.utility.ListUtil;
import com.dtds.platform.web.userinfo.exclude.LoginHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录请求URL配置
 */
@Component
public class LoginHandlerImpl extends LoginHandler {

    /**
     * 集合
     */
    private static List<String> list = new ArrayList ();

    @Override
    public List<String> getExcludeUris() {
        if (ListUtil.isEmpty (list)) {
            list.add ("/views/");
            list.add ("/js/");
            list.add ("/css/");
            list.add ("/images/");
            list.add ("/vas/");
            list.add("/static/");

            list.add ("/common/queryAreasByProvinceAndCityAndArea.do");
            list.add ("/common/getShopByInputAreaId.do");
            list.add ("/common/getLoginInfo.do");
            list.add ("/business/add/area");
            list.add ("/app/vas/train/");
            list.add ("/vas/phone/order/back");
            list.add ("/app/vas/phone/");
            list.add ("/vas/phonefare/test/");
            list.add ("/vas/phonefare/");
            list.add ("/netgold/");

            list.add ("/history");


        }
        return list;
    }
}
