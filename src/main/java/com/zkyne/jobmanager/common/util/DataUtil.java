package com.zkyne.jobmanager.common.util;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DataUtil {
    private DataUtil(){}

    public static final String EMPTY = "";

    /**
     * 只要有1个为空，即为空
     * 2014-2-18 
     * @param str
     * @return
     */
    public static boolean isEmpty(String... str) {
        for (String s : str) {
            if (isEmpty(s))
                return true;
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isEmpty(Collection<?> coll) {
        return (coll == null || coll.isEmpty());
    }

    public static boolean isEmpty(Map<?, ?> coll) {
        return (coll == null || coll.isEmpty());
    }

    public static <E> Collection<E> addNotHas(Collection<E> source, Collection<E> target) {
        for (E o : target) {
            if (!source.contains(o)) {
                source.add(o);
            }
        }
        return source;
    }

    public static <T> String join(Collection<T> list, final String separator) {
        if (isEmpty(list))
            return EMPTY;
        StringBuilder sb = new StringBuilder();
        for (T e : list) {
            sb.append(separator).append(e);
        }
        sb.delete(0, separator.length());
        return sb.toString();
    }

    public static String join(final String separator, Object... list) {
        StringBuilder sb = new StringBuilder(list[0].toString());
        for (int i = 1; i < list.length; i++) {
            sb.append(separator).append(list[i]);
        }
        return sb.toString();
    }

    public static boolean matcher(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static String getInt(String str) {
        return str.replaceAll("\\D", "");
    }

    public static int toInt(String str) {
        if (isEmpty(str))
            return 0;
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public static long toLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double toDouble(String str) {
        try {
            return Double.parseDouble(str.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public static String formatNumber(Number num, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(num);
    }

    public static String percent(Number num) {
        return formatNumber(num, "#.##%");
    }

    public static void main(String[] args) {

        System.out.println(DataUtil.isEmpty("ss", "sd", null));
    }

    /**
     * 组合字符串
     * 2013-11-27 
     * @param hyphen
     * @param f
     * @return
     */
    public final static String composeKey(String hyphen, Object... f) {
        StringBuilder a = new StringBuilder();
        a.append(f[0]);
        for (int i = 1; i < f.length; i++) {
            a.append(hyphen).append(f[i]);
        }
        return a.toString();
    }

    /**
     * 首字母大写
     * @param str
     * @return
     * <a href="http://my.oschina.net/u/556800" class="referer" target="_blank">@return</a> 
     */
    public static String firstLetterToUpper(String str) {
        Character c = Character.toUpperCase(str.charAt(0));
        return c.toString().concat(str.substring(1));
    }
}
