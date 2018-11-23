package com.zkyne.jobmanager.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: AppUtil
 * @Description:
 * @Author: zkyne
 * @Date: 2018/4/11 18:46
 */
public class AppUtil{
    private static ThreadLocal<HttpServletRequest> requestMap = new ThreadLocal<>();

    private static ThreadLocal<HttpServletResponse> responseMap = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return requestMap.get();
    }

    public static HttpServletResponse getResponse() {
        return responseMap.get();
    }

    public static void setHttp(HttpServletRequest request,
                               HttpServletResponse response) {
        requestMap.set(request);
        responseMap.set(response);
    }

    public static HttpSession getSession() {
        return requestMap.get().getSession();
    }

    public static boolean isEn() {
        boolean flag = false;
        Object isEn = getSession().getAttribute("isEn");
        if (isEn != null){
            flag = Boolean.parseBoolean(isEn.toString());
        }
        return flag;
    }

    public static void removeAll() {
        requestMap.remove();
        responseMap.remove();
    }

    public static <T> T getBeanFromApplication(ServletContext servletContext, final Class<T> clazz) {
        ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        return application.getBean(clazz);
    }

    private AppUtil(){

    }

}
