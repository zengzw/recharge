/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 *
 * @author zengzw
 * @date 2017年5月15日
 */
public class DateJsonDeserializer extends JsonDeserializer<Date>  
{  
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

    public Date deserialize(JsonParser jsonParser,DeserializationContext deserializationContext)  
            throws IOException,JsonProcessingException  
    {  
        try  
        {  
            if(StringUtils.isNotBlank(jsonParser.getText())){
                return format.parse(jsonParser.getText());  
            }
            
            return null;
        }  
        catch(Exception e)  
        {  
            throw new RuntimeException(e);  
        }   
    }  
}
