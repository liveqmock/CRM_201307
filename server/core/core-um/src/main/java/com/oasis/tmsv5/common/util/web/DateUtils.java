package com.oasis.tmsv5.common.util.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    
    public static final String MONTH_FORMAT = "yyyy-MM";
    
    public static final String DATE_FORMAT_NOT_SEP = "yyyyMMdd";

    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public static String formatMonth(Date date) {
        return dateFormat(date, MONTH_FORMAT);
    }

    public static String formatDate(Date date) {
        return dateFormat(date, DATE_FORMAT);
    }
    
    public static String formatDateNotSep(Date date) {
        return dateFormat(date, DATE_FORMAT_NOT_SEP);
    }

    public static String formatTime(Date date) {
        return dateFormat(new Date(), TIME_FORMAT);
    }

    public static String formatDateTime(Date date) {
        return dateFormat(date, DATE_TIME_FORMAT);
    }

    public static String formatDate() {
        return dateFormat(new Date(), DATE_FORMAT);
    }

    public static String formatTime() {
        return dateFormat(new Date(), TIME_FORMAT);
    }

    public static String todayDate() {
        return dateFormat(new Date(), DATE_FORMAT);
    }

    public static String todayTime() {
        return dateFormat(new Date(), DATE_TIME_FORMAT);
    }

    public static Date parseDate(String date) throws Exception {
        return parseDate(date, DATE_FORMAT);
    }

    public static Date parseTime(String date) throws Exception {
        return parseDate(date, TIME_FORMAT);
    }

    public static Date parseDateTime(String date) throws Exception {
        return parseDate(date, DATE_TIME_FORMAT);
    }

    public static String dateFormat(Date date, String formatStr) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        return simpleDateFormat.format(date);
    }

    public static Date parseDate(String date, String formatStr) throws Exception {
        if (date == null || date.length() == 0) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        return simpleDateFormat.parse(date);
    }

    public static Calendar addCalendarDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar;
    }

    public static Date addDay(int day) {
        return addCalendarDay(day).getTime();
    }
}
