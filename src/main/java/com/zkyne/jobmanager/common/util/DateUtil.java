package com.zkyne.jobmanager.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateUtil {
    private DateUtil(){}

    public static final long MINUTE = 60000;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    // yyyy-MM-dd hh:mm:ss 小写12小时制   yyyy-MM-dd HH:mm:ss  大写H，24小时制
    public final static String DEFAULT_PATTERN = "yyyyMMdd";

    public final static String F24_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String DAY_PATTERN = "yyyy-MM-dd";

    private static ThreadLocal<Map<String, SimpleDateFormat>> _simpleDateFormat = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new ConcurrentHashMap<String, SimpleDateFormat>();
        }
    };

    private static final Date _emptyDate = new Date(0);

    public static String Today = getToday();

    public static int getCurrentSeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static String format(Date time) {
        return getFormat(DEFAULT_PATTERN).format(time);
    }

    public static String format(Date time, String formart) {
        return getFormat(formart).format(time);
    }

    public static String format(long time) {
        return getFormat(DEFAULT_PATTERN).format(time);
    }

    public static String format(long currentTime, long time) {
        int min = (int) ((currentTime - time) / MINUTE);
        if (min <= 0)
            return "刚刚";
        if (min < 60)
            return min + "分钟前";
        if (min < 24 * 60)
            return (min / 60) + "小时前";
        return getFormat("Y年M月d日 H:mm:ss").format(time);
    }

    public static void setToday() {
        Today = getToday();
    }

    /**
     * 判断是否为同一天 
     * 支持  yyyyMMdd  yyyyMMdd hh:mm:ss 两种格式
     * @param date
     * @return
     */
    public static boolean isSameDay(String date) {
        if (DataUtil.isEmpty(date))
            return false;
        date = date.split(" ")[0];
        date = date.replace("-", "");
        return date.equals(getToday());
    }

    /**
     * 给外部使用，mopper直接用 Today 变量
     * @return
     */
    public static String getToday() {
        return getFormat(DEFAULT_PATTERN).format(new Date());
    }

    /**
     * @see DateUtil#getDayInt(long)
     */
    public static int getDayInt(Date date) {
        return date == null ? 0 : getDayInt(date.getTime());
    }

    /**
     * 得到从1970年1月1日到此日期的天数<br>
     * 可以利用返回值进行日期间隔的比较<br>
     * <br>
     * 适用于不需要构造Date对象的情况，如使用System.currentTimeMillis作为参数
     */
    public static int getDayInt(long time) {
        return (int) (time / DAY);
    }

    /**
     * 得到多少分钟的int 值
     * @param time  时间
     * @param m  几分钟
     * @return
     */
    public static int getMinuteInt(long time, int m) {
        return (int) (time / (MINUTE * m));
    }

    /**
     * 往前或者后 推多少天
     * @param days
     * @return
     */
    public static Calendar addDay(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar;
    }

    public static Date addDay(Date date, int days) {
        return new Date(date.getTime() + days * DAY);
    }

    /**
     * 往前或者后 推多少天, 返回字符串格式yyyyMMdd
     * @param date yyyyMMdd
     * @param days
     * @return
     */
    public static String addDay(String date, int days) {
        Date d = parse(date, DEFAULT_PATTERN);
        Date date2 = addDay(d, days);
        return getFormat(DEFAULT_PATTERN).format(date2);
    }

    public static Calendar addHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar;
    }

    public static Date addMinute(int minute) {
        return new Date(System.currentTimeMillis() + MINUTE * minute);
    }

    public static String getLocaleTime() {
        Date date = new Date();

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINA);

        return dateFormat.format(date);
    }

    public static String getMonthDir() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/";
    }

    public static String getDayDir() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE)
                + "/";
    }

    /**
     * 解析时间
     * 
     * @param time
     *            表示时间的字符串，如"2009-09-15"
     * @param form
     *            表示格式的字符串，如"yyyy-MM-dd"
     * @return 如解析失败，返回new Date(0)
     */
    public static Date parse(String time, String form) {
        if (DataUtil.isEmpty(form))
            return _emptyDate;
        SimpleDateFormat format = getFormat(form);
        try {
            return format.parse(time);
        } catch (ParseException e) {
            return _emptyDate;
        }
    }

    /**
     * 使用若干种规则解析时间
     * 
     * @see #parse(String, String)
     */
    public static Date parse(String time, String... forms) {
        for (String form : forms) {
            if (DataUtil.isEmpty(form))
                continue;
            SimpleDateFormat format = getFormat(form);
            try {
                return format.parse(time);
            } catch (ParseException e) {
            }
        }
        return _emptyDate;
    }

    /**
     * 由于SimpleDateFormat很常用，但并不是线程安全，每次new一个出来又有点费
     * 此函数使用ThreadLocal方式缓存SimpleDateFormat，保证性能前提下较好地解决了问题
     */
    public static SimpleDateFormat getFormat(String form) {
        Map<String, SimpleDateFormat> formatMap = _simpleDateFormat.get();
        if (formatMap.containsKey(form)) {
            return formatMap.get(form);
        } else {
            SimpleDateFormat format = new SimpleDateFormat(form);
            formatMap.put(form, format);
            return format;
        }
    }

    /**
     * 获取给定时间的当月开始时间
     * @param date
     * @return yyyy-MM-01 00:00:00
     */
    public static Date getMonthBeginDate(Date date){
        String format = format(date, "yyyy-MM");
        format = format + "-01";
        return parse(format,DAY_PATTERN);
    }

    /**
     * 获取给定时间的周一凌晨开始时间
     * @param date
     * @return yyyy-MM-dd 00:00:00
     */
    public static Date getWeekBeginDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String timeBegin = format(cal.getTime(), DAY_PATTERN);
        return parse(timeBegin, DAY_PATTERN);
    }

    /**
     * 获取给定时间的周日结束时间(即本周结束时间)
     * @param date
     * @return yyyy-MM-dd 23:59:59
     */
    public static Date getWeekEndDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        // 获取周日结束日期
        cal.add(Calendar.DATE, 7);
        String timeEnd = format(cal.getTime(), DAY_PATTERN);
        Date parse = parse(timeEnd, DAY_PATTERN);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }


    /**
     * 获取一天的开始时间
     * @param date
     * @return
     */
    public static Date getYesterdayBeginDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 获取一天的结束时间
     * @param date
     * @return
     */
    public static Date getYesterdayEndDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime();
    }

    public static void main(String[] args) {

//        Date data = DateUtil.getYesterdayEndDate(new Date());
//        Date data = DateUtil.getWeekBeginDate(new Date());
        Date data = DateUtil.getMonthBeginDate(new Date());
        String format = DateUtil.format(data, F24_PATTERN);
        System.out.println(format);
    }
}
