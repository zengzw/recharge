package com.tsh.broker.service;

import com.tsh.broker.dao.ProcessingRecordLogDao;
import com.tsh.broker.enumType.LogTypeEnum;
import com.tsh.broker.po.ProcessingRecordLog;
import com.tsh.broker.utils.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * ProcessingRecordLogService
 *
 * @author dengjd
 * @date 2016/10/19
 */
@Service
@Transactional
public class ProcessingLogService {

    @Resource
    private ProcessingRecordLogDao processingRecordLogDao;

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public String  writeProcessLog(Logger logger,String methodName,LogTypeEnum logTypeEnum,String content){
        String msgId = RandomUtils.generateUUID();
        writeProcessLog(logger,methodName,logTypeEnum,content,msgId);

        return msgId;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public String  writeProcessLog(Logger logger,String methodName,LogTypeEnum logTypeEnum,String content,String msgId){
        ProcessingRecordLog processingRecordLog = new ProcessingRecordLog();
        processingRecordLog.setMsgId(msgId);
        processingRecordLog.setLogSubject(logger.getName() + "." + methodName);
        processingRecordLog.setLogType(logTypeEnum.getValue());
        processingRecordLog.setLogContent(content);
        processingRecordLog.setCreateTime(System.currentTimeMillis());
        processingRecordLogDao.save(processingRecordLog);
        logger.info(content);

        return msgId;
    }

}
