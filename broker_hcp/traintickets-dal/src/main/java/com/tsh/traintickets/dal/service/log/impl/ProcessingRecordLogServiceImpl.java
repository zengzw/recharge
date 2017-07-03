package com.tsh.traintickets.dal.service.log.impl;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.HcpSupplierType;
import com.traintickets.common.HttpType;
import com.traintickets.common.LogType;
import com.tsh.traintickets.dal.service.log.ProcessingRecordLogService;
import com.tsh.traintickets.dao.HcpProcessingRecordLogDao;
import com.tsh.traintickets.po.HcpProcessingRecordLog;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 * Created by Administrator on 2016/11/22 022.
 */
@Service
public class ProcessingRecordLogServiceImpl implements ProcessingRecordLogService{

    @Resource
    HcpProcessingRecordLogDao hcpProcessingRecordLogDao;

    @Override
    @Async
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void saveLog(Object request, HcpSupplierType supplierType, HttpType httpType, LogType logType, String methodName, String messageId, long cost) {

        HcpProcessingRecordLog recordLog = new HcpProcessingRecordLog();
        recordLog.setCreateTime(System.currentTimeMillis());
        recordLog.setLogContent(StringEscapeUtils.unescapeJava(JSON.toJSONString(request)));
        recordLog.setLogType(logType.getCode());
        recordLog.setMethodName(methodName);
        recordLog.setMsgId(messageId);
        recordLog.setSupplierType(supplierType.toString());
        recordLog.setHttpType(httpType.toString());
        recordLog.setCost(cost);
        hcpProcessingRecordLogDao.save(recordLog);

    }
}
