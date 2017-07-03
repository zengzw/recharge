package com.tsh.phone.service;

/**
 * Created by Administrator on 2016/11/22 022.
 */
public interface IProcessingRecordLogService {

 
     void saveLog(String reqeust,String response,String supplierType, String httpType, int logType, String methodName, long cost);

}
