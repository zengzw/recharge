/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Result;
import com.tsh.vas.dao.ChargePayHttpLogDao;
import com.tsh.vas.model.ChargePayHttpLog;

/**
 *
 * @author zengzw
 * @date 2017年3月21日
 */
@Service
public class ChargePayHttpLogService {


    @Autowired
    ChargePayHttpLogDao chargePayHttpLogDao;

    public Result insert(Result result, ChargePayHttpLog chargePayHttpLog) throws Exception {
        return chargePayHttpLogDao.insert(result, chargePayHttpLog);
    }

}
