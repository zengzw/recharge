package com.tsh.traintickets.dal.service.log;

import com.dtds.platform.util.UUIDUtil;
import com.traintickets.common.HcpSupplierType;
import com.traintickets.common.HttpType;
import com.traintickets.common.LogType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/12/19 019.
 */
public class LogProxyService implements InvocationHandler {

    private static Logger logger = LogManager.getLogger(LogProxyService.class);

    ProcessingRecordLogService processingRecordLogService;

    public void setProcessingRecordLogService(ProcessingRecordLogService processingRecordLogService) {
        this.processingRecordLogService = processingRecordLogService;
    }

    //目标对象
    private Object targetObject;

    private Object request;
    private HcpSupplierType supplierType;
    private LogType logType;
    private String methodName;

    public void setRequest(Object request) {
        this.request = request;
    }

    public void setSupplierType(HcpSupplierType supplierType) {
        this.supplierType = supplierType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    public LogProxyService(Object targetObject){
        this.targetObject = targetObject;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {

        String messageId = UUIDUtil.getUUID();
        logger.info("==========》 save log, messageId:" + messageId);
        processingRecordLogService.saveLog(request, supplierType, HttpType.request, logType, methodName, messageId, 0l);
        long start = System.nanoTime();
        Object result = method.invoke(this.targetObject, args);
        long cost = (System.nanoTime()-start) / 1000000;
        processingRecordLogService.saveLog(result, supplierType, HttpType.response, logType, methodName, messageId, cost);

        return result;
    }
}
