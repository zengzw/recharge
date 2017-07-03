package com.traintickets.common.utils;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringEscapeUtils;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15 015.
 */
public class JsonUtils<T>{

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 把jsonarray转为List<t>
     * @param str
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T convert2List(String str, Type type) {
        str = str.substring(str.indexOf("["));
        str = str.substring(0, str.lastIndexOf("]")+1);
        str = StringEscapeUtils.unescapeJava(str);
        return gson.fromJson(str, type);
    }


    /**
     * 把jsonarray转为List<t>
     * @param str
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T convert2Object(String str, Type type) {
        return gson.fromJson(str, type);
    }

    public static void main(String args[]){
        String jsonStr = "";
//        List<T> rtn = fromJson(jsonStr, new TypeToken<List<T>>(){}.getType());

    }


}
