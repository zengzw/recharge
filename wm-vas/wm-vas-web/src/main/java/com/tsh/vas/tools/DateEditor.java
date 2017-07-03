/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.tsh.vas.tools;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.expression.ParseException;

/**
 *  自定义日期格式（stirng）转换类
 *  
 * @author zengzw
 * @date 2016年9月7日
 */

public class DateEditor extends PropertyEditorSupport {

    private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat TIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateFormat dateFormat;
    private boolean allowEmpty = true;

    public DateEditor() {
    }

    public DateEditor(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateEditor(DateFormat dateFormat, boolean allowEmpty) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !org.springframework.util.StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else {
            try {
                if(this.dateFormat != null)
                    setValue(this.dateFormat.parse(text));
                else {
                    if(text.contains(":")){

                        setValue(TIMEFORMAT.parse(text));
                    }

                    else{
                        setValue(DATEFORMAT.parse(text));
                    }
                }
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        DateFormat dateFormat = this.dateFormat;
        if(dateFormat == null)
            dateFormat = TIMEFORMAT;
        return (value != null ? dateFormat.format(value) : "");
    }

}

