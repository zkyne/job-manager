package com.zkyne.jobmanager.common.util;

/**
 * 登录相关
 */
public class AuthUtil {
    private final static ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static void addCurrentUserId(long userId) {
        currentUserId.set(userId);
    }

    public static long getCurrentUserId() {
        Long userId = currentUserId.get();
        return userId != null ? userId : 0;
    }

    public static void clear() {
        currentUserId.remove();
    }

    private AuthUtil(){}
}
