package com.oasis.tmsv5.util.tools;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateGen {

    public static String current(String format, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return sdf.format(new Date());
    }

    public static boolean isTimeout(int timeoutMinute, Timestamp startTime) {
        if (startTime == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, minute + timeoutMinute);
        return calendar.getTimeInMillis() < System.currentTimeMillis();
    }

    public static String getFormatDate(Date date) {
        if (date == null)
            return null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
        return df.format(date);
    }

    public static String getStringDateByFormat(Date date, String format) {
        if (date == null)
            return null;
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static boolean isSameDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR));

    }

    public static void main(String... s) {
        Calendar calendar = Calendar.getInstance();
        String today = DateGen.getStringDateByFormat(calendar.getTime(), "HH:mm");

        System.out.println("/" + "800BEST" + "/" + "LN" + "/" + today);
    }
}
