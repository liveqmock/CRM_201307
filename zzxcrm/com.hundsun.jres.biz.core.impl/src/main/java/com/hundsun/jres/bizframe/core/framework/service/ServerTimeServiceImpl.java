package com.hundsun.jres.bizframe.core.framework.service;

import java.util.Date;

/**
 * 
 * @author huhl
 *
 */
public class ServerTimeServiceImpl implements IServerTimeService {
    
	    
	    @SuppressWarnings("unused")
		private static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		/**
		 * 获取服务器一定格式的时间格式字符串，参照如下：
		 * 
		 * 0  -  yyyy年MM月dd日
		 * 1  -  yyyy-MM-dd
		 * 2  -  yyyy/MM/dd
		 * 3  -  yyyy年MM月dd日-HH时mm分ss秒
		 * 3  -  yyyy/MM/dd-HH:mm:ss
		 * 5  -  yyyy-MM-dd HH:mm:ss
		 * 6  -  HHmmss
		 * 7  -  yyyyMMddHHmmss
		 * 8  -  yyyy-MM-dd HH:mm
		 * 9  -   HH:mm)
		 * 10 -   dd/MM/yyyy
		 * 11 -  MM-dd
		 * 12 -  yyyy
		 * 13 -  yyyy-MM-dd HH
		 * 14 -  yyyyMMdd
		 * 15 -  yyyy-MM
		 * 16 -  ss mm HH dd MM E yyyy
		 * 17 -  yyyyMMddHHmm
		 * 18 -  yyyy/MM/dd HH:mm:ss
		 * 19 -  MMM dd yyyy HH:mm:ss
		 * 20 -  MMM dd yyyy
		 * 21 -  yyyyMMdd
		 * 
	     */
		public String getServerTime() throws Exception {
		    Date date=new Date();
		    return Long.toString(date.getTime());
//		    return weekDays[date.getDay()]+" "+DateUtil.dateString(date, 0);
		}

		
}
