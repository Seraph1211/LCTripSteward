package com.example.lctripsteward.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(new Date());
    }

    public static String getLastMonth(){
        Date date = new Date();
        date.setMonth(date.getMonth()-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

}
