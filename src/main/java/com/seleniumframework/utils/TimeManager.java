package com.seleniumframework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeManager {
    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
    public static String getSimpleTimeStamp(Date date) {
        return Long.toString(System.currentTimeMillis());
    }
}
