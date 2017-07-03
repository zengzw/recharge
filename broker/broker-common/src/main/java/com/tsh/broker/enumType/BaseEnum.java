package com.tsh.broker.enumType;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseEnum
 *
 * @author dengjd
 * @date 2016/9/30
 */
public class BaseEnum {
    private int value;
    private String name;

    private static  Map<String,BaseEnum> valueEnumMap = new HashMap<String, BaseEnum>();
    private static  Map<String,BaseEnum> nameEnumMap = new HashMap<String, BaseEnum>();

    public BaseEnum(int value, String name){
        this.value = value;
        this.name = name;

        addValueEnumItem(value);
        addNameEnumItem(name);
    }

    public  BaseEnum valueOf(int value){
        String key = getValueKey(value);
        return valueEnumMap.get(key);
    }

    public  BaseEnum nameOf(String name){
        String key = getNameKey(name);
        return nameEnumMap.get(key);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


    private void addValueEnumItem(int value){
        String key = getValueKey(value);
        if(valueEnumMap.containsKey(key))
            throw new RuntimeException(key + ",已经存在");
        valueEnumMap.put(key,this);
    }

    private void addNameEnumItem(String name){
        String key = getNameKey(name);

        if(nameEnumMap.containsKey(key))
            throw new RuntimeException(key + ",已经存在");
        nameEnumMap.put(key,this);
    }

    private String getValueKey(int value){
        return  this.getClass().getName() + value;
    }

    private String getNameKey(String name){
        return  this.getClass().getName() + name;
    }

}
