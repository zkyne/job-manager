package com.zkyne.jobmanager.common.constant;

import java.util.regex.Pattern;

/**
 * @ClassName: Constants
 * @Description:
 * @Author: zkyne
 * @Date: 2018/7/17 18:19
 */
public class Constants {
    private Constants(){}
    public static final String SYS_USER_INFO_STORED_IN_SESSION = "sys_user_session_info";
    public static final String SYS_ERROR_MESSAGE = "sys_error_message";
    public static final String DEFAULT_PASSWORD = "zkyne@admin";
    public static final Pattern COMMA_SPLITER = Pattern.compile(",");
    public static final Pattern SEMICOLON_SPLITER = Pattern.compile(";");
    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
}
