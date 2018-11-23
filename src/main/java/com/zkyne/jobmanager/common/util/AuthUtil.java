package com.zkyne.jobmanager.common.util;

/**
 * 登录相关
 */
public class AuthUtil {
    private final static ThreadLocal<Long> CURRENTUSERID = new ThreadLocal<>();

    public static void addCurrentUserId(long userId) {
        CURRENTUSERID.set(userId);
    }

    public static long getCurrentUserId() {
        Long userId = CURRENTUSERID.get();
        return userId != null ? userId : 0;
    }

    public static void clear() {
        CURRENTUSERID.remove();
    }

    private AuthUtil(){}
}
