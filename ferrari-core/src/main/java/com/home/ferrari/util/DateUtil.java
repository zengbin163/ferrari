/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

/** 
* @ClassName: DateUtil 
* @Description: 日期相关工具类
* @author zengbin
* @date 2015年11月14日 下午5:13:10 
*/
public class DateUtil {
    private static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN1 = "yyyyMMddHHmmss";
    
    public static Long currentUTCTime(){
    	return new Date().getTime();
    }
    
    public static String formatTime(String timePattern, Date date) {
    	if(null==date) {
    		return null;
    	}
    	DateFormat dateFormat = new SimpleDateFormat(timePattern);
    	return dateFormat.format(date);
    }
    
    public static String defaultFormatTime(Date date) {
        return formatTime(DEFAULT_TIME_PATTERN, date);
    }
    
    public static String defaultFormatDate(Date date) {
    	if(null==date) {
    		return null;
    	}
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        return dateFormat.format(date);
    }
    
    public static Date defaultParseTime(String date) {
    	if(StringUtils.isEmpty(date)) {
    		return null;
    	}
    	DateFormat dateFormat = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Date defaultParseDate(String date) {
    	if(StringUtils.isEmpty(date)) {
    		return null;
    	}
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getPreTime(String beginTime,String endTime) {
    	long begin = DateUtil.defaultParseTime(beginTime).getTime();
    	long end = DateUtil.defaultParseTime(endTime).getTime();
    	long interval = end - begin;
    	return DateUtil.defaultFormatTime(new Date(begin-interval));
    } 

    public static Date getIntervalTime(long days) {
    	long now = new Date().getTime();
    	long interval = days * 24 * 60 * 60 * 1000;
    	return new Date(now + interval);
    } 
    
    public static Long getSecondTime() {
        Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.SECOND));
        return 0L;
    }
    
    public static Integer getDayBetweenTime(Date date1, Date date2) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date1);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date2);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        return (day1 - day2);
    }
    
    public static Integer getCurrentYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
    }

    public static Integer getCurrentMonth() {
    	Calendar c = Calendar.getInstance();
    	return c.get(Calendar.MONTH)+1;
    }
    
    public static final void main(String []args) {
    	System.out.println(DateUtil.defaultFormatTime(new Date()));
    	System.out.println(DateUtil.formatTime(TIME_PATTERN1, new Date()));
    }
}
