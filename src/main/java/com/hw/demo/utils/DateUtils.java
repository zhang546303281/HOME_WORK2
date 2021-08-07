package com.hw.demo.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private DateUtils(){

    }

    public static final String format19 = "yyyy-MM-dd HH:mm:ss";

    public static Timestamp getTimeStamp(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return Timestamp.valueOf(df.format(new Date()));
    }
}
