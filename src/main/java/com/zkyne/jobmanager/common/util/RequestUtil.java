package com.zkyne.jobmanager.common.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class RequestUtil {

    public static String getParam(final ServletRequest request, String name, String defaultValue) {
        String v = request.getParameter(name);
        return DataUtil.isEmpty(v) ? defaultValue : v;
    }

    public static int getParamInt(final ServletRequest request, String name, int defaultValue) {
        String v = request.getParameter(name);
        if (DataUtil.isEmpty(v)) {
            return defaultValue;
        }
        return DataUtil.toInt(v);
    }

    public static Map<String, String> getParamterMap(final String param) {
        Map<String, String> m = new HashMap<String, String>();
        if (DataUtil.isEmpty(param))
            return m;
        String[] split = param.split("&");
        for (String s : split) {
            String[] ss = s.split("=");
            if (ss.length == 2) {
                m.put(ss[0], ss[1]);
            }
        }
        return m;
    }

    public static Map<String, String> getParameterMap(final ServletRequest request) {
        Map<String, String> m = new HashMap<String, String>();
        // Arrays.toString(a)
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            m.put(key, request.getParameter(key));
        }
        return m;
    }

    public static String toQueryString(Map<String, String> params) {
        if (DataUtil.isEmpty(params))
            return DataUtil.EMPTY;
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : params.entrySet()) {
            sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.substring(1);
    }

    public static Map<String, String> getReuestInfo(final HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uri", request.getRequestURI());
        String ip = request.getParameter("_ip");
        if (DataUtil.isEmpty(ip))
            ip = IpUtil.getIp(request);
        map.put("ip", ip);

        String ua = request.getHeader("User-Agent");
        if (ua != null)
            map.put("ua", ua);
        String r = request.getHeader("Referer");
        if (r != null)
            map.put("r", r);
        String uuid = CookieUtil.getUUIDCookie(request);
        if (uuid != null)
            map.put("uuid", uuid);
        String ajax = request.getHeader("X-Requested-With");
        if (ajax != null)
            map.put("ajax", "1");
        return map;
    }

    public static String getURL(final HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String query = request.getQueryString();
        if (query != null)
            url.append("?").append(query);
        return url.toString();

    }

    public static boolean isAjax(HttpServletRequest request){
        String header = request.getHeader("X-Requested-with");
        return header != null && "XMLHttpRequest".equals(header);
    }

}
