package com.zkyne.jobmanager.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CookieUtil {

    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0)
            return null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(key))
                return cookies[i].getValue();

        }
        return null;
    }

    /**
    * 删除cookie
    * 
    * @param request
    * @param response
    * @param domain
    * @param keys
    * @return false：没有对应的cookie, true：清除成功
    */
    public static boolean removeCookies(final HttpServletRequest request, final HttpServletResponse response, //
                                        final String domain, final String... keys) {
        checkDomainNotNull(domain);

        final Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return false;
        int delCount = 0;
        for (final Cookie cookie : cookies) {
            final String name = cookie.getName();
            for (String key : keys) {
                if (name.equals(key)) {
                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setDomain(domain);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    delCount++;
                }
            }
        }
        if (delCount == keys.length)
            return true;
        return false;
    }

    /**
     * 设置持久cookie
     * 
     * @param response
     * @param key
     * @param value
     * @param domain
     * @return
     */
    public static boolean setPersistentCookie(final HttpServletResponse response, //
                                              final String key, final String value, final String domain) {
        checkDomainNotNull(domain);

        try {
            final Cookie cookie = new Cookie(key, value);
            cookie.setPath("/");// very important
            cookie.setDomain(domain);
            cookie.setMaxAge(-1); // -1: persist , not set: session, value: seconds
            response.addCookie(cookie);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * 设置session级cookie
     * 
     * @param response
     * @param key
     * @param value
     * @param domain
     * @return
     */
    public static boolean setSessionCookie(final HttpServletResponse response, //
                                           final String key, final String value, final String domain) {
        checkDomainNotNull(domain);

        try {
            final Cookie cookie = new Cookie(key, value);
            cookie.setPath("/");// very important
            cookie.setDomain(domain);
            //cookie.setMaxAge(-1);
            response.addCookie(cookie);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * 设置定时过期的cookie
     * 
     * @param response
     * @param key
     * @param value
     * @param domain
     * @param expiry in seconds
     * @return
     */
    public static boolean setExpiryCookie(final HttpServletResponse response, //
                                          final String key, final String value, final String domain, final int expiry) {
        checkDomainNotNull(domain);

        final Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");// very important
        cookie.setDomain(domain.toString());
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
        return true;
    }

    private static void checkDomainNotNull(final String domain) {
        if (DataUtil.isEmpty(domain))
            throw new IllegalArgumentException("cookie domain is null!");
    }

    /**
     * 获取网站domain
      wbxie
      2013-6-19 
      @param request
      @return
     */
    public static final String getDomain(HttpServletRequest request) {
        String url = request.getServerName();
        return getDomain(url);
    }

    public static final String getDomain(String url) {
        url = url.replace("http://", "");
        if (url.contains("/")) {
            url = url.substring(0, url.indexOf("/"));
        }
        String[] str = url.split("\\.");
        int len = str.length;
        if (len > 2)
            return str[len - 2] + "." + str[len - 1];
        return url;
    }

    /**
     * 取uuid cookie
     * 2013-9-16 
     * @param request
     * @return
     */
    public static String getUUIDCookie(final HttpServletRequest request) {
        return getCookie(request, "_uuid");
    }

    /**
     * 取uuid cookie ，没有会生成，并设置uuid cookie
     * 2013-9-16 
     * @param request
     * @param response
     * @return
     */
    public static String getUUIDCookie(final HttpServletRequest request, final HttpServletResponse response) {
        String uuid = getCookie(request, "_uuid");
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
            setPersistentCookie(response, "_uuid", uuid, getDomain(request));
        }
        return uuid;
    }

}
