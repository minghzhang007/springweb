package com.lewis.master.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangminghua on 2016/11/11.
 * SimpleDateFormat非线程安全，在高并发情况下，直接使用会出现问题
 * 推荐每个线程一个SimpleDateFormat实例
 */
public class DateFormatUtil {

    private static Logger logger = LoggerFactory.getLogger(DateFormatUtil.class);

    private static final String  dateTimeString="yyyy-MM-dd HH:mm:ss";
    private static final String  dateString="yyyy-MM-dd";

    private static ThreadLocal<SimpleDateFormat> dateThreadLocal = new ThreadLocal<SimpleDateFormat>();
    private static ThreadLocal<SimpleDateFormat> dateTimeThreadLocal = new ThreadLocal<SimpleDateFormat>();

    public static String date2String(Date date){
        SimpleDateFormat simpleDateFormat = dateThreadLocal.get();
        System.out.println("currentThread = "+Thread.currentThread().getId());
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(dateString);
            dateThreadLocal.set(simpleDateFormat);
        }
        return simpleDateFormat.format(date);
    }

    public static Date string2Date(String dateString){
        SimpleDateFormat simpleDateFormat = dateThreadLocal.get();
        System.out.println("currentThread = "+Thread.currentThread().getId());
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(dateString);
            dateThreadLocal.set(simpleDateFormat);
        }
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error("DateFormatUtil string2Date ex:{}",e);
        }
        return null;
    }

    public static String dateTime2String(Date date){
        SimpleDateFormat simpleDateFormat = dateTimeThreadLocal.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(dateTimeString);
            dateThreadLocal.set(simpleDateFormat);
        }
        return simpleDateFormat.format(date);
    }


}
