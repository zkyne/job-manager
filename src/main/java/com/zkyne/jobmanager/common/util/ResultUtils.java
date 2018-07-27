package com.zkyne.jobmanager.common.util;

import com.google.common.collect.Maps;
import com.zkyne.jobmanager.common.constant.Constants;
import com.zkyne.jobmanager.common.enums.CodeEnum;

import java.util.Map;

/**
 * @ClassName: ResultUtils
 * @Description:
 * @Author: zkyne
 * @Date: 2018/7/17 18:22
 */
public class ResultUtils {

    private ResultUtils() {
    }

    public static Map<String, Object> success(Object obj) {
        Map<String, Object> result = Maps.newHashMap();
        result.put(Constants.CODE, CodeEnum.CODE_SUCCESS.getCode());
        result.put(Constants.MESSAGE, CodeEnum.CODE_SUCCESS.getMessage());
        result.put(Constants.DATA, obj);
        return result;
    }

    public static Map<String, Object> success() {
        return success(null);
    }

    public static Map<String, Object> error(int errorCode, String errorMessage) {
        Map<String, Object> result = Maps.newHashMap();
        result.put(Constants.CODE, errorCode);
        result.put(Constants.MESSAGE, errorMessage);
        return result;
    }

    public static Map<String, Object> error(String errorMessage) {
        return error(CodeEnum.CODE_FAILURE.getCode(), errorMessage);
    }
}
