package com.tsh.openpf.controller;

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

	private static List<String> list = new ArrayList<String>();

	@Override
	public List<String> getExcludeUris() {
		if (ListUtil.isEmpty(list)) {
			list.add("/index.do");
			list.add("/openpf/");
		}
		return list;
	}
}
