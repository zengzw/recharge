package com.tsh.phone.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dtds.platform.commons.utility.ListUtil;
import com.dtds.platform.web.userinfo.exclude.LoginHandler;

@Component
public class LoginHandlerImpl extends LoginHandler {

	private static List<String> list = new ArrayList<String>();

	@Override
	public List<String> getExcludeUris() {
		if (ListUtil.isEmpty(list)) {
			list.add("/index.do");
			list.add("/getUserName.do");
			list.add("/test/");
			list.add("/views/");
		
			list.add("/rechargephone/");
			list.add("/rechargephone/of/callback");
			list.add("/rechargephone/gy/callback");

			list.add("/rechargephone/fl/");
		}
		return list;
	}
}
