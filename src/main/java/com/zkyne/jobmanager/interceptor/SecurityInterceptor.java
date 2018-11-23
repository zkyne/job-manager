package com.zkyne.jobmanager.interceptor;

import com.zkyne.jobmanager.common.constant.Constants;
import com.zkyne.jobmanager.po.SysUser;
import com.zkyne.jobmanager.common.util.AuthUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zkyne
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        // 判断是否为业务调用
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 处理token
        dealUserToken(httpServletRequest);
        long currentUserId = AuthUtil.getCurrentUserId();
        if (currentUserId <= 0) {
            httpServletResponse.sendRedirect("/login");
            return false;
        }
        return true;
    }

    private void dealUserToken(HttpServletRequest httpServletRequest) {
        long userId = 0L;
        Object attribute = httpServletRequest.getSession().getAttribute(Constants.SYS_USER_INFO_STORED_IN_SESSION);
        if (attribute instanceof SysUser) {
            SysUser sysUser = (SysUser) attribute;
            userId = sysUser.getUserId();
        }
        AuthUtil.addCurrentUserId(userId);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
