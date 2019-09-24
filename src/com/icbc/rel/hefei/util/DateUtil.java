package com.icbc.rel.hefei.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xujunjie on 17/7/5.
 */
public class DateUtil {

    public static String getFdate() {
        Calendar now = Calendar.getInstance();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fdate = sdf.format(d);
        return fdate;
    }

    public static String getFtime() {
        Calendar now = Calendar.getInstance();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String ftime = sdf.format(d);
        return ftime;
    }

    public static String getRectime() {

        String rectime = null;
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        int seconds = calendar.get(Calendar.SECOND);
        int minutes = calendar.get(Calendar.MINUTE);
        System.out.println(seconds);
        if (seconds < 30) {
            calendar.set(Calendar.SECOND, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(calendar.getTime());
            rectime = dateStr;
            System.out.println(rectime);
        } else {
/*            calendar.set(Calendar.YEAR, 2014);
            calendar.set(Calendar.MONTH,9);//从0开始，0表是1月，1表示2月依次类推
            calendar.set(Calendar.DAY_OF_MONTH, 21);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);*/
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.MINUTE, 1);
            /*System.out.println(calendar.getTime());*/
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(calendar.getTime());
            rectime = dateStr;
           // System.out.println(rectime);
        }
        return rectime;
    }
}

