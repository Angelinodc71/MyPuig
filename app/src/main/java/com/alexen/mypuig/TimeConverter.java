package com.alexen.mypuig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeConverter {
    public static String converter(long time){

        long unix_seconds = time;
        //convert seconds to milliseconds
        Date date = new Date(unix_seconds*1000L);
        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        String java_date = jdf.format(date);
        System.out.println("\n"+java_date+"\n");
        return java_date;
    }
}
