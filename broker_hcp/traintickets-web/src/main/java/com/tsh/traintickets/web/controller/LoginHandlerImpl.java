package com.tsh.traintickets.web.controller;

import com.dtds.platform.commons.utility.ListUtil;
import com.dtds.platform.web.userinfo.exclude.LoginHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginHandlerImpl extends LoginHandler {

	private static List<String> list = new ArrayList();

	@Override
	public List<String> getExcludeUris() {
		if (ListUtil.isEmpty(list)) {
			list.add("/");
			list.add("/index.do");
            list.add("/traintickets/");
		}
		return list;
	}
}
