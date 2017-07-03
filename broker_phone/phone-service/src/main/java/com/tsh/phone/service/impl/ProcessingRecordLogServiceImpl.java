package com.tsh.phone.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dtds.platform.util.UUIDUtil;

import com.tsh.phone.dao.PhoneProcessingRecordLogDao;
import com.tsh.phone.po.PhoneProcessingRecordLog;
import com.tsh.phone.service.IProcessingRecordLogService;


/**
 * Created by Administrator on 2016/11/22 022.
 */
@Service
public class ProcessingRecordLogServiceImpl implements IProcessingRecordLogService{

    @Resource
    PhoneProcessingRecordLogDao processingRecordLogDao;

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @Override
    public void saveLog(String reqeuest,String response, String supplierType, String httpType, int logType, String methodName,
            long cost) {
        String messageId = UUIDUtil.getUUID();
        PhoneProcessingRecordLog recordLog = new PhoneProcessingRecordLog();
        recordLog.setCreateTime(new Date());
        recordLog.setReqeustContent(reqeuest);
        recordLog.setResponseContent(response);
        recordLog.setLogType(logType);
        recordLog.setMethodName(methodName);
        recordLog.setMsgId(messageId);
        recordLog.setSupplierType(supplierType);
        recordLog.setHttpType(httpType);
        recordLog.setCost(cost);
        
        processingRecordLogDao.save(recordLog);
    }

}
