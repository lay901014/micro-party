package edu.sjtu.party.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_PATTREN = "HH:mm:ss";
    public static final String TIME_PATTREN_1 = "HH:mm";
    public static final String DATATIME_DEFUALT_FORMAT_1 = "yyyy_MM_dd HH_mm_ss";

    private static Calendar gregorianCalendar = new GregorianCalendar();

    public static String format(String pattern) {
        return format(pattern, new Date());
    }

    public static String format(String pattern, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String format(String pattern, Calendar calendar) {
        return format(pattern, calendar.getTime());
    }

    public static Date parse(String pattern, String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(String.format("日期格式有误：%s -> %s", pattern, value));
        }
    }

    public static String getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return Integer.toString(calendar.get(Calendar.YEAR));
    }

    public static List<Date> getDates(Date startDate, Date finishDate) {
        List<Date> result = new ArrayList();
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(11, 0);
        Calendar finish = Calendar.getInstance();
        finish.setTime(finishDate);
        finish.set(11, 0);

        while(start.compareTo(finish) <= 0) {
            result.add(start.getTime());
            start.add(6, 1);
        }

        return result;
    }

    public static List<Date> getHours(Date startTime, Date finishTime) {
        List<Date> result = new ArrayList();
        Calendar start = Calendar.getInstance();
        start.setTime(startTime);
        start.set(12, 0);
        Calendar finish = Calendar.getInstance();
        finish.setTime(finishTime);
        finish.set(12, 0);

        while(start.compareTo(finish) <= 0) {
            result.add(start.getTime());
            start.add(11, 1);
        }

        return result;
    }

    /**
     * 获取日期前一天
     * <p>
     * param date
     * return
     */
    public static Date getDayBefore(Date date) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day - 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期后一天
     * <p>
     * param date
     * return
     */
    public static Date getDayAfter(Date date) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day + 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期后一天
     * <p>
     * param date
     * return
     */
    public static Date getDayAfterNum(Date date, int afterDays) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day + afterDays);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取@param i分钟前后的时间
     * @param date
     * @param i
     * @return
     */
    public static Date getDateTimeByMin(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        if(date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.MINUTE, i);
        Date beforeD = calendar.getTime();
        return beforeD;
    }

    /**
     * 获取当前日期为星期几  0-6
     * @param dt
     * @return
     */
    public static Integer getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return w;
    }

}
