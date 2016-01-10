package org.liangxiaokou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by moziqi on 2015/12/22 0022.
 */
public class DateUtils {
    /**
     * 功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static long getDaySub(String beginDateStr) {
        long day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate = new Date();
        try {
            beginDate = format.parse(beginDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * http://blog.csdn.net/cilen/article/details/7453285
     * 判断日期格式是否正确
     */
    public static boolean isDateFormat(String dataStr) {
        boolean state = false;
        try {
            java.text.SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
            dFormat.setLenient(false);
            java.util.Date d = dFormat.parse(dataStr);
            state = true;
        } catch (ParseException e) {
            e.printStackTrace();
            state = false;
        }
        return state;
    }
}
