package com.tsh.broker.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Gson getInstance(){
        return gson;
    }
}
