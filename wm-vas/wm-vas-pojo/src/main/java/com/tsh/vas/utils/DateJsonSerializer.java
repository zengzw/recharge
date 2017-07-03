/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 *
 * @author zengzw
 * @date 2017年5月15日
 */
public class DateJsonSerializer  extends JsonSerializer<Date>  
{  
    public static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    public void serialize(Date date,JsonGenerator jsonGenerator,SerializerProvider serializerProvider)  
            throws IOException,JsonProcessingException   
    {  
        
        jsonGenerator.writeString(format.format(date));    
    }    
}
