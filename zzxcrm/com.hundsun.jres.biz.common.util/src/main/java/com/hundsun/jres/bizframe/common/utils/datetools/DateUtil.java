/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : DateUtil.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.common.utils.datetools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 功能说明: 对日期进行date与string转换<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hundsun.com <br>
 * 开发时间: 2010-7-15<br>
 * <br>
 */
public class DateUtil {

	private static final SimpleDateFormat[] dayFormatter = {
			new SimpleDateFormat("yyyy年MM月dd日"),// 0
			new SimpleDateFormat("yyyy-MM-dd"),// 1
			new SimpleDateFormat("yyyy/MM/dd"),// 2
			new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒"),// 3
			new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss"),// 4
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),// 5
			new SimpleDateFormat("HHmmss"), // 6
			new SimpleDateFormat("yyyyMMddHHmmss"),// 7
			new SimpleDateFormat("yyyy-MM-dd HH:mm"),// 8
			new SimpleDateFormat("HH:mm"),// 9
			new SimpleDateFormat("dd/MM/yyyy"),// 10
			new SimpleDateFormat("MM-dd"),// 11
			new SimpleDateFormat("yyyy"),// 12
			new SimpleDateFormat("yyyy-MM-dd HH"),// 13
			new SimpleDateFormat("yyyyMMdd"),// 14
			new SimpleDateFormat("yyyy-MM"),// 15
			new SimpleDateFormat("ss mm HH dd MM E yyyy", Locale.US), // 16
			new SimpleDateFormat("yyyyMMddHHmm"), // 17
			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"), // 18
			new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US),// 19
			new SimpleDateFormat("MMM dd yyyy", Locale.US), // 20
			new SimpleDateFormat("yyyyMMdd") // 21
	};
	
    /**
     * 按照<code>kind</code>来生成日期string格式
     * @param date
     * @param kind
     * @return
     */
    public static String dateString(Date date, int kind) {
        if (kind < 0 || kind >= dayFormatter.length) {
            kind = 0;
        }
        if (date == null)
            return "";
        return (dayFormatter[kind].format(date));
    }
    
    /**
     * 按照<code>kind</code>来生成date类型日期
     * @param inputDate
     * @param kind
     * @return
     */
    public static Date stringDate(String inputDate, int kind) {
        Date tmp = null;
        try {
            tmp = dayFormatter[kind].parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tmp;
    }
    
    /**
     * 获得年月日的数字值
     * e.g. 20100715 [yyyyMMdd]
     * @param date
     * @return
     */
    public static int getYearMonthDay(Date date) {
        return Integer.parseInt(dateString(date, 21));
    }
        
    /**
     * 获得小时分秒数字值
     * e.g. 135630 [HHmmss]
     * @param date
     * @return
     */
    public static int getHourMinuteSecond(Date date){
    	return Integer.parseInt(dateString(date, 6));
    }
    
    /**
     * 获得年月日时分秒的数字值
     * @param date
     * @return
     */
    public static String getYearMonDayHourMinSec(Date date) {
        return dateString(date, 17);
    }
    
    /**
     * 将整形数字“20100720”转成毫秒数
     * @param date
     * @return 时间
     */
    public static long getTime(Integer date){
    	 String timeStr = String.valueOf(date);
    	 if(timeStr.length()==8){
    		 try {
				return dayFormatter[14].parse(timeStr).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	 }
    	 return -1; 
    }
}
