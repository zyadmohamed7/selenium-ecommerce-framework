package com.seleniumframework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TimeManager {
    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS").format(new Date());
    }
    public static String getSimpleTimeStamp(Date date) {
        return Long.toString(System.currentTimeMillis());
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getUniqueName(String prefix) {
        String shortTime = new SimpleDateFormat("HH-mm-ss").format(new Date());
        String shortUUID = UUID.randomUUID().toString().substring(0, 4);
        return prefix + "_" + shortTime + "_" + shortUUID;
    }
}
