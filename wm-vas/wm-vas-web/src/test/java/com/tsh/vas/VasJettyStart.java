package com.tsh.vas;

import com.dtds.platform.test.jetty.JettyServer;

/**
 * Created by Iritchie.ren on 2016/9/19.
 */
public class VasJettyStart {

    public static void main(String[] args) throws Exception {
    	System.setProperty("DTPROJECTNO", "demo");
		System.setProperty("DTENV", "dev");
    	JettyServer.start (8200);
        
    }
}
