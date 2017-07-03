package com.tsh.vas.sdm.httplog;

import org.bson.Document;

import com.dtds.platform.data.mongodb.MongodbClient;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ChargePayHttpLog;

/**
 * Created by Iritchie.ren on 2016/10/19.
 */
public class MongoHttpLogTask implements Runnable {
    
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger (getClass ());
    
    private MongodbClient mongodbClient = MongodbClient.getInstance ();
    
    private ChargePayHttpLog chargePayHttpLog;
    
    private Result result;
    
    public MongoHttpLogTask(Result result, ChargePayHttpLog chargePayHttpLog) {
        this.result = result;
        this.chargePayHttpLog = chargePayHttpLog;
    }
    
    @Override
    public void run() {
        mongodbClient.setHost ("172.16.1.191:27017");
        mongodbClient.setDatabase ("vas_new");
        mongodbClient.setCollection ("http_log");
        Document document = new Document ();
        document.put ("name", "renhuan");
        mongodbClient.insertOne (document);
    }
}
