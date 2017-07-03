package com.tsh.traintickets.dal.service.log;

import com.traintickets.common.HcpSupplierType;
import com.traintickets.common.HttpType;
import com.traintickets.common.LogType;

/**
 * Created by Administrator on 2016/11/22 022.
 */
public interface ProcessingRecordLogService {

    /**
     * 记录日志
     * @param request           请求对象
     * @param supplierType      供应商类型
     * @param httpType          请求类型
     * @param logType           日志类型
     * @param methodName        调用方法名
     * @param messageId         消息ID
     * @param cost              消耗时间
     * @return
     */
    void saveLog(Object request, HcpSupplierType supplierType, HttpType httpType, LogType logType, String methodName, String messageId, long cost);
}
